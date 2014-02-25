import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		int n = io.getInt();
		List<String> dictionary = new ArrayList<String>(n);
		for (int i = 0; i < n; i++)
			dictionary.add(io.getWord());
		int q = io.getInt();
		SMSTyper typer = new SMSTyper(dictionary);
		for (int i = 0; i < q; i++)
			io.println(typer.getOptimalKeypresses(io.getWord()));
		io.flush();
		io.close();
	}
}
