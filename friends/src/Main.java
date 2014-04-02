import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		while (io.hasMoreTokens()) {
			int n = io.getInt();
			int m = io.getInt();
			List<Set<Integer>> neighbourList = new ArrayList<>(n);
			Set<Integer> vertices = new HashSet<Integer>();
			for (int i = 0; i < n; i++) {
				neighbourList.add(new HashSet<Integer>());
				vertices.add(i);
			}

			for (int i = 0; i < m; i++) {
				int friendA = io.getInt() - 1;
				int friendB = io.getInt() - 1;
				neighbourList.get(friendA).add(friendB);
				neighbourList.get(friendB).add(friendA);
			}

			int setsOfFriends = new Clique(vertices, neighbourList).getNumMaximalCliques();
			if (setsOfFriends == Integer.MAX_VALUE)
				io.println("Too many maximal sets of friends.");
			else
				io.println(setsOfFriends);
		}
		io.flush();
		io.close();
	}
}
