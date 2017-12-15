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
import com.mass.crowdPressure.model.pedestrian.Pedestrian;
import com.mass.crowdPressure.model.pedestrian.PedestrianInformation;

public class CollisionDistance {

	private final static COD cod = CODFactory.setLevelOfDepression(2);
	BiFunction<Position, Position, Double> distance = (Position a, Position b) -> {
		return Math.sqrt(Math.pow((b.getX() - a.getX()), 2) + Math.pow((b.getY() - a.getY()), 2));
	};

	public double getCollistionDistanceValue(Environment environment, Double alpha,
			PedestrianInformation pedestrianInformation) {
		PedestrianCrossPoints pedestrianCrossPoints = new PedestrianCrossPoints(alpha, pedestrianInformation);

		Position pedestrianPosition = pedestrianInformation.getPosition();

		Double minimalDistance = pedestrianInformation.getHorizontDistance();

		if (environment != null) {
			minimalDistance = neighboursDistance(environment, pedestrianCrossPoints, pedestrianPosition,
					minimalDistance);
		}
		// TODO walls

		return minimalDistance;
	}

	private Double neighboursDistance(Environment environment, PedestrianCrossPoints pedestrianCrossPoints,
			Position pedestrianPosition, Double minimalDistance) {
		// TODO neighbor pedestrian velocity
		for (Pedestrian p : environment.getPedestrians()) {
			List<Position> crossPoints = pedestrianCrossPoints.getNeighborAllCrossPoints(p.getPedestrianInformation());

			OptionalDouble neighborMin = crossPoints.stream().mapToDouble(cp -> distance.apply(pedestrianPosition, cp))
					.min();
			if ((neighborMin.isPresent()) && (neighborMin.getAsDouble() < minimalDistance)) {
				minimalDistance = neighborMin.getAsDouble();
			}
			// cod.i(p.getPedestrianInformation().getId() + " :" + alpha,
			// Arrays.asList(minimalDistance, crossPoints));
		}
		return minimalDistance;
	}

}
