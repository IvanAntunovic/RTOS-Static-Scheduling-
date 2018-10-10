import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scheduler {

	public static final int EDF = 0;
	public static final int RMS = 1;

	private List<Task> readyTaskList;
	private List<Task> waitingTaskList;
	private int schedulingAlgorithm;

	private int globalTickTime;
	private int rms;
	private int scheduleTickTime;

	public Scheduler(List<Task> readyTaskList, int schedulingAlgorithm) {
		super();
		this.readyTaskList = readyTaskList;
		this.waitingTaskList = new ArrayList<Task>();
		this.schedulingAlgorithm = schedulingAlgorithm;

		this.globalTickTime = 0;
		this.scheduleTickTime = 0;
		this.rms = calcLCM(readyTaskList);

		// TODO: Check whether the list is already sorted
		Collections.sort(this.readyTaskList);

	}

	public void schedule() {

		switch (this.schedulingAlgorithm) {
			case RMS:
				this.rmsScheduling();
				break;
			
			case EDF:
				this.edfScheduling();
				break;
		}
	}
	
	private void edfScheduling() {
		
		
	}

	private boolean firstScheduleFlag = true;
	private void rmsScheduling() {
		if (this.isRMSExceeded() && !firstScheduleFlag) {

			firstScheduleFlag = false;
			this.scheduleTickTime = 0;
			if (this.readyTaskList != null && this.readyTaskList.size() > 0) {
				// move tasks from ready to waiting list, sorted with priorities
				this.moveTasksFromReadyToWaitingList();
			}
			this.moveTasksFromWaitingToReadyList();

		}

		// Check whether task needs to be added to ready list
		for (int index = 0; index < this.waitingTaskList.size(); ++index) {

			Task task = this.waitingTaskList.get(index);
			if (this.scheduleTickTime % task.getPeriod() == 0) {
				this.addTaskToReadyList(this.waitingTaskList.get(index));
				this.waitingTaskList.remove(index);
				index--;
			}

		}

		this.scheduleTask();

		this.globalTickTime++;
		this.scheduleTickTime++;
	}

	private void scheduleTask() {

		final int TASK_TO_SCHEDULE_INDEX = 0;
		if (this.readyTaskList != null && this.readyTaskList.size() > 0) {

			Task taskToSchedule = this.readyTaskList.get(TASK_TO_SCHEDULE_INDEX);

			System.out.println("Scheduling Task " + taskToSchedule.getId() + " at time: " + this.globalTickTime);
			taskToSchedule.compute();
			if (this.isTaskFullyExecuted(taskToSchedule)) {

				// Reset remaining computation time since task has been fully scheduled
				taskToSchedule.setRemainingComputationTime(taskToSchedule.getComputationTime());
				this.addTaskToWaitingList(taskToSchedule);
				this.readyTaskList.remove(TASK_TO_SCHEDULE_INDEX);

			}

		} else {
			System.out.println("No task has been scheduled at time: " + this.globalTickTime);
		}

	}

	private boolean isRMSExceeded() {
		return (this.globalTickTime % this.rms) == 0 ? true : false;
	}

	private boolean isTaskFullyExecuted(Task task) {
		return task.getRemainingComputationTime() == 0 ? true : false;
	}

	private void moveTasksFromWaitingToReadyList() {

		if (this.readyTaskList == null || this.waitingTaskList == null) {
			return;
		}

		for (int i = 0; i < this.waitingTaskList.size(); ++i) {
			this.readyTaskList.add(this.waitingTaskList.get(i));
			this.waitingTaskList.remove(i);
		}
	}

	private void moveTasksFromReadyToWaitingList() {

		if (this.readyTaskList == null || this.waitingTaskList == null) {
			return;
		}

		for (int i = 0; i < this.readyTaskList.size(); ++i) {
			Task readyTask = null;
			int startWaitingTaskListSize = this.waitingTaskList.size();
			for (int j = 0; j < this.waitingTaskList.size(); ++j) {
				readyTask = this.readyTaskList.get(0);
				if (readyTask.getPeriod() <= this.waitingTaskList.get(j).getPeriod()) {
					this.waitingTaskList.add(readyTask);
					break;
				}
			}

			// Check whether we managed to add new element, and if not add it to the end of
			// the list
			if (startWaitingTaskListSize == this.waitingTaskList.size()) {
				this.waitingTaskList.add(readyTask);
			}
		}

	}

	// Insert task inside the list at the place w.r.t. to its period, smaller the
	// period lower the index is (lower priority of the task)
	private void addTaskToReadyList(Task readyTask) {

		if (this.readyTaskList == null) {
			return;
		}

		if (this.readyTaskList.size() == 0) {
			this.readyTaskList.add(readyTask);
			return;
		}

		int oldReadyTaskListSize = this.readyTaskList.size();
		for (int index = 0; index < this.readyTaskList.size(); ++index) {
			Task task = this.readyTaskList.get(index);
			if (readyTask.getPeriod() <= task.getPeriod()) {
				this.readyTaskList.add(index, readyTask);
				return;
			}
		}

		if (oldReadyTaskListSize == this.readyTaskList.size()) {
			this.readyTaskList.add(readyTask);
		}

	}

	// Insert task at place w.r.t. to its period, smaller the period lower the index
	// is (lower priority of the task
	private void addTaskToWaitingList(Task waitingTask) {
		if (this.waitingTaskList == null) {
			return;
		}

		if (this.waitingTaskList.size() == 0) {
			this.waitingTaskList.add(waitingTask);
			return;
		}

		int oldWaitingTaskListSize = this.waitingTaskList.size();
		for (int index = 0; index < this.waitingTaskList.size(); ++index) {
			if (waitingTask.getPeriod() <= this.waitingTaskList.get(index).getPeriod()) {
				this.waitingTaskList.add(index, waitingTask);
				break;
			}
		}

		if (oldWaitingTaskListSize == this.waitingTaskList.size()) {
			this.waitingTaskList.add(waitingTask);
		}
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
