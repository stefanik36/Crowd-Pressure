package com.agh.cp;

import java.util.ArrayList;
import java.util.List;

import com.agh.cp.builders.MapFactory;
import com.agh.cp.builders.PedestriansFactory;
import com.agh.cp.model.Environment;
import com.agh.cp.model.map.Map;
import com.agh.cp.model.pedestrian.Pedestrian;

public class Initializer {


	static public Engine createEngine(Symulation sym, PedestriansFactory pedestriansFactory) {

		// create map
		MapFactory mapFactory = new MapFactory();
		Map map = mapFactory.getMap(sym);

		
		List<Pedestrian> pedestrians = new ArrayList<>();
		
		Environment environment = new Environment(pedestrians, map);
		
		// create pedestrians
		pedestriansFactory.addPedestrians(environment,sym);

		return new Engine(environment);
	}

}
