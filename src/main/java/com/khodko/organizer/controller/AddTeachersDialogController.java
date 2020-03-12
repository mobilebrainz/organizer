package com.khodko.organizer.controller;


import com.khodko.organizer.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;


public class AddTeachersDialogController {

    @FXML
    private TextField teacherField;

    @FXML
    private ListView<String> listView;

    private MainApp mainApp;
    private List<String> teachers;
    private ObservableList<String> observableTeachers = FXCollections.observableArrayList();

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        teachers = mainApp.getTeachersStorage().getTeachers();
        observableTeachers.addAll(teachers);
        listView.setItems(observableTeachers);
    }

    @FXML
    public void onAddBtn() {
        String teacher = teacherField.getText().trim();
        if (!teacher.isEmpty() && !observableTeachers.contains(teacher)) {
            teachers.add(teacher);
            mainApp.getTeachersStorage().write();
            observableTeachers.add(teacher);
        }
        teacherField.setText("");
    }

    @FXML
    public void onDeleteBtn() {
        ObservableList<String> selectedTeachers = listView.getSelectionModel().getSelectedItems();
        teachers.removeAll(selectedTeachers);
        mainApp.getTeachersStorage().write();
        observableTeachers.removeAll(selectedTeachers);
    }

}
