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

    private List<List<Pair>> weekSchedule = new ArrayList<>();
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
                weekSchedule.addAll(objectMapper.readValue(file, new TypeReference<List<List<Pair>>>() {
                }));
            } else {
                //создать пустой List<List<Pair>>
                for (int i = 0; i < 7; i++) {
                    weekSchedule.add(new ArrayList<>());
                }
                // создать пустой json файл типа List<List<Pair>>
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

    public List<List<Pair>> getWeekSchedule() {
        return weekSchedule;
    }

    public List<List<Pair>> getWeekSchedule(String lesson) {
        if (lesson == null) return weekSchedule;

        List<List<Pair>> lessonSchedule = new ArrayList<>();
        for (List<Pair> pairs : weekSchedule) {
            List<Pair> lessonPairs = new ArrayList<>();
            for (Pair pair : pairs) {
                if (pair.getLesson().equals(lesson)) {
                    lessonPairs.add(pair);
                }
            }
            lessonSchedule.add(lessonPairs);
        }
        return lessonSchedule;
    }

    public List<String> getScheduleLessons() {
        List<String> lessons = new ArrayList<>();
        for (List<Pair> pairs : weekSchedule) {
            for (Pair pair : pairs) {
                if (!lessons.contains(pair.getLesson())) {
                    lessons.add(pair.getLesson());
                }
            }
        }
        return lessons;
    }

}
