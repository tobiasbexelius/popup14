import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class solve the max flow problem using the Edmund-Karp algorithm.
 * 
 * @authors Tobias Andersson, Carl Frendin, Jonas Haglund
 */
public class MaxFlow {

	private List<List<EdgeTuple>> graph;
	private int numNodes;
	private int source, sink;

	/**
	 * Constructs a solution to the given maximum flow problem instance using the
	 * Edmond-Karp algorithm.
	 * 
	 * @param numNodes
	 *            the number of nodes
	 * @param source
	 *            the source node
	 * @param sink
	 *            the sink node
	 * @param edges
	 *            the list of edges. Each entry in the array is an array of int with
	 *            three fields where edges[i][0] is the source node of this edge,
	 *            edges[i][1] is the destination node of this edge, and edges[i][2]
	 *            is the weight of this edge.
	 */
	public MaxFlow(int numNodes, int source, int sink, int[][] edges) {
		this.numNodes = numNodes;
		this.source = source;
		this.sink = sink;
		graph = new ArrayList<List<EdgeTuple>>();
		for (int i = 0; i < numNodes; i++) {
			graph.add(new ArrayList<EdgeTuple>());
		}
		for (int[] e : edges) {
			addEdge(e[0], e[1], e[2]);
		}
		edmondKarp();
	}

	/**
	 * Returns the final flow graph.
	 * 
	 * @return the list of edges in the residual graph. Each entry in the array is an
	 *         array of int with three fields where edges[i][0] is the source node of
	 *         this edge, edges[i][1] is the destination node of this edge, and
	 *         edges[i][2] is the weight of this edge.
	 */
	public int[][] getMaxFlowGraph() {
		ArrayList<int[]> posEdges = new ArrayList<int[]>();
		for (int node1 = 0; node1 < numNodes; ++node1) {
			for (EdgeTuple edge : getNeighbours(node1)) {
				if (edge.flow != 0 && edge.from_node == node1) {// prevents
																// duplicates

					int value = edge.flow;
					if (edge.flow > 0)
						posEdges.add(new int[] { node1, edge.to_node, value });
					else
						posEdges.add(new int[] { edge.to_node, node1, -value });
				}
			}
		}

		int[][] res = new int[posEdges.size()][3];
		for (int i = 0; i < posEdges.size(); i++) {
			res[i] = posEdges.get(i);
		}

		return res;
	}

	/**
	 * Returns the corresponding residual graph to the final flow graph.
	 * 
	 * @return the list of edges in the residual graph. Each entry in the array is an
	 *         array of int with three fields where edges[i][0] is the source node of
	 *         this edge, edges[i][1] is the destination node of this edge, and
	 *         edges[i][2] is the weight of this edge.
	 */
	public int[][] getResidualGraph() {
		ArrayList<int[]> posEdges = new ArrayList<int[]>();
		for (int node1 = 0; node1 < numNodes; ++node1) {
			for (EdgeTuple edge : getNeighbours(node1)) {
				if (edge.from_node == node1) {// prevents duplicates
					if (edge.resi_flow_asc > 0)
						posEdges.add(new int[] { node1, edge.to_node, edge.resi_flow_asc });
					if (edge.resi_flow_des > 0)
						posEdges.add(new int[] { edge.to_node, node1, edge.resi_flow_des });
				}
			}
		}

		int[][] res = new int[posEdges.size()][3];
		for (int i = 0; i < posEdges.size(); i++) {
			res[i] = posEdges.get(i);
		}

		return res;
	}

	/**
	 * Returns the maximum flow.
	 * 
	 * @return the maximum flow
	 */
	public int getMaxFlow() {
		int totFlow = 0;
		// find total flow to sink
		for (EdgeTuple et : getNeighbours(sink)) {
			totFlow += Math.abs(et.flow);
		}
		return totFlow;
	}

	private void initResidualFlow() {
		for (List<EdgeTuple> adjList : graph) {
			for (EdgeTuple edge : adjList) {
				// cf[u][v] = c[u][v];
				edge.resi_flow_asc = edge.cap_asc;
				// cf[v][u] = c[v][u];
				edge.resi_flow_des = edge.cap_des;
			}
		}
	}

	private void addEdge(int u, int v, int cap) {
		// improvment: implement your own binary search(?), if element not found,
		// store element where it should have been
		EdgeTuple newEdge = new EdgeTuple(u, v, cap);
		// Handle flow from t_node or flow arriving at s_node.
		if ((newEdge.from_node == sink && newEdge.cap_asc > 0) || (newEdge.to_node == sink && newEdge.cap_des > 0) || (newEdge.from_node == source && newEdge.cap_des > 0)
				|| (newEdge.to_node == source && newEdge.cap_asc > 0))
			return;

		List<EdgeTuple> adjList1 = graph.get(newEdge.from_node);
		List<EdgeTuple> adjList2 = graph.get(newEdge.to_node);
		int index = Collections.binarySearch(adjList1, newEdge);

		if (index >= 0) {
			EdgeTuple oldEdge = adjList1.get(index);
			if (newEdge.cap_asc != 0)
				oldEdge.cap_asc = newEdge.cap_asc;
			else
				oldEdge.cap_des = newEdge.cap_des;
		} else {
			adjList1.add(newEdge);
			adjList2.add(newEdge);
			Collections.sort(adjList1);
			Collections.sort(adjList2);
		}
	}

	private List<EdgeTuple> getNeighbours(int u) {
		return graph.get(u);
	}

