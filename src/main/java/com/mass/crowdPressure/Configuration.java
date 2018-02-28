package com.mass.crowdPressure;

import java.util.function.Function;

import com.mass.crowdPressure.calculators.DestinationDistance;
import com.mass.crowdPressure.model.DestinationDistanceCalculatorInfo;
import com.mass.crowdPressure.model.Position;

public class Configuration {

    // PEDESTRIAN
    public static final double DEFAULT_PEDESTRIAN_MASS = 360.0;
    public static final double DEFAULT_PEDESTRIAN_COMFORTABLE_SPEED = 0.14;
    public static final double DEFAULT_PEDESTRIAN_VISION_ANGLE = 0.38;
    public static final double DEFAULT_PEDESTRIAN_HORIZON_DISTANCE = 40;
    public static final double DEFAULT_PEDESTRIAN_RELAXATION_TIME = 1;
    public static final double MASS_RADIUS_RATIO = 320.0;
    public static final Double MAX_ACCELERATION_VALUE = 0.5;
    public static final Position DEFAULT_DESTINATION_POSITION = new Position(45, 8);



    // OTHER
    public static final double ANGLE_GRANULATION = 0.12;
    public static final Double START_ANGLE = 0.0;
    public static final Double END_ANGLE = 2.0;
    public static final Double MAX_DISTANCE_TO_GOAL = 1.8;
    public static final Double K_PARAMETER = 3 * Math.pow(10, 3); // 5 * Math.pow(10, 3)
    public static final double PRECISION_OF_CALCULATIONS = 0.001;
    public static final Function<DestinationDistanceCalculatorInfo, Double> DESTINATION_DISTANCE_FUNCTION = DestinationDistance.originalDDFunction;
    public static final boolean FORCES = true;
    public static final int PERCENT_PROBABILITY_OF_CHANGING_VISION_CENTER = 5;
    public static final int PERCENT_PROBABILITY_OF_COMPUTE_NOT_FOR_WALLS = 5;
    public static final boolean CHANGE_VISION_CENTER_RANDOM = true;
    public static Double DESTINATION_ANGLE_RANGE = 2 * 0.1;
    public static final Double  WALL_DISTANCE_TO_CHANGE_DIRECTION = 4.0;

    // SYMULATION
    public static final Symulation SYMULATION_TYPE = Symulation.SYM_PX_VS_PX_W0;
    public static final int SCALE_VALUE = 10;
    public static final boolean SHOW_VISION_RADIUS = false;
    public static final int INITIAL_FPS = 70;

}
