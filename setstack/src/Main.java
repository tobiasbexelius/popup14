import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		Kattio io = new Kattio(System.in);
		int numTests = io.getInt();
		for (int i = 0; i < numTests; i++) {
			int numOperations = io.getInt();
			SetStack setstack = new SetStack();
			for (int j = 0; j < numOperations; j++) {
				String command = io.getWord();
				switch (command) {
				case ("PUSH"):
					System.out.println(setstack.push());
					break;
				case ("DUP"):
					System.out.println(setstack.dup());
					break;
				case ("ADD"):
					System.out.println(setstack.add());
					break;
				case ("UNION"):
					System.out.println(setstack.union());
					break;
				case ("INTERSECT"):
					System.out.println(setstack.intersect());
					break;
				}
			}
		}
		io.flush();
		io.close();
	}
}
