import java.util.Arrays;

public class State implements Comparable<State> {

	private final char[][] board;
	private final int gCost;
	private final int hCost;
	private final int spaceRow;
	private final int spaceCol;
	private final int hashCode;
	private final int cost;

	public State(char[][] board, int gCost, int hCost, int spaceRow, int spaceCol) {
		this.board = board;
		this.gCost = gCost;
		this.hCost = hCost;
		this.spaceRow = spaceRow;
		this.spaceCol = spaceCol;
		this.hashCode = Arrays.deepHashCode(board);
		this.cost = gCost + hCost;
	}

	public int getEmptyX() {
		return spaceCol;
	}

	public int getEmptyY() {
		return spaceRow;
	}

	public int getCost() {
		return cost;
	}

	public char[][] getBoard() {
		return board;
	}

	public int getGCost() {
		return gCost;
	}

	public int getHCost() {
		return hCost;
	}

	@Override
	public int compareTo(State s) {
		return getCost() - s.getCost();
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
		sb.append("gCost: " + gCost + "\thCost: " + hCost + "\trow: " + spaceRow + "\tcol: " + spaceCol + "\n");

		for (int i = 0; i < board.length; i++) {
			sb.append(Arrays.toString(board[i]));
			sb.append("\n");
		}
		return sb.toString();
	}
}