package com.reetfreelance.rm.stormscoming.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.reetfreelance.rm.stormscoming.util.IconHandler;
import com.reetfreelance.rm.stormscoming.util.StandardDateUtil;

import java.util.Date;

public class WeatherDay implements Parcelable{
    private long time;
    private double temperature;
    private String icon;

    public WeatherDay() {
    }

    public WeatherDay(long time, double temperature, String icon) {
        this.time = time;
        this.temperature = temperature;
        this.icon = icon;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDay() {
        Date date=new Date(time*1000);
        return StandardDateUtil.getDayOfWeek(date);
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getIconId(){
        return IconHandler.getSmallIconId(icon);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(time);
        dest.writeDouble(temperature);
        dest.writeString(icon);
    }

    private WeatherDay(Parcel in){
        time=in.readLong();
        temperature=in.readDouble();
        icon=in.readString();
    }

    public static final Creator<WeatherDay> CREATOR=new Creator<WeatherDay>() {
        @Override
        public WeatherDay createFromParcel(Parcel source) {
            return new WeatherDay(source);
        }

        @Override
        public WeatherDay[] newArray(int size) {
            return new WeatherDay[size];
        }
    };
}
