
/*
 * Authors: Tobias Andersson, Carl Frendin, Jonas Haglund.
 * 
 * Class that solves the Minimum Spanning Tree Problem.
 * An instance of this class can be used for several problem
 * instances as long as the number of edges an vertices does
 * not exceed the specified limits in the constructor.
 */
public class MinimumSpanningTree {
	//The following two fields are used to store the graph.
	//Stores source, destination, and weight.
	//edge[i][0] = u, edge[i][1] = v, edge[i][2] = w, where (u, v) ∈ E and w(e, v) = w.
	private int[][] edge;
	//The ID of the edge containing i's neighbor.
	private LinkedList<Integer>[] neighborList;	

	//The following field tells the number of edges and vertices
	//of the current problem instance. That is, the first numberOfVertices
	//indexes are relevant in neighborList.
	private int numberOfVertices;

	//Is true if a vertex can be visited from an arbitrary vertex in the graph.
	//Used to see if the graph consists of several components.
	private boolean[] visited;

	//A heap to maintain the nearest vertex that is not in the current minimum
	//spanning tree.
	private Heap nearestVertex;
	//Holds vertices: relates their ID and weight to nearest vertex in MST. Used
	//in conjunction with the heap.
	private Vertex[] vertex;

	//A parent array: parent[u][0] = v if (u, v) is in the tree, parent[u][1] = w(u, v)
	//One vertex r is the root and has parent[r] = r. I this code r = 0.
	private int[][] parent;

	//The cost of the latest computed minimum spanning tree problem instance.
	private long cost;

	//Holds the tree in lexicographically order.
	//MST[0][0] = u, MST[0][1] = v iff (u, v) ∈ the computed MST.
	private int[][] MST;

	/*
	 * @param maximumNumberOfEdges:	
	 */
	public MinimumSpanningTree(int maximumNumberOfVertices, int maximumNumberOfEdges) {
		edge = new int[maximumNumberOfEdges][3];

		neighborList = (LinkedList<Integer>[]) new LinkedList[maximumNumberOfVertices];;
		vertex = new Vertex[maximumNumberOfVertices];
		for (int i = 0; i < neighborList.length; i++) {
			neighborList[i] = new LinkedList<Integer>();
			vertex[i] = new Vertex(i);
		}

		nearestVertex = new Heap(maximumNumberOfVertices);

		visited = new boolean[maximumNumberOfVertices];

		parent = new int[maximumNumberOfVertices][2];

		MST = new int[maximumNumberOfVertices-1][2];
	}

	/*
	 * Initializes data structures for a problem instance.
	 * 
	 * @param numberOfEdges:	number of edges in the graph.
	 * @param numberOfVertices:	number of vertices in the graph.
	 * @param E:				the edges in the graph.
	 */
	public void setInstance(int numberOfVertices, int numberOfEdges, String[] E) {
		this.numberOfVertices = numberOfVertices;

		for (int i = 0; i < numberOfVertices; i++) {
			neighborList[i].clear();
			visited[i] = false;
		}

		for (int i = 0; i < numberOfEdges; i++) {
			String[] uvw = E[i].split(" ");
			int u = Integer.parseInt(uvw[0]);
			int v = Integer.parseInt(uvw[1]);
			int w = Integer.parseInt(uvw[2]);

			edge[i][0] = u;
			edge[i][1] = v;
			edge[i][2] = w;

			neighborList[u].addLast(i);
			neighborList[v].addLast(i);
		}

		nearestVertex.clear();
	}

