package com.agh.cp.model;

public class DirectionInfo {
	private double alpha;
	private MinimumDistance collisionDistance;
	private Double destinationDistance;

	public DirectionInfo(double alpha, MinimumDistance collisionDistanceValue, Double destinationDistance) {
		super();
		this.alpha = alpha;
		this.setCollisionDistance(collisionDistanceValue);
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

	public Double getDestinationDistance() {
		return destinationDistance;
	}

	public void setDestinationDistance(Double destinationDistance) {
		this.destinationDistance = destinationDistance;
	}

	public MinimumDistance getCollisionDistance() {
		return collisionDistance;
	}

	public void setCollisionDistance(MinimumDistance collisionDistance) {
		this.collisionDistance = collisionDistance;
	}

}
