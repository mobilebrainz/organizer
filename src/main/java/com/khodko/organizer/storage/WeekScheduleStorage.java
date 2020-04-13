package com.khodko.organizer.storage;


import com.khodko.organizer.model.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;


public class WeekScheduleStorage {

    private final String SCHEDULE_DIR = "src/main/resources/storage/";
    private final String SCHEDULE_FILE = "schedule.txt";

    private final List<Pair> schedule = new ArrayList<>();

    public WeekScheduleStorage() {
        read();
    }

    public void read() {
        try {
            if (Files.exists(Paths.get(SCHEDULE_DIR + SCHEDULE_FILE))) {
                List<String> lines = Files.readAllLines(Paths.get(SCHEDULE_DIR + SCHEDULE_FILE), UTF_8);

                schedule.clear();
                for (int i = 1; i < lines.size(); i++) {
                    String[] fields = lines.get(i).split("\\|");
                    schedule.add(new Pair(
                            Integer.valueOf(fields[0]),
                            Integer.valueOf(fields[1]),
                            fields[2],
                            fields[3],
                            fields[4],
                            fields[5]
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write() {
        try {
            List<String> lines = new ArrayList<>();
            lines.add("weekDay|num|teacher|lesson|type|cabinet");
            for (Pair pair : schedule) {
                lines.add(pair.toString());
            }

            Path directory = Paths.get(SCHEDULE_DIR);
            if (!Files.isDirectory(directory)) {
                Files.createDirectory(directory);
            }

            Files.deleteIfExists(Paths.get(SCHEDULE_DIR + SCHEDULE_FILE));
            Path file = Files.createFile(Paths.get(SCHEDULE_DIR + SCHEDULE_FILE));
            Files.write(file, lines, UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Pair> getSchedule() {
        return schedule;
    }

    public List<Pair> getDaySchedule(int weekDay) {
        List<Pair> daySchedule = new ArrayList<>();
        for (Pair pair : schedule) {
            if (pair.getWeekDay() == weekDay) {
                daySchedule.add(pair);
            }
        }
        return daySchedule;
    }

    public List<Pair> getWeekSchedule(String lesson) {
        if (lesson == null) return schedule;

        List<Pair> lessonSchedule = new ArrayList<>();
        for (Pair pair : schedule) {
            if (pair.getLesson().equals(lesson)) {
                lessonSchedule.add(pair);
            }
        }
        return lessonSchedule;
    }

    public List<String> getScheduleLessons() {
        List<String> lessons = new ArrayList<>();
        for (Pair pair : schedule) {
            if (!lessons.contains(pair.getLesson())) {
                lessons.add(pair.getLesson());
            }
        }
        return lessons;
    }

}
