package com.mass.crowdPressure.calculators;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map.Entry;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.FunctionValue;
import com.mass.crowdPressure.model.pedestrian.PedestrianInformation;

public class PedestrianCalculator {
	// private final static COD cod = CODFactory.setLevelOfDepression(2);
	private PedestrianInformation pedestrianInformation;
	private Environment environment;

	private DestinationDistance destinationDistance;
	private CollisionDistance collisionDistance;

	// FunctionValue<Double, Double> collisionDistanceFunctionValues;
	// FunctionValue<Double, Double> destinationDistanceFunctionValues;

	public PedestrianCalculator(PedestrianInformation pedestrianInformation, Environment environment) {
		this.pedestrianInformation = pedestrianInformation;
		this.environment = environment;
		// collisionDistanceFunctionValues = new FunctionValue<>();
		// destinationDistanceFunctionValues = new FunctionValue<>();

		// default calculators
		this.destinationDistance = new DestinationDistance();
		this.collisionDistance = new CollisionDistance();
	}

	public double getDesireDirection() {
		FunctionValue<Double, Double> destinationDistanceFunctionValues = getDestinationDistanceFunctionValues();
		return getKeyOfMinimum(destinationDistanceFunctionValues);
	}

	FunctionValue<Double, Double> getDestinationDistanceFunctionValues() {
		FunctionValue<Double, Double> destinationDistanceFunctionValues = new FunctionValue<>();
		double start = pedestrianInformation.getVisionCenter() - pedestrianInformation.getVisionAngle();
		double step = Configuration.ANGLE_GRANULATION;
		double end = pedestrianInformation.getVisionCenter() + pedestrianInformation.getVisionAngle();

		for (Double alpha = start; alpha <= end; alpha = alpha + step) {

			double collisionDistanceValue = collisionDistance.getCollistionDistanceValue(environment, alpha,
					pedestrianInformation);
			double destinationDistanceValue = destinationDistance.getDestinationDistanceFunction(alpha,
					pedestrianInformation.getVisionCenter(), pedestrianInformation.getHorizontDistance(),
					collisionDistanceValue);

			destinationDistanceFunctionValues.put(alpha, destinationDistanceValue);
		}
		return destinationDistanceFunctionValues;
	}

	public double getDesireVelocity() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getKeyOfMinimum(FunctionValue<Double, Double> distanceFunction) {
		Entry<Double, Double> min = Collections.min(distanceFunction.getMap().entrySet(),
				Comparator.comparingDouble(Entry::getValue));
		return min.getKey();
	}
}
