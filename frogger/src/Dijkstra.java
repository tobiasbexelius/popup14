/*
 * Authors: Tobias Andersson, Carl Frendin, Jonas Haglund
 * 
 * The algorithm is used as follows:
 * 1. Call the constructor of this class to construct data structures.
 * 2. Call setGraph with the graph as input.
 * 3. Call initialize[Timed]Dijkstra with the ID of the source as parameter
 * from which the shortest path is computed from. This initializes
 * all needed data structures for computing the path and its cost.
 * 4. Call findShortest[Time]Path to find the shortest path and its cost
 * to the destination, where the destination is given in the parameter.
 * 5. Call getParent to get the parent array, it is indexed by child and returns parent.
 * 
 * THE PATH FROM A SOURCE TO A DESTINATION IS NOT ALLOWED TO BE AS LONG AS INTEGER.MAX_VALUE!
 * 
 * DOUBLE EDGES ARE ALLOWED.
 * 
 * An object instance can be reused for another graph if that graph has not more vertices
 * than set when the constructor was called.
 * 
 */

public class Dijkstra {
	// Corresponds to the graph, indexed by source, and returns the index of the source's edges in
	// edge.
	private LinkedList<Integer>[] neighborList; // Indexed by vertex ID, and gives a vertex'
												// outgoing edges' indexes.
	// where the edges has the same index.
	private int[][] edge; // A reference to all the edges.
	private Vertex[] vertex; // Contains the vertices of G, used in the heap with key values.
	private int s; // Corresponds to the start node when computing the shortest path to any node.
	private Heap notInS; // Edges in V but not in S.
	private boolean[] S; // Explored set S.
	private int[] parent; // When the shortest path of s, u is found, it is stored in this array.
	// It is indexes by the node whose predecessor/parent is desired.
	private int numberOfVertices; // The number of vertices that the current graph holds.
	private boolean[] connectedPath; // Used in BFS to mark which nodes have a path from s.
	private Queue<Integer> Q; // A queue that is used in several places.

	/*
	 * @param maximumNumberOfVertices:	Sets size of memory allocated data structures. These data structures can be reused
	 * 									different graphs. Is only used for the purpose of reusing memory for different graphs.
	 */
	public Dijkstra(int maximumNumberOfVertices, int maximumNumberOfEdges) {
		neighborList = (LinkedList<Integer>[]) new LinkedList[maximumNumberOfVertices];
		vertex = new Vertex[maximumNumberOfVertices];
		notInS = new Heap(maximumNumberOfVertices);
		S = new boolean[maximumNumberOfVertices];
		parent = new int[maximumNumberOfVertices];
		connectedPath = new boolean[maximumNumberOfVertices];
		Q = new Queue<Integer>();

		// Initializes each vertex' neighbor list.
		for (int i = 0; i < this.neighborList.length; i++)
			this.neighborList[i] = new LinkedList<Integer>();

		// Creates the vertices that keeps track of their ID and key.
		for (int i = 0; i < vertex.length; i++)
			vertex[i] = new Vertex(i);
	}

	/*
	 * Initializes the graph G = (V, E).
	 * 
	 * @param int V:				Is equal to |V|, that is the number of vertices.
	 * @param int edge[][]:			Gives source, destination, and weight of edge i:
	 * 								edge[i][0] = v and edge[i][1] = u if (v, u) ∈ E; edge[v][2] = c(v, u)
	 * 								If timed Dijkstra is used, then edge[v][2] = t_0, edge[v][3] = P, and edge[v][4] = d 
	 * @param int numberOfEdges:	Is equal to |E|, the number of edges in the graph.
	 */
	public void setGraph(int numberOfVertices, int[][] edge, int numberOfEdges) {
		this.numberOfVertices = numberOfVertices;
		this.edge = edge;

		// Initializes the edges for all vertices.
		for (int i = 0; i < numberOfVertices; i++)
			this.neighborList[i].clear();
		// Inserts the edges into the data structure.
		for (int i = 0; i < numberOfEdges; i++) {
			// Self MUST BE REMOVED SINCE THEY ARE UNNECESSARY AND THE TIMED VERSION MUST BE WITHOUT
			// THEM.
			// if (edge[i][0] != edge[i][1])
			this.neighborList[edge[i][0]].addLast(i);
		}

	}

