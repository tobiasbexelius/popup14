import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		List<String> dictionary = new ArrayList<String>();
		while (true) {
			String word = io.getWord();
			if (word.equals("#"))
				break;
			else
				dictionary.add(word);
		}

		StringBuilder sb = new StringBuilder();
		while (true) {
			String message = io.getWord();
			if (message.equals("#"))
				break;
			if (message.endsWith("|")) {
				sb.append(message.subSequence(0, message.length() - 1));
				io.println(getMaxWords(dictionary, sb.toString()));
				sb = new StringBuilder();
				io.flush();
			} else {
				sb.append(message);
			}
		}
		io.flush();
		io.close();
	}

	private static int getMaxWords(List<String> dictionary, String message) {
		String[] matches = new String[message.length()];

		List<StringMatcher> wordMatchers = new ArrayList<StringMatcher>();
		for (int i = 0; i < dictionary.size(); i++) {
			wordMatchers.add(new StringMatcher(message, dictionary.get(i)));
		}

		for (int i = 0; i < dictionary.size(); i++) {
			boolean matchesLeft = true;
			while (matchesLeft) {
				int matchIndex = wordMatchers.get(i).nextSearch();
				if (matchIndex == StringMatcher.NO_MATCH) {
					matchesLeft = false;
				} else {
					if (matches[matchIndex] == null || matches[matchIndex].length() > dictionary.get(i).length()) {
						matches[matchIndex] = dictionary.get(i);
					}
				}
			}
		}

		int numMatches = 0;
		int previousMatchIndex = matches.length;
		for (int i = matches.length - 1; i >= 0; i--) {
			if (matches[i] != null && previousMatchIndex >= i + matches[i].length()) {
				previousMatchIndex = i;
				numMatches++;
			}
		}

		return numMatches;
	}
}