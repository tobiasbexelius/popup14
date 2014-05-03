public class Main {

	public static final double sin90 = 1.0;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		double h = io.getInt();
		double v = io.getInt();
		double length = sin90 * h / Math.sin(Math.toRadians(v));
		io.println((int) Math.ceil(length));
		io.flush();
		io.close();
	}
}
