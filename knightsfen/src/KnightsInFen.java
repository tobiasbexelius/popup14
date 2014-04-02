import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class KnightsInFen {
	public static final int ROWS = 5;
	public static final int COLS = 5;
	public static final int INFINITY = Integer.MAX_VALUE;
	public static final int MAX_DEPTH = 10;

	private static final int[] dy = new int[] { -2, -2, -1, -1, 1, 1, 2, 2 };
	private static final int[] dx = new int[] { -1, 1, -2, 2, -2, 2, -1, 1 };

	private static final char[][] SOLUTION_BOARD = new char[][] { //
	{ '1', '1', '1', '1', '1' }, //
			{ '0', '1', '1', '1', '1' }, //
			{ '0', '0', ' ', '1', '1' }, //
			{ '0', '0', '0', '0', '1' }, //
			{ '0', '0', '0', '0', '0' } //
	};
	private static final State SOLUTION = new State(SOLUTION_BOARD, 0, 0, ROWS / 2, COLS / 2);
	private static final char EMPTY = ' ';

	private final State initialState;
	private Map<State, Integer> forwardsVisited;
	private Map<State, Integer> backwardsVisited;

	public KnightsInFen(char[][] board) {
		initialState = getInitialState(board);
		forwardsVisited = new HashMap<>();
		backwardsVisited = new HashMap<>();
	}

	public int getMinimumMoves() {
		int solution = aStarForwards();
		return solution != INFINITY ? solution : aStarBackwards();
	}

	private int aStarForwards() {
		Queue<State> queue = new PriorityQueue<State>();
		State startState = initialState;
		queue.add(startState);
		forwardsVisited.put(startState, 0);
		while (!queue.isEmpty()) {
			State current = queue.poll();

			if (backwardsVisited.containsKey(current))
				return current.getGCost() + backwardsVisited.get(current);
			if (current.equals(SOLUTION))
				return current.getGCost();

			if (current.getGCost() < MAX_DEPTH / 2) {
				List<State> neighbours = getNonvisitedNeighbours(current, true);
				for (State neighbour : neighbours) {
					queue.add(neighbour);
				}
			}
		}
		return INFINITY;
	}

	private int aStarBackwards() {
		Queue<State> queue = new PriorityQueue<State>();
		State startState = getInitialState(SOLUTION.getBoard());
		queue.add(startState);
		backwardsVisited.put(startState, 0);
		while (!queue.isEmpty()) {
			State current = queue.poll();

			if (forwardsVisited.containsKey(current))
				return current.getGCost() + forwardsVisited.get(current);

			if (current.equals(initialState))
				return current.getGCost();

			if (current.getGCost() < MAX_DEPTH / 2) {
				List<State> neighbours = getNonvisitedNeighbours(current, false);
				for (State neighbour : neighbours) {
					queue.add(neighbour);
				}
			}
		}
		return INFINITY;
	}

	private List<State> getNonvisitedNeighbours(State state, boolean forwardSearch) {
		List<State> nonVisitedNeighbours = new ArrayList<State>();

		for (int i = 0; i < dx.length; i++) {
			int y = state.getEmptyY() + dy[i];
			int x = state.getEmptyX() + dx[i];
			if (isInBounds(x, y)) {
				char[][] newBoard = matrixCopy(state.getBoard());
				newBoard[state.getEmptyY()][state.getEmptyX()] = newBoard[y][x];
				newBoard[y][x] = EMPTY;
				int hCost = getHCost(state, y, x);
				State newState = new State(newBoard, state.getGCost() + 1, hCost, y, x);
				if (forwardSearch && !forwardsVisited.containsKey(newState)) {
					nonVisitedNeighbours.add(newState);
					forwardsVisited.put(newState, newState.getGCost());
				} else if (!forwardSearch && !backwardsVisited.containsKey(newState)) {
					nonVisitedNeighbours.add(newState);
					backwardsVisited.put(newState, newState.getGCost());
				}
			}
		}
		return nonVisitedNeighbours;
	}

	private boolean isInBounds(int x, int y) {
		return x >= 0 && x < COLS && y >= 0 && y < ROWS;
	}

	private int getInitialHCost(char[][] board) {
		int misplacedPieces = 0;
		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLS; j++)
				if (board[i][j] != SOLUTION_BOARD[i][j] && board[i][j] != EMPTY)
					misplacedPieces++;
		return misplacedPieces;
	}

	private int getHCost(State oldState, int newEmptyX, int newEmptyY) {
		char[][] oldBoard = oldState.getBoard();
		boolean oldPlacementCorrect = oldBoard[newEmptyY][newEmptyX] == SOLUTION_BOARD[newEmptyY][newEmptyX];
		boolean newPlacementCorrect = oldBoard[newEmptyY][newEmptyX] == SOLUTION_BOARD[oldState.getEmptyY()][oldState.getEmptyX()];

		if (oldPlacementCorrect && !newPlacementCorrect)
			return oldState.getHCost() + 1;
		if (!oldPlacementCorrect && newPlacementCorrect)
			return oldState.getHCost() - 1;
		return oldState.getHCost();
	}

	private State getInitialState(char[][] board) {
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
		return new State(matrixCopy(board), 0, getInitialHCost(board), spaceRow, spaceCol);
	}

	private char[][] matrixCopy(char[][] matrix) {
		char[][] copy = new char[matrix.length][];
		for (int i = 0; i < matrix.length; i++)
			copy[i] = matrix[i].clone();
		return copy;
	}

}
