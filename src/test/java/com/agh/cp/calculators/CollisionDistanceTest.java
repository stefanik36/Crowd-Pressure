package com.agh.cp.calculators;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.agh.cp.exceptions.AngleOutOfRangeException;
import com.agh.cp.model.Environment;
import com.agh.cp.model.map.Map;
import com.agh.cp.model.pedestrian.Pedestrian;
import com.agh.cp.model.pedestrian.PedestrianInformation;
import com.agh.cp.model.pedestrian.StaticInformation;
import com.agh.cp.model.pedestrian.VariableInformation;
import org.junit.Test;

import com.app.COD;
import com.app.CODFactory;
import com.agh.cp.model.Position;

public class CollisionDistanceTest {
	private final static COD cod = CODFactory.setLevelOfDepression(2);

	@Test
	public void getCollistionDistanceValueTest() throws AngleOutOfRangeException {
		List<Pedestrian> pedestrians = new ArrayList<>();
		Environment environment = new Environment(pedestrians, new Map(new ArrayList<>()));
		StaticInformation staticInformation1 = new StaticInformation(1, 640, 1, 1, 1, 1);
		VariableInformation variableInformation1 = new VariableInformation(1, new Position(1, 1), new Position(11, 8));
		StaticInformation staticInformation2 = new StaticInformation(2, 640, 1, 1, 1, 1);
		VariableInformation variableInformation2 = new VariableInformation(1, new Position(1, 1), new Position(7, 15));
		pedestrians.addAll(Arrays.asList(
				new Pedestrian(new PedestrianInformation(staticInformation1, variableInformation1), environment),
				new Pedestrian(new PedestrianInformation(staticInformation2, variableInformation2), environment)));

		StaticInformation staticInformation0 = new StaticInformation(0, 1, 1, 0.24, 5, 1);
		VariableInformation variableInformation0 = new VariableInformation(0.25, new Position(1, 1),
				new Position(6, 5));
		PedestrianInformation main = new PedestrianInformation(staticInformation0, variableInformation0);

		CollisionDistance cd = new CollisionDistance(environment);
		// cod.i(environment.getPedestrians().stream().map(p ->
		// p.getPedestrianInformation()).collect(Collectors.toList()));
		double result = cd.getCollistionDistanceValue(0.25, main).getMinimumDistance();

		// when(pedestrianInformation.getVisionCenter()).thenReturn(43.0);
		// when(pedestrianInformation.getVisionAngle()).thenReturn(5.0);
		// when(pedestrianInformation.getHorizontDistance()).thenReturn(10.0);
		// cod.i(result);
		assertEquals(4.24, result, 0.1);
	}

}
