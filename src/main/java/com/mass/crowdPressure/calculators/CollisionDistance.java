package com.mass.crowdPressure.calculators;

import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.pedestrian.PedestrianInformation;

public class CollisionDistance {
	private PedestrianInformation pedestrianInformation;
	private Environment environment;

	public CollisionDistance(PedestrianInformation pedestrianInformation, Environment environment) {
		this.pedestrianInformation = pedestrianInformation;
		this.environment = environment;
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
