import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scheduler {

	public static final int EDF = 0;
	public static final int RMS = 1;

	private List<Task> readyTaskList;
	private List<Task> waitingTaskList;
	private int schedulingAlgorithm;

	private int tickTime;
	private int rms;

	public Scheduler(List<Task> readyTaskList, int schedulingAlgorithm) {
		super();
		this.readyTaskList = readyTaskList;
		this.waitingTaskList = new ArrayList<Task>();
		this.schedulingAlgorithm = schedulingAlgorithm;

		this.tickTime = 0;
		this.rms = calcLCM(readyTaskList);

		// TODO: Check whether the list is already sorted
		if (this.schedulingAlgorithm == RMS) {
			Collections.sort(this.readyTaskList);
		} else if (this.schedulingAlgorithm == EDF) {
			// TODO: Sort tasks w.r.t. deadlines
		}
	}

	public void schedule() {
		
		if (this.readyTaskList == null 	|| this.waitingTaskList == null) {
			return;
		}

		// Check whether task needs to be added to ready list
		this.moveFromWaitingToReadyTaskListOnPeriod();
		this.scheduleTask();
		
		this.tickTime++;
		//TODO: If RMS exceeded and not all tasks have been scheduled, report the error 
	}
	
	private void scheduleTask() {

		final int TASK_TO_SCHEDULE_INDEX = 0;
		if ( this.readyTaskList.size() > 0) {

			Task taskToSchedule = this.readyTaskList.get(TASK_TO_SCHEDULE_INDEX);

			System.out.println("Scheduling Task " + taskToSchedule.getId() + " at time: " + this.tickTime);
			taskToSchedule.compute();
			if (this.isTaskFullyExecuted(taskToSchedule)) {

				// Reset remaining computation time since task has been fully scheduled
				taskToSchedule.setRemainingComputationTime(taskToSchedule.getComputationTime());
				this.addTaskToWaitingList(taskToSchedule);
				this.readyTaskList.remove(TASK_TO_SCHEDULE_INDEX);

			}

		} else {
			System.out.println("No task has been scheduled at time: " + this.tickTime);
		}

	}
	
	private void moveFromWaitingToReadyTaskListOnPeriod() {
		for (int index = 0; index < this.waitingTaskList.size(); ++index) {

			Task task = this.waitingTaskList.get(index);
			if ( this.isNewPeriod(task) ) {
				
				this.addTaskToReadyList( task );
				this.waitingTaskList.remove(index);
				index--;
			}
		}
	}
	
	// Insert task inside the list at the place w.r.t. to its period, smaller the
	// period lower the index is (lower priority of the task)
	private void addTaskToReadyList(Task task) {

		if (this.readyTaskList.size() == 0) {
			this.readyTaskList.add(task);
			return;
		}

		int oldReadyTaskListSize = this.readyTaskList.size();
		for (int index = 0; index < this.readyTaskList.size(); ++index) {
			Task readyTask = this.readyTaskList.get(index);
			if (this.schedulingAlgorithm == RMS && task.getPeriod() <= readyTask.getPeriod() ||
				this.schedulingAlgorithm == EDF && task.getDeadLine() <= readyTask.getDeadLine() ) {
				
				this.readyTaskList.add(index, task);
				return;
			}

		}

		if (oldReadyTaskListSize == this.readyTaskList.size()) {
			this.readyTaskList.add(task);
		}

	}

	// Insert task at place w.r.t. to its period, smaller the period lower the index
	// is (lower priority of the task
	private void addTaskToWaitingList(Task task) {

		if ( this.schedulingAlgorithm == EDF ) {
			task.setDeadLine( this.tickTime + task.getPeriod() );
		}
		
		if (this.waitingTaskList.size() == 0) {
			this.waitingTaskList.add(task);
			return;
		}

		int oldWaitingTaskListSize = this.waitingTaskList.size();
		for (int index = 0; index < this.waitingTaskList.size(); ++index) {
				
			if ( this.schedulingAlgorithm == RMS && task.getPeriod() <= this.waitingTaskList.get(index).getPeriod()) {
				this.waitingTaskList.add(index, task);
				break;
				
			} else if ( this.schedulingAlgorithm == EDF && task.getDeadLine() <= this.waitingTaskList.get(index).getDeadLine() ) {		
				this.waitingTaskList.add(index, task);
				break;
			}
		}

		if (oldWaitingTaskListSize == this.waitingTaskList.size()) {
			this.waitingTaskList.add(task);
		}
	}
	
	private boolean isTaskFullyExecuted(Task task) {
		return task.getRemainingComputationTime() == 0 ? true : false;
	}
	
	private boolean isNewPeriod(Task task) {
		return this.tickTime % task.getPeriod() == 0 ? true : false;
	}

	public static int calcLCM(List<Task> taskList) {

		int lcm = taskList.get(0).getPeriod();
		for (boolean flag = true; flag;) {
			for (Task x : taskList) {
				if (lcm % x.getPeriod() != 0) {
					flag = true;
					break;
				}
				flag = false;
			}
			lcm = flag ? (lcm + 1) : lcm;
		}

		return lcm;
	}

	public List<Task> getReadyTaskList() {
		return readyTaskList;
	}

	public void setReadyTaskList(List<Task> readyTaskList) {
		this.readyTaskList = readyTaskList;
	}

	public List<Task> getWaitingTaskList() {
		return waitingTaskList;
	}

	public void setWaitingTaskList(List<Task> waitingTaskList) {
		this.waitingTaskList = waitingTaskList;
	}

}
