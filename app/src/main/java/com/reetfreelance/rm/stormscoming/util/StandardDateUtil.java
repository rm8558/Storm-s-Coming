package com.reetfreelance.rm.stormscoming.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by reetmondal on 20/09/17.
 */

public class StandardDateUtil {
    private static final SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
    private static String days[]={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    public static String getDateAsString(Date date){
        String dateStr=sdf.format(date);
        return dateStr;
    }

    public static void setTimezone(String timezone){
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
    }

    public static String getDayOfWeek(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        String dayOfWeek=days[cal.get(Calendar.DAY_OF_WEEK)-1];
        return dayOfWeek;
    }
}
