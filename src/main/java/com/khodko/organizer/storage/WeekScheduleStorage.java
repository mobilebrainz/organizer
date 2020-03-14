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

    private List<List<Pair>> weekSchedule = new ArrayList<>();
    private ObjectMapper objectMapper;

    public WeekScheduleStorage() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        read();
    }

    public void read() {
        try {
            File file = new File(WEEK_SCHEDULE_DIR);
            if (file.exists()) {
                weekSchedule.clear();
                weekSchedule.addAll(objectMapper.readValue(file, new TypeReference<List<List<Pair>>>() {
                }));
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

    public List<List<Pair>> getWeekSchedule() {
        return weekSchedule;
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
