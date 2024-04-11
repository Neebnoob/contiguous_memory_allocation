package contiguous_memory_allocation;

public class Partition {
	
	//variables
	Process currProcess; //current process if not free
	Boolean isFree; //shows if partition has process
	int base; //starting memory value
	int end; //ending memory value
	
	//constructor (free)
	public Partition(int base, int end) {
		this.currProcess = null;
		this.isFree = true;
		this.base = base;
		this.end = end;
	}
	
	//constructor occupied
	public Partition(Process currProcess, int base) {
		this.currProcess = currProcess;
		this.isFree = false;
		this.base = base;
		this.end = currProcess.getSize() + base; //calculates end based on process end
	}
	
	//setters and getters
	public Process getCurrProcess() {
		return currProcess;
	}

	public void setCurrProcess(Process currProcess) {
		this.currProcess = currProcess;
	}

	public Boolean getIsFree() {
		return isFree;
	}

	public void setIsFree(Boolean isFree) {
		this.isFree = isFree;
	}

	public int getBase() {
		return base;
	}

	public void setBase(int base) {
		this.base = base;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
	
	public Boolean isProcessFinished() {
		if (this.currProcess.getTimeRemaining() == 0) {
			currProcess = null;
			isFree = true;
			return true;
		} else {
			return false;
		}
	}
	
	public String toString() {
		if (currProcess != null)
			return currProcess.toString();
		else
			return "Free (" + (this.end - this.base) + " KB)";
	}
	
	public int getPartSize() {
		return this.end - this.base;
	}

}