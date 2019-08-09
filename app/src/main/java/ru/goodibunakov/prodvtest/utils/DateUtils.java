package ru.goodibunakov.prodvtest.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static final String SERVER_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String UI_DATE_FORMAT = "dd MMM";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static String convertDateForUI(String dateIn){
        SimpleDateFormat inputFormat = new SimpleDateFormat(SERVER_DATE_FORMAT, Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat(UI_DATE_FORMAT, Locale.getDefault());
        Date date = null;
        try {
            date = inputFormat.parse(dateIn);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null){
            return outputFormat.format(date);
        } else {
            return "";
        }

    }

    public static String convertDate(String dateIn){
        SimpleDateFormat inputFormat = new SimpleDateFormat(SERVER_DATE_FORMAT, Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        Date date = null;
        try {
            date = inputFormat.parse(dateIn);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null){
            return outputFormat.format(date);
        } else {
            return "";
        }

    }
}
