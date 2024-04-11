package contiguous_memory_allocation;

public class BestFit extends ContigousMemoryAllocator{

	//constructor
	public BestFit(int size, Process[] processesArray) {
		super(size, processesArray);
	}
	
	// Return the index of the smallest hole in processArray that process can fit into
	@Override
	protected int pickInsert() {
		int index = -1;
		int procSize = processesArray[currProcessIndex].getSize();
		int sizeDif = Integer.MAX_VALUE;
		//loops though memory list
		for (int i = 0; i < memoryList.size(); i++){
			Partition part = memoryList.get(i);
			int partSize = memoryList.get(i).getEnd() - memoryList.get(i).getBase();
			//checks if partition is free and has space for process
			if (part.getIsFree() && partSize >= procSize){
				//checks if partition would have less left over space than a different spot
				if (sizeDif > partSize - procSize){
					index = i;
					sizeDif = partSize - procSize;
				}
			}
		}
		return index;
	}

}


