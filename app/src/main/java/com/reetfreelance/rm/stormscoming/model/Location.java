package com.reetfreelance.rm.stormscoming.model;


import com.reetfreelance.rm.stormscoming.R;
import com.reetfreelance.rm.stormscoming.util.StandardDateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class Location {
    private double latitude;
    private double longitude;

    private String unit;

    public Location() {
        this.latitude = 22.5726;
        this.longitude = 88.3639;
        this.unit = "si";
    }

    public Location(String unit) {
        this.latitude = 22.5726;
        this.longitude = 88.3639;
        this.unit = unit;
    }

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.unit = "si";
    }

    public Location(double latitude, double longitude, String unit) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.unit = unit;
    }

    private double temperature;
    private String timezone;
    private long time;
    private double humidity;
    private double precipitation;
    private String summary;
    private String icon;

    private WeatherDay weatherDays[];
    private WeatherHour weatherHours[];

    public WeatherDay[] getWeatherDays() {
        return weatherDays;
    }

    public void setWeatherDays(WeatherDay[] weatherDays) {
        this.weatherDays = weatherDays;
    }

    public ArrayList<WeatherDay> getDailyWeatherList(){
        ArrayList<WeatherDay> dailyWeatherList=new ArrayList<WeatherDay>();

        if(weatherDays!=null &&
                weatherDays.length>0){
            dailyWeatherList=(ArrayList<WeatherDay>) Arrays.asList(weatherDays);
        }
        return dailyWeatherList;
    }

    public WeatherHour[] getWeatherHours() {
        return weatherHours;
    }

    public void setWeatherHours(WeatherHour[] weatherHours) {
        this.weatherHours = weatherHours;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDateAsString(){
        Date date=new Date(time*1000);
        StandardDateUtil.setTimezone(timezone);
        return StandardDateUtil.getDateAsString(date);
    }

    public String getAPIUrl(String apiKey){
        StringBuffer urlBuffer=new StringBuffer("https://api.darksky.net/forecast/");

        urlBuffer.append(apiKey)
                .append("/")
                .append(latitude)
                .append(",")
                .append(longitude);

        if(unit!=null
                && !unit.trim().isEmpty()){
            urlBuffer.append("?units=")
                    .append(unit);
        }

        return urlBuffer.toString();
    }

    public int getIconId(){
        int iconId=R.drawable.ic_clear_day;

        if (icon.equals("clear-day")) {
            return (R.drawable.ic_clear_day);
        }
        else if (icon.equals("clear-night")) {
            return (R.drawable.ic_clear_night);
        }
        else if (icon.equals("rain")) {
            return (R.drawable.ic_rain);
        }
        else if (icon.equals("snow")) {
            return (R.drawable.ic_snow);
        }
        else if (icon.equals("sleet")
                || icon.equals("hail")) {
            return (R.drawable.ic_hail);
        }
        else if (icon.equals("wind")) {
            return (R.drawable.ic_wind);
        }
        else if (icon.equals("fog")) {
            return (R.drawable.ic_fog);
        }
        else if (icon.equals("cloudy")) {
            return (R.drawable.ic_cloudy);
        }
        else if (icon.equals("partly-cloudy-day")) {
            return (R.drawable.ic_partly_cloudy);
        }
        else if (icon.equals("partly-cloudy-night")) {
            return (R.drawable.ic_cloudy_night);
        }
        //Optional - for future purposes
        else if (icon.equals("thunderstorm")) {
            return (R.drawable.ic_storm);
        }
        else if (icon.equals("tornado")) {
            return (R.drawable.ic_tornado);
        }

        return iconId;
    }
}
