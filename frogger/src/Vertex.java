
/*
 * Authors: Tobias Andersson, Carl Frendin, Jonas Haglund
 * 
 * A Vertex in Dijkstra's algorithm. It as an ID, a distance from the source
 * and a position in the heap. Also a time field for when it was reached for
 * the timed Dijkstra algoritm.
 */

public class Vertex implements HeapObject {
	private int vertexID;
	private int distance;
	private int position;
	private int time;

	public Vertex(int vertexID) {
		this.vertexID = vertexID;
		this.position = -1;
	}
	
	public int getVertexID() {
		return this.vertexID;
	}

	public void setKey(int newKey) {
		this.distance = newKey;
	}
	
	public int getKey() {
		return this.distance;
	}

	public void setPosition(int currentPosition) {
		this.position = currentPosition;
	}

	public int getPosition() {
		return this.position;
	}
	
	public void setReachedTime(int time) {
		this.time = time;
	}
	
	public int getReachedTime() {
		return this.time;
	}
}
