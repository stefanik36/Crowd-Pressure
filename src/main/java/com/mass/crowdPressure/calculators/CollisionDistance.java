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
		Double horizontDistance = pedestrianInformation.getStaticInformation().getHorizontDistance();
		int id = pedestrianInformation.getStaticInformation().getId();
		if (environment == null) {
			return new MinimumDistance(horizontDistance, Optional.empty());
		}
		// cod.i(pedestrianInformation);
		// TODO inside geometric function
		PedestrianCrossPoints pedestrianCrossPoints = new PedestrianCrossPoints(alpha, pedestrianInformation);
		WallCrossPoint wallCrossPoint = new WallCrossPoint(alpha, pedestrianInformation);

		Position pedestrianPosition = pedestrianInformation.getVariableInformation().getPosition();

		Optional<Double> nDistance = neighboursDistance(pedestrianCrossPoints, pedestrianPosition, id);
		Optional<Double> wDistance = getWallDistance(wallCrossPoint, pedestrianPosition);

		MinimumDistance minimumDistance = getminimumDistance(horizontDistance, nDistance, wDistance);

		return minimumDistance;
	}

	private MinimumDistance getminimumDistance(Double horizontDistance, Optional<Double> nDistance,
			Optional<Double> wDistance) {
		Double minDistanceValue = horizontDistance;
		for (Optional<Double> d : Arrays.asList(nDistance, wDistance)) {
			if (d.isPresent()) {
				minDistanceValue = minDistanceValue < d.get() ? minDistanceValue : d.get();
			}
		}
		if (wDistance.isPresent()) {
			if (wDistance.get() < horizontDistance) {
				return new MinimumDistance(minDistanceValue, wDistance);
			}
		}
		return new MinimumDistance(minDistanceValue, Optional.empty());
	}

	private Optional<Double> getWallDistance(WallCrossPoint wallCrossPoint, Position pedestrianPosition) {
		Optional<Double> optionalGlobalMin = Optional.empty();
		for (Wall w : environment.getMap().getWalls()) {
			optionalGlobalMin = resolveMinimumOfOptionals(optionalGlobalMin,
					getMinDistance(pedestrianPosition, wallCrossPoint.getWallCrossPoints(w)));
		}
		return optionalGlobalMin;
	}

	private Optional<Double> neighboursDistance(PedestrianCrossPoints pedestrianCrossPointsCal,
			Position pedestrianPosition, int id) throws AngleOutOfRangeException {

		Optional<Double> optionalGlobalMin = Optional.empty();

		for (Pedestrian p : environment.getPedestrians()) {
			if (p.getPedestrianInformation().getStaticInformation().getId() != id) {
				optionalGlobalMin = resolveMinimumOfOptionals(optionalGlobalMin, getMinDistance(pedestrianPosition,
						pedestrianCrossPointsCal.getNeighborAllCrossPoints(p.getPedestrianInformation())));
			}
		}

		// if (minimalDistance < Configuration.DEFAULT_PEDESTRIAN_HORIZON_DISTANCE) {
		// minimalDistance = Math.sqrt(minimalDistance);
		// }

		return optionalGlobalMin;
	}

	private Optional<Double> resolveMinimumOfOptionals(Optional<Double> optionalGlobalMin,
			Optional<Double> optionalActualMin) {
		if (optionalGlobalMin.isPresent()) {
			if (optionalActualMin.isPresent()) {
				Double actualMin = optionalActualMin.get();
				if (actualMin < optionalGlobalMin.get()) {
					optionalGlobalMin = Optional.of(actualMin);
				}
			}
		} else {
			optionalGlobalMin = optionalActualMin;
		}
		return optionalGlobalMin;
	}

	private Optional<Double> getMinDistance(Position pedestrianPosition, List<Position> crossPoints) {
		OptionalDouble om = crossPoints.stream()
				.mapToDouble(cp -> GeometricCalculator.distance.apply(pedestrianPosition, cp)).min();
		if (om.isPresent()) {
			return Optional.of(om.getAsDouble());
		}
		return Optional.empty();
	}

}
