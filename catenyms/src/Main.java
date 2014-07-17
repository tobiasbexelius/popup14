import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		io.getInt();
		while (io.hasMoreTokens()) {
			int n = io.getInt();

			List<Character> nodeToChar = new ArrayList<>();
			Map<Character, Integer> charToNode = new HashMap<>();
			List<Edge> edges = new ArrayList<>();

			int smallestNode = -1;
			String smallestWord = "ööööööööööööööööööööö";

			for (int i = 0; i < n; i++) {
				String word = io.getWord();

				char first = word.charAt(0);
				char last = word.charAt(word.length() - 1);

				if (!charToNode.containsKey(first)) {
					charToNode.put(first, nodeToChar.size());
					nodeToChar.add(first);
				}

				if (!charToNode.containsKey(last)) {
					charToNode.put(last, nodeToChar.size());
					nodeToChar.add(last);
				}

				if (word.compareTo(smallestWord) < 1) {
					smallestNode = charToNode.get(word.charAt(0));
					smallestWord = word;
				}

				edges.add(new Edge(charToNode.get(first), charToNode.get(last), word));
			}

			EulerianPath ep = new EulerianPath(charToNode.size(), n, edges, smallestNode);
			Stack<String> s = ep.findEulerPath();

			if (s == null) {
				io.println("***");
			} else {
				StringBuilder sb = new StringBuilder();
				while (!s.isEmpty()) {
					String current = s.pop();
					sb.append(current + ".");
				}
				sb.deleteCharAt(sb.length() - 1);

				io.println(sb.toString());
			}

		}
		io.flush();
		io.close();
	}
}
