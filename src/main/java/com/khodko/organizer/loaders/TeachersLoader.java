package com.khodko.organizer.loaders;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TeachersLoader {

    private final String TEACHERS_DIR = "src/main/resources/files/teachers.json";

    private List<String> teachers = new ArrayList<>();
    private ObjectMapper objectMapper;

    public TeachersLoader() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    public void read() {
        try {
            File file = new File(TEACHERS_DIR);
            if (file.exists()) {
                teachers = objectMapper.readValue(file, new TypeReference<List<String>>() {
                });
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

    public List<String> getTeachers() {
        return teachers;
    }

}
