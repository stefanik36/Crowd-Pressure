package com.mass.crowdPressure;

<<<<<<< HEAD
=======
import java.util.List;

import com.app.COD;
import com.app.CODFactory;
>>>>>>> develop
import com.mass.crowdPressure.calculators.Configuration;
import com.mass.crowdPressure.exceptions.AngleOutOfRangeException;
import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.pedestrian.Pedestrian;

public class Engine {

	private final static COD cod = CODFactory.setLevelOfDepression(4);

	private Environment environment;

	public Engine(Environment environment) {
		this.environment = environment;
	}

	public void start() {
		int i = Configuration.STEPS;
		while (i > 0) {
			try {
				nextState();
			} catch (AngleOutOfRangeException e) {
				e.printStackTrace();
			}
			i--;
		}
	}

	public void nextState() throws AngleOutOfRangeException {
		for (Pedestrian p : environment.getPedestrians()) { // tu możnaby się pobawić wątkami
			p.prepareNextStep();
		}
		// ale tu trzeba te ^ joinować

<<<<<<< HEAD
//			System.out.println(p.getPedestrianInformation().getVariableInformation().getDesiredDirection()
//					+ ":(" + p.getPedestrianInformation().getVariableInformation().getPosition().getX() + ","
//					+ p.getPedestrianInformation().getVariableInformation().getPosition().getY() + ")");
=======
		for (Pedestrian p : environment.getPedestrians()) { // i tu też
			p.nextStep();
			cod.i("pi: ", p.getPedestrianInformation());
			System.out.println(p.getPedestrianInformation().getVariableInformation().getDesiredDirection() + ":("
					+ p.getPedestrianInformation().getVariableInformation().getPosition().getX() + ","
					+ p.getPedestrianInformation().getVariableInformation().getPosition().getY() + ")");
>>>>>>> develop
		}
		// te tu ^ też
	}

<<<<<<< HEAD

=======
>>>>>>> develop
	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
}
