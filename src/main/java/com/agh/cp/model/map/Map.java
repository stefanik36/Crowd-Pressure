package com.agh.cp.model.map;

import java.util.List;

public class Map {

	private List<Wall> walls;

	public Map(List<Wall> walls) {
		super();
		this.walls = walls;
	}

	public List<Wall> getWalls() {
		return walls;
	}

	public void setWalls(List<Wall> walls) {
		this.walls = walls;
	}

}
