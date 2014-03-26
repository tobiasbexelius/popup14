import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Main {

	private static final int[] dy = new int[] { -2, -2, -1, -1, 1, 1, 2, 2 };
	private static final int[] dx = new int[] { -1, 1, -2, 2, -2, 2, -1, 1 };
	private static final int INFINITY = Integer.MAX_VALUE;
	private static final char[][] SOLUTION = new char[][] { //
	{ '1', '1', '1', '1', '1' }, //
			{ '0', '1', '1', '1', '1' }, //
			{ '0', '0', ' ', '1', '1' }, //
			{ '0', '0', '0', '0', '1' }, //
			{ '0', '0', '0', '0', '0' } //
	};
	private static final int SOLUTION_HASHCODE = Arrays.deepHashCode(SOLUTION);
	private static final char EMPTY = ' ';
	private static final int ROWS = 5;
	private static final int COLS = 5;
	private static final int MAX_DEPTH = 10;
	private static final Set<State> visited = new HashSet<State>();

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int tests = Integer.parseInt(in.readLine());
		for (int i = 0; i < tests; i++) {
			char[][] board = new char[ROWS][COLS];
			for (int j = 0; j < ROWS; j++) {
				board[j] = in.readLine().toCharArray();
			}
			int minimumMoves = getMinimumMoves(board);
			if (minimumMoves == INFINITY)
				System.out.println("Unsolvable in less than 11 move(s).");
			else
				System.out.println("Solvable in " + minimumMoves + " move(s).");
		}
	}

	private static int getMinimumMoves(char[][] board) {
		int bestSolution = INFINITY;

		Queue<State> queue = new PriorityQueue<State>();
		State startState = getStartState(board);
		queue.add(startState);
		visited.add(startState);
		while (!queue.isEmpty()) {
			State current = queue.poll();
			if (current.hashCode() == SOLUTION_HASHCODE && Arrays.deepEquals(current.board, SOLUTION)) {
				bestSolution = current.gCost;
				break;
			} else if (current.gCost < MAX_DEPTH) {
				List<State> neighbours = getNonvisitedNeighbours(current);
				for (State neighbour : neighbours) {
					queue.add(neighbour);
				}
			}
		}
		return bestSolution;
	}

	private static List<State> getNonvisitedNeighbours(State state) {
		List<State> nonVisitedNeighbours = new ArrayList<State>();

		for (int i = 0; i < dx.length; i++) {
			int y = state.spaceRow + dy[i];
			int x = state.spaceCol + dx[i];
			if (isInBounds(x, y)) {
				char[][] newBoard = matrixCopy(state.board);
				newBoard[state.spaceRow][state.spaceCol] = newBoard[y][x];
				newBoard[y][x] = EMPTY;
				State newState = new State(newBoard, state.gCost + 1, getHCost(newBoard), y, x);

				if (!visited.contains(newState)) {
					nonVisitedNeighbours.add(newState);
					visited.add(newState);
				}
			}
		}
		return nonVisitedNeighbours;
	}

	private static boolean isInBounds(int x, int y) {
		return x >= 0 && x < COLS && y >= 0 && y < ROWS;
	}

	private static int getHCost(char[][] board) {
		int misplacedPieces = 0;
		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLS; j++)
				if (board[i][j] != SOLUTION[i][j])
					misplacedPieces++;
		return misplacedPieces;
	}

	private static State getStartState(char[][] board) {
		int spaceRow = -1;
		int spaceCol = -1;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (board[i][j] == EMPTY) {
					spaceRow = i;
					spaceCol = j;
					break;
				}
			}
			if (spaceRow != -1)
				break;
		}
		return new State(matrixCopy(board), 0, getHCost(board), spaceRow, spaceCol);
	}

	private static char[][] matrixCopy(char[][] matrix) {
		char[][] copy = new char[matrix.length][];
		for (int i = 0; i < matrix.length; i++)
			copy[i] = matrix[i].clone();
		return copy;
	}

	private static class State implements Comparable<State> {

		public char[][] board;
		public int gCost;
		public int hCost;
		public int spaceRow;
		public int spaceCol;
		public int hashCode;

		public State(char[][] board, int gCost, int hCost, int spaceRow, int spaceCol) {
			this.board = board;
			this.gCost = gCost;
			this.hCost = hCost;
			this.spaceRow = spaceRow;
			this.spaceCol = spaceCol;
			this.hashCode = Arrays.deepHashCode(board);
		}

		public int getCost() {
			return gCost + hCost;
		}

		@Override
		public int compareTo(State s) {
			if (this.equals(s))
				return 0;
			if (getCost() < s.getCost())
				return -1;
			return 1;
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof State && Arrays.deepEquals(((State) o).board, board);
		}

		@Override
		public int hashCode() {
			return hashCode;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("gCost: " + gCost + "\thCost: " + hCost + "\trow: " + spaceRow + "\tcol: " + spaceCol + "\n");

			for (int i = 0; i < ROWS; i++) {
				sb.append(Arrays.toString(board[i]));
				sb.append("\n");
			}
			return sb.toString();
		}
	}
}
