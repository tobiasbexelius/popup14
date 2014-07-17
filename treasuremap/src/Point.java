/**
 * 
 * This class represents a two-dimensional vector, and supports some common vector
 * operations. Coordinates are represented by doubles, therefore an epsilon is used
 * when comparing coordinates. Epsilon is 10^-7 by default, but can be specified by
 * using the appropriate constructor. Two points are considered equal if neither
 * their x nor y coordinates differ by more than epsilon.
 * 
 * @author Tobias Andersson, Jonas Haglund, Carl Frendin
 * 
 */
public class Point implements Comparable<Point> {

	private final double epsilon;
	private final double x;
	private final double y;

	/**
	 * Constructs a Point with the given x and y coordinates.
	 * 
	 * @param x
	 *            the x coorinate of this point
	 * @param y
	 *            the y coordinate of this point
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
		epsilon = Math.pow(10, -7);
	}

	/**
	 * Constructs a Point with the given x and y coordinates and the given epsilion
	 * as tolerance value when comparing points.
	 * 
	 * @param x
	 *            the x coorinate of this point
	 * @param y
	 *            the y coordinate of this point
	 * @param epsilon
	 *            the tolerance value when comparing points
	 */
	public Point(double x, double y, double epsilon) {
		this.x = x;
		this.y = y;
		this.epsilon = epsilon;
	}

	/**
	 * Returns the x coordinate of this point.
	 * 
	 * @return the x coordinate of this point.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns the y coordinate of this point.
	 * 
	 * @return the y coordinate of this point.
	 */
	public double getY() {
		return y;
	}

	/**
	 * Returns a point that represents the result of component-wise addition between
	 * this point and p.
	 * 
	 * @param p
	 *            the point to add with this point
	 * @return a new point that represents the result of the addition.
	 */
	public Point add(Point p) {
		return new Point(p.getX() + x, p.getY() + y);
	}

	/**
	 * Returns a point that represents the result of component-wise subtraction
	 * between this point and p, that is: (this - p).
	 * 
	 * @param p
	 *            the point to subtract from this point
	 * @return a new point that represents the result of the subtraction.
	 */
	public Point subtract(Point p) {
		return new Point(p.getX() - x, p.getY() - y);
	}

	/**
	 * Returns a point that represents the result of component-wise multiplication of
	 * this point and the given scalar.
	 * 
	 * @param scalar
	 *            the scalar to multiply this point with
	 * @return a new Point that represents the result of the multiplication
	 */
	public Point scalarMultiply(double scalar) {
		return new Point(scalar * x, scalar * y);
	}

	/**
	 * Returns a point that represents the result of component-wise division of this
	 * point by the given scalar.
	 * 
	 * @param scalar
	 *            the scalar to divide this point with
	 * @return a new Point that represents the result of the division
	 */
	public Point scalarDivide(double scalar) {
		return new Point(x / scalar, y / scalar);
	}

	/**
	 * Returns the dot product of this point and p.
	 * 
	 * @param p
	 *            the point which this point is multiplied with.
	 * @return the dot product, (this . p)
	 */
	public double dotProduct(Point p) {
		return x * p.getX() + y * p.getY();
	}

	/**
	 * Returns the cross product of this point and p, that is: this x p. The result
	 * is the z-component in (0, 0, this x p) if the other two points are considered
	 * in the x-y plane.
	 * 
	 * @param p
	 *            the point to calculate the cross-product with
	 * @return the cross product, (this x p)
	 */
	public double crossProduct(Point p) {
		return x * p.getY() - p.getX() * y;
	}

	/**
	 * Returns the euclidean distance between this point and p.
	 * 
	 * @param p
	 * @return the euclidean distance between this and p
	 */
	public double distanceTo(Point p) {
		return Math.sqrt(Math.pow(p.getX() - x, 2) + Math.pow(p.getY() - y, 2));
	}

	/**
	 * Returns the magnitude of this point, that is the distance from this point to
	 * origo.
	 * 
	 * @return the distance between this point and (0,0)
	 */
	public double magnitude() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	/**
	 * Return the angle between this point and p in radians.
	 * 
	 * @param p
	 * @return the angle between this and p
	 */
	public double angleTo(Point p) {
		return Math.acos(dotProduct(p) / (this.magnitude() * p.magnitude()));
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (Math.abs(x - other.getX()) > epsilon || Math.abs(y - other.getY()) > epsilon)
			return false;
		return true;
	}

	@Override
	public int compareTo(Point p) {
		if (equals(p))
			return 0;
		if (this.x < p.getX() && Math.abs(x - p.getX()) >= epsilon)
			return -1;
		if (this.x > p.getX() && Math.abs(x - p.getX()) >= epsilon)
			return 1;
		if (this.y < p.getY() && Math.abs(y - p.getY()) >= epsilon)
			return -1;
		return 1;
	}

}
