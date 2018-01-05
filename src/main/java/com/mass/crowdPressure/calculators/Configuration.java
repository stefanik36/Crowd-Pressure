package com.mass.crowdPressure.calculators;

public class Configuration {
	
	//PEDESTRIAN
	public static final double DEFAULT_PEDESTRIAN_MASS = 360.0;
	public static final double DEFAULT_PEDESTRIAN_COMFORTABLE_SPEED = 0.1;
	public static final double DEFAULT_PEDESTRIAN_VISION_ANGLE = 0.25;
	public static final double DEFAULT_PEDESTRIAN_HORIZON_DISTANCE = 10;
	public static final double DEFAULT_PEDESTRIAN_RELAXATION_TIME = 1;
	public static final double MASS_RADIUS_RATIO = 320.0;
	public static final Double MAX_ACCELERATION_VALUE = 0.5;
	
	
	//OTHER
	public static final double ANGLE_GRANULATION = 0.1;
	public static final Double START_ANGLE = 0.0;
	public static final Double END_ANGLE = 2.0;
	public static final int INIT_NO_PEDESTRIANS = 10;
	public static final Double MAX_DISTANCE_TO_GOAL = 0.1;
	public static final int STEPS = 10;
	public static final Double K_PARAMETER = 1 * Math.pow(10, 3); //5 * Math.pow(10, 3)
	
	public static final boolean FORCES = true;

}
