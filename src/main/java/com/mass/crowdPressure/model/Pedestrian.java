package com.mass.crowdPressure.model;

import com.mass.crowdPressure.calculators.CollisionDistance;

import lombok.Getter;
import lombok.Setter;

/*
 * -all obstacles
 * -all other pedestrains
 * 
 * 
 * 
 * 
 * -pedestrian 								i
 * -pedestrian radius 						r_i = m_i / 320
 * -mass of pedestrian						m_1 				60-100kg
 * -position 								x_i
 * -speed 									v_i
 * -comfortable walking speed 				v_i0
 * -destination point						O_i
 * -direction 								alpha is in [-phi, phi]
 * -desired directions 						alpha_des
 * -destination point (vision center)		alpha_0
 * -desired walking speed 					v_des
 * -reflecting collision in direction alpha	f(alpha)
 * -vision field (left and right) 			phi			 
 * -horizon distance						H_i
 * -relaxation time 						tau 				0.5 sek
 * -maximum distance						d_max = H_i
 * -distance								d(alpha)
 * 
 * 
 * 
 * d(alpha) = (d_max)^2 + (f(alpha))^2 - 2 * d_max * f(alpha) * cos(alpha_0 - alpha) 
 * 
 * 
 * 
 * -distance between pedestrian i and the first obstacle in direction alpha_des at time t		 t_h
 * 
 * -v_des(t) = min( v_i0, d_h/theta) 
 * 
 * 
 * 
 * 
 */
public class Pedestrian {

	private static final double DEFAULT_MASS = 70.0;
	private static final double DEFAULT_COMFORTABLE_SPEED = 1.3;
	private static final double DEFAULT_VISION_ANGLE = 75;
	private static final double DEFAULT_HORIZON_DISTANCE= 10;
	private static final double DEFAULT_RELAXATION_TIME= 10;
	
	
	
	private static final double MASS_RADIUS_RATIO = 320.0;
	@Getter	@Setter	private double mass;					// [kg]
	@Getter	@Setter	private double radius;					// [m]
	@Getter	@Setter	private double comfortableSpeed;		// [m/sec]
	@Getter	@Setter	private double visionAngle;				// [degrees]
	@Getter	@Setter	private double horizontDistance;		// [m]
	@Getter	@Setter	private double relaxationTime;			// [sec]

	@Getter	@Setter	private double visionCenter;			//
	@Getter	@Setter	private Position destinationPoint;		//
	@Getter	@Setter	private Position position;				//

	@Getter	@Setter	private double desiredDirection;		//
	@Getter	@Setter	private double desiredSpeed;			//
	
	
	
	public Pedestrian(double visionCenter,Position destinationPoint, Position position) {
		this(DEFAULT_MASS, DEFAULT_COMFORTABLE_SPEED, DEFAULT_VISION_ANGLE, DEFAULT_HORIZON_DISTANCE, 
				DEFAULT_RELAXATION_TIME,visionCenter,destinationPoint,position);
	}

	public Pedestrian(double mass, double comfortableSpeed, double visionAngle, double horizontDistance, 
			double relaxationTime, double visionCenter, Position destinationPoint, Position position) {
		super();
		this.mass = mass;
		this.radius = mass / MASS_RADIUS_RATIO;
		this.comfortableSpeed = comfortableSpeed;
		this.visionAngle = visionAngle;
		this.visionCenter = visionCenter;
		this.horizontDistance = horizontDistance;
		this.relaxationTime = relaxationTime;
		this.destinationPoint = destinationPoint;
		this.position = position;
		CollisionDistance collisionDistance = new CollisionDistance();
	}
	
	
	

}
