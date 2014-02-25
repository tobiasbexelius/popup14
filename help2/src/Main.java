import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int problemInstances = Integer.parseInt(reader.readLine());
		int i = 0;
		while (i < problemInstances) {
			List<String> phrase1 = Arrays.asList(reader.readLine().split(" "));
			List<String> phrase2 = Arrays.asList(reader.readLine().split(" "));
			System.out.println(findMatchingPhrase(phrase1, phrase2));
			i++;
		}
		reader.close();
	}

	private static String findMatchingPhrase(List<String> phrase1, List<String> phrase2) {
		if (phrase1.size() != phrase2.size())
			return "-";

		List<Placeholder> placeholdersInPhrase1 = findPlaceholders(phrase1);
		List<Placeholder> placeholdersInPhrase2 = findPlaceholders(phrase2);
		while (!(placeholdersInPhrase1.isEmpty() && placeholdersInPhrase2.isEmpty())) {
			int status1 = processList(phrase1, phrase2, placeholdersInPhrase1);
			if (status1 == -1)
				return "-";

			int status2 = processList(phrase2, phrase1, placeholdersInPhrase2);
			if (status2 == -1)
				return "-";

			if (status1 == 0 && status2 == 0) {
				break;
			}
		}

		String p1 = "";
		String p2 = "";
		for (int i = 0; i < phrase1.size(); i++) {
			if (phrase1.get(i).charAt(0) == '<')
				p1 += "whatever ";
			else
				p1 += phrase1.get(i) + " ";
			if (phrase2.get(i).charAt(0) == '<')
				p2 += "whatever ";
			else
				p2 += phrase2.get(i) + " ";
		}
		if (p1.equals(p2))
			return p1;
		return "-";
	}

	private static int processList(List<String> phraseToProcess, List<String> referencePhrase, List<Placeholder> placeholders) {
		boolean changesMade = false;
		List<Placeholder> toBeRemoved = new ArrayList<Placeholder>();
		for (Placeholder placeholder : placeholders) {
			String word = null;
			for (int index : placeholder.getIndices()) {
				if (referencePhrase.get(index).charAt(0) == '<')
					continue;
				if (word != null && !referencePhrase.get(index).equals(word))
					return -1;
				word = referencePhrase.get(index);
			}

			if (word != null) {
				changesMade = true;
				toBeRemoved.add(placeholder);
				for (int index : placeholder.getIndices())
					phraseToProcess.set(index, word);
			}
		}
		placeholders.removeAll(toBeRemoved);
		toBeRemoved.clear();
		if (changesMade)
			return 1;
		return 0;
	}

	private static List<Placeholder> findPlaceholders(List<String> phrase) {
		List<Placeholder> placeholders = new ArrayList<Placeholder>();
		for (int i = 0; i < phrase.size(); i++) {
			String word = phrase.get(i);
			if (word.charAt(0) == '<') {
				boolean placeholderFound = false;
				for (Placeholder ph : placeholders) {
					if (ph.getPlaceholder().equals(word)) {
						ph.addIndex(i);
						placeholderFound = true;
					}
				}
				if (!placeholderFound) {
					Placeholder newPH = new Placeholder(word);
					newPH.addIndex(i);
					placeholders.add(newPH);
				}
			}
		}
		return placeholders;
	}
}
