package com.khodko.organizer.controller;


import com.khodko.organizer.MainApp;
import com.khodko.organizer.storage.LessonsStorage;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


public class AddLessonsDialogController {

    @FXML
    private TextField lessonField;

    @FXML
    private ListView<String> listView;

    private ObservableList<String> lessons;
    private LessonsStorage lessonsStorage;

    public void setMainApp(MainApp mainApp) {
        lessonsStorage = mainApp.getLessonsStorage();
        lessons = lessonsStorage.getLessons();
        listView.setItems(lessons);
    }

    @FXML
    public void onAddBtn() {
        String lesson = lessonField.getText().trim();
        if (!lesson.isEmpty() && !lessons.contains(lesson)) {
            lessons.add(lesson);
            lessonsStorage.write();
        }
        lessonField.setText("");
    }

    @FXML
    public void onDeleteBtn() {
        ObservableList<String> selectedLessons = listView.getSelectionModel().getSelectedItems();
        lessons.removeAll(selectedLessons);
        lessonsStorage.write();
    }

}
