
/*
 * Authors: Tobias Andersson, Carl Frendin, Jonas Haglund
 * 
 * A Vertex in Dijkstra's algorithm. It as an ID, a distance from the source
 * and a position in the heap.
 */

public class Vertex implements HeapObject {
	private int vertexID;
	private int weight;
	private int position;

	public Vertex(int vertexID) {
		this.vertexID = vertexID;
	}
	
	public int getVertexID() {
		return this.vertexID;
	}

	public void setKey(int newKey) {
		this.weight = newKey;
	}
	
	public int getKey() {
		return weight;
	}

	public void setPosition(int currentPosition) {
		this.position = currentPosition;
	}

	public int getPosition() {
		return this.position;
	}
}
