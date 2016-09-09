package com.neusoft.my12306;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.neusoft.my12306.task.SplashTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SplashTask task = new SplashTask(this);
        task.execute();
    }
}
