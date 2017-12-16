package com.mass.crowdPressure.model.pedestrian;

import com.mass.crowdPressure.calculators.Configuration;
import com.mass.crowdPressure.calculators.GemoetricCalculator;
import com.mass.crowdPressure.calculators.GeometricCalculatorTest;
import com.mass.crowdPressure.calculators.PedestrianCalculator;
import com.mass.crowdPressure.model.Position;

public class VariableInformation {

	private double visionCenter;
	private double destinationAngle;
	private Position destinationPoint;
	private Position position;
	private Position nextPosition;
	private double desiredDirection;
	private double desiredSpeed;
	private boolean finished;

	public VariableInformation(double visionCenter, Position destinationPoint, Position position) {
		this.visionCenter = visionCenter;
		this.destinationPoint = destinationPoint;
		this.position = position;
		this.nextPosition = position;
		this.destinationAngle = GemoetricCalculator.calculateDestinationAngle.apply(nextPosition, destinationPoint);
		this.setFinished(GemoetricCalculator.isBigger.apply(
				GemoetricCalculator.distance.apply(position, destinationPoint), Configuration.MAX_DISTANCE_TO_GOAL));
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

	public double getDesiredSpeed() {
		return desiredSpeed;
	}

	public void setDesiredSpeed(double desiredSpeed) {
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
}
