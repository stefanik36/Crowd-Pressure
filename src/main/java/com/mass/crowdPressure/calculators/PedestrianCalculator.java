package com.mass.crowdPressure.calculators;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map.Entry;

import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.FunctionValue;
import com.mass.crowdPressure.model.pedestrian.PedestrianInformation;

public class PedestrianCalculator {
	private PedestrianInformation pedestrianInformation;
	private Environment environment;

	private DestinationDistance destinationDistance;
	private CollisionDistance collisionDistance;

	FunctionValue<Double, Double> collisionDistanceFunctionValues;
	FunctionValue<Double, Double> destinationDistanceFunctionValues;

	public PedestrianCalculator(PedestrianInformation pedestrianInformation, Environment environment) {
		this.pedestrianInformation = pedestrianInformation;
		this.environment = environment;
		collisionDistanceFunctionValues = new FunctionValue<>();
		destinationDistanceFunctionValues = new FunctionValue<>();

		// default calculators
		this.destinationDistance = new DestinationDistance();
		this.collisionDistance = new CollisionDistance();
	}

	public double getDesireDirection() {
		double horizontDistance = pedestrianInformation.getHorizontDistance();
		double visionCenter = pedestrianInformation.getVisionCenter();
		
		double start = pedestrianInformation.getVisionCenter() - pedestrianInformation.getVisionAngle();
		double step = Configuration.ANGLE_GRANULATION;
		double end = pedestrianInformation.getVisionCenter() + pedestrianInformation.getVisionAngle();
		for (Double alpha = start; alpha <= end; alpha = alpha + step) {
			double collisionDistanceValue = collisionDistance.getCollistionDistanceFunction(horizontDistance);
			double destinationDistanceValue = destinationDistance.getDestinationDistanceFunction(alpha, visionCenter,
					horizontDistance, collisionDistanceValue);

			collisionDistanceFunctionValues.put(alpha, collisionDistanceValue);
			destinationDistanceFunctionValues.put(alpha, destinationDistanceValue);

		}
		return getKeyOfMinimum(destinationDistanceFunctionValues);
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
