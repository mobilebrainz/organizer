package com.khodko.organizer.controller;

import com.khodko.organizer.MainApp;
import com.khodko.organizer.model.Pair;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    private Stage dialogStage;
    private Pair pair;
    private ObservableList<String> types = FXCollections.observableArrayList("ЛБ", "ПР", "Лекция");

    private List<Pair> weekSchedule;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void init(Pair pair) {
        this.pair = pair;

        int weekDayOrdianl = mainApp.getWeekDayOrdinal();
        weekSchedule = mainApp.getWeekScheduleStorage().getWeekSchedule().get(weekDayOrdianl);

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
        weekSchedule.remove(pair);

        String lesson = lessonsChoiceBox.getSelectionModel().getSelectedItem();
        pair.setLesson(lesson);

        String teacher = teachersChoiceBox.getSelectionModel().getSelectedItem();
        pair.setTeacher(teacher);

        String type = typesChoiceBox.getSelectionModel().getSelectedItem();
        pair.setPairType(type);

        pair.setCabinet(cabinetField.getText());

        int num = numPairSpinner.getValue();

        // Удалить пару с тем же номером, что добавляется. Это обеспечит уникальность пары в расписании
        //weekSchedule.remove(getPair(num));

        pair.setNum(num);
        weekSchedule.add(pair);

        weekSchedule.sort(Comparator.comparing(Pair::getNum));

        mainApp.getWeekScheduleStorage().write();

        dialogStage.close();
    }

    public Pair getPair(Integer num) {
        for (Pair pair : weekSchedule) {
            if (pair.getNum().equals(num)) {
                return pair;
            }
        }
        return null;
    }

    @FXML
    public void onDeleteBtn() {
        dialogStage.close();
        weekSchedule.remove(pair);
        mainApp.getWeekScheduleStorage().write();
    }

    @FXML
    public void onCancelBtn() {
        dialogStage.close();
    }

}
