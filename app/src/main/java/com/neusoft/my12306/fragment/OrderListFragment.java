package com.neusoft.my12306.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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
public class OrderListFragment extends Fragment implements RadioGroup.OnCheckedChangeListener{
    private RadioGroup radioGroup;
    private ListView listView;
    private List<Map<String,String>> data;


    public OrderListFragment() {
        data = new ArrayList<>();
        String[] str = {"今日订单","未出行订单","历史订单"};
        for(int i=0; i<str.length;i++){
            Map<String,String> map = new HashMap();
            map.put("title",str[i]);
            data.add(map);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group_id);
        listView = (ListView) view.findViewById(R.id.finish_order_listview_id);
        SimpleAdapter sa = new SimpleAdapter(getActivity(),data,R.layout.finishorder_listview_item,new String[]{"title"},new int[]{R.id.finishorder_item_txt_id});
        listView.setAdapter(sa);

        radioGroup.setOnCheckedChangeListener(this);

        return view;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        SearchActivity activity = (SearchActivity) getActivity();
        MyFragmentAdapter adapter = activity.getAdapter();
        switch (checkedId){
            case R.id.unfinish_btn_id:
                adapter.replace(this,new UnfinishOrderFragment());
                break;

            default:
                break;
        }

    }
}
