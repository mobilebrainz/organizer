package com.khodko.organizer.controller;


import com.khodko.organizer.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;


public class RootController {

    @FXML
    private DatePicker datePicker;

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        datePicker.setValue(mainApp.getDate());
    }

    @FXML
    public void onAddTeachersMenu() {
        mainApp.showAddTeachersDialog();
    }

    @FXML
    public void onAddLessonsMenu() {
        mainApp.showAddLessonsDialog();
    }

    @FXML
    public void onDatePicker() {
        LocalDate date = datePicker.getValue();
        mainApp.setDate(date);
        mainApp.showDaySchedule();
    }
}
