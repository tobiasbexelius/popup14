public class Piano implements Comparable<Piano> {
	private int firstDay;
	private int lastDay;

	public Piano(int firstDay, int lastDay) {
		this.firstDay = firstDay;
		this.lastDay = lastDay;
	}

	public int getFirstDay() {
		return firstDay;
	}

	public int getLastDay() {
		return lastDay;
	}

	public String toString() {
		return "(" + firstDay + ", " + lastDay + ")";
	}

	@Override
	public int compareTo(Piano p) {
		if (lastDay == p.getLastDay())
			return 0;
		if (lastDay < p.lastDay)
			return -1;
		return 1;
	}
}