	private void edmondKarp() {
		initResidualFlow();
		EdgeTuple[] visited;
		List<EdgeTuple> pathends;
		do {
			// find next path from s to t
			pathends = new ArrayList<EdgeTuple>();
			visited = BFS(pathends);
			augment(visited, pathends);
		} while (pathends.size() > 0);
	}

	// scaleParam = 1 --> normal BFS
	private EdgeTuple[] BFS(List<EdgeTuple> pathends) {
		IntQueue queue = new IntQueue(numNodes);
		queue.add(source);
		// unvisited nodes are null'd on their corresponding index
		EdgeTuple[] visited = new EdgeTuple[numNodes];
		// give s_node a non-null marker value
		visited[source] = new EdgeTuple(source, source);// bad bad:( yes it was
		while (!queue.isEmpty()) {
			int next = queue.poll();
			if (next == sink) {
				// if(DEBUG2) System.out.println("FOUND NODE!:!:!:!:" + (t_node+1));
				// return visited;//we've found our node
				continue;
			}
			for (EdgeTuple neb : getNeighbours(next)) {
				int neighbour = neb.from_node == next ? neb.to_node : neb.from_node;
				int resFlow = (next < neighbour) ? neb.resi_flow_asc : neb.resi_flow_des;
				if (resFlow > 0) {// if edge exists, ie for each edge
					// if(DEBUG2) System.out.println("adding " + (neighbour+1) + " ["
					// + (visited[neighbour] >= 0 ? "visited" : "not visited") +
					// "]");
					if (visited[neighbour] == null) {
						if (neighbour == sink) {
							pathends.add(neb);
						} else {
							visited[neighbour] = neb; // we came from "next"
							queue.add(neighbour);
						}

					}
				}
			}
		}
		return visited;
	}

	private void augment(EdgeTuple[] visited, List<EdgeTuple> pathends) {
		for (EdgeTuple pathend : pathends) {
			visited[sink] = pathend;
			augment(Arrays.copyOf(visited, visited.length));
		}
	}

	private void augment(EdgeTuple[] visited) {
		// first, 'reverse' visited so that it shows us where to go
		int bottleNeck = visitedInverse(visited);
		EdgeTuple current = visited[source];
		int last = source;
		while (current != null) {
			if (last == current.from_node) {// adj is is what we use
				// f[u][v] = f[u][v] + bottleNeck;
				current.flow += bottleNeck;
				// cf[u][v] = c[u][v] - f[u][v];
				current.resi_flow_asc = current.cap_asc - current.flow;
				// cf[v][u] = c[v][u] - f[v][u];
				current.resi_flow_des = current.cap_des - (-current.flow);
				last = current.to_node;
			} else {
				current.flow -= bottleNeck;
				current.resi_flow_des = current.cap_des - (-current.flow);
				current.resi_flow_asc = current.cap_asc - current.flow;
				last = current.from_node;
			}
			current = visited[last];// "next"
		}
	}

	private int visitedInverse(EdgeTuple[] reversePath) {
		if (reversePath == null)
			return 0;

		int min = Integer.MAX_VALUE;
		EdgeTuple current = reversePath[sink];
		int last = sink;
		while (current.from_node != current.to_node) {

			// shift the edges back one step
			if (last == current.from_node) {
				if (current.resi_flow_des < min)
					min = current.resi_flow_des;// asc and des are swaped since we
												// are going backwards
				last = current.to_node;
			} else {
				if (current.resi_flow_asc < min)
					min = current.resi_flow_asc;
				last = current.from_node;
			}
			EdgeTuple old = current;
			current = reversePath[last];// "next"
			reversePath[last] = old;
		}

		reversePath[sink] = null;
		return min;
	}

	private static class EdgeTuple implements Comparable<EdgeTuple> {
		public final int from_node;
		public final int to_node;
		public int cap_asc;
		public int cap_des;
		public int flow;
		public int resi_flow_asc;
		public int resi_flow_des;

		public EdgeTuple(int nodeU, int nodeV, int capacity) {
			if (nodeU < nodeV) {
				from_node = nodeU;
				to_node = nodeV;
				cap_asc = capacity;
			} else {
				from_node = nodeV;
				to_node = nodeU;
				cap_des = capacity;
			}
		}

		public EdgeTuple(int from_node, int to_node, int cap_asc, int cap_des, int flow, int from_resi_flow, int to_resi_flow) {
			if (to_node >= from_node) {
				this.from_node = from_node;
				this.to_node = to_node;
			} else {
				this.from_node = to_node;
				this.to_node = from_node;
			}
			this.cap_asc = cap_asc;
			this.cap_des = cap_des;
			this.flow = flow;
			this.resi_flow_asc = from_resi_flow;
			this.resi_flow_des = to_resi_flow;
		}

		public EdgeTuple(int from_node, int to_node) {
			this(from_node, to_node, 0, 0, 0, 0, 0);
		}

		public int compareTo(EdgeTuple other) {
			// first 15 bits mark from_node, 16 remaining marks to_node
			return (this.from_node * 0x10000 + this.to_node) - (other.from_node * 0x10000 + other.to_node);
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			// first edge
			sb.append("|");
			sb.append(flow);
			sb.append("(");
			sb.append(from_node + 1);
			sb.append(", ");
			sb.append(to_node + 1);
			sb.append(", ");
			sb.append(cap_asc);
			sb.append(", ");
			sb.append(resi_flow_asc);
			sb.append(")");
			// second edge
			sb.append(":(");
			sb.append(to_node + 1);
			sb.append(", ");
			sb.append(from_node + 1);
			sb.append(", ");
			sb.append(cap_des);
			sb.append(", ");
			sb.append(resi_flow_des);
			sb.append(")|");

			return sb.toString();
		}
	}// EdgeTuple end
}