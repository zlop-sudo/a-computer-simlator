package components;

public class Instruction_Register{
	private int instruction = 0;
	private int opcode = 0;
	private int register = 0;
	private int indexregister = 0;
	private int indirect = 0;
	private int address = 0;

	public Instruction_Register(){
	}

	public boolean setinstruction(int newinstruction){
		// sets all of the appropriate values according to the instruction (part of decode)
		if (newinstruction < Math.pow(2,16) && newinstruction >= 0){
			instruction = newinstruction;
			opcode = instruction / (int) Math.pow(2, 10);
			register = instruction % (int) Math.pow(2, 10) / (int) Math.pow(2, 8);
			indexregister = instruction % (int) Math.pow(2, 8) / (int) Math.pow(2, 6);
			indirect = instruction % (int) Math.pow(2, 6) / (int) Math.pow(2, 5);
			address = instruction % (int) Math.pow(2, 5);
			return true;
		}
		else
			return false;
	}
	
	public int getinstruction(){
		return instruction;
	}

	public int getopcode(){
		return opcode;
	}

	public int getregister(){
		return register;
	}

	public int getindexregister(){
		return indexregister;
	}

	public int getindirect(){
		return indirect;
	}

	public int getaddress(){
		return address;
	}
}