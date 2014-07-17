import java.text.DecimalFormat;

public class Main {

	public static void main(String args[]) {
		DecimalFormat df = new DecimalFormat("0.00");
		Kattio io = new Kattio(System.in);
		while (io.hasMoreTokens()) {
			int n = io.getInt();
			double[] lat = new double[n];
			double[] lon = new double[n];

			for (int i = 0; i < n; i++) {
				lat[i] = io.getDouble();
				lon[i] = io.getDouble();
			}

			double minLat = Double.POSITIVE_INFINITY;
			double minLon = Double.POSITIVE_INFINITY;
			double minDist = Double.POSITIVE_INFINITY;

			for (int i = 0; i < n; i++) {
				double lat1 = lat[i];
				double lon1 = lon[i];
				double maxDist = Double.NEGATIVE_INFINITY;
				for (int j = 0; j < n; j++) {
					double lat2 = lat[j];
					double lon2 = lon[j];
					double dist = haversineDistance(lat1, lon1, lat2, lon2);
					if (maxDist < dist) {
						maxDist = dist;
					}
				}

				if (maxDist <= minDist) {
					minLat = lat1;
					minLon = lon1;
					minDist = maxDist;
				}

			}

			io.println(df.format(minLat) + " " + df.format(minLon));

		}
		io.flush();
		io.close();
	}

	private static double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
		double rLat1 = Math.toRadians(lat1);
		double rLon1 = Math.toRadians(lon1);
		double rLat2 = Math.toRadians(lat2);
		double rLon2 = Math.toRadians(lon2);

		double rLat = rLat2 - rLat1;
		double rLon = rLon2 - rLon1;

		double a = Math.sin(rLat / 2) * Math.sin(rLat / 2) + Math.cos(rLat1) * Math.cos(rLat2) * Math.sin(rLon / 2) * Math.sin(rLon / 2);
		return 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	}
}
