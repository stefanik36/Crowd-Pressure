package com.mass.crowdPressure.model;

import java.util.Arrays;
import java.util.Optional;

import com.app.COD;
import com.app.CODFactory;

public class MinimumDistance {
	private static final COD cod = CODFactory.getCOD();
	private boolean emptySpace;
	private double minimumDistanceObstacles;
	private Optional<Double> minimumDistanceWall;

	public MinimumDistance(boolean emptySpace, Double minimumDistanceObstacles, Optional<Double> minimumDistanceWall) {
		super();
		this.emptySpace = emptySpace;
		this.minimumDistanceObstacles = minimumDistanceObstacles;
		this.minimumDistanceWall = minimumDistanceWall;
		cod.i("MD:" + minimumDistanceWall.isPresent(), Arrays.asList(minimumDistanceObstacles, minimumDistanceWall));
	}

	public boolean isEmptySpace() {
		return emptySpace;
	}

	public void setEmptySpace(boolean emptySpace) {
		this.emptySpace = emptySpace;
	}

	public double getMinimumDistanceObstacles() {
		return minimumDistanceObstacles;
	}

	public void setMinimumDistanceObstacles(double minimumDistanceObstacles) {
		this.minimumDistanceObstacles = minimumDistanceObstacles;
	}

	public Optional<Double> getMinimumDistanceWall() {
		return minimumDistanceWall;
	}

	public void setMinimumDistanceWall(Optional<Double> minimumDistanceWall) {
		this.minimumDistanceWall = minimumDistanceWall;
	}

}
