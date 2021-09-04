package de.stylextv.lynx.pathing.calc;

import java.util.List;

import de.stylextv.lynx.pathing.movement.Movement;

public class PathSegment {
	
	private List<Movement> list;
	
	private int pointer;
	
	public PathSegment(List<Movement> list) {
		this.list = list;
	}
	
	public Node findMatch(Path path) {
		int index = path.getCurrentIndex();
		
		int start = length() - 1;
		
		for(int i = start; i >= 0; i--) {
			
			Node n = getNode(i);
			
			int j = path.indexOf(n);
			
			if(j != -1 && j > index) return n;
		}
		
		return null;
	}
	
	public void next() {
		pointer++;
	}
	
	public Node lastNode() {
		int l = list.size();
		
		Movement m = list.get(l - 1);
		
		return m.getDestination();
	}
	
	public double ticksLeft() {
		double sum = 0;
		
		for(int i = pointer; i < list.size(); i++) {
			
			Movement m = list.get(i);
			
			sum += m.favoredCost();
		}
		
		return sum;
	}
	
	public int nodesLeft() {
		return length() - pointer;
	}
	
	public void trimToNode(Node n) {
		while(!isEmpty()) {
			
			Movement m = getMovement(0);
			
			Node source = m.getSource();
			
			if(source.equals(n)) return;
			
			list.remove(0);
		}
	}
	
	public int trim(int amount) {
		int l = length();
		
		if(amount >= l) {
			
			list.clear();
			
			return l;
		}
		
		for(int i = 0; i < amount; i++) {
			
			list.remove(length() - 1);
		}
		
		return amount;
	}
	
	public int indexOf(Node n) {
		for(int i = 0; i < length(); i++) {
			
			Node other = getNode(i);
			
			if(other.equals(n)) return i;
		}
		
		return -1;
	}
	
	public Node getCurrentNode() {
		if(isFinished()) return null;
		
		return getNode(pointer);
	}
	
	public Node getNode(int index) {
		Movement m = getMovement(index);
		
		return m.getDestination();
	}
	
	public Movement getCurrentMovement() {
		if(isFinished()) return null;
		
		return getMovement(pointer);
	}
	
	public Movement getMovement(int index) {
		return list.get(index);
	}
	
	public boolean isFinished() {
		return pointer >= length();
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public int length() {
		return list.size();
	}
	
	public int getPointer() {
		return pointer;
	}
	
}
