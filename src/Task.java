
public class Task implements Comparable {

	private int id;
	private int period;
	private int computationTime;
	private int remainingComputationTime;

	public Task(int id, int period, int computationTime) {
		super();
		this.id = id;
		this.period = period;
		this.computationTime = computationTime;
		this.remainingComputationTime = computationTime;
	}

	@Override
	public int compareTo(Object obj) {

		if (!(obj instanceof Task)) {
			return 0;
		}

		Task task = (Task) obj;
		return this.period - task.getPeriod();
	}

	public void compute() {
		this.remainingComputationTime--;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getComputationTime() {
		return computationTime;
	}

	public void setComputationTime(int computationTime) {
		this.computationTime = computationTime;
	}

	public int getRemainingComputationTime() {
		return remainingComputationTime;
	}

	public void setRemainingComputationTime(int remainingComputationTime) {
		this.remainingComputationTime = remainingComputationTime;
	}

}
