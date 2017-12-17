package com.mass.crowdPressure.calculators;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.Vector;
import com.mass.crowdPressure.model.VectorXY;
import com.mass.crowdPressure.model.map.StraightWall;
import com.mass.crowdPressure.model.map.Wall;
import com.mass.crowdPressure.model.pedestrian.Pedestrian;
import com.mass.crowdPressure.model.pedestrian.PedestrianInformation;

public class Force {
	private Environment environment;

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
		Double force = pedestrianInfo.getStaticInformation().getRadius()
				+ neighborInfo.getStaticInformation().getRadius();
		force = force - GemoetricCalculator.distance.apply(pedestrianInfo.getVariableInformation().getPosition(),
				neighborInfo.getVariableInformation().getPosition());

		force = force * Configuration.K_PARAMETER;

		if (force > 0) {
			VectorXY direction = GemoetricCalculator.subtractVectors(new VectorXY(neighborInfo.getVariableInformation().getPosition()),
					new VectorXY(pedestrianInfo.getVariableInformation().getPosition()));
			Vector forceVector = GemoetricCalculator.changeVector(direction);
			forceVector.setValue(force);
			return Optional.of(forceVector);
		}
		return Optional.empty();
	}

	public List<Vector>  getForceWalls(PedestrianInformation pedestrianInformation) {
		List<Vector> forces = new ArrayList<>();
		for (Wall w : environment.getMap().getWalls()) {
			if (w instanceof StraightWall) {
				StraightWall sw = (StraightWall)w;
				Optional<Vector> oForce = calculateForce(pedestrianInformation, sw);
				if (oForce.isPresent()) {
					 forces.add(oForce.get());
				}
			}
		}
		return forces;
	}

	private Optional<Vector> calculateForce(PedestrianInformation pedestrianInfo, StraightWall wall) {
		Double force = pedestrianInfo.getStaticInformation().getRadius();
		
		
		
		if (force > 0) {
			double neutral = 1;
			VectorXY direction = new VectorXY((wall.getStartPosition().getX()*neutral)/wall.getStartPosition().getY(),neutral);
			Vector forceVector = GemoetricCalculator.changeVector(direction);
			forceVector.setValue(force);
			return Optional.of(forceVector);
		}
		return Optional.empty();
	}

}
