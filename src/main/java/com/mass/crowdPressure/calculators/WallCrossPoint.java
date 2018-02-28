package com.mass.crowdPressure.calculators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.calculators.figures.LinePointAngle;
import com.mass.crowdPressure.calculators.figures.LineTwoPoints;
import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.map.StraightWall;
import com.mass.crowdPressure.model.map.Wall;
import com.mass.crowdPressure.model.pedestrian.PedestrianInformation;

public class WallCrossPoint {
	private final static COD cod = CODFactory.setLevelOfDepression(2);
	private Double alpha;
	private Position pedestrianPosition;

	public WallCrossPoint(Double alpha, PedestrianInformation pedestrianInformation) {
		this.alpha = alpha;
		this.pedestrianPosition = pedestrianInformation.getVariableInformation().getPosition();
	}

	public List<Position> getWallCrossPoints(Wall w) {
		if (w instanceof StraightWall) {
			StraightWall sw = (StraightWall) w;
			Optional<Position> op = GeometricCalculator.crossPointTwoLines.apply(
					new LinePointAngle(pedestrianPosition, alpha),
					new LineTwoPoints(sw.getStartPosition(), sw.getEndPosition()));
			if (op.isPresent()) {
				return Arrays.asList(op.get());
			} else {
				return new ArrayList<>();
			}
		}
		// TODO not straight walls
		return null;

	}

}
