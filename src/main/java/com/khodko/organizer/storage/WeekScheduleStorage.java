package com.khodko.organizer.storage;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khodko.organizer.model.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class WeekScheduleStorage {

    private final String SCHEDULE_DIR = "src/main/resources/storage/";
    private final String SCHEDULE_FILE = "week-schedule.json";

    private List<Pair> weekSchedule = new ArrayList<>();
    private ObjectMapper objectMapper;

    public WeekScheduleStorage() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        read();
    }

    public void read() {
        try {
            //File file = new File(WEEK_SCHEDULE_DIR2);
            weekSchedule.clear();

            File file = new File(SCHEDULE_DIR + SCHEDULE_FILE);
            if (file.exists()) {
                weekSchedule.addAll(objectMapper.readValue(file, new TypeReference<List<Pair>>() {
                }));
            } else {
                // создать пустой json файл типа List
                write();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write() {
        try {
            Path directory = Paths.get(SCHEDULE_DIR);
            if (!Files.isDirectory(directory)) {
                Files.createDirectory(directory);
            }
            File file = new File(SCHEDULE_DIR + SCHEDULE_FILE);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, weekSchedule);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Pair> getWeekSchedule() {
        return weekSchedule;
    }

    public List<Pair> getDaySchedule(int weekDay) {
        List<Pair> daySchedule = new ArrayList<>();
        for (Pair pair : weekSchedule) {
            if (pair.getWeekDay() == weekDay) {
                daySchedule.add(pair);
            }
        }
        return daySchedule;
    }

    public List<Pair> getWeekSchedule(String lesson) {
        if (lesson == null) return weekSchedule;

        List<Pair> lessonSchedule = new ArrayList<>();
        for (Pair pair : weekSchedule) {
            if (pair.getLesson().equals(lesson)) {
                lessonSchedule.add(pair);
            }
        }
        return lessonSchedule;
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

}
