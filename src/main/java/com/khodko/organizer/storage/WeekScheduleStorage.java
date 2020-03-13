package com.khodko.organizer.storage;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khodko.organizer.model.Pair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class WeekScheduleStorage {

    private final String WEEK_SCHEDULE_DIR = "src/main/resources/storage/week-schedule.json";

    private List<Pair> weekSchedule = new ArrayList<>();
    private ObjectMapper objectMapper;

    public WeekScheduleStorage() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    public void read() {
        try {
            File file = new File(WEEK_SCHEDULE_DIR);
            if (file.exists()) {
                weekSchedule = objectMapper.readValue(file, new TypeReference<List<Pair>>() {
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(WEEK_SCHEDULE_DIR), weekSchedule);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Pair> getWeekSchedule() {
        return weekSchedule;
    }

    public Pair getPair(Integer weekDay, Integer num) {
        for (Pair pair : weekSchedule) {
            if (pair.getWeekDay().equals(weekDay) && pair.getNum().equals(num)) {
                return pair;
            }
        }
        return null;
    }

    public List<Pair> getDayPairs(Integer weekDay) {
        List<Pair> dayPairs = new ArrayList<>();
        for (Pair pair : weekSchedule) {
            if (pair.getWeekDay().equals(weekDay)) {
                dayPairs.add(pair);
            }
        }
        return dayPairs;
    }

    public List<String> getScheduleLessons() {
        List<String> lessons = new ArrayList<>();
        for (Pair pair : weekSchedule) {
            if (!lessons.contains(pair.getLesson())) {
                lessons.add(pair.getLesson());
            }
        }
        return lessons;
    }

    public List<Pair> getScheduleByLesson(String lesson) {
        List<Pair> pairs = new ArrayList<>();
        for (Pair pair : weekSchedule) {
            if (pair.getLesson().equals(lesson)) {
                pairs.add(pair);
            }
        }
        return pairs;
    }

}
