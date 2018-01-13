package com.mass.crowdPressure;

import java.util.Iterator;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.exceptions.AngleOutOfRangeException;
import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.pedestrian.Pedestrian;

public class Engine {

	private final static COD cod = CODFactory.setLevelOfDepression(4);
	private int step;

	private Environment environment;

	public Engine(Environment environment) {
		this.environment = environment;
		step = 0;
	}

//	public void start() {
//		int i = Configuration.STEPS;
//		while (i > 0) {
//			try {
//				nextState();
//			} catch (AngleOutOfRangeException e) {
//				e.printStackTrace();
//			}
//			i--;
//		}
//	}

	public void nextState() throws AngleOutOfRangeException {
		
		
		for (Pedestrian p : environment.getPedestrians()) { // tu możnaby się pobawić wątkami
			if (p.getPedestrianInformation().getVariableInformation().isFinished()) {
				cod.i("PEDESTRIAN: " + p.getPedestrianInformation().getStaticInformation().getId() + " FINISHED ON STEP: "+step);
				continue;
			}
			p.prepareNextStep();
		}
		// ale tu trzeba te ^ joinować
		for (Iterator<Pedestrian> iterator = environment.getPedestrians().iterator(); iterator.hasNext();) {
			Pedestrian p = iterator.next();
			if (p.getPedestrianInformation().getVariableInformation().isFinished()) {
				iterator.remove();
			} else {
				p.nextStep();
				step++;
			}

		}

		// for (Pedestrian p : environment.getPedestrians()) { // i tu też
		// p.nextStep();
		//// cod.i("pi: ", p.getPedestrianInformation());
		//// System.out.println(p.getPedestrianInformation().getVariableInformation().getDesiredDirection()
		// + ":("
		//// +
		// p.getPedestrianInformation().getVariableInformation().getPosition().getX() +
		// ","
		//// +
		// p.getPedestrianInformation().getVariableInformation().getPosition().getY() +
		// ")");
		// }
		// // te tu ^ też
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
}
