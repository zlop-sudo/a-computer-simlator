package components;

// CC link to the ALU
public class condition_code {
	private int OVERFLOW = 0;
	private int UNDERFLOW = 0;
	private int DIVZERO = 0;
	private int EQUALORNOT = 0;
	
	public int getccbit(int cc) {
		switch(cc) {
		case 0:
			return OVERFLOW;
		case 1:
			return UNDERFLOW;
		case 2:
			return DIVZERO;
		case 3:
			return EQUALORNOT;
		}
		return -1;
	}
	
	public void OVERFLOW() {
		OVERFLOW = 1;
	}
	
	public void UNDERFLOW() {
		UNDERFLOW = 1;
	}
	
	public void DIVZERO() {
		DIVZERO = 1;
	}
	
	public void EQUALORNOT() {
		EQUALORNOT = 1;
	}
	
	public void resetCC() {
		OVERFLOW = 0;
		UNDERFLOW = 0;
		DIVZERO = 0;
		EQUALORNOT = 0;
	}
}
