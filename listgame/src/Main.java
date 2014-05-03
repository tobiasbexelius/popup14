import java.math.BigInteger;

public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		long n = io.getLong();
		PollardRho pollard = new PollardRho();
		int k = pollard.factorize(BigInteger.valueOf(n)).size();
		io.println(k);
		io.flush();
		io.close();
	}

}
