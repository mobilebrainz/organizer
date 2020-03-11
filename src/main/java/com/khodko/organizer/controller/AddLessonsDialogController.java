package com.khodko.organizer.controller;


import com.khodko.organizer.StaticStorage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.Collections;
import java.util.List;


public class AddLessonsDialogController {

    @FXML
    private TextField lessonField;

    @FXML
    private ListView<String> listView;

    private ObservableList<String> lessons = FXCollections.observableArrayList();

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // todo: take from mainApp field lessons
        List<String> lessonsList = StaticStorage.loadLessons();;
        lessons.addAll(lessonsList);
        listView.setItems(lessons);
    }

    @FXML
    public void onAddBtn() {
        String lesson = lessonField.getText().trim();
        if (!lesson.isEmpty() && !lessons.contains(lesson)) {
            saveLesson(lesson);
            lessons.add(lesson);
        }
        lessonField.setText("");
    }

    @FXML
    public void onDeleteBtn() {
        ObservableList<String> selectedLessons = listView.getSelectionModel().getSelectedItems();
        removeLessons(selectedLessons);
        lessons.removeAll(selectedLessons);
    }

    private void saveLesson(String lesson) {
        StaticStorage.lessons.add(lesson);
        // todo: перед сохранением предметов в файле - сортировать их
        Collections.sort(StaticStorage.lessons);
    }

    private void removeLessons(List<String> lessons) {
        StaticStorage.lessons.removeAll(lessons);
    }
}
