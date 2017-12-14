package com.mass.crowdPressure.builders;

import java.util.ArrayList;
import java.util.List;

import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.pedestrian.Pedestrian;
import com.mass.crowdPressure.model.pedestrian.PedestrianInformation;

public class PedestriansBuilder {

	private static final double DEFAULT_PEDESTRIAN_MASS = 70.0;
	private static final double DEFAULT_PEDESTRIAN_COMFORTABLE_SPEED = 1.3;
	private static final double DEFAULT_PEDESTRIAN_VISION_ANGLE = 75;
	private static final double DEFAULT_PEDESTRIAN_HORIZON_DISTANCE = 10;
	private static final double DEFAULT_PEDESTRIAN_RELAXATION_TIME = 10;

	private Pedestrian createPedestrian(int id, Environment environment) {
		double visionCenter = 0.0;
		Position destinationPoint = new Position(100,100);
		Position position = new Position(0,0);

		PedestrianInformation pedestrianInformation = new PedestrianInformation(id, DEFAULT_PEDESTRIAN_MASS,
				DEFAULT_PEDESTRIAN_COMFORTABLE_SPEED, DEFAULT_PEDESTRIAN_VISION_ANGLE,
				DEFAULT_PEDESTRIAN_HORIZON_DISTANCE, DEFAULT_PEDESTRIAN_RELAXATION_TIME, visionCenter, destinationPoint,
				position);

		return new Pedestrian(pedestrianInformation, environment);
	}

	public void addPedestrians(Environment environment, int initNoPedestrians) {
		for (int id = 0; id < initNoPedestrians; id++) {
			environment.getPedestrians().add(createPedestrian(id, environment));
		}
	}

}
