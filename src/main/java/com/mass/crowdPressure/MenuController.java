package com.mass.crowdPressure;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;


public class MenuController {

    @FXML
    private Canvas canvas;

    @FXML
    private Button button;

    @FXML
    public void initialize(){
    }

    @FXML
    public void pauseStartButton(ActionEvent event){
        System.out.print("Dupa");
    }
}
