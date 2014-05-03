public class Main {

	private static final int X = 0;
	private static final int Y = 1;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		io.getInt();
		while (io.hasMoreTokens()) {
			int nInner = io.getInt();
			int[][] inner = new int[nInner][2];
			for (int i = 0; i < nInner; i++) {
				inner[i][X] = io.getInt();
				inner[i][Y] = io.getInt();
			}

			int nOuter = io.getInt();
			int[][] outer = new int[nOuter][2];
			for (int i = 0; i < nOuter; i++) {
				outer[i][X] = io.getInt();
				outer[i][Y] = io.getInt();
			}

			io.println((shortestDistance(inner, outer) / 2));

		}
		io.flush();
		io.close();

	}

	private static double shortestDistance(int[][] inner, int[][] outer) {
		int nInner = inner.length;
		int nOuter = outer.length;

		double minDistance = Double.POSITIVE_INFINITY;

		for (int i = 0; i < nInner; i++) {
			for (int j = 0; j < nOuter; j++) {
				double distance = getDistance(inner[i][X], inner[i][Y], outer[j][X], outer[j][Y]);
				if (distance < minDistance)
					minDistance = distance;
			}
		}

		return minDistance;
	}

	private static double getDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt(Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2));
	}
}
