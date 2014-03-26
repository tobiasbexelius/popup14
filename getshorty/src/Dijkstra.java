import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Dijkstra {

	private static final int INF = Integer.MIN_VALUE;
	private static final int UNDEFINED = -1;

	private int numVertices;
	private double[] distance;
	private int[] previous;
	private List<List<Edge>> neighbourList;

	public Dijkstra(int numVertices, List<List<Edge>> neighbourList) {
		this.numVertices = numVertices;
		this.neighbourList = neighbourList;
	}

	public void findShortestPaths(int source) {
		distance = new double[numVertices];
		Arrays.fill(distance, INF);
		distance[source] = 1;

		previous = new int[numVertices];
		Arrays.fill(previous, UNDEFINED);

		Queue<VertexDistance> queue = new PriorityQueue<>();
		queue.add(new VertexDistance(source, 1));

		while (!queue.isEmpty()) {
			int u = queue.poll().vertex;

			if (distance[u] == INF)
				break;

			for (Edge e : neighbourList.get(u)) {
				double alt = distance[u] * e.getWeight();
				if (alt > distance[e.getEnd()]) {
					distance[e.getEnd()] = alt;
					previous[e.getEnd()] = u;
					queue.add(new VertexDistance(e.getEnd(), distance[e.getEnd()]));
				}

			}
		}
	}

	public List<Integer> getShortestPath(int destination) {
		List<Integer> path = new ArrayList<Integer>();
		int current = destination;
		while (current != UNDEFINED) {
			path.add(current);
			current = previous[current];
		}
		Collections.reverse(path);
		return path;
	}

	public double getShortestDistance(int destination) {
		return distance[destination];
	}

	private static class VertexDistance implements Comparable<VertexDistance> {
		public int vertex;
		public double distance;

		public VertexDistance(int vertex, double distance) {
			this.vertex = vertex;
			this.distance = distance;
		}

		@Override
		public int compareTo(VertexDistance v) {
			if (vertex == v.vertex && distance == v.distance)
				return 0;
			return distance > v.distance ? -1 : 1;
		}
	}
}
