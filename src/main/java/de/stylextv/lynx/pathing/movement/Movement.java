package de.stylextv.lynx.pathing.movement;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.input.Input;
import de.stylextv.lynx.input.controller.InputController;
import de.stylextv.lynx.input.controller.ViewController;
import de.stylextv.lynx.pathing.calc.Node;
import net.minecraft.util.math.BlockPos;

public abstract class Movement {
	
	private Node node;
	
	public Movement(Node n) {
		this.node = n;
	}
	
	public abstract void onRenderTick();
	
	protected void setPressed(Input i, boolean pressed) {
		InputController.setPressed(i, pressed);
	}
	
	protected void lookAt(Node n) {
		ViewController.lookAt(n);
	}
	
	protected void lookAt(Node n, boolean lookDown) {
		ViewController.lookAt(n, lookDown);
	}
	
	public boolean isCompleted() {
		BlockPos pos = PlayerContext.feetPosition();
		
		return getNode().equals(pos);
	}
	
	public Node getNode() {
		return node;
	}
	
}
