import java.util.ArrayList;
import java.util.List;

public class Placeholder {

	private String placeholder;
	private List<Integer> indices;

	public Placeholder(String placeholder) {
		this.placeholder = placeholder;
		indices = new ArrayList<Integer>();
	}

	public String toString() {

		return "Placeholder: " + placeholder + "\tindices: " + indices;
	}

	public List<Integer> getIndices() {
		return indices;
	}

	public void addIndex(int index) {
		indices.add(index);
	}

	public String getPlaceholder() {
		return placeholder;
	}
}
