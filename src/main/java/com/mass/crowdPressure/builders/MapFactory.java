package com.mass.crowdPressure.builders;

import java.util.ArrayList;
import java.util.List;

import com.mass.crowdPressure.Symulation;
import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.map.Map;
import com.mass.crowdPressure.model.map.StraightWall;
import com.mass.crowdPressure.model.map.Wall;

public class MapFactory {
	private final double wt = 2;

	public Map getMap(Symulation sym) {
		if (sym.equals(Symulation.SYM_P1_W1)) {
			return getMap1();
		} else if (sym.equals(Symulation.SYM_P1_W2)) {
			return getMap2();
		} else if (sym.equals(Symulation.SYM_P0_W1)) {
			return getMap3();
		} else if (sym.equals(Symulation.SYM_ROOM)) {
			return getRoom();
		}
		return getEmpty();
	}

	private Map getRoom() {
		List<Wall> walls = new ArrayList<>();

		// Position tlo = new Position(18,82);
		// Position tli = new Position(20,80);
		//
		// Position tro = new Position(80,82);
		// Position tri = new Position(78,80);
		//
		// Position bro = new Position(80,20);
		// Position bri = new Position(78,22);
		//
		// Position blo = new Position(18,20);
		// Position bli = new Position(20,22);
		//
		//
		// Position glt = new Position(40,22);
		// Position glb = new Position(40,20);
		//
		// Position grt = new Position(60,22);
		// Position grb = new Position(60,20);
		//
		//
		//
		//
		// walls.add(new StraightWall(tlo, tro));
		// walls.add(new StraightWall(tli, tri));
		// walls.add(new StraightWall(tlo, blo));
		// walls.add(new StraightWall(tli, bli));
		// walls.add(new StraightWall(tro, bro));
		// walls.add(new StraightWall(tri, bri));
		//
		//
		// walls.add(new StraightWall(bli, glt));
		// walls.add(new StraightWall(blo, glb));
		// walls.add(new StraightWall(glt, glb));
		//
		// walls.add(new StraightWall(bri, grt));
		// walls.add(new StraightWall(bro, grb));
		// walls.add(new StraightWall(grt, grb));

		// walls.add(new StraightWall(blo, bli));

		// // bottom left
		// double by = 18;
		// double bllx = 6;
		// double blrx = 20;
		// walls.add(new StraightWall(new Position(bllx, by + wt), new Position(blrx, by
		// + wt)));
		// walls.add(new StraightWall(new Position(bllx - wt, by), new Position(blrx,
		// by)));
		// walls.add(new StraightWall(new Position(blrx, by), new Position(blrx, by +
		// wt)));
		//
		//
		// // bottom right
		// double brlx = 40;
		// double brrx = 60;
		// walls.add(new StraightWall(new Position(brrx, by + wt), new Position(brlx, by
		// + wt)));
		// walls.add(new StraightWall(new Position(brrx + wt, by), new Position(brlx,
		// by)));
		// walls.add(new StraightWall(new Position(brlx, by), new Position(brlx, by +
		// wt)));
		//
		// // left
		// double ty = 90;
		// walls.add(new StraightWall(new Position(bllx - wt, by), new Position(bllx -
		// wt, ty + wt)));
		// walls.add(new StraightWall(new Position(bllx, by + wt), new Position(bllx,
		// ty)));

		// // top
		// double bly = 18;
		// double bllx = 6;
		// double blrx = 20;
		//
		// walls.add(new StraightWall(new Position(bllx - wt, ty + wt), new
		// Position(bllx - wt, ty + wt)));
		// walls.add(new StraightWall(new Position(bllx, ty), new Position(brrx, ty)));

		// walls.add(new StraightWall(new Position(6, 20), new Position(6, 39)));
		// walls.add(new StraightWall(new Position(25, 20), new Position(39, 20)));
		// walls.add(new StraightWall(new Position(6, 20), new Position(6, 40)));

		
		Position rt = new Position(80, 80);
		Position lt = new Position(10, 80);
		Position rb = new Position(80, 20);
		Position lb = new Position(10, 20);
		Position gl = new Position(42, 20);
		Position gr = new Position(48, 20);
		
		//bottom
		walls.add(new StraightWall(lb, gl));
		walls.add(new StraightWall(rb, gr));
		
		//left
		walls.add(new StraightWall(lt, lb));

		//right
		walls.add(new StraightWall(rt, rb));

		// top
		walls.add(new StraightWall(lt, rt));
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
