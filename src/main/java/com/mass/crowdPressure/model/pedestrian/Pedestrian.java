package com.mass.crowdPressure.model.pedestrian;

import com.mass.crowdPressure.calculators.CollisionDistance;
import com.mass.crowdPressure.calculators.DestinationDistance;
import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.Function;

public class Pedestrian {

	private PedestrianInformation pedestrianInformation;
	private Environment environment;
	private CollisionDistance collisionDistance;
	private DestinationDistance destinationDistance;

	private double desireDirection;
	private double desireVelocity;


	public Pedestrian(PedestrianInformation pedestrianInformation, Environment environment) {
		this.environment = environment;
		this.pedestrianInformation = pedestrianInformation;
		collisionDistance = new CollisionDistance(pedestrianInformation, environment);
		destinationDistance = new DestinationDistance();
	}

	public void prepareNextStep() {
//		Function collistionDistanceFunction = collisionDistance.getCollistionDistanceFunction();
//		Function distanceFunction = collisionDistance.getCollistionDistanceFunction();
//		
//		desireDirection = minimalizationOfDistance. (collistionDistance)
				//calculate  desireDirection
				//calculate desireVelocity
		
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
