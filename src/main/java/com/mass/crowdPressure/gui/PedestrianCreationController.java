package com.mass.crowdPressure.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PedestrianCreationController implements Initializable {

    @FXML Pane pane;
    @FXML Button exitButton;

    @FXML
    private void ExitAction(){
        ((Stage)pane.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        exitButton.setDefaultButton(true);
    }
}
