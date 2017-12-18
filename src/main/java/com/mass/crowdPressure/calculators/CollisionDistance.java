package com.mass.crowdPressure.calculators;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.map.Wall;
import com.mass.crowdPressure.model.pedestrian.Pedestrian;
import com.mass.crowdPressure.model.pedestrian.PedestrianInformation;

public class CollisionDistance {

	private final static COD cod = CODFactory.setLevelOfDepression(2);
	private Environment environment;

	public CollisionDistance(Environment environment) {
		this.environment = environment;
	}

	public double getCollistionDistanceValue(Double alpha, PedestrianInformation pedestrianInformation) {
		Double minimalDistance = pedestrianInformation.getStaticInformation().getHorizontDistance();
		int id = pedestrianInformation.getStaticInformation().getId();
		if (environment == null) {
			return minimalDistance;
		}
		// cod.i(pedestrianInformation);
		// TODO inside geometric function
		PedestrianCrossPoints pedestrianCrossPoints = new PedestrianCrossPoints(alpha, pedestrianInformation);
		WallCrossPoint wallCrossPoint = new WallCrossPoint(alpha, pedestrianInformation);

		Position pedestrianPosition = pedestrianInformation.getVariableInformation().getPosition();

		minimalDistance = neighboursDistance(pedestrianCrossPoints, pedestrianPosition, minimalDistance, id);
		// System.out.print("alpha: " + alpha + " ");
		minimalDistance = getWallDistance(wallCrossPoint, pedestrianPosition, minimalDistance);
		// System.out.println("" + alpha + ": "+minimalDistance);

		return minimalDistance;
	}

	private double getWallDistance(WallCrossPoint wallCrossPoint, Position pedestrianPosition, Double minimalDistance) {
		for (Wall w : environment.getMap().getWalls()) {
			List<Position> crossPoints = wallCrossPoint.getWallCrossPoints(w);
			minimalDistance = getMinDistance(pedestrianPosition, minimalDistance, crossPoints);

			// if (!crossPoints.isEmpty()) {
			// cod.i("cp md pp", Arrays.asList(crossPoints.get(0), minimalDistance,
			// pedestrianPosition));
			// } else {
			// cod.i("cp md pp", Arrays.asList(null, minimalDistance, pedestrianPosition));
			// }
		}
		return minimalDistance;
	}

	private Double neighboursDistance(PedestrianCrossPoints pedestrianCrossPoints, Position pedestrianPosition,
			Double minimalDistance, int id) {
		// TODO neighbor pedestrian velocity
		for (Pedestrian p : environment.getPedestrians()) {
			if (p.getPedestrianInformation().getStaticInformation().getId() != id) {
				List<Position> crossPoints = pedestrianCrossPoints
						.getNeighborAllCrossPoints(p.getPedestrianInformation());
				minimalDistance = getMinDistance(pedestrianPosition, minimalDistance, crossPoints);
			}
		}
		return minimalDistance;
	}

	private Double getMinDistance(Position pedestrianPosition, Double minimalDistance, List<Position> crossPoints) {
		OptionalDouble min = crossPoints.stream()
				.mapToDouble(cp -> GeometricCalculator.distance.apply(pedestrianPosition, cp)).min();
		if ((min.isPresent()) && (min.getAsDouble() < minimalDistance)) {
			minimalDistance = min.getAsDouble();
		}
		return minimalDistance;
	}

}
