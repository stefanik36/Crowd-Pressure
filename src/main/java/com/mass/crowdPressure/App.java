package com.mass.crowdPressure;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class App extends Application{
	static CrowdSimulationGUI gui = new CrowdSimulationGUI("CrowdPressureSym",2, 1000, 1000);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
	    gui.setEngine(Initializer.createEngine());
		gui.initialize(stage);
//		gui.start();


	}
}
