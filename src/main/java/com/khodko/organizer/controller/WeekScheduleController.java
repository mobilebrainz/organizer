package com.khodko.organizer.controller;

import com.khodko.organizer.MainApp;
import com.khodko.organizer.model.Pair;
import javafx.fxml.FXML;

import java.util.List;


public class WeekScheduleController {

    private MainApp mainApp;
    private List<Pair> pairs;

    public void init(MainApp mainApp, List<Pair> pairs) {
        this.mainApp = mainApp;
        this.pairs = pairs;
    }

    @FXML
    public void onSaveScheduleBtn() {

    }
}
