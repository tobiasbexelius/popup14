import java.util.HashMap;
import java.util.Map;

public class SMSNode {
	private boolean isAccepting;
	private Map<Character, SMSNode> children;
	private int index;

	public SMSNode() {
		isAccepting = false;
		children = new HashMap<Character, SMSNode>();
	}

	public SMSNode(int index) {
		isAccepting = true;
		children = new HashMap<Character, SMSNode>();
		this.index = index;
	}

	public boolean hasChild(char c) {
		return children.containsKey(c);
	}

	public SMSNode getChild(char c) {
		return children.get(c);
	}

	public boolean isAccepting() {
		return isAccepting;
	}

	public void addChild(char c, SMSNode node) {
		children.put(c, node);
	}

	public int getIndex() {
		return index;
	}
}