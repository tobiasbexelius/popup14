import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents a disjoint-set data structure. A DisjointSet is created by passing a single
 * parameter, n, to the contstructor. Upon creation n disjoint sets are created, each containing one
 * element, which is also the representative of the set. The disjoint set can be modified with the
 * operation union(int x, int y) which unions the sets containing x and y. One can also find the
 * representative of a given set with the operation find(x).
 * 
 * @author Tobias Andersson and Carl Frendin
 */
public class DisjointSet {

	private int[] parents; // parents[i] contains the parent of element number i;
	private int[] rank; // rank[i] contains the rank of element number i. This is used for balancing
						// the disjoint set.

	/**
	 * Constructs a disjoint set containing n elements. Each element is initially placed its own set
	 * of which it is the representative.
	 * 
	 * @param n
	 *            the number of elements in this disjoint set
	 */
	public DisjointSet(int n) {
		parents = new int[n];
		rank = new int[n];
		for (int i = 0; i < n; i++) {
			parents[i] = i;
		}
	}

	/**
	 * Unions the two sets which contain elements x and y. If x and y are in the same set, the same
	 * element or either does not exist, nothing happens.
	 * 
	 * @param x
	 *            The first element.
	 * @param y
	 *            The second element.
	 */
	public void union(int x, int y) {
		int xRoot = find(x);
		int yRoot = find(y);
		if (xRoot == yRoot)
			return;

		// uses the rank of the representatives to determine which tree to append to which to get
		// optimal balance
		if (rank[xRoot] < rank[yRoot]) {
			parents[xRoot] = yRoot;
		} else if (rank[xRoot] > rank[yRoot]) {
			parents[yRoot] = xRoot;
		} else {
			parents[yRoot] = xRoot;
			rank[xRoot] = rank[xRoot] + 1;
		}
	}

	/**
	 * Finds and returns the representative of the set containing x.
	 * 
	 * @param x
	 *            the element whose representative is to be searched for.
	 * @return the representative of the set containing x
	 */
	public int find(int x) {
		if (parents[x] != x)
			parents[x] = find(parents[x]);
		return parents[x];
	}

	public String toString() {
		int n = parents.length;
		List<Set<Integer>> sets = new ArrayList<>();
		for (int i = 0; i < n; i++)
			sets.add(new HashSet<Integer>());
		for (int i = 0; i < n; i++) {
			sets.get(find(i)).add(i);

		}
		return sets.toString();
	}
}
