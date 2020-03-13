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

}
