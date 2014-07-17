public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		int n = io.getInt();

		int fives = legendresPrimePower(n, 5);

		int twos = 0;
		long res = 1;
		for (int i = 1; i <= n; i++) {
			int factor = i;
			while (twos < fives && factor % 2 == 0) {
				factor /= 2;
				twos++;
			}

			while (factor % 5 == 0)
				factor /= 5;

			res = (res * factor) % 1000;
		}

		int nFacDigits = getNFacDigits(n);

		if (Long.toString(res).length() < 3 && nFacDigits - fives >= 3) {
			if (Long.toString(res).length() == 1)
				io.println("00" + res);
			else
				io.println("0" + res);
		} else {
			io.println(res);
		}

		io.flush();
		io.close();
	}

	private static int getNFacDigits(int n) {
		double logSum = 0;
		for (int i = 1; i <= n; i++) {
			logSum += Math.log(i) / Math.log(10);
		}
		return (int) (Math.floor(logSum) + 1);
	}

	private static int legendresPrimePower(int n, int p) {
		int res = 0;
		for (int i = p; i <= n; i *= p) {
			res += n / i;
		}
		return res;
	}

}
