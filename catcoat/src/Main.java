import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	private static final String black = "Black";
	private static final String blue = "Blue";
	private static final String chocolate = "Chocolate";
	private static final String lilac = "Lilac";
	private static final String red = "Red";
	private static final String cream = "Cream";
	private static final String blackRed = "Black-Red Tortie";
	private static final String blueCream = "Blue-Cream Tortie";
	private static final String chocolateRed = "Chocolate-Red Tortie";
	private static final String lilacCream = "Lilac-Cream Tortie";

	private static final int B = 0;
	private static final int D = 1;
	private static final int O = 2;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String fColor = reader.readLine();
		String mColor = reader.readLine();
		Map<String, RationalNumber> probs = catCoat(fColor, mColor);

		List<CatCoatProbability> res = new ArrayList<>();
		for (String key : probs.keySet()) {
			RationalNumber prob = probs.get(key);
			if (prob.getNumerator() > 0)
				res.add(new CatCoatProbability(key, (double) prob.getNumerator() / prob.getDenominator()));
		}

		Collections.sort(res);

		for (CatCoatProbability p : res) {
			System.out.println(p.color + " " + p.probability);
		}
	}

	private static Map<String, RationalNumber> catCoat(String fColor, String mColor) {
		Map<int[], RationalNumber> femaleGenes = colorToGenes(fColor);
		Map<int[], RationalNumber> maleGenes = colorToGenes(mColor);
		return kittenProbs(femaleGenes, maleGenes);
	}

	private static Map<String, RationalNumber> kittenProbs(Map<int[], RationalNumber> femaleGenes, Map<int[], RationalNumber> maleGenes) {
		Map<String, RationalNumber> res = new HashMap<>();
		res.put(black, new RationalNumber(0, 1));
		res.put(blue, new RationalNumber(0, 1));
		res.put(chocolate, new RationalNumber(0, 1));
		res.put(lilac, new RationalNumber(0, 1));
		res.put(red, new RationalNumber(0, 1));
		res.put(cream, new RationalNumber(0, 1));
		res.put(blackRed, new RationalNumber(0, 1));
		res.put(blueCream, new RationalNumber(0, 1));
		res.put(chocolateRed, new RationalNumber(0, 1));
		res.put(lilacCream, new RationalNumber(0, 1));

		for (int[] fGenes : femaleGenes.keySet()) {

			for (int[] mGenes : maleGenes.keySet()) {

				RationalNumber weight = RationalArithmetic.multiply(femaleGenes.get(fGenes), maleGenes.get(mGenes));
				weight = RationalArithmetic.multiply(weight, new RationalNumber(1, 2));

				RationalNumber BB = twoDomProb(fGenes, mGenes, B);
				RationalNumber Bb = oneDomOneRecProb(fGenes, mGenes, B);
				RationalNumber bb = twoRecProb(fGenes, mGenes, B);

				RationalNumber DD = twoDomProb(fGenes, mGenes, D);
				RationalNumber Dd = oneDomOneRecProb(fGenes, mGenes, D);
				RationalNumber dd = twoRecProb(fGenes, mGenes, D);

				// male

				RationalNumber R = new RationalNumber(fGenes[O], 2);
				RationalNumber r = RationalArithmetic.subtract(new RationalNumber(1, 1), R);

				res.put(black, add(res.get(black), mul(weight, mul(r, mul(add(BB, Bb), add(DD, Dd))))));
				res.put(blue, add(res.get(blue), mul(weight, mul(r, mul(add(BB, Bb), dd)))));
				res.put(chocolate, add(res.get(chocolate), mul(weight, mul(r, mul(bb, add(DD, Dd))))));
				res.put(lilac, add(res.get(lilac), mul(weight, mul(r, mul(bb, dd)))));
				res.put(red, add(res.get(red), mul(weight, mul(R, add(DD, Dd)))));
				res.put(cream, add(res.get(cream), mul(weight, mul(R, dd))));

				// female

				if (mGenes[O] == 1)
					mGenes[O] = 2;

				RationalNumber RR = twoDomProb(fGenes, mGenes, O);
				RationalNumber Rr = oneDomOneRecProb(fGenes, mGenes, O);
				RationalNumber rr = twoRecProb(fGenes, mGenes, O);

				res.put(black, add(res.get(black), mul(weight, mul(rr, mul(add(BB, Bb), add(DD, Dd))))));
				res.put(blue, add(res.get(blue), mul(weight, mul(rr, mul(add(BB, Bb), dd)))));
				res.put(chocolate, add(res.get(chocolate), mul(weight, mul(rr, mul(bb, add(DD, Dd))))));
				res.put(lilac, add(res.get(lilac), mul(weight, mul(rr, mul(bb, dd)))));
				res.put(red, add(res.get(red), mul(weight, mul(RR, add(DD, Dd)))));
				res.put(cream, add(res.get(cream), mul(weight, mul(RR, dd))));
				res.put(blackRed, add(res.get(blackRed), mul(weight, mul(Rr, mul(add(BB, Bb), add(DD, Dd))))));
				res.put(blueCream, add(res.get(blueCream), mul(weight, mul(Rr, mul(add(BB, Bb), dd)))));
				res.put(chocolateRed, add(res.get(chocolateRed), mul(weight, mul(Rr, mul(bb, add(DD, Dd))))));
				res.put(lilacCream, add(res.get(lilacCream), mul(weight, mul(Rr, mul(bb, dd)))));

			}
		}

		return res;
	}

	private static RationalNumber add(RationalNumber a, RationalNumber b) {
		return RationalArithmetic.add(a, b);
	}

	private static RationalNumber mul(RationalNumber a, RationalNumber b) {
		return RationalArithmetic.multiply(a, b);
	}

	private static RationalNumber twoDomProb(int[] fGenes, int[] mGenes, int type) {
		if (fGenes[type] == 0 || mGenes[type] == 0)
			return new RationalNumber(0, 1);
		int num = fGenes[type] + mGenes[type];

		if (num == 4)
			return new RationalNumber(1, 1);

		if (num == 3)
			return new RationalNumber(1, 2);

		return new RationalNumber(1, 4);
	}

	private static RationalNumber twoRecProb(int[] fGenes, int[] mGenes, int type) {
		if (fGenes[type] == 2 || mGenes[type] == 2)
			return new RationalNumber(0, 1);
		int num = fGenes[type] + mGenes[type];

		if (num == 0)
			return new RationalNumber(1, 1);

		if (num == 1)
			return new RationalNumber(1, 2);

		return new RationalNumber(1, 4);
	}

	private static RationalNumber oneDomOneRecProb(int[] fGenes, int[] mGenes, int type) {
		int f = fGenes[type];
		int m = mGenes[type];

		if ((f == 0 && m == 0) || (f == 2 && m == 2))
			return new RationalNumber(0, 1);

		if ((f == 0 && m == 2) || f == 2 && m == 0)
			return new RationalNumber(1, 1);

		return new RationalNumber(1, 2);
	}

	private static Map<int[], RationalNumber> colorToGenes(String color) {
		Map<int[], RationalNumber> res = new HashMap<>();
		switch (color) {
		case black:
			res.put(new int[] { 2, 2, 0 }, new RationalNumber(1, 4));
			res.put(new int[] { 1, 2, 0 }, new RationalNumber(1, 4));
			res.put(new int[] { 2, 1, 0 }, new RationalNumber(1, 4));
			res.put(new int[] { 1, 1, 0 }, new RationalNumber(1, 4));
			break;
		case blue:
			res.put(new int[] { 2, 0, 0 }, new RationalNumber(1, 2));
			res.put(new int[] { 1, 0, 0 }, new RationalNumber(1, 2));
			break;
		case chocolate:
			res.put(new int[] { 0, 2, 0 }, new RationalNumber(1, 2));
			res.put(new int[] { 0, 1, 0 }, new RationalNumber(1, 2));
			break;
		case lilac:
			res.put(new int[] { 0, 0, 0 }, new RationalNumber(1, 1));
			break;
		case red:
			res.put(new int[] { 0, 2, 2 }, new RationalNumber(1, 6));
			res.put(new int[] { 1, 2, 2 }, new RationalNumber(1, 6));
			res.put(new int[] { 2, 2, 2 }, new RationalNumber(1, 6));
			res.put(new int[] { 0, 1, 2 }, new RationalNumber(1, 6));
			res.put(new int[] { 1, 1, 2 }, new RationalNumber(1, 6));
			res.put(new int[] { 2, 1, 2 }, new RationalNumber(1, 6));
			break;
		case cream:
			res.put(new int[] { 0, 0, 2 }, new RationalNumber(1, 3));
			res.put(new int[] { 1, 0, 2 }, new RationalNumber(1, 3));
			res.put(new int[] { 2, 0, 2 }, new RationalNumber(1, 3));
			break;
		case blackRed:
			res.put(new int[] { 1, 2, 1 }, new RationalNumber(1, 4));
			res.put(new int[] { 2, 2, 1 }, new RationalNumber(1, 4));
			res.put(new int[] { 1, 1, 1 }, new RationalNumber(1, 4));
			res.put(new int[] { 2, 1, 1 }, new RationalNumber(1, 4));
			break;
		case blueCream:
			res.put(new int[] { 1, 0, 1 }, new RationalNumber(1, 2));
			res.put(new int[] { 2, 0, 1 }, new RationalNumber(1, 2));
			break;
		case chocolateRed:
			res.put(new int[] { 0, 2, 1 }, new RationalNumber(1, 2));
			res.put(new int[] { 0, 1, 1 }, new RationalNumber(1, 2));
			break;
		case lilacCream:
			res.put(new int[] { 0, 0, 1 }, new RationalNumber(1, 1));
			break;
		}

		return res;

	}

	private static class CatCoatProbability implements Comparable<CatCoatProbability> {

		public final String color;
		public final double probability;

		public CatCoatProbability(String color, double probability) {
			this.color = color;
			this.probability = probability;
		}

		@Override
		public int compareTo(CatCoatProbability arg0) {
			int c = Double.compare(probability, arg0.probability);
			if (c == 0)
				return color.compareTo(arg0.color);
			return -c;
		}

	}
}
