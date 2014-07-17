import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	private static final int INFINITY = Integer.MAX_VALUE / 2;
	private static int n;
	private static Character[] s;
	private static Character[] t;
	private static Character[] revS;
	private static Map<List<Character>, Integer> dp;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		io.getInt();
		while (io.hasMoreTokens()) {
			char[] charS = io.getWord().toCharArray();
			char[] charT = io.getWord().toCharArray();
			s = new Character[charS.length];
			t = new Character[charT.length];

			for (int i = 0; i < s.length; i++)
				s[i] = charS[i];
			for (int i = 0; i < t.length; i++)
				t[i] = charT[i];

			n = t.length;
			revS = reverse(s);
			dp = new HashMap<>();
			dp.put(Arrays.asList(t), 0);
			int ops = copyDNA(new Character[n]);
			System.out.println("states: " + dp.size());
			if (ops != INFINITY)
				io.println(ops);
			else
				io.println("impossible");
		}
		io.flush();
		io.close();
	}

	private static int copyDNA(Character[] pt) {
		List<Character> ptList = Arrays.asList(pt);
		if (dp.containsKey(ptList))
			return dp.get(ptList);
		Character[] revPt = reverse(pt);

		int best = INFINITY;
		for (int i = 0; i < n; i++) {
			int spaces = numSpacesAt(i, pt);
			if (spaces == 0)
				continue;

			int matches = longestMatchAt(i, i + spaces, s);
			matches = Math.max(matches, longestMatchAt(i, i + spaces, revS));
			matches = Math.max(matches, longestMatchAt(i, i + spaces, pt));
			matches = Math.max(matches, longestMatchAt(i, i + spaces, revPt));

			if (matches == 0) {
				return INFINITY;
			}

			Character[] newPt = Arrays.copyOf(pt, n);

			for (int j = i; j < i + matches; j++) {
				newPt[j] = t[j];
			}

			best = Math.min(best, copyDNA(newPt) + 1);

		}
		dp.put(ptList, best);
		return best;
	}

	private static int numSpacesAt(int index, Character[] pt) {
		for (int i = index; i < n; i++) {
			if (pt[i] != null)
				return i - index;
		}
		return n - index;
	}

	private static Character[] reverse(Character[] a) {
		Character[] rev = new Character[a.length];
		for (int i = 0; i < a.length; i++) {
			rev[a.length - 1 - i] = a[i];
		}
		return rev;
	}

	private static int longestMatchAt(int index, int maxIndex, Character[] source) {
		int maxLength = maxIndex - index;
		int best = 0;
		for (int i = 0; i < source.length; i++) {
			for (int j = 0; j < maxLength && i + j < source.length; j++) {
				if (!t[j + index].equals(source[i + j]))
					break;
				best = Math.max(best, j + 1);
			}
		}
		return best;
	}
}
