package com.mass.crowdPressure.model.pedestrian;

import com.mass.crowdPressure.calculators.CollisionDistance;
import com.mass.crowdPressure.calculators.DestinationDistance;
import com.mass.crowdPressure.calculators.PedestrianCalculator;
import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.FunctionValue;

public class Pedestrian {

	private PedestrianInformation pedestrianInformation;
	private Environment environment;

	private PedestrianCalculator pedestrianCalculator;

	private double desireDirection;
	private double desireVelocity;


	public Pedestrian(PedestrianInformation pedestrianInformation, Environment environment) {
		this.environment = environment;
		this.pedestrianInformation = pedestrianInformation;

		this.pedestrianCalculator = new PedestrianCalculator(pedestrianInformation, environment);
	}

	public void prepareNextStep() {
		desireDirection = pedestrianCalculator.getDesireDirection();
		desireVelocity = pedestrianCalculator.getDesireVelocity();
		
		
		
	}


	public void nextStep() {
		//move in direction desireDirection and value desireVelocity
		//set new position, itd
		
	}

	// gettesrs and setters
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
