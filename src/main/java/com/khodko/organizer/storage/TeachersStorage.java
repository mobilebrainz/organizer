package com.khodko.organizer.storage;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class TeachersStorage {

    private final String TEACHERS_DIR = "src/main/resources/storage/teachers.json";

    private ObservableList<String> teachers = FXCollections.observableArrayList();
    private ObjectMapper objectMapper;

    public TeachersStorage() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    public void read() {
        try {
            File file = new File(TEACHERS_DIR);
            if (file.exists()) {
                teachers.addAll(objectMapper.readValue(file, new TypeReference<List<String>>() {
                }));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write() {
        try {
            Collections.sort(teachers);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(TEACHERS_DIR), teachers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<String> getTeachers() {
        return teachers;
    }

}
