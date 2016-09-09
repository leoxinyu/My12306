package com.neusoft.my12306.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.neusoft.my12306.R;
import com.neusoft.my12306.bean.Train;

import java.util.List;

/**
 * Created by Administrator on 2016/8/26.
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Train> trainList;

    public MyExpandableListAdapter(Context context, List<Train> trainList) {
        this.context = context;
        this.trainList = trainList;
    }

    @Override
    public int getGroupCount() {
        return trainList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return trainList.get(groupPosition).getStationList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return trainList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return trainList.get(groupPosition).getStationList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder;
        if (convertView == null) {
            holder = new GroupHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.trainlist_item, null);
            holder.trainNumTxt = (TextView) convertView.findViewById(R.id.trainnum_id);
            holder.myfromCityTxt = (TextView) convertView.findViewById(R.id.myfrom_city_id);
            holder.mytoCityTxt = (TextView) convertView.findViewById(R.id.myto_city_id);
            holder.fromImageView = (ImageView) convertView.findViewById(R.id.from_img_id);
            holder.toImageView = (ImageView) convertView.findViewById(R.id.to_img_id);

            //保存groupPosition
            convertView.setTag(R.id.trainnum_id, groupPosition);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }

        holder.trainNumTxt.setText(trainList.get(groupPosition).getNum());
        holder.myfromCityTxt.setText(trainList.get(groupPosition).getMyfrom_city());
        holder.mytoCityTxt.setText(trainList.get(groupPosition).getMyto_city());

        //如果车的出发城市和乘客的出发城市一样
        if (trainList.get(groupPosition).getFrom_city().equals(trainList.get(groupPosition).getMyfrom_city())) {
            holder.fromImageView.setImageResource(R.drawable.shi);
        }
        //如果车的出发城市和乘客的出发城市不一样
        if (!trainList.get(groupPosition).getFrom_city().equals(trainList.get(groupPosition).getMyfrom_city())) {
            holder.fromImageView.setImageResource(R.drawable.guo);
        }
        //如果车的到达城市和乘客的到达城市一样
        if (trainList.get(groupPosition).getTo_city().equals(trainList.get(groupPosition).getMyto_city())) {
            holder.toImageView.setImageResource(R.drawable.zhong);
        }
        //如果车的到达城市和乘客的到达城市不一样
        if (!trainList.get(groupPosition).getTo_city().equals(trainList.get(groupPosition).getMyto_city())) {
            holder.toImageView.setImageResource(R.drawable.guo);
        }
        return  convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.stationlist_item, null);
        TextView passNumTxt = (TextView) convertView.findViewById(R.id.pass_num_id);
        TextView stationNameTxt = (TextView) convertView.findViewById(R.id.station_name_id);
        int passNum = trainList.get(groupPosition).getStationList().get(childPosition).getPass_num();
        String stationName = trainList.get(groupPosition).getStationList().get(childPosition).getStation_name();
        passNumTxt.setText(String.valueOf(passNum));
        stationNameTxt.setText(stationName);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    //这2个内部类是用来暂存数据
    class GroupHolder {
        public TextView trainNumTxt;
        public TextView myfromCityTxt;
        public TextView mytoCityTxt;
        ImageView fromImageView;
        ImageView toImageView;
    }

//    class ChildHolder{
//        public TextView passNumTxt;
//        public TextView stationNameTxt;
//    }
}
