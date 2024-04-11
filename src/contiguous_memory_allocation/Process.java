package contiguous_memory_allocation;

public class Process {
	
	// variables
	String name;
	int size;
	int time;
	int timeRemaining;
	
	// default constructor
	public Process() {
		this.name = "Default";
		this.size = -1;
		this.time = -1;
		this.timeRemaining = -1;
	}
	
	// constructor
	public Process(String name, int size, int time) {
		this.name = name;
		this.size = size;
		this.time = time;
		this.timeRemaining = time;
	}
	
	// setters and getters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getTimeRemaining() {
		return timeRemaining;
	}

	public void setTimeRemaining(int timeRemaining) {
		this.timeRemaining = timeRemaining;
	}
	
	// reduce the remaining time by 1
	public void decrementTimeRemaining() {
		this.timeRemaining--;
	}
	
	// creates an array of processes with random values based on the max the user supplied
    public static Process[] generateProcessArray(int procCount, int maxSize, int maxTime) {
        Process[] processArray = new Process[procCount];
        for (int i = 0; i < procCount; i++) {
            processArray[i] = new Process(Integer.toString(i), (int)(maxSize * Math.random()), (int)(maxTime * Math.random()));
        }
        return processArray;
    }
    
    // takes the existing array and resets the remaining time value
    public void resetProcessArray(Process[] processArray) {
        for (int i = 0; i < processArray.length; i++) {
            processArray[i].setTimeRemaining(processArray[i].getTime());
        }
    }

    //toString
    public String toString() {
        return String.format(this.getName() + " [ " + this.getTimeRemaining() + " ] " + " ( " + this.getSize()) + " ) ";
    }

}
