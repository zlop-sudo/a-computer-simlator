package components;

public class Vector_Registers {
	public int[] vector;
	
	public void initial(int length) {
		vector = new int[length];
	}
	
	public void writeElement(int index, int element) {
		vector[index] = element;
	}
	
	public int readElement(int index) {
		return vector[index];
	}
}
