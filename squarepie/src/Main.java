import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		int n = io.getInt();

		Map<Integer, TreeMap<Integer, TreeMap<Integer, LineSegment>>> vertical = new HashMap<>();
		Map<Integer, TreeMap<Integer, TreeMap<Integer, LineSegment>>> horizontal = new HashMap<>();

		int minY = Integer.MAX_VALUE;
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;

		for (int i = 0; i < n; i++) {
			int x1 = io.getInt();
			int y1 = io.getInt();
			int x2 = io.getInt();
			int y2 = io.getInt();
			LineSegment l = new LineSegment(new Point(x1, y1), new Point(x2, y2));
			int length = (int) Math.round(l.length());
			int x = (int) l.getStart().getX();
			int y = (int) l.getStart().getY();
			if (l.isVertical()) {
				if (!vertical.containsKey(length))
					vertical.put(length, new TreeMap<Integer, TreeMap<Integer, LineSegment>>());
				if (!vertical.get(length).containsKey(x))
					vertical.get(length).put(x, new TreeMap<Integer, LineSegment>());
				vertical.get(length).get(x).put(y, l);
			} else {
				if (!horizontal.containsKey(length))
					horizontal.put(length, new TreeMap<Integer, TreeMap<Integer, LineSegment>>());
				if (!horizontal.get(length).containsKey(x))
					horizontal.get(length).put(x, new TreeMap<Integer, LineSegment>());
				horizontal.get(length).get(x).put(y, l);
			}

			minX = (int) Math.round(Math.min(minX, l.getStart().getX()));
			minY = (int) Math.round(Math.min(minY, l.getStart().getY()));
			maxX = (int) Math.round(Math.max(maxX, l.getEnd().getX()));
			maxY = (int) Math.round(Math.max(maxY, l.getEnd().getY()));

		}

		Point min = new Point(minX, minY);
		Point max = new Point(maxX, maxY);

		List<Double> squares = new SquarePie(vertical, horizontal, min, max).squares();

		for (int i = 0; i < squares.size(); i++)
			io.println(squares.get(i));

		io.flush();
		io.close();
	}
}
