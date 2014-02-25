import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SMSTyper {

	private SMSTrie trie;
	private Map<String, List<Integer>> optimalTable;
	private static final int OPT_INDEX = 0;
	private static final int OPT_PRESSES = 1;

	public SMSTyper(List<String> dictionary) {
		trie = new SMSTrie(dictionary);
		optimalTable = new HashMap<String, List<Integer>>();
	}

	public String getOptimalKeypresses(String word) {
		calcOptSubWords(word);

		System.out.println(optimalTable);

		return null;
	}

	private void calcOptSubWords(String word) {
		if (optimalTable.containsKey(word))
			return;

		int optIndex = -1;
		int optPresses = Integer.MAX_VALUE;

		for (int i = 1; i <= word.length(); i++) {
			String sub = word.substring(0, i);
			String rest = word.substring(i, word.length());
			if (trie.isWord(sub) && trie.isWord(rest)) {
				calcOptSubWords(sub);
				calcOptSubWords(rest);
				if (getOptNumPresses(sub) + getOptNumPresses(rest) < optPresses) {
					optPresses = getOptNumPresses(sub) + getOptNumPresses(rest);
					optIndex = i;
				}
			}
		}

		List<Integer> opt = new ArrayList<Integer>();
		opt.add(optIndex);
		if (optIndex != -1) {
			opt.add(optPresses);
			optimalTable.put(word, opt);
		} else {
			opt.add(calcOptPresses(word));
			optimalTable.put(word, opt);
		}
	}

	/**
	 * TODO räkna ut hur man bäst skriver ordet utan att dela upp det
	 * 
	 * @param word
	 * @return
	 */
	private int calcOptPresses(String word) {
		int index = trie.getNode(word).getIndex();
		int above = 0;
		int below = 0;
		for (int i = word.length(); i > 0; i--) {
		}
		return 0;
	}

	private int getOptNumPresses(String word) {
		return optimalTable.get(word).get(OPT_PRESSES);
	}
}
