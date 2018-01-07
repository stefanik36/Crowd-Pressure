package com.mass.crowdPressure.calculators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.calculators.figures.Vector;
import com.mass.crowdPressure.calculators.figures.VectorXY;
import com.mass.crowdPressure.exceptions.AngleOutOfRangeException;
import com.mass.crowdPressure.model.DirectionInfo;
import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.MinimumDistance;
import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.pedestrian.PedestrianInformation;

public class PedestrianCalculator {
	private final static COD cod = CODFactory.setLevelOfDepression(2);
	private PedestrianInformation pedestrianInformation;
	private DestinationDistance destinationDistanceCal;
	private CollisionDistance collisionDistanceCal;
	private CrowdPressure crowdPressureCal;
	private Force forceCal;

	public PedestrianCalculator(PedestrianInformation pedestrianInformation, Environment environment) {
		this.pedestrianInformation = pedestrianInformation;
		this.destinationDistanceCal = new DestinationDistance();
		this.collisionDistanceCal = new CollisionDistance(environment);
		this.crowdPressureCal = new CrowdPressure();
		this.forceCal = new Force(environment);
	}

	public DirectionInfo getDirectionInfo() throws AngleOutOfRangeException {
		List<DirectionInfo> directionInfos = getDestinationDistanceFunctionValues();

		List<DirectionInfo> filtered = directionInfos.stream()
				.filter(di -> !di.getCollisionDistance().getMinimumDistanceWall().isPresent())
				.collect(Collectors.toList());
		
		cod.i("FILTERED: ",filtered);
		if (filtered.isEmpty()) {
			return getMinimum(directionInfos);
		} else {
			return getMinimum(filtered);
		}

	}

	List<DirectionInfo> getDestinationDistanceFunctionValues() throws AngleOutOfRangeException {
		List<DirectionInfo> directionInfos = new ArrayList<>();
		double start = pedestrianInformation.getVariableInformation().getVisionCenter()
				- pedestrianInformation.getStaticInformation().getVisionAngle();
		double step = Configuration.ANGLE_GRANULATION;
		double end = pedestrianInformation.getVariableInformation().getVisionCenter()
				+ pedestrianInformation.getStaticInformation().getVisionAngle();
		double alpha;
		for (Double i = start; i <= end; i = i + step) {
			alpha = getAlpha(i);
			MinimumDistance collisionDistanceValue = collisionDistanceCal.getCollistionDistanceValue(alpha,
					pedestrianInformation);
			double destinationDistanceValue = destinationDistanceCal.getDestinationDistanceFunction(alpha,
					pedestrianInformation.getVariableInformation().getDestinationAngle(),
					pedestrianInformation.getStaticInformation().getHorizontDistance(), collisionDistanceValue);

			directionInfos.add(new DirectionInfo(alpha, collisionDistanceValue, destinationDistanceValue));
		}
		// cod.i("DIRECTIONS: ",directionInfos.stream().filter(d ->
		// d.getCollisionDistance()<10.0).collect(Collectors.toList()));
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

		return Collections.min(directionInfos, new Comparator<DirectionInfo>() {
			@Override
			public int compare(DirectionInfo di1, DirectionInfo di2) {
				return di1.getDestinationDistance().compareTo(di2.getDestinationDistance());
			}
		});
	}

	public Position getNextPosition() throws AngleOutOfRangeException {
		// double desiredDirection =
		// pedestrianInformation.getVariableInformation().getDesiredDirection();
		// double desiredSpeed =
		// pedestrianInformation.getVariableInformation().getDesiredSpeed().getValue();

		Vector velocity = pedestrianInformation.getVariableInformation().getDesiredSpeed();
		Vector acceleration = pedestrianInformation.getVariableInformation().getDesiredAcceleration();

		Vector finalForce = velocity;

		if (Configuration.FORCES) {

			finalForce = GeometricCalculator.addVectors(velocity, acceleration);
		}

		// cod.i(pedestrianInformation.getStaticInformation().getId()+" VELO ACC FF:
		// ",Arrays.asList(velocity,acceleration, finalForce));

		VectorXY shift = GeometricCalculator.changeVector(finalForce);

		double x = pedestrianInformation.getVariableInformation().getPosition().getX();
		double y = pedestrianInformation.getVariableInformation().getPosition().getY();

		return new Position(x + shift.getX(), y + shift.getY());

	}

