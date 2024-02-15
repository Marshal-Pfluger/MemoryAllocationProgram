//********************************************************************
//
//  Author:        Marshal Pfluger
//
//  Program #:     Capstone Project
//
//  File Name:     CapstoneProject.java
//
//  Course:        COSC 4302 Operating Systems
//
//  Due Date:      12/08/2023
//
//  Instructor:    Prof. Fred Kumi
//
//  Java Version:  17
//
//  Description:    Implement different algorithms for contiguous memory
//                  allocation.
//********************************************************************
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class CapstoneProject {
	
    public static void main(String[] args) {
    	CapstoneProject obj = new CapstoneProject();
    	obj.developerInfo();
    	obj.runDemo();
    }
    
    //***************************************************************
    //
    //  Method:       runDemo
    // 
    //  Description:  Runs the program operations 
    //
    //  Parameters:   N/A
    //
    //  Returns:      N/A
    //
    //**************************************************************
    public void runDemo() {
    	// Flag to control the loop
        boolean exit = true;
        // Get user input for memory size
        long memorySize = parseMemorySize();
            
        // Create an Allocator object with the specified memory size
        Allocator obj = new Allocator(new MemoryBlock(memorySize));

        // List to store user commands
        ArrayList<String> commandList = new ArrayList<>();
            
        // Inner loop for processing user commands
        while (exit) {
        	// Prompt for user input
            printOutput("\nallocator>");
                
            // Parse user input and add commands to the list
            commandList.addAll(Arrays.asList(userChoice().split("\\s+")));

            // Get the first command from the list
            String command = commandList.get(0);
                
            // Switch statement to handle different commands
            switch (command) {
                case "RQ" -> {
                	// Process "RQ" command for memory allocation
                    String processName = commandList.get(1);
                    Long size = longInputValidation(commandList.get(2));
                    String strategy = stringInputValidation(commandList.get(3));

                    // Check if any of the values is null or command parameters are incorrect
                    if (processName == null || size == null || strategy == null || !(commandList.size() == 4)) {
                    	printOutput("Invalid command parameters, try again\n");
                    	} 
                    else {
                    	// Handle memory allocation request
                    	obj.handleRequest(processName, size, strategy);
                    	}
                    }
                case "RL" -> 
                    // Process "RL" command for memory release
                    obj.handleRelease(commandList.get(1));
                case "C" -> 
                    // Process "C" command for memory compaction
                    obj.compactMemory();
                case "STAT" -> 
                    // Process "STAT" command to print memory status
                    obj.printMemoryStatus();
                case "X" -> 
                    // Exit the program
                    exit = false;
                default -> 
                    // Handle invalid commands
                    printOutput("Invalid command.");
                }
            // Clear the command list for the next iteration
            commandList.clear();
            }
        // Display a termination message
        printOutput("Program has terminated, See you later Allocator");
    }
    
    //***************************************************************
    //
    //  Method:       parseMemorySize
    // 
    //  Description:  helps validate and parse the inital memory size 
    //
    //  Parameters:   N/A
    //
    //  Returns:     long
    //
    //**************************************************************
    public long parseMemorySize() {
        // Variable to store the converted memory size in bytes
        long convertedNumber = 0;

        // List to store user input for memory size
        ArrayList<String> memSize;

        // Flag to track the validity of user input
        boolean valid = true;

        // Variable to store the numeric part of the memory size
        int numUnits = 0;

        // Main loop to ensure valid input
        do {
            // Reset the validity flag
            valid = true;

            // Prompt the user to enter the initial amount of memory
            printOutput("Enter the initial amount of memory: ");

            // Split and store user input into the memSize list
            memSize = new ArrayList<>(Arrays.asList(userChoice().split("\\s+")));

            // Check if the input has two parts (numeric and unit)
            if (!(memSize.size() == 2)) {
                valid = false;
            } else {
                try {
                    // Parse the numeric part of the memory size
                    numUnits = Integer.parseInt(memSize.get(0));

                    // Convert the user input to bytes
                    convertedNumber = convertToBytes(numUnits, memSize.get(1));

                    // If conversion fails, throw an exception
                    if (convertedNumber == -1) {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    // Catch any exceptions (e.g., invalid numeric input or unit)
                    valid = false;
                }
            }

            // If the input is not valid, inform the user and prompt again
            if (!valid)
                printOutput("Invalid input: must be in the form (1 MB)\n");

        } while (!valid);

        // Return the converted memory size in bytes
        return convertedNumber;
    }
    
    //***************************************************************
    //
    //  Method:       convertToBytes
    // 
    //  Description:  Uses an enhanced switch case to convert the units to bytes 
    //
    //  Parameters:   int numUnits, String unit
    //
    //  Returns:     long
    //
    //**************************************************************
    public long convertToBytes(int numUnits, String unit) {
        // Extract the number and unit parts
        long byteSize = 0;

        // Convert to bytes based on the unit
        byteSize = switch (unit) {
            case "KB" -> numUnits * 1024;
            case "MB" -> numUnits * (long) Math.pow(1024, 2);
            case "GB" -> numUnits * (long) Math.pow(1024, 3);
            case "TB" -> numUnits * (long) Math.pow(1024, 4);
            default -> -1;
        };

        // Return -1 for invalid input
        return byteSize;
    }

    
    //***************************************************************
    //
    //  Method:       longInputValidation (Non Static)
    // 
    //  Description:  validates the user input
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public Long longInputValidation(String userInput) {
    	
    	Long userLong =  (long) 0;
    	// Make sure input is numeric
    		try {
    			userLong = Long.parseLong(userInput);	
    			} catch (NumberFormatException e) {
    				userLong = null;
    			}
    	return userLong;
    	}

    
    //***************************************************************
    //
    //  Method:       stringinputValidation (Non Static)
    // 
    //  Description:  validates the user input
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public String stringInputValidation(String userInput) {
        // Check user input for initial menu item
    	if(!(userInput.equalsIgnoreCase("F") || userInput.equalsIgnoreCase("B") || userInput.equalsIgnoreCase("W"))) {
    		userInput = null;
    		}
    	return userInput;
    	}
    
    //**************************************************************
    //
    //  Method:       userChoice
    //
    //  Description:  gets input from user, closes scanner when program exits 
    //
    //  Parameters:   N/A
    //
    //  Returns:      String file
    //
    //**************************************************************	
    public String userChoice() {
    	String userChoice;
    	// Use Scanner to receive user input
    	Scanner userInput = new Scanner(System.in);
    	// Store user choice
    	userChoice = userInput.nextLine();
    	
    	// close scanner when program exits.
    	if (userChoice.equalsIgnoreCase("0")) {
    		userInput.close();
    		}
    	return userChoice;
    	}
    
	//***************************************************************
	//
	//  Method:       printOutput (Non Static)
	// 
	//  Description:  handles printing output
	//
	//  Parameters:   String output
	//
	//  Returns:      N/A
	//
	//***************************************************************
	public void printOutput(String output) {
		//Print the output to the terminal
		System.out.print("\n");
		System.out.print(output);
	}//End printOutput
    
    //***************************************************************
    //
    //  Method:       developerInfo (Non Static)
    // 
    //  Description:  The developer information method of the program
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public void developerInfo(){
       System.out.println("Name:    Marshal Pfluger");
       System.out.println("Course:  COSC 4302 Operating Systems");
       System.out.println("Project: Capstone\n\n");
    } // End of the developerInfo method
}
