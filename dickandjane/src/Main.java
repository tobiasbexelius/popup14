public class Main {
	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		while (io.hasMoreTokens()) {
			int s = io.getInt();
			int p = io.getInt();
			int y = io.getInt();
			int j = io.getInt();

			io.println(dickAndJane(s, p, y, j));

		}
		io.flush();
		io.close();
	}

	/**
	 * S + P + Y = 12 + j S = Y + y P = Y + p
	 * 
	 * @param s
	 * @param p
	 * @param y
	 * @param j
	 * @return
	 */
	private static String dickAndJane(int s, int p, int y, int j) {
		int Y = (12 + j - y - p) / 3;
		int S = Y + y;
		int P = Y + p;

		if (S + P + Y - 12 - j == 0)
			return S + " " + P + " " + Y;

		if (S + P + Y - 12 - j == -2)
			return (S + 1) + " " + (P + 1) + " " + Y;

		if (y > s + p)
			P++;
		else
			S++;

		return S + " " + P + " " + Y;

	}
}
