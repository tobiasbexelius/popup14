/**
 * 
 * @author Tobias Andersson, Jonas Haglund, Carl Frendin
 * 
 */
public class LineSegment implements Comparable<LineSegment> {

	private static final double EPSILON = Math.pow(10, -10);
	private final Point start;
	private final Point end;
	private final double x1;
	private final double y1;
	private final double x2;
	private final double y2;

	public LineSegment(Point start, Point end) {
		if (start.compareTo(end) > 0) {
			Point tmp = start;
			start = end;
			end = tmp;
		}
		this.start = start;
		this.end = end;
		this.x1 = start.getX();
		this.y1 = start.getY();
		this.x2 = end.getX();
		this.y2 = end.getY();

	}

	public double distanceTo(LineSegment line) {
		if (intersection(line) != null)
			return 0;

		double min = distanceTo(line.getStart());
		min = Math.min(min, distanceTo(line.getEnd()));
		min = Math.min(min, line.distanceTo(start));
		min = Math.min(min, line.distanceTo(end));

		return min;
	}

	public double distanceTo(Point point) {
		double x3 = point.getX();
		double y3 = point.getY();

		double u = ((x3 - x1) * (x2 - x1) + (y3 - y1) * (y2 - y1)) / (Math.pow(start.subtract(end).magnitude(), 2));

		if (u < 0 || u > 1 || Double.isNaN(u)) {
			return Math.min(point.distanceTo(start), point.distanceTo(end));
		}

		double x = x1 + u * (x2 - x1);
		double y = y1 + u * (y2 - y1);

		return point.distanceTo(new Point(x, y));
	}

	public LineSegment intersection(LineSegment line) {
		Point p1 = start, p2 = end;
		Point p3 = line.getStart(), p4 = line.getEnd();

		if (isPoint() || line.isPoint()) {

			if (isPoint() && line.isPoint()) {
				return p1.equals(p3) ? new LineSegment(p1, p1) : null;
			}

			if (isPoint())
				return line.isOnSegment(p1) ? new LineSegment(p1, p1) : null;

			return isOnSegment(p3) ? new LineSegment(p3, p3) : null;

		}

		if (equals(line))
			return line;

		if (compareTo(line) > 0) {
			Point tmp1 = p1;
			Point tmp2 = p2;
			p1 = p3;
			p2 = p4;
			p3 = tmp1;
			p4 = tmp2;
		}

		// the line segment p1, p2 is strictly less than the line segment p3, p4 at
		// this point

		double x1 = p1.getX(), y1 = p1.getY();
		double x2 = p2.getX(), y2 = p2.getY();
		double x3 = p3.getX(), y3 = p3.getY();
		double x4 = p4.getX(), y4 = p4.getY();

		double n1 = (x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3);
		double n2 = (x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3);
		double d = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
		double u1 = n1 / d, u2 = n2 / d;

		if (!almostEquals(d, 0) && u1 >= 0 && u2 >= 0 && u1 <= 1 && u2 <= 1) {
			double x = x1 + u1 * (x2 - x1);
			double y = y1 + u1 * (y2 - y1);
			Point intersection = new Point(x, y);
			return new LineSegment(intersection, intersection);
		} else if (almostEquals(d, 0) && almostEquals(n1, 0) && almostEquals(n2, 0)) {

			if (p2.equals(p3))
				return new LineSegment(p2, p2);
			else if (p1.equals(p3))
				return new LineSegment(p1, p2);
			else if (p2.compareTo(p4) > 0)
				return new LineSegment(p3, p4);
			else if (p2.compareTo(p3) > 0)
				return new LineSegment(p3, p2);
		} else {

			if (p1.equals(p3) || p1.equals(p4))
				return new LineSegment(p1, p1);

			if (p2.equals(p3) || p2.equals(p4))
				return new LineSegment(p2, p2);
		}

		return null;
	}

	public boolean isOnSegment(Point point) {
		if (isPoint())
			return start.equals(point);
		double x3 = point.getX();
		double y3 = point.getY();
		boolean result = false;
		if (x1 != x2) {
			if (y1 != y2) {
				double t1 = (x3 - x1) / (x2 - x1);
				double t2 = (y3 - y1) / (y2 - y1);
				if (almostEquals(t1, t2) && t1 >= 0 && t1 <= 1)
					result = true;
			} else if (y1 == y3 && x1 <= x3 && x3 <= x2) // horizontal line
				result = true;
		} else if (y1 != y2) { // vertical line
			double t1 = (y3 - y1) / (y2 - y1);
			if (t1 >= 0 && t1 <= 1)
				result = true;
		}

		return result;
	}

	private boolean almostEquals(double a, double b) {
		return Math.abs(a - b) < EPSILON;
	}

	public boolean isPoint() {
		return start.equals(end);
	}

	public Point getStart() {
		return start;
	}

	public Point getEnd() {
		return end;
	}

	@Override
	public int compareTo(LineSegment line) {
		if (equals(line))
			return 0;
		int cmp = start.compareTo(line.getStart());
		if (cmp != 0)
			return cmp;
		return end.compareTo(line.getEnd());

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
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
		LineSegment other = (LineSegment) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[start=" + start + ", end=" + end + "]";
	}

	public boolean isVertical() {
		return x1 == x2;
	}

	public boolean isHorizontal() {
		return y1 == y2;
	}

	public double length() {
		return start.distanceTo(end);
	}

}
