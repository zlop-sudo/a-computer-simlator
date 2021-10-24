package components;

public class ProgramCounter {
	//initializes the PC to 0
	private int PCaddress = 0;

	public ProgramCounter(){
	}

	//initial the PC address if appropriate
	public ProgramCounter(int PCaddress){
		if (PCaddress < Math.pow(2,12) && PCaddress >= 0){
			this.PCaddress = PCaddress;
		}
	}

	//increments the PC
	public void PCPlus(){
		PCaddress++;
	}

	//gets the PC address
	public int getPCaddress(){
		return PCaddress;
	}

	//re-assigns the PC address if required and fits the appropriate range of the machine
	public boolean setPCaddress(int newaddress){
		if (newaddress < Math.pow(2,12) && newaddress >= 0){
			PCaddress = newaddress;
			return true;
		}
		else
			return false;
	}
}
