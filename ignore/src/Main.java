public class Main {

	public static final char[] convertertionArray = new char[] { '0', '1', '2', '5', '6', '8', '9' };

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		while (io.hasMoreTokens()) {
			int k = io.getInt();
			String baseSevenRep = Integer.toString(k, 7);
			String correctRep = convertNumbers(baseSevenRep);
			String result = turnUpsideDown(correctRep);
			io.println(result);
		}
		io.flush();
		io.close();
	}

	private static String convertNumbers(String number) {
		StringBuilder sb = new StringBuilder();
		for (char c : number.toCharArray()) {
			sb.append(convertertionArray[Character.getNumericValue(c)]);
		}
		return sb.toString();
	}

	private static String turnUpsideDown(String number) {
		String s = new StringBuilder(number).reverse().toString();
		s = s.replace('6', 'A');
		s = s.replace('9', '6');
		s = s.replace('A', '9');
		return s;
	}

}
