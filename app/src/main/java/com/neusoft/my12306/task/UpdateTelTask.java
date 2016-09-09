package com.neusoft.my12306.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.neusoft.my12306.SearchActivity;
import com.neusoft.my12306.adapter.MyFragmentAdapter;
import com.neusoft.my12306.fragment.MyFragment;
import com.neusoft.my12306.util.MyServer;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/18.
 */
public class UpdateTelTask extends AsyncTask<String,Void,String> {
    private Context context;
    private Fragment fragment;
    private ProgressDialog pd;
    public UpdateTelTask(Context context, Fragment fragment, ProgressDialog pd){
        this.context = context;
        this.fragment = fragment;
        this.pd = pd;
    }

    @Override
    protected String doInBackground(String... params) {
        String username = params[0];
        String tel = params[1];
        HttpClient client = new DefaultHttpClient();
        String url = MyServer.url+"UpdateTelServlet";
        HttpPost post = new HttpPost(url);
        List<BasicNameValuePair> ls = new ArrayList<BasicNameValuePair>();
        ls.add(new BasicNameValuePair("username",username));
        ls.add(new BasicNameValuePair("tel",tel));

        StringBuilder sb = new StringBuilder();
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(ls,"utf-8");
            post.setEntity(urlEncodedFormEntity);
            HttpResponse response = client.execute(post);
            if(response.getStatusLine().getStatusCode()==200){
                InputStream is = response.getEntity().getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String str = null;
                while ((str=br.readLine())!=null){
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
        pd.dismiss();
        if(s.equals("success")){
            Toast.makeText(context,"修改成功",Toast.LENGTH_LONG).show();
            SearchActivity activity = (SearchActivity)fragment.getActivity();
            MyFragmentAdapter adapter = activity.getAdapter();
            adapter.replace(fragment,new MyFragment());
        }else{
            Toast.makeText(context,"修改不成功",Toast.LENGTH_LONG).show();
        }
    }
}
