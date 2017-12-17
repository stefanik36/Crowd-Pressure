package com.mass.crowdPressure.calculators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.exceptions.AngleOutOfRangeException;
import com.mass.crowdPressure.model.DirectionInfo;
import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.Vector;
import com.mass.crowdPressure.model.VectorXY;
import com.mass.crowdPressure.model.pedestrian.PedestrianInformation;

public class PedestrianCalculator {
	private final static COD cod = CODFactory.setLevelOfDepression(2);
	private PedestrianInformation pedestrianInformation;
	private DestinationDistance destinationDistanceCal;
	private CollisionDistance collisionDistanceCal;
	private Force forceCal;

	public PedestrianCalculator(PedestrianInformation pedestrianInformation, Environment environment) {
		this.pedestrianInformation = pedestrianInformation;
		this.destinationDistanceCal = new DestinationDistance();
		this.collisionDistanceCal = new CollisionDistance(environment);
		this.forceCal = new Force(environment);
	}

	public DirectionInfo getDirectionInfo() {
		List<DirectionInfo> directionInfos = getDestinationDistanceFunctionValues();
		return getMinimum(directionInfos);
	}

	List<DirectionInfo> getDestinationDistanceFunctionValues() {
		List<DirectionInfo> directionInfos = new ArrayList<>();
		double start = pedestrianInformation.getVariableInformation().getVisionCenter()
				- pedestrianInformation.getStaticInformation().getVisionAngle();
		double step = Configuration.ANGLE_GRANULATION;
		double end = pedestrianInformation.getVariableInformation().getVisionCenter()
				+ pedestrianInformation.getStaticInformation().getVisionAngle();
		double alpha;
		for (Double i = start; i <= end; i = i + step) {
			alpha = getAlpha(i);
			double collisionDistanceValue = collisionDistanceCal.getCollistionDistanceValue(alpha,
					pedestrianInformation);
			double destinationDistanceValue = destinationDistanceCal.getDestinationDistanceFunction(alpha,
					pedestrianInformation.getVariableInformation().getDestinationAngle(),
					pedestrianInformation.getStaticInformation().getHorizontDistance(), collisionDistanceValue);
			directionInfos.add(new DirectionInfo(alpha, collisionDistanceValue, destinationDistanceValue));
		}
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
		double desiredSpeed = pedestrianInformation.getVariableInformation().getDesiredSpeed().getValue();
		double x = pedestrianInformation.getVariableInformation().getPosition().getX();
		double y = pedestrianInformation.getVariableInformation().getPosition().getY();

		VectorXY shift = GemoetricCalculator.changeVector(new Vector(desiredDirection, desiredSpeed));

		return new Position(x + shift.getX(), y + shift.getY());

	}

	public Vector getDesireVelocity(double collisionDistance, double alpha) {
		double result = pedestrianInformation.getStaticInformation().getComfortableSpeed();
		Vector v = new Vector(alpha, Arrays.asList(goalVelocity(), collisionVelocity(collisionDistance), result)
				.stream().mapToDouble(d -> d).min().getAsDouble());
		return v;
	}

	private double collisionVelocity(double collisionDistance) {
		return collisionDistance / pedestrianInformation.getStaticInformation().getRelaxationTime();
	}

	private double goalVelocity() {
		double distance = GemoetricCalculator.distance.apply(
				pedestrianInformation.getVariableInformation().getPosition(),
				pedestrianInformation.getVariableInformation().getDestinationPoint());
		return distance / pedestrianInformation.getStaticInformation().getRelaxationTime();
	}

	public Vector getDesireAcceleration(Vector vdes) throws AngleOutOfRangeException {
		Vector acceleration = GemoetricCalculator.subtractVectors(vdes,
				pedestrianInformation.getVariableInformation().getDesiredSpeed());

		Vector nForce = getForcesSum(forceCal.getForceNeighbours(pedestrianInformation));
		nForce.setValue(nForce.getValue() / pedestrianInformation.getStaticInformation().getMass());

		Vector wForce = getForcesSum(forceCal.getForceWalls(pedestrianInformation));
		wForce.setValue(wForce.getValue() / pedestrianInformation.getStaticInformation().getMass());

		acceleration
				.setValue(acceleration.getValue() / pedestrianInformation.getStaticInformation().getRelaxationTime());
		acceleration = GemoetricCalculator.addVectors(acceleration, nForce);
		acceleration = GemoetricCalculator.addVectors(acceleration, wForce);
		return acceleration;
	}

	private Vector getForcesSum(List<Vector> forces) throws AngleOutOfRangeException {
		Vector force = new Vector(0.0, 0.0);
		cod.i(forces);
		for (Vector f : forces) {
			force = GemoetricCalculator.addVectors(force, f);
		}
		return force;
	}

}
