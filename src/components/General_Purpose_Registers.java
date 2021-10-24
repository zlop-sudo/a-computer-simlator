package components;

public class General_Purpose_Registers{
	// defines four General_Purpose_Registers
	private int[] registers = {0, 0, 0, 0};

	public General_Purpose_Registers(){
	}

	public int getregister(int index){
		// checks if the index value is between 0 and 3 and then grabs it, otherwise throws an error
		if (index >= 0 && index < 4)
			return registers[index];
		else
			return -1;
	}

	public boolean setregister(int index, int newvalue){
		// sets the register according to the new value being passed in
		//limit the index and the value
		if (index >= 0 && index < 4){
			if (newvalue < Math.pow(2, 16) && newvalue >= 0){
				registers[index] = newvalue;
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}
}