package com.neusoft.my12306;

import android.app.ProgressDialog;
import android.content.Intent;
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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class RegActivity extends AppCompatActivity implements View.OnClickListener{
    private ProgressDialog pd;
    private EditText userTxt,realnameTxt,pswTxt,idCardTxt,emailTxt,telTxt;
    private String username,realname,psw,idcard,email,tel;
    private Button saveRegBtn;
    private Handler handler;
    private String receiveStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findView();
    }
    private void findView(){
        userTxt = (EditText)findViewById(R.id.reg_user_id);
        realnameTxt = (EditText)findViewById(R.id.reg_realname_id);
        pswTxt = (EditText)findViewById(R.id.reg_psw_id);
        idCardTxt = (EditText)findViewById(R.id.reg_idcard_id);
        emailTxt = (EditText)findViewById(R.id.reg_email_id);
        telTxt = (EditText)findViewById(R.id.reg_tel_id);
        saveRegBtn = (Button)findViewById(R.id.save_reg_btn_id);
        saveRegBtn.setOnClickListener(this);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                pd.dismiss();
                if(receiveStr.equals("success")){
                    Toast.makeText(RegActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                    Intent it = new Intent(RegActivity.this,LoginActivity.class);
                    startActivity(it);
                }else{
                    Toast.makeText(RegActivity.this,"注册不成功",Toast.LENGTH_LONG).show();
                }

            }
        };
    }
    @Override
    public void onClick(View v) {
        username = userTxt.getEditableText().toString();
        realname = realnameTxt.getEditableText().toString();
        psw = pswTxt.getEditableText().toString();
        idcard = idCardTxt.getEditableText().toString();
        email = emailTxt.getEditableText().toString();
        tel = telTxt.getEditableText().toString();
        if(username.equals("")){
            userTxt.setError("用户名不能为空");
        }
        if(realname.equals("")){
            userTxt.setError("真实姓名不能为空");
        }
        if(psw.equals("")){
            pswTxt.setError("密码不能为空");
        }
        if(idcard.equals("")){
            idCardTxt.setError("身份证不能为空");
        }
        if(email.equals("")){
            emailTxt.setError("邮箱不能为空");
        }
        if(tel.equals("")){
            telTxt.setError("电话不能为空");
        }else{
            pd = new ProgressDialog(this);
            pd.setMessage("正在注册中…");
            pd.setCancelable(false);
            pd.show();
            //启动线程
            MyThread mt = new MyThread();
            new Thread(mt).start();
        }

    }

    //内部类：子线程
    private class MyThread implements Runnable{

        @Override
        public void run() {
            HttpClient client = new DefaultHttpClient();
            String url = MyServer.url+"RegServlet";
            HttpPost post = new HttpPost(url);

            List<BasicNameValuePair> ls = new ArrayList<BasicNameValuePair>();
            ls.add(new BasicNameValuePair("user",username));
            ls.add(new BasicNameValuePair("realname",realname));
            ls.add(new BasicNameValuePair("psw",psw));
            ls.add(new BasicNameValuePair("idcard",idcard));
            ls.add(new BasicNameValuePair("email",email));
            ls.add(new BasicNameValuePair("tel",tel));
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
                    //千万不要在子线程中写更新主线程（界面）的数据）————比如：：Toast.makeText(RegActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                    //给主线程发消息
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
