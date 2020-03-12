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
    private ObservableList<String> observableLessons = FXCollections.observableArrayList();

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        List<String> lessonsList = mainApp.getLessonsLoader().getLessons();
        observableLessons.addAll(lessonsList);
        listView.setItems(observableLessons);
    }

    @FXML
    public void onAddBtn() {
        String lesson = lessonField.getText().trim();
        if (!lesson.isEmpty() && !observableLessons.contains(lesson)) {
            mainApp.getLessonsLoader().getLessons().add(lesson);
            mainApp.getLessonsLoader().write();
            observableLessons.add(lesson);
        }
        lessonField.setText("");
    }

    @FXML
    public void onDeleteBtn() {
        ObservableList<String> selectedLessons = listView.getSelectionModel().getSelectedItems();
        mainApp.getLessonsLoader().getLessons().removeAll(selectedLessons);
        mainApp.getLessonsLoader().write();
        observableLessons.removeAll(selectedLessons);
    }

}
