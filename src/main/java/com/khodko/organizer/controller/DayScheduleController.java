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
        int weekDay = mainApp.getDate().getDayOfWeek().ordinal();
        List<Pair> dayPairs = mainApp.getWeekScheduleStorage().getWeekSchedule().get(weekDay);

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

    @FXML
    public void onAddPairBtn() {
        mainApp.showPairEditDialog(null);
    }
}
