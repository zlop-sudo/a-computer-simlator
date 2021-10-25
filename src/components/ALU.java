package components;

// compute unsigned number (except shift and rotate)
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
		Z = 0;
		for (int i = 15; i >= 0; i--) {
			Z += Math.pow(((secondData / (int) Math.pow(2, i)) + 1 ) % 2 * 2, i);
			secondData %= (int) Math.pow(2, i);
		}
	}
	
	public void remainder(int secondData) {
		if (secondData == 0) {
			CC.DIVZERO();
		}
		else {
			Z = Y % secondData;
		}
	}
	
	public void shift(int secondData, int AL, int LR, int count) {
		if (AL == 0) {
			if (LR == 1) {
				short signed = (short) secondData;
				Z = (int) (signed << count); 
			}
			else if (LR == 0) {
				short signed = (short) secondData;
				Z = (int) (signed >> count); 
			}
		}
		else if (AL == 1) {
			if (LR == 1) {
				Z = secondData * (int) Math.pow(2, count);
				short lowbits = (short) Z;
				Z = (int) lowbits;
			}
			else if (LR == 0) {
				short signed = (short) secondData;
				Z = (int) (signed >>> count); 
			}
		}
	}
	
	public void rotate(int secondData, int LR, int count) {
		if (LR == 1) {
			Z = secondData * (int) Math.pow(2, count);
			int highbits = Z / (int) Math.pow(2, 16);
			short lowbits = (short) Z;
			Z = (int) lowbits;
			Z += highbits;
		}
		else if (LR == 0) {
			int lowbits = secondData % (int) Math.pow(2, count);
			short signed = (short) secondData;
			Z = (int) (signed >>> count); 
			Z += lowbits * (int) Math.pow(2, 16 - count);
		}
	}
	
	public int getResult() {
		return Z;
	}
}
