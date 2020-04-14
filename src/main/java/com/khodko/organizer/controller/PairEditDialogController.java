package com.khodko.organizer.controller;

import com.khodko.organizer.model.Pair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.khodko.organizer.MainApp.mainApp;


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

    @FXML
    public Label warningLabel;

    private Stage dialogStage;
    private Pair pair;
    private ObservableList<String> types = FXCollections.observableArrayList("ЛБ", "ПР", "Лекция");

    private List<Pair> schedule;

    public void init(Pair pair, Stage dialogStage) {
        this.pair = pair;
        this.dialogStage = dialogStage;
        schedule = mainApp.getScheduleStorage().getSchedule();

        initChoiceBoxes();
        showDetails();
    }

    private void initChoiceBoxes() {
        ObservableList<String> lessons = mainApp.getLessonsStorage().getLessons();
        lessonsChoiceBox.setItems(lessons);

        ObservableList<String> teachers = mainApp.getTeachersStorage().getTeachers();
        teachersChoiceBox.setItems(teachers);

        Collections.sort(types);
        typesChoiceBox.setItems(types);
    }

    private void showDetails() {
        if (pair != null) {
            deleteButton.setVisible(true);
            numPairSpinner.getValueFactory().setValue(pair.getNum());
            lessonsChoiceBox.getSelectionModel().select(pair.getLesson());
            typesChoiceBox.getSelectionModel().select(pair.getPairType());
            teachersChoiceBox.getSelectionModel().select(pair.getTeacher());
            cabinetField.setText(pair.getCabinet());
        } else {
            pair = new Pair();
            pair.setWeekDay(mainApp.getWeekDayOrdinal());
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
        schedule.remove(pair);

        String lesson = lessonsChoiceBox.getSelectionModel().getSelectedItem();
        if (lesson == null) {
            warningLabel.setVisible(true);
            warningLabel.setText("Добавьте и выберите предмет!");
            return;
        }
        pair.setLesson(lesson);

        String teacher = teachersChoiceBox.getSelectionModel().getSelectedItem();
        if (teacher == null) {
            warningLabel.setVisible(true);
            warningLabel.setText("Добавьте и выберите учителя!");
            return;
        }
        pair.setTeacher(teacher);

        String type = typesChoiceBox.getSelectionModel().getSelectedItem();
        pair.setPairType(type);

        String cabinet = cabinetField.getText().trim();
        if (cabinet.equals("")) {
            warningLabel.setVisible(true);
            warningLabel.setText("Введите номер кабинета!");
            return;
        }
        pair.setCabinet(cabinet);

        int num = numPairSpinner.getValue();
        pair.setNum(num);
        schedule.add(pair);

        //todo: сделать свою сортировку
        schedule.sort(Comparator.comparing(Pair::getNum));

        mainApp.getScheduleStorage().write();

        dialogStage.close();
        mainApp.showEditDaySchedule();
    }

    @FXML
    public void onDeleteBtn() {
        dialogStage.close();
        schedule.remove(pair);
        mainApp.getScheduleStorage().write();
        mainApp.showEditDaySchedule();
    }

    @FXML
    public void onCancelBtn() {
        dialogStage.close();
    }

}
