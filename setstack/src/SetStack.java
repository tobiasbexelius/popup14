import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class SetStack {

	private Stack<CoolSet> stack;

	public SetStack() {
		stack = new Stack<CoolSet>();
	}

	public int push() {
		stack.push(new CoolSet());
		return 0;
	}

	public int dup() {
		CoolSet set1 = stack.pop();
		stack.push(set1);
		stack.push(set1);
		return set1.size;
	}

	public int add() {
		CoolSet set1 = stack.pop();
		CoolSet set2 = stack.pop();
		set2.add(set1);
		stack.push(set2);
		return set2.size;
	}

	public int union() {
		CoolSet set1 = stack.pop();
		CoolSet set2 = stack.pop();
		CoolSet newSet = new CoolSet();
		newSet.add(set1);
		newSet.add(set2);
		stack.push(newSet);
		return newSet.size;
	}

	public int intersect() {
		CoolSet set1 = stack.pop();
		CoolSet set2 = stack.pop();
		CoolSet intersection = new CoolSet();
		for (CoolSet e : set1.contents) {
			if (set2.contents.contains(e))
				intersection.add(e);
		}
		stack.push(intersection);
		return intersection.size;
	}

	private static class CoolSet {
		private Set<CoolSet> contents;
		private int size;

		public CoolSet() {
			this.size = 0;
		}

		private void add(CoolSet set) {
			if (contents == null)
				contents = new HashSet<CoolSet>();
			contents.add(set);
			this.size += set.size + 1;
		}

		@Override
		public int hashCode() {
			if (contents == null)
				return 1;
			return size = size * 10000 + contents.hashCode();
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof CoolSet && this.size == ((CoolSet) o).size && this.hashCode() == o.hashCode())
				return true;
			return false;
		}
	}
}
