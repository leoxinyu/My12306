package com.neusoft.my12306.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.Voice;
import android.util.Log;
import android.widget.Toast;

import com.neusoft.my12306.SearchTrainsActivity;
import com.neusoft.my12306.bean.Station;
import com.neusoft.my12306.bean.Train;
import com.neusoft.my12306.util.MyServer;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 */
public class SearchTask extends AsyncTask<String,Void,String>{
    private Context context;
    private ProgressDialog progressDialog;
    public SearchTask(Context context,ProgressDialog progressDialog){
        this.context = context;
        this.progressDialog = progressDialog;
    }
    @Override
    protected String doInBackground(String... params) {
        String fromCity = params[0];
        String toCity = params[1];
        String date = params[2];
        String time = params[3];

        String url = MyServer.url+"SearchServlet";
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        List<BasicNameValuePair> ls = new ArrayList<BasicNameValuePair>();
        ls.add(new BasicNameValuePair("fromCity",fromCity));
        ls.add(new BasicNameValuePair("toCity",toCity));
        ls.add(new BasicNameValuePair("date",date));
        ls.add(new BasicNameValuePair("time",time));
        StringBuilder sb = new StringBuilder();
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(ls,"utf-8");
            post.setEntity(urlEncodedFormEntity);
            HttpResponse response = client.execute(post);
            if(response.getStatusLine().getStatusCode()==200){
                InputStream is = response.getEntity().getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String str = null;
                while ( (str=br.readLine())!=null ){
                    sb.append(str);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        //Toast.makeText(context,s,Toast.LENGTH_LONG).show();
        List<Train> trainList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(s);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jo = jsonArray.getJSONObject(i);
                int id = Integer.parseInt(jo.getString("id"));
                String num = jo.getString("num");
                String start_date = jo.getString("start_date");
                String start_time = jo.getString("start_time");
                String from_city = jo.getString("from_city");
                String to_city = jo.getString("to_city");
                String myfrom_city = jo.getString("myfrom_city");
                String myto_city = jo.getString("myto_city");
                Train train = new Train();
                train.setId(id);
                train.setNum(num);
                train.setStart_date(start_date);
                train.setStart_time(start_time);
                train.setFrom_city(from_city);
                train.setTo_city(to_city);
                train.setMyfrom_city(myfrom_city);
                train.setMyto_city(myto_city);
                List<Station> stationList = new ArrayList<>();
                JSONArray jsonArray2 =new JSONArray(jo.getString("station"));
                for(int j=0;j<jsonArray2.length();j++){
                    JSONObject jo2 = jsonArray2.getJSONObject(j);
                    int pass_num = jo2.getInt("pass_num");
                    String station_name = jo2.getString("station_name");
                    Station station = new Station();
                    station.setPass_num(pass_num);
                    station.setStation_name(station_name);
                    stationList.add(station);
                }

                train.setStationList(stationList);
                trainList.add(train);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent it = new Intent(context, SearchTrainsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("trainList",(Serializable)trainList);
        it.putExtras(bundle);
        context.startActivity(it);
    }
}
