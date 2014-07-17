import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);

		Map<Integer, Integer> wordSize = new HashMap<>();

		for (int decade = 1960, exp = 4; decade <= 2160; decade += 10, exp *= 2)
			wordSize.put(decade, exp);

		while (io.hasMoreTokens()) {
			int year = io.getInt();
			if (year == 0)
				break;

			int decade = (year / 10) * 10;
			double exp = wordSize.get(decade) * Math.log(2);
			double logNFactorial = 0;
			int n;
			for (n = 1; logNFactorial < exp; n++) {
				logNFactorial += Math.log(n);
			}
			io.println(n - 2);

		}
		io.flush();
		io.close();
	}
}
