package com.mass.crowdPressure.calculators;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.model.MinimumDistance;

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
		MinimumDistance collisionDistanceValue = new MinimumDistance(false, 2.0, Optional.empty());
		double result = destinationDistance.getDestinationDistanceFunction(10, 11, 10.0, collisionDistanceValue);
		// cod.i(result);
		assertEquals(144.0, result, 0.01);
	}

	@Test
	public void getDestinationDistanceFunction2Test() {
		DestinationDistance destinationDistance = new DestinationDistance();
		double hd = 5.0;

		List<Double> cdv = Arrays.asList(hd, hd, hd, hd, hd, 4.0, hd, hd, hd, hd, hd);

		int i = 0;
		for (double a = 0.0; a < 1.0; a = a + 0.1) {
			MinimumDistance collisionDistanceValue = new MinimumDistance(false, cdv.get(i), Optional.empty());
			cod.i("" + i + ": " + cdv.get(i),
					destinationDistance.getDestinationDistanceFunction(a, 0.5, hd, collisionDistanceValue));
			i++;
		}
		// assertEquals(144.0, result, 0.01);
	}
}
