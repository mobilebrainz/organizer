package com.khodko.organizer.controller;


import com.khodko.organizer.MainApp;
import com.khodko.organizer.model.Pair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class RootController {

    @FXML
    public ChoiceBox<String> lessonsChoiceBox;

    @FXML
    private DatePicker datePicker;

    private MainApp mainApp;
    private String allLessonsItem = "Все предметы";

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        datePicker.setValue(mainApp.getDate());
    }

    @FXML
    public void onShowDayScheduleMenu() {
        datePicker.setVisible(true);
        lessonsChoiceBox.setVisible(false);

        LocalDate date = LocalDate.now();
        datePicker.setValue(date);
        mainApp.setDate(date);
        mainApp.showDaySchedule();
    }

    @FXML
    public void onShowWeekScheduleMenu() {
        datePicker.setVisible(false);
        lessonsChoiceBox.setVisible(true);

        mainApp.showWeekSchedule();
        initLessonsChoiceBox();
        lessonsChoiceBox.getSelectionModel().selectFirst();
    }

    private void initLessonsChoiceBox() {
        ObservableList<String> observableLessons = FXCollections.observableArrayList();
        observableLessons.add(allLessonsItem);
        observableLessons.addAll(getWeekScheduleLessons());
        lessonsChoiceBox.setItems(observableLessons);
    }

    private List<String> getWeekScheduleLessons() {
        List<String> lessons = new ArrayList<>();
        for (Pair pair : mainApp.getWeekScheduleStorage().getWeekSchedule()) {
            if (!lessons.contains(pair.getLesson())) {
                lessons.add(pair.getLesson());
            }
        }
        return lessons;
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
    public void onSaveScheduleLessonsMenu() {

    }

    @FXML
    public void onDatePicker() {
        LocalDate date = datePicker.getValue();
        mainApp.setDate(date);
        mainApp.showDaySchedule();
    }
}
