package com.mass.crowdPressure;

import com.mass.crowdPressure.builders.MapBuilder;
import com.mass.crowdPressure.builders.PedestriansBuilder;
import com.mass.crowdPressure.model.Environment;

public class Initializer {

	private static final int INIT_NO_PEDESTRIANS = 10;

	public void create() {
		// create pedestrians
		PedestriansBuilder pedestriansBuilder = new PedestriansBuilder();

		// create map
		MapBuilder mapBuilder = new MapBuilder();
		// create engine

		Engine engine = new Engine(
				new Environment(pedestriansBuilder.getPedestrians(INIT_NO_PEDESTRIANS), mapBuilder.getMap()));
		engine.start();

	}

}
