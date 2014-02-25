import java.util.List;

public class SMSTrie {
	private SMSNode root;

	public SMSTrie(List<String> dictionary) {
		root = new SMSNode();
		for (int i = 0; i < dictionary.size(); i++)
			addWord(dictionary.get(i), i);
	}

	private void addWord(String word, int index) {
		SMSNode current = root;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (current.hasChild(c)) {
				current = current.getChild(c);
			} else if (i != word.length() - 1) {
				SMSNode next = new SMSNode();
				current.addChild(c, next);
				current = next;
			} else {
				current.addChild(c, new SMSNode(index));
			}
		}
	}

	public boolean isWord(String word) {
		SMSNode current = root;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (current.hasChild(c))
				current = current.getChild(c);
			else
				return false;
		}
		return true;
	}

	public SMSNode getNode(String word) {
		SMSNode current = root;
		for (int i = 0; i < word.length(); i++) {
			current = current.getChild(word.charAt(i));
		}
		return current;

	}
}