	/*
	 * Computes the minimum spanning tree for the initialized instance.
	 * 
	 * @return:	The set of edges in the computed minimum spanning tree. 
	 * 			Edges are stored in an array in lexicographically 
	 * 			increasing order with lower index the smaller vertex ID
	 * 			and the second index has the larger vertex ID. That is
	 * 			MST[i][0] = u, MST[i][1] = v iff (u, v) ∈ MST and u < v.
	 * 
	 * 			Null is returned if there is no MST.
	 */
	public int[][] computeMST() {
		//Inserts all vertices into the heap.
		int u = 0;
		vertex[u].setKey(0);
		nearestVertex.insert(vertex[u]);
		for (int i = 1; i < numberOfVertices; i++) {
			vertex[i].setKey(Integer.MAX_VALUE);
			nearestVertex.insert(vertex[i]);
		}

		//The main loop for removing vertices that are not in the current tree
		//into the tree by setting their parent in a rooted tree with 0 as root.
		while (nearestVertex.getSize() > 0) {
			u = ((Vertex) nearestVertex.removeMin()).getVertexID();

			if (vertex[u].getKey() == Integer.MAX_VALUE)
				return null;
			
			//Updates u's neighbors distances to some vertex in the current tree.
			LinkedListElement<Integer> e = neighborList[u].getFirst();
			while (e != null) {
				int uv = e.getElement(), v;
				if (edge[uv][0] != u) {
					v = edge[uv][0];
					if (edge[uv][2] < vertex[v].getKey() && vertex[v].getPosition() != -1) {
						vertex[v].setKey(edge[uv][2]);
						nearestVertex.updateKey(vertex[v]);
						parent[v][0] = u;
						parent[v][1] = edge[uv][2];
					}
				} else if (edge[uv][1] != u) {
					v = edge[uv][1];
					if (edge[uv][2] < vertex[v].getKey() && vertex[v].getPosition() != -1) {
						vertex[v].setKey(edge[uv][2]);
						nearestVertex.updateKey(vertex[v]);
						parent[v][0] = u;
						parent[v][1] = edge[uv][2];
					}
				}

				e = e.getNext();
			}
		}

		//Inserts the edges in the MST array and then the MST array is sorted. At the same
		//time the cost of the MST is computed.
		this.cost = 0;
		for (int v = 1; v < this.numberOfVertices; v++)
			if (parent[v][0] < v) {
				MST[v-1][0] = parent[v][0];
				MST[v-1][1] = v;
				this.cost += parent[v][1];
			} else {
				MST[v-1][0] = v;
				MST[v-1][1] = parent[v][0];	
				this.cost += parent[v][1];			
			}

		return sortEdges(MST, 0, this.numberOfVertices - 2);
	}
	
	//Sorts the edges via merge sort in lexicographically increasing order.
	//The start and stop indexes are inclusive.
	public int[][] sortEdges(int[][] edges, int start, int stop) {
		//Base cases.
		if (stop-start == 0) {
			int[][] result = new int[1][2];
			result[0][0] = edges[start][0];
			result[0][1] = edges[start][1];
			return result;
		} else if (stop-start == 1) {
			int[][] result = new int[2][2];
			if (edges[start][0] < edges[stop][0] || (edges[start][0] == edges[stop][0] && edges[start][1] < edges[stop][1])) {
				result[0][0] = edges[start][0];
				result[0][1] = edges[start][1];
				result[1][0] = edges[stop][0];
				result[1][1] = edges[stop][1];
			} else {
				result[0][0] = edges[stop][0];
				result[0][1] = edges[stop][1];	
				result[1][0] = edges[start][0];
				result[1][1] = edges[start][1];			
			}
			return result;
		}
		
		//Recursive step.
		int middle = start + (stop-start)/2;
		int[][] result1 = sortEdges(edges, start, middle);
		int[][] result2 = sortEdges(edges, middle + 1, stop);
		int[][] result = new int[stop - start + 1][2];

		int p = 0, p1 = 0, p2 = 0;
		
		//The part where both subresults are considered.
		while (p1 < result1.length && p2 < result2.length) {
			if (result1[p1][0] < result2[p2][0] || (result1[p1][0] == result2[p2][0] && result1[p1][1] < result2[p2][1])) {
				result[p][0] = result1[p1][0];
				result[p][1] = result1[p1][1];
				p++;
				p1++;
			} else {
				result[p][0] = result2[p2][0];
				result[p][1] = result2[p2][1];
				p++;
				p2++;
			}
		}

		//The part when only the first result is left.
		while (p1 < result1.length) {
			result[p][0] = result1[p1][0];
			result[p][1] = result1[p1][1];
			p++;
			p1++;
		}

		//The part when only the second result is left.
		while (p2 < result2.length) {
			result[p][0] = result2[p2][0];
			result[p][1] = result2[p2][1];
			p++;
			p2++;
		}

		return result;
	}

	public long getCost() {
		return this.cost;
	}
}
