package com.agh.cp.model;

import java.util.List;

import com.agh.cp.model.map.Map;
import com.agh.cp.model.pedestrian.Pedestrian;

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
