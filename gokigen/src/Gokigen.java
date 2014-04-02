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
	private final Set<Coordinate> constraints;

	public Gokigen(char[][] puzzle, int n) {
		this.puzzle = puzzle;
		this.n = n;
		solution = new char[n][n];
		visited = new HashSet<State>();
		constraints = getConstraints();
	}

	public char[][] solve() {
		fillDeterminableBoxes();
		Queue<State> queue = new PriorityQueue<State>();
		queue.add(new State(matrixCopy(solution), getUnsatConstraints(solution)));
		while (true) {
			State current = queue.poll();
			if (current.getCost() == 0) {
				return current.getBoard();
			}
			queue.addAll(getValidNeighbours(current));

		}
	}

	private List<State> getValidNeighbours(State current) {
		List<State> validNeighbours = new ArrayList<State>();
		char[][] solutionCopy = matrixCopy(current.getBoard());
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (solutionCopy[i][j] == NOT_FILLED) {
					solutionCopy[i][j] = UP;
					int unsatConstraints = getUnsatConstraints(solutionCopy);
					if (unsatConstraints != -1 && !containsLoops(new Coordinate(i, j), solutionCopy)) {
						validNeighbours.add(new State(solutionCopy, unsatConstraints));
						solutionCopy = matrixCopy(current.getBoard());
					}
					solutionCopy[i][j] = DOWN;
					unsatConstraints = getUnsatConstraints(solutionCopy);
					if (unsatConstraints != -1 && !containsLoops(new Coordinate(i, j), solutionCopy)) {
						validNeighbours.add(new State(solutionCopy, unsatConstraints));
						solutionCopy = matrixCopy(current.getBoard());
					} else {
						solutionCopy[i][j] = NOT_FILLED;
					}
				}
			}
		}
		return validNeighbours;
	}

	private boolean containsLoops(Coordinate start, char[][] board) {
		Queue<Coordinate> queue = new LinkedList<>();
		boolean[][] visited = new boolean[n][n];

		queue.add(start);
		visited[start.getY()][start.getX()] = true;
		while (!queue.isEmpty()) {
			Coordinate current = queue.poll();
			List<Coordinate> neighbours = getNeighbours(current, board);
			for (Coordinate neighbour : neighbours) {
				if (visited[neighbour.getY()][neighbour.getX()]) {
					return true;
				}
				if (!visited[neighbour.getY()][neighbour.getX()]) {
					visited[neighbour.getY()][neighbour.getX()] = true;
					queue.add(neighbour);
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
			int x = c.getX() + dx[k];
			int y = c.getY() + dy[k];
			if (isInBounds(new Coordinate(x, y), n + 1) && edgeBetween(board, c.getX(), c.getY(), x, y)) {
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

	private int getUnsatConstraints(char[][] board) {
		int unsatConstraints = 0;
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
				unsatConstraints++;
			}

		}
		return unsatConstraints;
	}

	private char[][] matrixCopy(char[][] matrix) {
		char[][] copy = new char[matrix.length][];
		for (int i = 0; i < matrix.length; i++)
			copy[i] = matrix[i].clone();
		return copy;
	}

	private void fillDeterminableBoxes() {
		boolean changesMade = true;
		while (changesMade) {
			changesMade = false;
			for (int i = 0; i <= n; i++) {
				for (int j = 0; j <= n; j++) {
					if (puzzle[i][j] != NO_CONSTRAINT) {
						boolean changeMade = tryFill(new Coordinate(j, i));
						if (changeMade) {
							changesMade = true;
						}
					}
				}
			}
		}
	}

	private void setSolution(Coordinate cell, char value) {
		solution[cell.getY()][cell.getX()] = value;
	}

	private boolean tryFill(Coordinate node) {
		int y = node.getY();
		int x = node.getX();
		Set<Coordinate> adjacentBoxes = getAdjacentBoxes(node);
		int value = Character.getNumericValue(puzzle[y][x]);
		int incomingLines = 0;
		int emptyBoxes = 0;
		for (Coordinate box : adjacentBoxes) {
			if (isLineTowards(solution, node, box))
				incomingLines++;
			else if (valueAt(solution, box) == NOT_FILLED)
				emptyBoxes++;
		}
		if (emptyBoxes == 0)
			return false;

		if (incomingLines == value) {
			for (Coordinate cell : adjacentBoxes) {
				if (valueAt(solution, cell) == NOT_FILLED) {
					fillBoxAwayFrom(node, cell);
				}
			}
			return true;
		}

		if (incomingLines + emptyBoxes == value) {
			for (Coordinate cell : adjacentBoxes) {
				if (valueAt(solution, cell) == NOT_FILLED) {
					fillBoxTowards(node, cell);
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

	private boolean isInBounds(Coordinate coordinate, int size) {
		int x = coordinate.getX(), y = coordinate.getY();
		return x >= 0 && x < size && y >= 0 && y < size;
	}

	private boolean isLineTowards(char[][] board, Coordinate node, Coordinate box) {
		if (valueAt(board, box) == NOT_FILLED)
			return false;
		if (node.getY() == box.getY()) {
			if (node.getX() == box.getX())
				return valueAt(board, box) == DOWN;
			else
				return valueAt(board, box) == UP;
		} else {
			if (node.getX() == box.getX())
				return valueAt(board, box) == UP;
			else
				return valueAt(board, box) == DOWN;
		}
	}

	private void fillBoxTowards(Coordinate constraint, Coordinate cell) {
		if (constraint.getY() == cell.getY()) {
			if (constraint.getX() == cell.getX())
				setSolution(cell, DOWN);
			else
				setSolution(cell, UP);
		} else {
			if (constraint.getX() == cell.getX())
				setSolution(cell, UP);
			else
				setSolution(cell, DOWN);
		}
	}

	private void fillBoxAwayFrom(Coordinate constraint, Coordinate cell) {
		if (constraint.getY() == cell.getY()) {
			if (constraint.getX() == cell.getX())
				setSolution(cell, UP);
			else
				setSolution(cell, DOWN);
		} else {
			if (constraint.getX() == cell.getX())
				setSolution(cell, DOWN);
			else
				setSolution(cell, UP);
		}
	}

	private char valueAt(char[][] board, Coordinate c) {
		return board[c.getY()][c.getX()];
	}

	private Set<Coordinate> getConstraints() {
		Set<Coordinate> constraints = new HashSet<>();
		for (int i = 0; i <= n; i++)
			for (int j = 0; j <= n; j++)
				if (puzzle[i][j] != NO_CONSTRAINT)
					constraints.add(new Coordinate(j, i));
		return constraints;
	}
}
