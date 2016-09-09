package com.neusoft.my12306.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/25.
 */
public class Train implements Serializable{
    private int id;
    private String num;
    private String start_date;
    private String start_time;
    private String from_city;
    private String to_city;
    private String myfrom_city;
    private String myto_city;
    private List<Station> stationList;

    public List<Station> getStationList() {
        return stationList;
    }

    public void setStationList(List<Station> stationList) {
        this.stationList = stationList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getFrom_city() {
        return from_city;
    }

    public void setFrom_city(String from_city) {
        this.from_city = from_city;
    }

    public String getTo_city() {
        return to_city;
    }

    public void setTo_city(String to_city) {
        this.to_city = to_city;
    }

    public String getMyfrom_city() {
        return myfrom_city;
    }

    public void setMyfrom_city(String myfrom_city) {
        this.myfrom_city = myfrom_city;
    }

    public String getMyto_city() {
        return myto_city;
    }

    public void setMyto_city(String myto_city) {
        this.myto_city = myto_city;
    }
}
