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


public class LessonsStorage {

    private final String LESSONS_DIR = "src/main/resources/storage/";
    private final String LESSONS_FILE = "lessons.json";

    private ObservableList<String> lessons = FXCollections.observableArrayList();
    private ObjectMapper objectMapper;

    public LessonsStorage() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        read();
    }

    public void read() {
        try {
            File file = new File(LESSONS_DIR + LESSONS_FILE);
            if (file.exists()) {
                lessons.clear();
                lessons.addAll(objectMapper.readValue(file, new TypeReference<List<String>>() {
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
            Collections.sort(lessons);

            Path directory = Paths.get(LESSONS_DIR);
            if (!Files.isDirectory(directory)) {
                Files.createDirectory(directory);
            }
            File file = new File(LESSONS_DIR + LESSONS_FILE);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, lessons);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<String> getLessons() {
        return lessons;
    }

}
