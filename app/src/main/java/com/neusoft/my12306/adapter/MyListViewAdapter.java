package com.neusoft.my12306.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.neusoft.my12306.R;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18.
 */
public class MyListViewAdapter extends BaseAdapter {
    private List<String> data;
    private Context ctx;
    public MyListViewAdapter( Context ctx,List<String> data){
        this.ctx = ctx;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConvertHolder holder;
        if(null==convertView)
        {
            holder=new ConvertHolder();
            convertView= LayoutInflater.from(ctx).inflate(R.layout.myfragment_listview_item, null);
            TextView txt =(TextView)convertView.findViewById(R.id.my_fragment_listview_item_textview_id);
            txt.setText(data.get(position));
            holder.textView = txt;
            convertView.setTag(holder);
        }
        else
        {
            holder=(ConvertHolder)convertView.getTag();
        }
        return convertView;
    }

    class ConvertHolder{
        public TextView textView;
    }
}
