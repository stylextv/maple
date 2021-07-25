package de.stylextv.lynx.input;

public enum InputAction {
	
	MOVE_FORWARD(true), MOVE_BACK(true), MOVE_LEFT(true), MOVE_RIGHT(true), LEFT_CLICK, RIGHT_CLICK, SNEAK, SPRINT, JUMP;
	
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
