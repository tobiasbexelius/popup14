
/*
 * Authors: Tobias Andersson, Carl Frendin, Jonas Haglund
 */

public class LinkedListElement<T> {
	
	private LinkedListElement<T> next;
	private T element;
	
	public LinkedListElement(T element) {
		this.element = element;
		next = null;
	}
	
	public T getElement() {
		return element;
	}
	
	public void setNext(LinkedListElement<T> next) {
		this.next = next;
	}
	
	public LinkedListElement<T> getNext() {
		return next;
	}
}
