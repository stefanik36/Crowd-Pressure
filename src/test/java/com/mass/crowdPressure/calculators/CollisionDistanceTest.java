package com.mass.crowdPressure.calculators;

import static org.junit.Assert.*;

import org.junit.Test;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.model.FunctionValue;
import com.mass.crowdPressure.model.pedestrian.PedestrianInformation;
import static org.mockito.Mockito.*;

public class CollisionDistanceTest {
	private final static COD cod = CODFactory.setLevelOfDepression(2);

	@Test
	public void getCollistionDistanceFunctionTest() {
//		PedestrianInformation pedestrianInformation = mock(PedestrianInformation.class);
		CollisionDistance cd = new CollisionDistance();
		
		double result = cd.getCollistionDistanceFunction(12.3);

//		when(pedestrianInformation.getVisionCenter()).thenReturn(43.0);
//		when(pedestrianInformation.getVisionAngle()).thenReturn(5.0);
//		when(pedestrianInformation.getHorizontDistance()).thenReturn(10.0);
//		cod.i(functionValue);
		assertEquals(12.3, result, 0.001);
	}

}
