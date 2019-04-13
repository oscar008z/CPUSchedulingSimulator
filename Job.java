
public class Job {
	private String jobName;
	private int jobLength;
	private int currentJobLength;
	private int jobPriority;
	private int finalPriority;
	private long entryTime;
	private long endTime;
	private long waitTime;
	private long lastExecutTime;	//the time when an element was last executed need to be considered when two elements with same priority are compared 
	private boolean flagExecuted;
	
	public Job(String jobName, int jobLength, int jobPriority) {
		this.jobName = jobName;
		this.jobLength = jobLength;
		this.currentJobLength = jobLength;
		this.jobPriority = jobPriority;
		this.finalPriority = jobPriority;
		this.entryTime = 0;
		this.endTime = 0;
		this.waitTime = 0;
		this.lastExecutTime = 0;
		this.flagExecuted = false;
	}
	
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public int getJobLength() {
		return jobLength;
	}
	public void setJobLength(int jobLength) {
		this.jobLength = jobLength;
	}
	public int getCurrentJobLength() {
		return currentJobLength;
	}
	public void setCurrentJobLength(int currentJobLength) {
		this.currentJobLength = currentJobLength;
	}
	public int getJobPriority() {
		return jobPriority;
	}
	public void setJobPriority(int jobPriority) {
		this.jobPriority = jobPriority;
	}
	public int getFinalPriority() {
		return finalPriority;
	}
	public void setFinalPriority(int finalPriority) {
		this.finalPriority = finalPriority;
	}
	public long getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(long entryTime) {
		this.entryTime = entryTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public long getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(long waitTime) {
		this.waitTime = waitTime;
	}
	public long getLastExecutTime() {
		return lastExecutTime;
	}
	public void setLastExecutTime(long lastExecutTime) {
		this.lastExecutTime = lastExecutTime;
	}
	public boolean isFlagExecuted() {
		return flagExecuted;
	}
	public void setFlagExecuted(boolean flagExecuted) {
		this.flagExecuted = flagExecuted;
	}
	public String toString() {
		String str = "Here are the information of this array of job: "+System.lineSeparator()+ "Name: " +this.jobName+System.lineSeparator()+ "Length: " +this.jobLength+System.lineSeparator()+ "final Priority: " +this.finalPriority+System.lineSeparator()+ "Current Length: " +this.currentJobLength+System.lineSeparator()+ "Entry Time:  " +this.entryTime+System.lineSeparator()+ "End Time:  " +this.endTime;
		return str;
	}
	
}
