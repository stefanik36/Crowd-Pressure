package com.mass.crowdPressure.calculators;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.exceptions.AngleOutOfRangeException;
import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.pedestrian.PedestrianInformation;

public class GeometricCalculatorCalculateDestinationAngleTest {
	private final static COD cod = CODFactory.setLevelOfDepression(2);

	@Test
	public void calculateDestinationAngleTest() {
		double result = GemoetricCalculator.calculateDestinationAngle.apply(new Position(6, 5), new Position(14, 11));
		assertEquals(0.204, result, 0.01);
	}
	
	@Test
	public void calculateDestinationAngleTest2() {
		double result = GemoetricCalculator.calculateDestinationAngle.apply(new Position(-4.960573506572389, 0.6266661678215222), new Position(5, 5));
//		cod.i(result);
		assertEquals(0.131, result, 0.01);
	}

	@Test
	public void calculateDestinationAngleTestRT() {
		double result = GemoetricCalculator.calculateDestinationAngle.apply(new Position(2, 2), new Position(3, 3));
		assertEquals(0.25, result, 0.01);
	}

	@Test
	public void calculateDestinationAngleTestT() {
		double result = GemoetricCalculator.calculateDestinationAngle.apply(new Position(2, 2), new Position(2, 3));
		assertEquals(0.5, result, 0.01);
	}

	@Test
	public void calculateDestinationAngleTestLT() {
		double result = GemoetricCalculator.calculateDestinationAngle.apply(new Position(2, 2), new Position(1, 3));
		assertEquals(0.75, result, 0.01);
	}

	@Test
	public void calculateDestinationAngleTestL() {
		double result = GemoetricCalculator.calculateDestinationAngle.apply(new Position(2, 2), new Position(1, 2));
		assertEquals(1.0, result, 0.01);
	}

	@Test
	public void calculateDestinationAngleTestR() {
		double result = GemoetricCalculator.calculateDestinationAngle.apply(new Position(2, 2), new Position(3, 2));
		assertEquals(0.0, result, 0.01);
	}

	@Test
	public void calculateDestinationAngleTestLB() {
		double result = GemoetricCalculator.calculateDestinationAngle.apply(new Position(2, 2), new Position(1, 1));
		assertEquals(1.25, result, 0.01);
	}

	@Test
	public void calculateDestinationAngleTestB() {
		double result = GemoetricCalculator.calculateDestinationAngle.apply(new Position(2, 2), new Position(2, 1));
		assertEquals(1.5, result, 0.01);
	}

	@Test
	public void calculateDestinationAngleTestRB() {
		double result = GemoetricCalculator.calculateDestinationAngle.apply(new Position(2, 2), new Position(3, 1));
		assertEquals(1.75, result, 0.01);
	}

}
