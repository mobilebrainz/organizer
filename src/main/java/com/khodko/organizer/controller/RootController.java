package com.khodko.organizer.controller;


import com.khodko.organizer.MainApp;
import com.khodko.organizer.utils.DateUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;


public class RootController {

    @FXML
    public ChoiceBox<String> lessonsChoiceBox;

    @FXML
    public ChoiceBox<String> weekDaysChoiceBox;

    @FXML
    private DatePicker datePicker;

    private MainApp mainApp;
    private String allLessonsItem = "Все предметы";
    private ObservableList<String> weekDays = FXCollections.observableArrayList();

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        // вызывает onDatePicker()
        datePicker.setValue(LocalDate.now());
    }

    @FXML
    private void initialize() {
        weekDays.addAll(DateUtil.weekDays);
        weekDaysChoiceBox.setItems(weekDays);

        lessonsChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        if (newValue.equals(allLessonsItem)) {
                            mainApp.showWeekSchedule(null);
                        } else {
                            mainApp.showWeekSchedule(newValue);
                        }
                    }
                }
        );

        weekDaysChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        mainApp.showEditDaySchedule(weekDays.indexOf(newValue));
                    }
                }
        );
    }

    @FXML
    public void onShowDayScheduleMenu() {
        datePicker.setVisible(true);
        lessonsChoiceBox.setVisible(false);
        weekDaysChoiceBox.setVisible(false);

        datePicker.setValue(LocalDate.now());
        onDatePicker();
    }

    @FXML
    public void onShowWeekScheduleMenu() {
        datePicker.setVisible(false);
        weekDaysChoiceBox.setVisible(false);
        lessonsChoiceBox.setVisible(true);

        ObservableList<String> observableLessons = FXCollections.observableArrayList();
        observableLessons.add(allLessonsItem);
        observableLessons.addAll(mainApp.getWeekScheduleStorage().getScheduleLessons());
        lessonsChoiceBox.setItems(observableLessons);
        // вызывает слушателя на lessonsChoiceBox (см. в initialize())
        lessonsChoiceBox.getSelectionModel().selectFirst();
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
    public void onEditScheduleMenu() {
        datePicker.setVisible(false);
        lessonsChoiceBox.setVisible(false);
        weekDaysChoiceBox.setVisible(true);

        // вызывает слушателя на weekDaysChoiceBox (см. в initialize())
        weekDaysChoiceBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void onDatePicker() {
        LocalDate date = datePicker.getValue();
        mainApp.showDaySchedule(date);
    }

}
