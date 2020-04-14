package com.khodko.organizer.controller;


import com.khodko.organizer.storage.TeachersStorage;
import com.khodko.organizer.storage.ScheduleStorage;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.time.LocalDate;

import static com.khodko.organizer.MainApp.mainApp;


public class AddTeachersDialogController {

    @FXML
    private TextField teacherField;

    @FXML
    private ListView<String> listView;

    private ObservableList<String> teachers;
    private TeachersStorage teachersStorage;
    private ScheduleStorage scheduleStorage;

    @FXML
    private void initialize() {
        teachersStorage = mainApp.getTeachersStorage();
        scheduleStorage = mainApp.getScheduleStorage();
        teachers = mainApp.getTeachersStorage().getTeachers();
        listView.setItems(teachers);
    }

    /**
     * Добавить учителей и сохранить в файле.
     */
    @FXML
    public void onAddBtn() {
        String teacher = teacherField.getText().trim();
        // если введённое значение не пустое и не содержится в списке уже существующих учителей
        if (!teacher.isEmpty() && !teachers.contains(teacher)) {
            teachers.add(teacher);
            teachersStorage.write();
        }
        teacherField.setText("");
    }

    /**
     * Удалить из памяти и файла выбранных учителей.
     * Одновременно удаляются все пары из расписания, содержавшие удаляемых учителей.
     */
    @FXML
    public void onDeleteBtn() {
        ObservableList<String> selectedTeachers = listView.getSelectionModel().getSelectedItems();

        // Удалить пары с удаляемыми предметами из расписания
        scheduleStorage.deletePairsByTeachers(selectedTeachers);

        // Удалить преподавателей из памяти и файла
        teachers.removeAll(selectedTeachers);
        teachersStorage.write();

        // Показать обновленное после удаления расписание текущего дня
        mainApp.showDaySchedule(LocalDate.now());
    }

}
