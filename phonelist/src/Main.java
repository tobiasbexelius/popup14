import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		int t = io.getInt();
		for (int i = 0; i < t; i++) {
			int n = io.getInt();
			PhoneListTrie trie = new PhoneListTrie();
			boolean success = true;
			for (int j = 0; j < n; j++) {
				if (success)
					success = trie.add(io.getWord());
				else
					io.getWord();
			}
			if (success)
				io.println("YES");
			else
				io.println("NO");
		}
		io.flush();
		io.close();
	}

	private static class PhoneListTrie {
		private TrieNode root;

		public PhoneListTrie() {
			root = new TrieNode(false);
		}

		public boolean add(String word) {
			TrieNode current = root;
			for (int i = 0; i < word.length(); i++) {
				char c = word.charAt(i);
				if (current.isValid())
					return false;

				if (i == word.length() - 1) {
					if (current.hasChild(c))
						return false;
					else
						current.addChild(c, true);
				} else {
					if (!current.hasChild(c)) {
						current.addChild(c, false);
					}
				}
				current = current.getChild(c);
			}
			return true;
		}
	}

	private static class TrieNode {
		private boolean isValid;
		private Map<Character, TrieNode> children;

		public TrieNode(boolean isValid) {
			this.isValid = isValid;
			children = new HashMap<Character, TrieNode>();
		}

		public void addChild(char c, boolean isValid) {
			children.put(c, new TrieNode(isValid));
		}

		public boolean hasChild(char c) {
			return children.containsKey(c);
		}

		public TrieNode getChild(char c) {
			return children.get(c);
		}

		public boolean isValid() {
			return isValid;
		}
	}

}