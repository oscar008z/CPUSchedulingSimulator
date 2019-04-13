import java.util.Random;
import java.io.*;

public class PriorityQueueSimulatorTester {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//int[] maxNumberOfJobs = {100, 1000, 20000};
		int[] maxNumberOfJobs = {100, 1000, 10000};
		Random r = new Random();
		String resArray = "";
		String resHeap = "";
		
		for(int i = 0; i<maxNumberOfJobs.length; i++) {
			Job[] jobsInputArray = new Job[maxNumberOfJobs[i]];
			Job[] jobsInputArrayHeap = new Job[maxNumberOfJobs[i]];
			
			for(int j = 0; j<jobsInputArray.length; j++) {
				String jobName = "JOB_" + (j+1);
				int jobLength = r.nextInt(70) + 1;
				int jobPriority = r.nextInt(40) + 1;
				Job newJob = new Job(jobName, jobLength, jobPriority);
				Job newJobHeap = new Job(jobName, jobLength, jobPriority);
				jobsInputArray[j] = newJob;
				jobsInputArrayHeap[j] = newJobHeap;
			}
			
			if(maxNumberOfJobs[i]<=10000) {
				CPUSimulator simulator = new CPUSimulator(jobsInputArray);
				simulator.loadProcessArrayPQ();
				resArray += simulator.jobProcessArrayPQ();
				System.out.println(resArray);
				resArray += "End the process of liner PQ" + System.lineSeparator();
				System.out.println("End the process of liner PQ");
				resArray += "===========================================================================" + System.lineSeparator();
				System.out.println("===========================================================================");
				
				CPUSimulator simulatorHeap = new CPUSimulator(jobsInputArrayHeap);
				simulatorHeap.loadProcessHeapPQ();
				resHeap += simulatorHeap.jobProcessHeapPQ();
				System.out.println(resHeap);
				resHeap += "End the process of Heap PQ" + System.lineSeparator();
				System.out.println("End the process of Heap PQ" + System.lineSeparator());
				resHeap += "===========================================================================" + System.lineSeparator();
				System.out.println("===========================================================================");
			}
			else {
				CPUSimulator simulatorHeap = new CPUSimulator(jobsInputArrayHeap);
				simulatorHeap.loadProcessHeapPQ();
				resHeap += simulatorHeap.jobProcessHeapPQ();
				System.out.println(resHeap);
				resHeap += "End the process of Heap PQ" + System.lineSeparator();
				System.out.println("End the process of Heap PQ" + System.lineSeparator());
				resHeap += "===========================================================================" + System.lineSeparator();
				System.out.println("===========================================================================");
			}
		}
		
		String resTotal = resArray + System.lineSeparator() + resHeap;
		
		File file = new File("SimulatorPerformanceResults.txt");	//create an object of File class for storing the result of program execution
		if(!file.exists())
			file.createNewFile();	//if the file doesn't exist, create the file
		BufferedWriter bwr = new BufferedWriter(new FileWriter(file,true));	//create the object of IO write stream
		bwr.write(resTotal);	//write the result of program execution to the target file
		bwr.close();	//close the IO stream	
	}

}
