package com.mass.crowdPressure.model.pedestrian;

import java.util.List;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.Configuration;
import com.mass.crowdPressure.calculators.GeometricCalculator;
import com.mass.crowdPressure.calculators.PedestrianCalculator;
import com.mass.crowdPressure.calculators.figures.Vector;
import com.mass.crowdPressure.calculators.figures.VectorXY;
import com.mass.crowdPressure.exceptions.AngleOutOfRangeException;
import com.mass.crowdPressure.model.DirectionInfo;
import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.Position;

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
		
		
		DirectionInfo desiredDirectionInfo = pedestrianCalculator.getDirectionInfo();
		Vector desiredVelocity = pedestrianCalculator.getDesireVelocity(desiredDirectionInfo.getCollisionDistance().getMinimumDistance(),
				desiredDirectionInfo.getAlpha(), pedestrianInformation.getStaticInformation().getId());
		// cod.i(desiredVelocity);

		List<Vector> forces = pedestrianCalculator.getForces();
		double forcesValue = pedestrianCalculator.getForcesValue(forces);
		Vector desiredAcceleration = pedestrianCalculator.getDesireAcceleration(desiredVelocity, forces);
		pedestrianInformation.getVariableInformation().setDesiredDirection(desiredDirectionInfo.getAlpha());
		pedestrianInformation.getVariableInformation().setDesiredSpeed(desiredVelocity);
		pedestrianInformation.getVariableInformation().setDesiredAcceleration(desiredAcceleration);
		pedestrianInformation.getVariableInformation().setNextPosition(pedestrianCalculator.getNextPosition());
		pedestrianInformation.getVariableInformation()
				.setDestinationAngle(GeometricCalculator.calculateAngle.apply(
						pedestrianInformation.getVariableInformation().getNextPosition(),
						pedestrianInformation.getVariableInformation().getDestinationPoint()));
		// cod.e("ACCELERATION",desiredAcceleration);
		pedestrianInformation.getVariableInformation()
				.setCrowdPressure(pedestrianCalculator.getCrowdPressure(forcesValue));
	}

	public void nextStep() {
		// cod.i("pos",pedestrianInformation.getVariableInformation().getPosition());
		// cod.i("next",pedestrianInformation.getVariableInformation().getNextPosition());
		pedestrianInformation.getVariableInformation()
				.setPosition(pedestrianInformation.getVariableInformation().getNextPosition());
		pedestrianInformation.getVariableInformation()
				.setVisionCenter(pedestrianInformation.getVariableInformation().getDesiredDirection());

		pedestrianInformation.getVariableInformation()
				.setFinished(GeometricCalculator.isBigger.apply(
						GeometricCalculator.distance.apply(pedestrianInformation.getVariableInformation().getPosition(),
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
