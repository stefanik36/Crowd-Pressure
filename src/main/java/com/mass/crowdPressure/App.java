package com.mass.crowdPressure;

import com.mass.crowdPressure.gui.CrowdSimulationGUI;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

	private static final int FPS = 60;
	private CrowdSimulationGUI gui = new CrowdSimulationGUI("CrowdPressureSym", FPS, 1000, 1000);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Symulation sym = Symulation.SYM_ROOM;
		gui.setEngine(Initializer.createEngine(sym));
		gui.initialize(stage);

	}
}
