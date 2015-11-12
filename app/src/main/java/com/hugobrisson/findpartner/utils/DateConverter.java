package com.hugobrisson.findpartner.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hugo on 11/11/2015.
 */
public class DateConverter {

    public static Date concatDate(String date, String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(date + " " + time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;
    }

    public static Date formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(allDateToString(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    public static String allDateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        return dateFormat.format(date);
    }

    public static String dateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    public static String timeToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        return dateFormat.format(date);
    }

    public static String formatDate(int day, int month, int year) {
        String months;
        String days;
        month++;
        months = String.valueOf(month);
        days = String.valueOf(day);
        if (months.length() == 1) {
            months = "0" + month;
        }
        if (days.length() == 1) {
            days = "0" + day;
        }
        return String.valueOf(days) + "/" + (months) + "/" + year;
    }

    public static String formatTime(int hour, int minute) {
        String hours;
        String minutes;
        hours = String.valueOf(hour);
        minutes = String.valueOf(minute);
        if (hours.length() == 1) {
            hours = "0" + hours;
        }
        if (minutes.length() == 1) {
            minutes = "0" + minutes;
        }
        return String.valueOf(hours) + ":" + (minutes);
    }

}
