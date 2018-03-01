package com.agh.cp.calculators;

import java.util.function.BiFunction;

import com.app.COD;
import com.app.CODFactory;

public class CrowdPressure {
	private static final COD cod = CODFactory.getCOD();

	private static final double MIN_ACCELERATION_VALUE = 0.0;
	private static final double MULTIPLE = 3;
	private static final double MAX_CROWD_PRESSURE = 1.0;

	private static final BiFunction<Double, Double, Double> multipleByFunction = (Double ac, Double multi) -> {
		return ac * multi;
	};

	public double getCrowdPressure(double forcesValue) {
//		cod.i("FV: ",forcesValue );
		double crowdPressure = 0.0;

		if (forcesValue > MIN_ACCELERATION_VALUE) {
			crowdPressure = multipleByFunction.apply(forcesValue, MULTIPLE);
		} else { 
			crowdPressure = 0.0;
		}
		if (crowdPressure > MAX_CROWD_PRESSURE) {
			crowdPressure = MAX_CROWD_PRESSURE;
		}
//		cod.i("AFT FV: ",Arrays.asList(forcesValue,crowdPressure) );
//		cod.i("ACC, CP ", Arrays.asList(forcesValue,crowdPressure));

		return crowdPressure;
	}

}
