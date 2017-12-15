package com.mass.crowdPressure.model.pedestrian;

import com.mass.crowdPressure.calculators.PedestrianCalculator;
import com.mass.crowdPressure.exceptions.AngleOutOfRangeException;
import com.mass.crowdPressure.model.Environment;

public class Pedestrian {

	private PedestrianInformation pedestrianInformation;
	private Environment environment;

	private PedestrianCalculator pedestrianCalculator;

	public Pedestrian(PedestrianInformation pedestrianInformation, Environment environment) {
		this.environment = environment;
		this.pedestrianInformation = pedestrianInformation;

		this.pedestrianCalculator = new PedestrianCalculator(pedestrianInformation, environment);
	}

	public void prepareNextStep() throws AngleOutOfRangeException {

		double desiredDirection = pedestrianCalculator.getDesireDirection();
		double deiredSpeed = pedestrianCalculator.getDesireVelocity();
		pedestrianInformation.setDesiredDirection(desiredDirection);
		pedestrianInformation.setDesiredSpeed(deiredSpeed);
		pedestrianInformation.setDestinationAngle(PedestrianCalculator.calculateDestinationAngle
				.apply(pedestrianInformation.getNextPosition(), pedestrianInformation.getDestinationPoint()));

		pedestrianInformation.setNextPosition(pedestrianCalculator.getNextPosition());
		pedestrianInformation.setVisionCenter(desiredDirection);

	}

	public void nextStep() {
		// move in direction desireDirection and value desireVelocity
		// set new position, itd

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
