package com.khodko.organizer.utils;


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

    public static String getDateString(LocalDate date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int dayOfWeek = date.getDayOfWeek().ordinal();
        return dtf.format(date) + " " + weekDays[dayOfWeek];
    }
}
