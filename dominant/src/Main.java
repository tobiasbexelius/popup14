import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		List<String> strings = new ArrayList<String>();
		while (io.hasMoreTokens())
			strings.add(io.getWord());
		List<String> dominantWords = getDominantWords(strings);
		for (String word : dominantWords)
			io.println(word);
		io.flush();
		io.close();
	}

	private static List<String> getDominantWords(List<String> strings) {
		List<String> dominating = new ArrayList<String>();
		Map<String, Map<Character, Integer>> wordTable = getWordTable(strings);

		TreeSet<String> tree = new TreeSet<String>(new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				if (s1.length() < s2.length())
					return 1;
				return -1;
			}
		});

		for (String s : strings)
			tree.add(s);

		for (int i = 0; i < strings.size(); i++) {
			String word = strings.get(i);
			boolean isDominated = false;
			for (String bigger : tree.headSet(word)) {
				if (isDominating(bigger, word, wordTable)) {
					isDominated = true;
					break;
				}
			}
			if (!isDominated)
				dominating.add(word);
		}

		Collections.sort(dominating);
		return dominating;
	}

	private static Map<String, Map<Character, Integer>> getWordTable(List<String> strings) {
		Map<String, Map<Character, Integer>> wordTable = new HashMap<String, Map<Character, Integer>>();
		for (String s : strings) {
			Map<Character, Integer> charactersInWord = new HashMap<Character, Integer>();
			wordTable.put(s, charactersInWord);
			for (char c : s.toCharArray())
				if (charactersInWord.containsKey(c))
					charactersInWord.put(c, charactersInWord.get(c) + 1);
				else
					charactersInWord.put(c, 1);
		}
		return wordTable;
	}

	private static boolean isDominating(String bigger, String smaller, Map<String, Map<Character, Integer>> wordTable) {
		Map<Character, Integer> biggerChars = wordTable.get(bigger);
		Map<Character, Integer> smallerChars = wordTable.get(smaller);
		for (char c : smaller.toCharArray())
			if (!biggerChars.containsKey(c) || biggerChars.get(c) < smallerChars.get(c))
				return false;
		return true;
	}
}