public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		while (io.hasMoreTokens()) {
			long m = io.getLong();
			long n = io.getLong();
			if (m < 0)
				break;
			if (m == n) {
				long zeros = 0;
				String s = Long.toString(n);
				for (int i = 0; i < s.length(); i++)
					if (s.charAt(i) == '0')
						zeros++;
				io.println(zeros);
			} else {
				io.println(numZeros(n) - numZeros(m - 1));
			}
		}
		io.flush();
		io.close();
	}

	private static long numZeros(long n) {
		if (n < 0)
			return 0;
		String s = Long.toString(n);
		long zeros = 1;
		for (int i = s.length() - 1; i >= 1; i--) {
			long pre = Long.parseLong(s.substring(0, i));
			long pow = Math.round(Math.pow(10, s.length() - (i + 1)));
			if (s.charAt(i) == '0') {
				long suf = (i == s.length() - 1) ? 0 : Long.parseLong(s.substring(i + 1, s.length()));
				zeros += (pre - 1) * pow + suf + 1;
			} else {
				zeros += pre * pow;
			}

		}
		return zeros;
	}
}
