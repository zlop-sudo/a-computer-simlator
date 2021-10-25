package components;

public class Memory{
	// sets the initial conditions for the memory
	protected int[] Memwords = new int[2048];
	protected int start = 0;

	protected Memory(){
	}

	protected Memory(int size, int start){
		// determines if new words directory is needed, else it sets start to itself
		if (size >= 2048 && size <= 4096){
			Memwords = new int[size];
		}
		if (start >= 0 && start < size){
			this.start = start;
		}
	}

	protected int readMem(int address){
		//determines if the memory address is acceptable or not, else throws and error
		if (address >= start && address < Memwords.length)
			return Memwords[address];
		else if (address < start && address >= 0) {
			return -1;
		}
		else if (address > Memwords.length) {
			return -2;
		}
		else
			return -3;
	}
	
	//	used by CPU_control to access the reserved location
	protected int CPUaccess(int address) {
		return Memwords[address];
	}

	protected int writeMem(int address, int newData){
		// writes to memory if the address is within the acceptable range and if the new data is within the limits of our bit-limits
		if (address >= start && address < Memwords.length){
			if (newData >= 0 && newData < Math.pow(2, 16)){
				Memwords[address] = newData;
				return 0;
			}
			else
				return 1;
		}
		else if (address < start && address >= 0) {
			return -1;
		}
		else if (address > Memwords.length) {
			return -2;
		}
		else
			return -3;
	}
	
	//	used by CPU_control to access the reserved location
	protected void CPUwrite(int address, int newData) {
		Memwords[address] = newData;
	}
}