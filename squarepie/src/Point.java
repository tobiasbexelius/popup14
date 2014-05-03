public class Point implements Comparable<Point> {

	private final int x;
	private final int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
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
		Point other = (Point) obj;
		if (x != other.getX())
			return false;
		if (y != other.getY())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[x=" + x + ", y=" + y + "]";
	}

	@Override
	public int compareTo(Point p) {

		if (y < p.getY())
			return -1;

		if (y > p.getY())
			return 1;

		if (x < p.getX())
			return -1;

		if (x > p.getX())
			return 1;

		return 0;
	}
}