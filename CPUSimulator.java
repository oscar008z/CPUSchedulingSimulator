
public class CPUSimulator<E> {
	private long currentTime;
	private int countProcess;
	private long accumulatedWaitingTime;
	private double averageWaitingTime;
	private int countPriorityAdjust;
	private long accturalRunTime;
	private int countJobCompleted;	//每完成一个job，自增1至30个，检索pq中工作，更新优先级
	private arrayPQ arrPQ;
	private heapPQ arrHeapPQ;
	private E[] jobsInputArray;
	//private int jobRemainCount;
	
	public CPUSimulator(){
		this.currentTime = 0;
		this.countJobCompleted = 0;
		this.countProcess = 0;
		this.accumulatedWaitingTime = 0;
		this.averageWaitingTime = 0;
		this.countPriorityAdjust = 0;
		this.accturalRunTime = 0;
		this.arrPQ = null;
		this.arrHeapPQ = null;
		this.jobsInputArray = null;
		//this.jobRemainCount = 0;
	}
	
	public CPUSimulator(E[] jobsInputArra){
		this.currentTime = 0;
		this.countJobCompleted = 0;
		this.countProcess = 0;
		this.accumulatedWaitingTime = 0;
		this.averageWaitingTime = 0;
		this.countPriorityAdjust = 0;
		this.accturalRunTime = 0;
		this.arrPQ = new arrayPQ(jobsInputArra.length);
		this.arrHeapPQ = new heapPQ(jobsInputArra.length);
		this.jobsInputArray = jobsInputArra;
		//this.jobRemainCount = jobsInputArra.length;
	}
	
	public long getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(long currentTime) {
		this.currentTime = currentTime;
	}

	public int getCountProcess() {
		return countProcess;
	}

	public void setCountProcess(int countProcess) {
		this.countProcess = countProcess;
	}

	public long getAccumulatedWaitingTime() {
		return accumulatedWaitingTime;
	}

	public void setAccumulatedWaitingTime(int accumulatedWaitingTime) {
		this.accumulatedWaitingTime = accumulatedWaitingTime;
	}

	public double getAverageWaitingTime() {
		return averageWaitingTime;
	}

	public void setAverageWaitingTime(double averageWaitingTime) {
		this.averageWaitingTime = averageWaitingTime;
	}

	public int getCountPriorityAdjust() {
		return countPriorityAdjust;
	}

	public void setCountPriorityAdjust(int countPriorityAdjust) {
		this.countPriorityAdjust = countPriorityAdjust;
	}

	public long getAccturalRunTime() {
		return accturalRunTime;
	}

	public void setAccturalRunTime(long accturalRunTime) {
		this.accturalRunTime = accturalRunTime;
	}

	public int getCountJobCompleted() {
		return countJobCompleted;
	}

	public void setCountJobCompleted(int countJobCompleted) {
		this.countJobCompleted = countJobCompleted;
	}

	public arrayPQ getArrPQ() {
		return arrPQ;
	}
	public heapPQ getArrHeapPQ() {
		return arrHeapPQ;
	}

	public void setArrPQ(arrayPQ arrPQ) {
		this.arrPQ = arrPQ;
	}
	public void setArrHeapPQ(heapPQ arrHeapPQ) {
		this.arrHeapPQ = arrHeapPQ;
	}

	public E[] getJobsInputArray() {
		return jobsInputArray;
	}

	public void setJobsInputArray(E[] jobsInputArray) {
		this.jobsInputArray = jobsInputArray;
	}
	/**
	 * The implementation of simulator on array-based PQ
	 * */
	public void loadProcessArrayPQ() {
		long startTime = System.currentTimeMillis();
		int n = 1;
		for(int i=0; i<this.jobsInputArray.length; i++) {
			System.out.println(n++ + ". waiting patiently, the data are being loaded.");
			this.currentTime++;
			if(this.jobsInputArray[i].getClass().getName().equals("Job")) {
				Job item = (Job) this.jobsInputArray[i];
				this.arrPQ.add(item);
				item.setEntryTime(this.currentTime);
			}
			else {
				E item = this.jobsInputArray[i];
				this.arrPQ.add(item);
			}
		}
		long endTime = System.currentTimeMillis();
		this.accturalRunTime += (endTime - startTime);
	}
	
