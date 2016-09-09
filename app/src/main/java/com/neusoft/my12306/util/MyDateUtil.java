package com.neusoft.my12306.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/23.
 */
public class MyDateUtil {
    public static int getYear(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(date);
        return Integer.parseInt(year);
    }
    public static int getMonth(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String month = sdf.format(date);
        return Integer.parseInt(month)-1;
    }
    public static int getDay(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String day = sdf.format(date);
        return Integer.parseInt(day);
    }
}
