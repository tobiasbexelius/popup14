import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {

	private static Map<MailboxTest, Integer> resultCache;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int problemInstances = Integer.parseInt(reader.readLine());
		resultCache = new HashMap<MailboxTest, Integer>();
		for (int i = 0; i < problemInstances; i++) {
			String[] line = reader.readLine().split(" ");
			System.out.println(searchForWorstCase(1, Integer.parseInt(line[1]), Integer.parseInt(line[0])));
		}
		reader.close();
	}

	private static int searchForWorstCase(int min, int max, int mailboxesLeft) {

		// System.out.println(min + "\t" + max + "\t" + "\t" + mailboxesLeft);
		MailboxTest test = new MailboxTest(min, max, mailboxesLeft);
		if (resultCache.containsKey(test)) {
			return resultCache.get(test);
		}

		int bestWorstCase = Integer.MAX_VALUE;
		if (mailboxesLeft == 1 || max == min) {
			bestWorstCase = (max - min + 1) * (min + max) / 2;
			resultCache.put(test, bestWorstCase);
			return bestWorstCase;
		}

		for (int i = min; i <= max; i++) {
			int mailboxBlowsUp;
			if (i != min)
				mailboxBlowsUp = searchForWorstCase(min, i - 1, mailboxesLeft - 1);
			else
				mailboxBlowsUp = i;
			int mailboxHolds;
			if (i != max)
				mailboxHolds = searchForWorstCase(i + 1, max, mailboxesLeft);
			else
				mailboxHolds = i;
			int worstCase = Math.max(mailboxBlowsUp, mailboxHolds) + i;
			if (worstCase < bestWorstCase)
				bestWorstCase = worstCase;
		}

		resultCache.put(test, bestWorstCase);
		return bestWorstCase;
	}
}
