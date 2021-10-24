package components;

// compute unsigned number
public class ALU {
	private int Y = 0;
	private int Z = 0;
	public condition_code CC = new condition_code();
	
	public boolean setY(int firstData){
		// reset Conditional code register and result register
		CC.resetCC();
		Z = 0;
		//checks if the data is appropriate before set it to the register
		if (firstData < Math.pow(2,16) && firstData >= 0){
			Y = firstData;
			return true;
		}
		else
			return false;
	}
	
	public void add(int secondData){
		Z = Y + secondData;
		if (Z >= Math.pow(2,16)) {
			CC.OVERFLOW();
		}
	}
	
	public void subtract(int secondData){
		Z = Y - secondData;
		if (Z < 0) {
			CC.UNDERFLOW();
		}
	}
	
	public void multiply(int secondData) {
		Z = Y * secondData;
		if (Z >= Math.pow(2,16)) {
			CC.OVERFLOW();
		}
	}
	
	public void divide(int secondData) {
		if (secondData == 0) {
			CC.DIVZERO();
		}
		else {
			Z = Y / secondData;
		}
	}
	
	public void equal(int secondData) {
		if (Y == secondData) {
			CC.EQUALORNOT();
		}
	}
	
	public void and(int secondData) {
		Z = Y & secondData;
	}
	
	public void or(int secondData) {
		Z = Y | secondData;
	}
	
	public void not(int secondData) {
		Z = ~ secondData;
	}
	
	public int getResult() {
		return Z;
	}
}
