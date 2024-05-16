package com.example.treeze.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public final class CalendarUtil {
    public static String getCurrentDate() {
        return formatDate(Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA), "yyyy-MM-dd");
    }

    public static String getAddDayDatetime(int add) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA);
        calendar.add(Calendar.DATE, add);
        return formatDate(calendar, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDate(Calendar calendar, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREA);
        return formatter.format(calendar.getTime());
    }
}
