package com.neusoft.my12306.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/26.
 */
public class Station implements Serializable{
    private int pass_num;
    private String station_name;

    public int getPass_num() {
        return pass_num;
    }

    public void setPass_num(int pass_num) {
        this.pass_num = pass_num;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }
}
