public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		while (io.hasMoreTokens()) {
			int n = io.getInt();

			if (n < 2) {
				io.println(1);
			} else {
				int rem = 1;
				int ins = 1;
				while (rem != 0) {
					rem = (rem * 10 + 1) % n;
					ins++;
				}
				io.println(ins);
			}
		}
		io.flush();
		io.close();
	}

}
