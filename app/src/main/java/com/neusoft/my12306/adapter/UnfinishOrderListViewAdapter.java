package com.neusoft.my12306.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.neusoft.my12306.R;
import com.neusoft.my12306.bean.Ticket;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18.
 */
public class UnfinishOrderListViewAdapter extends BaseAdapter {
    private List<Ticket> data;
    private Context ctx;
    public UnfinishOrderListViewAdapter(Context ctx, List<Ticket> data){
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
            convertView= LayoutInflater.from(ctx).inflate(R.layout.unfinish_order_item, null);
            TextView realnameTxt =(TextView)convertView.findViewById(R.id.unfinish_user_realname_id);
            TextView fromDateTxt =(TextView)convertView.findViewById(R.id.unfinish_user_fromdate_id);
            TextView trainNumTxt =(TextView)convertView.findViewById(R.id.unfinish_trainnum_id);
            TextView fromcityTxt =(TextView)convertView.findViewById(R.id.unfinish_fromcity_id);
            TextView tocityTxt =(TextView)convertView.findViewById(R.id.unfinish_tocity_id);
            TextView seatTxt =(TextView)convertView.findViewById(R.id.unfinish_seat_id);
            TextView priceTxt =(TextView)convertView.findViewById(R.id.unfinish_price_id);
            TextView ordertimeTxt =(TextView)convertView.findViewById(R.id.unfinish_ordertime_id);

            realnameTxt.setText(data.get(position).getRealname());
            fromDateTxt.setText(data.get(position).getFrom_date());
            trainNumTxt.setText(data.get(position).getTrain_num());
            fromcityTxt.setText(data.get(position).getFrom_city());
            tocityTxt.setText(data.get(position).getTo_city());
            seatTxt.setText(data.get(position).getSeat());
            priceTxt.setText(data.get(position).getPrice());
            ordertimeTxt.setText(data.get(position).getOrder_datetime());

            holder.realnameTxt = realnameTxt;
            holder.fromDateTxt = fromDateTxt;
            holder.trainNumTxt = trainNumTxt;
            holder.fromcityTxt = fromcityTxt;
            holder.tocityTxt = tocityTxt;
            holder.seatTxt = seatTxt;
            holder.priceTxt = priceTxt;
            holder.ordertimeTxt = ordertimeTxt;
            convertView.setTag(holder);
        }
        else
        {
            holder=(ConvertHolder)convertView.getTag();
        }
        return convertView;
    }

    class ConvertHolder{
        public TextView realnameTxt;
        public TextView fromDateTxt;
        public TextView trainNumTxt;
        public TextView fromcityTxt;
        public TextView tocityTxt;
        public TextView seatTxt;
        public TextView priceTxt;
        public TextView ordertimeTxt;
    }
}
