package com.mass.crowdPressure.calculators;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.function.BiFunction;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.exceptions.AngleOutOfRangeException;
import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.FunctionValue;
import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.pedestrian.PedestrianInformation;

public class PedestrianCalculator {
	private final static COD cod = CODFactory.setLevelOfDepression(2);
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

	public static BiFunction<Position, Position, Double> calculateDestinationAngle = (Position pos,
			Position destinationPoint) -> {

		double result = (pos.getY() - destinationPoint.getY()) / (pos.getX() - destinationPoint.getX());
		result = Math.atan(result) / Math.PI;
		if (result < 0) {
			result = 1 + result;
		}
		if (pos.getY() >= destinationPoint.getY()) {
			if ((pos.getX() >= destinationPoint.getX()) || (result != 0)) {
				result = result + 1;
			}
		}
		return result;
	};

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
					pedestrianInformation.getDestinationAngle(), pedestrianInformation.getHorizontDistance(),
					collisionDistanceValue);
//			cod.i(alpha + ": ", Arrays.asList(collisionDistanceValue, destinationDistanceValue));

			destinationDistanceFunctionValues.put(alpha, destinationDistanceValue);
		}
		return destinationDistanceFunctionValues;
	}

	public double getDesireVelocity() {
		// TODO Auto-generated method stub
		return pedestrianInformation.getComfortableSpeed();
	}

	public double getKeyOfMinimum(FunctionValue<Double, Double> distanceFunction) {
		Entry<Double, Double> min = Collections.min(distanceFunction.getMap().entrySet(),
				Comparator.comparingDouble(Entry::getValue));
		return min.getKey();
	}

	public Position getNextPosition() throws AngleOutOfRangeException {
		double desiredDirection = pedestrianInformation.getDesiredDirection();
		double desiredSpeed = pedestrianInformation.getDesiredSpeed();
		double x = pedestrianInformation.getPosition().getX();
		double y = pedestrianInformation.getPosition().getY();
		double pi = Math.PI;
		double alpha = desiredDirection * pi;
		if (desiredDirection < 0.5) {
			return new Position(x + desiredSpeed * Math.cos(alpha), y + desiredSpeed * Math.sin(alpha));
		} else if (desiredDirection == 0.5) {
			return new Position(x, y + desiredSpeed);
		} else if (desiredDirection <= 1.0) {
			return new Position(x - desiredSpeed * Math.cos(pi - alpha), y + desiredSpeed * Math.sin(pi - alpha));
		} else if (desiredDirection <= 1.5) {
			return new Position(x - desiredSpeed * Math.cos(alpha - pi), y - desiredSpeed * Math.sin(alpha - pi));
		} else if (desiredDirection == 1.5) {
			return new Position(x, y - desiredSpeed);
		} else if (desiredDirection < 2.0) {
			return new Position(x + desiredSpeed * Math.cos(2 * pi - alpha),
					y - desiredSpeed * Math.sin(2 * pi - alpha));
		}

		throw new AngleOutOfRangeException();
	}
}
