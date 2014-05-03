public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		int n = io.getInt();

		int twos = (int) (Math.log(n) / Math.log(2)) + 1;
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < twos; i++) {
			sb.append((int) Math.pow(2, i));
			sb.append(" ");
		}

		io.println(twos);
		io.println(sb.toString());

		io.flush();
		io.close();
	}
}
