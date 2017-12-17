package com.mass.crowdPressure;

import com.mass.crowdPressure.calculators.Configuration;
import com.mass.crowdPressure.exceptions.AngleOutOfRangeException;
import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.pedestrian.Pedestrian;

public class Engine {


	private Environment environment;

	public Engine(Environment environment) {
		this.environment = environment;
	}
	
	public void start() {
		int i = Configuration.STEPS;
		while(i>0) {
			try {
				nextState();
			} catch (AngleOutOfRangeException e) {
				e.printStackTrace();
			}
			i--;
		}
	}

	public void nextState() throws AngleOutOfRangeException {
		for(Pedestrian p : environment.getPedestrians()) { //tu możnaby się pobawić wątkami
			p.prepareNextStep();
		}
		//ale tu trzeba te ^ joinować 
		
		for(Pedestrian p : environment.getPedestrians()) {  //i tu też
			p.nextStep();

//			System.out.println(p.getPedestrianInformation().getVariableInformation().getDesiredDirection()
//					+ ":(" + p.getPedestrianInformation().getVariableInformation().getPosition().getX() + ","
//					+ p.getPedestrianInformation().getVariableInformation().getPosition().getY() + ")");
		}
		//te tu ^ też
	}


	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
}
