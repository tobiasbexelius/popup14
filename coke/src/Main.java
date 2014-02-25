public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		int problemInstances = io.getInt();
		int i = 0;
		while (i < problemInstances) {
			System.out.println(getMinCoins(io.getInt(), io.getInt(), io.getInt(), io.getInt()));
			i++;
		}
		io.close();
	}

	private static int getMinCoins(int cokes, int ones, int fives, int tens) {
		Coins coins = new Coins(ones, fives, tens);
		int coinsUsed = 0;
		while (cokes > 0) {
			int onesToInsert = 0, fivesToInsert = 0, tensToInsert = 0;
			if (coins.getTens() > 0) {
				tensToInsert = 1;
				if (coins.getTens() + coins.getFives() < cokes && coins.getOnes() > 2) {
					onesToInsert = 3;
				}
			} else if (coins.getFives() > 0) {
				if (coins.getFives() > 1 && coins.getFives() > cokes) {
					fivesToInsert = 2;
				} else {
					fivesToInsert = 1;
					onesToInsert = 3;
				}
			} else {
				onesToInsert = 8;
			}
			buyACoke(coins, new Coins(onesToInsert, fivesToInsert, tensToInsert));
			coinsUsed += onesToInsert + fivesToInsert + tensToInsert;
			cokes--;
		}
		return coinsUsed;
	}

	private static void buyACoke(Coins coins, Coins coinsToInsert) {
		Coins exchange = calculateExchange(coinsToInsert.getValue());
		coins.setOnes(coins.getOnes() - coinsToInsert.getOnes() + exchange.getOnes());
		coins.setFives(coins.getFives() - coinsToInsert.getFives() + exchange.getFives());
		coins.setTens(coins.getTens() - coinsToInsert.getTens() + exchange.getTens());
	}

	private static Coins calculateExchange(int valueOfInsertedCoins) {
		int exchangeLeft = valueOfInsertedCoins - 8;
		int tens = exchangeLeft / 10;
		exchangeLeft -= 10 * tens;
		int fives = exchangeLeft / 5;
		exchangeLeft -= 5 * fives;
		int ones = exchangeLeft;
		return new Coins(ones, fives, tens);
	}
}
