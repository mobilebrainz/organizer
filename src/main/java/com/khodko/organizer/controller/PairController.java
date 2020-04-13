package com.khodko.organizer.controller;

import com.khodko.organizer.model.Pair;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import static com.khodko.organizer.MainApp.mainApp;


public class PairController {

    @FXML
    private Label numPairLabel;

    @FXML
    public Label pairTime;

    @FXML
    private Label lessonLabel;

    @FXML
    private Label teacherLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label cabinetLabel;

    @FXML
    public ImageView editImageView;

    public static String[] pairTimes = {
            "8:00",
            "9:30",
            "11:00",
            "12:30",
            "14:00",
            "15:30",
            "17:00",
            "18:30",
            "20:00",
            "21:30"
    };

    private Pair pair;
    private boolean isEdit;

    public void setMainApp(Pair pair, boolean isEdit) {
        this.pair = pair;
        this.isEdit = isEdit;

        editImageView.setVisible(isEdit);

        showPairDetails();
    }

    private void showPairDetails() {
        numPairLabel.setText(pair.getNum().toString());
        pairTime.setText(pairTimes[pair.getNum() - 1]);
        lessonLabel.setText(pair.getLesson());
        teacherLabel.setText(pair.getTeacher());
        typeLabel.setText(pair.getPairType());
        cabinetLabel.setText(pair.getCabinet());
    }

    @FXML
    public void onEditPairBtn() {
        if (isEdit) {
            mainApp.showPairEditDialog(pair);
        }
    }

}
