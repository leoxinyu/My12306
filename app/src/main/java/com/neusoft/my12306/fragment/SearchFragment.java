package com.neusoft.my12306.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.neusoft.my12306.R;
import com.neusoft.my12306.SearchActivity;
import com.neusoft.my12306.liucanwen.citylist.CityListActivity;
import com.neusoft.my12306.task.SearchTask;
import com.neusoft.my12306.util.MyDateUtil;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment implements View.OnClickListener{
    private ImageView changeImageView;
    private Button searchBtn;
    private ListView searchListView;
    private TextView fromCityTxt,toCityTxt,startDateTxt,startTimeTxt;
    private String time;
    private String date;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        changeImageView = (ImageView) view.findViewById(R.id.change_img_id);
        searchBtn = (Button) view.findViewById(R.id.search_btn_id);
        searchListView = (ListView) view.findViewById(R.id.search_lv_id);
        fromCityTxt = (TextView) view.findViewById(R.id.from_id);
        toCityTxt = (TextView) view.findViewById(R.id.to_id);
        startDateTxt = (TextView) view.findViewById(R.id.start_date_txt_id);
        startTimeTxt = (TextView) view.findViewById(R.id.start_time_txt_id);
        fromCityTxt.setOnClickListener(this);
        toCityTxt.setOnClickListener(this);
        changeImageView.setOnClickListener(this);
        startDateTxt.setOnClickListener(this);
        startTimeTxt.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1&&resultCode==1){
            String fromcity = data.getStringExtra("fromCity");
            fromCityTxt.setText(fromcity);
        }
        if(requestCode==2&&resultCode==2){
            String toCity = data.getStringExtra("toCity");
            toCityTxt.setText(toCity);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.from_id:
                Intent it1 = new Intent(getActivity(), CityListActivity.class);
                it1.putExtra("fromCity","fromCity");
                startActivityForResult(it1,1);
                break;
            case R.id.to_id:
                Intent it2 = new Intent(getActivity(), CityListActivity.class);
                it2.putExtra("toCity","toCity");
                startActivityForResult(it2,2);
                break;
            case R.id.change_img_id:
                String tocity = toCityTxt.getText().toString();
                String fromcity = fromCityTxt.getText().toString();
                fromCityTxt.setText(tocity);
                toCityTxt.setText(fromcity);
                break;
            case R.id.start_date_txt_id:
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                          int month = monthOfYear + 1;
                          String monthStr = String.valueOf(month);
                          String dayStr = String.valueOf(dayOfMonth);
                          if(month<10){
                              monthStr = "0"+monthStr;
                          }
                          if(dayOfMonth<10){
                              dayStr = "0"+dayStr;
                          }
                        date = year+"-"+monthStr+"-"+dayStr;
                        startDateTxt.setText(date);
                    }
                }, MyDateUtil.getYear(),MyDateUtil.getMonth(),MyDateUtil.getDay());
                datePickerDialog.show();
                break;
            case R.id.start_time_txt_id:
                final String[] strs = {"00:00-06:00","06:00-12:00","12:00-18:00","18:00-24:00"};
                time = strs[0];
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("请选择时间段")
                       .setSingleChoiceItems(strs, 0, new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              time = strs[which];
                          }
                       })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startTimeTxt.setText(time);
                            }
                        })
                        .create()
                        .show();

                break;
            case R.id.search_btn_id:
                String fromCity = fromCityTxt.getText().toString();
                String toCity = toCityTxt.getText().toString();
                String startTime = startTimeTxt.getText().toString();
                String startDate = startDateTxt.getText().toString();
                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("正在查询...");
                progressDialog.show();
                SearchTask task = new SearchTask(getActivity(),progressDialog);
                task.execute(fromCity,toCity,startDate,startTime);
                break;
        }
    }


}
