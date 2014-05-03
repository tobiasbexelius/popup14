public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		while (io.hasMoreTokens()) {
			int n = io.getInt();
			if (n == 0)
				break;
			io.println(perfectPowers(n));
		}
		io.flush();
		io.close();
	}

	private static int perfectPowers(int n) {
		int res = Integer.MIN_VALUE;
		for (int i = 32; i >= 1; i--) {
			if (n < 0 && i % 2 == 0)
				continue;

			double preX = 1;
			double constant = Math.abs(n);
			double exp = i;
			double delta = Double.POSITIVE_INFINITY;
			double MIN_DELTA = 0.00001;
			while (delta > MIN_DELTA) {
				double f = Math.pow(preX, exp) - constant;
				double fPrim = exp * Math.pow(preX, exp - 1);
				double nextX = preX - (f / fPrim);
				delta = Math.abs(nextX - preX);
				preX = nextX;
			}

			if (Math.pow(Math.ceil(preX), i) == Math.abs(n) || Math.pow(Math.floor(preX), i) == Math.abs(n)) {
				res = i;
				break;
			}
		}

		return res;
	}
}
