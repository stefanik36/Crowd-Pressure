package com.mass.crowdPressure.calculators;

import static org.junit.Assert.*;

import org.junit.Test;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.model.FunctionValue;

public class DestinationDistanceTest {
	private final static COD cod = CODFactory.setLevelOfDepression(2);

	@Test
	public void getDestinationDistanceFunctionTest() {
		// FunctionValue<Double, Double> functionValue = new FunctionValue<>();
		// functionValue.put(1.0, 2.2);
		// functionValue.put(1.1, 3.3);
		// functionValue.put(1.2, 1.1);
		// functionValue.put(1.3, 7.1);
		// functionValue.put(1.4, 1.4);

		DestinationDistance destinationDistance = new DestinationDistance();
		double result = destinationDistance.getDestinationDistanceFunction(10, 11, 10.0, 2.0);
//		cod.i(result);
		assertEquals(144.0, result, 0.01);
	}

}
