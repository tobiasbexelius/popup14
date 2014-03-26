public class Edge {
	private int startVertex;
	private int endVertex;
	private double weight;

	public Edge(int startVertex, int endVertex, double weight) {
		this.startVertex = startVertex;
		this.endVertex = endVertex;
		this.weight = weight;
	}

	public int getStart() {
		return startVertex;
	}

	public int getEnd() {
		return endVertex;
	}

	public double getWeight() {
		return weight;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Edge && startVertex == ((Edge) o).getStart() && endVertex == ((Edge) o).getEnd() && weight == ((Edge) o).getWeight())
			return true;
		return false;
	}

	@Override
	public String toString() {
		return startVertex + " -> " + endVertex + " w: " + weight;
	}
}