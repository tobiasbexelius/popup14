import java.util.Arrays;

public class Main {

	private static final int[] dx = new int[] { -1, 0, -1, 1, 1, 0 };
	private static final int[] dy = new int[] { -1, -1, 0, 0, 1, 1 };
	private static final int MAX_STEPS = 14;
	private static int[][][] walks;

	public static void main(String[] args) {

		walks = new int[MAX_STEPS + 1][MAX_STEPS + 1][MAX_STEPS + 1];
		for (int i = 0; i < MAX_STEPS + 1; i++)
			for (int j = 0; j < MAX_STEPS + 1; j++)
				Arrays.fill(walks[i][j], -1);

		Kattio io = new Kattio(System.in);
		io.getInt();
		while (io.hasMoreTokens()) {
			int n = io.getInt();
			io.println(calcWalks(0, 0, n));
			io.flush();
		}
		io.close();
	}

	private static int calcWalks(int x, int y, int n) {
		if (n == 0)
			return (x == 0 && y == 0) ? 1 : 0;
		int w = getWalks(x, y, n);
		if (w != -1)
			return w;
		w = 0;
		for (int i = 0; i < dx.length; i++) {
			if (isInBounds(x + dx[i], y + dy[i])) {
				w += calcWalks(x + dx[i], y + dy[i], n - 1);
			}
		}

		setWalks(x, y, n, w);
		return w;
	}

	private static int getWalks(int x, int y, int n) {
		return walks[x + MAX_STEPS / 2][y + MAX_STEPS / 2][n];
	}

	private static void setWalks(int x, int y, int n, int v) {
		walks[x + MAX_STEPS / 2][y + MAX_STEPS / 2][n] = v;
	}

	private static boolean isInBounds(int x, int y) {
		return x >= -(MAX_STEPS / 2) && x <= (MAX_STEPS / 2) && y >= -(MAX_STEPS / 2) && y <= (MAX_STEPS / 2);
	}
}
