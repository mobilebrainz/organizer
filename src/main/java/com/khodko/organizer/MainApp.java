package com.khodko.organizer;

import com.khodko.organizer.controller.DayScheduleController;
import com.khodko.organizer.controller.PairEditDialogController;
import com.khodko.organizer.controller.RootController;
import com.khodko.organizer.model.Pair;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private LocalDate date = LocalDate.now();
    private WeekSchedule weekSchedule = new WeekSchedule();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Organizer");
        initRootLayout();

        //weekSchedule.write();

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

    public void savePair(Integer numPair, Pair pair) {
        // todo: save schedule in file
        StaticStorage.pairs.put(numPair, pair);

        showDaySchedule();
    }

    public void deletePair(Integer numPair) {
        // todo: delete from memory and save schedule in file
        StaticStorage.pairs.remove(numPair);

        showDaySchedule();
    }

    public void showPairEditDialog(Pair pair, Integer numPair) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/PairEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактировать пары");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            PairEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.init(this, pair, numPair);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddLessonsDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/AddLessonsDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Добавить предметы");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Show the dialog and wait until the user closes it
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

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Добавить преподавателей");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Show the dialog and wait until the user closes it
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

    public static void main(String[] args) {
        launch(args);
    }
}