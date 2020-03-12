package com.khodko.organizer.controller;

import com.khodko.organizer.utils.DateUtil;
import com.khodko.organizer.MainApp;
import com.khodko.organizer.storage.StaticStorage;
import com.khodko.organizer.model.Pair;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DayScheduleController {

    @FXML
    private VBox pairVBox;

    @FXML
    private Label dataLabel;

    private MainApp mainApp;

    public void init(MainApp mainApp) {
        this.mainApp = mainApp;

        LocalDate date = mainApp.getDate();
        showDate(date);

        showPairs();
    }

    public void showDate(LocalDate date) {
        String dataString = DateUtil.getDateString(date);
        dataLabel.setText(dataString);
    }

    public void showPairs() {
        LocalDate date = mainApp.getDate();
        String weekDay = DateUtil.getWeekDay(date);

        Map<String, Map<Integer, Pair>> weekSchedule = mainApp.getWeekScheduleStorage().getWeekSchedule();
        Map<Integer, Pair> daySchedule = weekSchedule.get(weekDay);
        if (daySchedule == null) {
            daySchedule = new HashMap<>();
            weekSchedule.put(weekDay, daySchedule);
        }

        List<Integer> numPairKeys = new ArrayList<>(daySchedule.keySet());
        Collections.sort(numPairKeys);
        for (Integer numPair : numPairKeys) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("/fxml/PairLayout.fxml"));
                AnchorPane pairLayout = loader.load();
                pairVBox.getChildren().add(pairLayout);

                PairController controller = loader.getController();
                controller.init(mainApp, daySchedule.get(numPair), numPair);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onAddPairBtn() {
        mainApp.showPairEditDialog(null);
    }
}
