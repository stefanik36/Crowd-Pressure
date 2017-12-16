package com.mass.crowdPressure.builders;

import java.util.ArrayList;
import java.util.List;

import com.mass.crowdPressure.Symulation;
import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.map.Map;
import com.mass.crowdPressure.model.map.StraightWall;
import com.mass.crowdPressure.model.map.Wall;

public class MapFactory {
	public static final String SYMULATION_1 = "SYMULATION1";

	public Map getMap(Symulation sym) {
		if (sym.equals(Symulation.SYM_P1_W1)) {
			return getMap1();
		} else if (sym.equals(Symulation.SYM_P1_W2)) {
			return getMap2();
		}
		return getEmpty();
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

}
