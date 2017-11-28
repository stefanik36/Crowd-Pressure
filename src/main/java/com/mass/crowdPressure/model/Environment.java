package com.mass.crowdPressure.model;

import java.util.List;

import com.mass.crowdPressure.model.map.Map;
import com.mass.crowdPressure.model.pedestrian.Pedestrian;

public class Environment {

	private List<Pedestrian> pedestrians;
	private Map map;

	public Environment(List<Pedestrian> pedestrians, Map map) {
		super();
		this.pedestrians = pedestrians;
		this.map = map;
	}

	public List<Pedestrian> getPedestrians() {
		return pedestrians;
	}

	public void setPedestrians(List<Pedestrian> pedestrians) {
		this.pedestrians = pedestrians;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	
	
}