	public Vector getDesireVelocity(double collisionDistance, double alpha, int i) {
		double result = pedestrianInformation.getStaticInformation().getComfortableSpeed();
		// cod.i("v: "+i,Arrays.asList(goalVelocity(),
		// collisionVelocity(collisionDistance), result));
		Vector v = new Vector(alpha, Arrays.asList(goalVelocity(), collisionVelocity(collisionDistance), result)
				.stream().mapToDouble(d -> d).min().getAsDouble());
		return v;
	}

	private double collisionVelocity(double collisionDistance) {
		return collisionDistance / pedestrianInformation.getStaticInformation().getRelaxationTime();
	}

	private double goalVelocity() {
		double distance = GeometricCalculator.distance.apply(
				pedestrianInformation.getVariableInformation().getPosition(),
				pedestrianInformation.getVariableInformation().getDestinationPoint());
		return distance / pedestrianInformation.getStaticInformation().getRelaxationTime();
	}

	public List<Vector> getForces() throws AngleOutOfRangeException {

		List<Vector> neighborsForces = forceCal.getForceNeighbours(pedestrianInformation);
		List<Vector> wallForces = forceCal.getForceWalls(pedestrianInformation);

		Vector nForce = getForcesSum(neighborsForces);
		nForce.setValue(nForce.getValue() / pedestrianInformation.getStaticInformation().getMass());

		Vector wForce = getForcesSum(wallForces);
		wForce.setValue(wForce.getValue() / pedestrianInformation.getStaticInformation().getMass());
		return Arrays.asList(nForce, wForce);
	}

	public double getForcesValue(List<Vector> forces) {
		double forceValue = 0;
		for (Vector force : forces) {
			forceValue = forceValue + force.getValue();
		}
		return forceValue;
	}

	public Vector getDesireAcceleration(Vector vdes, List<Vector> forces) throws AngleOutOfRangeException {
		Vector velocity = pedestrianInformation.getVariableInformation().getDesiredSpeed();
		Vector acceleration = GeometricCalculator.subtractVectors(vdes, velocity);
		//
		// List<Vector> neighborsForces =
		// forceCal.getForceNeighbours(pedestrianInformation);
		// List<Vector> wallForces = forceCal.getForceWalls(pedestrianInformation);
		//
		// Vector nForce = getForcesSum(neighborsForces);
		// nForce.setValue(nForce.getValue() /
		// pedestrianInformation.getStaticInformation().getMass());
		//
		// Vector wForce = getForcesSum(wallForces);
		// wForce.setValue(wForce.getValue() /
		// pedestrianInformation.getStaticInformation().getMass());

		// double crowdPressure = nForce.getValue() + wForce.getValue();

		acceleration
				.setValue(acceleration.getValue() / pedestrianInformation.getStaticInformation().getRelaxationTime());
		for (Vector force : forces) {
			acceleration = GeometricCalculator.addVectors(acceleration, force);

		}
		// acceleration = GeometricCalculator.addVectors(acceleration, wForce);

		// if(pedestrianInformation.getStaticInformation().getId() == 1) {
		// cod.i("ACC:", acceleration);
		// }

		if (acceleration.getValue() > velocity.getValue()) {
			acceleration.setValue(velocity.getValue());
		}
		if (Configuration.MAX_ACCELERATION_VALUE < acceleration.getValue()) {
			acceleration.setValue(Configuration.MAX_ACCELERATION_VALUE);
		}

		return acceleration;
	}

	private Vector getForcesSum(List<Vector> forces) throws AngleOutOfRangeException {
		Vector force = new Vector(Double.NaN, 0.0);
		// cod.i(forces);
		for (Vector f : forces) {
			// cod.i("FORCES",crowdPressureCalforces);
			force = GeometricCalculator.addVectors(force, f);
		}
		return force;
	}

	public double getCrowdPressure(double forcesValue) {
		// cod.i(pedestrianInformation.getStaticInformation().getId());
		return crowdPressureCal.getCrowdPressure(forcesValue);
	}

}
