package contiguous_memory_allocation;

public class BestFit extends ContigousMemoryAllocator{

	//constructor
	public BestFit(int size, Process[] processesArray) {
		super(size, processesArray);
	}
	
	

	@Override
	protected int pickInsert() {
		int index = -1;
		int procSize = processesArray[currProcessIndex].getSize();
		int sizeDif = 0;
		for (int i = 0; i < memoryList.size(); i++){
			Partition part = memoryList.get(i);
			int partSize = memoryList.get(i).getEnd() - memoryList.get(i).getBase();
			if (part.getIsFree() && partSize >= procSize){
				if (sizeDif > partSize - procSize){
					index = i;
					sizeDif = partSize - procSize;
				}
			}
		}
		return index;
	}

}


