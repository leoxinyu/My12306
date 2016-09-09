package com.neusoft.my12306;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText userTxt,pswTxt;
    private String username,password;
    private Button loginBtn,regBtn;
    private ProgressDialog pd;
    private Handler handler;
    private String receiveStr;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sp = getSharedPreferences("myfile", Context.MODE_PRIVATE);
        findView();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                pd.dismiss();
                if(!receiveStr.equals("fail")){
                    //登录成功，保存用户名和密码
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("username",username);
                    editor.putString("realname",receiveStr);
                    editor.putString("password",password);
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this,SearchActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(LoginActivity.this,"用户名或者密码错误",Toast.LENGTH_LONG).show();
                }
            }
        };
    }
    private void findView(){
        userTxt = (EditText)findViewById(R.id.usertxt_id);
        pswTxt = (EditText)findViewById(R.id.pswtxt_id);
        loginBtn = (Button)findViewById(R.id.loginbtn_id);
        regBtn = (Button)findViewById(R.id.regbtn_id);
        loginBtn.setOnClickListener(this);
        regBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginbtn_id:
                username = userTxt.getEditableText().toString();
                password = pswTxt.getEditableText().toString();
                if(username.equals("")){
                    userTxt.setError("用户名不能为空");
                }
                if(password.equals("")){
                    pswTxt.setError("密码不能为空");
                }else {
                    pd = new ProgressDialog(this);
                    pd.setMessage("正在登录中...");
                    pd.show();
                    LoginThread loginThread = new LoginThread();
                    new Thread(loginThread).start();
                }
                break;
            case R.id.regbtn_id:
                Intent it = new Intent(this,RegActivity.class);
                startActivity(it);
                break;
        }
    }

    //完成登录的内部类（线程）
    private class LoginThread implements Runnable{

        @Override
        public void run() {
            HttpClient client = new DefaultHttpClient();
            String url = MyServer.url+"LoginServlet";
            HttpPost post = new HttpPost(url);
            List<BasicNameValuePair> ls = new ArrayList<BasicNameValuePair>();
            ls.add(new BasicNameValuePair("user",username));
            ls.add(new BasicNameValuePair("psw",password));
            try {
                UrlEncodedFormEntity urlEntity = new UrlEncodedFormEntity(ls,"utf-8");
                post.setEntity(urlEntity);
                HttpResponse response = client.execute(post);
                if(response.getStatusLine().getStatusCode()==200){//状态码等于200，代表成功执行了HTTP的请求
                    //通过HttpResponse得到服务器端发来的数据
                    InputStream is = response.getEntity().getContent();
                    //DataInputStream dis = new DataInputStream(is);
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    StringBuilder sb = new StringBuilder();
                    String str=null;
                    while ( (str = br.readLine())!=null ){
                        sb.append(str);
                    }
                    receiveStr = sb.toString();
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);

                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
