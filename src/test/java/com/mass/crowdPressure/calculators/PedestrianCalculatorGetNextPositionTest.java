package com.mass.crowdPressure.calculators;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.exceptions.AngleOutOfRangeException;
import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.pedestrian.PedestrianInformation;

public class PedestrianCalculatorGetNextPositionTest {
	private final static COD cod = CODFactory.setLevelOfDepression(2);

	@Test
	public void getNextPositionTestR() throws AngleOutOfRangeException {
		PedestrianInformation pedestrianInformation = new PedestrianInformation(0, 1, 10, 0.1, 1, 1, 0.25,
				new Position(1, 1), new Position(0, 0));
		pedestrianInformation.setDesiredDirection(0);
		pedestrianInformation.setDesiredSpeed(2);
		PedestrianCalculator pc = new PedestrianCalculator(pedestrianInformation, null);
		Position result = pc.getNextPosition();
		assertEquals(2.0, result.getX(), 0.01);
		assertEquals(0.0, result.getY(), 0.01);
	}

	@Test
	public void getNextPositionTestL() throws AngleOutOfRangeException {
		PedestrianInformation pedestrianInformation = new PedestrianInformation(0, 1, 10, 0.1, 1, 1, 0.25,
				new Position(1, 1), new Position(0, 0));
		pedestrianInformation.setDesiredDirection(1.0);
		pedestrianInformation.setDesiredSpeed(2);
		PedestrianCalculator pc = new PedestrianCalculator(pedestrianInformation, null);
		Position result = pc.getNextPosition();
//		cod.i(result);
		assertEquals(-2.0, result.getX(), 0.01);
		assertEquals(0.0, result.getY(), 0.01);
	}

	@Test
	public void getNextPositionTestTL() throws AngleOutOfRangeException {
		PedestrianInformation pedestrianInformation = new PedestrianInformation(0, 1, 10, 0.1, 1, 1, 0.25,
				new Position(1, 1), new Position(0, 0));
		pedestrianInformation.setDesiredDirection(0.75);
		pedestrianInformation.setDesiredSpeed(5);
		PedestrianCalculator pc = new PedestrianCalculator(pedestrianInformation, null);
		Position result = pc.getNextPosition();
		assertEquals(-3.535, result.getX(), 0.01);
		assertEquals(3.535, result.getY(), 0.01);
	}

	@Test
	public void getNextPositionTestT() throws AngleOutOfRangeException {
		PedestrianInformation pedestrianInformation = new PedestrianInformation(0, 1, 10, 0.1, 1, 1, 0.25,
				new Position(1, 1), new Position(0, 0));
		pedestrianInformation.setDesiredDirection(0.5);
		pedestrianInformation.setDesiredSpeed(2);
		PedestrianCalculator pc = new PedestrianCalculator(pedestrianInformation, null);
		Position result = pc.getNextPosition();
		assertEquals(0.0, result.getX(), 0.01);
		assertEquals(2.0, result.getY(), 0.01);
	}

	@Test
	public void getNextPositionTestTR() throws AngleOutOfRangeException {
		PedestrianInformation pedestrianInformation = new PedestrianInformation(0, 1, 10, 0.1, 1, 1, 0.25,
				new Position(1, 1), new Position(0, 0));
		pedestrianInformation.setDesiredDirection(0.25);
		pedestrianInformation.setDesiredSpeed(5);
		PedestrianCalculator pc = new PedestrianCalculator(pedestrianInformation, null);
		Position result = pc.getNextPosition();
		// cod.i(result);
		assertEquals(3.535, result.getX(), 0.01);
		assertEquals(3.535, result.getY(), 0.01);
	}
	
	
	
	
	@Test
	public void getNextPositionTestBL() throws AngleOutOfRangeException {
		PedestrianInformation pedestrianInformation = new PedestrianInformation(0, 1, 10, 0.1, 1, 1, 0.25,
				new Position(1, 1), new Position(0, 0));
		pedestrianInformation.setDesiredDirection(1.25);
		pedestrianInformation.setDesiredSpeed(5);
		PedestrianCalculator pc = new PedestrianCalculator(pedestrianInformation, null);
		Position result = pc.getNextPosition();
		assertEquals(-3.535, result.getX(), 0.01);
		assertEquals(-3.535, result.getY(), 0.01);
	}

	@Test
	public void getNextPositionTestB() throws AngleOutOfRangeException {
		PedestrianInformation pedestrianInformation = new PedestrianInformation(0, 1, 10, 0.1, 1, 1, 0.25,
				new Position(1, 1), new Position(0, 0));
		pedestrianInformation.setDesiredDirection(1.5);
		pedestrianInformation.setDesiredSpeed(2);
		PedestrianCalculator pc = new PedestrianCalculator(pedestrianInformation, null);
		Position result = pc.getNextPosition();
//		cod.i(result);
		assertEquals(0.0, result.getX(), 0.01);
		assertEquals(-2.0, result.getY(), 0.01);
	}

	@Test
	public void getNextPositionTestBR() throws AngleOutOfRangeException {
		PedestrianInformation pedestrianInformation = new PedestrianInformation(0, 1, 10, 0.1, 1, 1, 0.25,
				new Position(1, 1), new Position(0, 0));
		pedestrianInformation.setDesiredDirection(1.75);
		pedestrianInformation.setDesiredSpeed(5);
		PedestrianCalculator pc = new PedestrianCalculator(pedestrianInformation, null);
		Position result = pc.getNextPosition();
		// cod.i(result);
		assertEquals(3.535, result.getX(), 0.01);
		assertEquals(-3.535, result.getY(), 0.01);
	}
}
