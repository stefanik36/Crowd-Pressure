package com.agh.cp.calculators.figures;

import com.agh.cp.model.Position;

public class LinePointAngle {
	private Position point;
	private double angle;

	public LinePointAngle(Position point, double angle) {
		super();
		this.point = point;
		this.angle = angle;
	}

	public Position getPoint() {
		return point;
	}

	public void setPoint(Position point) {
		this.point = point;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

}
