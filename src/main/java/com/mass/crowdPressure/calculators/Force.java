package com.mass.crowdPressure.calculators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.calculators.figures.LineTwoPoints;
import com.mass.crowdPressure.calculators.figures.Vector;
import com.mass.crowdPressure.calculators.figures.VectorXY;
import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.map.StraightWall;
import com.mass.crowdPressure.model.map.Wall;
import com.mass.crowdPressure.model.pedestrian.Pedestrian;
import com.mass.crowdPressure.model.pedestrian.PedestrianInformation;

public class Force {
	private Environment environment;
	private static final COD cod = CODFactory.getCOD();

	public Force(Environment environment) {
		this.environment = environment;
	}

	public List<Vector> getForceNeighbours(PedestrianInformation pedestrianInformation) {
		List<Vector> forces = new ArrayList<>();
		for (Pedestrian p : environment.getPedestrians()) {
			if (p.getPedestrianInformation().getStaticInformation().getId() != pedestrianInformation
					.getStaticInformation().getId()) {
				Optional<Vector> oForce = calculateForce(pedestrianInformation, p.getPedestrianInformation());
				if (oForce.isPresent()) {
					forces.add(oForce.get());
				}
			}
		}
		return forces;
	}

	private Optional<Vector> calculateForce(PedestrianInformation pedestrianInfo, PedestrianInformation neighborInfo) {
		
		Double radiusSum = pedestrianInfo.getStaticInformation().getRadius()
				+ neighborInfo.getStaticInformation().getRadius();
		
//		cod.i("POS", Arrays.asList(pedestrianInfo.getVariableInformation().getPosition(),
//				neighborInfo.getVariableInformation().getPosition()));
		Double distance = GeometricCalculator.distance.apply(pedestrianInfo.getVariableInformation().getPosition(),
				neighborInfo.getVariableInformation().getPosition());

		Double force = (radiusSum - distance);	
//		cod.i(pedestrianInfo.getStaticInformation().getId()+" RS, D, F", Arrays.asList(radiusSum, distance, force));
		force = force * Configuration.K_PARAMETER;
		if (force > 0) {
			VectorXY direction = GeometricCalculator.subtractVectors(
					new VectorXY(pedestrianInfo.getVariableInformation().getPosition()),
					new VectorXY(neighborInfo.getVariableInformation().getPosition()));
			Vector forceVector = GeometricCalculator.changeVector(direction);
			forceVector.setValue(force);
			return Optional.of(forceVector);
		}
		return Optional.empty();
	}

	public List<Vector> getForceWalls(PedestrianInformation pedestrianInformation) {
		List<Vector> forces = new ArrayList<>();
		for (Wall w : environment.getMap().getWalls()) {
			if (w instanceof StraightWall) {
				StraightWall sw = (StraightWall) w;
				Optional<Vector> oForce = calculateForce(pedestrianInformation, sw);
				if (oForce.isPresent()) {
					forces.add(oForce.get());
				}
			}
		}
		return forces;
	}

	private Optional<Vector> calculateForce(PedestrianInformation pedestrianInfo, StraightWall wall) {
		Optional<Vector> optionalVector = GeometricCalculator.vectorStraightPoint.apply(
				pedestrianInfo.getVariableInformation().getPosition(),
				new LineTwoPoints(wall.getStartPosition(), wall.getEndPosition()));
		
		
		
		
		
		if (optionalVector.isPresent()) {
			Vector forceVector = optionalVector.get();
			Double force = pedestrianInfo.getStaticInformation().getRadius() - forceVector.getValue();
			if (force > 0) {

				force = force * Configuration.K_PARAMETER;

				forceVector.setValue(force);

				// double neutral = 1;
				// VectorXY direction = new VectorXY(
				// (wall.getStartPosition().getX() * neutral) / wall.getStartPosition().getY(),
				// neutral);
				// Vector forceVector = GeometricCalculator.changeVector(direction);
				// forceVector.setValue(force);
				return Optional.of(forceVector);
			}
		}
		return Optional.empty();
	}

}
