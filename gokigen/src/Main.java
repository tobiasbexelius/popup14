
public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		int n = io.getInt();
		char[][] board = new char[n + 1][n + 1];
		for (int i = 0; i < n + 1; i++)
			board[i] = io.getWord().toCharArray();

		char[][] solution = new Gokigen(board, n).solve();

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				io.print(solution[i][j]);
			}
			io.println();
		}

		io.flush();
		io.close();
	}

}
