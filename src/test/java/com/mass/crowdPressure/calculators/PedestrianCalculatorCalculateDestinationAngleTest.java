package com.mass.crowdPressure.calculators;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.exceptions.AngleOutOfRangeException;
import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.pedestrian.PedestrianInformation;

public class PedestrianCalculatorCalculateDestinationAngleTest {
	private final static COD cod = CODFactory.setLevelOfDepression(2);

	@Test
	public void calculateDestinationAngleTest() {
		double result = PedestrianCalculator.calculateDestinationAngle.apply(new Position(6, 5), new Position(14, 11));
		assertEquals(0.204, result, 0.01);
	}

	@Test
	public void calculateDestinationAngleTestRT() {
		double result = PedestrianCalculator.calculateDestinationAngle.apply(new Position(2, 2), new Position(3, 3));
		assertEquals(0.25, result, 0.01);
	}

	@Test
	public void calculateDestinationAngleTestT() {
		double result = PedestrianCalculator.calculateDestinationAngle.apply(new Position(2, 2), new Position(2, 3));
		assertEquals(0.5, result, 0.01);
	}

	@Test
	public void calculateDestinationAngleTestLT() {
		double result = PedestrianCalculator.calculateDestinationAngle.apply(new Position(2, 2), new Position(1, 3));
		assertEquals(0.75, result, 0.01);
	}

	@Test
	public void calculateDestinationAngleTestL() {
		double result = PedestrianCalculator.calculateDestinationAngle.apply(new Position(2, 2), new Position(1, 2));
		assertEquals(1.0, result, 0.01);
	}

	@Test
	public void calculateDestinationAngleTestR() {
		double result = PedestrianCalculator.calculateDestinationAngle.apply(new Position(2, 2), new Position(3, 2));
		assertEquals(0.0, result, 0.01);
	}

	@Test
	public void calculateDestinationAngleTestLB() {
		double result = PedestrianCalculator.calculateDestinationAngle.apply(new Position(2, 2), new Position(1, 1));
		assertEquals(1.25, result, 0.01);
	}

	@Test
	public void calculateDestinationAngleTestB() {
		double result = PedestrianCalculator.calculateDestinationAngle.apply(new Position(2, 2), new Position(2, 1));
		assertEquals(1.5, result, 0.01);
	}

	@Test
	public void calculateDestinationAngleTestRB() {
		double result = PedestrianCalculator.calculateDestinationAngle.apply(new Position(2, 2), new Position(3, 1));
		assertEquals(1.75, result, 0.01);
	}

}
