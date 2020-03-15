package com.khodko.organizer.controller;

import com.khodko.organizer.MainApp;
import com.khodko.organizer.model.Pair;
import com.khodko.organizer.utils.DateUtil;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


public class WeekScheduleController {

    @FXML
    public VBox dayVBox;

    private MainApp mainApp;
    private List<List<Pair>> weekSchedule;

    public void setMainApp(MainApp mainApp, String lesson) {
        this.mainApp = mainApp;
        weekSchedule = mainApp.getWeekScheduleStorage().getWeekSchedule(lesson);

        showSchedule();
    }

    private void showSchedule() {
        for (int weekDay = 0; weekDay < weekSchedule.size(); weekDay++) {
            List<Pair> daySchedule = weekSchedule.get(weekDay);

            if (!daySchedule.isEmpty()) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MainApp.class.getResource("/fxml/DayScheduleLayout.fxml"));
                    AnchorPane pairLayout = loader.load();
                    dayVBox.getChildren().add(pairLayout);

                    DayScheduleController controller = loader.getController();
                    String dateString = DateUtil.weekDays[weekDay];
                    controller.setMainApp(mainApp, daySchedule, dateString, true);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    public void onSaveScheduleBtn() {

    }
}
