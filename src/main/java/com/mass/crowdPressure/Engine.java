package com.mass.crowdPressure;

import java.util.List;

import com.mass.crowdPressure.exceptions.AngleOutOfRangeException;
import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.map.Map;
import com.mass.crowdPressure.model.pedestrian.Pedestrian;

public class Engine {


	private Environment environment;

	public Engine(Environment environment) {
		this.environment = environment;
	}
	
	public void start() {
		int i = 100;
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
