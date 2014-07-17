import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Main {

	private static final int[] dx = new int[] { -1, 0, 0, 1 };
	private static final int[] dy = new int[] { 0, -1, 1, 0 };
	private static final char SPACE = ' ';
	private static final char WALL = '#';
	private static final char ALIEN = 'A';
	private static final char START = 'S';

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		reader.readLine();
		while (reader.ready()) {
			String[] dimensions = reader.readLine().split(" ");
			int x = Integer.parseInt(dimensions[0]);
			int y = Integer.parseInt(dimensions[1]);

			char[][] maze = new char[y][x];
			List<Coordinate> splitPoints = new ArrayList<>();
			Map<Coordinate, Integer> index = new HashMap<>();

			for (int i = 0; i < y; i++) {
				maze[i] = reader.readLine().toCharArray();
				for (int j = 0; j < maze[i].length; j++)
					if (maze[i][j] == ALIEN || maze[i][j] == START) {
						Coordinate p = new Coordinate(j, i);
						splitPoints.add(p);
						index.put(p, splitPoints.size() - 1);
					}

			}

			if (x < 3 || y < 3 || splitPoints.size() < 2) {
				System.out.println("0");
				continue;
			}

			String[] edges = new String[splitPoints.size() * (splitPoints.size() - 1)];
			int edgeIndex = 0;
			for (Coordinate point : splitPoints) {
				boolean[][] visited = new boolean[y][x];
				Queue<Coordinate> queue = new LinkedList<>();
				queue.add(point);
				visited[point.y][point.x] = true;
				while (!queue.isEmpty()) {
					Coordinate current = queue.poll();

					if (maze[current.y][current.x] != SPACE && !current.equals(point)) {
						edges[edgeIndex] = index.get(point) + " " + index.get(current) + " " + current.distance;
						edgeIndex++;
					}

					for (int i = 0; i < dx.length; i++) {
						int nX = current.x + dx[i];
						int nY = current.y + dy[i];
						if (nX < 0 || nY < 0 || nX >= x || nY >= y)
							continue;

						if (!visited[nY][nX] && maze[nY][nX] != WALL) {
							queue.add(new Coordinate(nX, nY, current.distance + 1));
							visited[nY][nX] = true;
						}

					}

				}

			}

			MinimumSpanningTree mst = new MinimumSpanningTree(splitPoints.size(), edges.length);
			mst.setInstance(splitPoints.size(), edges.length, edges);
			mst.computeMST();
			System.out.println(mst.getCost());

		}
	}

	private static class Coordinate {
		public final int x;
		public final int y;
		public final int distance;

		public Coordinate(int x, int y) {
			this.x = x;
			this.y = y;
			this.distance = 0;
		}

		public Coordinate(int x, int y, int distance) {
			this.x = x;
			this.y = y;
			this.distance = distance;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Coordinate other = (Coordinate) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

	}

}
