package com.neusoft.my12306.task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.neusoft.my12306.adapter.UnfinishOrderListViewAdapter;
import com.neusoft.my12306.bean.Station;
import com.neusoft.my12306.bean.Ticket;
import com.neusoft.my12306.bean.Train;
import com.neusoft.my12306.util.MyServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/1.
 */
public class SearchUnfinishOrderTask extends AsyncTask<String,Void,String>{
    private Context context;
    private ListView listView;
    private String receiveStr;

    public SearchUnfinishOrderTask(Context context,ListView listView){
        this.context = context;
        this.listView = listView;
    }

    @Override
    protected String doInBackground(String... params) {
        String httpUrl = MyServer.url+"SearchUnfinishOrderServlet";
        String username = params[0];
        String content = "username="+username;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(httpUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write(content);
            pw.flush();
            pw.close();
            if(conn.getResponseCode()==200){
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String str = null;
                while ( (str=br.readLine())!=null ){
                    sb.append(str);
                }
                receiveStr = sb.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return receiveStr;
    }

    @Override
    protected void onPostExecute(String result) {
        List<Ticket> ticketList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jo = jsonArray.getJSONObject(i);
                int id = jo.getInt("id");
                String realname = jo.getString("realname");
                String train_num = jo.getString("train_num");
                String from_city = jo.getString("from_city");
                String to_city = jo.getString("to_city");
                String from_date = jo.getString("from_date");
                String seat = jo.getString("seat");
                String price = jo.getString("price");
                String order_datetime = jo.getString("order_datetime");
                Ticket ticket = new Ticket();
                ticket.setId(id);
                ticket.setRealname(realname);
                ticket.setTrain_num(train_num);
                ticket.setFrom_city(from_city);
                ticket.setTo_city(to_city);
                ticket.setFrom_date(from_date);
                ticket.setSeat(seat);
                ticket.setPrice(price);
                ticket.setOrder_datetime(order_datetime);
                ticketList.add(ticket);
            }
            //Toast.makeText(context,jsonArray.toString(),Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        UnfinishOrderListViewAdapter adapter = new UnfinishOrderListViewAdapter(context,ticketList);
        listView.setAdapter(adapter);
    }
}
