import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		Kattio io = new Kattio(System.in);
		while (io.hasMoreTokens()) {
			int n = io.getInt();
			int m = io.getInt();
			AlmostUnion union = new AlmostUnion(n);
			for (int i = 0; i < m; i++) {
				int command = io.getInt();
				int p = io.getInt();
				if (command == 1) {
					int q = io.getInt();
					union.union(p - 1, q - 1);
				} else if (command == 2) {
					int q = io.getInt();
					union.move(p - 1, q - 1);
				} else {
					System.out.println(union.componentString(p - 1));
				}
			}
		}
		io.close();
	}
}
