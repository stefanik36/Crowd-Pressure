package com.mass.crowdPressure.model.pedestrian;

import static org.junit.Assert.*;

import org.junit.Test;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.exceptions.AngleOutOfRangeException;
import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.Position;

public class PedestrianTest {
	private final static COD cod = CODFactory.setLevelOfDepression(2);

	@Test
	public void prepareNextStepTest() throws AngleOutOfRangeException {

		PedestrianInformation pedestrianInformation = new PedestrianInformation(0, 1, 5, 0.24, 10, 1, 0.25,
				new Position(5, 5), new Position(0, 0));
		Pedestrian pedestrian = new Pedestrian(pedestrianInformation, null);
		cod.i(pedestrian);

		pedestrian.prepareNextStep();

		cod.i(pedestrian);

//		fail("Not yet implemented");
	}

}
