package com.neusoft.my12306;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import com.neusoft.my12306.adapter.MyExpandableListAdapter;
import com.neusoft.my12306.bean.Train;

import java.util.List;

public class SearchTrainsActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener{
    private ExpandableListView listView;
    private List<Train> trainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trains);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        trainList =  (List<Train>)bundle.getSerializable("trainList");

        //Toast.makeText(this,trainList.get(0).getStationList().get(0).getStation_name(),Toast.LENGTH_LONG).show();
        listView = (ExpandableListView) findViewById(R.id.trainlist_id);
        MyExpandableListAdapter adapter = new MyExpandableListAdapter(this,trainList);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(this);
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("position",String.valueOf(position));
        Log.i("id",String.valueOf(id));
        //取出适配器保存的groupPosition
        int pos = (int)view.getTag(R.id.trainnum_id);
        Train train = trainList.get(pos);
        Intent it = new Intent(this,BuyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("train",train);
        it.putExtras(bundle);
        startActivity(it);
        return true;
    }
}
