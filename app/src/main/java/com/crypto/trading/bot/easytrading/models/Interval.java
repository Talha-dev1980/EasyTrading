package com.crypto.trading.bot.easytrading.models;

public enum Interval {

     Year("1y"),Hour("1h");

    private final String time;

    Interval(String time){
        this.time=time;
    }
    public String getTime(){
        return time;
    }
}
