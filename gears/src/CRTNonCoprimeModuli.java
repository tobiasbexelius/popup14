/*
 * Authors: Tobias Andersson, Carl Frendin, Jonas Haglund
 * 
 * Solves a system of congruence equations where the moduli are not necessarily co-prime.
 */

public class CRTNonCoprimeModuli {

	public static long solveMultipleCongruences(long[] a, long[] n) {
		long a1 = a[0];
		long n1 = n[0];
		long a2 = -1, n2 = -1;
		for (int i = 1; i < a.length - 1; i++) {
			a2 = a[i];
			n2 = n[i];

			long d = gcd(n1, n2);

			if (a1 > a2) {
				long a1Temp = a1, n1Temp = n1;
				a1 = a2;
				n1 = n2;
				a2 = a1Temp;
				n2 = n1Temp;
			}

			if ((a2 - a1) % d != 0) {
				return -1;
			}

			long n1Prime = n1 / d, n2Prime = n2 / d;
			long aBis = (a2 - a1) / d;

			long xBis = ChineseRemainder.solveCongruences(0, n1Prime, aBis, n2Prime);
			a1 = d * xBis + a1;
			n1 = lcm(n1, n2);

		}
		a2 = a[a.length - 1];
		n2 = n[n.length - 1];

		return solveCongruences(a1, n1, a2, n2);
	}

	// If x ≡ a1 mod n1 has a solution then x is output, otherwise -1 is returned.
	// x ≡ a2 mod n2
	//
	// All input parameters must be at most 31 bits long
	public static long solveCongruences(long a1, long n1, long a2, long n2) {
		// Finds the greatest common divisor to n1 and n2.
		long d = gcd(n1, n2);

		// Rewrites the variables such that a2 >= a1.
		if (a1 > a2) {
			long a1Temp = a1, n1Temp = n1;
			a1 = a2;
			n1 = n2;
			a2 = a1Temp;
			n2 = n1Temp;
		}

		// If d ∤ (a2 - a1) then the system has no solution.
		// Source: 'http://math.stackexchange.com/questions/305745/
		// how-to-solve-for-multiple-congruences-what-arent-relatively-prime'
		if ((a2 - a1) % d != 0)
			return -1;

		// If n1 and n2 are coprime, then the system can be solved with the ordinary
		// chinese remainder theorem without modifications.
		if (d == 1)
			return ChineseRemainder.solveCongruences(a1, n1, a2, n2);

		// Rewrites the equations such that the moduli are coprime.
		long n1Prime = n1 / d, n2Prime = n2 / d;
		long aBis = (a2 - a1) / d;

		// No we have information to rewrite the system of congruence equations
		// in such a form that we can retrieve x'' from the classical Chinese
		// Remainder
		// Theorem.
		long xBis = ChineseRemainder.solveCongruences(0, n1Prime, aBis, n2Prime);

		// Computes x that is the potential solution to our system.
		long x = d * xBis + a1;

		// Checks that x is within K and indeed a solution to the original system of
		// equations.
		long K = n1 * n2 / d;

		if (x < K)
			return x;

		// x was greater than or equal to K and is therefore reduced.
		x = x % K;

		// Checks if x is still a solution.
		if (ChineseRemainder.minus(x, a1, K) == 0 && ChineseRemainder.minus(x, a2, K) == 0)
			return x;

		return -1;
	}

	public static long gcd(long a, long b) {
		long t;

		while (b != 0) {
			t = b;
			b = a % b;
			a = t;
		}

		return a;
	}

	public static long lcm(long n, long m) {
		return n * m / gcd(n, m);
	}
}
