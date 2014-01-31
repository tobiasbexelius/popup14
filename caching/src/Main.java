import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class Main {

	public static void main(String[] args) throws IOException {
		Kattio io = new Kattio(System.in);
		// Kattio io = new Kattio(new FileInputStream("bin/test3"));
		int cacheSpace = io.getInt();
		int numObjects = io.getInt();
		int numAccesses = io.getInt();
		List<Integer> accesses = new ArrayList<Integer>();
		for (int i = 0; i < numAccesses; i++) {
			accesses.add(io.getInt());
		}
		io.flush();
		System.out.println(getOptimalCacheWrites(cacheSpace, numObjects, accesses));
	}

	private static int getOptimalCacheWrites(int cacheSpace, int numObjects, List<Integer> accesses) {
		Map<Integer, Queue<Integer>> occurrences = getOccurances(accesses);
		Set<Integer> cache = new HashSet<Integer>();
		TreeSet<CacheObject> nextOccurrences = new TreeSet<CacheObject>();

		int cacheWrites = 0, accessIndex = 0;
		while (accessIndex < accesses.size()) {
			int currentObject = accesses.get(accessIndex);
			accessIndex++;
			int removed = occurrences.get(currentObject).poll();
			if (!cache.contains(currentObject)) {
				if (cacheWrites >= cacheSpace) {
					int objectToReplace = nextOccurrences.pollFirst().getObjectNumber();
					cache.remove(objectToReplace);
				}
				nextOccurrences.add(new CacheObject(currentObject, getNextOccurrence(currentObject, occurrences)));
				cache.add(currentObject);
				cacheWrites++;
			} else {
				nextOccurrences.remove(new CacheObject(currentObject, removed));
				nextOccurrences.add(new CacheObject(currentObject, getNextOccurrence(currentObject, occurrences)));
			}
		}
		return cacheWrites;
	}

	private static int getNextOccurrence(int currentObject, Map<Integer, Queue<Integer>> occurrences) {
		if (occurrences.get(currentObject).isEmpty())
			return Integer.MAX_VALUE;
		else
			return occurrences.get(currentObject).peek();
	}

	private static Map<Integer, Queue<Integer>> getOccurances(List<Integer> accesses) {
		Map<Integer, Queue<Integer>> occurances = new HashMap<Integer, Queue<Integer>>();
		for (int i = 0; i < accesses.size(); i++) {
			if (occurances.containsKey(accesses.get(i))) {
				occurances.get(accesses.get(i)).add(i);
			} else {
				Queue<Integer> list = new LinkedList<Integer>();
				list.add(i);
				occurances.put(accesses.get(i), list);
			}
		}
		return occurances;
	}
}