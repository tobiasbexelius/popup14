import java.util.ArrayList;
import java.util.List;

public class AlmostUnion {

	private int[] aliases;
	private List<Integer> parents;
	private List<Integer> ranks;
	private List<Integer> numChildren;
	private List<Integer> sumChildren;

	public AlmostUnion(int n) {
		aliases = new int[n];
		parents = new ArrayList<Integer>(n);
		ranks = new ArrayList<Integer>(n);
		numChildren = new ArrayList<Integer>(n);
		sumChildren = new ArrayList<Integer>(n);
		for (int i = 0; i < n; i++) {
			aliases[i] = i;
			parents.add(i);
			ranks.add(0);
			numChildren.add(0);
			sumChildren.add(0);
		}
	}

	public void union(int x, int y) {
		x = aliases[x];
		y = aliases[y];
		int xRoot = findRoot(x);
		int yRoot = findRoot(y);
		if (xRoot == yRoot)
			return;
		if (ranks.get(xRoot) < ranks.get(yRoot)) {
			parents.set(xRoot, yRoot);
			numChildren.set(yRoot, numChildren.get(yRoot) + numChildren.get(xRoot) + 1);
			sumChildren.set(yRoot, sumChildren.get(yRoot) + sumChildren.get(xRoot) + xRoot);
		} else if (ranks.get(xRoot) > ranks.get(yRoot)) {
			parents.set(yRoot, xRoot);
			numChildren.set(xRoot, numChildren.get(xRoot) + numChildren.get(yRoot) + 1);
			sumChildren.set(xRoot, sumChildren.get(xRoot) + sumChildren.get(yRoot) + yRoot);
		} else {
			parents.set(yRoot, xRoot);
			numChildren.set(xRoot, numChildren.get(xRoot) + numChildren.get(yRoot) + 1);
			sumChildren.set(xRoot, sumChildren.get(xRoot) + sumChildren.get(yRoot) + yRoot);
			ranks.set(xRoot, ranks.get(xRoot) + 1);
		}
	}

	public int findRoot(int x) {
		if (parents.get(x) != x)
			parents.set(x, findRoot(parents.get(x)));
		return parents.get(x);
	}

	public boolean same(int a, int b) {
		a = aliases[a];
		b = aliases[b];
		int xRoot = findRoot(a);
		int yRoot = findRoot(b);
		return xRoot == yRoot;
	}

	public void move(int a, int b) {
		int originalA = a;
		a = aliases[a];
		b = aliases[b];
		int xRoot = findRoot(a);
		int yRoot = findRoot(b);
		if (xRoot == yRoot)
			return;
		int newA = parents.size();
		aliases[originalA] = newA;
		parents.add(yRoot);

		numChildren.set(yRoot, numChildren.get(yRoot) + 1);
		sumChildren.set(yRoot, sumChildren.get(yRoot) + originalA);
		numChildren.set(xRoot, numChildren.get(xRoot) - 1);
		sumChildren.set(xRoot, sumChildren.get(xRoot) - originalA);
	}

	public String componentString(int a) {
		a = aliases[a];
		int root = findRoot(a);
		return numChildren.get(root) + 1 + " " + (sumChildren.get(root) + root + numChildren.get(root) + 1);

	}
}
