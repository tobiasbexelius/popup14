
/*
 * Authors: Tobias Andersson, Carl Frendin, Jonas Haglund
 */

public class Heap {
	private HeapObject[] heap;
	private int numberOfElementsInHeap;
	
	/*
	 * ALL MENTIONS OF i IN THIS PROGRAM ARE FROM 1 to NUMBEROFELEMENTSINHEAP, 
	 * AND DECREMENTED BY 1 WHEN ACCESSING THE ARRAY!
	 * 
	 * THIS IS A MIN-HEAP!!!!!
	 */
	
	public Heap(int capacity) {
		heap = new HeapObject[capacity];
		numberOfElementsInHeap = 0;
	}
	
	public void clear() {
		numberOfElementsInHeap = 0;
	}
	
	public boolean isFull() {
		return heap.length <= numberOfElementsInHeap;
	}
	
	public int getSize() {
		return numberOfElementsInHeap;
	}
	
	//ERROR CHECKING!
	public boolean checkPositionConsistency() {
		for (int i = 0; i < numberOfElementsInHeap; i++)
			if (heap[i].getPosition() != i + 1)
				return false;
		
		return true;
	}
	
	//Returns the minimum object without removing it.
	public HeapObject findMin() {
		return heap[0];
	}
	
	//Moves the object at position i up in the tree until it is at most as large as its parent.
	private void heapifyUp(int i) {
		if (i > 1) {
			int j = i/2;
			
			if (heap[i-1].getKey() < heap[j-1].getKey()) {
				HeapObject temp = heap[i-1];
				heap[i-1] = heap[j-1];
				heap[j-1] = temp;
				
				heap[i-1].setPosition(i);
				heap[j-1].setPosition(j);
				
				heapifyUp(j);
			}
		}
	}
	
	//Moves the object at position i down in the tree until its both children are at least as large.
	private void heapifyDown(int i) {
		int n = numberOfElementsInHeap;
		int j = -1;
		
		if (2*i > n)
			return;
		else if (2*i < n)
			j = heap[2*i-1].getKey() <= heap[2*i+1-1].getKey() ? 2*i : 2*i+1;
		else if (2*i == n)
			j = 2*i;
		
		if (heap[j-1].getKey() < heap[i-1].getKey()) {
			HeapObject temp = heap[i-1];
			heap[i-1] = heap[j-1];
			heap[j-1] = temp;
			
			heap[i-1].setPosition(i);
			heap[j-1].setPosition(j);
			
			heapifyDown(j);			
		}
	}
	
	/*
	 * @param newObject:	The object to be inserted into the heap.
	 * 
	 * @return:				returns true if and only if the objected could be inserted.
	 */
	public boolean insert(HeapObject newObject) {
		if (numberOfElementsInHeap >= heap.length)
			return false;
		
		heap[numberOfElementsInHeap] = newObject;
		numberOfElementsInHeap++;
		newObject.setPosition(numberOfElementsInHeap);
		
		heapifyUp(numberOfElementsInHeap);
		
		return true;
	}
	
	/*
	 * @param removeObject:	The object to remove.
	 * 
	 * @return:				Returns true if and only if the object to remove was in the heap.
	 */
	public boolean remove(HeapObject removeObject) {
		//The object is not in the heap.
		if (removeObject.getPosition() > numberOfElementsInHeap || removeObject.getPosition() < 1)
			return false;
		//The object has the last position in the heap.
		else if (removeObject.getPosition() == numberOfElementsInHeap) {
			removeObject.setPosition(-1);
			numberOfElementsInHeap--;
			return true;
		}
		
		//The object does not have the last position in the heap.
		//Removes the object and records its old position.
		int holesPosition = removeObject.getPosition();
		removeObject.setPosition(-1);
		
		//Sets the last object at the hole's position.
		HeapObject moveObject = heap[numberOfElementsInHeap-1];
		heap[holesPosition-1] = moveObject;
		moveObject.setPosition(holesPosition);
		heapifyUp(moveObject.getPosition());
		heapifyDown(moveObject.getPosition());
		numberOfElementsInHeap--;
		
		return true;
	}
	
	/*
	 * Updates the updatedObject's position after it has changed its key.
	 */
	public void updateHeap(HeapObject updatedObject) {
		heapifyUp(updatedObject.getPosition());
		heapifyDown(updatedObject.getPosition());
	}
	
	public HeapObject removeMinAndReplaceWith(HeapObject newObject) {
		//Removes minimum object.
		HeapObject min = heap[0];
		min.setPosition(-1);
		
		//Insertes newObject
		heap[0] = newObject;
		newObject.setPosition(1);
		heapifyDown(newObject.getPosition());
		
		return min;
	}
	
	/*
	 * @return:	Object with smallest key in this heap.
	 */
	public HeapObject removeMin() {
		HeapObject removeMin = heap[0];
		remove(heap[0]);
		return removeMin;
	}
	
	public void print() {
		System.out.println("Start of heap information-------------------------------------------");
		if (numberOfElementsInHeap == 0)
			System.out.println("The Heap is empty!");
		else
			System.out.println("The heap contains " + numberOfElementsInHeap + " elements");
		
		for (int i = 1; i <= numberOfElementsInHeap; i++) {
			System.out.print("This is node " + i + " with key = " + heap[i-1].getKey());
			
			if (2*i <= numberOfElementsInHeap)
				System.out.print(", and I have left child " + 2*i + " with key = " + heap[2*i-1].getKey());
			if (2*i+1 <= numberOfElementsInHeap)
				System.out.print(", and I have right child " + (2*i+1) + " with key = " + heap[2*i+1-1].getKey());
			if (2*i > numberOfElementsInHeap)
				System.out.print(", and I have no children");
			
			System.out.println();
		}
		
		System.out.println("Positions are consistent: " + this.checkPositionConsistency());
		System.out.println("End of heap information-----------------------------------------------");
	}
}
