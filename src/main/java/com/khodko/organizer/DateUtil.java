package com.khodko.organizer;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static String[] weekDays = {
            "понедельник",
            "вторник",
            "среда",
            "четверг",
            "пятница",
            "суббота",
            "воскресенье"
    };

    public static String getWeekDay(LocalDate date) {
        int dayOfWeek = date.getDayOfWeek().ordinal();
        return weekDays[dayOfWeek];
    }

    public static String getDateString(LocalDate date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dtf.format(date) + " " + getWeekDay(date);
    }
}
