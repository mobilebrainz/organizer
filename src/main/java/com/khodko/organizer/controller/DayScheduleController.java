package com.khodko.organizer.controller;

import com.khodko.organizer.MainApp;
import com.khodko.organizer.model.Pair;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

import static com.khodko.organizer.MainApp.mainApp;


public class DayScheduleController {

    @FXML
    public Button addPairButton;

    @FXML
    private VBox pairVBox;

    @FXML
    private Label dataLabel;

    private List<Pair> daySchedule;
    private boolean isEdit;

    public void init(List<Pair> daySchedule, String dataString, boolean isEdit) {
        this.daySchedule = daySchedule;
        this.isEdit = isEdit;

        addPairButton.setVisible(isEdit);

        dataLabel.setText(dataString);
        showPairs();
    }

    public void showPairs() {
        for (Pair pair : daySchedule) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("/fxml/PairLayout.fxml"));
                AnchorPane pairLayout = loader.load();
                pairVBox.getChildren().add(pairLayout);

                PairController controller = loader.getController();
                controller.setMainApp(pair, isEdit);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onAddPairBtn() {
        mainApp.showPairEditDialog(null);
    }
}
