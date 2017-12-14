package com.mass.crowdPressure;

import java.util.ArrayList;
import java.util.List;

import com.mass.crowdPressure.builders.MapBuilder;
import com.mass.crowdPressure.builders.PedestriansBuilder;
import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.map.Map;
import com.mass.crowdPressure.model.pedestrian.Pedestrian;

public class Initializer {

	private static final int INIT_NO_PEDESTRIANS = 10;

	public void create() {

		// create map
		MapBuilder mapBuilder = new MapBuilder();
		Map map = mapBuilder.getMap();

		
		List<Pedestrian> pedestrians = new ArrayList<>();
		
		Environment environment = new Environment(pedestrians, map);
		
		// create pedestrians
		PedestriansBuilder pedestriansBuilder = new PedestriansBuilder();
		pedestriansBuilder.addPedestrians(environment,INIT_NO_PEDESTRIANS);

		// create engine

		Engine engine = new Engine(environment);
		engine.start();

	}

}
