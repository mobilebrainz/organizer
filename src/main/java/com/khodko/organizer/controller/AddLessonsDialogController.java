package com.khodko.organizer.controller;


import com.khodko.organizer.MainApp;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


public class AddLessonsDialogController {

    @FXML
    private TextField lessonField;

    @FXML
    private ListView<String> listView;

    private MainApp mainApp;
    private ObservableList<String> lessons;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        lessons = mainApp.getLessonsStorage().getLessons();
        listView.setItems(lessons);
    }

    @FXML
    public void onAddBtn() {
        String lesson = lessonField.getText().trim();
        if (!lesson.isEmpty() && !lessons.contains(lesson)) {
            lessons.add(lesson);
            mainApp.getLessonsStorage().write();
        }
        lessonField.setText("");
    }

    @FXML
    public void onDeleteBtn() {
        ObservableList<String> selectedLessons = listView.getSelectionModel().getSelectedItems();
        lessons.removeAll(selectedLessons);
        mainApp.getLessonsStorage().write();
    }

}
