package com.example.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public final class CalendarUtils {

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy, MM/dd", Locale.US);

    public static Calendar getZeroTimeCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.SECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        calendar.add(Calendar.HOUR_OF_DAY, 0);
        calendar.add(Calendar.HOUR, 0);

        return calendar;
    }
}