	public String jobProcessArrayPQ() {
		long startTime = System.currentTimeMillis();
		String strInfo = "";
		String strInfo1 = "";
		int processCount = 1;
		while(this.arrPQ.isEmpty()!=true) {
			//出队操作，选择最高优先级，打印过程，更新相关工作状态
			this.currentTime++;
			Object e = this.arrPQ.removeMin();
			if(e.getClass().getName().equals("Job")) {
				Job item = (Job) e;
				
				item.setCurrentJobLength(item.getCurrentJobLength()-1);
				item.setLastExecutTime(this.currentTime);
				item.setFlagExecuted(true);
				//strInfo += "Now executing " + item.getJobName() + ". Job length: " + item.getJobLength() + " cycles; Current remaing length: " + item.getCurrentJobLength() + " cycles; Initial priority: " + item.getJobPriority() + "; Current priority: " + item.getFinalPriority() + System.lineSeparator() + "";
				strInfo1 = processCount +  " :Now executing " + item.getJobName() + ". Job length: " + item.getJobLength() + " cycles; Current remaing length: " + item.getCurrentJobLength() + " cycles; Initial priority: " + item.getJobPriority() + "; Current priority: " + item.getFinalPriority() + System.lineSeparator() + "";
				System.out.println(strInfo1);
				processCount++;
				if(item.getCurrentJobLength()>0) {
					//如工作未完成，调用入队操作，更新相关工作状态
					this.arrPQ.add(item);
				}
				else {
					item.setEndTime(this.currentTime);
					item.setWaitTime(item.getEndTime()-item.getEntryTime()-item.getJobLength());
					this.accumulatedWaitingTime += item.getWaitTime();
					this.countProcess++;
					this.countJobCompleted++;
					//this.jobRemainCount--;
					if(this.countJobCompleted==30) {
						//调用优先级检测方法;
						this.currentTime++;
						this.priorityAdjust();
						this.countPriorityAdjust++;
						this.countJobCompleted = 0;
					}
				}
			}
			else {
				E item = (E) e;
			}
		}
		//如工作完成(结束循环)，更新类信息，如完成工作数量，平均等待时间等类属性，判断是否调用队列其他成员优先级
		this.averageWaitingTime = (double) this.accumulatedWaitingTime / this.countProcess;
		long endTime = System.currentTimeMillis();
		this.accturalRunTime += (endTime - startTime);
		strInfo += "Sorted List Priority Queue : " + System.lineSeparator() + "Current system time (cycles): " + this.currentTime + ". Total number of jobs executed: " + this.countProcess + " jobs" + System.lineSeparator() + "Average process waiting time: " + this.averageWaitingTime + " cycles" + System.lineSeparator() + "Total number of priority changes: " + this.countPriorityAdjust + ". Actual system time needed to execute all hobs: " + this.accturalRunTime + " ms";
		return strInfo;
	}
	
	public void priorityAdjust() {
		int tempIdx = this.arrPQ.getFront();
		Job tempItem = (Job) this.arrPQ.getArrayPQ()[tempIdx];
		for(int i=this.arrPQ.getFront(); i<(this.arrPQ.getFront()+this.arrPQ.getSize()); i++) {
			Job item = (Job) this.arrPQ.getArrayPQ()[i % this.arrPQ.getArrLength()];
			if(item.isFlagExecuted()==false && item.getEntryTime()<tempItem.getEntryTime()) {
				tempIdx = i % this.arrPQ.getArrLength();
				tempItem = item;
			}
		}
		if(tempIdx!=this.arrPQ.getFront() && tempItem!=null) {
			this.arrPQ.swap(this.arrPQ.getFront(), tempIdx);
			tempItem.setFinalPriority(1);
		}
		
	}
	
	/**
	 * The implementation of simulator on heap-based PQ
	 * */
	
