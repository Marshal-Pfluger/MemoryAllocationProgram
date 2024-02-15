//********************************************************************
//
//  Author:        Marshal Pfluger
//
//  Program #:     Capstone Project
//
//  File Name:     MemoryBlock.java
//
//  Course:        COSC 4302 Operating Systems
//
//  Due Date:      12/08/2023
//
//  Instructor:    Prof. Fred Kumi
//
//  Java Version:  17
//
//  Description:   Virtualize memory block
//********************************************************************
public class MemoryBlock {
    private long startAddress;
    private long endAddress;
    private String processId;
    private long byteSize;

    //***************************************************************
    //
    //  Method:       constructor 
    // 
    //  Description:  single argument constructor 
    //
    //  Parameters:   Long initialMemory
    //
    //  Returns:      N/A
    //
    //**************************************************************
    public MemoryBlock(Long initialMemory) {
    	this.setStartAddress(0);
    	this.setEndAddress(initialMemory - 1);
    	this.setProcessId("Unused");
    }
    
    //***************************************************************
    //
    //  Method:       constructor 
    // 
    //  Description:  triple argument constructor 
    //
    //  Parameters:   long startAddress, long endAddress, String processId
    //
    //  Returns:      N/A
    //
    //**************************************************************
    public MemoryBlock(long startAddress, long endAddress, String processId) {
        this.setStartAddress(startAddress);
        this.setEndAddress(endAddress);
        this.setProcessId(processId);
    }

	public String getProcessId() {
		return this.processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public long getEndAddress() {
		return this.endAddress;
	}

	public void setEndAddress(long endAddress) {
		this.endAddress = endAddress;
	}

	public long getStartAddress() {
		return this.startAddress;
	}

	public void setStartAddress(long startAddress) {
		this.startAddress = startAddress;
	}
	
	public void setByteSize(long size) {
		this.byteSize = size;
	}
	
	public long getByteSize() {
		return this.byteSize;
	}
	
    //***************************************************************
    //
    //  Method:       toString
    // 
    //  Description:  returns a string of the object 
    //
    //  Parameters:   N/A
    //
    //  Returns:      String
    //
    //**************************************************************
	public String toString() {
		return "Addresses [" + getStartAddress() + ":" + getEndAddress() + "] Process " + getProcessId();
		
	}
}