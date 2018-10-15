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

	public static void rmsScheduling() {
		
		List<Task> readyTaskList = new ArrayList<Task>();
		readyTaskList.add(new Task(1, 4, 1));
		readyTaskList.add(new Task(2, 6, 2));
		readyTaskList.add(new Task(3, 12, 3));
		readyTaskList.add(new Task(4, 24, 4));
		
		scheduler = new Scheduler(readyTaskList, Scheduler.RMS);
	}
	
	public static void edfScheduling() {
		
		List<Task> readyTaskList = new ArrayList<Task>();
		readyTaskList.add(new Task(2, 5, 2, 3));
		readyTaskList.add(new Task(1, 20, 3, 6));
		
		readyTaskList.add(new Task(3, 10, 2, 7));
		
		scheduler = new Scheduler(readyTaskList, Scheduler.EDF);
	}
	
	public static void main(String[] args) {

		rmsScheduling();
		
		startTimer();
	}

}
