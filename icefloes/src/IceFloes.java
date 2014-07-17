import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class IceFloes {

	private final int s1;
	private final int s2;
	private final List<List<Integer>> neighbourList;
	private final Polygon[] floes;

	public IceFloes(int s1, int s2, List<List<Integer>> neighbourList, Polygon[] floes) {
		this.s1 = s1;
		this.s2 = s2;
		this.neighbourList = neighbourList;
		this.floes = floes;
	}

	public double findMostRiskyFloe() {
		int start = s1;
		int end = s2;

		Queue<FloePath> queue = new PriorityQueue<>();
		boolean[] visited = new boolean[neighbourList.size()];
		queue.add(new FloePath(start, floes[start].area()));
		visited[start] = true;

		while (!queue.isEmpty()) {

			FloePath current = queue.poll();
			visited[current.floe] = true;

			for (int neighbour : neighbourList.get(current.floe)) {
				if (neighbour == end)
					return Math.min(current.smallestFloe, floes[neighbour].area());
				if (!visited[neighbour])
					queue.add(new FloePath(neighbour, Math.min(current.smallestFloe, floes[neighbour].area())));
			}
		}
		return -1;
	}

	private static class FloePath implements Comparable<FloePath> {
		public int floe;
		public double smallestFloe;

		public FloePath(int pos, double worst) {
			this.floe = pos;
			this.smallestFloe = worst;
		}

		@Override
		public int compareTo(FloePath arg0) {
			return (int) Math.round(arg0.smallestFloe - smallestFloe);
		}
	}

}
