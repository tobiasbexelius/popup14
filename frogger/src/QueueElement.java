
/*
 * Authors: Tobias Andersson, Carl Frendin, Jonas Haglund
 */

public class QueueElement<T> {

	private T element;
	private QueueElement<T> next;
	
	public QueueElement(T element) {
		this.element = element;
		next = null;
	}
	
	public T getElement() {
		return element;
	}
	
	public void setNext(QueueElement<T> next) {
		this.next = next;
	}
	
	public QueueElement<T> getNext() {
		return next;
	}
}
