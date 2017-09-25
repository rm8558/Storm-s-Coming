package com.reetfreelance.rm.stormscoming.util;


import com.reetfreelance.rm.stormscoming.R;

public class IconHandler {
    public static int getSmallIconId(String icon){
        int iconId= R.drawable.ic_clear_day_small;

        if (icon.equals("clear-day")) {
            return (R.drawable.ic_clear_day_small);
        }
        else if (icon.equals("clear-night")) {
            return (R.drawable.ic_clear_night_small);
        }
        else if (icon.equals("rain")) {
            return (R.drawable.ic_rain_small);
        }
        else if (icon.equals("snow")) {
            return (R.drawable.ic_snow_small);
        }
        else if (icon.equals("sleet")
                || icon.equals("hail")) {
            return (R.drawable.ic_hail_small);
        }
        else if (icon.equals("wind")) {
            return (R.drawable.ic_wind_small);
        }
        else if (icon.equals("fog")) {
            return (R.drawable.ic_fog_small);
        }
        else if (icon.equals("cloudy")) {
            return (R.drawable.ic_cloudy_small);
        }
        else if (icon.equals("partly-cloudy-day")) {
            return (R.drawable.ic_partly_cloudy_small);
        }
        else if (icon.equals("partly-cloudy-night")) {
            return (R.drawable.ic_cloudy_night_small);
        }
        //Optional - for future purposes
        else if (icon.equals("thunderstorm")) {
            return (R.drawable.ic_storm_small);
        }
        else if (icon.equals("tornado")) {
            return (R.drawable.ic_tornado_small);
        }

        return iconId;
    }
}
