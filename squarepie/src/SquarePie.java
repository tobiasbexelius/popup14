import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SquarePie {

	private final Map<Integer, TreeMap<Integer, TreeMap<Integer, LineSegment>>> vertical;
	private final Map<Integer, TreeMap<Integer, TreeMap<Integer, LineSegment>>> horizontal;
	private final double totalArea;
	private final Point lowerLeft;
	private final Point lowerRight;
	private final Point upperLeft;
	private final Point upperRight;
	private List<Double> squares;

	public SquarePie(Map<Integer, TreeMap<Integer, TreeMap<Integer, LineSegment>>> vertical, Map<Integer, TreeMap<Integer, TreeMap<Integer, LineSegment>>> horizontal, Point min, Point max) {
		this.vertical = vertical;
		this.horizontal = horizontal;
		this.lowerLeft = min;
		this.upperRight = max;
		this.lowerRight = new Point(max.getX(), min.getY());
		this.upperLeft = new Point(min.getX(), max.getY());
		this.totalArea = (max.getX() - min.getX()) * (max.getY() - min.getY());
	}

	public List<Double> squares() {
		this.squares = new ArrayList<Double>();
		findSquares(lowerLeft, lowerRight, upperLeft, upperRight);
		Collections.sort(squares, Collections.reverseOrder());
		return squares;
	}

	private void findSquares(Point ll, Point lr, Point ul, Point ur) {
		removeLines(ll, lr, ul, ur);

		int length = (int) Math.round(ll.distanceTo(ul));
		LineSegment verticalDivider = findDivider(vertical.get(length), ll, lr, ul, ur);

		if (verticalDivider != null) {
			findSquares(ll, verticalDivider.getStart(), ul, verticalDivider.getEnd());
			findSquares(verticalDivider.getStart(), lr, verticalDivider.getEnd(), ur);

		} else {
			length = (int) Math.round(ll.distanceTo(lr));
			LineSegment horizontalDivider = findDivider(horizontal.get(length), ll, lr, ul, ur);

			if (horizontalDivider != null) {
				findSquares(ll, lr, horizontalDivider.getStart(), horizontalDivider.getEnd());
				findSquares(horizontalDivider.getStart(), horizontalDivider.getEnd(), ul, ur);

			} else {
				squares.add(getFraction(ll, lr, ul, ur));
			}
		}
	}

	private void removeLines(Point ll, Point lr, Point ul, Point ur) {

		LineSegment l = new LineSegment(ll, ul);
		LineSegment r = new LineSegment(lr, ur);
		LineSegment d = new LineSegment(ll, lr);
		LineSegment u = new LineSegment(ul, ur);
		int lLen = (int) l.length();
		int rLen = (int) r.length();
		int dLen = (int) d.length();
		int uLen = (int) u.length();
		if (vertical.containsKey(lLen) && vertical.get(lLen).containsKey((int) ll.getX()))
			vertical.get(lLen).get((int) ll.getX()).remove((int) ll.getY());
		if (horizontal.containsKey(dLen) && horizontal.get(dLen).containsKey((int) ll.getX()))
			horizontal.get(dLen).get((int) ll.getX()).remove((int) ll.getY());
		if (vertical.containsKey(rLen) && vertical.get(rLen).containsKey((int) lr.getX()))
			vertical.get(rLen).get((int) lr.getX()).remove((int) lr.getY());
		if (horizontal.containsKey(uLen) && horizontal.get(uLen).containsKey((int) ul.getX()))
			horizontal.get(uLen).get((int) ul.getX()).remove((int) ul.getY());
	}

	private LineSegment findDivider(TreeMap<Integer, TreeMap<Integer, LineSegment>> segments, Point ll, Point lr, Point ul, Point ur) {

		if (segments == null)
			return null;

		int s1 = (int) ((ll.getX() + ur.getX()) / 2);
		int e1 = (int) (s1 + ur.getX()) / 2;
		Iterator<Integer> validXs = segments.subMap(s1, e1 + 1).keySet().iterator();
		while (validXs.hasNext()) {
			int x = validXs.next();
			Iterator<Integer> validYs = segments.get(x).subMap((int) ll.getY(), (int) ur.getY() + 1).keySet().iterator();
			while (validYs.hasNext()) {
				int y = validYs.next();
				LineSegment line = segments.get(x).get(y);
				if (line.getEnd().getX() <= ur.getX() && line.getEnd().getY() <= ur.getY()) {
					return line;
				}
			}
		}

		int s2 = (int) (ll.getX() + s1) / 2;
		int e2 = s1;
		validXs = segments.subMap(s2, e2 + 1).keySet().iterator();
		while (validXs.hasNext()) {
			int x = validXs.next();
			Iterator<Integer> validYs = segments.get(x).subMap((int) ll.getY(), (int) ur.getY() + 1).keySet().iterator();
			while (validYs.hasNext()) {
				int y = validYs.next();
				LineSegment line = segments.get(x).get(y);
				if (line.getEnd().getX() <= ur.getX() && line.getEnd().getY() <= ur.getY()) {
					return line;
				}
			}
		}

		int s3 = e1;
		int e3 = (int) ur.getX() + 1;

		validXs = segments.subMap(s3, e3).keySet().iterator();
		while (validXs.hasNext()) {
			int x = validXs.next();
			Iterator<Integer> validYs = segments.get(x).subMap((int) ll.getY(), (int) ur.getY() + 1).keySet().iterator();
			while (validYs.hasNext()) {
				int y = validYs.next();
				LineSegment line = segments.get(x).get(y);
				if (line.getEnd().getX() <= ur.getX() && line.getEnd().getY() <= ur.getY()) {
					return line;
				}
			}
		}

		validXs = segments.subMap((int) ll.getX(), s2 + 1).keySet().iterator();
		while (validXs.hasNext()) {
			int x = validXs.next();
			Iterator<Integer> validYs = segments.get(x).subMap((int) ll.getY(), (int) ur.getY() + 1).keySet().iterator();
			while (validYs.hasNext()) {
				int y = validYs.next();
				LineSegment line = segments.get(x).get(y);
				if (line.getEnd().getX() <= ur.getX() && line.getEnd().getY() <= ur.getY()) {
					return line;
				}
			}
		}
		return null;
	}

	private double getFraction(Point ll, Point lr, Point ul, Point ur) {
		return (lr.getX() - ll.getX()) * (ul.getY() - ll.getY()) / totalArea;
	}

}
