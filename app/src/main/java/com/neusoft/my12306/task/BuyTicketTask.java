package com.neusoft.my12306.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.neusoft.my12306.SearchActivity;
import com.neusoft.my12306.SearchTrainsActivity;
import com.neusoft.my12306.util.MyServer;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2016/8/29.
 */
public class BuyTicketTask extends AsyncTask<String,Void,String>{
    private Context context;
    private String content;
    private String receiveStr;
    public BuyTicketTask(Context context){
        this.context = context;
    }
    @Override
    protected String doInBackground(String... params) {
        String s = MyServer.url+"BuyTicketServlet";
        //HttpClient client = new DefaultHttpClient();
        try {
            content = "username="+ URLEncoder.encode(params[0],"utf-8")+
                             "&trainid="+ URLEncoder.encode(params[1],"utf-8")+
                             "&trainnum="+ URLEncoder.encode(params[2],"utf-8")+
                             "&fromcity="+ URLEncoder.encode(params[3],"utf-8")+
                             "&tocity="+URLEncoder.encode(params[4],"utf-8")+
                             "&startdate="+URLEncoder.encode(params[5],"utf-8")+
                             "&starttime="+URLEncoder.encode(params[6],"utf-8")+"";
            URL url = new URL(s);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setConnectTimeout(8000);
//            conn.setReadTimeout(8000);

            OutputStream os = conn.getOutputStream();
//            os.write(content.getBytes());
//            os.flush();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return receiveStr;
    }

    @Override
    protected void onPostExecute(String s) {
        if(s.equals("success")){
            Toast.makeText(context,"购票成功",Toast.LENGTH_LONG).show();
            //Intent it = new Intent(context, SearchActivity.class);
            //context.startActivity(it);
        } else {
            Toast.makeText(context,"购票不成功",Toast.LENGTH_LONG).show();
        }
    }
}
