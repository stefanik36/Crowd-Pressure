package com.mass.crowdPressure.model.pedestrian;

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
	private StaticInformation staticInformation;
	private VariableInformation variableInformation;

	public PedestrianInformation(StaticInformation staticInformation,
			VariableInformation variableInformation) {
		super();
		this.staticInformation = staticInformation;
		this.variableInformation = variableInformation;
	}

	public StaticInformation getStaticInformation() {
		return staticInformation;
	}

	public void setStaticInformation(StaticInformation staticInformation) {
		this.staticInformation = staticInformation;
	}

	public VariableInformation getVariableInformation() {
		return variableInformation;
	}

	public void setVariableInformation(VariableInformation variableInformation) {
		this.variableInformation = variableInformation;
	}

}
