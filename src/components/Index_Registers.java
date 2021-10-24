package components;

public class Index_Registers{
	//initializes the index registers
	private int[] registers = {0, 0, 0};

	public Index_Registers(){
	}

	public Index_Registers(int register1, int register2, int register3){
		// recreate the IXR and determines which register is appropriate and limit the value
		if (register1 < Math.pow(2, 12) && register1 >= 0)
			registers[0] = register1;
		if (register2 < Math.pow(2, 12) && register2 >= 0)
			registers[1] = register2;
		if (register3 < Math.pow(2, 12) && register3 >= 0)
			registers[2] = register3;
	}

	public int getregister(int index){
		if (index >= 1 && index < 4)
			return registers[index - 1];
		// fetches the index if it is between 1 and 3
		else
			return -1;
	}
	
	public boolean setregister(int index, int newvalue) {
		// set the IXRs from GUI
		// limit the index and value
		if (index >= 1 && index < 4) {
			if (newvalue < Math.pow(2, 12) && newvalue >= 0) {
				registers[index - 1] = newvalue;
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}
}