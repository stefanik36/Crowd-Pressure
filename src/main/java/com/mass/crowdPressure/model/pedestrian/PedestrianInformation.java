package com.mass.crowdPressure.model.pedestrian;

import com.mass.crowdPressure.model.Position;

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
public class PedestrianInformation {

	private static final double MASS_RADIUS_RATIO = 320.0;
	private int id;
	private double mass; 					// [kg]
	private double radius; 					// [m]
	private double comfortableSpeed;		// [m/sec]
	private double visionAngle; 			// [degrees]
	private double horizontDistance; 		// [m]
	private double relaxationTime; 			// [sec]

	private double visionCenter; 
	private Position destinationPoint; 
	private Position position; 
	private double desiredDirection; 
	private double desiredSpeed; 

	public PedestrianInformation(int id, double mass, double comfortableSpeed, double visionAngle,
			double horizontDistance, double relaxationTime, double visionCenter, Position destinationPoint,
			Position position) {
		super();
		this.id = id;
		this.mass = mass;
		this.radius = mass / MASS_RADIUS_RATIO;
		this.comfortableSpeed = comfortableSpeed;
		this.visionAngle = visionAngle;
		this.visionCenter = visionCenter;
		this.horizontDistance = horizontDistance;
		this.relaxationTime = relaxationTime;
		this.destinationPoint = destinationPoint;
		this.position = position;
	}

	
	
	
	
	

	// gettesrs and setters
	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getComfortableSpeed() {
		return comfortableSpeed;
	}

	public void setComfortableSpeed(double comfortableSpeed) {
		this.comfortableSpeed = comfortableSpeed;
	}

	public double getVisionAngle() {
		return visionAngle;
	}

	public void setVisionAngle(double visionAngle) {
		this.visionAngle = visionAngle;
	}

	public double getHorizontDistance() {
		return horizontDistance;
	}

	public void setHorizontDistance(double horizontDistance) {
		this.horizontDistance = horizontDistance;
	}

	public double getRelaxationTime() {
		return relaxationTime;
	}

	public void setRelaxationTime(double relaxationTime) {
		this.relaxationTime = relaxationTime;
	}

	public double getVisionCenter() {
		return visionCenter;
	}

	public void setVisionCenter(double visionCenter) {
		this.visionCenter = visionCenter;
	}

	public Position getDestinationPoint() {
		return destinationPoint;
	}

	public void setDestinationPoint(Position destinationPoint) {
		this.destinationPoint = destinationPoint;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public double getDesiredDirection() {
		return desiredDirection;
	}

	public void setDesiredDirection(double desiredDirection) {
		this.desiredDirection = desiredDirection;
	}

	public double getDesiredSpeed() {
		return desiredSpeed;
	}

	public void setDesiredSpeed(double desiredSpeed) {
		this.desiredSpeed = desiredSpeed;
	}

	public int getId() {
		return id;
	}

}
