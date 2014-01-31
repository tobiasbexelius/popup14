public class Coins {

	private int ones;
	private int fives;
	private int tens;

	public Coins(int ones, int fives, int tens) {
		this.ones = ones;
		this.fives = fives;
		this.tens = tens;
	}

	public int getValue() {
		return 10 * tens + 5 * fives + 1 * ones;
	}

	public int getOnes() {
		return ones;
	}

	public void setOnes(int ones) {
		this.ones = ones;
	}

	public int getFives() {
		return fives;
	}

	public void setFives(int fives) {
		this.fives = fives;
	}

	public int getTens() {
		return tens;
	}

	public void setTens(int tens) {
		this.tens = tens;
	}

}
