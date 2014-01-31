import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OldMain {

	public static void main(String[] args) throws IOException {
		Kattio io = new Kattio(System.in);
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
		Map<Integer, List<Integer>> occurances = getOccurances(accesses);
		Map<Integer, Boolean> cache = new HashMap<Integer, Boolean>();
		int cacheWrites = 0;
		int accessIndex = 0;
		while (cacheWrites < cacheSpace && accessIndex < accesses.size()) {
			System.out.println(accessIndex + "\t" + cache);
			int currentObject = accesses.get(accessIndex);
			accessIndex++;
			occurances.get(currentObject).remove(0);
			if (!cache.containsKey(currentObject)) {
				cache.put(currentObject, true);
				cacheWrites++;
			}
		}
		while (accessIndex < accesses.size()) {
			System.out.println(accessIndex + "\t" + cache);
			int currentObject = accesses.get(accessIndex);
			accessIndex++;
			occurances.get(currentObject).remove(0);
			if (!(cache.containsKey(currentObject) && cache.get(currentObject) == true)) {
				int objectToReplace = getObjectToReplace(currentObject, occurances, numObjects, cache);
				cache.put(currentObject, true);
				cache.put(objectToReplace, false);
				cacheWrites++;
			}
		}
		return cacheWrites;
	}

	private static int getObjectToReplace(int currentObject, Map<Integer, List<Integer>> occurances, int numObjects, Map<Integer, Boolean> cache) {
		int replaceObject = -1;
		int replaceAccessTime = -1;
		for (int i = 0; i < numObjects; i++) {
			if ((cache.containsKey(i) && cache.get(i) == true) && i != currentObject) {
				if (occurances.get(i).isEmpty()) {
					return i;
				} else {
					int accessTime = occurances.get(i).get(0);
					if (accessTime > replaceAccessTime) {
						replaceAccessTime = accessTime;
						replaceObject = i;
					}
				}
			}
		}
		return replaceObject;
	}

	private static Map<Integer, List<Integer>> getOccurances(List<Integer> accesses) {
		Map<Integer, List<Integer>> occurances = new HashMap<Integer, List<Integer>>();
		for (int i = 0; i < accesses.size(); i++) {
			if (occurances.containsKey(accesses.get(i))) {
				occurances.get(accesses.get(i)).add(i);
			} else {
				List<Integer> list = new ArrayList<Integer>();
				list.add(i);
				occurances.put(accesses.get(i), list);
			}
		}
		return occurances;
	}
}