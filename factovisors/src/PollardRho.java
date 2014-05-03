import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class PollardRho {

	private static final int CERTAINTY = 10;

	public static List<BigInteger> factorize(BigInteger n) {

		List<BigInteger> factors = new ArrayList<BigInteger>();
		if (n.isProbablePrime(CERTAINTY)) {
			factors.add(n);
			return factors;
		}

		Queue<BigInteger> queue = new LinkedList<BigInteger>();
		queue.add(n);

		boolean possiblePow = true;

		while (!queue.isEmpty()) {
			BigInteger current = queue.poll();
			BigInteger factor = null;

			if (possiblePow && !factors.isEmpty()) {
				factor = tryPow(current, factors.get(0));
				if (factor == null) {
					possiblePow = false;
				}
			}

			if (factor == null) {
				factor = trialDivision(current);
			}

			if (factor != null) {
				factors.add(factor);
			} else {

				factor = brentPollardRho(current);

				if (factor == null) {
					return null;
				}

				if (factor.isProbablePrime(CERTAINTY))
					factors.add(factor);
				else
					queue.add(factor);
			}

			if (current.divide(factor).isProbablePrime(CERTAINTY)) {
				factors.add(current.divide(factor));
			} else {
				queue.add(current.divide(factor));
			}
		}
		return factors;
	}

	private static BigInteger brentPollardRho(BigInteger n) {
		BigInteger ys = BigInteger.ZERO;
		BigInteger x = BigInteger.ZERO;

		BigInteger y = randomBigInteger(n);
		BigInteger c = randomBigInteger(n);
		BigInteger m = randomBigInteger(n);

		BigInteger g = BigInteger.ONE;
		BigInteger r = BigInteger.ONE;
		BigInteger q = BigInteger.ONE;

		while (g.compareTo(BigInteger.ONE) <= 0) {
			x = y;

			for (int i = 0; i < r.intValue(); i++) {
				y = func(y, c, n);
			}

			BigInteger k = BigInteger.ZERO;

			while (k.compareTo(r) < 0 && g.compareTo(BigInteger.ONE) <= 0) {
				ys = y;
				for (int i = 0; i < m.min(r.subtract(k)).intValue(); i++) {
					y = func(y, c, n);
					q = q.multiply(x.subtract(y).abs()).mod(n);
				}
				g = q.gcd(n);
				k = k.add(m);
			}

			r = r.multiply(BigInteger.valueOf(2));
		}
		if (g.equals(n)) {
			while (true) {
				ys = func(ys, c, n);
				g = n.gcd(x.subtract(ys).abs());
				if (g.compareTo(BigInteger.ONE) > 0)
					break;
			}
		}
		if (g.equals(n)) {
			return null;
		} else {
			return g;
		}
	}

	private BigInteger pollardRho(BigInteger n) {
		BigInteger res = trialDivision(n);
		if (res != null) {
			return res;
		}
		BigInteger x = BigInteger.valueOf(2);
		BigInteger y = BigInteger.valueOf(2);
		BigInteger g = BigInteger.ONE;
		while (g.equals(BigInteger.ONE)) {
			x = f(x, n);
			y = f(f(y, n), n);
			g = n.gcd(x.subtract(y));
		}
		if (g.equals(n)) {
			return null;
		} else {
			return g;
		}
	}

	public static BigInteger randomBigInteger(BigInteger n) {
		Random rand = new Random();
		BigInteger result = new BigInteger(n.bitLength(), rand);
		while (result.compareTo(n) > 0) {
			result = new BigInteger(n.bitLength(), rand);
		}
		return result;
	}

	private static BigInteger trialDivision(BigInteger n) {
		for (int p : Constants.PRIMES) {
			if (n.compareTo(BigInteger.valueOf(p)) < 0) {
				return null;
			}
			if (n.mod(BigInteger.valueOf(p)).equals(BigInteger.ZERO)) {
				return (BigInteger.valueOf(p));
			}
		}
		return null;
	}

	private static BigInteger tryPow(BigInteger current, BigInteger factor) {
		if (current.mod(factor).equals(BigInteger.ZERO)) {
			return factor;
		}
		return null;
	}

	private BigInteger f(BigInteger x, BigInteger n) {
		return x.pow(2).subtract(BigInteger.ONE).mod(n);
	}

	private static BigInteger func(BigInteger y, BigInteger c, BigInteger n) {
		return y.pow(2).mod(n).add(c).mod(n);
	}

	public static void main(String[] args) {
		System.out.println(new PollardRho().pollardRho(BigInteger.valueOf(9)));
	}
}