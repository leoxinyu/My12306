package com.neusoft.my12306.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.neusoft.my12306.R;
import com.neusoft.my12306.SearchActivity;
import com.neusoft.my12306.adapter.MyFragmentAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDataFragment extends MyFragment implements AdapterView.OnItemClickListener,View.OnClickListener{
    private SharedPreferences sp;
    private FragmentManager fm;
    private ListView lv;
    private Button exitBtn;
    private String[] str = {"个人资料修改","联系电话修改","密码修改","邮箱修改"};
    private List<Map<String,String>> data;
    public MyDataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getActivity().getSharedPreferences("myfile", Context.MODE_PRIVATE);

        data = new ArrayList<Map<String, String>>();
        for(int i=0;i<str.length;i++){
            Map map = new HashMap();
            map.put("myinfo",str[i]);
            data.add(map);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_data, container, false);
        exitBtn = (Button)view.findViewById(R.id.exit_btn_id);
        exitBtn.setOnClickListener(this);

        lv = (ListView)view.findViewById(R.id.mydata_lv_id);
        SimpleAdapter sa = new SimpleAdapter(getActivity(),
                data,
                R.layout.myfragment_listview_item,
                new String[]{"myinfo"},
                new int[]{R.id.my_fragment_listview_item_textview_id});
        lv.setAdapter(sa);
        lv.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SearchActivity activity = (SearchActivity)getActivity();
        MyFragmentAdapter adapter = activity.getAdapter();
        switch (position){
            case 0:
                MyInfoUpdateFragment miuf = new MyInfoUpdateFragment();
                adapter.replace(this,miuf);
                break;
            case 1:
                MyTelUpdateFragment myTelUpdateFragment = new MyTelUpdateFragment();
                adapter.replace(this,myTelUpdateFragment);
                break;
            case 2:

                break;
            case 3:

                break;
            default:
                break;
        }


    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("username");
        editor.remove("password");
        editor.commit();
        Intent it = new Intent(getActivity(), SearchActivity.class);
        startActivity(it);
    }
}
