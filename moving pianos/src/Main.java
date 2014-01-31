import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main {

	public static final int DAYS = 100;
	private static final int WORKERS_PER_PIANO = 2;
	public static final String FINE = "fine";
	public static final String WEEKEND_WORK = "weekend work";
	public static final String TROUBLE = "serious trouble";

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int problemInstances = Integer.parseInt(reader.readLine());
		for (int i = 0; i < problemInstances; i++) {
			String[] pianosAndWorkers = reader.readLine().split(" ");
			int numPianos = Integer.parseInt(pianosAndWorkers[0]);
			int numWorkers = Integer.parseInt(pianosAndWorkers[1]) / WORKERS_PER_PIANO;
			List<Piano> pianoQueue = new ArrayList<Piano>();
			for (int j = 0; j < numPianos; j++) {
				String[] pianoInfo = reader.readLine().split(" ");
				pianoQueue.add(new Piano(Integer.parseInt(pianoInfo[0]), Integer.parseInt(pianoInfo[1])));
			}
			System.out.println(getScheduleDiagnosis(numPianos, numWorkers, pianoQueue));
		}
		reader.close();
	}

	private static String getScheduleDiagnosis(int numPianos, int numWorkers, List<Piano> pianos) {
		String status = FINE;
		Queue<Piano> pianoQueue = new PriorityQueue<Piano>();
		pianoQueue.addAll(pianos);
		boolean weekdaysOnlyPossible = isSchedulePossible(numPianos, numWorkers, pianoQueue, false);
		if (weekdaysOnlyPossible)
			return FINE;
		pianoQueue.clear();
		pianoQueue.addAll(pianos);
		boolean weekendsAllowedPOssible = isSchedulePossible(numPianos, numWorkers, pianoQueue, true);
		if (weekendsAllowedPOssible)
			return WEEKEND_WORK;
		return TROUBLE;
	}

	private static boolean isSchedulePossible(int numPianos, int numWorkers, Queue<Piano> pianoQueue, boolean weekendsAllowed) {
		for (int currentDay = 1; currentDay <= DAYS && !pianoQueue.isEmpty(); currentDay++) {
			if (!weekendsAllowed && isDayWeekend(currentDay))
				continue;
			for (int workersUsed = 0; workersUsed < numWorkers && !pianoQueue.isEmpty(); workersUsed++) {
				Piano piano = getPianoToMove(pianoQueue, currentDay);
				if (piano == null)
					break;
				if (piano.getLastDay() < currentDay)
					return false;
			}
		}
		if (pianoQueue.isEmpty()) {
			return true;
		}

		return false;
	}

	private static Piano getPianoToMove(Queue<Piano> pianoQueue, int currentDay) {
		Piano pianoToMove = null;
		List<Piano> tooEarlyToMove = new ArrayList<Piano>();
		while (!pianoQueue.isEmpty()) {
			pianoToMove = pianoQueue.poll();
			if (pianoToMove.getFirstDay() <= currentDay)
				break;
			else
				tooEarlyToMove.add(pianoToMove);
		}
		pianoQueue.addAll(tooEarlyToMove);
		return pianoToMove;
	}

	public static boolean isDayWeekend(int n) {
		return n % 7 == 6 || n % 7 == 0;
	}
}
