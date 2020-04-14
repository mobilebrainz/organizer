package com.khodko.organizer.controller;

import com.khodko.organizer.MainApp;
import com.khodko.organizer.model.Pair;
import com.khodko.organizer.utils.DateUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static com.khodko.organizer.MainApp.mainApp;


public class WeekScheduleController {

    @FXML
    public VBox dayVBox;

    private List<Pair> weekSchedule;

    public void init(String lesson) {
        weekSchedule = mainApp.getWeekScheduleStorage().getScheduleByLesson(lesson);
        showSchedule();
    }

    private List<Pair> getDaySchedule(int weekDay) {
        List<Pair> daySchedule = new ArrayList<>();
        for (Pair pair : weekSchedule) {
            if (pair.getWeekDay() == weekDay) {
                daySchedule.add(pair);
            }
        }
        return daySchedule;
    }

    private void showSchedule() {
        for (int i = 0; i < 7; i++) {
            List<Pair> daySchedule = getDaySchedule(i);
            if (!daySchedule.isEmpty()) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MainApp.class.getResource("/fxml/DayScheduleLayout.fxml"));
                    AnchorPane pairLayout = loader.load();
                    dayVBox.getChildren().add(pairLayout);

                    DayScheduleController controller = loader.getController();
                    String dateString = DateUtil.weekDays[i];
                    controller.init(daySchedule, dateString, false);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    public void onSaveScheduleBtn() {
        FileChooser fileChooser = new FileChooser();
        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Txt files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".txt")) {
                file = new File(file.getPath() + ".txt");
            }
            saveFile(file);
        }
    }

    private void saveFile(File file) {
        try {
            StringBuilder stringBuilder = new StringBuilder("");
            for (int i = 0; i < 7; i++) {
                List<Pair> daySchedule = getDaySchedule(i);
                if (!daySchedule.isEmpty()) {
                    stringBuilder.append(DateUtil.weekDays[i]).append("\n");
                    for (Pair pair : daySchedule) {
                        stringBuilder.append("\t")
                                .append(pair.getNum())
                                .append(" (").append(PairController.pairTimes[pair.getNum()]).append("): ")
                                .append(pair.getLesson())
                                .append(" (").append(pair.getPairType()).append("), ")
                                .append(pair.getTeacher()).append(", ")
                                .append("каб: ").append(pair.getCabinet())
                                .append("\n");
                    }
                }
            }
            byte[] strToBytes = stringBuilder.toString().getBytes();
            Files.write(file.toPath(), strToBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
