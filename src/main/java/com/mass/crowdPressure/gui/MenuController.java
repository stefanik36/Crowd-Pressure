package com.mass.crowdPressure.gui;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;


public class MenuController implements Initializable {

    private Group root;

    private GraphicsContext gp;

    private Timeline simLoop;

    @FXML private Button pauseStartButton;
    @FXML private ComboBox<String> actionComboBox;

    @FXML
    private void chooseAction(){
        System.out.print(actionComboBox.getValue());

    }

    @FXML
    private void pauseStartSim() {
        switch (simLoop.getStatus()) {
            case RUNNING:
                simLoop.pause();
                break;
            case PAUSED:
            case STOPPED:
                simLoop.play();
                break;
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        actionComboBox.getItems().removeAll(actionComboBox.getItems());
        actionComboBox.getItems().addAll("Nothing", "Add wall", "Add Pedestrian");
        actionComboBox.getSelectionModel().select("Nothing");
    }

    public void setGraphicContext(GraphicsContext graphicContext) {
        this.gp = graphicContext;
    }

    public void setSimLoop(Timeline simLoop) {
        this.simLoop = simLoop;
    }

    public void setRoot(Group root) {
        this.root = root;
    }
}
