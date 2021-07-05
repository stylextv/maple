package de.stylextv.dwarf.input;

public enum Input {
	
	MOVE_FORWARD(true), MOVE_BACK(true), MOVE_LEFT(true), MOVE_RIGHT(true), LEFT_CLICK, RIGHT_CLICK, SNEAK, SPRINT, JUMP;
	
	private boolean move;
	
	private Input() {
		this(false);
	}
	
	private Input(boolean move) {
		this.move = move;
	}
	
	public boolean isMove() {
		return move;
	}
	
}
