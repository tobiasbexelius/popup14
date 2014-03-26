
/*
 * Authors: Tobias Andersson, Carl Frendin, Jonas Haglund
 */

public class Queue<T> {

	private QueueElement<T> first;
	private QueueElement<T> last;
	private int size;
	
	public Queue() {
		first = null;
		last = null;
		size = 0;
	}
	
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}
	
	public boolean isEmpty() {
		if (first == null)
			return true;
		
		return false;
	}
	
	public int getSize() {
		return size;
	}
	
	public void addLast(T newElement) {
		if (first == null) {
			first = new QueueElement<T>(newElement);
			last = first;
		} else {
			last.setNext(new QueueElement<T>(newElement));
			last = last.getNext();
		}
		
		size++;
	}
	
	public T removeFirst() {
		//If the queue is empty.
		if (size == 0)
			return null;
		
		QueueElement<T> polledElement = first;
		first = first.getNext();
		
		//If the queue has/had one element.
		if (first == null)
			last = null;
		
		size--;
		
		return polledElement.getElement();			
	}
	
	public void print() {
		if (first == null) {
			System.out.println("The queue is empty!");
			return;
		}
		
		System.out.println("The queue contains the following elements in the shown order:");
		QueueElement<T> element = first;
		while (element != null) {
			System.out.println(element.getElement());
			element = element.getNext();
		}
	}
}
