import java.text.DecimalFormat;

public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);

		DecimalFormat df = new DecimalFormat("#.###");
		df.setMinimumFractionDigits(3);
		df.setMaximumFractionDigits(3);

		while (io.hasMoreTokens()) {
			int n = io.getInt();
			double t = io.getDouble();
			if (n == 0)
				break;
			double expectedPrize = getExpectedPrize(n, t, 0);
			io.println(df.format(expectedPrize));
		}
		io.flush();
		io.close();
	}

	private static double getExpectedPrize(int n, double t, int q) {

		double leave = Math.pow(2, q);

		if (q == n)
			return leave;

		double answer = getExpectedPrize(n, t, q + 1);

		double breakPoint = leave / answer;

		if (breakPoint < t)
			return (t + 1) / 2 * answer;

		double answerChance = ((1 - breakPoint) / (1 - t));
		double answerPrecision = (breakPoint + 1) / 2;

		return answerChance * answerPrecision * answer + (1 - answerChance) * leave;
	}
}
