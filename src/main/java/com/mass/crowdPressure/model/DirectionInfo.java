package com.mass.crowdPressure.model;

public class DirectionInfo {
	private Double alpha;
	private MinimumDistance collisionDistance;
	private Double destinationDistance;

	public DirectionInfo(double alpha, MinimumDistance collisionDistanceValue, double destinationDistance) {
		super();
		this.setAlpha(alpha);
		this.setCollisionDistance(collisionDistanceValue);
		this.setDestinationDistance(destinationDistance);
	}

	public DirectionInfo() {
		super();
	}

	public MinimumDistance getCollisionDistance() {
		return collisionDistance;
	}

	public void setCollisionDistance(MinimumDistance collisionDistance) {
		this.collisionDistance = collisionDistance;
	}

	public Double getAlpha() {
		return alpha;
	}

	public void setAlpha(Double alpha) {
		this.alpha = alpha;
	}

	public Double getDestinationDistance() {
		return destinationDistance;
	}

	public void setDestinationDistance(Double destinationDistance) {
		this.destinationDistance = destinationDistance;
	}

}
