package contiguous_memory_allocation;

import java.util.Scanner;

public class ConsoleGUI {
	
	static Process[] processesArray;

	public static void startUp() {

		//console questions
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to Contiguous Memory Allocation\n"
						 + "Enter -1 to use the default value\n"
						 + "Please input the desired max size (kb) of the contiuous memory");
		int maxMemorySize = inputMaxMemorySize(scan);
		System.out.println("Please input the desired number of processes");
		int numProcesses = inputNumProcesses(scan);
		System.out.println("Please input the max size (kb) you would like the processes to be");
		int maxProcSize = inputMaxProcSize(scan);
		System.out.println("Please input the max time (ms) of each process");
		int maxProcTime = inputMaxProcTime(scan);
		scan.close();
		
		//Print out of all selected values from user
		System.out.println("Choosen vales\n"
						+  "Memory Size: " + maxMemorySize + " kb\n"
						+  "Number of Processes: " + numProcesses + "\n"
						+  "Max Process Size: " + maxProcSize + " kb\n"
						+  "Max Process Time: " + maxProcTime + " ms");
		
		//new code here
		processesArray = Process.generateProcessArray(numProcesses, maxProcSize, maxProcTime);
		
		//Print a table of all process data
		for (int i = 0; i <processesArray.length; i++) {
			System.out.println(processesArray[i].toString());
		}
		
		System.out.println("\n" + "##################################" + "\n");
		System.out.println("-------------First Fit------------" + "\n");
		
		//first fit simulation call
		FirstFit firstFit = new FirstFit(maxMemorySize - 1, processesArray);
		while (!firstFit.isDone()) {
			firstFit.schedule();
		}
		firstFit.toString();
		
		//resets all process remaining time values
		Process.resetProcessArray(processesArray);

		System.out.println("\n" + "##################################" + "\n");
		System.out.println("-------------Best Fit-------------" + "\n");
		
		//best fit simulation call
		BestFit bestFit = new BestFit(maxMemorySize - 1, processesArray);
		while (!bestFit.isDone()) {
			bestFit.schedule();
		}
		bestFit.toString();
		
		//resets all process remaining time values
		Process.resetProcessArray(processesArray);

		System.out.println("\n" + "##################################" + "\n");
		System.out.println("-------------Worst Fit------------" + "\n");
		
		//worst fit simulation call
		WorstFit worstFit = new WorstFit(maxMemorySize - 1, processesArray);
		while (!worstFit.isDone()) {
			worstFit.schedule();
		}
		worstFit.toString();

	}

	/// Asks the user to input a number greater than zero to be the max memory size
	/// Throws an error for any value 0 or less and for any non number value
	private static int inputMaxMemorySize(Scanner scan) {
		Integer maxMemorySize = null;
	    do {
	        String input = scan.nextLine();
	        try {
	        	maxMemorySize = Integer.parseInt(input);
	        	if (maxMemorySize == -1)
					return 1024;
				else if (maxMemorySize <= 0)
					throw new IllegalValueException();
				else
					return maxMemorySize;
	        } catch (IllegalValueException ive) {
				System.out.println("Please enter a value greater than 0");
			} catch(NumberFormatException e) {
	            System.out.println("Please enter a number"); 
	        }
	    } while(maxMemorySize == null);
	    
	    return -1;
	}

	/// Asks the user to input a number greater than zero to be the number of
	/// processes they want
	// Throws an error for any value 0 or less and for any non number value
	private static int inputNumProcesses(Scanner scan) {
		Integer numProcesses = null;
	    do {
	        String input = scan.nextLine();
	        try {
	        	numProcesses = Integer.parseInt(input);
	        	if (numProcesses == -1)
					return 10;
				else if (numProcesses <= 0)
					throw new IllegalValueException();
				else
					return numProcesses;
	        } catch (IllegalValueException ive) {
				System.out.println("Please enter a value greater than 0");
			} catch(NumberFormatException e) {
	            System.out.println("Please enter a number"); 
	        }
	    } while(numProcesses == null);
	    
	    return -1;
	}

	/// Asks the user to input a number greater than zero to be the max size they
	/// want a process to be
	// Throws an error for any value 0 or less and for any non number value
	private static int inputMaxProcSize(Scanner scan) {
		Integer maxProcSize = null;
	    do {
	        String input = scan.nextLine();
	        try {
	        	maxProcSize = Integer.parseInt(input);
	        	if (maxProcSize == -1)
					return 256;
				else if (maxProcSize <= 0)
					throw new IllegalValueException();
				else
					return maxProcSize;
	        } catch (IllegalValueException ive) {
				System.out.println("Please enter a value greater than 0");
			} catch(NumberFormatException e) {
	            System.out.println("Please enter a number"); 
	        }
	    } while(maxProcSize == null);
	    
	    return -1;
	}

	/// Asks the user to input a number greater than zero to be the max time
	/// want a process to last for
	// Throws an error for any value 0 or less and for any non number value
	private static int inputMaxProcTime(Scanner scan) {
		Integer maxProcTime = null;
	    do {
	        String input = scan.nextLine();
	        try {
	        	maxProcTime = Integer.parseInt(input);
	        	if (maxProcTime == -1)
					return 10000;
				else if (maxProcTime <= 0)
					throw new IllegalValueException();
				else
					return maxProcTime;
	        } catch (IllegalValueException ive) {
				System.out.println("Please enter a value greater than 0");
			} catch(NumberFormatException e) {
	            System.out.println("Please enter a number"); 
	        }
	    } while(maxProcTime == null);
	    
	    return -1;
	}

}
