package de.stylextv.lynx.pathing.calc;

import java.util.List;

public class Path {
	
	private List<Node> nodes;
	
	private int pointer;
	
	public Path(List<Node> nodes) {
		this.nodes = nodes;
	}
	
	public void next() {
		pointer++;
	}
	
	public Node getCurrentNode() {
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
