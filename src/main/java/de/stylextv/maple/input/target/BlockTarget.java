package de.stylextv.maple.input.target;

import de.stylextv.maple.context.WorldContext;
import de.stylextv.maple.input.controller.AwarenessController;
import de.stylextv.maple.input.controller.PlaceController;
import de.stylextv.maple.input.controller.ViewController;
import de.stylextv.maple.util.world.Offset;
import de.stylextv.maple.world.BlockInterface;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;

public class BlockTarget {
	
	private BlockPos pos;
	
	public BlockTarget(int x, int y, int z) {
		this(new BlockPos(x, y, z));
	}
	
	public BlockTarget(BlockPos pos) {
		this.pos = pos;
	}
	
	public boolean lookAt(boolean atNeighbour) {
		Offset o = visibleSide(atNeighbour);
		
		if(o == null) {
			
			if(atNeighbour) return false;
			
			o = center();
			
			System.out.println(ViewController.canSee(o));
			
			if(!ViewController.canSee(o)) return false;
		}
		
		ViewController.lookAt(o);
		
		return true;
	}
	
	public Offset visibleSide(boolean inside) {
		BlockPos pos = getPos();
		
		int blockX = pos.getX();
		int blockY = pos.getY();
		int blockZ = pos.getZ();
		
		for(Offset o : Offset.DIRECT_BLOCK_NEIGHBOURS) {
			
			int x = blockX;
			int y = blockY;
			int z = blockZ;
			
			Direction dir = o.getDirection();
			
			if(inside) {
				
				dir = dir.getOpposite();
				
				x += o.getBlockX();
				y += o.getBlockY();
				z += o.getBlockZ();
			}
			
			boolean visible = PlaceController.canPlaceAgainst(x, y, z, dir);
			
			if(visible) {
				
				double rx = pos.getX() + 0.5;
				double ry = pos.getY() + 0.5;
				double rz = pos.getZ() + 0.5;
				
				Offset o2 = o.divide(2);
				
				o2 = o2.add(rx, ry, rz);
				
				rx = o2.getX();
				ry = o2.getY();
				rz = o2.getZ();
				
				if(ViewController.canSee(o2)) return o2;
			}
		}
		
		return null;
	}
	
	public Offset center() {
		BlockState state = BlockInterface.getState(pos);
		
		ClientWorld world = WorldContext.world();
		
		VoxelShape shape = state.getOutlineShape(world, pos);
		
		Vec3d v = shape.getBoundingBox().getCenter();
		
		v = v.add(Vec3d.of(pos));
		
		return new Offset(v);
	}
	
	public boolean isInReach() {
		return AwarenessController.canReach(pos);
	}
	
	public boolean isSelected(boolean ignoreNeighbours) {
		BlockHitResult result = AwarenessController.getBlockTarget();
		
		if(result == null) return false;
		
		BlockPos p = result.getBlockPos();
		
		BlockPos pos = getPos();
		
		if(p.equals(pos)) return true;
		
		if(ignoreNeighbours) return false;
		
		Direction dir = result.getSide();
		
		return p.offset(dir).equals(pos);
	}
	
	public BlockState state() {
		return BlockInterface.getState(pos);
	}
	
	public BlockPos getPos() {
		return pos;
	}
	
}
