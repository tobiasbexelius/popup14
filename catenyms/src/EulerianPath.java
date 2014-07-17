import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class EulerianPath {

	List<List<Edge>> edgesFrom;

	// degree[v][0] = v's out degree, degree[v][1] = v's in degree.
	// degree[v][2] = v's number of self loops.
	private int[][] degree;
	private int numVertices;
	private int numEdges;
	private Stack<String> stack;
	private int smallestNode;

	public EulerianPath(int numVertices, int numEdges, List<Edge> edges, int smallestNode) {
		this.numVertices = numVertices;
		this.numEdges = numEdges;
		this.smallestNode = smallestNode;

		edgesFrom = new ArrayList<List<Edge>>(numVertices);

		for (int v = 0; v < numVertices; v++)
			edgesFrom.add(new LinkedList<Edge>());

		degree = new int[numVertices][2];

		for (int i = 0; i < numEdges; i++) {
			int u = edges.get(i).getU();
			int v = edges.get(i).getV();

			degree[u][0] += 1; // vertex u's out degree is incremented.
			degree[v][1] += 1; // vertex v's in degree is incremented.
			edgesFrom.get(u).add(edges.get(i));
		}
	}

	public Stack<String> findEulerPath() {
		stack = new Stack<String>();
		int startVertex = -1;
		int numStartVertices = 0;
		int numEndVertices = 0;

		// Checks the initial conditions that either exactly 2 vertices
		// have |outdegree - indegree| = 1 and that one is -1 and the
		// other +1, or that all vertices have |outdegree - indegree| = 0.

		for (int v = 0; v < this.numVertices; v++)
			if (degree[v][0] - degree[v][1] == 1) {
				startVertex = v;
				numStartVertices++;
				if (numStartVertices > 1)
					return null;
			} else if (degree[v][0] - degree[v][1] == -1) {
				numEndVertices++;
				if (numEndVertices > 1)
					return null;
			} else if (degree[v][1] - degree[v][0] < -1)
				return null;
			else if (degree[v][1] - degree[v][0] > 1)
				return null;

		// If all vertices have the same in degree as out degree then the current
		// vertex is chosen as vertex 0.
		if (numStartVertices == 0 && numEndVertices == 0) {
			startVertex = smallestNode;
		}

		// If there is only one potential start vertex but no end vertex, or vice
		// verse, then there is no Euler path.
		else if ((numStartVertices == 0 && numEndVertices != 0) || (numStartVertices != 0 && numEndVertices == 0))
			return null;

		stack.clear();
		findTour(startVertex, null);

		// System.out.println(stack.size() + "\t" + numEdges);
		// System.out.println(stack);
		// The list shall be of size |E|+1. If not there is no Euler path
		if (stack.size() != numEdges)
			return null;
		else
			return stack;
	}

	private void findTour(int u, String word) {

		List<Edge> edges = edgesFrom.get(u);
		Collections.sort(edges);

		// System.out.println(u + "\t" + edges);

		while (!edges.isEmpty()) {
			Edge e = edges.remove(0);
			findTour(e.getV(), e.getWord());
		}
		if (word != null)
			stack.push(word);
	}
}
