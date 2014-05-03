import java.util.BitSet;
import java.util.List;

public class Clique {

	private static final int MAX_CLIQUES = 1000;
	private final List<BitSet> neighbourList;
	private int maximalCliques;
	private boolean tooBig;
	private final int n;

	public Clique(List<BitSet> neighbourList) {
		this.neighbourList = neighbourList;
		maximalCliques = 0;
		this.n = neighbourList.size();
		tooBig = false;
	}

	public int getNumMaximalCliques() {
		BitSet R = new BitSet(n);
		BitSet P = new BitSet(n);
		P.flip(0, n);
		BitSet X = new BitSet(n);
		bronKerbosch(R, P, X);
		return tooBig ? -1 : maximalCliques;
	}

	private void bronKerbosch(BitSet R, BitSet P, BitSet X) {
		if (maximalCliques > MAX_CLIQUES) {
			tooBig = true;
			return;
		}
		if (P.isEmpty() && X.isEmpty()) {
			maximalCliques++;
			return;
		}

		int pivot = getPivot(P, X);

		BitSet nonPivotNeighbours = (BitSet) P.clone();
		nonPivotNeighbours.andNot(neighbourList.get(pivot));

		for (int v = nonPivotNeighbours.nextSetBit(0); v >= 0; v = nonPivotNeighbours.nextSetBit(v + 1)) {
			if (maximalCliques > MAX_CLIQUES) {
				tooBig = true;
				return;
			}
			BitSet newR = (BitSet) R.clone();
			newR.set(v);
			BitSet newP = (BitSet) P.clone();
			newP.and(neighbourList.get(v));

			BitSet newX = (BitSet) X.clone();
			newX.and(neighbourList.get(v));

			bronKerbosch(newR, newP, newX);

			P.clear(v);
			X.set(v);
		}
	}

	private int getPivot(BitSet P, BitSet X) {
		int maxVertexP = getHighestDegreeVertex(P, P);
		int maxDegreeP = maxVertexP == -1 ? -1 : neighbourList.get(maxVertexP).size();
		int maxVertexX = getHighestDegreeVertex(P, X);
		int maxDegreeX = maxVertexX == -1 ? -1 : neighbourList.get(maxVertexX).size();
		return maxDegreeP > maxDegreeX ? maxVertexP : maxVertexX;
	}

	private int getHighestDegreeVertex(BitSet P, BitSet vertices) {
		int max = -1;
		int maxVertex = -1;

		for (int v = vertices.nextSetBit(0); v >= 0; v = vertices.nextSetBit(v + 1)) {
			BitSet neighboursInP = (BitSet) P.clone();
			neighboursInP.and(neighbourList.get(v));
			int degree = neighboursInP.cardinality();
			if (degree > max) {
				max = degree;
				maxVertex = v;
			}
		}
		return maxVertex;
	}
}
