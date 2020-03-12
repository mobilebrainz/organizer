package com.khodko.organizer;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khodko.organizer.model.Pair;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WeekSchedule {

    private final String WEEK_SCHEDULE_DIR = "src/main/resources/files/week-schedule.json";

    /*
    {
        "monday" : {
            "2" : {
                "teacher" : "Иванов Ф.А.",
                        "lesson" : "Физика",
                        "pairType" : "ЛБ",
                        "cabinet" : "414-1"
            }
            "3" : {
                "teacher" : "Иванов Ф.А.",
                        "lesson" : "Физика",
                        "pairType" : "ЛБ",
                        "cabinet" : "414-1"
            }
            "5" : {
                "teacher" : "Иванов Ф.А.",
                        "lesson" : "Физика",
                        "pairType" : "ЛБ",
                        "cabinet" : "414-1"
                }
            },
            ...
     }
     */
    private Map<String, Map<Integer, List<Pair>>> weekSchedule = new HashMap<>();
    private ObjectMapper objectMapper;

    public WeekSchedule() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    public void read() {
        try {
            File file = new File(WEEK_SCHEDULE_DIR);
            if (file.exists()) {
                weekSchedule = objectMapper.readValue(file, new TypeReference<Map<String, Map<Integer, List<Pair>>>>() {});
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

    public Map<String, Map<Integer, List<Pair>>> get() {
        return weekSchedule;
    }

}
