import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		while (io.hasMoreTokens()) {
			int n = io.getInt();
			int m = io.getInt();
			List<BitSet> neighbourList = new ArrayList<>(n);
			for (int i = 0; i < n; i++) {
				neighbourList.add(new BitSet(n));
			}

			for (int i = 0; i < m; i++) {
				int friendA = io.getInt() - 1;
				int friendB = io.getInt() - 1;
				neighbourList.get(friendA).set(friendB);
				neighbourList.get(friendB).set(friendA);
			}

			int setsOfFriends = new Clique(neighbourList).getNumMaximalCliques();
			if (setsOfFriends == -1)
				io.println("Too many maximal sets of friends.");
			else
				io.println(setsOfFriends);
		}
		io.flush();
		io.close();
	}
}
