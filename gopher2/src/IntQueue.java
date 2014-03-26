
public class IntQueue {
	
	private int[] queue;
	
	private int write;
	private int read;
	private int size;
	
	public IntQueue(int size) {
		this.size = size;
		queue = new int[size];
		
		this.write = 0;
		this.read = 0;
	}
	
	public int poll() {
		int res = queue[read];
		read++;
		read %= size;
		return res;
	}
	
	public void add(int i) {
		queue[write] = i;
		write++;
		write %= size;
	}
	
	public boolean isEmpty() {
		return read == write;
	}
	
	public void reset() {
		write = read = 0;
	}
	
	public static void main(String[] args) {
		IntQueue queue = new IntQueue(4);
		queue.add(1);
		queue.add(2);
		queue.add(3);
		queue.add(4);
		queue.reset();
		queue.add(5);
		queue.add(6);
		queue.add(7);
		queue.add(8);
		System.out.println("in between");
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		queue.add(9);
		queue.add(10);
		queue.add(11);
		queue.add(12);
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		System.out.println(queue.poll());
	}
	
}
