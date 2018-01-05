package com.mass.crowdPressure.builders;

import java.util.ArrayList;
import java.util.List;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.Symulation;
import com.mass.crowdPressure.calculators.Configuration;
import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.pedestrian.Pedestrian;
import com.mass.crowdPressure.model.pedestrian.PedestrianInformation;
import com.mass.crowdPressure.model.pedestrian.VariableInformation;
import com.mass.crowdPressure.model.pedestrian.StaticInformation;

public class PedestriansFactory {
	private static final COD cod = CODFactory.getCOD();
	private static int ID = 0;

	public void addPedestrians(Environment environment, Symulation sym) {
		if (sym.equals(Symulation.SYM_P1_W1)) {
			addPedestrians1(environment, 1);
		} else if (sym.equals(Symulation.SYM_P1_W2)) {
			addPedestrians2(environment, 1);
		} else if (sym.equals(Symulation.SYM_P2_W0)) {
			addPedestrian(environment, 30, 5);
			addPedestrian(environment, 30.5, 30);
		} else if (sym.equals(Symulation.SYM_P0_W1)) {
			// none
		}
	}

	private void addPedestrians1(Environment environment, int initNoPedestrians) {
		Position destinationPoint = new Position(5, 5);
		Position position = new Position(0, 0);
		for (int id = 0; id < initNoPedestrians; id++) {
			environment.getPedestrians().add(createPedestrian(ID++, environment, destinationPoint, position));
		}
	}

	public void addPedestrian(Environment environment, double x, double y) {
//		Position p = new Position(30, 5);
//		if (ID % 2 == 0) {
//			p = new Position(30, 30);
//		}
//		cod.i("id:" + ID, p);
		Position p = new Position(22, 1);
		Position destinationPoint = p;
		Position position = new Position(x, y);
		environment.getPedestrians().add(createPedestrian(ID++, environment, destinationPoint, position));
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

	private Pedestrian createPedestrian(int id, Environment environment, Position destinationPoint, Position position) {

		StaticInformation staticInformation = new StaticInformation(id, Configuration.DEFAULT_PEDESTRIAN_MASS,
				Configuration.DEFAULT_PEDESTRIAN_COMFORTABLE_SPEED, Configuration.DEFAULT_PEDESTRIAN_VISION_ANGLE,
				Configuration.DEFAULT_PEDESTRIAN_HORIZON_DISTANCE, Configuration.DEFAULT_PEDESTRIAN_RELAXATION_TIME);

		VariableInformation variableInformation = new VariableInformation(destinationPoint, position);

		PedestrianInformation pedestrianInformation = new PedestrianInformation(staticInformation, variableInformation);

		return new Pedestrian(pedestrianInformation, environment);
	}

	private Pedestrian createPedestrian(int id, Environment environment, double visionCenter, Position destinationPoint,
			Position position) {

		StaticInformation staticInformation = new StaticInformation(id, Configuration.DEFAULT_PEDESTRIAN_MASS,
				Configuration.DEFAULT_PEDESTRIAN_COMFORTABLE_SPEED, Configuration.DEFAULT_PEDESTRIAN_VISION_ANGLE,
				Configuration.DEFAULT_PEDESTRIAN_HORIZON_DISTANCE, Configuration.DEFAULT_PEDESTRIAN_RELAXATION_TIME);

		VariableInformation variableInformation = new VariableInformation(visionCenter, destinationPoint, position);

		PedestrianInformation pedestrianInformation = new PedestrianInformation(staticInformation, variableInformation);

		return new Pedestrian(pedestrianInformation, environment);
	}

}
