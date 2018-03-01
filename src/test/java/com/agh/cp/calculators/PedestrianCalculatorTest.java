package com.agh.cp.calculators;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.agh.cp.exceptions.AngleOutOfRangeException;
import com.agh.cp.model.DirectionInfo;
import com.agh.cp.model.Environment;
import com.agh.cp.model.map.Map;
import com.agh.cp.model.map.StraightWall;
import com.agh.cp.model.pedestrian.Pedestrian;
import com.agh.cp.model.pedestrian.PedestrianInformation;
import com.agh.cp.model.pedestrian.StaticInformation;
import com.agh.cp.model.pedestrian.VariableInformation;
import org.junit.Test;

import com.app.COD;
import com.app.CODFactory;
import com.agh.cp.model.Position;

public class PedestrianCalculatorTest {
	private final static COD cod = CODFactory.setLevelOfDepression(2);

	@Test
	public void getDestinationDistanceFunctionValuesTest() throws AngleOutOfRangeException {

		// List<Pedestrian> pedestrians = new ArrayList<>();
		// Environment environment = new Environment(pedestrians, null);
		// pedestrians.addAll(Arrays.asList(new Pedestrian(
		// new PedestrianInformation(1, 640, 1, 1, 1, 1, 1, new Position(1, 1), new
		// Position(11, 8)), environment),
		// new Pedestrian(
		// new PedestrianInformation(2, 640, 1, 1, 1, 1, 1, new Position(1, 1), new
		// Position(7, 15)),
		// environment)));
		//
		//
		//
		// PedestrianInformation pedestrianInformation = new PedestrianInformation(0, 1,
		// 1, 0.24, 10, 1, 0.25,
		// new Position(14, 11), new Position(6, 5));

		List<Pedestrian> pedestrians = new ArrayList<>();
		Environment environment = new Environment(pedestrians, new Map(new ArrayList<>()));
		StaticInformation staticInformation1 = new StaticInformation(1, 640, 1, 1, 1, 1);
		VariableInformation variableInformation1 = new VariableInformation(1, new Position(1, 1), new Position(11, 8));
		StaticInformation staticInformation2 = new StaticInformation(1, 640, 1, 1, 1, 1);
		VariableInformation variableInformation2 = new VariableInformation(1, new Position(1, 1), new Position(7, 15));
		pedestrians.addAll(Arrays.asList(
				new Pedestrian(new PedestrianInformation(staticInformation1, variableInformation1), environment),
				new Pedestrian(new PedestrianInformation(staticInformation2, variableInformation2), environment)));

		StaticInformation staticInformation0 = new StaticInformation(0, 1, 1, 0.24, 10, 1);
		VariableInformation variableInformation0 = new VariableInformation(0.25, new Position(14, 11),
				new Position(6, 5));
		PedestrianInformation main = new PedestrianInformation(staticInformation0, variableInformation0);

		PedestrianCalculator pc = new PedestrianCalculator(main, environment);

		List<DirectionInfo> ddfv = pc.getDestinationDistanceFunctionValues();
		// cod.i(ddfv);
	}

	@Test
	public void getDesireDirectionTest() throws AngleOutOfRangeException {

		// List<Pedestrian> pedestrians = new ArrayList<>();
		// Environment environment = new Environment(pedestrians, null);
		// pedestrians.addAll(Arrays.asList(new Pedestrian(
		// new PedestrianInformation(1, 640, 1, 1, 1, 1, 1, new Position(1, 1), new
		// Position(11, 8)), environment),
		// new Pedestrian(
		// new PedestrianInformation(2, 640, 1, 1, 1, 1, 1, new Position(1, 1), new
		// Position(7, 15)),
		// environment)));
		//
		// PedestrianInformation pedestrianInformation = new PedestrianInformation(0, 1,
		// 1, 0.24, 10, 1, 0.25,
		// new Position(14, 17), new Position(6, 5));

		List<Pedestrian> pedestrians = new ArrayList<>();
		Environment environment = new Environment(pedestrians, new Map(new ArrayList<>()));
		StaticInformation staticInformation1 = new StaticInformation(1, 640, 1, 1, 1, 1);
		VariableInformation variableInformation1 = new VariableInformation(1, new Position(1, 1), new Position(11, 8));
		StaticInformation staticInformation2 = new StaticInformation(1, 640, 1, 1, 1, 1);
		VariableInformation variableInformation2 = new VariableInformation(1, new Position(1, 1), new Position(7, 15));
		pedestrians.addAll(Arrays.asList(
				new Pedestrian(new PedestrianInformation(staticInformation1, variableInformation1), environment),
				new Pedestrian(new PedestrianInformation(staticInformation2, variableInformation2), environment)));

		StaticInformation staticInformation0 = new StaticInformation(0, 1, 1, 0.24, 10, 1);
		VariableInformation variableInformation0 = new VariableInformation(0.25, new Position(14, 17),
				new Position(6, 5));
		PedestrianInformation main = new PedestrianInformation(staticInformation0, variableInformation0);

		// cod.i(pedestrianInformation);
		PedestrianCalculator pc = new PedestrianCalculator(main, environment);

		DirectionInfo dd = pc.getDirectionInfo();
		cod.i(dd);
		assertEquals(0.310, dd.getAlpha(), 0.01);

	}

	@Test
	public void getDesireDirectionTest2() throws AngleOutOfRangeException {

		List<Pedestrian> pedestrians = new ArrayList<>();
		Environment environment = new Environment(pedestrians, new Map(new ArrayList<>()));
		StaticInformation staticInformation1 = new StaticInformation(1, 640, 1, 1, 1, 1);
		VariableInformation variableInformation1 = new VariableInformation(1, new Position(1, 1), new Position(11, 8));
		StaticInformation staticInformation2 = new StaticInformation(1, 640, 1, 1, 1, 1);
		VariableInformation variableInformation2 = new VariableInformation(1, new Position(1, 1), new Position(7, 15));
		pedestrians.addAll(Arrays.asList(
				new Pedestrian(new PedestrianInformation(staticInformation1, variableInformation1), environment),
				new Pedestrian(new PedestrianInformation(staticInformation2, variableInformation2), environment)));

		StaticInformation staticInformation0 = new StaticInformation(0, 1, 1, 0.24, 10, 1);
		VariableInformation variableInformation0 = new VariableInformation(0.25, new Position(14, 11),
				new Position(6, 5));
		PedestrianInformation main = new PedestrianInformation(staticInformation0, variableInformation0);

		// cod.i(pedestrianInformation);
		PedestrianCalculator pc = new PedestrianCalculator(main, environment);

		DirectionInfo dd = pc.getDirectionInfo();
		// cod.i(dd);

	}

	@Test
	public void getDesireDirectionTest3() throws AngleOutOfRangeException {

		List<Pedestrian> pedestrians = new ArrayList<>();
		Environment environment = new Environment(pedestrians,
				new Map(Arrays.asList(new StraightWall(new Position(5, 2), new Position(2.5, 4.5)))));

		StaticInformation staticInformation0 = new StaticInformation(0, 640, 5, 0.4, 10, 1);
		VariableInformation variableInformation0 = new VariableInformation(0.20, new Position(5, 5),
				new Position(0, 0));
		PedestrianInformation main = new PedestrianInformation(staticInformation0, variableInformation0);

		// cod.i(pedestrianInformation);
		PedestrianCalculator pc = new PedestrianCalculator(main, environment);

		DirectionInfo dd = pc.getDirectionInfo();
		// cod.i(dd);

	}

}
