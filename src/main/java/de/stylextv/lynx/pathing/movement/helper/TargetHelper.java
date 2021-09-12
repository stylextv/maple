package de.stylextv.lynx.pathing.movement.helper;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.stylextv.lynx.event.events.RenderWorldEvent;
import de.stylextv.lynx.input.target.BlockTarget;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.render.ShapeRenderer;
import de.stylextv.lynx.scheme.Colors;
import net.minecraft.util.math.BlockPos;

public abstract class TargetHelper extends MovementHelper<Movement> {
	
	private List<BlockTarget> targets = new CopyOnWriteArrayList<>();
	
	public TargetHelper(Movement m) {
		super(m);
	}
	
	public void collectBlocks(Node n, int height) {
		collectBlocks(n, 0, height);
	}
	
	public void collectBlocks(Node n, int offset, int height) {
		int x = n.getX();
		int y = n.getY();
		int z = n.getZ();
		
		collectBlocks(x, y, z, offset, height);
	}
	
	public void collectBlocks(int x, int y, int z, int offset, int height) {
		for(int i = 0; i < height; i++) {
			
			collectBlock(x, y + offset + i, z);
		}
	}
	
	public void collectBlock(Node n) {
		collectBlock(n, 0);
	}
	
	public void collectBlock(Node n, int offset) {
		int x = n.getX();
		int y = n.getY() + offset;
		int z = n.getZ();
		
		collectBlock(x, y, z);
	}
	
	public abstract void collectBlock(int x, int y, int z);
	
	public void render(RenderWorldEvent event) {
		for(BlockTarget target : targets) {
			
			BlockPos pos = target.getPos();
			
			ShapeRenderer.drawBox(event, pos, Colors.BLOCK_TARGET, 2);
		}
	}
	
	public void addTarget(int x, int y, int z) {
		targets.add(new BlockTarget(x, y, z));
	}
	
	public void removeTarget(BlockTarget target) {
		targets.remove(target);
	}
	
	public boolean hasTarget(int x, int y, int z) {
		for(BlockTarget target : targets) {
			
			BlockPos pos = target.getPos();
			
			if(pos.getX() == x && pos.getY() == y && pos.getZ() == z) return true;
		}
		
		return false;
	}
	
	public boolean hasTargets() {
		return !targets.isEmpty();
	}
	
	public List<BlockTarget> getTargets() {
		return targets;
	}
	
}
