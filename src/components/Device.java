package components;
import java.util.LinkedList;

// Device 0..1 
public class Device {
	public LinkedList<Integer> keyboard = new LinkedList<Integer>();
	public int printer = 0;
	
	// cpu get data from keyboard
	public int readKeyboard() {
		if (keyboard.size() > 1) {
			int res = keyboard.pollFirst();
			return res;
		}
		else {
			return keyboard.get(0);
		}
	}
	
	// input
	public void inputKeyboard(int input) {
		keyboard.offerLast(input);
	}
	
	public void keyboardClear() {
		keyboard = new LinkedList<Integer>();
	}
}
