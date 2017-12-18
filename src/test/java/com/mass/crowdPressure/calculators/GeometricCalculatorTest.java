package com.mass.crowdPressure.calculators;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.calculators.figures.LinePointAngle;
import com.mass.crowdPressure.calculators.figures.LineTwoPoints;
import com.mass.crowdPressure.calculators.figures.Vector;
import com.mass.crowdPressure.calculators.figures.VectorXY;
import com.mass.crowdPressure.model.Position;

public class GeometricCalculatorTest {
	private final static COD cod = CODFactory.setLevelOfDepression(2);

	@Test
	public void distanceStraightPointTest() {
		Position p = new Position(0, 0);
		LineTwoPoints l = new LineTwoPoints(new Position(5, 0), new Position(0, 5));
		Optional<Double> result = GeometricCalculator.vectorStraightPoint.apply(p, l);
		assertEquals(3.53, result.get(), 0.1);
	}
	
	@Test
	public void distanceStraightPointTest2() {
		Position p = new Position(0, 0);
		LineTwoPoints l = new LineTwoPoints(new Position(0, 1), new Position(0, 5));
		Optional<Double> result = GeometricCalculator.vectorStraightPoint.apply(p, l);
		assertEquals(false, result.isPresent());
	}
	@Test
	public void distanceStraightPointTest3() {
		Position p = new Position(0, 0);
		LineTwoPoints l = new LineTwoPoints(new Position(0, 1), new Position(1, 5));
		Optional<Double> result = GeometricCalculator.vectorStraightPoint.apply(p, l);
		assertEquals(false, result.isPresent());
	}
	@Test
	public void changeVectorTest() {
		Vector v = GeometricCalculator.changeVector(new VectorXY(-5, 5));
		assertEquals(0.75, v.getAngle(), 0.001);
		assertEquals(7.07, v.getValue(), 0.1);
	}

	@Test
	public void distanceTest() {

		Position positionA = new Position(9, 8);
		Position positionB = new Position(5, 5);
		double result = GeometricCalculator.distance.apply(positionA, positionB);

		// cod.i(result);
		assertEquals(5.0, result, 0.001);
	}

	@Test
	public void distanceTest2() {

		Position positionA = new Position(9, 8);
		Position positionB = new Position(6, 5);
		double result = GeometricCalculator.distance.apply(positionA, positionB);

		// cod.i(result);
		assertEquals(4.24, result, 0.1);
	}

	@Test
	public void crossPointTwoLinesTest() {
		Optional<Position> result = GeometricCalculator.crossPointTwoLines.apply(
				new LinePointAngle(new Position(5, 1), 0.75),
				new LineTwoPoints(new Position(2, 2), new Position(2, 7)));

		assertEquals(2.0, result.get().getX(), 0.001);
		assertEquals(4.0, result.get().getY(), 0.001);
	}

	@Test
	public void crossPointTwoLinesTest2() {
		Optional<Position> result = GeometricCalculator.crossPointTwoLines.apply(
				new LinePointAngle(new Position(5, 1), 0.75),
				new LineTwoPoints(new Position(5, 2), new Position(5, 7)));
		assertEquals(false, result.isPresent());
	}

	@Test
	public void crossPointTwoLinesTest3() {
		Optional<Position> result = GeometricCalculator.crossPointTwoLines.apply(
				new LinePointAngle(new Position(5, 1), 1.75),
				new LineTwoPoints(new Position(2, 2), new Position(2, 7)));
		assertEquals(false, result.isPresent());
	}

}
