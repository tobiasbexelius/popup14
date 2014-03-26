import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		int tests = io.getInt();
		for (int i = 0; i < tests; i++) {
			int n = io.getInt();
			int m = io.getInt();
			if (n == 1) {
				io.println(0);
				continue;
			}

			List<List<Integer>> adjacencyList = new ArrayList<>();
			for (int j = 0; j < n; j++)
				adjacencyList.add(new ArrayList<Integer>());

			for (int j = 0; j < m; j++)
				adjacencyList.get(io.getInt() - 1).add(io.getInt() - 1);

			List<Set<Integer>> SCCs = getSCCs(n, adjacencyList);
			int[] vertexToSCC = new int[n];
			for (int j = 0; j < SCCs.size(); j++) {
				Set<Integer> SCC = SCCs.get(j);
				for (int vertex : SCC) {
					vertexToSCC[vertex] = j;
				}
			}

			// System.out.println(SCCs);

			Set<Integer> incoming = new HashSet<Integer>();
			Set<Integer> outgoing = new HashSet<Integer>();
			for (int u = 0; u < n; u++) {
				List<Integer> neighbours = adjacencyList.get(u);
				for (Integer v : neighbours) {
					if (vertexToSCC[u] != vertexToSCC[v]) {
						incoming.add(vertexToSCC[v]);
						outgoing.add(vertexToSCC[u]);
					}
				}
			}

			// System.out.println(SCCs.size());
			// System.out.println(outgoing);
			// System.out.println(incoming);

			if (SCCs.size() == 1)
				io.println(0);
			else {
				int impilcationsLeft = Math.max(SCCs.size() - incoming.size(), SCCs.size() - outgoing.size());
				io.println(impilcationsLeft);
			}

		}
		io.flush();
		io.close();
	}

	private static List<Set<Integer>> getSCCs(int n, List<List<Integer>> adjacencyList) {
		List<Set<Integer>> SCCs = new ArrayList<>();
		Stack<Integer> stack = new Stack<Integer>();
		boolean[] visited = new boolean[n];

		for (int i = 0; i < n; i++)
			if (!visited[i])
				visitOrder(i, visited, stack, adjacencyList);

		List<List<Integer>> reversedGraph = reverseGraph(adjacencyList);

		Arrays.fill(visited, false);

		int sccIndex = 0;

		while (!stack.isEmpty()) {
			int vertex = stack.pop();
			if (!visited[vertex]) {
				SCCs.add(new HashSet<Integer>());
				processSCC(vertex, visited, SCCs, sccIndex, reversedGraph);
				sccIndex++;
			}
		}
		return SCCs;
	}

	private static void processSCC(int vertex, boolean[] visited, List<Set<Integer>> SCCs, int sccIndex, List<List<Integer>> adjacencyList) {
		visited[vertex] = true;
		SCCs.get(sccIndex).add(vertex);
		for (int neighbour : adjacencyList.get(vertex)) {
			if (!visited[neighbour]) {
				processSCC(neighbour, visited, SCCs, sccIndex, adjacencyList);
			}
		}
	}

	private static List<List<Integer>> reverseGraph(List<List<Integer>> adjacencyList) {
		int n = adjacencyList.size();
		List<List<Integer>> reverse = new ArrayList<>();

		for (int i = 0; i < n; i++)
			reverse.add(new ArrayList<Integer>());

		for (int i = 0; i < n; i++) {
			for (int neighbour : adjacencyList.get(i)) {
				reverse.get(neighbour).add(i);
			}
		}

		return reverse;
	}

	private static void visitOrder(int vertex, boolean[] visited, Stack<Integer> stack, List<List<Integer>> adjacencyList) {
		visited[vertex] = true;
		for (int neighbour : adjacencyList.get(vertex))
			if (!visited[neighbour])
				visitOrder(neighbour, visited, stack, adjacencyList);

		stack.push(vertex);
	}
}
