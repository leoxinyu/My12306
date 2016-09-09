package com.neusoft.my12306.liucanwen.citylist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.neusoft.my12306.R;
import com.neusoft.my12306.liucanwen.citylist.adapter.CityAdapter;
import com.neusoft.my12306.liucanwen.citylist.data.CityData;
import com.neusoft.my12306.liucanwen.citylist.model.CityItem;
import com.neusoft.my12306.liucanwen.citylist.widget.ContactItemInterface;
import com.neusoft.my12306.liucanwen.citylist.widget.ContactListViewImpl;

public class CityListActivity extends AppCompatActivity implements TextWatcher
{
	private Context context_ = CityListActivity.this; 
	private ContactListViewImpl listview;
	private EditText searchBox;
	private String searchString;
	private String fromCity; 
	private String toCity;

	private Object searchLock = new Object();
	boolean inSearchMode = false;

	private final static String TAG = "MainActivity2";

	List<ContactItemInterface> contactList;
	List<ContactItemInterface> filterList;
	private SearchListTask curSearchTask = null;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.citylist);
		Toolbar toolbar = (Toolbar) findViewById(R.id.citylist_toolbar);
		toolbar.setTitle("选择城市");
		setSupportActionBar(toolbar);
		fromCity = getIntent().getExtras().getString("fromCity", "");
		toCity = getIntent().getExtras().getString("toCity", "");

		filterList = new ArrayList<ContactItemInterface>();
		contactList = CityData.getSampleContactList();

		CityAdapter adapter = new CityAdapter(this,R.layout.city_item, contactList);

		listview = (ContactListViewImpl) this.findViewById(R.id.listview);
		listview.setFastScrollEnabled(true);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView parent, View v, int position,
					long id)
			{
				List<ContactItemInterface> searchList = inSearchMode ? filterList
						: contactList;

				//Toast.makeText(context_,searchList.get(position).getDisplayInfo(),Toast.LENGTH_SHORT).show();
				String city = searchList.get(position).getDisplayInfo();
				if(fromCity.equals("")){
					Intent intent1 = new Intent();
					intent1.putExtra("toCity", city);
					setResult(2, intent1);
					finish();
				}
				if(toCity.equals("")){
					Intent intent2 = new Intent();
					intent2.putExtra("fromCity", city);
					setResult(1, intent2);
					finish();
				}
				
			}
		});

		searchBox = (EditText) findViewById(R.id.input_search_query);
		searchBox.addTextChangedListener(this);
	}

	@Override
	public void afterTextChanged(Editable s)
	{
		searchString = searchBox.getText().toString().trim().toUpperCase();

		if (curSearchTask != null
				&& curSearchTask.getStatus() != AsyncTask.Status.FINISHED)
		{
			try
			{
				curSearchTask.cancel(true);
			} catch (Exception e)
			{
				Log.i(TAG, "Fail to cancel running search task");
			}

		}
		curSearchTask = new SearchListTask();
		curSearchTask.execute(searchString); 
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after)
	{
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
		// do nothing
	}

	private class SearchListTask extends AsyncTask<String, Void, String>
	{

		@Override
		protected String doInBackground(String... params)
		{
			filterList.clear();

			String keyword = params[0];

			inSearchMode = (keyword.length() > 0);

			if (inSearchMode)
			{
				// get all the items matching this
				for (ContactItemInterface item : contactList)
				{
					CityItem contact = (CityItem) item;

					boolean isPinyin = contact.getFullName().toUpperCase().indexOf(keyword) > -1;
					boolean isChinese = contact.getNickName().indexOf(keyword) > -1;

					if (isPinyin || isChinese)
					{
						filterList.add(item);
					}

				}

			}
			return null;
		}

		protected void onPostExecute(String result)
		{

			synchronized (searchLock)
			{

				if (inSearchMode)
				{

					CityAdapter adapter = new CityAdapter(context_,R.layout.city_item, filterList);
					adapter.setInSearchMode(true);
					listview.setInSearchMode(true);
					listview.setAdapter(adapter);
				} else
				{
					CityAdapter adapter = new CityAdapter(context_,R.layout.city_item, contactList);
					adapter.setInSearchMode(false);
					listview.setInSearchMode(false);
					listview.setAdapter(adapter);
				}
			}

		}
	}

}
