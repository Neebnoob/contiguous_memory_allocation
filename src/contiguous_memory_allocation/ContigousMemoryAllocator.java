package contiguous_memory_allocation;

import java.util.List;

//Parent class to First-Fit, Best-Fit, Worst-Fit
public abstract class ContigousMemoryAllocator {
	
	//variables
	int size; // max size
	List<Partition> memoryList; // list of all current partitions
	Process[] processesArray; // array of processes
	int holes;
	int currProcessIndex;
	
	//constructor
	public ContigousMemoryAllocator(int size, Process[] processesArray) {
		this.size = size;
		this.processesArray = processesArray;
		memoryList.add(new Partition(0, size));
		this.holes = 1;
		this.currProcessIndex = 0;
	}
	
	protected abstract int pickInsert();
	
	//toString
 	public String toString() {
		
		StringBuilder toReturn = new StringBuilder("| ");
		
		for (int i = 0; i < memoryList.size(); i++) {
			toReturn.append(memoryList.get(i).toString() + " | ");
		}
		return toReturn.toString();
	}
	
}
