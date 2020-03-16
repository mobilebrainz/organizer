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
import com.khodko.organizer.utils.DateUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private int weekDayOrdinal = 0;

    private WeekScheduleStorage weekScheduleStorage = new WeekScheduleStorage();
    private LessonsStorage lessonsStorage = new LessonsStorage();
    private TeachersStorage teachersStorage = new TeachersStorage();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Organizer");
        this.primaryStage.getIcons().add(new Image("/images/calendar.png"));
        initRootLayout();

        showDaySchedule(LocalDate.now());
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

    public void showEditDaySchedule(int weekDayOrdinal) {
        this.weekDayOrdinal = weekDayOrdinal;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/DayScheduleLayout.fxml"));
            AnchorPane dayScheduleLayout = loader.load();

            rootLayout.setCenter(dayScheduleLayout);

            List<Pair> daySchedule = weekScheduleStorage.getWeekSchedule().get(weekDayOrdinal);

            DayScheduleController controller = loader.getController();
            controller.setMainApp(this, daySchedule, DateUtil.weekDays[weekDayOrdinal], true);

            primaryStage.sizeToScene();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDaySchedule(LocalDate date) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/DayScheduleLayout.fxml"));
            AnchorPane dayScheduleLayout = loader.load();

            rootLayout.setCenter(dayScheduleLayout);

            int weekDay = date.getDayOfWeek().ordinal();
            List<Pair> daySchedule = weekScheduleStorage.getWeekSchedule().get(weekDay);
            String dataString = DateUtil.getDateString(date);

            DayScheduleController controller = loader.getController();
            controller.setMainApp(this, daySchedule, dataString, false);

            primaryStage.sizeToScene();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showWeekSchedule(String lesson) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/WeekScheduleLayout.fxml"));
            AnchorPane weekScheduleLayout = loader.load();

            rootLayout.setCenter(weekScheduleLayout);
            WeekScheduleController controller = loader.getController();
            controller.setMainApp(this, lesson);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPairEditDialog(Pair pair) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/PairEditDialog.fxml"));
            AnchorPane dialogLayout = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактировать пару");
            dialogStage.getIcons().add(new Image("/images/edit.png"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(dialogLayout);
            dialogStage.setScene(scene);

            PairEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this, pair);

            dialogStage.showAndWait();

            showEditDaySchedule(weekDayOrdinal);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddLessonsDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/AddLessonsDialog.fxml"));
            AnchorPane dialogLayout = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Добавить предметы");
            dialogStage.getIcons().add(new Image("/images/edit.png"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(dialogLayout);
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
            AnchorPane dialogLayout = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Добавить преподавателей");
            dialogStage.getIcons().add(new Image("/images/edit.png"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(dialogLayout);
            dialogStage.setScene(scene);

            AddTeachersDialogController controller = loader.getController();
            controller.setMainApp(this);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
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

    public int getWeekDayOrdinal() {
        return weekDayOrdinal;
    }

    public static void main(String[] args) {
        launch(args);
    }
}