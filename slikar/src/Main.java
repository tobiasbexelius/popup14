import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {

	private static final int[] dx = { 1, -1, 0, 0 };
	private static final int[] dy = { 0, 0, -1, 1 };
	private static final char EMPTY = '.';
	private static final char FLOODED = '*';
	private static final char ROCK = 'X';
	private static final char DEN = 'D';
	private static final char PAINTER = 'S';

	private static int r;
	private static int c;
	private static char[][] map;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		r = io.getInt();
		c = io.getInt();

		SpaceTime start = null;

		map = new char[r][c];
		for (int i = 0; i < r; i++) {
			String line = io.getWord();
			for (int j = 0; j < c; j++) {
				map[i][j] = line.charAt(j);
				if (map[i][j] == PAINTER)
					start = new SpaceTime(i, j, 0);
			}
		}

		boolean[][] visited = new boolean[r][c];
		Queue<SpaceTime> queue = new LinkedList<SpaceTime>();
		queue.add(start);
		visited[start.y][start.x] = true;
		int preTime = -1;
		boolean success = false;
		SpaceTime current = null;

		while (!queue.isEmpty()) {
			current = queue.poll();

			if (map[current.y][current.x] == DEN) {
				success = true;
				break;
			}

			if (current.time > preTime) {
				floodMap();
				preTime = current.time;
			}

			for (int i = 0; i < dx.length; i++) {
				int x = current.x + dx[i];
				int y = current.y + dy[i];
				if (inBounds(y, x) && !visited[y][x]) {
					visited[y][x] = true;
					if (map[y][x] == DEN || map[y][x] == EMPTY)
						queue.add(new SpaceTime(y, x, current.time + 1));
				}
			}
		}

		if (success)
			io.println(current.time);
		else
			io.println("KAKTUS");
		io.flush();
		io.close();
	}

	private static void floodMap() {
		List<SpaceTime> toFlood = new ArrayList<SpaceTime>();
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				if (map[i][j] == FLOODED) {
					for (int k = 0; k < dx.length; k++) {
						int x = j + dx[k];
						int y = i + dy[k];
						if (inBounds(y, x) && map[y][x] == EMPTY) {
							toFlood.add(new SpaceTime(y, x, -1));
						}
					}
				}
			}
		}

		for (int i = 0; i < toFlood.size(); i++) {
			SpaceTime current = toFlood.get(i);
			map[current.y][current.x] = FLOODED;
		}

	}

	private static boolean inBounds(int y, int x) {
		return y >= 0 && x >= 0 && y < r && x < c;
	}

	private static class SpaceTime {
		public int time;
		public int y, x;

		public SpaceTime(int y, int x, int time) {
			this.y = y;
			this.x = x;
			this.time = time;
		}

		public boolean equals(Object o) {
			return o instanceof SpaceTime && ((SpaceTime) o).y == y && ((SpaceTime) o).x == x;
		}

		@Override
		public String toString() {
			return "[" + y + ", " + x + " d: " + time + "]";
		}
	}
}
