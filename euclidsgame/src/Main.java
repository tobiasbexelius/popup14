public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		while (io.hasMoreTokens()) {
			int a = io.getInt();
			int b = io.getInt();

			if (a == 0 && b == 0)
				break;

			if (currentPlayerWins(a, b))
				io.println("Stan wins");
			else
				io.println("Ollie wins");
		}
		io.flush();
		io.close();
	}

	private static boolean currentPlayerWins(int a, int b) {
		if (a < b) {
			int tmp = a;
			a = b;
			b = tmp;
		}

		if (a % b == 0)
			return true;

		if (!currentPlayerWins(b, a % b) || (a / b > 1 && !currentPlayerWins(b + (a % b), b)))
			return true;

		return false;
	}

}
