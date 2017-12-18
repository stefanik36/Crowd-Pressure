package com.mass.crowdPressure;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.builders.MapFactory;
import com.mass.crowdPressure.builders.PedestriansFactory;
import com.mass.crowdPressure.calculators.Configuration;
import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.map.Map;
import com.mass.crowdPressure.model.pedestrian.Pedestrian;

public class SymTest {

	private final static COD cod = CODFactory.setLevelOfDepression(4);
	@Test
	public void test() {

		// create map
		MapFactory mapFactory = new MapFactory();
		Map map = mapFactory.getMap(Symulation.SYM_P1_W1);

		List<Pedestrian> pedestrians = new ArrayList<>();

		Environment environment = new Environment(pedestrians, map);
		PedestriansFactory pedestriansBuilder = new PedestriansFactory();
		pedestriansBuilder.addPedestrians(environment, Symulation.SYM_P1_W1);
		Engine engine = new Engine(environment);
		engine.start();
	}
	@Test
	public void test2() {

		// create map
		MapFactory mapFactory = new MapFactory();
		Map map = mapFactory.getMap(Symulation.SYM_P1_W2);

		List<Pedestrian> pedestrians = new ArrayList<>();

		Environment environment = new Environment(pedestrians, map);
		PedestriansFactory pedestriansBuilder = new PedestriansFactory();
		pedestriansBuilder.addPedestrians(environment, Symulation.SYM_P1_W2);
		Engine engine = new Engine(environment);
		engine.start();
	}

}
