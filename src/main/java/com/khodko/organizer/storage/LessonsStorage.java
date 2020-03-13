package com.khodko.organizer.storage;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class LessonsStorage {

    private final String LESSONS_DIR = "src/main/resources/storage/lessons.json";

    private ObservableList<String> lessons = FXCollections.observableArrayList();
    private ObjectMapper objectMapper;

    public LessonsStorage() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    public void read() {
        try {
            File file = new File(LESSONS_DIR);
            if (file.exists()) {
                lessons.addAll(objectMapper.readValue(file, new TypeReference<List<String>>() {
                }));
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

    public ObservableList<String> getLessons() {
        return lessons;
    }

}
