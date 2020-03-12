package com.khodko.organizer.controller;

import com.khodko.organizer.MainApp;
import com.khodko.organizer.storage.StaticStorage;
import com.khodko.organizer.model.Pair;
import com.khodko.organizer.utils.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PairEditDialogController {

    @FXML
    private Spinner<Integer> numPairSpinner;

    @FXML
    private ChoiceBox<String> lessonsChoiceBox;

    @FXML
    private ChoiceBox<String> typesChoiceBox;

    @FXML
    private ChoiceBox<String> teachersChoiceBox;

    @FXML
    private TextField cabinetField;

    @FXML
    private Button deleteButton;

    private MainApp mainApp;
    private Stage dialogStage;
    private Pair pair;
    private Integer numPair;

    private Map<String, Map<Integer, Pair>> weekSchedule;
    private String weekDay;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void init(MainApp mainApp, Integer numPair) {
        this.mainApp = mainApp;
        this.numPair = numPair;

        weekSchedule = mainApp.getWeekScheduleStorage().getWeekSchedule();
        weekDay = DateUtil.getWeekDay(mainApp.getDate());
        if (numPair != null) {
            // todo: check on null get(weekDay)
            this.pair = weekSchedule.get(weekDay).get(numPair);
        }

        initLessonsChoiceBox();
        initTypesChoiceBox();
        initTeachersChoiceBox();
        showDetails();
    }

    private void initLessonsChoiceBox() {
        List<String> lessons = mainApp.getLessonsStorage().getLessons();
        ObservableList<String> observableLessons = FXCollections.observableArrayList();
        observableLessons.addAll(lessons);
        lessonsChoiceBox.setItems(observableLessons);
    }

    private void initTypesChoiceBox() {
        List<String> types = StaticStorage.types;
        Collections.sort(types);
        ObservableList<String> observableTypes = FXCollections.observableArrayList();
        observableTypes.addAll(types);
        typesChoiceBox.setItems(observableTypes);
    }

    private void initTeachersChoiceBox() {
        List<String> teachers = mainApp.getTeachersStorage().getTeachers();
        ObservableList<String> ObservableTeachers = FXCollections.observableArrayList();
        ObservableTeachers.addAll(teachers);
        teachersChoiceBox.setItems(ObservableTeachers);
    }

    private void showDetails() {
        if (pair != null) {
            deleteButton.setVisible(true);
            numPairSpinner.getValueFactory().setValue(numPair);
            lessonsChoiceBox.getSelectionModel().select(pair.getLesson());
            typesChoiceBox.getSelectionModel().select(pair.getPairType());
            teachersChoiceBox.getSelectionModel().select(pair.getTeacher());
            cabinetField.setText(pair.getCabinet());
        } else {
            pair = new Pair();
            deleteButton.setVisible(false);
            numPairSpinner.getValueFactory().setValue(1);
            lessonsChoiceBox.getSelectionModel().selectFirst();
            typesChoiceBox.getSelectionModel().selectFirst();
            teachersChoiceBox.getSelectionModel().selectFirst();
            cabinetField.setText("");
        }
    }

    @FXML
    public void onOkBtn() {
        String lesson = lessonsChoiceBox.getSelectionModel().getSelectedItem();
        pair.setLesson(lesson);

        String teacher = teachersChoiceBox.getSelectionModel().getSelectedItem();
        pair.setTeacher(teacher);

        String type = typesChoiceBox.getSelectionModel().getSelectedItem();
        pair.setPairType(type);

        pair.setCabinet(cabinetField.getText());

        dialogStage.close();

        Map<Integer, Pair> daySchedule = weekSchedule.get(weekDay);
        if (daySchedule == null) {
            daySchedule = new HashMap<>();
            weekSchedule.put(weekDay, daySchedule);
        }

        daySchedule.remove(numPair);
        numPair = numPairSpinner.getValue();
        daySchedule.put(numPair, pair);
        mainApp.getWeekScheduleStorage().write();
    }

    @FXML
    public void onDeleteBtn() {
        dialogStage.close();

        Map<Integer, Pair> daySchedule = weekSchedule.get(weekDay);
        daySchedule.remove(numPair);
        mainApp.getWeekScheduleStorage().write();
    }

    @FXML
    public void onCancelBtn() {
        dialogStage.close();
    }

}
