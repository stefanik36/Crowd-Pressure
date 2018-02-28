package com.mass.crowdPressure.calculators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.mass.crowdPressure.calculators.figures.VectorXY;
import com.mass.crowdPressure.exceptions.AngleOutOfRangeException;
import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.pedestrian.PedestrianInformation;

public class PedestrianCrossPoints {
	private double xi;
	private double yi;
	private double tanA;
	private double B;
	private double alpha;

	public PedestrianCrossPoints(Double alpha, PedestrianInformation pedestrianInformation) {
		super();
		
		
		
		xi = pedestrianInformation.getVariableInformation().getPosition().getX();
		yi = pedestrianInformation.getVariableInformation().getPosition().getY();
		tanA = Math.tan(alpha * Math.PI);
		this.alpha = alpha;
		B = (yi - tanA * xi);
	}

	public List<Position> getNeighborAllCrossPoints(PedestrianInformation neighborInformation)
			throws AngleOutOfRangeException {

		Position oldPosition = neighborInformation.getVariableInformation().getPosition();
		VectorXY shift = GeometricCalculator
				.changeVector(neighborInformation.getVariableInformation().getDesiredSpeed());

		Position neighborNewPosition = new Position(oldPosition.getX() + shift.getX(),
				oldPosition.getY() + shift.getY());
		// VectorXY shift = new VectorXY(0, 0);

		List<Double> coords = calculateNeighborAllCrossPointCoord(neighborNewPosition,
				neighborInformation.getStaticInformation().getRadius());

		List<Position> crossPoints = calculateNeighborCrossPoints(coords);
		return crossPoints;
	}

	List<Position> calculateNeighborCrossPoints(List<Double> coords) {
		Function<Double, Position> positionFromCoord = (Double coordX) -> {
			return new Position(coordX, tanA * (coordX - xi) + yi);
		};

		return coords.stream().map(positionFromCoord)
				.filter(p -> GeometricCalculator
						.changeVector(GeometricCalculator.vectorFromTwoPoints(new Position(xi, yi), p))
						.getAngle() == alpha)
				.collect(Collectors.toList());
		// return coords.stream().map(positionFromCoord).collect(Collectors.toList());
	}

	List<Double> calculateNeighborAllCrossPointCoord(Position position, double radius) {

		double underSqrt = calculateCrossPointCoordUnderSqrt(position, radius);

		List<Double> result = new ArrayList<>();
		if (underSqrt > 0) {
			double sqrt = Math.sqrt(underSqrt);
			result.addAll(Arrays.asList(calculateNeighborCrossPointCoord(sqrt, position),
					calculateNeighborCrossPointCoord(-sqrt, position)));
		} else if (underSqrt == 0) {
			result.addAll(Arrays.asList(calculateNeighborCrossPointCoord(0.0, position)));
		}
		return result;
	}

	double calculateNeighborCrossPointCoord(Double sqrt, Position position) {
		double xn = position.getX();
		double yn = position.getY();
		double result = sqrt;
		result += xn;
		result += tanA * yn;
		result += -tanA * B;
		result = result / (tanA * tanA + 1);
		return result;
	}

	double calculateCrossPointCoordUnderSqrt(Position position, double rn) {
		double xn = position.getX();
		double yn = position.getY();

		double result = 0;
		result += -xn * xn * tanA * tanA;
		result += 2 * xn * yn * tanA;
		result += -2 * xn * tanA * B;
		result += tanA * tanA * rn * rn;
		result += -yn * yn;
		result += 2 * yn * B;
		result += -B * B;
		result += rn * rn;
		return result;

	}
}
