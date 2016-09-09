package com.neusoft.my12306.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.neusoft.my12306.LoginActivity;
import com.neusoft.my12306.R;
import com.neusoft.my12306.SearchActivity;
import com.neusoft.my12306.adapter.MyFragmentAdapter;
import com.neusoft.my12306.task.SearchUnfinishOrderTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnfinishOrderFragment extends OrderListFragment implements RadioGroup.OnCheckedChangeListener{
    private RadioGroup radioGroup;
    private ListView listView;
    private SharedPreferences sp;

    public UnfinishOrderFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_unfinish_order, container, false);
        radioGroup = (RadioGroup) view.findViewById(R.id.unfinish_radio_group_id);
        listView = (ListView) view.findViewById(R.id.unfinish_listview_id);
        radioGroup.setOnCheckedChangeListener(this);
        sp = getActivity().getSharedPreferences("myfile", Context.MODE_PRIVATE);
        String username = sp.getString("username","");
        if(username.equals("")){
            Intent it = new Intent(getActivity(), LoginActivity.class);
            getActivity().startActivity(it);
        }else {
            SearchUnfinishOrderTask task = new SearchUnfinishOrderTask(getActivity(),listView);
            task.execute(username);
        }
        return view;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        SearchActivity activity = (SearchActivity) getActivity();
        MyFragmentAdapter adapter = activity.getAdapter();
        switch (checkedId){
            case R.id.finish_btn_id2:
                adapter.replace(this,new OrderListFragment());
                break;

            default:
                break;
        }
    }
}
