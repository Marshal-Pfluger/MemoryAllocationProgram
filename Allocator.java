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
//  Description:   Implement different algorithms for contiguous memory
//                 allocation.
//********************************************************************
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Allocator {
	private ArrayList<MemoryBlock> blockStorage;
	
	public Allocator(MemoryBlock initialMemory){
		this.blockStorage = new ArrayList<>(Arrays.asList(initialMemory));
	}
		
    //***************************************************************
    //
    //  Method:       handleRequest
    // 
    //  Description:  Runs request handling operations 
    //
    //  Parameters:   String processId, long size, String strategy
    //
    //  Returns:      N/A
    //
    //**************************************************************
	public void handleRequest(String processId, long size, String strategy) {
		switch (strategy) {
	    case "F" -> allocateFirstFit(processId, size);
	    case "B" -> allocateBestFit(processId, size);
	    case "W" -> allocateWorstFit(processId, size);
	    default -> printOutput("Error: Invalid allocation strategy.");    
	}
	}

    //***************************************************************
    //
    //  Method:       allocateFirstFit
    // 
    //  Description:  allocates the first fit strategy 
    //
    //  Parameters:   String processId, long size
    //
    //  Returns:      N/A
    //
    //**************************************************************
	public void allocateFirstFit(String processId, long size) {
	    // Flag to track whether memory allocation was successful
	    boolean status = false;
	    
	    MemoryBlock newBlock = null;
	    // Iterate through each memory block in the blockStorage
	    for (MemoryBlock block : blockStorage) {
	        // Check if the block is unused and has enough space for the process
	        if (block.getProcessId().equals("Unused") && (block.getEndAddress() - block.getStartAddress() + 1) >= size) {
	            // Allocate memory for the process in the current block
	            block.setProcessId(processId);
	            status = true;

	            // If the block is larger than needed, split the block
	            if ((block.getEndAddress() - block.getStartAddress() + 1) > size) {
	                // Create a new block representing the unused portion after allocation
	                newBlock = new MemoryBlock(block.getStartAddress() + size, block.getEndAddress(), "Unused");

	                // Adjust the end address of the current block to match the allocated size
	                block.setEndAddress(block.getStartAddress() + size - 1);

	            }
	        }
	    }
        if(!(newBlock == null)) {
            // Add the new block to the blockStorage
            blockStorage.add(newBlock);
        }

        // Print appropriate messages based on the allocation status
        if (status) {

            printOutput("Allocated memory for process " + processId + " using First Fit.");
        } 
        else {
            // If no suitable hole found
            printOutput("Error: Insufficient memory to allocate for process " + processId + " using First Fit.");
        }
	}

    //***************************************************************
    //
    //  Method:       allocateBestFit
    // 
    //  Description:  allocates the best fit strategy 
    //
    //  Parameters:   String processId, long size
    //
    //  Returns:      N/A
    //
    //**************************************************************
	public void allocateBestFit(String processId, long size) {
	    // Implementation of Best Fit strategy
	    MemoryBlock bestFitBlock = null;

	    // Iterate through each memory block in the blockStorage
	    for (MemoryBlock block : blockStorage) {
	        // Check if the block is unused and has enough space for the process
	        if (block.getProcessId().equals("Unused") && (block.getEndAddress() - block.getStartAddress() + 1) >= size) {
	            // Update the bestFitBlock if it's the first suitable block or smaller than the current best fit
	            if (bestFitBlock == null || (block.getEndAddress() - block.getStartAddress()) < (bestFitBlock.getEndAddress() - bestFitBlock.getStartAddress())) {
	                bestFitBlock = block;
	            }
	        }
	    }

	    // If a suitable block is found
	    if (bestFitBlock != null) {
	        // Allocate memory for the process in the best fit block
	        bestFitBlock.setProcessId(processId);

	        // If the block is larger than needed, split the block
	        if ((bestFitBlock.getEndAddress() - bestFitBlock.getStartAddress() + 1) > size) {
	            // Create a new block representing the unused portion after allocation
	            MemoryBlock newBlock = new MemoryBlock(bestFitBlock.getStartAddress() + size, bestFitBlock.getEndAddress(), "Unused");

	            // Adjust the end address of the best fit block to match the allocated size
	            bestFitBlock.setEndAddress(bestFitBlock.getStartAddress() + size - 1);

	            // Add the new block to the blockStorage
	            blockStorage.add(newBlock);
	        }

	        // Print a message indicating successful memory allocation using Best Fit
	        printOutput("Allocated memory for process " + processId + " using Best Fit.");

	        // Sort the blocks after allocation
	        sortBlocks();
	    } 
	    else {
	        // If no suitable hole found
	        printOutput("Error: Insufficient memory to allocate for process " + processId + " using Best Fit.");
	    }
	}

    //***************************************************************
    //
    //  Method:       allocateWorstFit
    // 
    //  Description:  allocates the Worst fit strategy 
    //
    //  Parameters:   String processId, long size
    //
    //  Returns:      N/A
    //
    //**************************************************************
	public void allocateWorstFit(String processId, long size) {
	    // Implementation of Worst Fit strategy
	    MemoryBlock worstFitBlock = null;

	    // Iterate through each memory block in the blockStorage
	    for (MemoryBlock block : blockStorage) {
	        // Check if the block is unused and has enough space for the process
	        if (block.getProcessId().equals("Unused") && (block.getEndAddress() - block.getStartAddress() + 1) >= size) {
	            // Update the worstFitBlock if it's the first suitable block or larger than the current worst fit
	            if (worstFitBlock == null || (block.getEndAddress() - block.getStartAddress()) > (worstFitBlock.getEndAddress() - worstFitBlock.getStartAddress())) {
	                worstFitBlock = block;
	            }
	        }
	    }

	    // If a suitable block is found
	    if (worstFitBlock != null) {
	        // Allocate memory for the process in the worst fit block
	        worstFitBlock.setProcessId(processId);

	        // If the block is larger than needed, split the block
	        if ((worstFitBlock.getEndAddress() - worstFitBlock.getStartAddress() + 1) > size) {
	            // Create a new block representing the unused portion after allocation
	            MemoryBlock newBlock = new MemoryBlock(worstFitBlock.getStartAddress() + size, worstFitBlock.getEndAddress(), "Unused");

	            // Adjust the end address of the worst fit block to match the allocated size
	            worstFitBlock.setEndAddress(worstFitBlock.getStartAddress() + size - 1);

	            // Add the new block to the blockStorage
	            blockStorage.add(newBlock);
	        }

	        // Print a message indicating successful memory allocation using Worst Fit
	        printOutput("Allocated memory for process " + processId + " using Worst Fit.");

	        // Sort the blocks after allocation
	        sortBlocks();
	    } else {
	        // If no suitable hole found
	        printOutput("Error: Insufficient memory to allocate for process " + processId + " using Worst Fit.");
	    }
	}

    //***************************************************************
    //
    //  Method:       handleRelease
    // 
    //  Description:  releases the process with same processId 
    //
    //  Parameters:   String processId
    //
    //  Returns:      N/A
    //
    //**************************************************************
	public void handleRelease(String processId) {
	    // Flag to track whether memory release was successful
	    boolean status = false;

	    // Iterate through each memory block in the blockStorage
	    for (MemoryBlock block : blockStorage) {
	        // Check if the block corresponds to the specified processId
	        if (block.getProcessId().equals(processId)) {
	            // Release memory by marking the block as "Unused"
	            block.setProcessId("Unused");
	            status = true;
	        }
	    }

	    // Print appropriate messages based on the release status
	    if (status) {
	        // If memory release was successful
	        printOutput("Released memory for process " + processId);
	    } else {
	        // If processId not found
	        printOutput("Error: Process " + processId + " not found in memory.");
	    }
	}


    //***************************************************************
    //
    //  Method:       compactMemory
    // 
    //  Description:  Compacts empty memory blocks 
    //
    //  Parameters:   N/A
    //
    //  Returns:      N/A
    //
    //**************************************************************
	public void compactMemory() {
	    // List to store compacted memory blocks
	    ArrayList<MemoryBlock> compactedList = new ArrayList<MemoryBlock>();

	    // Variable to track the previous memory block during iteration
	    MemoryBlock previousBlock = null;

	    // Flag to indicate whether a block has been added to the compactedList
	    boolean added = false;

	    // Iterate through each memory block in the blockStorage
	    for (MemoryBlock block : blockStorage) {
	        // Reset the added flag for each block
	        added = false;

	        // If this is the first block, initialize the previousBlock and add it to compactedList
	        if (previousBlock == null) {
	            previousBlock = block;
	            block.setByteSize(block.getEndAddress() - block.getStartAddress() + 1);
	            block.setEndAddress(0);
	            compactedList.add(block);
	        } else {
	            // Iterate through each block in compactedList to check for duplicates
	            for (MemoryBlock newBlock : compactedList) {
                    // If a matching processId is found, update the byte size
	                if (block.getProcessId().equals(newBlock.getProcessId())) {
	                    newBlock.setByteSize(newBlock.getByteSize() + (block.getEndAddress() - block.getStartAddress()) + 1);
	                    added = true;
	                }
	            }

	            // If the block is not a duplicate, add it to compactedList
	            if (!added) {
	                block.setByteSize(block.getEndAddress() - block.getStartAddress() + 1);
	                block.setStartAddress(0);
	                block.setEndAddress(0);
	                compactedList.add(block);
	            }
	        }
	    }
	    // Sort compactedList by processId
	    Collections.sort(compactedList, Comparator.comparing(MemoryBlock::getProcessId));

	    // Adjust start and end addresses in compactedList to form contiguous memory
	    long newStart = 0;
	    for (MemoryBlock block : compactedList) {
	        block.setStartAddress(newStart);
	        block.setEndAddress(newStart + block.getByteSize() - 1);
	        newStart += block.getByteSize();
	    }

	    // Clear blockStorage and add the compactedList
	    blockStorage.clear();
	    blockStorage.addAll(compactedList);

	    // Print a message indicating successful memory compaction
	    printOutput("Memory compacted.\n");
	}


	//***************************************************************
	//
	//  Method:       sortBlocks
	// 
	//  Description:  sorts the blocks based on starting address
	//
	//  Parameters:   N/A
	//
	//  Returns:      N/A
	//
	//**************************************************************
	public void sortBlocks() {
		Collections.sort(blockStorage, Comparator.comparingLong(MemoryBlock::getStartAddress));
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
	//  Method:       printMemoryStatus
	// 
	//  Description:  print the contents of the blockStorage list 
	//
	//  Parameters:   N/A
	//
	//  Returns:      N/A
	//
	//**************************************************************
	public void printMemoryStatus() {
	    // Print the status of allocated and unused memory regions
	    for (MemoryBlock block : blockStorage) {
	    	System.out.println(block);
	    	}
	    }
	
	
	}