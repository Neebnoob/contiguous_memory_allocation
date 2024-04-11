package contiguous_memory_allocation;

import java.util.ArrayList;
import java.util.List;

//Parent class to First-Fit, Best-Fit, Worst-Fit
public abstract class ContigousMemoryAllocator {

	// variables
	int size; // max size
	List<Partition> memoryList; // list of all current partitions
	Process[] processesArray; // array of processes
	int iterations; //Used to calculate Hole size averages
	int holeAvgPercent; //Keeps track of average hole percentage
	int currProcessIndex; //next process to be added
	int totalHoles; //total holes ever had

	// constructor
	public ContigousMemoryAllocator(int size, Process[] processesArray) {
		this.size = size;
		this.processesArray = processesArray;
		this.memoryList = new ArrayList<Partition>();
		memoryList.add(new Partition(0, size));
		this.currProcessIndex = 0;
		this.iterations = 0;
		this.holeAvgPercent = 0;
		this.totalHoles = 1;
	}

	public void schedule() {
		timeDecrease();
		//uncomment to bug test
		//System.out.println(toString());
		Boolean flag = true;
		//Pick next process loop to keep adding processes while there is space
		while (flag && currProcessIndex < processesArray.length) {
			int insertIndex = pickInsert();
			//check if insert index return -1
			if (insertIndex == -1) {
				//if so next process cannot be added
				flag = false;
			} else {
				//else add next process to good index
				memoryList.add(insertIndex, new Partition(processesArray[currProcessIndex], memoryList.get(insertIndex).getBase()));
				//adjust free space if possible
				if (processesArray[insertIndex].getSize() == (memoryList.get(insertIndex + 1).getEnd() - memoryList.get(insertIndex + 1).getBase())) {
					memoryList.remove(insertIndex + 1);
				} else {
					memoryList.get(insertIndex + 1).setBase(memoryList.get(insertIndex).getEnd());
				}
				//increase process index
				currProcessIndex++;
			}
		}
		
		int holes = 0;
		int holeTotalSize = 0;
		//documents current holes and total hole size
		for (int i = 0; i < memoryList.size(); i++) {
			if (memoryList.get(i).getIsFree()) {
				holes++;
				holeTotalSize+= memoryList.get(i).getPartSize();
			}
		}
		//calls toString
		System.out.println(toString());
		//Prints hole stats in a neat way
		System.out.println("Holes: " + holes + "\n"
						 + "Avg: " + (holeTotalSize/holes) + " KB\n"
						 + "Total: " + holeTotalSize + " KB\n"
						 //calculates current hole percentage
						 + "Percent: " + (double)Math.round((holeTotalSize/(double)size) * 10000) / 100 + "%\n"
						 + "Cum Avg: " + cumAvg(holeTotalSize, holes) + "%");
	}
	
	//calculate Cumulative hole percentage
	//BUG every other iteration is negative
	private double cumAvg(int holeTotalSize, int holes) {
		holeAvgPercent = ((holeAvgPercent * (iterations - 1)) + holeTotalSize / totalHoles);
		totalHoles += holes;
		return (double)Math.round((holeAvgPercent / (double)size) * 10000) / 100;
	}

	//decrease the remaining time of every process by 1
	private void timeDecrease() {
		for (int i = 0; i < memoryList.size(); i++) {
			Partition currPart = memoryList.get(i);
			// check if partition is occupied
			if (!currPart.getIsFree()) {
				// if so decrease the processes remaining time by 1
				currPart.getCurrProcess().decrementTimeRemaining();
			}

			// check part is occupied
			// and if process is at 0 (update it if it is)
			// and if current partition is not the first
			// and if previous partition is also free
			if (!currPart.getIsFree() && currPart.isProcessFinished() && i != 0 && memoryList.get(i - 1).getIsFree()) {
				// if so merge the two partition into 1
				currPart.setBase(memoryList.get(i - 1).getBase());
				memoryList.remove(i - 1);
				i--;
			} else if (currPart.getIsFree() && i != 0 && memoryList.get(i - 1).getIsFree()) {
				currPart.setBase(memoryList.get(i - 1).getBase());
				memoryList.remove(i - 1);
				i--;
			}
		}
	}
	
	//checks to see if all processes have been added and if the memory is all free
	public Boolean isDone() {
		return (currProcessIndex >= processesArray.length && memoryList.size() == 1);
	}

	//parent abstract class
	protected abstract int pickInsert();

	// toString
	public String toString() {

		StringBuilder toReturn = new StringBuilder("| ");

		for (int i = 0; i < memoryList.size(); i++) {
			toReturn.append(memoryList.get(i).toString() + " | ");
		}
		return toReturn.toString();
	}

}
