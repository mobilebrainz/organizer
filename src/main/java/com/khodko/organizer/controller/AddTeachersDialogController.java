package com.khodko.organizer.controller;


import com.khodko.organizer.StaticStorage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.Collections;
import java.util.List;


public class AddTeachersDialogController {

    @FXML
    private TextField teacherField;

    @FXML
    private ListView<String> listView;

    private ObservableList<String> teachers = FXCollections.observableArrayList();

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // todo: take from mainApp field teachers
        List<String> teachersList = StaticStorage.loadTeachers();
        teachers.addAll(teachersList);
        listView.setItems(teachers);
    }

    @FXML
    public void onAddBtn() {
        String teacher = teacherField.getText().trim();
        if (!teacher.isEmpty() && !teachers.contains(teacher)) {
            saveTeacher(teacher);
            teachers.add(teacher);
        }
        teacherField.setText("");
    }

    @FXML
    public void onDeleteBtn() {
        ObservableList<String> selectedTeachers = listView.getSelectionModel().getSelectedItems();
        removeTeachers(selectedTeachers);
        teachers.removeAll(selectedTeachers);
    }

    private void saveTeacher(String lesson) {
        StaticStorage.teachers.add(lesson);
        // todo: перед сохранением предметов в файле - сортировать их
        Collections.sort(StaticStorage.teachers);
    }

    private void removeTeachers(List<String> teachers) {
        StaticStorage.teachers.removeAll(teachers);
    }

}
