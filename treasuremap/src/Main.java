import java.util.HashMap;
import java.util.Map;

public class Main {

	Map<String, Double> directions = new HashMap<String, Double>() {
		{
			put("N", 0.0);
			put("NbN", 11.25);
			put("NNE", 22.5);
			put("NEbN", 33.75);
			put("NE", 45.0);
			put("NEbE", 56.25);
			put("ENE", 67.5);
			put("EbN", 78.75);
			put("E", 90.0);
			put("EbS", 101.25);
			put("ESE", 112.5);
			put("SEbE", 123.75);
			put("SE", 135.0);
			put("SEbS", 146.25);
			put("SSE", 157.5);
			put("SbE", 168.75);
			put("S", 180.0);
			put("SbW", 191.25);
			put("SSW", 202.5);
			put("SWbS", 213.75);
			put("SW", 225.0);
			put("SWbW", 236.25);
			put("WSW", 247.5);
			put("WbS", 258.75);
			put("W", 270.0);
			put("WbN", 281.25);
			put("WNW", 292.5);
			put("NWbW", 303.75);
			put("NW", 315.0);
			put("NWN", 326.25);
			put("NNW", 337.5);
			put("NbW", 348.75);

		}
	};

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		while (io.hasMoreTokens()) {
			int n = io.getInt();
			if (n == 0)
				break;

			for (int i = 0; i < n; i++) {
				String dir = io.getWord();
				int steps = io.getInt();
			}

			double d = io.getDouble();

		}
		io.flush();
		io.close();
	}
}
