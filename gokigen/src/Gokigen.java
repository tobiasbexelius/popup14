import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Gokigen {

	private static final char NOT_FILLED = '\u0000';
	private static final char UP = '/';
	private static final char DOWN = '\\';
	private static final char NO_CONSTRAINT = '.';

	private final char[][] solution;
	private final char[][] puzzle;
	private final int n;
	private final Set<State> visited;
	private Set<Coordinate> constraints;

	public Gokigen(char[][] puzzle, int n) {
		this.puzzle = puzzle;
		this.n = n;
		solution = new char[n][n];
		visited = new HashSet<State>();
		constraints = getConstraints();
	}

	public char[][] solve() {
		fillAllDeterminableBoxes(solution);
		Queue<State> queue = new PriorityQueue<State>();
		State initialState = new State(matrixCopy(solution), getCost(solution), null);
		queue.add(initialState);
		visited.add(initialState);
		while (true) {
			State current = queue.poll();
			if (current.getCost() == 0 && boardIsFilled(current.getBoard())) {
				return current.getBoard();
			}
			Coordinate lastChanged = current.getLastChanged();
			if (lastChanged != null) {
				fillAllDeterminableBoxes(current.getBoard());
				if (current.getCost() == 0 && boardIsFilled(current.getBoard())) {
					return current.getBoard();
				}
			}

			queue.addAll(getValidNeighbours(current));

		}
	}

	private boolean boardIsFilled(char[][] board) {
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board.length; j++)
				if (board[i][j] == NOT_FILLED)
					return false;
		return true;
	}

	private List<State> getValidNeighbours(State current) {
		List<State> validNeighbours = new ArrayList<State>();
		char[][] workingCopy = matrixCopy(current.getBoard());
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (workingCopy[i][j] == NOT_FILLED) {
					workingCopy[i][j] = UP;
					int cost = getCost(workingCopy);
					State newState = new State(workingCopy, cost, new Coordinate(i, j));
					if (!visited.contains(newState) && cost != -1 && !containsLoops(new Coordinate(i, j), workingCopy)) {
						validNeighbours.add(newState);
						visited.add(newState);
						workingCopy = matrixCopy(current.getBoard());
					}
					workingCopy[i][j] = DOWN;
					cost = getCost(workingCopy);
					newState = new State(workingCopy, cost, new Coordinate(i, j));
					if (!visited.contains(newState) && cost != -1 && !containsLoops(new Coordinate(i, j), workingCopy)) {
						validNeighbours.add(newState);
						visited.add(newState);
						workingCopy = matrixCopy(current.getBoard());
					} else {
						workingCopy[i][j] = NOT_FILLED;
					}
				}
			}
		}
		return validNeighbours;
	}

	private boolean containsLoops(Coordinate box, char[][] board) {
		List<Coordinate> adjacentNodes = getAdjacentNodes(box.getY(), box.getX());
		for (Coordinate node : adjacentNodes)
			if (containsLoopsFromStart(node, board))
				return true;
		return false;
	}

	private boolean containsLoopsFromStart(Coordinate start, char[][] board) {
		Queue<Coordinate> queue = new LinkedList<>();
		boolean[][] visited = new boolean[n + 1][n + 1];
		queue.add(start);
		visited[start.getY()][start.getX()] = true;
		while (!queue.isEmpty()) {
			Coordinate current = queue.poll();
			List<Coordinate> neighbours = getNeighbours(current, board);
			for (Coordinate c : neighbours) {
				if (visited[c.getY()][c.getX()]) {
					return true;
				}
				if (!visited[c.getY()][c.getX()]) {
					visited[c.getY()][c.getX()] = true;
					queue.add(c);
				}

			}
		}
		return false;
	}

	private static final int[] dy = new int[] { -1, -1, 1, 1 };
	private static final int[] dx = new int[] { -1, 1, -1, 1 };

	private List<Coordinate> getNeighbours(Coordinate c, char[][] board) {
		List<Coordinate> neighbours = new ArrayList<Coordinate>();
		for (int k = 0; k < dx.length; k++) {
			Coordinate neighbour = new Coordinate(c.getX() + dx[k], c.getY() + dy[k], c);
			if (!neighbour.equals(c.getParent()) && isInBounds(neighbour, n + 1) && edgeBetween(board, c, neighbour)) {
				neighbours.add(neighbour);
			}
		}
		return neighbours;
	}

	private boolean edgeBetween(char[][] board, Coordinate node1, Coordinate node2) {
		int x1 = node1.getX(), y1 = node1.getY(), x2 = node2.getX(), y2 = node2.getY();
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

	private int getCost(char[][] board) {
		int cost = 0;
		for (Coordinate c : constraints) {
			Set<Coordinate> adjacentBoxes = getAdjacentBoxes(c);
			int towards = 0;
			for (Coordinate box : adjacentBoxes) {
				if (isLineTowards(board, c, box)) {
					towards++;
				}
			}
			if (towards > Character.getNumericValue(puzzle[c.getY()][c.getX()])) {
				return -1; // invalid state
			}

			if (towards < Character.getNumericValue(puzzle[c.getY()][c.getX()])) {
				cost++;
			}

		}
		return cost;
	}

	private void fillAllDeterminableBoxes(char[][] board) {
		boolean changesMade = true;
		while (changesMade) {
			changesMade = false;
			for (int i = 0; i <= n; i++) {
				for (int j = 0; j <= n; j++) {
					if (puzzle[i][j] != NO_CONSTRAINT) {
						boolean changeMade = tryFill(board, new Coordinate(j, i));
						if (changeMade) {
							changesMade = true;
						}
					}
				}
			}
		}
	}

	private void fillDeterminableBoxesNear(char[][] board, Coordinate box) {
		boolean changesMade = true;
		while (changesMade) {
			changesMade = false;
			List<Coordinate> ajdacentNodes = getAdjacentNodes(box.getY(), box.getX());
			for (Coordinate node : ajdacentNodes) {
				if (puzzle[node.getY()][node.getX()] != NO_CONSTRAINT) {
					boolean changeMade = tryFill(board, node);
					if (changeMade) {
						changesMade = true;
					}
				}
			}
		}
	}

	private List<Coordinate> getAdjacentNodes(int i, int j) {
		List<Coordinate> adjacentConstraints = new ArrayList<>();
		adjacentConstraints.add(new Coordinate(j, i));
		adjacentConstraints.add(new Coordinate(j + 1, i));
		adjacentConstraints.add(new Coordinate(j, i + 1));
		adjacentConstraints.add(new Coordinate(j + 1, i + 1));
		return adjacentConstraints;
	}

	private boolean tryFill(char[][] board, Coordinate node) {
		int y = node.getY();
		int x = node.getX();
		Set<Coordinate> adjacentBoxes = getAdjacentBoxes(node);
		int value = Character.getNumericValue(puzzle[y][x]);
		int incomingLines = 0;
		int emptyBoxes = 0;
		for (Coordinate box : adjacentBoxes) {
			if (isLineTowards(board, node, box))
				incomingLines++;
			else if (board[box.getY()][box.getX()] == NOT_FILLED)
				emptyBoxes++;
		}
		if (emptyBoxes == 0)
			return false;

		if (incomingLines == value) {
			for (Coordinate box : adjacentBoxes) {
				if (board[box.getY()][box.getX()] == NOT_FILLED) {
					fillBoxAwayFrom(board, node, box);
				}
			}
			return true;
		}

		if (incomingLines + emptyBoxes == value) {
			for (Coordinate box : adjacentBoxes) {
				if (board[box.getY()][box.getX()] == NOT_FILLED) {
					fillBoxTowards(board, node, box);
				}
			}
			return true;
		}

		return false;
	}

	private Set<Coordinate> getAdjacentBoxes(Coordinate constraint) {
		int y = constraint.getY();
		int x = constraint.getX();
		Set<Coordinate> adjacentBoxes = new HashSet<Coordinate>();
		Coordinate upperLeft = new Coordinate(x - 1, y - 1);
		if (isInBounds(upperLeft, n))
			adjacentBoxes.add(upperLeft);
		Coordinate upperRight = new Coordinate(x, y - 1);
		if (isInBounds(upperRight, n))
			adjacentBoxes.add(upperRight);
		Coordinate lowerLeft = new Coordinate(x - 1, y);
		if (isInBounds(lowerLeft, n))
			adjacentBoxes.add(lowerLeft);
		Coordinate lowerRight = new Coordinate(x, y);
		if (isInBounds(lowerRight, n))
			adjacentBoxes.add(lowerRight);
		return adjacentBoxes;
	}

	private boolean isLineTowards(char[][] board, Coordinate node, Coordinate box) {
		if (board[box.getY()][box.getX()] == NOT_FILLED)
			return false;
		if (node.getY() == box.getY()) {
			if (node.getX() == box.getX())
				return board[box.getY()][box.getX()] == DOWN;
			else
				return board[box.getY()][box.getX()] == UP;
		} else {
			if (node.getX() == box.getX())
				return board[box.getY()][box.getX()] == UP;
			else
				return board[box.getY()][box.getX()] == DOWN;
		}
	}

	private boolean isInBounds(Coordinate coordinate, int size) {
		int x = coordinate.getX(), y = coordinate.getY();
		return x >= 0 && x < size && y >= 0 && y < size;
	}

	private void fillBoxTowards(char[][] board, Coordinate constraint, Coordinate cell) {
		if (constraint.getY() == cell.getY()) {
			if (constraint.getX() == cell.getX())
				board[cell.getY()][cell.getX()] = DOWN;
			else
				board[cell.getY()][cell.getX()] = UP;
		} else {
			if (constraint.getX() == cell.getX())
				board[cell.getY()][cell.getX()] = UP;
			else
				board[cell.getY()][cell.getX()] = DOWN;
		}
	}

	private void fillBoxAwayFrom(char[][] board, Coordinate constraint, Coordinate cell) {
		if (constraint.getY() == cell.getY()) {
			if (constraint.getX() == cell.getX())
				board[cell.getY()][cell.getX()] = UP;
			else
				board[cell.getY()][cell.getX()] = DOWN;
		} else {
			if (constraint.getX() == cell.getX())
				board[cell.getY()][cell.getX()] = DOWN;
			else
				board[cell.getY()][cell.getX()] = UP;
		}
	}

	private Set<Coordinate> getConstraints() {
		Set<Coordinate> constraints = new HashSet<>();
		for (int i = 0; i <= n; i++)
			for (int j = 0; j <= n; j++)
				if (puzzle[i][j] != NO_CONSTRAINT)
					constraints.add(new Coordinate(j, i));
		return constraints;
	}

	private char[][] matrixCopy(char[][] matrix) {
		char[][] copy = new char[matrix.length][];
		for (int i = 0; i < matrix.length; i++)
			copy[i] = matrix[i].clone();
		return copy;
	}
}
