package de.stylextv.maple.input;

public enum InputAction {
	
	MOVE_FORWARD(true),
	MOVE_BACK(true),
	MOVE_LEFT(true),
	MOVE_RIGHT(true),
	
	JUMP,
	
	SNEAK,
	SPRINT,
	
	LEFT_CLICK,
	RIGHT_CLICK;
	
	private boolean move;
	
	private InputAction() {
		this(false);
	}
	
	private InputAction(boolean move) {
		this.move = move;
	}
	
	public boolean isMove() {
		return move;
	}
	
}
