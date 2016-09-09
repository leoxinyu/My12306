package com.neusoft.my12306.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.neusoft.my12306.R;
import com.neusoft.my12306.task.UpdateMyDataTask;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyInfoUpdateFragment extends MyFragment implements View.OnClickListener{
    private SharedPreferences sp;
    private String username;
    private String mydataFromServer;
    private EditText idcardTxt,telTxt,emailTxt;
    private Button saveBtn;

    public MyInfoUpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sp = getActivity().getSharedPreferences("myfile", Context.MODE_PRIVATE);
        username = sp.getString("username","");
        InitUIDataTask task = new InitUIDataTask();
        task.execute(username);
        View view = inflater.inflate(R.layout.fragment_my_info_update, container, false);
        idcardTxt = (EditText) view.findViewById(R.id.update_idcard_txt_id);
        telTxt = (EditText) view.findViewById(R.id.update_tel_txt_id);
        emailTxt = (EditText) view.findViewById(R.id.update_email_txt_id);
        saveBtn = (Button) view.findViewById(R.id.update_mydata_btn_id);
        saveBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        UpdateMyDataTask task = new UpdateMyDataTask(getActivity(),this);
        task.execute(username,idcardTxt.getEditableText().toString(),telTxt.getEditableText().toString(),emailTxt.getEditableText().toString());
    }


    //初始化界面数据的异步任务
    private class InitUIDataTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {//这方法不能用Toast
            StringBuilder sb = new StringBuilder();

            String url = MyServer.url+"InitMyDataServlet";
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            List<BasicNameValuePair> ls = new ArrayList<BasicNameValuePair>();
            ls.add(new BasicNameValuePair("username",params[0]));
            try {
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(ls,"utf-8");
                post.setEntity(urlEncodedFormEntity);
                HttpResponse response = client.execute(post);
                if(response.getStatusLine().getStatusCode()==200){
                     InputStream is = response.getEntity().getContent();
                     BufferedReader br = new BufferedReader(new InputStreamReader(is));
                     String str=null;
                     while ( (str = br.readLine())!=null ){
                         sb.append(str);
                     }
                    Log.i("mytag",sb.toString());

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
            mydataFromServer = s;
            try {
                JSONArray jsonArray = new JSONArray(s);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jo = jsonArray.getJSONObject(i);
                    String email = jo.getString("email");
                    String tel = jo.getString("tel");
                    String idcard = jo.getString("idcard");
                    idcardTxt.setText(idcard);
                    telTxt.setText(tel);
                    emailTxt.setText(email);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
