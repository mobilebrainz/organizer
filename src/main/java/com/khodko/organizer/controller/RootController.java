package com.khodko.organizer.controller;


import com.khodko.organizer.utils.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;

import static com.khodko.organizer.MainApp.mainApp;


public class RootController {

    @FXML
    public ChoiceBox<String> lessonsChoiceBox;

    @FXML
    public ChoiceBox<String> weekDaysChoiceBox;

    @FXML
    private DatePicker datePicker;

    private String allLessonsItem = "Все предметы";

    @FXML
    private void initialize() {
        // вызывает onDatePicker()
        datePicker.setValue(LocalDate.now());

        ObservableList<String> weekDays = FXCollections.observableArrayList(DateUtil.weekDays);
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
                        mainApp.setWeekDayOrdinal(weekDays.indexOf(newValue));
                        mainApp.showEditDaySchedule();
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
        weekDaysChoiceBox.getSelectionModel().clearSelection();
        weekDaysChoiceBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void onDatePicker() {
        LocalDate date = datePicker.getValue();
        mainApp.showDaySchedule(date);
    }

}
