package com.mass.crowdPressure.calculators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import java.util.List;

import org.junit.Test;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.exceptions.AngleOutOfRangeException;
import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.pedestrian.PedestrianInformation;
import com.mass.crowdPressure.model.pedestrian.VariableInformation;
import com.mass.crowdPressure.model.pedestrian.StaticInformation;

public class PedestrianCrossPointsTest {
	private final static COD cod = CODFactory.setLevelOfDepression(2);

	@Test
	public void calculateCrossPointUnderSqrtTest() {

		StaticInformation staticInformation0 = new StaticInformation(0, 5, 5, 0.24, 5, 5);
		VariableInformation variableInformation0 = new VariableInformation(0.2, new Position(100, 100),
				new Position(6, 5));
		PedestrianInformation main = new PedestrianInformation(staticInformation0, variableInformation0);

		StaticInformation staticInformation1 = new StaticInformation(1, 640, 5, 0.24, 5, 5);
		VariableInformation variableInformation1 = new VariableInformation(0.2, new Position(100, 100),
				new Position(11, 8));
		PedestrianInformation neighborInformation = new PedestrianInformation(staticInformation1, variableInformation1);

		//
		//
		//
		// PedestrianInformation pedestrianInformation = new PedestrianInformation(0, 5,
		// 5, 0.2, 5, 5, 0.24,
		// new Position(100, 100), new Position(6, 5));
		// PedestrianInformation neighborInformation = new PedestrianInformation(1, 640,
		// 5, 0.2, 5, 5, 0.24,
		// new Position(100, 100), new Position(11, 8));
		// cod.i(pedestrianInformation);
		// cod.i(neighborInformation);
		PedestrianCrossPoints pcp = new PedestrianCrossPoints(0.25, main);

		double result = pcp.calculateCrossPointCoordUnderSqrt(
				neighborInformation.getVariableInformation().getPosition(),
				neighborInformation.getStaticInformation().getRadius());
		// cod.i(result);
		assertEquals(3.999, result, 0.1);

	}

	@Test
	public void calculateNeighborCrossPointsTest() throws AngleOutOfRangeException {
		StaticInformation staticInformation0 = new StaticInformation(0, 5, 5, 0.24, 5, 5);
		VariableInformation variableInformation0 = new VariableInformation(0.2, new Position(100, 100),
				new Position(6, 5));
		PedestrianInformation main = new PedestrianInformation(staticInformation0, variableInformation0);

		StaticInformation staticInformation1 = new StaticInformation(1, 640, 5, 0.24, 5, 5);
		VariableInformation variableInformation1 = new VariableInformation(0.2, new Position(100, 100),
				new Position(11, 8));
		PedestrianInformation neighborInformation = new PedestrianInformation(staticInformation1, variableInformation1);

		// PedestrianInformation pedestrianInformation = new PedestrianInformation(0, 5,
		// 5, 0.2, 5, 5, 0.24,
		// new Position(100, 100), new Position(6, 5));
		// PedestrianInformation neighborInformation = new PedestrianInformation(1, 640,
		// 5, 0.2, 5, 5, 0.24,
		// new Position(100, 100), new Position(11, 8));
		// cod.i(pedestrianInformation);
		// cod.i(neighborInformation);
		PedestrianCrossPoints pcp = new PedestrianCrossPoints(0.25, main);

		List<Position> result = pcp.getNeighborAllCrossPoints(neighborInformation);
		// cod.i(result);
		assertEquals(11.0, result.get(0).getX(), 0.1);
		assertEquals(10.0, result.get(0).getY(), 0.1);
		assertEquals(9.0, result.get(1).getX(), 0.1);
		assertEquals(8.0, result.get(1).getY(), 0.1);
		assertEquals(2, result.size());

	}

}
