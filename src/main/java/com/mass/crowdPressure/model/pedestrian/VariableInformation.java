package com.mass.crowdPressure.model.pedestrian;

import com.mass.crowdPressure.calculators.Configuration;
import com.mass.crowdPressure.calculators.GeometricCalculator;
import com.mass.crowdPressure.calculators.GeometricCalculatorTest;
import com.mass.crowdPressure.calculators.PedestrianCalculator;
import com.mass.crowdPressure.calculators.figures.Vector;
import com.mass.crowdPressure.calculators.figures.VectorXY;
import com.mass.crowdPressure.model.Position;

public class VariableInformation {

	private double visionCenter;
	private double destinationAngle;
	private Position destinationPoint;
	private Position position;
	private Position nextPosition;
	private double desiredDirection;
	private Vector desiredSpeed;
	private Vector desiredAcceleration;
	private boolean finished;

	public VariableInformation(double visionCenter, Position destinationPoint, Position position) {
		this.visionCenter = visionCenter;
		this.destinationPoint = destinationPoint;
		this.position = position;
		this.nextPosition = position;
		this.destinationAngle = GeometricCalculator.calculateAngle.apply(nextPosition, destinationPoint);
		this.setFinished(GeometricCalculator.isBigger.apply(
				GeometricCalculator.distance.apply(position, destinationPoint), Configuration.MAX_DISTANCE_TO_GOAL));
		this.desiredDirection = visionCenter;
		this.desiredSpeed = new Vector(desiredDirection, 0);
		this.desiredAcceleration = new Vector(Double.NaN, 0.0);
	}

	public VariableInformation(Position destinationPoint, Position position) {
		this(GeometricCalculator.calculateAngle.apply(position, destinationPoint), destinationPoint, position);
	}

	public void setDestinationAngle(double destinationAngle) {
		this.destinationAngle = destinationAngle;
	}

	public double getVisionCenter() {
		return visionCenter;
	}

	public void setVisionCenter(double visionCenter) {
		this.visionCenter = visionCenter;
	}

	public Position getDestinationPoint() {
		return destinationPoint;
	}

	public void setDestinationPoint(Position destinationPoint) {
		this.destinationPoint = destinationPoint;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public double getDesiredDirection() {
		return desiredDirection;
	}

	public void setDesiredDirection(double desiredDirection) {
		this.desiredDirection = desiredDirection;
	}

	public Vector getDesiredSpeed() {
		return desiredSpeed;
	}

	public void setDesiredSpeed(Vector desiredSpeed) {
		this.desiredSpeed = desiredSpeed;
	}

	public double getDestinationAngle() {
		return destinationAngle;
	}

	public Position getNextPosition() {
		return nextPosition;
	}

	public void setNextPosition(Position nextPosition) {
		this.nextPosition = nextPosition;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public Vector getDesiredAcceleration() {
		return desiredAcceleration;
	}

	public void setDesiredAcceleration(Vector desiredAcceleration) {
		this.desiredAcceleration = desiredAcceleration;
	}
}
