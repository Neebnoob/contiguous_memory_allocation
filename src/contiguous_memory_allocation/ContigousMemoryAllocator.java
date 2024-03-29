package contiguous_memory_allocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ContigousMemoryAllocator {
	private int size; // maximum memory size in bytes (B)
	private Map<String, Partition> allocMap; // map process to partition
	private List<Partition> partList; // list of memory partitions
	// constructor

	public ContigousMemoryAllocator(int size) {
		this.size = size;
		this.allocMap = new HashMap<>();
		this.partList = new ArrayList<>();
		this.partList.add(new Partition(0, size)); // add the first hole, which is the whole memory at start up
	}

	// prints the list of available commands
	public void print_help_message() {
		System.out.println("RQ <process> <size> to request memory of size for the process");
		System.out.println("RL <process> to release the memory of the process");
		System.out.println("STAT to show the memory allocation status");
		System.out.println("EXIT to exit");
		System.out.println("HELP to show the available commands");

	}

	// prints the allocation map (free + allocated) in ascending order of base
	// addresses
	public void print_status() {
		order_partitions(); // sort the memory partitions in ascending order of based address
		System.out.printf("Partitions [Allocated = %d B, Free = %d B]:\n", allocated_memory(), free_memory());
		for (Partition part : partList) {
			System.out.printf("Address [%d:%d] %s (%d B)\n", part.getBase(), part.getBase() + part.getLength() - 1,
					part.isbFree() ? "Free" : part.getProcess(), part.getLength());
		}
	}

	// get the size of total allocated memory
	private int allocated_memory() {
		int allocated = 0;
		for (Partition part : partList)
			if (!part.isbFree())
				allocated += part.getLength();
		return allocated;
	}

	// get the size of total free memory
	private int free_memory() {
		int free = 0;
		for (Partition part : partList)
			if (part.isbFree())
				free += part.getLength();
		return free;
	}

	// sort the list of partitions in ascending order of base addresses
	private void order_partitions() {
		Collections.sort(partList, Comparator.comparingInt(Partition::getBase));
	}

	// implements the first fit memory allocation algorithm
	public int first_fit(String process, int size) {
		if (allocMap.containsKey(process))
			return -1;
		int index = 0, alloc = -1;
		while (index < partList.size()) {
			Partition part = partList.get(index);
			if (part.isbFree() && part.getLength() >= size) {
				Partition newPart = new Partition(part.getBase(), size);
				newPart.setbFree(false);
				newPart.setProcess(process);
				partList.add(index, newPart);
				allocMap.put(process, newPart);
				part.setBase(part.getBase() + size);
				part.setLength(part.getLength() - size);
				if (part.getLength() == 0)
					partList.remove(part);
				alloc = size;
				break;
			}
			index++;
		}
		return alloc;
	}

	// release the allocated memory of a process
	public int release(String process) {
		int free = -1;
		for (Partition part : partList) {
			if (!part.isbFree() && part.getProcess().equals(process)) {
				part.setbFree(true);
				part.setProcess(null);
				free = part.getLength();
				break; // found requested partition and update it
			}
		}
		if (free < 0)
			return free; // failed search, maybe not found in the requested process
		// merge adjacent hole (free memory partitions
		merge_holes();
		return free;
	}

	// procedure to merge adjacent holes
	private void merge_holes() {
		order_partitions(); // sort the partition list by base address
		int i = 0;
		while (i < partList.size()) {
			Partition part = partList.get(i);
			if (!part.isbFree()) {
				i++; // try the next partition
				continue; // skip to the next partition
			}
			int end_i = part.getBase() + part.getLength() - 1;
			int j = i + 1;
			while (j < partList.size() && partList.get(j).isbFree()) {
				int start_j = partList.get(j).getBase();
				if (start_j == end_i + 1) {// continuous memory indexes
					// merge partition j into i
					part.setLength(part.getLength() + partList.get(j).getLength());
					partList.remove(j); // remove partition at index j
				} else {
					break; // cannot merge j into i
				}
			}
			i++; // try with the next partition in the partList
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the max size of the memory: ");
		int size = Integer.parseInt(sc.nextLine());
		// Create the memory simulator
		ContigousMemoryAllocator mmu = new ContigousMemoryAllocator(size);
		while (true) {
			System.out.print("mmu> ");
			String command = sc.nextLine();
			String[] arr = command.split(" ");
			if (arr[0].equalsIgnoreCase("EXIT"))
				break; // break while loop and terminate this simulator
			else if (arr[0].equalsIgnoreCase("HELP"))
				mmu.print_help_message(); // print available command
			else if (arr[0].equalsIgnoreCase("STAT"))
				mmu.print_status(); // print memory allocation status
			else if (arr[0].equalsIgnoreCase("RQ")) {
				if (arr.length < 3) {
					System.out.println("Please provide the process name and request size");
					continue;
				}
				String process = arr[1];
				int proc_size = Integer.parseInt(arr[2]);
				if (mmu.first_fit(process, proc_size) > 0)
					System.out.println("Successfully allocate " + proc_size + "KB to the process " + process);
				else
					System.out.println("Cannot allocate " + proc_size + "KB to the process " + process);
			} else if (arr[0].equalsIgnoreCase("RL")) { // release memory command
				if (arr.length < 2) {
					System.out.println("Please provide the process name");
					continue;
				}
				String process = arr[1];
				if (mmu.release(process) > 0)
					System.out.println("Successfully dellocate the memory of the process" + process);
				else
					System.out.println("Cannot allocate the memory of the proccess" + process);
			} else {
				System.out.println("Invalid command!");
			}

		}
	}
}
