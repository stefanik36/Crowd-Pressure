package com.mass.crowdPressure.model;

public class DestinationDistanceCalculatorInfo {
	private double alpha;
	private double destinationAngle;
	private double horizontDistance;
	private MinimumDistance collisionDistanceValue;

	public DestinationDistanceCalculatorInfo(double alpha, double destinationAngle, double horizontDistance,
			MinimumDistance collisionDistanceValue2) {
		super();
		this.alpha = alpha;
		this.destinationAngle = destinationAngle;
		this.horizontDistance = horizontDistance;
		this.setCollisionDistanceValue(collisionDistanceValue2);
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

	public MinimumDistance getCollisionDistanceValue() {
		return collisionDistanceValue;
	}

	public void setCollisionDistanceValue(MinimumDistance collisionDistanceValue) {
		this.collisionDistanceValue = collisionDistanceValue;
	}

	

}
