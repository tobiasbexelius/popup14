import java.util.ArrayList;
import java.util.List;

public class Main {

	private static double maxDist;
	private static double squareMaxDist;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		io.getInt();
		while (io.hasMoreTokens()) {
			int numFloes = io.getInt();
			maxDist = io.getDouble() + Math.pow(10, -7);
			squareMaxDist = maxDist * maxDist + Math.pow(10, -7);

			Point s1Pos = new Point(parseX(io.getWord()), parseY(io.getWord()));
			Point s2Pos = new Point(parseX(io.getWord()), parseY(io.getWord()));

			int s1Floe = -1;
			int s2Floe = -1;

			Polygon[] floes = new Polygon[numFloes];
			Point[][] boundingRects = new Point[numFloes][2];

			for (int i = 0; i < numFloes; i++) {
				int v = io.getInt();
				List<Point> vertices = new ArrayList<>(v);

				int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
				int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

				for (int j = 0; j < v; j++) {
					int x = parseX(io.getWord());
					int y = parseY(io.getWord());

					vertices.add(new Point(x, y));

					if (x < minX)
						minX = x;
					if (x > maxX)
						maxX = x;
					if (y < minY)
						minY = y;
					if (y > maxY)
						maxY = y;

				}
				Polygon floe = new Polygon(vertices);

				if (s1Floe == -1 && floe.insidePoly(s1Pos) >= 0)
					s1Floe = i;
				if (s2Floe == -1 && floe.insidePoly(s2Pos) >= 0)
					s2Floe = i;

				floes[i] = floe;

				boundingRects[i][0] = new Point(minX, maxY);
				boundingRects[i][1] = new Point(maxX, minY);
			}

			if (s1Floe == s2Floe) {
				io.println(floes[s1Floe].area());
				continue;
			}
			List<List<Integer>> adjacentFloes = getAdajcentFloes(floes, boundingRects);

			double minArea = new IceFloes(s1Floe, s2Floe, adjacentFloes, floes).findMostRiskyFloe();

			if (minArea == -1)
				io.println("Scientists cannot meet");
			else
				io.println(minArea);
		}
		io.flush();
		io.close();
	}

	private static List<List<Integer>> getAdajcentFloes(Polygon[] floes, Point[][] rectangles) {
		int n = floes.length;
		List<List<Integer>> adjacencyList = new ArrayList<>(n);
		for (int i = 0; i < n; i++)
			adjacencyList.add(new ArrayList<Integer>(n));

		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (canJumpRect(rectangles[i], rectangles[j])) {
					if (canJump(floes[i], floes[j])) {
						adjacencyList.get(i).add(j);
						adjacencyList.get(j).add(i);
					}
				}
			}
		}

		return adjacencyList;
	}

	private static boolean canJump(Polygon p1, Polygon p2) {
		List<LineSegment> p1Segs = p1.getSegments();
		List<LineSegment> p2Segs = p2.getSegments();
		List<Point> p1Vertices = p1.getVertices();
		List<Point> p2Vertices = p2.getVertices();
		int p1size = p1Segs.size();
		int p2size = p2Segs.size();

		for (int i = 0; i < p1size; i++) {
			LineSegment s1 = p1Segs.get(i);
			Point v1 = p1Vertices.get(i);
			for (int j = 0; j < p2size; j++) {
				Point v2 = p2Vertices.get(j);
				LineSegment s2 = p2Segs.get(j);
				double d1 = s1.distanceTo(v2);
				double d2 = s2.distanceTo(v1);
				if (d1 <= maxDist || d2 <= maxDist)
					return true;
			}
		}

		return false;
	}

	private static boolean canJumpRect(Point[] rect1, Point[] rect2) {

		Point l1 = rect1[0];
		Point r1 = rect1[1];
		Point l2 = rect2[0];
		Point r2 = rect2[1];

		if (rectOverlap(l1, r1, l2, r2))
			return true;

		Point[] mostLeft = rect1[0].getX() < rect2[0].getX() ? rect1 : rect2;
		Point[] mostRight = rect2[0].getX() < rect1[0].getX() ? rect1 : rect2;

		double xDifference = mostLeft[0].getX() == mostRight[0].getX() ? 0 : mostRight[0].getX() - (mostLeft[0].getX() + (mostLeft[1].getX() - mostLeft[0].getX()));
		xDifference = xDifference >= 0 ? xDifference : 0;

		Point[] upper = rect1[0].getY() < rect2[0].getY() ? rect1 : rect2;
		Point[] lower = rect2[0].getY() < rect1[0].getY() ? rect1 : rect2;

		double yDifference = upper[0].getY() == lower[0].getY() ? 0 : lower[0].getY() - (upper[0].getY() + upper[0].getY() - upper[1].getY());
		yDifference = yDifference >= 0 ? yDifference : 0;

		double squareDist = xDifference * xDifference + yDifference * yDifference;

		return squareDist <= squareMaxDist;

	}

	private static boolean rectOverlap(Point l1, Point r1, Point l2, Point r2) {

		if (l1.getX() > r2.getX() || l2.getX() > r1.getX())
			return false;

		if (l1.getY() < r2.getY() || l2.getY() < r1.getY())
			return false;

		return true;
	}

	private static int parseX(String x) {
		return Integer.parseInt(x.substring(1));
	}

	private static int parseY(String y) {
		return Integer.parseInt(y.substring(0, y.length() - 1));
	}

}
