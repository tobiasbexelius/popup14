
/*
 * Tobias Andersson, Carl Frendin, Jonas Haglund
 */

public class LinkedList<T extends Comparable<T>> {
	
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
	
	/*
	 * Assumes the element is in the list.
	 */
	public void removeElement(T element) {
		if (size == 0)
			return;
		else if (size == 1) {
			size = 0;
			first = null;
			last = null;
			return;
		} else {
			if (first.getElement().compareTo(element) == 0) {
				first = first.getNext();
				size--;
				return;
			}
			
			LinkedListElement<T> previous = first;
			LinkedListElement<T> current = previous.getNext();
			while (current != null) {
				if (current.getElement().compareTo(element) == 0)
					break;
				
				previous = current;
				current = current.getNext();
			}
			
			//Current contains the element to remove.			
			if (current == last) {
				last = previous;
				previous.setNext(null);
				size--;
				return;
			} else {
				previous.setNext(current.getNext());
				size--;
				return;
			}
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
