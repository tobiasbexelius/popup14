import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		int n = io.getInt();

		List<Double> boxes = new ArrayList<>();
		TreeSet<Point> points = new TreeSet<>();

		int minY = Integer.MAX_VALUE;
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;

		for (int i = 0; i < n * 2; i++) {
			int x = io.getInt();
			int y = io.getInt();
			if (x < minX)
				minX = x;
			if (y < minY)
				minY = y;
			if (x > maxX)
				maxX = x;
			if (y > maxY)
				maxY = y;
			points.add(new Point(x, y));
		}

		double totalArea = (maxX - minX) * (maxY - minY);

		for (Point current : points) {
			if (current.getY() == maxY)
				break;
			for (Point right : points.tailSet(current, false)) {

				if (current.getY() != right.getY())
					break;

				Point up = null;

				for (Point p : points.tailSet(current, false)) {
					if (current.getX() == p.getX()) {
						up = p;
						break;
					}
					if (p.getY() > current.getY() && p.getX() > current.getX())
						break;
				}

				if (up == null)
					continue;

				Point upperRight = null;

				for (Point p : points.tailSet(up, false)) {
					if (right.getX() == p.getX() && up.getY() == p.getY()) {
						upperRight = p;
						break;
					}

					if (p.getX() > right.getX() || p.getY() > up.getY())
						break;
				}

				if (upperRight == null)
					continue;

				double area = (right.getX() - current.getX()) * (up.getY() - current.getY());
				boxes.add(area / totalArea);
				break;

			}
		}

		Collections.sort(boxes);

		for (int i = boxes.size() - 1; i >= 0; i--) {
			io.println(boxes.get(i));
		}

		io.flush();
		io.close();
	}
}
