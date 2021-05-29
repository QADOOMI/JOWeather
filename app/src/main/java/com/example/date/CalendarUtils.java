package com.example.date;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public final class CalendarUtils {

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy, MM/dd", Locale.US);

    public static Calendar getZeroTimeCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.HOUR, 0);

        return calendar;
    }
}
