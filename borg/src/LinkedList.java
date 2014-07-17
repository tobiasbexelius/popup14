
/*
 * Authors: Tobias Andersson, Carl Frendin, Jonas Haglund
 */

public class LinkedList<T> {
	
	private LinkedListElement<T> first;
	private LinkedListElement<T> last;
	private int size;
	
	public LinkedList() {
		first = null;
		last = null;
		size = 0;
	}
	
	public void addLast(T newElement) {
		if (size == 0) {
			first = new LinkedListElement<T>(newElement);
			last = first;
		} else {
			last.setNext(new LinkedListElement<T>(newElement));
			last = last.getNext();
		}
		
		size++;
	}
	
	public T removeFirst() {
		if (size == 0)
			return null;
		else if (size == 1) {
			T removedElement = first.getElement();
			first = null;
			last = null;
			size = 0;
			return removedElement;
		} else {
			T removedElement = first.getElement();
			first = first.getNext();
			size--;
			return removedElement;
		}
	}
	
	public LinkedListElement<T> getFirst() {
		return first;
	}
	
	public int getSize() {
		return size;
	}
	
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}
	
	public void print() {
		System.out.println("The list contains " + size + " elements in the following order:");
		LinkedListElement<T> element = first;
		while (element != null) {
			System.out.println(element.getElement());
			element = element.getNext();
		}
	}
}
