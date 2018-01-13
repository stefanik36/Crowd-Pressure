package com.mass.crowdPressure.builders;

import java.util.ArrayList;
import java.util.List;

import com.mass.crowdPressure.Symulation;
import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.map.Map;
import com.mass.crowdPressure.model.map.StraightWall;
import com.mass.crowdPressure.model.map.Wall;

public class MapFactory {
//	private final double wt = 2;

	public Map getMap(Symulation sym) {
		if (sym.equals(Symulation.SYM_P1_W1)) {
			return getMap1();
		} else if (sym.equals(Symulation.SYM_P1_W2)) {
			return getMap2();
		} else if (sym.equals(Symulation.SYM_P0_W1)) {
			return getMap3();
		} else if (sym.equals(Symulation.SYM_ROOM)) {
			return getRoom();
		} else if (sym.equals(Symulation.SYM_ROOM_OBSTACLE1)) {
			return getRoomObstacle1();
		} else if (sym.equals(Symulation.SYM_ROOM_PERP_WALL)) {
			return getRoomPerpWall();
		} else if (sym.equals(Symulation.SYM_PX_VS_PX_W2)) {
			return getRoomTramWallMap();
		}

		return getEmpty();
	}

	public Map getRoomTramWallMap() {
		 List<Wall> walls = new ArrayList<>();

		walls.add(new StraightWall(new Position(50, 5), new Position(50, 45)));

		walls.add(new StraightWall(new Position(50, 55), new Position(50, 85)));
		Map map = new Map(walls);


		return map;
	}


	private Map getRoomPerpWall() {
		List<Wall> walls = getRoomWalls(6);

		walls.add(new StraightWall(new Position(45, 20), new Position(45, 30)));

		Map map = new Map(walls);
		return map;
	}

	private Map getRoomObstacle1() {
		List<Wall> walls = getRoomWalls(6);

		walls.add(new StraightWall(new Position(42, 17), new Position(48, 17)));

		Map map = new Map(walls);
		return map;
	}

	private Map getRoom() {
		List<Wall> walls = getRoomWalls(4);
		Map map = new Map(walls);
		return map;
	}

	private List<Wall> getRoomWalls(double gateWidth) {
		List<Wall> walls = new ArrayList<>();

		double bw = 80 - 10;
		if (gateWidth > bw) {
			gateWidth = bw;
		}
		double wallWidth = bw - gateWidth;
		if (wallWidth < 0) {
			wallWidth = 0;
		}

		Position rt = new Position(80, 80);
		Position lt = new Position(10, 80);
		Position rb = new Position(80, 20);
		Position lb = new Position(10, 20);
		Position gl = new Position(10 + wallWidth / 2, 20);
		Position gr = new Position(80 - wallWidth / 2, 20);

		// bottom
		walls.add(new StraightWall(lb, gl));
		walls.add(new StraightWall(rb, gr));

		// left
		walls.add(new StraightWall(lt, lb));

		// right
		walls.add(new StraightWall(rt, rb));

		// top
		walls.add(new StraightWall(lt, rt));
		return walls;
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
