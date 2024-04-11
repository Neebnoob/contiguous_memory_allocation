package contiguous_memory_allocation;

public class WorstFit extends ContigousMemoryAllocator{

	//Constructor
	public WorstFit(int size, Process[] processesArray) {
		super(size, processesArray);
	}
	
	// Return the index of the biggest hole in processArray that process can fit into
	@Override
	protected int pickInsert() {
		int index = -1;
		int procSize = processesArray[currProcessIndex].getSize();
		int sizeDif = 0;
		for (int i = 0; i < memoryList.size(); i++){
			Partition part = memoryList.get(i);
			int partSize = memoryList.get(i).getEnd() - memoryList.get(i).getBase();
			//checks if partition is free and has space for process
			if (part.getIsFree() && partSize >= procSize){
				//checks if partition would have more left over space than a different spot
				if (sizeDif < partSize - procSize){
					index = i;
					sizeDif = partSize - procSize;
				}
			}
		}
		return index;
	}

}
