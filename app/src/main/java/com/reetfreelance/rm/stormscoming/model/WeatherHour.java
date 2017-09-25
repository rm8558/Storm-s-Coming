package com.reetfreelance.rm.stormscoming.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.reetfreelance.rm.stormscoming.util.IconHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherHour implements Parcelable{
    private long time;
    private String timezone;
    private String icon;
    private double temperature;
    private String summary;

    public WeatherHour(){}

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
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

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getHour(){
        SimpleDateFormat sdf=new SimpleDateFormat("h a");
        Date date=new Date(time*1000);
        return sdf.format(date).toUpperCase();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(time);
        dest.writeString(timezone);
        dest.writeString(icon);
        dest.writeDouble(temperature);
        dest.writeString(summary);
    }

    public static final Creator<WeatherHour> CREATOR=new Creator<WeatherHour>() {
        @Override
        public WeatherHour createFromParcel(Parcel source) {
            return new WeatherHour(source);
        }

        @Override
        public WeatherHour[] newArray(int size) {
            return new WeatherHour[size];
        }
    };

    private WeatherHour(Parcel in){
        time=in.readLong();
        timezone=in.readString();
        icon=in.readString();
        temperature=in.readDouble();
        summary=in.readString();
    }
}
