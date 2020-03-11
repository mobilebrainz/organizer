package com.khodko.organizer;


import com.khodko.organizer.model.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//  todo: only for test
public class StaticStorage {

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
    public static Map<String, Map<Integer, List<Pair>>> weekSchedule;

    public static Map<Integer, Pair> pairs = new HashMap<>();

    static {
        pairs.put(2, new Pair("Иванов Ф.А.", "Физика", "ЛБ", "408-2"));
        pairs.put(3, new Pair("Сидоров С.К.", "Математика", "ПР", "414-k1"));
        pairs.put(4, new Pair("Петров С.У.", "Логика", "Лекция", "341-3"));
        pairs.put(5, new Pair("Воробьёв А.Ф.", "История", "Лекция", "313-5"));
    }

    public static List<String> types = new ArrayList<>();

    static {
        types.add("ЛБ");
        types.add("ПР");
        types.add("Лекция");
    }

    public static List<String> lessons = new ArrayList<>();

    static {
        lessons.add("Физика");
        lessons.add("Математика");
        lessons.add("Логика");
        lessons.add("История");
    }

    public static List<String> teachers = new ArrayList<>();

    static {
        teachers.add("Иванов Ф.А.");
        teachers.add("Сидоров С.К.");
        teachers.add("Петров С.У.");
        teachers.add("Воробьёв А.Ф.");
    }

    public static List<String> loadLessons() {
        return lessons;
    }

    public static List<String> loadTeachers() {
        return teachers;
    }

}
