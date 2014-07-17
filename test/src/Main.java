import java.util.PriorityQueue;
import java.util.Queue;

public class Main {

	public static void main(String[] args) {

		Queue<FloePath> queue = new PriorityQueue<>();
		queue.add(new FloePath(2, 5));
		queue.add(new FloePath(3, 6));
		queue.add(new FloePath(1, 5));
		queue.add(new FloePath(6, 3));
		queue.add(new FloePath(7, 1));
		System.out.println(queue.poll());

	}

	private static class FloePath implements Comparable<FloePath> {
		public int pos;
		public double worst;

		public FloePath(int pos, double worst) {
			this.pos = pos;
			this.worst = worst;
		}

		@Override
		public int compareTo(FloePath arg0) {
			return (int) Math.round(arg0.worst - worst);
		}

		@Override
		public String toString() {
			return "FloePath [pos=" + pos + ", worst=" + worst + "]";
		}

	}
}
