package com.mass.crowdPressure.calculators;

import java.util.Arrays;
import java.util.function.Function;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.model.DestinationDistanceCalculatorInfo;
import com.mass.crowdPressure.model.MinimumDistance;

public class DestinationDistance {
	private static final COD cod = CODFactory.getCOD();

	public static Function<DestinationDistanceCalculatorInfo, Double> originalDDFunction = (
			DestinationDistanceCalculatorInfo info) -> {
		double a = info.getAlpha();
		double da = info.getDestinationAngle();
		double hd = info.getHorizontDistance();
		double cdv = info.getCollisionDistanceValue().getMinimumDistanceObstacles();
		return hd * hd + cdv * cdv - 2 * hd * cdv * Math.cos(da * Math.PI - a * Math.PI);
	};
	
	public static Function<DestinationDistanceCalculatorInfo, Double> modifiedDDFunction = (
			DestinationDistanceCalculatorInfo info) -> {
		double a = info.getAlpha();
		double da = info.getDestinationAngle();
		double hd = info.getHorizontDistance();
		double cdv = info.getCollisionDistanceValue().getMinimumDistanceObstacles();
		
		double lParameter = 0.0;
		if(cdv<hd) {
			lParameter = hd-cdv;
			lParameter = lParameter*100;
//			return 100000000000000.0;
		}
		double result = hd * hd + cdv * cdv - 2 * hd * cdv * Math.cos(da * Math.PI - a * Math.PI) + lParameter;
//		cod.i("alpha, L, cdv, hd, result ",Arrays.asList(a,lParameter,cdv,hd, result));
		return result;
	};

	public double getDestinationDistanceFunction(double alpha, double destinationAngle, double horizontDistance,
			MinimumDistance collisionDistanceValue) {

		DestinationDistanceCalculatorInfo ddci = new DestinationDistanceCalculatorInfo(alpha, destinationAngle,
				horizontDistance, collisionDistanceValue);

		return Configuration.ddFunction.apply(ddci);
	}

}
