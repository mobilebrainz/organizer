package com.khodko.organizer.controller;


import com.khodko.organizer.storage.TeachersStorage;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import static com.khodko.organizer.MainApp.mainApp;


public class AddTeachersDialogController {

    @FXML
    private TextField teacherField;

    @FXML
    private ListView<String> listView;

    private ObservableList<String> teachers;
    private TeachersStorage teachersStorage;

    @FXML
    private void initialize() {
        teachersStorage = mainApp.getTeachersStorage();
        teachers = mainApp.getTeachersStorage().getTeachers();
        listView.setItems(teachers);
    }

    @FXML
    public void onAddBtn() {
        String teacher = teacherField.getText().trim();
        if (!teacher.isEmpty() && !teachers.contains(teacher)) {
            teachers.add(teacher);
            teachersStorage.write();
        }
        teacherField.setText("");
    }

    @FXML
    public void onDeleteBtn() {
        ObservableList<String> selectedTeachers = listView.getSelectionModel().getSelectedItems();
        teachers.removeAll(selectedTeachers);
        teachersStorage.write();
    }

}
