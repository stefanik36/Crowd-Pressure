package com.mass.crowdPressure.calculators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.pedestrian.PedestrianInformation;

public class PedestrianCrossPoints {
	private double xi;
	private double yi;
	private double tanA;
	private double B;

	public PedestrianCrossPoints(Double alpha, PedestrianInformation pedestrianInformation) {
		super();
		xi = pedestrianInformation.getPosition().getX();
		yi = pedestrianInformation.getPosition().getY();
		tanA = Math.tan(alpha * Math.PI);
		B = (yi - tanA * xi);
	}

	public List<Position> getNeighborAllCrossPoints(PedestrianInformation neighborInformation) {
		List<Double> coords = calculateNeighborAllCrossPointCoord(neighborInformation);
		List<Position> crossPoints = calculateNeighborCrossPoints(coords, neighborInformation);
		return crossPoints;
	}

	List<Position> calculateNeighborCrossPoints(List<Double> coords, PedestrianInformation neighborInformation) {
		Function<Double, Position> positionFromCoord = (Double coordX) -> {
			return new Position(coordX, tanA * (coordX - xi) + yi);
		};
		return coords.stream().map(positionFromCoord).collect(Collectors.toList());
	}

	List<Double> calculateNeighborAllCrossPointCoord(PedestrianInformation neighborInformation) {

		double underSqrt = calculateCrossPointCoordUnderSqrt(neighborInformation);

		List<Double> result = new ArrayList<>();
		if (underSqrt > 0) {
			double sqrt = Math.sqrt(underSqrt);
			result.addAll(Arrays.asList(calculateNeighborCrossPointCoord(sqrt, neighborInformation),
					calculateNeighborCrossPointCoord(-sqrt, neighborInformation)));
		} else if (underSqrt == 0) {
			result.addAll(Arrays.asList(calculateNeighborCrossPointCoord(0.0, neighborInformation)));
		}
		return result;
	}

	double calculateNeighborCrossPointCoord(Double sqrt, PedestrianInformation neighborInformation) {
		double xn = neighborInformation.getPosition().getX();
		double yn = neighborInformation.getPosition().getY();
		double result = sqrt;
		result += xn;
		result += tanA * yn;
		result += -tanA * B;
		result = result / (tanA * tanA + 1);
		return result;
	}

	double calculateCrossPointCoordUnderSqrt(PedestrianInformation neighborInformation) {
		double xn = neighborInformation.getPosition().getX();
		double yn = neighborInformation.getPosition().getY();
		double rn = neighborInformation.getRadius();

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