	public void loadProcessHeapPQ() {
		long startTime = System.currentTimeMillis();
		int n = 1;
		for(int i=0; i<this.jobsInputArray.length; i++) {
			System.out.println(n++ + ". waiting patiently, the data are being loaded.");
			this.currentTime++;
			if(this.jobsInputArray[i].getClass().getName().equals("Job")) {
				Job item = (Job) this.jobsInputArray[i];
				this.arrHeapPQ.getArrayHeap()[i+1] = item;
				item.setEntryTime(this.currentTime);
				this.arrHeapPQ.setRear(this.arrHeapPQ.getRear()+1);
				this.arrHeapPQ.setSize(this.arrHeapPQ.getSize()+1);
			}
			else {
				E item = this.jobsInputArray[i];
				this.arrHeapPQ.getArrayHeap()[i+1] = item;
			}
		}
		this.getArrHeapPQ().resize();
		this.arrHeapPQ.buildHeap(this.arrHeapPQ.getArrayHeap(), this.arrHeapPQ.getSize());
		long endTime = System.currentTimeMillis();
		this.accturalRunTime += (endTime - startTime);
	}
	
	public String jobProcessHeapPQ() {
		long startTime = System.currentTimeMillis();
		String strInfo = "";
		String strInfo1 = "";
		int processCount = 1;
		while(this.arrHeapPQ.isEmpty()!=true) {
			//出队操作，选择最高优先级，打印过程，更新相关工作状态
			this.currentTime++;
			Object e = this.arrHeapPQ.removeMin();
			
			if(e.getClass().getName().equals("Job")) {
				Job item = (Job) e;
				
				item.setCurrentJobLength(item.getCurrentJobLength()-1);
				item.setLastExecutTime(this.currentTime);
				item.setFlagExecuted(true);
				//strInfo += "Now executing " + item.getJobName() + ". Job length: " + item.getJobLength() + " cycles; Current remaing length: " + item.getCurrentJobLength() + " cycles; Initial priority: " + item.getJobPriority() + "; Current priority: " + item.getFinalPriority() + System.lineSeparator() + "";
				strInfo1 = processCount +  " :Now executing " + item.getJobName() + ". Job length: " + item.getJobLength() + " cycles; Current remaing length: " + item.getCurrentJobLength() + " cycles; Initial priority: " + item.getJobPriority() + "; Current priority: " + item.getFinalPriority() + System.lineSeparator() + "";
				System.out.println(strInfo1);
				processCount++;
				if(item.getCurrentJobLength()>0) {
					//如工作未完成，调用入队操作，更新相关工作状态
					this.arrHeapPQ.add(item);
				}
				else {
					item.setEndTime(this.currentTime);
					item.setWaitTime(item.getEndTime()-item.getEntryTime()-item.getJobLength());
					this.accumulatedWaitingTime += item.getWaitTime();
					this.countProcess++;
					this.countJobCompleted++;
					//this.jobRemainCount--;
					if(this.countJobCompleted==30) {
						//调用优先级检测方法;
						this.currentTime++;
						this.priorityAdjustHeap();
						this.countPriorityAdjust++;
						this.countJobCompleted = 0;
					}
				}
			}
			else {
				E item = (E) e;
			}
		}
		//如工作完成(结束循环)，更新类信息，如完成工作数量，平均等待时间等类属性，判断是否调用队列其他成员优先级
		this.averageWaitingTime = (double) this.accumulatedWaitingTime / this.countProcess;
		long endTime = System.currentTimeMillis();
		this.accturalRunTime += (endTime - startTime);
		strInfo += "Array-List-based Heap Priority Queue : " + System.lineSeparator() + "Current system time (cycles): " + this.currentTime + ". Total number of jobs executed: " + this.countProcess + " jobs" + System.lineSeparator() + "Average process waiting time: " + this.averageWaitingTime + " cycles" + System.lineSeparator() + "Total number of priority changes: " + this.countPriorityAdjust + ". Actual system time needed to execute all hobs: " + this.accturalRunTime + " ms";
		return strInfo;
	}
	
	public void priorityAdjustHeap() {
		int tempIdx = this.arrHeapPQ.getFront();
		Job tempItem = (Job) this.arrHeapPQ.getArrayHeap()[tempIdx];
		for(int i=this.arrHeapPQ.getFront(); i<this.arrHeapPQ.getRear(); i++) {
			Job item = (Job) this.arrHeapPQ.getArrayHeap()[i];
			if(item.isFlagExecuted()==false && item.getEntryTime()<tempItem.getEntryTime()) {
				tempIdx = i;
				tempItem = item;
			}
		}
		if(tempIdx != this.arrHeapPQ.getFront() && tempItem!=null) {
			this.arrHeapPQ.swap(this.arrHeapPQ.getFront(), tempIdx);
			tempItem.setFinalPriority(1);
		}
	}
}
