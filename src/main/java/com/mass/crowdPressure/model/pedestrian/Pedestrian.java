package com.mass.crowdPressure.model.pedestrian;

import java.util.Arrays;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.calculators.Configuration;
import com.mass.crowdPressure.calculators.GemoetricCalculator;
import com.mass.crowdPressure.calculators.PedestrianCalculator;
import com.mass.crowdPressure.exceptions.AngleOutOfRangeException;
import com.mass.crowdPressure.model.DirectionInfo;
import com.mass.crowdPressure.model.Environment;

public class Pedestrian {

	private final static COD cod = CODFactory.setLevelOfDepression(2);
	private PedestrianInformation pedestrianInformation;
	private Environment environment;
	private PedestrianCalculator pedestrianCalculator;

	public Pedestrian(PedestrianInformation pedestrianInformation, Environment environment) {
		this.environment = environment;
		this.pedestrianInformation = pedestrianInformation;
		this.pedestrianCalculator = new PedestrianCalculator(pedestrianInformation, environment);
	}

	public void prepareNextStep() throws AngleOutOfRangeException {
		if (pedestrianInformation.getVariableInformation().isFinished()) {
			cod.i("PEDESTRIAN: " + pedestrianInformation.getStaticInformation().getId() + " FINISHED");
			return;
		}

		DirectionInfo desiredDirectionInfo = pedestrianCalculator.getDirectionInfo();
		double deiredSpeed = pedestrianCalculator.getDesireVelocity();
		pedestrianInformation.getVariableInformation().setDesiredDirection(desiredDirectionInfo.getAlpha());
		pedestrianInformation.getVariableInformation().setDesiredSpeed(deiredSpeed);
		pedestrianInformation.getVariableInformation().setNextPosition(pedestrianCalculator.getNextPosition());
		pedestrianInformation.getVariableInformation()
				.setDestinationAngle(GemoetricCalculator.calculateDestinationAngle.apply(
						pedestrianInformation.getVariableInformation().getNextPosition(),
						pedestrianInformation.getVariableInformation().getDestinationPoint()));
	}

	public void nextStep() {
		if (pedestrianInformation.getVariableInformation().isFinished()) {
			return;
		}
		// cod.i("pos",pedestrianInformation.getVariableInformation().getPosition());
		// cod.i("next",pedestrianInformation.getVariableInformation().getNextPosition());
		pedestrianInformation.getVariableInformation()
				.setPosition(pedestrianInformation.getVariableInformation().getNextPosition());
		pedestrianInformation.getVariableInformation()
				.setVisionCenter(pedestrianInformation.getVariableInformation().getDesiredDirection());

		pedestrianInformation.getVariableInformation()
				.setFinished(GemoetricCalculator.isBigger.apply(
						GemoetricCalculator.distance.apply(pedestrianInformation.getVariableInformation().getPosition(),
								pedestrianInformation.getVariableInformation().getDestinationPoint()),
						Configuration.MAX_DISTANCE_TO_GOAL));

		// cod.i("af pos",pedestrianInformation.getVariableInformation().getPosition());
		// cod.i("af
		// next",pedestrianInformation.getVariableInformation().getNextPosition());
	}

	public PedestrianInformation getPedestrianInformation() {
		return pedestrianInformation;
	}

	public void setPedestrianInformation(PedestrianInformation pedestrianInformation) {
		this.pedestrianInformation = pedestrianInformation;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

}
