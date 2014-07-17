public class Edge implements Comparable<Edge> {
	private final int u;
	private final int v;
	private final String word;

	public Edge(int u, int v, String word) {
		this.u = u;
		this.v = v;
		this.word = word;
	}

	public int getU() {
		return u;
	}

	public int getV() {
		return v;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + u;
		result = prime * result + v;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (u != other.u)
			return false;
		if (v != other.v)
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}

	public String getWord() {
		return word;
	}

	@Override
	public String toString() {
		return "[u=" + u + ", v=" + v + ", word=" + word + "]";
	}

	@Override
	public int compareTo(Edge arg0) {
		return word.compareTo(arg0.getWord());
	}

}