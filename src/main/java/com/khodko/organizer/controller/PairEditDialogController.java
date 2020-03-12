package com.khodko.organizer.controller;

import com.khodko.organizer.MainApp;
import com.khodko.organizer.StaticStorage;
import com.khodko.organizer.model.Pair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.List;


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

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void init(MainApp mainApp, Pair pair, Integer numPair) {
        this.mainApp = mainApp;
        this.pair = pair;
        this.numPair = numPair;
        initLessonsChoiceBox();
        initTypesChoiceBox();
        initTeachersChoiceBox();
        showDetails();
    }

    private void initLessonsChoiceBox() {
        List<String> lessonsList = mainApp.getLessonsLoader().getLessons();
        ObservableList<String> lessons = FXCollections.observableArrayList();
        lessons.addAll(lessonsList);
        lessonsChoiceBox.setItems(lessons);
    }

    private void initTypesChoiceBox() {
        List<String> typesList = StaticStorage.types;
        Collections.sort(typesList);
        ObservableList<String> types = FXCollections.observableArrayList();
        types.addAll(typesList);
        typesChoiceBox.setItems(types);
    }

    private void initTeachersChoiceBox() {
        List<String> teachersList = mainApp.getTeachersLoader().getTeachers();
        ObservableList<String> teachers = FXCollections.observableArrayList();
        teachers.addAll(teachersList);
        teachersChoiceBox.setItems(teachers);
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
        numPair = numPairSpinner.getValue();

        String lesson = lessonsChoiceBox.getSelectionModel().getSelectedItem();
        pair.setLesson(lesson);

        String teacher = teachersChoiceBox.getSelectionModel().getSelectedItem();
        pair.setTeacher(teacher);

        String type = typesChoiceBox.getSelectionModel().getSelectedItem();
        pair.setPairType(type);

        pair.setCabinet(cabinetField.getText());

        dialogStage.close();
        mainApp.savePair(numPair, pair);
    }

    @FXML
    public void onDeleteBtn() {
        dialogStage.close();
        mainApp.deletePair(numPair);
    }

    @FXML
    public void onCancelBtn() {
        dialogStage.close();
    }

}
