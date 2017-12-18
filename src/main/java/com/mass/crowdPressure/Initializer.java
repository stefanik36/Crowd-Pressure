package com.mass.crowdPressure;

import java.util.ArrayList;
import java.util.List;

import com.mass.crowdPressure.builders.MapFactory;
import com.mass.crowdPressure.builders.PedestriansFactory;
import com.mass.crowdPressure.calculators.Configuration;
import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.map.Map;
import com.mass.crowdPressure.model.pedestrian.Pedestrian;

public class Initializer {


	static public Engine createEngine() {

		// create map
		MapFactory mapFactory = new MapFactory();
		Map map = mapFactory.getMap(Symulation.SYM_P1_W1);

		
		List<Pedestrian> pedestrians = new ArrayList<>();
		
		Environment environment = new Environment(pedestrians, map);
		
		// create pedestrians
		PedestriansFactory pedestriansBuilder = new PedestriansFactory();
		pedestriansBuilder.addPedestrians(environment,Symulation.SYM_P1_W1);

		return new Engine(environment);
	}

}
