import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

	private static Scheduler scheduler;

	public static void startTimer() {

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				scheduler.schedule();
			}
		}, 0, 1000);

	}

	public static void main(String[] args) {

		List<Task> readyTaskList = new ArrayList<Task>();

		readyTaskList.add(new Task(1, 20, 3));
		readyTaskList.add(new Task(2, 5, 2));
		readyTaskList.add(new Task(3, 10, 2));

		scheduler = new Scheduler(readyTaskList, Scheduler.RMS);

		startTimer();
	}

}
