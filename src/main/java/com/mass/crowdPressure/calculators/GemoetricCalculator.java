package com.mass.crowdPressure.calculators;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.BiFunction;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.calculators.figures.LinePointAngle;
import com.mass.crowdPressure.calculators.figures.LineTwoPoints;
import com.mass.crowdPressure.model.Position;

public class GemoetricCalculator {

	private final static COD cod = CODFactory.setLevelOfDepression(2);

	public static final BiFunction<LinePointAngle, LineTwoPoints, Optional<Position>> crossPointTwoLines = (
			LinePointAngle lpa, LineTwoPoints ltp) -> {

		// TODO alpha =0.5 or 1.5
		// TODO inf points
		double tan = Math.tan(lpa.getAngle() * Math.PI);
		double xi = lpa.getPoint().getX();
		double yi = lpa.getPoint().getY();
		double xp1 = ltp.getStartPosition().getX();
		double yp1 = ltp.getStartPosition().getY();
		double xp2 = ltp.getEndPosition().getX();
		double yp2 = ltp.getEndPosition().getY();

		double numerator = -xi * xp1 * tan + xi * xp2 * tan + yi * xp1 - yi * xp2 - xp1 * yp2 + yp1 * xp2;
		double denominator = -xp1 * tan + yp1 + xp2 * tan - yp2;
		double x = numerator / denominator;
		double y = tan * x + yi - tan * xi;

		if (((x >= xp1 && x <= xp2) || (x >= xp2 && x <= xp1)) && ((y >= yp1 && y <= yp2) || (y >= xp2 && y <= xp1))) {
			if ((lpa.getAngle() < 1 && y > yi) || (lpa.getAngle() > 2 && y < yi)
					|| (lpa.getAngle() == 1 && y == yi && x > xi) || (lpa.getAngle() == 0 && y == yi && x < xi))
				return Optional.of(new Position(x, y));
		}
		return Optional.empty();
	};

	public static BiFunction<Position, Position, Double> distance = (Position a, Position b) -> {
		return Math.sqrt(Math.pow((b.getX() - a.getX()), 2) + Math.pow((b.getY() - a.getY()), 2));
	};

	public static BiFunction<Position, Position, Double> calculateDestinationAngle = (Position pos,
			Position destinationPoint) -> {

		double result = (pos.getY() - destinationPoint.getY()) / (pos.getX() - destinationPoint.getX());
		result = Math.atan(result) / Math.PI;
		if (result < 0) {
			result = 1 + result;
		}
		if (pos.getY() >= destinationPoint.getY()) {
			if ((pos.getX() >= destinationPoint.getX()) || (result != 0)) {
				result = result + 1;
			}
		}
		return result;
	};

	public static BiFunction<Double, Double, Boolean> isBigger = (Double d1, Double d2) -> {
		return d1 < d2;
	};
}