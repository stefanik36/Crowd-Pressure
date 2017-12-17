package com.mass.crowdPressure.calculators;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.exceptions.AngleOutOfRangeException;
import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.Vector;
import com.mass.crowdPressure.model.pedestrian.PedestrianInformation;
import com.mass.crowdPressure.model.pedestrian.StaticInformation;
import com.mass.crowdPressure.model.pedestrian.VariableInformation;

public class PedestrianCalculatorGetNextPositionTest {
	private final static COD cod = CODFactory.setLevelOfDepression(2);

	@Test
	public void getNextPositionTestR() throws AngleOutOfRangeException {

		StaticInformation staticInformation0 = new StaticInformation(0, 1, 10, 0.1, 1, 1);
		VariableInformation variableInformation0 = new VariableInformation(0.25, new Position(1, 1),
				new Position(0, 0));
		PedestrianInformation main = new PedestrianInformation(staticInformation0, variableInformation0);

		main.getVariableInformation().setDesiredDirection(0);
		main.getVariableInformation().setDesiredSpeed(new Vector(0, 2));
		PedestrianCalculator pc = new PedestrianCalculator(main, null);
		Position result = pc.getNextPosition();
		assertEquals(2.0, result.getX(), 0.01);
		assertEquals(0.0, result.getY(), 0.01);
	}

	@Test
	public void getNextPositionTestL() throws AngleOutOfRangeException {
		StaticInformation staticInformation0 = new StaticInformation(0, 1, 10, 0.1, 1, 1);
		VariableInformation variableInformation0 = new VariableInformation(0.25, new Position(1, 1),
				new Position(0, 0));
		PedestrianInformation main = new PedestrianInformation(staticInformation0, variableInformation0);

		main.getVariableInformation().setDesiredDirection(1);
		main.getVariableInformation().setDesiredSpeed(new Vector(1, 2));
		PedestrianCalculator pc = new PedestrianCalculator(main, null);
		Position result = pc.getNextPosition();
		assertEquals(-2.0, result.getX(), 0.01);
		assertEquals(0.0, result.getY(), 0.01);
	}

	@Test
	public void getNextPositionTestTL() throws AngleOutOfRangeException {
		StaticInformation staticInformation0 = new StaticInformation(0, 1, 10, 0.1, 1, 1);
		VariableInformation variableInformation0 = new VariableInformation(0.25, new Position(1, 1),
				new Position(0, 0));
		PedestrianInformation main = new PedestrianInformation(staticInformation0, variableInformation0);

		main.getVariableInformation().setDesiredDirection(0.75);
//		main.getVariableInformation().setDesiredSpeed(5);
		main.getVariableInformation().setDesiredSpeed(new Vector(0.75, 5));
		PedestrianCalculator pc = new PedestrianCalculator(main, null);
		Position result = pc.getNextPosition();
		assertEquals(-3.535, result.getX(), 0.01);
		assertEquals(3.535, result.getY(), 0.01);
	}

	@Test
	public void getNextPositionTestT() throws AngleOutOfRangeException {
		StaticInformation staticInformation0 = new StaticInformation(0, 1, 10, 0.1, 1, 1);
		VariableInformation variableInformation0 = new VariableInformation(0.25, new Position(1, 1),
				new Position(0, 0));
		PedestrianInformation main = new PedestrianInformation(staticInformation0, variableInformation0);

		main.getVariableInformation().setDesiredDirection(0.5);
//		main.getVariableInformation().setDesiredSpeed(2);
		main.getVariableInformation().setDesiredSpeed(new Vector(0.5, 2));
		PedestrianCalculator pc = new PedestrianCalculator(main, null);
		Position result = pc.getNextPosition();
		assertEquals(0.0, result.getX(), 0.01);
		assertEquals(2.0, result.getY(), 0.01);
	}

	@Test
	public void getNextPositionTestTR() throws AngleOutOfRangeException {
		StaticInformation staticInformation0 = new StaticInformation(0, 1, 10, 0.1, 1, 1);
		VariableInformation variableInformation0 = new VariableInformation(0.25, new Position(1, 1),
				new Position(0, 0));
		PedestrianInformation main = new PedestrianInformation(staticInformation0, variableInformation0);

		main.getVariableInformation().setDesiredDirection(0.25);
//		main.getVariableInformation().setDesiredSpeed(5);
		main.getVariableInformation().setDesiredSpeed(new Vector(0.25, 5));
		PedestrianCalculator pc = new PedestrianCalculator(main, null);
		Position result = pc.getNextPosition();
		// cod.i(result);
		assertEquals(3.535, result.getX(), 0.01);
		assertEquals(3.535, result.getY(), 0.01);
	}

	@Test
	public void getNextPositionTestBL() throws AngleOutOfRangeException {
		StaticInformation staticInformation0 = new StaticInformation(0, 1, 10, 0.1, 1, 1);
		VariableInformation variableInformation0 = new VariableInformation(0.25, new Position(1, 1),
				new Position(0, 0));
		PedestrianInformation main = new PedestrianInformation(staticInformation0, variableInformation0);

		main.getVariableInformation().setDesiredDirection(1.25);
//		main.getVariableInformation().setDesiredSpeed(5);
		main.getVariableInformation().setDesiredSpeed(new Vector(1.25, 5));
		PedestrianCalculator pc = new PedestrianCalculator(main, null);
		Position result = pc.getNextPosition();
		assertEquals(-3.535, result.getX(), 0.01);
		assertEquals(-3.535, result.getY(), 0.01);
	}

	@Test
	public void getNextPositionTestB() throws AngleOutOfRangeException {
		StaticInformation staticInformation0 = new StaticInformation(0, 1, 10, 0.1, 1, 1);
		VariableInformation variableInformation0 = new VariableInformation(0.25, new Position(1, 1),
				new Position(0, 0));
		PedestrianInformation main = new PedestrianInformation(staticInformation0, variableInformation0);

		main.getVariableInformation().setDesiredDirection(1.5);
//		main.getVariableInformation().setDesiredSpeed(2);
		main.getVariableInformation().setDesiredSpeed(new Vector(1.5, 2));
		PedestrianCalculator pc = new PedestrianCalculator(main, null);
		Position result = pc.getNextPosition();
		// cod.i(result);
		assertEquals(0.0, result.getX(), 0.01);
		assertEquals(-2.0, result.getY(), 0.01);
	}

	@Test
	public void getNextPositionTestBR() throws AngleOutOfRangeException {
		StaticInformation staticInformation0 = new StaticInformation(0, 1, 10, 0.1, 1, 1);
		VariableInformation variableInformation0 = new VariableInformation(0.25, new Position(1, 1),
				new Position(0, 0));
		PedestrianInformation main = new PedestrianInformation(staticInformation0, variableInformation0);

		main.getVariableInformation().setDesiredDirection(1.75);
//		main.getVariableInformation().setDesiredSpeed(5);
		main.getVariableInformation().setDesiredSpeed(new Vector(1.75, 5));
		PedestrianCalculator pc = new PedestrianCalculator(main, null);
		Position result = pc.getNextPosition();
		// cod.i(result);
		assertEquals(3.535, result.getX(), 0.01);
		assertEquals(-3.535, result.getY(), 0.01);
	}
}
