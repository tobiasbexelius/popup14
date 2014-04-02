import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader in = new BufferedReader(new InputStreamReader(new
		// FileInputStream("bin/test")));

		int tests = Integer.parseInt(in.readLine());
		for (int i = 0; i < tests; i++) {
			char[][] board = new char[KnightsInFen.ROWS][KnightsInFen.COLS];
			for (int j = 0; j < KnightsInFen.ROWS; j++) {
				board[j] = in.readLine().toCharArray();
			}
			int minimumMoves = new KnightsInFen(board).getMinimumMoves();
			if (minimumMoves == KnightsInFen.INFINITY)
				System.out.println("Unsolvable in less than " + (KnightsInFen.MAX_DEPTH + 1) + " move(s).");
			else
				System.out.println("Solvable in " + minimumMoves + " move(s).");
		}
	}
}
