package com.khodko.organizer.storage;


import java.util.ArrayList;
import java.util.List;


public class StaticStorage {

    public static List<String> types = new ArrayList<>();

    static {
        types.add("ЛБ");
        types.add("ПР");
        types.add("Лекция");
    }

}
