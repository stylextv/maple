package de.stylextv.maple.pathing.calc.goal;

import de.stylextv.maple.event.events.RenderWorldEvent;
import de.stylextv.maple.pathing.calc.Cost;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.render.ShapeRenderer;
import de.stylextv.maple.scheme.Colors;
import de.stylextv.maple.util.world.rotation.Rotation;
import de.stylextv.maple.util.world.rotation.RotationUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class XZGoal extends Goal {
	
	private int x;
	private int z;
	
	public XZGoal(int x, int z) {
		this.x = x;
		this.z = z;
	}
	
	@Override
	public double heuristic(Node n) {
		int dx = n.getX() - x;
		int dz = n.getZ() - z;
		
		return cost(dx, dz);
	}
	
	@Override
	public boolean isFinalNode(Node n) {
		return n.getX() == x && n.getZ() == z;
	}
	
	@Override
	public void render(RenderWorldEvent event) {
		BlockPos pos1 = new BlockPos(x, 0, z);
		BlockPos pos2 = new BlockPos(x, 257, z);
		
		ShapeRenderer.drawBox(event, pos1, pos2, Colors.GOAL, 2);
	}
	
	@Override
	public boolean equals(Goal other) {
		if(!(other instanceof XZGoal)) return false;
		
		XZGoal g = (XZGoal) other;
		
		int otherX = g.getX();
		int otherZ = g.getZ();
		
		return otherX == x && otherZ == z;
	}
	
	@Override
	public String toString() {
		return String.format("XZGoal{x=%s, z=%s}", x, z);
	}
	
	public int getX() {
		return x;
	}
	
	public int getZ() {
		return z;
	}
	
	public static double cost(int dx, int dz) {
		int x = Math.abs(dx);
		int z = Math.abs(dz);
		
		int straight;
		int diagonal;
		
		if(x < z) {
			
			straight = z - x;
			diagonal = x;
			
		} else {
			
			straight = x - z;
			diagonal = z;
		}
		
		return Cost.SPRINT_STRAIGHT * straight + Cost.SPRINT_DIAGONALLY * diagonal;
	}
	
	public static XZGoal inDirection(BlockPos pos, float yaw, double dis) {
		Rotation r = new Rotation(yaw);
		
		Vec3d v = RotationUtil.rotationToVec(r);
		
		v = v.multiply(dis).add(Vec3d.of(pos));
		
		int x = (int) Math.round(v.getX());
		int z = (int) Math.round(v.getZ());
		
		return new XZGoal(x, z);
	}
	
}
