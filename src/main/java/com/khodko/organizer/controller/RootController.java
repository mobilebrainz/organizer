package com.khodko.organizer.controller;


import com.khodko.organizer.MainApp;
import com.khodko.organizer.model.Pair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
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
        // вызывает onDatePicker()
        datePicker.setValue(mainApp.getDate());
    }

    @FXML
    private void initialize() {
        lessonsChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        List<Pair> weekPairs;
                        if (newValue.equals(allLessonsItem)) {
                            weekPairs = mainApp.getWeekScheduleStorage().getWeekSchedule();
                        } else {
                            weekPairs = mainApp.getWeekScheduleStorage().getScheduleByLesson(newValue);
                        }
                        mainApp.showWeekSchedule(weekPairs);
                    }
                }
        );
    }

    @FXML
    public void onShowDayScheduleMenu() {
        datePicker.setVisible(true);
        lessonsChoiceBox.setVisible(false);

        // вызывает onDatePicker()
        datePicker.setValue(LocalDate.now());
    }

    @FXML
    public void onShowWeekScheduleMenu() {
        datePicker.setVisible(false);
        lessonsChoiceBox.setVisible(true);

        initLessonsChoiceBox();
        lessonsChoiceBox.getSelectionModel().selectFirst();
    }

    private void initLessonsChoiceBox() {
        ObservableList<String> observableLessons = FXCollections.observableArrayList();
        observableLessons.add(allLessonsItem);
        observableLessons.addAll(mainApp.getWeekScheduleStorage().getScheduleLessons());
        lessonsChoiceBox.setItems(observableLessons);
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
