package com.neusoft.my12306;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.neusoft.my12306.bean.Train;
import com.neusoft.my12306.task.BuyTicketTask;

public class BuyActivity extends AppCompatActivity implements View.OnClickListener{
    private SharedPreferences sp;
    private Train train;
    private TextView myfromCityTxt,mytoCityTxt,trainNumtxt,fromCityTxt,toCityTxt;
    private ImageView startImageView,endImageView;
    private Button subBtn;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sp = getSharedPreferences("myfile", Context.MODE_PRIVATE);
        username = sp.getString("username","");
        findView();

    }

    private void findView(){
        Bundle bundle = getIntent().getExtras();
        train = (Train) bundle.getSerializable("train");
        myfromCityTxt = (TextView) findViewById(R.id.myfrom_id);
        fromCityTxt = (TextView) findViewById(R.id.from_id);
        trainNumtxt = (TextView) findViewById(R.id.train_num_id);
        mytoCityTxt = (TextView) findViewById(R.id.myto_id);
        toCityTxt = (TextView) findViewById(R.id.to_id);
        subBtn = (Button) findViewById(R.id.submit_order_btn_id);
        subBtn.setOnClickListener(this);

        startImageView = (ImageView)findViewById(R.id.start_img_id);
        endImageView = (ImageView)findViewById(R.id.end_img_id);

        myfromCityTxt.setText(train.getMyfrom_city());
        fromCityTxt.setText(train.getFrom_city());
        trainNumtxt.setText(train.getNum());
        mytoCityTxt.setText(train.getMyto_city());
        toCityTxt.setText(train.getTo_city());

        if(!train.getFrom_city().equals(train.getMyfrom_city())){
            startImageView.setImageResource(R.drawable.guo);
        }
        if(!train.getTo_city().equals(train.getMyto_city())){
            endImageView.setImageResource(R.drawable.guo);
        }
    }

    @Override
    public void onClick(View v) {
         if(username.equals("")){
             Intent it = new Intent(this,LoginActivity.class);
             startActivity(it);
         }else{
             BuyTicketTask task = new BuyTicketTask(this);
             task.execute(username,String.valueOf(train.getId()),train.getNum(),train.getMyfrom_city(),train.getMyto_city(),train.getStart_date(),train.getStart_time());
         }
    }
}
