package contiguous_memory_allocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


//Parent class to First-Fit, Best-Fit, Worst-Fit
public class ContigousMemoryAllocator {
	
	//variables
	int size; // max size
	List<Partition> memoryList; // list of all current partitions
	Process[] processesArray; // array of processes
	int holes;
	
	//constructor
	public ContigousMemoryAllocator(int size, Process[] processesArray) {
		this.size = size;
		this.processesArray = processesArray;
		memoryList.add(new Partition(0, size));
		this.holes = 1;
	}
	
	//toString
	public String toString() {
		
		StringBuilder toReturn = new StringBuilder("| ");
		
		for (int i = 0; i < memoryList.size(); i++) {
			toReturn.append(memoryList.get(i).toString() + " | ");
		}
		return toReturn.toString();
	}
	
}
