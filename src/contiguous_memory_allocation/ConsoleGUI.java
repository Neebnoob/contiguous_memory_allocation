package contiguous_memory_allocation;

import java.util.Scanner;

public class ConsoleGUI {

	public static void startUp() {

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
		
		//Print out of all selected values from user
		System.out.println("Choosen vales\n"
						+  "Memory Size: " + maxMemorySize + " kb\n"
						+  "Number of Processes: " + numProcesses + "\n"
						+  "Max Process Size: " + maxProcSize + " kb\n"
						+  "Max Process Time: " + maxProcTime + " ms");
		
		
		
		scan.close();

	}

	/// Asks the user to input a number greater than zero to be the max memory size
	/// Throws an error for any value 0 or less and for any non number value
	// BUG - when a non int is input gets infinite loop of "Please enter a number"
	private static int inputMaxMemorySize(Scanner scan) {
		Boolean flag = true;
		int maxMemorySize;
		while (flag) {
			try {
				maxMemorySize = scan.nextInt();
				if (maxMemorySize == -1)
					return 1024;
				else if (maxMemorySize <= 0)
					throw new IllegalValueException();
				else
					return maxMemorySize;
				//flag = false;
			} catch (IllegalValueException ive) {
				System.out.println("Please enter a value greater than 0");
			} catch (Exception e) {
				System.out.println("Please enter a number");
			}
		}
		return -1;
	}

	/// Asks the user to input a number greater than zero to be the number of
	/// processes they want
	// Throws an error for any value 0 or less and for any non number value
	// BUG - when a non int is input gets infinite loop of "Please enter a number"
	private static int inputNumProcesses(Scanner scan) {
		Boolean flag = true;
		int numProcesses;
		while (flag) {
			try {
				numProcesses = scan.nextInt();
				if (numProcesses == -1)
					return 10;
				else if (numProcesses <= 0)
					throw new IllegalValueException();
				else
					return numProcesses;
				//flag = false;
			} catch (IllegalValueException ive) {
				System.out.println("Please enter a value greater than 0");
			} catch (Exception e) {
				System.out.println("Please enter a number");
			}
		}
		return -1;
	}

	/// Asks the user to input a number greater than zero to be the max size they
	/// want a process to be
	// Throws an error for any value 0 or less and for any non number value
	// BUG - when a non int is input gets infinite loop of "Please enter a number"
	private static int inputMaxProcSize(Scanner scan) {
		Boolean flag = true;
		int maxProcSize;
		while (flag) {
			try {
				maxProcSize = scan.nextInt();
				if (maxProcSize == -1)
					return 256;
				else if (maxProcSize <= 0)
					throw new IllegalValueException();
				else
					return maxProcSize;
				//flag = false;
			} catch (IllegalValueException ive) {
				System.out.println("Please enter a value greater than 0");
			} catch (Exception e) {
				System.out.println("Please enter a number");
			}
		}
		return -1;
	}

	/// Asks the user to input a number greater than zero to be the max time
	/// want a process to last for
	// Throws an error for any value 0 or less and for any non number value
	// BUG - when a non int is input gets infinite loop of "Please enter a number"
	private static int inputMaxProcTime(Scanner scan) {
		Boolean flag = true;
		int maxProcTime;
		while (flag) {
			try {
				maxProcTime = scan.nextInt();
				if (maxProcTime == -1)
					return 10000;
				else if (maxProcTime <= 0)
					throw new IllegalValueException();
				else
					return maxProcTime;
				//flag = false;
			} catch (IllegalValueException ive) {
				System.out.println("Please enter a value greater than 0");
			} catch (Exception e) {
				System.out.println("Please enter a number");
			}
		}
		return -1;
	}

}
