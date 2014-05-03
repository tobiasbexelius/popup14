import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		while (io.hasMoreTokens()) {
			int n = io.getInt();
			int m = io.getInt();

			if (factovisors(n, m))
				io.println(m + " divides " + n + "!");
			else
				io.println(m + " does not divide " + n + "!");
		}
		io.flush();
		io.close();
	}

	private static boolean factovisors(int n, int m) {
		if (m == 0)
			return false;

		if (n < 2)
			return m == 1;

		if (n >= m)
			return true;

		List<BigInteger> mFactorList = PollardRho.factorize(BigInteger.valueOf(m));

		Map<BigInteger, Integer> mFactorization = new HashMap<>();
		for (int i = 0; i < mFactorList.size(); i++) {
			BigInteger factor = mFactorList.get(i);
			if (!mFactorization.containsKey(factor))
				mFactorization.put(factor, 1);
			else
				mFactorization.put(factor, mFactorization.get(factor) + 1);
		}

		for (BigInteger factor : mFactorization.keySet()) {
			if (legendresPrimePower(n, factor.intValue()) < mFactorization.get(factor))
				return false;
		}

		return true;
	}

	private static int legendresPrimePower(int n, int p) {
		int res = 0;
		for (int i = p; i <= n; i *= p) {
			res += n / i;
		}
		return res;
	}
}
