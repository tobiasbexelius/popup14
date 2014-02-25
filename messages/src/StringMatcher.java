import java.util.ArrayList;
import java.util.List;

public class StringMatcher {

	public static final int NO_MATCH = -1;
	List<Integer> kmpTable = new ArrayList<Integer>();
	int usedTableSize;
	String text, word;
	int m, i;

	public StringMatcher(String text, String word) {
		kmpTable = new ArrayList<Integer>();
		usedTableSize = -1;
		this.text = text;
		this.word = word;
		m = 0;
		i = 0;
		initTable();
	}

	private void initTable() {
		for (int i = 0; i < word.length(); i++) {
			kmpTable.add(0);
		}
		int pos = 2;
		int cnd = 0;
		kmpTable.set(0, -1);
		while (pos < word.length()) {
			if (word.charAt(pos - 1) == word.charAt(cnd)) {
				cnd++;
				kmpTable.set(pos, cnd);
				pos++;
			} else if (cnd > 0) {
				cnd = kmpTable.get(cnd);
			} else {
				kmpTable.set(pos, 0);
				pos++;
			}
		}
	}

	public int nextSearch() {
		while (m + i < text.length()) {
			if (word.charAt(i) == text.charAt(m + i)) {
				if (i == word.length() - 1) {
					int retVal = m;
					m = m + i - kmpTable.get(i);
					if (kmpTable.get(i) > -1) {
						i = kmpTable.get(i);
					} else {
						i = 0;
					}
					return retVal;
				}
				i++;
			} else {
				m = m + i - kmpTable.get(i);
				if (kmpTable.get(i) > -1) {
					i = kmpTable.get(i);
				} else {
					i = 0;
				}
			}
		}
		return NO_MATCH;
	}
}