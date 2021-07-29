package de.stylextv.lynx.pathing.calc;

import java.util.List;

import net.minecraft.core.BlockPos;

public class PathSegment {
	
	private List<Node> nodes;
	
	private int pointer;
	
	public PathSegment(List<Node> nodes) {
		this.nodes = nodes;
	}
	
	public void next() {
		pointer++;
	}
	
	public BlockPos lastPosition() {
		int l = nodes.size();
		
		Node n = nodes.get(l - 1);
		
		return n.blockPos();
	}
	
	public int nodesLeft() {
		return length() - pointer;
	}
	
	public Node getCurrentNode() {
		if(isFinished()) return null;
		
		return getNode(pointer);
	}
	
	public Node getNode(int index) {
		return nodes.get(index);
	}
	
	public boolean isFinished() {
		return pointer >= length();
	}
	
	public int length() {
		return nodes.size();
	}
	
}
