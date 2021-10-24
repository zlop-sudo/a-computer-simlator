package components;

public class Memory_Address_Register{
	//initializes the MAR
	private int Memaddress = 0;

	public Memory_Address_Register(){
	}
	
	// gets the returns the current address in the MAR
	public int getMemaddress(){
		return Memaddress;
	}

	//Gets the new address, determines if it is appropriate, then sets 
	public boolean setMemaddress(int newaddress){
		if (newaddress < Math.pow(2,12) && newaddress >= 0){
			Memaddress = newaddress;
			return true;
		}
		else
			return false;
	}
}