package com.mass.crowdPressure.calculators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.BiFunction;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.exceptions.AngleOutOfRangeException;
import com.mass.crowdPressure.model.DirectionInfo;
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

	public PedestrianCalculator(PedestrianInformation pedestrianInformation, Environment environment) {
		this.pedestrianInformation = pedestrianInformation;
		this.environment = environment;
		this.destinationDistance = new DestinationDistance();
		this.collisionDistance = new CollisionDistance();
	}

	public DirectionInfo getDirectionInfo() {
//		FunctionValue<Double, Double> destinationDistanceFunctionValues = getDestinationDistanceFunctionValues();
//		return getKeyOfMinimum(destinationDistanceFunctionValues);
		List<DirectionInfo> directionInfos = getDestinationDistanceFunctionValues();
		return getMinimum(directionInfos);
	}

	List<DirectionInfo> getDestinationDistanceFunctionValues() {
		List<DirectionInfo> directionInfos = new ArrayList<>();

//		FunctionValue<Double, Double> destinationDistanceFunctionValues = new FunctionValue<>();
		double start = pedestrianInformation.getVariableInformation().getVisionCenter()
				- pedestrianInformation.getStaticInformation().getVisionAngle();
		double step = Configuration.ANGLE_GRANULATION;
		double end = pedestrianInformation.getVariableInformation().getVisionCenter()
				+ pedestrianInformation.getStaticInformation().getVisionAngle();
		double alpha;
		for (Double i = start; i <= end; i = i + step) {
			alpha = getAlpha(i);

//			cod.i(alpha);
			double collisionDistanceValue = collisionDistance.getCollistionDistanceValue(environment, alpha,
					pedestrianInformation);
			double destinationDistanceValue = destinationDistance.getDestinationDistanceFunction(alpha,
					pedestrianInformation.getVariableInformation().getDestinationAngle(),
					pedestrianInformation.getStaticInformation().getHorizontDistance(), collisionDistanceValue);
//			destinationDistanceFunctionValues.put(alpha, destinationDistanceValue);
			directionInfos.add(new DirectionInfo(alpha, collisionDistanceValue, destinationDistanceValue));
		}
//		return destinationDistanceFunctionValues;
		return directionInfos;
	}

	private double getAlpha(Double i) {
		// TODO if i>4 or i<-2
		double alpha;
		if (i < Configuration.START_ANGLE) {
			alpha = 2 + i;
		} else if (i > Configuration.END_ANGLE) {
			alpha = i - 2;
		} else {
			alpha = i;
		}
		return alpha;
	}

//	public double getKeyOfMinimum(FunctionValue<Double, Double> distanceFunction) {
//		Entry<Double, Double> min = Collections.min(distanceFunction.getMap().entrySet(),
//				Comparator.comparingDouble(Entry::getValue));
//		return min.getKey();
//	}

	public DirectionInfo getMinimum(List<DirectionInfo> directionInfos) {
		DirectionInfo min = Collections.min(directionInfos, new Comparator<DirectionInfo>() {

			@Override
			public int compare(DirectionInfo di1, DirectionInfo di2) {
				return di1.getDestinationDistance().compareTo(di2.getDestinationDistance());
			}

		});

		return min;
	}

	public Position getNextPosition() throws AngleOutOfRangeException {
		double desiredDirection = pedestrianInformation.getVariableInformation().getDesiredDirection();
		double desiredSpeed = pedestrianInformation.getVariableInformation().getDesiredSpeed();
		double x = pedestrianInformation.getVariableInformation().getPosition().getX();
		double y = pedestrianInformation.getVariableInformation().getPosition().getY();
		double pi = Math.PI;
		// cod.i("bef: ",pedestrianInformation.getVariableInformation().getPosition());

//		cod.i(desiredDirection);
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

	public double getDesireVelocity() {
		// TODO Auto-generated method stub
		double result = pedestrianInformation.getStaticInformation().getComfortableSpeed();
		double distance = GemoetricCalculator.distance.apply(
				pedestrianInformation.getVariableInformation().getPosition(),
				pedestrianInformation.getVariableInformation().getDestinationPoint());
		result = result < distance ? result : distance;
		return result;
	}

}
