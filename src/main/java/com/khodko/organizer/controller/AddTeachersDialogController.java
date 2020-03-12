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
    private ObservableList<String> observableTeachers = FXCollections.observableArrayList();

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        List<String> lessonsList = mainApp.getTeachersLoader().getTeachers();
        observableTeachers.addAll(lessonsList);
        listView.setItems(observableTeachers);
    }

    @FXML
    public void onAddBtn() {
        String teacher = teacherField.getText().trim();
        if (!teacher.isEmpty() && !observableTeachers.contains(teacher)) {
            mainApp.getTeachersLoader().getTeachers().add(teacher);
            mainApp.getTeachersLoader().write();
            observableTeachers.add(teacher);
        }
        teacherField.setText("");
    }

    @FXML
    public void onDeleteBtn() {
        ObservableList<String> selectedTeachers = listView.getSelectionModel().getSelectedItems();
        mainApp.getTeachersLoader().getTeachers().removeAll(selectedTeachers);
        mainApp.getTeachersLoader().write();
        observableTeachers.removeAll(selectedTeachers);
    }

}
