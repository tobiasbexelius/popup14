import java.util.StringTokenizer;

/**
 * This class contains methods for performing rational arithmetic operations.
 * Supported operations are addition, subtraction, multiplication and division.
 * Numbers will overflow if the result of any operation results in a numerator or
 * denominator greater than 2^63-1 or smaller than -2^63.
 * 
 * @authors Tobias Andersson, Carl Frendin, Jonas Haglund
 * 
 */
public class RationalArithmetic {

	/**
	 * Returns a RationalNumber that represents the sum of the given RationalNumbers.
	 * 
	 * @param a
	 *            the first term
	 * @param b
	 *            the second term
	 * @return the sum of a and b
	 */
	public static RationalNumber add(RationalNumber a, RationalNumber b) {
		a = a.clone();
		b = b.clone();

		normalize(a);
		normalize(b);

		long lcm = lcm(a.getDenominator(), b.getDenominator());
		a.extend(lcm / a.getDenominator());
		b.extend(lcm / b.getDenominator());

		RationalNumber result = new RationalNumber(a.getNumerator() + b.getNumerator(), a.getDenominator());
		normalize(result);

		return result;
	}

	/**
	 * Returns a RationalNumber that represents the difference of a subtracted by b.
	 * 
	 * @param a
	 *            the first term
	 * @param b
	 *            the second term
	 * @return a RationalNumber representing the difference of a - b
	 */
	public static RationalNumber subtract(RationalNumber a, RationalNumber b) {
		a = a.clone();
		b = b.clone();

		normalize(a);
		normalize(b);

		long lcm = lcm(a.getDenominator(), b.getDenominator());
		a.extend(lcm / a.getDenominator());
		b.extend(lcm / b.getDenominator());

		RationalNumber result = new RationalNumber(a.getNumerator() - b.getNumerator(), a.getDenominator());
		normalize(result);

		return result;
	}

	/**
	 * Return a RationalNumber that represents the product of a and b.
	 * 
	 * @param a
	 *            the first factor
	 * @param b
	 *            the second factor
	 * @return the product of a and b
	 */
	public static RationalNumber multiply(RationalNumber a, RationalNumber b) {
		a = a.clone();
		b = b.clone();

		normalize(a);
		normalize(b);

		RationalNumber result = new RationalNumber(a.getNumerator() * b.getNumerator(), a.getDenominator() * b.getDenominator());
		normalize(result);

		return result;
	}

	/**
	 * Returns a RationalNumber that represents the quotient of a dividing a by b.
	 * 
	 * @param a
	 *            the numerator
	 * @param b
	 *            the denominator
	 * @return a RationalNumber that represents the result dividing a by b
	 */
	public static RationalNumber divide(RationalNumber a, RationalNumber b) {
		a = a.clone();
		b = b.clone();

		normalize(a);
		normalize(b);

		RationalNumber result = new RationalNumber(a.getNumerator() * b.getDenominator(), a.getDenominator() * b.getNumerator());
		normalize(result);
		return result;
	}

	/**
	 * Returns -1, 0 or 1 as the first RationalNumber is smaller than, equal two or
	 * greater then the second RationalNumber.
	 * 
	 * @param a
	 *            the first RationalNumber
	 * @param b
	 *            the second RationalNumber
	 * @return -1, 0 or 1 as a is smaller than, equal to, or greater than b
	 */
	public static int compare(RationalNumber a, RationalNumber b) {
		RationalNumber res = subtract(a, b);
		if (res.getNumerator() < 0)
			return -1;
		if (res.getNumerator() > 0)
			return 1;
		return 0;
	}

	/**
	 * Parses the given String for one rational arithmetic operation on the form
	 * "x1 y1 op x2 y2" where x1 is the numerator of the first operand, y1 is the
	 * denominator of the first operand, op is the operator, x2 is the numerator of
	 * the second operand and y2 is the denominator of the second operand.
	 * 
	 * The operator can be one of the following: '+' - addition '-' - subration '*' -
	 * multiplication '/' - division 'c' - comparison
	 * 
	 * Addition, subraction, multiplication and division operations result in a
	 * rational number on the form "x / y". Comparison acts like a standard
	 * comparator: if the first operand is smaller than the second, -1 is returned,
	 * if they are equal, 0 is returned and if the second is larger then the first, 1
	 * is returned.
	 * 
	 * @param input
	 *            a String which describes a rational arithmetic operation
	 * @return the result of the operation
	 */
	public static String fromString(String input) {
		StringTokenizer tokenizer = new StringTokenizer(input);
		int x1 = Integer.parseInt(tokenizer.nextToken());
		int y1 = Integer.parseInt(tokenizer.nextToken());
		char op = tokenizer.nextToken().charAt(0);
		int x2 = Integer.parseInt(tokenizer.nextToken());
		int y2 = Integer.parseInt(tokenizer.nextToken());

		RationalNumber a = new RationalNumber(x1, y1);
		RationalNumber b = new RationalNumber(x2, y2);

		RationalNumber result = null;
		switch (op) {
		case '+':
			result = RationalArithmetic.add(a, b);
			break;
		case '-':
			result = RationalArithmetic.subtract(a, b);
			break;
		case '*':
			result = RationalArithmetic.multiply(a, b);
			break;
		case '/':
			result = RationalArithmetic.divide(a, b);
			break;
		case 'c':
			return Integer.toString(RationalArithmetic.compare(a, b));
		}

		return result.toString();
	}

	private static void normalize(RationalNumber a) {
		long gcd = gcd(a.getNumerator(), a.getDenominator());
		a.simplify(gcd);

		if (a.getDenominator() < 0) {
			a.simplify(-1);
		}
	}

	private static long lcm(long a, long b) {
		return a * b / gcd(a, b);
	}

	private static long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, (a % b));
	}
}
