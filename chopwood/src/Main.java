import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Main {

	public static void main(String[] args) throws IOException {
		Kattio io = new Kattio(System.in);
		int n = io.getInt();
		int[] v = new int[n];
		for (int i = 0; i < n; i++) {
			v[i] = io.getInt();
		}
		int[] u = chopWood(n, v);
		if (u == null) {
			io.println("error");
		} else {
			for (int i = 0; i < n; i++) {
				io.println(u[i]);
			}
		}
		io.flush();
		io.close();
	}

	private static int[] chopWood(int n, int[] v) {
		if (v[n - 1] != n + 1)
			return null;
		int[] u = new int[n];
		Map<Integer, Integer> lastIndex = getLastIndex(v);
		Queue<Integer> unlocked = getQueue(n, lastIndex.keySet());
		for (int i = 0; i < n; i++) {
			u[i] = unlocked.poll();
			if (lastIndex.get(v[i]) == i)
				unlocked.add(v[i]);
		}
		return u;
	}

	private static Queue<Integer> getQueue(int n, Set<Integer> vElements) {
		Queue<Integer> queue = new PriorityQueue<Integer>();
		for (int i = 1; i <= n; i++) {
			if (!vElements.contains(i)) {
				queue.add(i);
			}
		}
		return queue;
	}

	private static Map<Integer, Integer> getLastIndex(int[] v) {
		Map<Integer, Integer> index = new HashMap<Integer, Integer>();
		for (int i = 0; i < v.length; i++) {
			index.put(v[i], i);
		}
		return index;
	}
}