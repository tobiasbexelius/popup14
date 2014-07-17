import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	static Map<String, Double> directions = new HashMap<String, Double>() {
		private static final long serialVersionUID = 1L;

		{ // SWbS SW SWbW WSW WbS W WbN WNW NWbW NW NWbN
			put("N", 0.0);
			put("NbE", 11.25);
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
			put("NWbN", 326.25);
			put("NNW", 337.5);
			put("NbW", 348.75);

		}
	};

	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat("0.00");
		Kattio io = new Kattio(System.in);
		while (io.hasMoreTokens()) {
			int n = io.getInt();
			if (n == 0)
				break;

			List<LineSegment> path = new ArrayList<>();
			Point current = new Point(0, 0);

			for (int i = 0; i < n; i++) {
				String dir = io.getWord();
				int steps = io.getInt();
				double x = Math.sin(Math.toRadians(90 - directions.get(dir)));
				double y = Math.cos(Math.toRadians(90 - directions.get(dir)));
				Point next = new Point(current.getX() + steps * x, current.getY() + steps * y);
				path.add(new LineSegment(current, next));
				current = next;
			}

			double d = Math.toRadians(io.getDouble());

			double x = current.getX() * Math.cos(d) - current.getY() * Math.sin(d);
			double y = current.getY() * Math.cos(d) + current.getX() * Math.sin(d);

			Point realX = new Point(x, y);
			double min = Double.POSITIVE_INFINITY;
			for (int i = 0; i < path.size(); i++) {
				double dist = path.get(i).distanceTo(realX);
				if (dist < min)
					min = dist;
			}

			io.println(df.format(min));

		}
		io.flush();
		io.close();
	}
}
