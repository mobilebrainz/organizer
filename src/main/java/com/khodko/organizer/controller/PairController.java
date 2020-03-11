package com.khodko.organizer.controller;

import com.khodko.organizer.MainApp;
import com.khodko.organizer.model.Pair;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class PairController {

    @FXML
    private Label numPairLabel;

    @FXML
    private Label lessonLabel;

    @FXML
    private Label teacherLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label cabinetLabel;

    private MainApp mainApp;
    private Pair pair;
    private Integer numPair;

    public void init(MainApp mainApp, Pair pair, Integer numPair) {
        this.mainApp = mainApp;
        this.pair = pair;
        this.numPair = numPair;
        showPairDetails();
    }

    /**
     * Fills all text fields to show details about the pair.
     */
    private void showPairDetails() {
        if (pair != null) {
            numPairLabel.setText(numPair.toString());
            lessonLabel.setText(pair.getLesson());
            teacherLabel.setText(pair.getTeacher());
            typeLabel.setText(pair.getPairType());
            cabinetLabel.setText(pair.getCabinet());
        }
    }

    @FXML
    public void onEditPairBtn() {
        if (pair != null) {
            mainApp.showPairEditDialog(pair, numPair);
        }
    }

}
