/*
 * Authors: Tobias Andersson, Carl Frendin, Jonas Haglund.
 * 
 * Input parameters to solveCongruences must med at most 31 bits.
 * Other methods require at most 62 bits.
 * 
 * In general n is the n in Zn and 0 <= a, b < n.
 */

public class ChineseRemainder {

	/*
	 * Returns x in:
	 * x ≡ a1 mod n1
	 * x ≡ a2 mod n2
	 */
	public static long solveCongruences(long a1, long n1, long a2, long n2) {
		long n1n2 = n1*n2;
		long n1Inverse = getInverse(n1, n2);
		long n2Inverse = getInverse(n2, n1);
		long a1coeff, a2coeff;
		
		a1coeff = times(n2, n2Inverse, n1n2);
		a2coeff = times(n1, n1Inverse, n1n2);
		
		long t1 = times(a1, a1coeff, n1n2);
		long t2 = times(a2, a2coeff, n1n2);
		
		return plus(t1, t2, n1n2);
	}
	
	public static long plus(long a, long b, long n) {
		return (a + b) % n;
	}
	
	public static long minus(long a, long b, long n) {
		if (a >= b)
			return (a - b) % n;
		else
			return (a - b + n) % n;
	}
	
	public static long division(long a, long b, long n) {
		long inverse = getInverse(b, n);
		if (b == 0 || inverse == -1)
			return -1;
		else
			return times(a, inverse, n);
	}
	
	public static long times(long a, long b, long n) {
		long result = 0;
		while (b > 0) {
			if ((b & 0x1L) == 0x1L)
				result = (result + a) % n;
			a = (a<<1) % n;
			b = b/2;
		}
		
		return result;
	}
	
	/*
	 * Returns a's inverse in Zn.
	 */
	public static long getInverse(long a, long n) {
		long t = 0, r = n, newt = 1, newr = a;
		long quotient, tTemp, rTemp;

		while (newr != 0) {
			quotient = r / newr;
			
			//(t, newt) := (newt, t - quotient * newt) 
			tTemp = t;
			t = newt;
			newt = tTemp - quotient * newt;

			//(r, newr) := (newr, r - quotient * newr)
			rTemp = r;
			r = newr;
			newr = rTemp - quotient * newr;
		}
		
		if (r > 1)
			return -1;	//a is not invertible;
		if (t < 0)
			t = t + n;
		
		return t;
	}
}