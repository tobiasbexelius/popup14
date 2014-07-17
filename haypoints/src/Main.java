import java.util.HashMap;

public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		int m = io.getInt();
		int n = io.getInt();
		HashMap<String, Integer> dictionary = new HashMap<>();
		for (int i = 0; i < m; i++)
			dictionary.put(io.getWord(), io.getInt());

		for (int i = 0; i < n; i++) {
			int salary = 0;
			String word = io.getWord();
			while (!word.equals(".")) {
				if (dictionary.containsKey(word))
					salary += dictionary.get(word);
				word = io.getWord();
			}
			io.println(salary);
		}
		io.flush();
		io.close();
	}
}