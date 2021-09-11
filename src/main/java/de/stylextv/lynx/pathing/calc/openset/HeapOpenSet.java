package de.stylextv.lynx.pathing.calc.openset;

import java.util.Arrays;

import de.stylextv.lynx.pathing.calc.Node;

public class HeapOpenSet {
	
	private static final int DEFAULT_CAPACITY = 1024;
	
	private Node[] array;
	
	private int size;
	
	public HeapOpenSet() {
		this(DEFAULT_CAPACITY);
	}
	
	public HeapOpenSet(int capacity) {
		this.array = new Node[capacity];
	}
	
	public void add(Node n) {
		if(isFull()) {
			
			int l = array.length << 1;
			
			array = Arrays.copyOf(array, l);
		}
		
		size++;
		
		array[size] = n;
		
		n.setHeapPosition(size);
		
		update(n);
	}
	
	public void update(Node n) {
		int index = n.getHeapPosition();
		
		double cost = n.getFinalCost();
		
		int parentIndex = index >>> 1;
		
		Node parent = array[parentIndex];
		
		while(index > 1 && parent.getFinalCost() > cost) {
			
			array[index] = parent;
			array[parentIndex] = n;
			
			n.setHeapPosition(parentIndex);
			
			parent.setHeapPosition(index);
			
			index = parentIndex;
			
			parentIndex = index >>> 1;
			
			parent = array[parentIndex];
		}
	}
	
	public Node poll() {
		Node node = array[1];
		
		Node n = array[size];
		
		array[1] = n;
		array[size] = null;
		
		n.setHeapPosition(1);
		
		node.setHeapPosition(-1);
		
		size--;
		
		if(size < 2) return node;
		
		int index = 1;
		
		int childIndex = 2;
		
		double cost = n.getFinalCost();
		
		while(true) {
			
			Node child = array[childIndex];
			
			double childCost = child.getFinalCost();
			
			if(childIndex < size) {
				
				Node rightChild = array[childIndex + 1];
				
				double rightChildCost = rightChild.getFinalCost();
				
				if(childCost > rightChildCost) {
					
					childIndex++;
					
					child = rightChild;
					childCost = rightChildCost;
				}
			}
			
			if(cost <= childCost) break;
			
			array[index] = child;
			array[childIndex] = n;
			
			n.setHeapPosition(childIndex);
			
			child.setHeapPosition(index);
			
			index = childIndex;
			
			if((index <<= 1) <= size) break;
		}
		
		return node;
	}
	
	public boolean isFull() {
		return size >= array.length - 1;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
}
