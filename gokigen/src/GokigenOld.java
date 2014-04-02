import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class GokigenOld {

	public static final int INFINITY = Integer.MAX_VALUE;
	public static final int MAX_DEPTH = 10;
	private static final int[] dy = new int[] { -1, -1, 0, 0 };
	private static final int[] dx = new int[] { -1, 0, -1, 0 };
	private static final int[] dy2 = new int[] { -1, -1, 1, 1 };
	private static final int[] dx2 = new int[] { -1, 1, -1, 1 };
	private static final char UP = '/';
	private static final char DOWN = '\\';
	private static final char EMPTY = '.';

	private char[][] constraints;
	private final int n;
	private final Set<State> visited;

	public GokigenOld(char[][] constraints, int n) {
		this.constraints = constraints;
		this.n = n;
		visited = new HashSet<State>();
	}

	public char[][] solve() {
		Queue<State> queue = new PriorityQueue<State>();
		State initialState = getInitialState();
		queue.add(initialState);
		visited.add(initialState);
		State current = null;
		while (true) {
			if (queue.isEmpty()) {
				queue.add(randomChange(current));
				visited.add(queue.peek());
			}
			current = queue.poll();
			if (current.getCost() == 0 && !containsLoops(current.getBoard()))
				return current.getBoard();

			List<State> neighbours = getNonvisitedNeighbours(current);
			for (State neighbour : neighbours)
				queue.add(neighbour);
		}
	}

	private State randomChange(State current) {
		char[][] newBoard = matrixCopy(current.getBoard());
		Random random = new Random();
		int x = random.nextInt(n);
		int y = random.nextInt(n);

		newBoard[y][x] = newBoard[y][x] == UP ? DOWN : UP;

		return containsLoops(newBoard) ? randomChange(current) : new State(newBoard, getUnsatConstraints(newBoard));

	}

	private State getInitialState() {
		char[][] board = new char[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				board[i][j] = i % 2 == 0 ? UP : DOWN;

		return new State(board, getUnsatConstraints(board));
	}

	private List<State> getNonvisitedNeighbours(State current) {
		List<State> neighbours = new ArrayList<>();

		for (Coordinate c : current.getUnsatConstraints()) {
			for (int i = 0; i < dx.length; i++) {
				int x = c.getX() + dx[i];
				int y = c.getY() + dy[i];
				if (isInBounds(x, y, n)) {
					char[][] newBoard = matrixCopy(current.getBoard());
					newBoard[y][x] = newBoard[y][x] == UP ? DOWN : UP;
					State newState = new State(newBoard, getUnsatConstraints(newBoard));
					if (!visited.contains(newState) && !containsLoops(newBoard)) {
						neighbours.add(newState);
						visited.add(newState);
					}

				}
			}
		}

		return neighbours;
	}

	private boolean containsLoops(char[][] board) {
		Queue<Coordinate> queue = new LinkedList<>();
		int N = constraints.length;
		boolean[][] visited = new boolean[N][N];

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				if (!visited[i][j]) {
					queue.add(new Coordinate(j, i));
					visited[i][j] = true;
					while (!queue.isEmpty()) {
						Coordinate current = queue.poll();
						List<Coordinate> neighbours = getNeighbours(current, board);
						for (Coordinate n : neighbours) {
							if (visited[n.getY()][n.getX()]) {
								return true;
							}
							if (!visited[n.getY()][n.getX()]) {
								visited[n.getY()][n.getX()] = true;
								queue.add(n);
							}

						}
					}
				}
			}
		return false;
	}

	private List<Coordinate> getNeighbours(Coordinate c, char[][] board) {
		List<Coordinate> neighbours = new ArrayList<Coordinate>();
		for (int k = 0; k < dx2.length; k++) {
			int x = c.getX() + dx2[k];
			int y = c.getY() + dy2[k];
			if (isInBounds(x, y, n + 1) && edgeBetween(board, c.getX(), c.getY(), x, y)) {
				Coordinate neighbour = new Coordinate(x, y, c);
				if (!neighbour.equals(c.getParent()))
					neighbours.add(neighbour);
			}
		}
		return neighbours;
	}

	private boolean edgeBetween(char[][] board, int x1, int y1, int x2, int y2) {
		int minX = Math.min(x1, x2);
		int minY = Math.min(y1, y2);
		char direction = board[minY][minX];
		if (x1 < x2 && ((y1 < y2 && direction == DOWN) || (y1 > y2 && direction == UP))) {
			return true;
		}

		if (x1 > x2 && ((y1 > y2 && direction == DOWN) || (y1 < y2 && direction == UP))) {
			return true;
		}

		return false;
	}

	private Set<Coordinate> getUnsatConstraints(char[][] board) {
		Set<Coordinate> unsatConstraints = new HashSet<>();
		int N = constraints.length;
		for (int y = 0; y < N; y++)
			for (int x = 0; x < N; x++)
				if (constraints[y][x] != EMPTY && numConnections(board, x, y) != Character.getNumericValue(constraints[y][x])) {
					unsatConstraints.add(new Coordinate(x, y));
				}

		return unsatConstraints;
	}

	private int numConnections(char[][] board, int x, int y) {
		int numConnections = 0;

		if (isInBounds(x - 1, y - 1, n) && board[y - 1][x - 1] == DOWN)
			numConnections++;

		if (isInBounds(x, y - 1, n) && board[y - 1][x] == UP)
			numConnections++;

		if (isInBounds(x - 1, y, n) && board[y][x - 1] == UP)
			numConnections++;

		if (isInBounds(x, y, n) && board[y][x] == DOWN)
			numConnections++;

		return numConnections;
	}

	private boolean isInBounds(int x, int y, int size) {
		return x >= 0 && x < size && y >= 0 && y < size;
	}

	private char[][] matrixCopy(char[][] matrix) {
		char[][] copy = new char[matrix.length][];
		for (int i = 0; i < matrix.length; i++)
			copy[i] = matrix[i].clone();
		return copy;
	}

	// private List<Coordinate> getAdjacentConstraints(int i, int j) {
	// List<Coordinate> adjacentConstraints = new ArrayList<>();
	// if (constraints[i][j] != EMPTY)
	// adjacentConstraints.add(new Coordinate(j, i));
	// if (constraints[i][j + 1] != EMPTY)
	// adjacentConstraints.add(new Coordinate(j + 1, i));
	// if (constraints[i + 1][j] != EMPTY)
	// adjacentConstraints.add(new Coordinate(j, i + 1));
	// if (constraints[i + 1][j + 1] != EMPTY)
	// adjacentConstraints.add(new Coordinate(j + 1, i + 1));
	// return adjacentConstraints;
	// }
	//
	// private boolean isEdge(int i, int j) {
	// return i == 0 || i == n || j == 0 || j == n;
	// }
	//
	// private boolean isCorner(int i, int j) {
	// return (i == 0 && j == 0) || (i == 0 && j == n) || (i == n && j == 0) || (i ==
	// n && j == n);
	// }
	//
	// private void tryFillCorners() {
	// char upperLeft = constraints[0][0];
	// if (upperLeft != EMPTY)
	// solution[0][0] = upperLeft == 0 ? UP : DOWN;
	// char upperRight = constraints[0][n];
	// if (upperRight != EMPTY)
	// solution[0][n - 1] = upperRight == 0 ? DOWN : UP;
	// char lowerLeft = constraints[n][0];
	// if (lowerLeft != EMPTY)
	// solution[n - 1][0] = lowerLeft == 0 ? DOWN : UP;
	// char lowerRight = constraints[n][n];
	// if (lowerRight != EMPTY)
	// solution[n - 1][n - 1] = lowerRight == 0 ? UP : DOWN;
	// }
}
