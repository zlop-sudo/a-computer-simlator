package components;

// this is the class that GUI can use and all the components are in the object CPU

import conversion.ConvertBinToDec;

public class interact {
	public CPU_Control CPU = new CPU_Control();
	public int pro1inputcnt = 0; // count for the 20 numbers input
	
	public interact() {
		CPU.initial();
	}
	
	public void IPL_button() {
		CPU.initial();
	}
	
	public int SS_button() {
		CPU.runsinglestep();
		return CPU.halt;	// return halt or not, if hault is 1 then it means something went wrong, and the display of MFR will change
	}
	
	// if the opcode is correct which is 000001
	public int Load_button(String InputofBin) {
		int Input = ConvertBinToDec.convertbintodec(InputofBin);
		CPU.IR.setinstruction(Input);
		CPU.runinstruction();
		return CPU.halt;	// return halt or not, if hault is 1 then it means something went wrong, and the display of MFR will change
	}
	
	// if the opcode is correct which is 000010
	public int Store_button(String InputofBin) {
		int Input = ConvertBinToDec.convertbintodec(InputofBin);
		CPU.IR.setinstruction(Input);
		CPU.runinstruction();
		return CPU.halt;	// return halt or not, if hault is 1 then it means something went wrong, and the display of MFR will change
	}
	
	// LD_button can change the value of each components according to the order of the GUI
	public boolean LD_button(String InputofBin, int index) {
		int Input = ConvertBinToDec.convertbintodec(InputofBin);
		switch (index) {
		case 1:
			return CPU.GPRs.setregister(0, Input);
		case 2:
			return CPU.GPRs.setregister(1, Input);
		case 3:
			return CPU.GPRs.setregister(2, Input);
		case 4:
			return CPU.GPRs.setregister(3, Input);
		case 5:
			return CPU.IXR.setregister(1, Input);
		case 6:
			return CPU.IXR.setregister(2, Input);
		case 7:
			return CPU.IXR.setregister(3, Input);
		case 8:
			return CPU.PC.setPCaddress(Input);
		case 9:
			return CPU.MAR.setMemaddress(Input);
		case 10:
			return CPU.MBR.setData(Input);
		}
		return false;
	}
	
	// This function can return the value of each components according to the order of the GUI
	public int get_number(int index) {
		switch (index) {
		case 1: 
			return CPU.GPRs.getregister(0);
		case 2:
			return CPU.GPRs.getregister(1);
		case 3:
			return CPU.GPRs.getregister(2);
		case 4:
			return CPU.GPRs.getregister(3);
		case 5:
			return CPU.IXR.getregister(1);
		case 6:
			return CPU.IXR.getregister(2);
		case 7:
			return CPU.IXR.getregister(3);
		case 8:
			return CPU.PC.getPCaddress();
		case 9:
			return CPU.MAR.getMemaddress();
		case 10:
			return CPU.MBR.getData();
		case 11:
			return CPU.IR.getinstruction();
		case 12:
			return CPU.MFR.getFault();
		case 13:
			return CPU.device.keyboard;
		case 14:
			return CPU.device.printer;
		}
		return 0;
	}
	
	// This function can put 20 numbers to the device 0
	public int program1input(String inputstring) {
		int input = Integer.parseInt(inputstring);
		CPU.device.keyboard = input;
		pro1inputcnt++;
		if (pro1inputcnt == 20) {
			return 0;
		}
		else {
			return 1;
		}
	}
	
	// This function can load the program1 to memory
	public void loadprogram1() {
		pro1inputcnt = 0;
		CPU.loadprogram1();
	}
	
	// This function return the number used to compare with the 20 numbers
	public int pro1target() {
		return CPU.cache.readCache(122 + 8);
	}
	
	// This function is for printing the 20 numbers together
	public int pro1_20numbers(int i) {
		return CPU.cache.readCache(101 + 8 + i);
	}
	
	// This function return the value of device 1
	public int printer() {
		return CPU.device.printer;
	}
}
