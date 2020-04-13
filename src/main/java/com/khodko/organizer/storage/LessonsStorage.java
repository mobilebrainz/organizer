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


public class LessonsStorage {

    private final String LESSONS_DIR = "src/main/resources/storage/";
    private final String LESSONS_FILE = "lessons.txt";

    private final Path filePath = Paths.get(LESSONS_DIR + LESSONS_FILE);
    private final ObservableList<String> lessons = FXCollections.observableArrayList();

    public LessonsStorage() {
        read();
    }

    public void read() {
        try {
            if (Files.exists(filePath)) {
                List<String> lines = Files.readAllLines(filePath, UTF_8);
                lessons.clear();
                lessons.addAll(lines);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write() {
        try {
            Path directory = Paths.get(LESSONS_DIR);
            if (!Files.isDirectory(directory)) {
                Files.createDirectory(directory);
            }

            Files.deleteIfExists(filePath);
            Path file = Files.createFile(filePath);
            Collections.sort(lessons);
            Files.write(file, lessons, UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<String> getLessons() {
        return lessons;
    }

}
