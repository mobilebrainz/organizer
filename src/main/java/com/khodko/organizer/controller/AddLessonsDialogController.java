package com.khodko.organizer.controller;


import com.khodko.organizer.storage.LessonsStorage;
import com.khodko.organizer.storage.WeekScheduleStorage;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.time.LocalDate;

import static com.khodko.organizer.MainApp.mainApp;


public class AddLessonsDialogController {

    @FXML
    private TextField lessonField;

    @FXML
    private ListView<String> listView;

    private ObservableList<String> lessons;
    private LessonsStorage lessonsStorage;
    private WeekScheduleStorage weekScheduleStorage;

    @FXML
    private void initialize() {
        lessonsStorage = mainApp.getLessonsStorage();
        weekScheduleStorage = mainApp.getWeekScheduleStorage();
        lessons = lessonsStorage.getLessons();
        listView.setItems(lessons);
    }

    /**
     * Добавить предметы и сохранить в файле.
     */
    @FXML
    public void onAddBtn() {
        String lesson = lessonField.getText().trim();
        // если введённое значение не пустое и не содержится в списке уже существующих предметов
        if (!lesson.isEmpty() && !lessons.contains(lesson)) {
            lessons.add(lesson);
            lessonsStorage.write();
        }
        lessonField.setText("");
    }

    /**
     * Удалить из памяти и файла выбранные предметы.
     * Одновременно удаляются все пары из расписания, содержавшие удаляемые предметы.
     */
    @FXML
    public void onDeleteBtn() {
        ObservableList<String> selectedLessons = listView.getSelectionModel().getSelectedItems();

        // Удалить пары с удаляемыми предметами из расписания
        weekScheduleStorage.deletePairsByLessons(selectedLessons);

        // Удалить предметы из памяти и файла
        lessons.removeAll(selectedLessons);
        lessonsStorage.write();

        // Показать обновленное после удаления расписание текущего дня
        mainApp.showDaySchedule(LocalDate.now());
    }

}
