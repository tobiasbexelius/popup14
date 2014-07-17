import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a simple polygon represented by a List<Point> representing
 * its vertices. Supported operations are calculation of the area, determining
 * whether the orientations of the vertices are clockwise or counter-clockwise, and
 * determining whether a given point is inside, on, or outside this polygon.
 * 
 * @author Tobias Andersson, Jonas Haglund, Carl Frendin
 * 
 */
public class Polygon {

	private static final double EPSILON = Math.pow(10, -7);
	private List<LineSegment> segments;
	private List<Point> vertices;
	private String orientation;
	private double area;

	public List<LineSegment> getSegments() {
		return segments;
	}

	public List<Point> getVertices() {
		return vertices;
	}

	/**
	 * Constructs a new polygon with the given vertices. The vertices must form a
	 * simple polygon and must be given in either clockwise or counter-clockwise
	 * order.
	 * 
	 * @param vertices
	 *            the vertices of this polygon
	 */
	public Polygon(List<Point> vertices) {
		this.vertices = vertices;
		this.segments = new ArrayList<LineSegment>(vertices.size());

		Point first = vertices.get(0);
		Point current = first;
		for (int i = 1; i < vertices.size(); i++) {
			Point next = vertices.get(i);
			segments.add(new LineSegment(current, next));
			current = next;
		}
		segments.add(new LineSegment(current, first));
		vertices.add(first);
	}

	/**
	 * Returns the area of this polygon.
	 * 
	 * @return the area of this polygon
	 */
	public double area() {
		if (area != 0)
			return area;

		Point current = vertices.get(0);
		for (int i = 1; i < vertices.size(); i++) {
			Point next = vertices.get(i);
			area += current.crossProduct(next);
			current = next;
		}

		orientation = area > 0 ? "CCW" : "CW";
		area = Math.abs(area / 2);

		return area;
	}

	/**
	 * Returns the orientation of the vertices of this polygon.
	 * 
	 * @return "CCW" or "CW" as the orientation of this polygon is counter-clockwise
	 *         or clockwise, respectively.
	 */
	public String orientation() {
		if (orientation == null)
			area();
		return orientation;
	}

	/**
	 * Returns -1, 0 or 1 as p is outside, on or inside this polygon.
	 * 
	 * @param p
	 *            the point to evaluate
	 * @return -1, 0 or 1 as p is outisde, on or inside this polygon.
	 */
	public int insidePoly(Point p) {

		if (vertices.contains(p))
			return 0;

		double angleSum = 0;
		Point u = vertices.get(0);
		for (int i = 1; i < vertices.size(); i++) {
			Point v = vertices.get(i);
			Point pu = u.subtract(p);
			Point pv = v.subtract(p);
			double angle = Math.atan2(pu.crossProduct(pv), pu.dotProduct(pv));

			if (Math.abs(Math.abs(angle) - Math.PI) < EPSILON)
				return 0;

			angleSum += angle;
			u = v;
		}

		if (Math.abs(angleSum) > Math.PI)
			return 1;
		return -1;
	}

	@Override
	public String toString() {
		return "Polygon [" + vertices + "]";
	}
}
