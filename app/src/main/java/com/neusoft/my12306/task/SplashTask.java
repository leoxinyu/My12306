package com.neusoft.my12306.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.neusoft.my12306.SearchActivity;
import com.neusoft.my12306.SplashActivity;

/**
 * Created by Administrator on 2016/9/1.
 */
public class SplashTask extends AsyncTask<Void,Void,Void>{
    private Context context;
    public SplashTask(Context context){
        this.context = context;
    }
    @Override
    protected Void doInBackground(Void... params) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Intent it = new Intent(context, SearchActivity.class);
        context.startActivity(it);
        ((SplashActivity)context).finish();
    }
}
