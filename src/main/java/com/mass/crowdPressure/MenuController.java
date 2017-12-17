package com.mass.crowdPressure;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;


public class MenuController implements Initializable {

    private GraphicsContext gp;

    private Timeline simLoop;

    @FXML
    private Button pauseStartButton;

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

    }

    public void setGraphicContext(GraphicsContext graphicContext) {
        this.gp = graphicContext;
    }

    public void setSimLoop(Timeline simLoop) {
        this.simLoop = simLoop;
    }
}
