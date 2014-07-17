public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		int n = io.getInt();
		int[] S = new int[n];
		int[] B = new int[n];
		for (int i = 0; i < n; i++) {
			S[i] = io.getInt();
			B[i] = io.getInt();
		}

		int min = Integer.MAX_VALUE;
		int permutations = (int) Math.round(Math.pow(2, n));

		for (int i = 1; i < permutations; i++) {
			String bits = String.format("%" + n + "s", Integer.toBinaryString(i)).replace(' ', '0');
			int s = 1;
			int b = 0;
			for (int j = 0; j < bits.length(); j++) {
				s *= bits.charAt(j) != '0' ? S[j] : 1;
				b += bits.charAt(j) != '0' ? B[j] : 0;
			}

			int taste = Math.abs(s - b);
			if (taste < min)
				min = taste;
		}

		io.println(min);
		io.flush();
		io.close();

	}
}
