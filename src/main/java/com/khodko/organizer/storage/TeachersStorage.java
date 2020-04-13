package com.khodko.organizer.storage;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;


public class TeachersStorage {

    private final String TEACHERS_DIR = "src/main/resources/storage/";
    private final String TEACHERS_FILE = "teachers.json";

    private ObservableList<String> teachers = FXCollections.observableArrayList();
    private ObjectMapper objectMapper;

    public TeachersStorage() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        read();
    }

    public void read() {
        try {
            File file = new File(TEACHERS_DIR + TEACHERS_FILE);
            if (file.exists()) {
                teachers.clear();
                teachers.addAll(objectMapper.readValue(file, new TypeReference<List<String>>() {
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
            Collections.sort(teachers);

            Path directory = Paths.get(TEACHERS_DIR);
            if (!Files.isDirectory(directory)) {
                Files.createDirectory(directory);
            }
            File file = new File(TEACHERS_DIR + TEACHERS_FILE);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, teachers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<String> getTeachers() {
        return teachers;
    }

}
