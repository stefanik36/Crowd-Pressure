package com.agh.cp;

import java.util.ArrayList;
import java.util.List;

import com.agh.cp.builders.MapFactory;
import com.agh.cp.builders.PedestriansFactory;
import com.agh.cp.exceptions.AngleOutOfRangeException;
import com.agh.cp.model.Environment;
import com.agh.cp.model.map.Map;
import com.agh.cp.model.pedestrian.Pedestrian;
import org.junit.Test;

import com.app.COD;
import com.app.CODFactory;

public class SymTest {

	private final static COD cod = CODFactory.setLevelOfDepression(4);
	private static final int STEPS = 10;

	@Test
	public void test() throws AngleOutOfRangeException {
		// create map
		MapFactory mapFactory = new MapFactory();
		Map map = mapFactory.getMap(Symulation.SYM_P1_W1);
		List<Pedestrian> pedestrians = new ArrayList<>();
		Environment environment = new Environment(pedestrians, map);
		PedestriansFactory pedestriansBuilder = new PedestriansFactory();
		pedestriansBuilder.addPedestrians(environment, Symulation.SYM_P1_W1);
		Engine engine = new Engine(environment);
		int i = STEPS;
		while (i > 0) {
			engine.nextState();
			i--;
		}
	}

	@Test
	public void test2() throws AngleOutOfRangeException {
		// create map
		MapFactory mapFactory = new MapFactory();
		Map map = mapFactory.getMap(Symulation.SYM_P1_W2);
		List<Pedestrian> pedestrians = new ArrayList<>();
		Environment environment = new Environment(pedestrians, map);
		PedestriansFactory pedestriansBuilder = new PedestriansFactory();
		pedestriansBuilder.addPedestrians(environment, Symulation.SYM_P1_W2);
		Engine engine = new Engine(environment);
		int i = STEPS;
		while (i > 0) {
			engine.nextState();
			i--;
		}
	}

}
