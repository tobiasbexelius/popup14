import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		long n = io.getLong();
		PollardRho factorizer = new PollardRho();
		List<BigInteger> factors = factorizer.factorize(BigInteger.valueOf(n));
		Map<BigInteger, Integer> factorPowers = new HashMap<>();
		for (int i = 0; i < factors.size(); i++) {
			BigInteger current = factors.get(i);
			if (!factorPowers.containsKey(current))
				factorPowers.put(current, 0);
			factorPowers.put(current, factorPowers.get(current) + 1);
		}

		TreeMap<Integer, Integer> sortedfactorPowers = new TreeMap<>();
		Iterator<Integer> powers = factorPowers.values().iterator();

		for (int i = 0; powers.hasNext(); i++)
			sortedfactorPowers.put(i, powers.next());

		io.println(numUniqueFactors(sortedfactorPowers));
		io.flush();
		io.close();
	}

	private static int numUniqueFactors(TreeMap<Integer, Integer> factorPowers) {
		Set<HashSet<Integer>> combinations = new HashSet<>();

		return combinations.size();
	}

}