package com.mass.crowdPressure.calculators;

import static org.junit.Assert.*;

import org.junit.Test;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.builders.PedestriansBuilder;
import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.FunctionValue;
import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.pedestrian.Pedestrian;
import com.mass.crowdPressure.model.pedestrian.PedestrianInformation;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollisionDistanceTest {
	private final static COD cod = CODFactory.setLevelOfDepression(2);

	@Test
	public void getCollistionDistanceValueTest() {
		List<Pedestrian> pedestrians = new ArrayList<>();
		Environment environment = new Environment(pedestrians, null);
		pedestrians.addAll(Arrays.asList(
				new Pedestrian(new PedestrianInformation(1, 640, 1, 1, 1, 1, 1, new Position(1, 1), new Position(11, 8)), environment),
				new Pedestrian(new PedestrianInformation(2, 640, 1, 1, 1, 1, 1, new Position(1, 1), new Position(7, 15)), environment)
		));
		PedestrianInformation pedestrianInformation = new PedestrianInformation(0, 1, 1, 0.24, 5, 1, 0.25, new Position(1, 1), new Position(6, 5));
		
		CollisionDistance cd = new CollisionDistance();

		double result = cd.getCollistionDistanceValue(environment, 0.25, pedestrianInformation);

		// when(pedestrianInformation.getVisionCenter()).thenReturn(43.0);
		// when(pedestrianInformation.getVisionAngle()).thenReturn(5.0);
		// when(pedestrianInformation.getHorizontDistance()).thenReturn(10.0);
//		cod.i(result);
		assertEquals(4.24, result, 0.1);
	}

	@Test
	public void distanceTest() {
		CollisionDistance cd = new CollisionDistance();

		Position positionA = new Position(9, 8);
		Position positionB = new Position(5, 5);
		double result = cd.distance.apply(positionA, positionB);

		// cod.i(result);
		assertEquals(5.0, result, 0.001);
	}
	
	@Test
	public void distanceTest2() {
		CollisionDistance cd = new CollisionDistance();

		Position positionA = new Position(9, 8);
		Position positionB = new Position(6, 5);
		double result = cd.distance.apply(positionA, positionB);

//		 cod.i(result);
		assertEquals(4.24, result, 0.1);
	}

}
