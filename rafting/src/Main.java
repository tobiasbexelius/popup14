import java.util.ArrayList;
import java.util.List;

public class Main {

	private static final int X = 0;
	private static final int Y = 1;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		io.getInt();
		while (io.hasMoreTokens()) {
			int nInner = io.getInt();
			List<LineSegment> inner = new ArrayList<>();

			Point first = new Point(io.getInt(), io.getInt());
			Point current = first;
			Point next;
			for (int i = 1; i < nInner; i++) {
				next = new Point(io.getInt(), io.getInt());
				inner.add(new LineSegment(current, next));
				current = next;
			}
			inner.add(new LineSegment(current, first));

			int nOuter = io.getInt();
			List<LineSegment> outer = new ArrayList<>();
			first = new Point(io.getInt(), io.getInt());
			current = first;
			for (int i = 1; i < nOuter; i++) {
				next = new Point(io.getInt(), io.getInt());
				outer.add(new LineSegment(current, next));
				current = next;
			}
			outer.add(new LineSegment(current, first));

			io.println((shortestDistance(inner, outer) / 2));

		}
		io.flush();
		io.close();

	}

	private static double shortestDistance(List<LineSegment> inner, List<LineSegment> outer) {
		double min = Double.POSITIVE_INFINITY;

		for (int i = 0; i < inner.size(); i++) {
			LineSegment s1 = inner.get(i);
			for (int j = 0; j < outer.size(); j++) {
				LineSegment s2 = outer.get(j);
				double dist = s1.distanceTo(s2);
				if (dist < min)
					min = dist;
			}
		}

		return min;
	}
}
