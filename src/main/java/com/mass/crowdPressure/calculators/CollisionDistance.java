package com.mass.crowdPressure.calculators;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.exceptions.AngleOutOfRangeException;
import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.MinimumDistance;
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

	public MinimumDistance getCollistionDistanceValue(Double alpha, PedestrianInformation pedestrianInformation)
			throws AngleOutOfRangeException {
		Double minimalDistance = pedestrianInformation.getStaticInformation().getHorizontDistance();
		int id = pedestrianInformation.getStaticInformation().getId();
		if (environment == null) {
			return new MinimumDistance(false, minimalDistance, Optional.empty());
		}
		// cod.i(pedestrianInformation);
		// TODO inside geometric function
		PedestrianCrossPoints pedestrianCrossPoints = new PedestrianCrossPoints(alpha, pedestrianInformation);
		WallCrossPoint wallCrossPoint = new WallCrossPoint(alpha, pedestrianInformation);

		Position pedestrianPosition = pedestrianInformation.getVariableInformation().getPosition();

		Optional<Double> nDistance = neighboursDistance(pedestrianCrossPoints, pedestrianPosition, minimalDistance, id);
		Optional<Double> wDistance = getWallDistance(wallCrossPoint, pedestrianPosition, minimalDistance);
		boolean emptySpace = nDistance.isPresent() || wDistance.isPresent();
		if (nDistance.isPresent()) {
			if (wDistance.isPresent()) {
				minimalDistance = nDistance.get() < wDistance.get() ? nDistance.get() : wDistance.get();
			}
			minimalDistance = nDistance.get();

		}
		MinimumDistance md = new MinimumDistance(emptySpace, minimalDistance, wDistance);

		// System.out.print("alpha: " + alpha + " ");
		// if(getWallDistance(wallCrossPoint, pedestrianPosition,
		// minimalDistance)<minimalDistance) {
		// return -1;
		// }
		// System.out.println("" + alpha + ": "+minimalDistance);

		return md;
	}

	private Optional<Double> getWallDistance(WallCrossPoint wallCrossPoint, Position pedestrianPosition,
			Double minimalDistance) {
		Optional<Double> min = Optional.empty();
		for (Wall w : environment.getMap().getWalls()) {
			List<Position> crossPoints = wallCrossPoint.getWallCrossPoints(w);
			// cod.i("CROS",crossPoints);
			min = getMinDistance(pedestrianPosition, minimalDistance, crossPoints);

			// if (!crossPoints.isEmpty()) {
			// cod.i("cp md pp", Arrays.asList(crossPoints.get(0), minimalDistance,
			// pedestrianPosition));
			// } else {
			// cod.i("cp md pp", Arrays.asList(null, minimalDistance, pedestrianPosition));
			// }
		}
		return min;
	}

	private Optional<Double> neighboursDistance(PedestrianCrossPoints pedestrianCrossPointsCal,
			Position pedestrianPosition, Double minimalDistance, int id) throws AngleOutOfRangeException {
		Optional<Double> min = Optional.empty();
		for (Pedestrian p : environment.getPedestrians()) {
			if (p.getPedestrianInformation().getStaticInformation().getId() != id) {
				List<Position> crossPoints = pedestrianCrossPointsCal
						.getNeighborAllCrossPoints(p.getPedestrianInformation());
				Optional<Double> optional = getMinDistance(pedestrianPosition, minimalDistance, crossPoints);
				min = optional;

			}
		}

		// if (minimalDistance < Configuration.DEFAULT_PEDESTRIAN_HORIZON_DISTANCE) {
		// minimalDistance = Math.sqrt(minimalDistance);
		// }

		return min;
	}

	private Optional<Double> getMinDistance(Position pedestrianPosition, Double minimalDistance,
			List<Position> crossPoints) {
		OptionalDouble min = crossPoints.stream()
				.mapToDouble(cp -> GeometricCalculator.distance.apply(pedestrianPosition, cp)).min();

		if ((min.isPresent()) && (min.getAsDouble() < minimalDistance)) {
			// return Optional.of(Math.pow(min.getAsDouble(),
			// Configuration.WALL_DISTANCE_POWER));
			return Optional.of(min.getAsDouble());

		}
		// cod.i("WALL",minimalDistance);
		return Optional.empty();
	}

}
