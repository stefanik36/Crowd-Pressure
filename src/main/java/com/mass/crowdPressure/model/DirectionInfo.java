package com.mass.crowdPressure.model;

public class DirectionInfo {
	private double alpha;
	private double collisionDistance;
	private double destinationDistance;

	public DirectionInfo(double alpha, double collisionDistance, double destinationDistance) {
		super();
		this.alpha = alpha;
		this.collisionDistance = collisionDistance;
		this.destinationDistance = destinationDistance;
	}
	public DirectionInfo() {
		super();
	}
	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public double getCollisionDistance() {
		return collisionDistance;
	}

	public void setCollisionDistance(double collisionDistance) {
		this.collisionDistance = collisionDistance;
	}

	public Double getDestinationDistance() {
		return destinationDistance;
	}

	public void setDestinationDistance(double destinationDistance) {
		this.destinationDistance = destinationDistance;
	}

}
