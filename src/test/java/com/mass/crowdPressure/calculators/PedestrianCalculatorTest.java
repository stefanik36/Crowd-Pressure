package com.mass.crowdPressure.calculators;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.FunctionValue;
import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.pedestrian.Pedestrian;
import com.mass.crowdPressure.model.pedestrian.PedestrianInformation;

public class PedestrianCalculatorTest {
	private final static COD cod = CODFactory.setLevelOfDepression(2);

	@Test
	public void getDestinationDistanceFunctionValuesTest() {

		List<Pedestrian> pedestrians = new ArrayList<>();
		Environment environment = new Environment(pedestrians, null);
		pedestrians.addAll(Arrays.asList(new Pedestrian(
				new PedestrianInformation(1, 640, 1, 1, 1, 1, 1, new Position(1, 1), new Position(11, 8)), environment),
				new Pedestrian(
						new PedestrianInformation(2, 640, 1, 1, 1, 1, 1, new Position(1, 1), new Position(7, 15)),
						environment)));

		PedestrianInformation pedestrianInformation = new PedestrianInformation(0, 1, 1, 0.24, 10, 1, 0.25,
				new Position(1, 1), new Position(6, 5));
		PedestrianCalculator pc = new PedestrianCalculator(pedestrianInformation, environment);

		FunctionValue<Double, Double> ddfv = pc.getDestinationDistanceFunctionValues();
		
	}

}
