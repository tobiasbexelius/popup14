import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			String[] line = reader.readLine().split(" ");
			int numJackCDs = Integer.parseInt(line[0]);
			int numJillCDs = Integer.parseInt(line[1]);
			List<Integer> jackCDs = new ArrayList<Integer>();
			List<Integer> jillCDs = new ArrayList<Integer>();
			if (numJillCDs == 0 && numJackCDs == 0)
				break;
			for (int i = 0; i < numJackCDs; i++) {
				jackCDs.add(Integer.parseInt(reader.readLine()));
			}
			for (int i = 0; i < numJillCDs; i++) {
				jillCDs.add(Integer.parseInt(reader.readLine()));
			}
			int commonCDs = 0;
			int jackPointer = 0;
			int jillPointer = 0;
			while (jackPointer < jackCDs.size() && jillPointer < jillCDs.size()) {
				Integer jack = jackCDs.get(jackPointer);
				Integer jill = jillCDs.get(jillPointer);
				if (jack.equals(jill)) {
					commonCDs++;
					jackPointer++;
					jillPointer++;
				} else if (jack > jill) {
					jillPointer++;
				} else {
					jackPointer++;
				}
			}
			System.out.println(commonCDs);
		}
		reader.close();
	}
}