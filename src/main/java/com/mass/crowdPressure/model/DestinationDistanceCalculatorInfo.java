package com.mass.crowdPressure.model;

public class DestinationDistanceCalculatorInfo {
	private double alpha;
	private double destinationAngle;
	private double horizontDistance;
	private double collisionDistanceValue;

	public DestinationDistanceCalculatorInfo(double alpha, double destinationAngle, double horizontDistance,
			double collisionDistanceValue) {
		super();
		this.alpha = alpha;
		this.destinationAngle = destinationAngle;
		this.horizontDistance = horizontDistance;
		this.collisionDistanceValue = collisionDistanceValue;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public double getDestinationAngle() {
		return destinationAngle;
	}

	public void setDestinationAngle(double destinationAngle) {
		this.destinationAngle = destinationAngle;
	}

	public double getHorizontDistance() {
		return horizontDistance;
	}

	public void setHorizontDistance(double horizontDistance) {
		this.horizontDistance = horizontDistance;
	}

	public double getCollisionDistanceValue() {
		return collisionDistanceValue;
	}

	public void setCollisionDistanceValue(double collisionDistanceValue) {
		this.collisionDistanceValue = collisionDistanceValue;
	}

}
