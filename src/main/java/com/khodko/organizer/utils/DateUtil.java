package com.khodko.organizer.utils;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static String[] weekDays = {
            "Понедельник",
            "Вторник",
            "Среда",
            "Четверг",
            "Пятница",
            "Суббота",
            "Воскресенье"
    };

    public static String getDateString(LocalDate date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int dayOfWeek = date.getDayOfWeek().ordinal();
        return dtf.format(date) + " " + weekDays[dayOfWeek];
    }
}
