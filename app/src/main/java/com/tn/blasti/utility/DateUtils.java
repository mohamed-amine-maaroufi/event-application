package com.tn.blasti.utility;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by amine 15/12/2018.
 */
public class DateUtils {

    public enum DateTimeFormat {
        // Date formats
        DD_MM_YYYY_DASH("dd-MM-yyyy"),
        YYMMDD_HHMMSS("yyyyMMddHHmmss"),
        DD_MONTH("dd LLL"),
        DAY_TIME("EEE kk:mm");

        private final String name;

        DateTimeFormat(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static String getCurrentDateinLong() {
        Calendar cal = Calendar.getInstance();
        return String.valueOf(cal.getTimeInMillis());
    }

    public static String getDateFromLong(String longDate, DateTimeFormat format) {
        String longV = longDate;
        long millisecond = Long.parseLong(longV);
        String dateString = DateFormat.format(format.toString(), new Date(millisecond)).toString();
        return dateString;
    }

    public static long getTimeDifference(long strOne, long strTwo, TimeUnit ts) {


        long difference = strOne >= strTwo ? (strOne - strTwo) : (strTwo - strOne);

        if (ts == TimeUnit.SECONDS) {
            difference = difference / 1000;
        } else if (ts == TimeUnit.MINUTES) {
            difference = (difference / 1000) / 60;
        } else if (ts == TimeUnit.HOURS) {
            difference = (difference / 1000) / 3600;
        }

        return difference;
    }

    public static long getTimeDifference(String strOne, String strTwo, TimeUnit ts) {
        return getTimeDifference(Long.valueOf(strOne), Long.valueOf(strTwo), ts);
    }

    public static String getFormatedTime(String longDate) {

        String timeString = "";

        long difference = Long.parseLong(getCurrentDateinLong()) - Long.parseLong(longDate);

        // If less than 60 return now
        if (difference / 1000 <= 60) {
            timeString = "Now";
        }
        // If more than 120 and 3600 then 2,3,4 min ago
        else if (difference / 1000 > 60 && difference / 1000 <= 3600) {
            long timeD = getTimeDifference(longDate, getCurrentDateinLong(), TimeUnit.MINUTES);
            timeString = timeD <= 1 ? timeD + " min" : timeD + " mins";
        }
        // Otherwise date and time
        else {
            timeString = getDateFromLong(longDate, DateTimeFormat.DAY_TIME);
        }

        return timeString;

    }

    public static String getDateTimeString(DateTimeFormat format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format.toString());
        return sdf.format(new Date());
    }
}
