package com.agh.cp.model.pedestrian;

import static org.junit.Assert.*;

import java.util.ArrayList;

import com.agh.cp.exceptions.AngleOutOfRangeException;
import com.agh.cp.model.Environment;
import com.agh.cp.model.map.Map;
import org.junit.Test;

import com.app.COD;
import com.app.CODFactory;
import com.agh.cp.model.Position;

public class PedestrianTest {
	private final static COD cod = CODFactory.setLevelOfDepression(10);

	@Test
	public void prepareNextStepTest() throws AngleOutOfRangeException {
		StaticInformation staticInformation0 = new StaticInformation(0, 1, 5, 0.24, 10, 1);
		VariableInformation variableInformation0 = new VariableInformation(0.2, new Position(5, 5), new Position(0, 0));
		PedestrianInformation main = new PedestrianInformation(staticInformation0, variableInformation0);

		Pedestrian pedestrian = new Pedestrian(main, new Environment(new ArrayList<>(), new Map(new ArrayList<>())));
		
		pedestrian.prepareNextStep();
		assertEquals(3.53, pedestrian.getPedestrianInformation().getVariableInformation().getNextPosition().getX(),0.01);
		assertEquals(3.53, pedestrian.getPedestrianInformation().getVariableInformation().getNextPosition().getY(),0.01);
	}
	
	@Test
	public void prepareNextStepTest2() throws AngleOutOfRangeException {
		StaticInformation staticInformation0 = new StaticInformation(0, 1, 5, 0.24, 10, 1);
		VariableInformation variableInformation0 = new VariableInformation(0.8, new Position(5, 5), new Position(0, 0));
		PedestrianInformation main = new PedestrianInformation(staticInformation0, variableInformation0);

		Pedestrian pedestrian = new Pedestrian(main, new Environment(new ArrayList<>(), new Map(new ArrayList<>())));
		cod.i(pedestrian.getPedestrianInformation());
		for (int i = 0; i < 5; i++) {
			pedestrian.prepareNextStep();
			cod.i(pedestrian.getPedestrianInformation());
		}
	}

	@Test
	public void nextStepTest() throws AngleOutOfRangeException {
		StaticInformation staticInformation0 = new StaticInformation(0, 1, 5, 0.44, 10, 1);
		VariableInformation variableInformation0 = new VariableInformation(0.2, new Position(5, 5), new Position(0, 0));
		PedestrianInformation main = new PedestrianInformation(staticInformation0, variableInformation0);

		Pedestrian pedestrian = new Pedestrian(main, new Environment(new ArrayList<>(), new Map(new ArrayList<>())));
//		cod.i(pedestrian.getPedestrianInformation());
		for (int i = 0; i < 10; i++) {
			pedestrian.prepareNextStep();
			pedestrian.nextStep();
			// cod.i(pedestrian.getPedestrianInformation());
//			System.out.println(pedestrian.getPedestrianInformation().getVariableInformation().getDesiredDirection()
//					+ ":(" + pedestrian.getPedestrianInformation().getVariableInformation().getPosition().getX() + ","
//					+ pedestrian.getPedestrianInformation().getVariableInformation().getPosition().getX() + ")");
		}
	}
	
	@Test
	public void nextStepTest2() throws AngleOutOfRangeException {
		StaticInformation staticInformation0 = new StaticInformation(0, 1, 5, 0.44, 10, 1);
		VariableInformation variableInformation0 = new VariableInformation(1.75, new Position(5, 5), new Position(0, 0));
		PedestrianInformation main = new PedestrianInformation(staticInformation0, variableInformation0);

		Pedestrian pedestrian = new Pedestrian(main, new Environment(new ArrayList<>(), new Map(new ArrayList<>())));
	
//		cod.i(pedestrian.getPedestrianInformation());
		for (int i = 0; i < 10; i++) {
			pedestrian.prepareNextStep();
			pedestrian.nextStep();
//			// cod.i(pedestrian.getPedestrianInformation());
//			System.out.println(pedestrian.getPedestrianInformation().getVariableInformation().getDesiredDirection()
//					+ ":(" + pedestrian.getPedestrianInformation().getVariableInformation().getPosition().getX() + ","
//					+ pedestrian.getPedestrianInformation().getVariableInformation().getPosition().getX() + ")");
		}
	}
}
