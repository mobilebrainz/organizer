package com.khodko.organizer;

import com.khodko.organizer.controller.AddLessonsDialogController;
import com.khodko.organizer.controller.AddTeachersDialogController;
import com.khodko.organizer.controller.DayScheduleController;
import com.khodko.organizer.controller.PairEditDialogController;
import com.khodko.organizer.controller.RootController;
import com.khodko.organizer.controller.WeekScheduleController;
import com.khodko.organizer.model.Pair;
import com.khodko.organizer.storage.LessonsStorage;
import com.khodko.organizer.storage.TeachersStorage;
import com.khodko.organizer.storage.WeekScheduleStorage;

import java.io.IOException;
import java.time.LocalDate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private LocalDate date = LocalDate.now();
    private WeekScheduleStorage weekScheduleStorage = new WeekScheduleStorage();
    private LessonsStorage lessonsStorage = new LessonsStorage();
    private TeachersStorage teachersStorage = new TeachersStorage();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Organizer");
        initRootLayout();

        weekScheduleStorage.read();
        lessonsStorage.read();
        teachersStorage.read();

        showDaySchedule();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/RootLayout.fxml"));
            rootLayout = loader.load();
            RootController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDaySchedule() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/DayScheduleLayout.fxml"));
            AnchorPane daySchedule = loader.load();

            rootLayout.setCenter(daySchedule);
            DayScheduleController controller = loader.getController();
            controller.init(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showWeekSchedule(String lesson) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/WeekScheduleLayout.fxml"));
            AnchorPane daySchedule = loader.load();

            rootLayout.setCenter(daySchedule);
            WeekScheduleController controller = loader.getController();
            controller.init(this, lesson);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPairEditDialog(Pair pair) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/PairEditDialog.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактировать пары");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            PairEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.init(this, pair);

            dialogStage.showAndWait();

            showDaySchedule();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddLessonsDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/AddLessonsDialog.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Добавить предметы");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddLessonsDialogController controller = loader.getController();
            controller.setMainApp(this);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddTeachersDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/AddTeachersDialog.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Добавить преподавателей");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddTeachersDialogController controller = loader.getController();
            controller.setMainApp(this);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public WeekScheduleStorage getWeekScheduleStorage() {
        return weekScheduleStorage;
    }

    public LessonsStorage getLessonsStorage() {
        return lessonsStorage;
    }

    public TeachersStorage getTeachersStorage() {
        return teachersStorage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}