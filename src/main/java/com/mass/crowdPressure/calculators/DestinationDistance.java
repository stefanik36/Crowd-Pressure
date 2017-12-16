package com.mass.crowdPressure.calculators;

public class DestinationDistance {

	public double getDestinationDistanceFunction(double alpha, double destinationAngle, double horizontDistance,
			double collisionDistanceValue) {

		// d(alpha) = (d_max)^2 + (f(alpha))^2 - 2 * d_max * f(alpha) * cos(alpha_0 -
		// alpha)
		double result = horizontDistance * horizontDistance + collisionDistanceValue * collisionDistanceValue - 2
				* horizontDistance * collisionDistanceValue * Math.cos(destinationAngle * Math.PI - alpha * Math.PI);

		return result;
	}

}