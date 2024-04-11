package contiguous_memory_allocation;

public class FirstFit extends ContigousMemoryAllocator{

	//constructor
	public FirstFit(int size, Process[] processesArray) {
		super(size, processesArray);
	}
	
	@Override
	//returns first index taht next process can fit into
	protected int pickInsert() {
		for (int i = 0; i < memoryList.size(); i++) {
			Partition part = memoryList.get(i);
			if (part.getIsFree() && part.getPartSize() >= processesArray[currProcessIndex].getSize()) {
				return i;
			}
		}
		return -1;
	}

}
