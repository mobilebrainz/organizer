package com.khodko.organizer.loaders;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khodko.organizer.StaticStorage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class LessonsStorage {

    private final String LESSONS_DIR = "src/main/resources/files/lessons.json";

    private List<String> lessons = new ArrayList<>();
    private ObjectMapper objectMapper;

    public LessonsStorage() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    public void read() {
        try {
            File file = new File(LESSONS_DIR);
            if (file.exists()) {
                lessons = objectMapper.readValue(file, new TypeReference<List<String>>() {
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write() {
        try {
            Collections.sort(lessons);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(LESSONS_DIR), lessons);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getLessons() {
        return lessons;
    }

}
