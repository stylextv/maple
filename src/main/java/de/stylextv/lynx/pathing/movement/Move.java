package de.stylextv.lynx.pathing.movement;

import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.calc.PathFinder;
import de.stylextv.lynx.pathing.movement.moves.AscendMove;
import de.stylextv.lynx.pathing.movement.moves.DescendMove;
import de.stylextv.lynx.pathing.movement.moves.DiagonalMove;
import de.stylextv.lynx.pathing.movement.moves.ParkourMove;
import de.stylextv.lynx.pathing.movement.moves.PillarMove;
import de.stylextv.lynx.pathing.movement.moves.StraightMove;

public abstract class Move {
	
	private static final Move[] MOVES = new Move[30];
	
	public static final Move STRAIGHT_NORTH = new StraightMove(0, 0, -1);
	
	public static final Move STRAIGHT_SOUTH = new StraightMove(0, 0, 1);
	
	public static final Move STRAIGHT_EAST = new StraightMove(1, 0, 0);
	
	public static final Move STRAIGHT_WEST = new StraightMove(-1, 0, 0);
	
	public static final Move DIAGONAL_NORTHEAST = new DiagonalMove(1, 0, -1);
	
	public static final Move DIAGONAL_SOUTHEAST = new DiagonalMove(1, 0, 1);
	
	public static final Move DIAGONAL_SOUTHWEST = new DiagonalMove(-1, 0, 1);
	
	public static final Move DIAGONAL_NORTHWEST = new DiagonalMove(-1, 0, -1);
	
	public static final Move ASCEND_NORTH = new AscendMove(0, 1, -1);
	
	public static final Move ASCEND_SOUTH = new AscendMove(0, 1, 1);
	
	public static final Move ASCEND_EAST = new AscendMove(1, 1, 0);
	
	public static final Move ASCEND_WEST = new AscendMove(-1, 1, 0);
	
	public static final Move ASCEND_NORTHEAST = new AscendMove(1, 1, -1);
	
	public static final Move ASCEND_SOUTHEAST = new AscendMove(1, 1, 1);
	
	public static final Move ASCEND_SOUTHWEST = new AscendMove(-1, 1, 1);
	
	public static final Move ASCEND_NORTHWEST = new AscendMove(-1, 1, -1);
	
	public static final Move PILLAR = new PillarMove(0, 1, 0);
	
	public static final Move DESCEND_NORTH = new DescendMove(0, -1, -1);
	
	public static final Move DESCEND_SOUTH = new DescendMove(0, -1, 1);
	
	public static final Move DESCEND_EAST = new DescendMove(1, -1, 0);
	
	public static final Move DESCEND_WEST = new DescendMove(-1, -1, 0);
	
	public static final Move DESCEND_NORTHEAST = new DescendMove(1, -1, -1);
	
	public static final Move DESCEND_SOUTHEAST = new DescendMove(1, -1, 1);
	
	public static final Move DESCEND_SOUTHWEST = new DescendMove(-1, -1, 1);
	
	public static final Move DESCEND_NORTHWEST = new DescendMove(-1, -1, -1);
	
	public static final Move DESCEND_DOWN = new DescendMove(0, -1, 0);
	
	public static final Move PARKOUR_NORTH = new ParkourMove(0, 0, -2);
	
	public static final Move PARKOUR_SOUTH = new ParkourMove(0, 0, 2);
	
	public static final Move PARKOUR_EAST = new ParkourMove(2, 0, 0);
	
	public static final Move PARKOUR_WEST = new ParkourMove(-2, 0, 0);
	
	private static int pointer;
	
	private int dx;
	private int dy;
	private int dz;
	
	public Move(int dx, int dy, int dz) {
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		
		registerMove(this);
	}
	
	public int getDeltaX() {
		return dx;
	}
	
	public int getDeltaY() {
		return dy;
	}
	
	public int getDeltaZ() {
		return dz;
	}
	
	public abstract Movement apply(Node n, PathFinder finder);
	
	private static void registerMove(Move m) {
		MOVES[pointer] = m;
		
		pointer++;
	}
	
	public static Move[] getAllMoves() {
		return MOVES;
	}
	
}
