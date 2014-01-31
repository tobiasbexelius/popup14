public class MailboxTest {

	private int min;
	private int max;
	private int mailboxesLeft;

	public MailboxTest(int min, int max, int mailboxesLeft) {
		this.min = min;
		this.max = max;
		this.mailboxesLeft = mailboxesLeft;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	public int getMailboxesLeft() {
		return mailboxesLeft;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof MailboxTest))
			return false;
		MailboxTest m = (MailboxTest) o;
		if (min == m.min && max == m.max && mailboxesLeft == m.mailboxesLeft)
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		return mailboxesLeft * 200000 + max * 100 + min;
	}
}
