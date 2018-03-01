package com.agh.cp.calculators.figures;

import com.agh.cp.model.Position;

public class LineTwoPoints {
	private Position startPosition;
	private Position endPosition;

	public LineTwoPoints(Position startPosition, Position endPosition) {
		this.startPosition = startPosition;
		this.endPosition = endPosition;
	}

	public Position getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(Position startPosition) {
		this.startPosition = startPosition;
	}

	public Position getEndPosition() {
		return endPosition;
	}

	public void setEndPosition(Position endPosition) {
		this.endPosition = endPosition;
	}
}
