package contiguous_memory_allocation;

import java.util.List;

//Parent class to First-Fit, Best-Fit, Worst-Fit
public abstract class ContigousMemoryAllocator {

	// variables
	int size; // max size
	List<Partition> memoryList; // list of all current partitions
	Process[] processesArray; // array of processes
	int holes;
	int iterations;
	int holeAvgPercent;
	int currProcessIndex;

	// constructor
	public ContigousMemoryAllocator(int size, Process[] processesArray) {
		this.size = size;
		this.processesArray = processesArray;
		memoryList.add(new Partition(0, size));
		this.holes = 1;
		this.currProcessIndex = 0;
		this.iterations = 0;
		this.holeAvgPercent = 0;
	}

	public void schedule() {
		timeDecrease();
		Boolean flag = true;
		//Pick next process loop to keep adding processes while there is space
		while (flag) {
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
					memoryList.get(insertIndex + 1).setBase(memoryList.get(insertIndex).getEnd() + 1);
				}
				//increase process index
				currProcessIndex++;
			}
		}
		
		this.holes = 0;
		int holeTotalSize = 0;
		for (int i = 0; i < memoryList.size(); i++) {
			if (memoryList.get(i).getIsFree()) {
				holes++;
			}
		}
			
	}

	private void timeDecrease() {
		for (int i = 0; i < memoryList.size(); i++) {
			Partition currPart = memoryList.get(i);
			// check if partition is occupied
			if (!currPart.getIsFree()) {
				// if so decrease the processes remaining time by 1
				currPart.getCurrProcess().decrementTimeRemaining();
			}

			// check if process is at 0 (update it if it is)
			// and if current partition is not the first
			// and if previous partition is also free
			if (currPart.isProcessFinished() && i != 0 && memoryList.get(i - 1).getIsFree()) {
				// if so merge the two partition into 1
				currPart.setBase(memoryList.get(i - 1).getBase());
				memoryList.remove(i - 1);

			}
		}
	}

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
