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
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.neusoft.my12306.LoginActivity;
import com.neusoft.my12306.R;
import com.neusoft.my12306.RegActivity;
import com.neusoft.my12306.SearchActivity;
import com.neusoft.my12306.adapter.MyFragmentAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment implements AdapterView.OnItemClickListener{
    private FragmentManager fm;
    private ListView lv;
    private List<Map<String,String>> data;
    private SharedPreferences sp;
    private String username,realname,password;
    private List<String> ls;
    public MyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getActivity().getSharedPreferences("myfile", Context.MODE_PRIVATE);
        username = sp.getString("username","");
        realname = sp.getString("realname","");
        password = sp.getString("password","");

        ls = new ArrayList<String>();
        data = new ArrayList<Map<String, String>>();
        //没有登录保存用户数据的情况
        if(username.equals("")||password.equals("")){
            ls.add("登录");
            ls.add("注册");
        }else {//有登录保存用户数据的情况
            ls.add(realname);
        }
        ls.add("出行向导");
        ls.add("基础数据");
        ls.add("系统通知");
        ls.add("关于系统");

        for(String s:ls){
            Map<String,String> map = new HashMap<String,String>();
            map.put("mydata",s);
            data.add(map);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        lv = (ListView)view.findViewById(R.id.my_fragment_lv_id);
        SimpleAdapter sa = new SimpleAdapter(getActivity(),
                data,
                R.layout.myfragment_listview_item,
                new String[]{"mydata"},
                new int[]{R.id.my_fragment_listview_item_textview_id});
        lv.setAdapter(sa);
        lv.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(ls.get(position).equals("登录")){
            Intent it1 = new Intent(getActivity(),LoginActivity.class);
            startActivity(it1);
        }else if(ls.get(position).equals("注册")){
            Intent it2 = new Intent(getActivity(),RegActivity.class);
            startActivity(it2);
        }else if(ls.get(position).equals("出行向导")){

        }else if(ls.get(position).equals("基础数据")){

        }else if(ls.get(position).equals("系统通知")){

        }else if(ls.get(position).equals("关于系统")){

        }else if(ls.get(position).equals(realname)){
            MyDataFragment myDataFragment = new MyDataFragment();
            SearchActivity searchActivity = (SearchActivity)getActivity();
            MyFragmentAdapter adapter = searchActivity.getAdapter();
            adapter.replace(this,myDataFragment);
        }
    }
}
