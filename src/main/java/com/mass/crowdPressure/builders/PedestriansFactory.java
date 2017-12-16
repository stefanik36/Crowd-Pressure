package com.mass.crowdPressure.builders;

import java.util.ArrayList;
import java.util.List;

import com.mass.crowdPressure.Symulation;
import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.pedestrian.Pedestrian;
import com.mass.crowdPressure.model.pedestrian.PedestrianInformation;
import com.mass.crowdPressure.model.pedestrian.VariableInformation;
import com.mass.crowdPressure.model.pedestrian.StaticInformation;

public class PedestriansFactory {

	private static final double DEFAULT_PEDESTRIAN_MASS = 360.0;
	private static final double DEFAULT_PEDESTRIAN_COMFORTABLE_SPEED = 5;
	private static final double DEFAULT_PEDESTRIAN_VISION_ANGLE = 0.4;
	private static final double DEFAULT_PEDESTRIAN_HORIZON_DISTANCE = 10;
	private static final double DEFAULT_PEDESTRIAN_RELAXATION_TIME = 1;

	public void addPedestrians(Environment environment, Symulation sym) {
		if (sym.equals(Symulation.SYM_P1_W1)) {
			addPedestrians1(environment, 1);
		} else if (sym.equals(Symulation.SYM_P1_W2)) {
			addPedestrians2(environment, 1);
		}
	}

	public void addPedestrians1(Environment environment, int initNoPedestrians) {
		double visionCenter = 0.25;
		Position destinationPoint = new Position(5, 5);
		Position position = new Position(0, 0);
		for (int id = 0; id < initNoPedestrians; id++) {
			environment.getPedestrians()
					.add(createPedestrian(id, environment, visionCenter, destinationPoint, position));
		}
	}

	public void addPedestrians2(Environment environment, int initNoPedestrians) {
		double visionCenter = 0.25;
		Position destinationPoint = new Position(23, 22);
		Position position = new Position(13, 5);
		for (int id = 0; id < initNoPedestrians; id++) {
			environment.getPedestrians()
					.add(createPedestrian(id, environment, visionCenter, destinationPoint, position));
		}
	}

	private Pedestrian createPedestrian(int id, Environment environment, double visionCenter, Position destinationPoint,
			Position position) {

		StaticInformation staticInformation = new StaticInformation(id, DEFAULT_PEDESTRIAN_MASS,
				DEFAULT_PEDESTRIAN_COMFORTABLE_SPEED, DEFAULT_PEDESTRIAN_VISION_ANGLE,
				DEFAULT_PEDESTRIAN_HORIZON_DISTANCE, DEFAULT_PEDESTRIAN_RELAXATION_TIME);

		VariableInformation variableInformation = new VariableInformation(visionCenter, destinationPoint, position);

		PedestrianInformation pedestrianInformation = new PedestrianInformation(staticInformation, variableInformation);

		return new Pedestrian(pedestrianInformation, environment);
	}

}
