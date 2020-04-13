package com.khodko.organizer.storage;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static java.nio.charset.StandardCharsets.UTF_8;


public class TeachersStorage {

    private final String TEACHERS_DIR = "src/main/resources/storage/";
    private final String TEACHERS_FILE = "teachers.txt";

    private final Path filePath = Paths.get(TEACHERS_DIR + TEACHERS_FILE);
    private ObservableList<String> teachers = FXCollections.observableArrayList();

    public TeachersStorage() {
        read();
    }

    public void read() {
        try {
            if (Files.exists(filePath)) {
                List<String> lines = Files.readAllLines(filePath, UTF_8);
                teachers.clear();
                teachers.addAll(lines);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write() {
        try {
            Path directory = Paths.get(TEACHERS_DIR);
            if (!Files.isDirectory(directory)) {
                Files.createDirectory(directory);
            }

            Files.deleteIfExists(filePath);
            Path file = Files.createFile(filePath);
            Collections.sort(teachers);
            Files.write(file, teachers, UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<String> getTeachers() {
        return teachers;
    }

}
