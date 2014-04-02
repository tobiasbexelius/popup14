import java.util.Arrays;
import java.util.Set;

public class OldState implements Comparable<State> {

	private final char[][] board;
	private final int hashCode;
	private final int cost;
	private final Set<Coordinate> unsatConstraints;

	public OldState(char[][] board, Set<Coordinate> unsatConstraints) {
		this.board = board;
		this.hashCode = Arrays.deepHashCode(board);
		this.cost = unsatConstraints.size();
		this.unsatConstraints = unsatConstraints;
	}

	public int getCost() {
		return cost;
	}

	public char[][] getBoard() {
		return board;
	}

	@Override
	public int compareTo(State s) {
		return getCost() - s.getCost();
	}

	// @Override
	// public boolean equals(Object o) {
	// return o instanceof State && hashCode == ((State) o).hashCode &&
	// Arrays.deepEquals(((State) o).board, board);
	// }

	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("cost: " + cost + "\n");

		for (int i = 0; i < board.length; i++) {
			sb.append(Arrays.toString(board[i]));
			sb.append("\n");
		}
		return sb.toString();
	}

	public Set<Coordinate> getUnsatConstraints() {
		return unsatConstraints;
	}
}