	/*
	 * Initialization that is in common to both regular Dijkstra and timed Dijkstra.
	 */
	private void dijkstraInitialization(int s) {
		this.s = s;

		// Initializes S, the explored set.
		for (int i = 0; i < this.numberOfVertices; i++) {
			S[i] = false;
			vertex[i].setKey(Integer.MAX_VALUE);
		}

		// The not explored set, but reachable from s.
		notInS.clear();

		// Initializes the BFS-search data structures.
		for (int i = 0; i < this.numberOfVertices; i++)
			connectedPath[i] = false;

		// Finds all reachable nodes from s, including s, and inserts them into notInS.
		Q.clear();
		Q.addLast(s);
		while (!Q.isEmpty()) {
			int v = Q.removeFirst();
			if (!connectedPath[v]) {
				notInS.insert(vertex[v]);
				connectedPath[v] = true;

				LinkedListElement<Integer> edgeID = neighborList[v].getFirst();
				while (edgeID != null) {
					int u = edge[edgeID.getElement()][1]; // u is v's neighbor.
					if (!connectedPath[u])
						Q.addLast(u);
					edgeID = edgeID.getNext();
				}
			}
		}

		// Removes s from notInS and inserts s into S.
		S[s] = true;
		vertex[s].setKey(0);
		notInS.remove(vertex[s]);
	}

	/*
	 * Initializes this object before computing the shortest path from the given node.
	 * To compute the shortest path to a given end node v, call the method dijkstra(v)
	 * after this call.
	 * 
	 * @param s:	start node to compute the shortest path from.
	 */
	public void initializeDijkstra(int s) {
		dijkstraInitialization(s);

		// Updates s' neighbors' key values and positions in the heap, and also their parent.
		LinkedListElement<Integer> edgeID = neighborList[s].getFirst();
		while (edgeID != null) {
			int v = edge[edgeID.getElement()][1]; // v is s' neighbor.
			int cost = edge[edgeID.getElement()][2]; // the cost from s to v.

			if (cost < vertex[v].getKey()) {
				vertex[v].setKey(cost);
				notInS.updateHeap(vertex[v]);
				parent[v] = s;
			}

			edgeID = edgeID.getNext();
		}
	}

	/*
	 * Initializes this object before computing the shortest time path from the given node s.
	 * To compute the shortest time path to a given end node v, call the method findShortestTimePath(v)
	 * after this call.
	 * 
	 * Also initializes each reached node's time field to -1 to indicate that it is not computed.
	 * All nodes v that are disconnected from the source have reachable[v] = false and vertex[v].getReachedTime() = -1.
	 * Those nodes u that have some path from source to themselves have reachable[u] = true and vertex[u].getReachedTime() = ∞.
	 * The source is initialized as earlierVisited[source] = true and has vertex[source].getReachedTime() = 0.
	 * 
	 * @param s:	start node to compute the shortest path from.
	 */
	public void initializeTimedDijkstra(int s) {
		dijkstraInitialization(s);

		// Updates s' neighbors' key values and positions in the heap, and also their parent.
		LinkedListElement<Integer> edgeID = neighborList[s].getFirst();
		while (edgeID != null) {
			int su = edgeID.getElement();
			int u = edge[su][1]; // u is s' neighbor.
			int t0 = edge[su][2];
			int d = edge[su][4];

			if (t0 + d < vertex[u].getKey()) {
				vertex[u].setKey(t0 + d);
				notInS.updateHeap(vertex[u]);
				parent[u] = s;
			}

			edgeID = edgeID.getNext();
		}
	}

