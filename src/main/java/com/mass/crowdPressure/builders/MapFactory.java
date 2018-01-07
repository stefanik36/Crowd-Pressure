package com.mass.crowdPressure.builders;

import java.util.ArrayList;
import java.util.List;

import com.mass.crowdPressure.Symulation;
import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.map.Map;
import com.mass.crowdPressure.model.map.StraightWall;
import com.mass.crowdPressure.model.map.Wall;

public class MapFactory {

	public Map getMap(Symulation sym) {
		if (sym.equals(Symulation.SYM_P1_W1)) {
			return getMap1();
		} else if (sym.equals(Symulation.SYM_P1_W2)) {
			return getMap2();
		} else if(sym.equals(Symulation.SYM_P0_W1)) {
			return getMap3();
		} else if(sym.equals(Symulation.SYM_ROOM)) {
			return getRoom();
		}
		return getEmpty();
	}

	private Map getRoom() {
		List<Wall> walls = new ArrayList<>();
		walls.add(new StraightWall(new Position(6, 30), new Position(45, 30)));
		walls.add(new StraightWall(new Position(55, 30), new Position(90, 30)));
		walls.add(new StraightWall(new Position(6, 30), new Position(6, 80)));
		walls.add(new StraightWall(new Position(90, 30), new Position(90, 80)));
		walls.add(new StraightWall(new Position(6, 80), new Position(90, 80)));
		Map map = new Map(walls);
		return map;
	}

	private Map getEmpty() {
		List<Wall> walls = new ArrayList<>();
		Map map = new Map(walls);
		return map;
	}

	private Map getMap1() {
		List<Wall> walls = new ArrayList<>();
		walls.add(new StraightWall(new Position(5, 2), new Position(2.5, 4.5)));
		
		Map map = new Map(walls);
		return map;
	}

	private Map getMap2() {
		List<Wall> walls = new ArrayList<>();
		walls.add(new StraightWall(new Position(19, 2), new Position(15, 12)));
		walls.add(new StraightWall(new Position(15, 12), new Position(11, 12)));
		Map map = new Map(walls);
		return map;
	}
	
	private Map getMap3() {
		List<Wall> walls = new ArrayList<>();
		walls.add(new StraightWall(new Position(3, 1), new Position(3, 6)));
		walls.add(new StraightWall(new Position(3.1, 1), new Position(3.1, 6)));
		walls.add(new StraightWall(new Position(3, 1), new Position(3.1, 1)));
		walls.add(new StraightWall(new Position(3, 6), new Position(3.1, 6)));
		Map map = new Map(walls);
		return map;
	}

}
