package components;

// It now can store the machine Fault 0, 2, 3
// ID	Fault
// 0	Illegal Memory Address to Reserved Locations MFR set to binary 0001
// 1	Illegal TRAP code  MFR set to binary 0010
// 2	Illegal Operation Code MFR set to 0100
// 3	Illegal Memory Address beyond 2048 (memory installed)  MFR set to binary 1000

public class Machine_Fault_Register {
	private int Faultindex = -1;
	
	public Machine_Fault_Register() {
	}
	
	public int getFault() {
		return Faultindex;
	}
	
	public boolean setFault(int Faultindex) {
		if (Faultindex >= 0 && Faultindex < 4) {
			this.Faultindex = Faultindex;
			return true;
		}
		else 
			return false;
	}
	
	public void resetMFR() {
		Faultindex = 0;
	}
}
