package com.khodko.organizer.controller;


import com.khodko.organizer.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;


public class AddLessonsDialogController {

    @FXML
    private TextField lessonField;

    @FXML
    private ListView<String> listView;

    private MainApp mainApp;
    private List<String> lessons;
    private ObservableList<String> observableLessons = FXCollections.observableArrayList();

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        lessons = mainApp.getLessonsStorage().getLessons();
        observableLessons.addAll(lessons);
        listView.setItems(observableLessons);
    }

    @FXML
    public void onAddBtn() {
        String lesson = lessonField.getText().trim();
        if (!lesson.isEmpty() && !observableLessons.contains(lesson)) {
            lessons.add(lesson);
            mainApp.getLessonsStorage().write();
            observableLessons.add(lesson);
        }
        lessonField.setText("");
    }

    @FXML
    public void onDeleteBtn() {
        ObservableList<String> selectedLessons = listView.getSelectionModel().getSelectedItems();
        lessons.removeAll(selectedLessons);
        mainApp.getLessonsStorage().write();
        observableLessons.removeAll(selectedLessons);
    }

}
