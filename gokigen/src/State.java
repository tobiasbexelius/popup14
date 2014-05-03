import java.util.Arrays;

public class State implements Comparable<State> {

	private final char[][] board;
	private final int hashCode;
	private final int cost;
	private final Coordinate lastChanged;

	public State(char[][] board, int cost, Coordinate lastChanged) {
		this.board = board;
		this.cost = cost;
		this.lastChanged = lastChanged;

		int result = 1;
		result = 31 * result + Arrays.deepHashCode(board);
		result = 31 * result + cost;

		this.hashCode = result;

	}

	public Coordinate getLastChanged() {
		return lastChanged;
	}

	public int getCost() {
		return cost;
	}

	public char[][] getBoard() {
		return board;
	}

	@Override
	public int compareTo(State s) {
		if (getCost() == s.getCost() && equals(s))
			return 0;
		return getCost() - s.getCost() >= 0 ? 1 : -1;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof State && hashCode == ((State) o).hashCode && Arrays.deepEquals(((State) o).board, board);
	}

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
}