	/*
	 * Computes the minimum distance to destination from s, and the corresponding path.
	 * This method can be reused if several destinations' distances from a fixed source
	 * is wanted. The parent array can also be reused.
	 * 
	 * @param destination:	The destination vertex to find the shortest path to from s.
	 * 
	 * @return:				The distance in the shortest path from s to destination. If 
	 * 						s and destination are unconnected, then -1.
	 */
	public int findShortestPath(int destination) {
		if (!connectedPath[destination])
			return Integer.MAX_VALUE;
		if (destination == s)
			return 0;
		// If the destination is already queried, then we can return that value.
		if (vertex[destination].getPosition() != Integer.MAX_VALUE)
			return vertex[destination].getKey();

		boolean done = neighborList[s].getSize() == 0;

		// As long as we can add new vertices to S, we do that. If s have no neighbors, then we have
		// failed.
		while (!done) {
			int v = ((Vertex) notInS.findMin()).getVertexID();

			// If the closest vertex to s is not connected to s, then we have failed and end.
			if (v == destination)
				done = true;
			else { // Otherwise we add the closestVertex to S and remove it from the complement.
					// Removes the closest vertex to s that is not in S and puts it in S.
				notInS.removeMin();
				S[v] = true;

				// Now we have to consider each neighbor of v and update their keys and parents.
				LinkedListElement<Integer> edgeID = neighborList[v].getFirst();
				while (edgeID != null) {
					int u = edge[edgeID.getElement()][1];
					int cost = edge[edgeID.getElement()][2];
					// If the path to v extended with the edge (v,u) to u can make u closer to s,
					// then u's key is updated and its position in the heap.
					// Also u's parent is updated since this is the now best known path to u from s.
					if (vertex[u].getKey() > vertex[v].getKey() + cost) {
						vertex[u].setKey(vertex[v].getKey() + cost);
						notInS.updateHeap(vertex[u]);
						parent[u] = v;
					}

					edgeID = edgeID.getNext();
				}
			}
		}

		return vertex[destination].getKey();
	}

	/*
	 * Computes the minimum time to get to destination from s, and the corresponding path.
	 * This method can be reused if several destinations' distances from a fixed source
	 * is desired. The parent array can also be reused, that is data from one path to the
	 * same source is the retained when calling this method again for distinct destination.
	 * 
	 * InitializeTimedDijkstra must have been called before this method is called with 
	 * the current graph or if regular Dijkstra has been called before this call.
	 * 
	 * @param destination:	The destination vertex to find the shortest path to from s.
	 * 
	 * @return:				The distance in the shortest path from s to destination.
	 * 						If there is no path to the destination from s, then 
	 * 						Integer.MAX_VALUE is returned.
	 */

	public int findShortestTimePath(int destination) {
		// If there is no path to destination from s, then we do not have to do any computations.
		if (!connectedPath[destination])
			return Integer.MAX_VALUE;

		// If already explored, then we can return that value, since a shorter value cannot be
		// found.
		if (S[destination])
			return vertex[destination].getKey();

		// Otherwise some computations must be done to determine the shortest time or if the
		// destination is unreachable.
		boolean done = false;
		while (!done && notInS.getSize() != 0) {
			int u = ((Vertex) notInS.removeMin()).getVertexID();
			int currentTime = vertex[u].getKey();

			if (currentTime == Integer.MAX_VALUE)
				done = true;
			else {
				S[u] = true;

				LinkedListElement<Integer> e = neighborList[u].getFirst();
				while (e != null) {
					int v = edge[e.getElement()][1]; // v is u's neighbor.
					int t0 = edge[e.getElement()][2];
					int P = edge[e.getElement()][3];
					int d = edge[e.getElement()][4];

					// If the edge departures once.
					if (P == 0) {
						// If the edge leaves after the arrival time, and v's arrival time is closer
						// now than was previously known.
						if (t0 >= currentTime && vertex[v].getKey() > t0 + d) {
							vertex[v].setKey(t0 + d); // Update v's key since it now has a shorter
														// arrival time.
							notInS.updateHeap(vertex[v]); // Update the heap.
							parent[v] = u; // u is the neighbor of v that gave v this currently
											// known shortst path.
						}
					}
					// If the edges departures periodically.
					else {
						// Computes the smallest non-negative t for nearest departure of this edge
						int t = 0; // If t0 >= currentTime, then the first departure is after our
									// arrival time.
						if (t0 < currentTime) // Otherwise we compute the smallest non-negative t
												// such that t0 + tP >= currentTime.
							t = (((currentTime - t0) % P == 0) ? (currentTime - t0) / P : (currentTime - t0) / P + 1);

						if (t0 + t * P + d < vertex[v].getKey()) {
							vertex[v].setKey(t0 + t * P + d);
							notInS.updateHeap(vertex[v]);
							parent[v] = u;
						}
					}

					e = e.getNext();
				}

				if (u == destination)
					done = true;
			}
		}

		return vertex[destination].getKey();
	}

	/*
	 * @return:	All nodes that are visited in Dijkstra's algorithm, their parents are
	 * 			saved in this array. Index it by vertex, and vertex' parent is returned.
	 */
	public int[] getParent() {
		return this.parent;
	}
}
