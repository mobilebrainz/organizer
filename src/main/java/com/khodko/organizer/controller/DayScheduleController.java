package com.khodko.organizer.controller;

import com.khodko.organizer.MainApp;
import com.khodko.organizer.model.Pair;
import com.khodko.organizer.utils.DateUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class DayScheduleController {

    @FXML
    private VBox pairVBox;

    @FXML
    private Label dataLabel;

    private MainApp mainApp;

    public void init(MainApp mainApp) {
        this.mainApp = mainApp;

        showDate();
        showPairs();
    }

    public void showDate() {
        String dataString = DateUtil.getDateString(mainApp.getDate());
        dataLabel.setText(dataString);
    }

    public void showPairs() {
        String weekDay = DateUtil.getWeekDay(mainApp.getDate());
        List<Pair> dayPairs = getDayPairs(weekDay);

        // todo: сделать сортировку вручную
        dayPairs.sort(Comparator.comparing(Pair::getNum));

        for (Pair pair : dayPairs) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("/fxml/PairLayout.fxml"));
                AnchorPane pairLayout = loader.load();
                pairVBox.getChildren().add(pairLayout);

                PairController controller = loader.getController();
                controller.init(mainApp, pair);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Pair> getDayPairs(String weekDay) {
        List<Pair> dayPairs = new ArrayList<>();
        List<Pair> weekSchedule = mainApp.getWeekScheduleStorage().getWeekSchedule();
        for (Pair pair : weekSchedule) {
            if (pair.getWeekDay().equals(weekDay)) {
                dayPairs.add(pair);
            }
        }
        return dayPairs;
    }

    @FXML
    public void onAddPairBtn() {
        mainApp.showPairEditDialog(null);
    }
}
