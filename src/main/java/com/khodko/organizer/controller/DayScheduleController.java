package com.khodko.organizer.controller;

import com.khodko.organizer.MainApp;
import com.khodko.organizer.StaticStorage;
import com.khodko.organizer.model.Pair;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class DayScheduleController {

    @FXML
    private VBox pairVBox;

    @FXML
    private Label dataLabel;

    private MainApp mainApp;
    private Map<Integer, Pair> pairs;

    public void init(MainApp mainApp) {
        this.mainApp = mainApp;

        //todo: из date найти weekDay и получить пары из mainApp поля всего расписания
        LocalDate date = mainApp.getDate();
        this.pairs = StaticStorage.pairs;

        showDate(date);
        showPairs(pairs);
    }

    public void showDate(LocalDate date) {
        int dayOfWeek = date.getDayOfWeek().ordinal();
        String[] weekDays = {"понедельник", "вторник", "среда", "четверг", "пятница",
                "суббота", "воскресенье"};
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataString = dtf.format(date) + " " + weekDays[dayOfWeek];
        dataLabel.setText(dataString);
    }

    private void showPairs(Map<Integer, Pair> pairs) {
        List<Integer> numPairList = new ArrayList<>(pairs.keySet());
        Collections.sort(numPairList);
        for (Integer numPair : numPairList) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("/fxml/PairLayout.fxml"));
                AnchorPane pairLayout = loader.load();
                pairVBox.getChildren().add(pairLayout);

                PairController controller = loader.getController();
                controller.init(mainApp, pairs.get(numPair), numPair);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onAddPairBtn() {
        mainApp.showPairEditDialog(null, null);
    }
}
