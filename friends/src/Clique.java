import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Clique {

	private static final int MAX_ALLOWED_CLIQUES = 1000;
	private final Set<Integer> vertices;
	private final List<Set<Integer>> neighbourList;
	private int maximalCliques;
	private boolean tooBig;

	public Clique(Set<Integer> vertices, List<Set<Integer>> neighbourList) {
		this.vertices = vertices;
		this.neighbourList = neighbourList;
		maximalCliques = 0;
		tooBig = false;
	}

	public int getNumMaximalCliques() {
		bronKerboschVertexOrder();
		return tooBig ? Integer.MAX_VALUE : maximalCliques;
	}

	private void bronKerboschVertexOrder() {
		Set<Integer> R = new HashSet<Integer>();
		Set<Integer> P = new HashSet<Integer>(vertices);
		Set<Integer> X = new HashSet<Integer>();
		List<Integer> degeneracyList = getDegeneracyList();
		for (int v : degeneracyList) {
			Set<Integer> newR = new HashSet<Integer>(R);
			newR.add(v);
			Set<Integer> newP = getIntersection(P, neighbourList.get(v));
			Set<Integer> newX = getIntersection(X, neighbourList.get(v));
			bronKerbosch(newR, newP, newX);
			P.remove(v);
			X.add(v);
		}
	}

	private List<Integer> getDegeneracyList() {
		List<Integer> degeneracyList = new ArrayList<Integer>(vertices);
		Collections.sort(degeneracyList, new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				return neighbourList.get(o1).size() - neighbourList.get(o2).size();
			}

		});

		return degeneracyList;

	}

	private void bronKerbosch(Set<Integer> R, Set<Integer> P, Set<Integer> X) {
		if (maximalCliques > MAX_ALLOWED_CLIQUES) {
			tooBig = true;
			return;
		}

		if (P.isEmpty() && X.isEmpty()) {
			maximalCliques++;
			return;
		}

		int pivot = getPivot(P, X);

		Set<Integer> nonPivotNeighbours = new HashSet<>(P);
		nonPivotNeighbours.removeAll(neighbourList.get(pivot));

		for (int v : nonPivotNeighbours) {
			if (maximalCliques > MAX_ALLOWED_CLIQUES) {
				tooBig = true;
				return;
			}
			Set<Integer> newR = new HashSet<>(R);
			newR.add(v);
			Set<Integer> newP = getIntersection(P, neighbourList.get(v));
			Set<Integer> newX = getIntersection(X, neighbourList.get(v));
			bronKerbosch(newR, newP, newX);
			P.remove(v);
			X.add(v);
		}
	}

	private int getPivot(Set<Integer> P, Set<Integer> X) {
		int maxVertexP = getHighestDegreeVertex(P, P);
		int maxDegreeP = maxVertexP == -1 ? -1 : neighbourList.get(maxVertexP).size();
		int maxVertexX = getHighestDegreeVertex(P, X);
		int maxDegreeX = maxVertexX == -1 ? -1 : neighbourList.get(maxVertexX).size();
		return maxDegreeP > maxDegreeX ? maxVertexP : maxVertexX;
	}

	private int getHighestDegreeVertex(Set<Integer> P, Set<Integer> vertices) {
		int max = -1;
		int maxVertex = -1;
		for (int v : vertices) {
			Set<Integer> neighboursInP = getIntersection(neighbourList.get(v), P);
			int degree = neighboursInP.size();
			if (degree > max) {
				max = degree;
				maxVertex = v;
			}
		}
		return maxVertex;
	}

	public Set<Integer> getIntersection(Set<Integer> set1, Set<Integer> set2) {
		Set<Integer> a;
		Set<Integer> b;
		Set<Integer> res = new HashSet<>();
		if (set1.size() <= set2.size()) {
			a = set1;
			b = set2;
		} else {
			a = set2;
			b = set1;
		}
		for (int e : a) {
			if (b.contains(e)) {
				res.add(e);
			}
		}
		return res;
	}
}
