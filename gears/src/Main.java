public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		io.getInt();
		while (io.hasMoreTokens()) {
			int n = io.getInt();
			long[] a = new long[n];
			long[] b = new long[n];
			for (int i = 0; i < n; i++) {
				a[i] = io.getLong();
				b[i] = io.getLong();
			}
			long t = timeUntilDesiredConfiguration(n, a, b);
			if (t == -1)
				io.println("Impossible");
			else
				io.println(t);
		}
		io.flush();
		io.close();
	}

	private static long timeUntilDesiredConfiguration(int n, long[] a, long[] b) {

		if (n == 1)
			return b[0];

		for (int i = 0; i < n; i++) {
			if (i % 2 != 0)
				b[i] = a[i] - b[i];
		}

		return CRTNonCoprimeModuli.solveMultipleCongruences(b, a);
	}

}
