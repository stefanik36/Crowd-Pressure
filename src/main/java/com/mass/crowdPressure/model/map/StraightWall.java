package com.mass.crowdPressure.model.map;

import com.mass.crowdPressure.model.Position;

public class StraightWall implements Wall{
	private Position startPosition;
	private Position endPosition;

	public StraightWall(Position startPosition, Position endPosition) {
		super();
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

	@Override
	public String toString() {
		return "Wall(start(" + this.getStartPosition().getX() + ", " + this.getStartPosition().getY() +
				"), end(" + this.getEndPosition().getX() + ", " + this.getEndPosition().getY() + "\n";
	}
}
