/**
 * This class represents a rational number which supports the operations extend and
 * simplify.
 * 
 * @authors Tobias Andersson, Carl Frendin, Jonas Haglund
 * 
 */
public class RationalNumber {

	private long numerator;
	private long denominator;

	/**
	 * Constructs an instance of RationalNumber with the given numerator and
	 * denominator.
	 * 
	 * @param numerator
	 *            the numerator of this RationalNumber
	 * @param denominator
	 *            the denominator of this RationalNumber
	 */
	public RationalNumber(long numerator, long denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}

	public long getNumerator() {
		return numerator;
	}

	public long getDenominator() {
		return denominator;
	}

	/**
	 * Simplifies this RationalNumber with the given factor. If the factor is not a
	 * factor in either the numerator or denominator an ArithmeticException is
	 * thrown.
	 * 
	 * @param factor
	 *            the factor to simplify this RationalNumber with
	 */
	public void simplify(long factor) {
		if (numerator % factor != 0 || denominator % factor != 0)
			throw new ArithmeticException("Cannot simplify with a number that is not a factor in numerator and denominator");
		numerator = numerator / factor;
		denominator = denominator / factor;
	}

	/**
	 * Extends this RationalNumber with the given factor.
	 * 
	 * @param factor
	 *            the factor to extend this RationalNumber with
	 */
	public void extend(long factor) {
		numerator = numerator * factor;
		denominator = denominator * factor;
	}

	@Override
	public RationalNumber clone() {
		return new RationalNumber(numerator, denominator);
	}

	@Override
	public String toString() {
		return numerator + " / " + denominator;
	}

}
