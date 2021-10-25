package components;

// This class control all the components' object and can decode the instruction and execute them

import java.io.*;
import conversion.*;

public class CPU_Control{
	// all the components including Memory
	public ProgramCounter PC = new ProgramCounter();
	public General_Purpose_Registers GPRs = new General_Purpose_Registers();
	public Instruction_Register IR = new Instruction_Register();
	public Index_Registers IXR = new Index_Registers();
	public Memory_Address_Register MAR = new Memory_Address_Register();
	public Memory_Buffer_Register MBR = new Memory_Buffer_Register();
	public Machine_Fault_Register MFR = new Machine_Fault_Register();
	public Cache cache = new Cache();
	public ALU alu = new ALU();
	// machine fault status 
	private int mfindex = 0;
	// halt or not
	public int halt = 0;

	public CPU_Control(){
	}
// This sets the initial components of the machine (initial or restart) and load IPL.txt
	public void initial(){
		PC = new ProgramCounter(0);
		GPRs = new General_Purpose_Registers();
		IR = new Instruction_Register();
		IXR = new Index_Registers(0, 100, 1000);
		MAR = new Memory_Address_Register();
		MBR = new Memory_Buffer_Register();
		MFR.resetMFR();
		cache = new Cache();
		cache.CPUwrite(1, 6);
		cache.CPUwrite(6, 0b0000000000000000);
		
		//reset halt
		halt = 0; 
		
		// read IPX.txt and load it to the memory
		try {
			String pathname = "./IPL.txt";
			File IPL = new File(pathname);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(IPL));
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			while (line != null) {
				line  = br.readLine();
				if (line == null) break;
				String[] loadtoMem = line.split(" ");
				mfindex = cache.writeCache(ConvertHexToDec.convertHexToDec(loadtoMem[0])+8, ConvertHexToDec.convertHexToDec(loadtoMem[1]));
				checkaddress();
			}
			br.close();
		}	catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//  This sets the initial components of the machine (initial or restart) and load program1
	public void loadprogram1(){
		PC = new ProgramCounter(0);
		GPRs = new General_Purpose_Registers();
		IR = new Instruction_Register();
		IXR = new Index_Registers(0, 100, 1000);
		MAR = new Memory_Address_Register();
		MBR = new Memory_Buffer_Register();
		MFR.resetMFR();
		cache = new Cache();
		cache.CPUwrite(1, 6);
		cache.CPUwrite(6, 0b0000000000000000);
		
		//reset halt
		halt = 0; 
		
		// read IPX.txt and load it to the memory
		try {
			String pathname = "./program1.txt";
			File IPL = new File(pathname);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(IPL));
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			while (line != null) {
				line  = br.readLine();
				if (line == null) break;
				String[] loadtoMem = line.split(" ");
				mfindex = cache.writeCache(ConvertHexToDec.convertHexToDec(loadtoMem[0])+8, ConvertHexToDec.convertHexToDec(loadtoMem[1]));
				checkaddress();
			}
			br.close();
		}	catch (Exception e) {
			e.printStackTrace();
		}
	}
	
// This function is for post-Project #1 to run a single cycle of our machine simulator.
	public void runsinglestep(){
		// if the machine halts, the CPU will not work any more
		if (halt == 1) return;
		// set the MAR according to the PC
		MAR.setMemaddress(PC.getPCaddress());
		// PC += 1
		PC.PCPlus();
		// get the instruction from the Memory to MBR
		mfindex = cache.readCache(MAR.getMemaddress()+8);
		checkaddress();
		MBR.setData(cache.readCache(MAR.getMemaddress()+8));
		// set the instruction to IR from MBR
		IR.setinstruction(MBR.getData());
		// decode and get the opcode and then execute instruction accordingly
		switch(IR.getopcode()){
			case 1:
				Load();
				break;
			case 2:
				Store();
				break;
			case 0:
				Halt();
				break;
			case 3:
				LDA();
				break;
			case 33:
				LDX();
				break;
			case 34:
				STX();
				break;
			case 8:
				JZ();
				break;
			case 9:
				JNE();
				break;
			case 10:
				JCC();
				break;
			case 11:
				JMA();
				break;
			case 12:
				JSR();
				break;
			case 13:
				RFS();
				break;
			case 14:
				SOB();
				break;
			case 15:
				JGE();
				break;
			case 4:
				AMR();
				break;
			case 5:
				SMR();
				break;
			case 6:
				AIR();
				break;
			case 7:
				SIR();
				break;
			case 16:
				MLT();
				break;
			case 17:
				DVD();
				break;
			case 18:
				TRR();
				break;
			case 19:
				AND();
				break;
			case 20:
				ORR();
				break;
			case 21:
				NOT();
				break;
			case 25:
				SRC();
				break;
			case 26:
				RRC();
				break;
			default:
				MFR.setFault(2);
				halt = 1;
		}
	}
	
// this function is for load and store button in UI
	public void runinstruction() {
		// if the machine halts, the CPU will not work any more
		if (halt == 1) return;
		switch(IR.getopcode()){
			case 1:
				Load();
				break;
			case 2:
				Store();
				break;
			default:
				MFR.setFault(2);
				halt = 1;
		}
	}
	
// This acts as the load instruction
	public void Load(){
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		}
		else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
		}
		// set the correct EA to the MAR
		MAR.setMemaddress(EA);
		// read the Memory and fetch the data to the MBR
		mfindex = cache.readCache(MAR.getMemaddress()+8);
		checkaddress();
		MBR.setData(cache.readCache(MAR.getMemaddress()+8));
		// load the data in MBR to the target register
		GPRs.setregister(IR.getregister(), MBR.getData());
	}
	
// Store instruction
	public void Store(){
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		}
		else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
		}
		// set the correct EA to the MAR
		MAR.setMemaddress(EA);
		// get the data from GPRs to MBR
		MBR.setData(GPRs.getregister(IR.getregister()));
		// write the data in MBR to the Memory with the address of MAR
		mfindex = cache.writeCache(MAR.getMemaddress()+8, MBR.getData());
		checkaddress();
	}
	
	// This acts as the LDA instruction
	public void LDA(){
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		}
		else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
		}
		
		// load the EA to the target register
		GPRs.setregister(IR.getregister(), EA);
	}
	
	// This acts as the LDX instruction
	public void LDX(){
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		}
		else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
		}
		// set the correct EA to the MAR
		MAR.setMemaddress(EA);
		// read the Memory and fetch the data to the MBR
		mfindex = cache.readCache(MAR.getMemaddress()+8);
		checkaddress();
		MBR.setData(cache.readCache(MAR.getMemaddress()+8));
		// load the data in MBR to the target register
		IXR.setregister(IR.getindexregister(), MBR.getData());
	}
	
	// STX instruction
	public void STX(){
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		}
		else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
		}
		// set the correct EA to the MAR
		MAR.setMemaddress(EA);
		// get the data from GPRs to MBR
		MBR.setData(IXR.getregister(IR.getindexregister()));
		// write the data in MBR to the Memory with the address of MAR
		mfindex = cache.writeCache(MAR.getMemaddress()+8, MBR.getData());
		checkaddress();
	}
	
	// JZ instruction
	public void JZ() {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		}
		else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
		}
		
		// if c(r) != 0, then PC <- EA
		if (GPRs.getregister(IR.getregister()) == 0) {
			PC.setPCaddress(EA);
		}
	}
	
	// JNE instruction
	public void JNE() {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		}
		else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
		}
		
		// if c(r) != 0, then PC <- EA
		if (GPRs.getregister(IR.getregister()) != 0) {
			PC.setPCaddress(EA);
		}
	}
	
	// JCC instruction
	public void JCC() {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		}
		else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
		}
		
		if (alu.CC.getccbit(IR.getregister()) == 1) {
			PC.setPCaddress(EA);
		}
	}
	
	// JMA instruction
	public void JMA() {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		}
		else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
		}
		
		PC.setPCaddress(EA);
	}

	// JSR instruction
	public void JSR() {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		}
		else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
		}
		
		GPRs.setregister(3, PC.getPCaddress());
		PC.setPCaddress(EA);
	}

	// RFS instruction
	public void RFS() {
		PC.setPCaddress(GPRs.getregister(3));
		GPRs.setregister(0, IR.getaddress());
	}
	
	// SOB instruction
	public void SOB() {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		}
		else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
		}
		
		GPRs.setregister(IR.getregister(), GPRs.getregister(IR.getregister()) - 1);
		if (GPRs.getregister(IR.getregister()) != 0) {
			PC.setPCaddress(EA);
		}
	}
	
	// JGE instruction
	public void JGE() {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		}
		else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
		}
		
		if (GPRs.getregister(IR.getregister()) >= 0) {
			PC.setPCaddress(EA);
		}
	}
	
	// AMR instruction
	public void AMR() {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		}
		else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
		}
		
		alu.setY(GPRs.getregister(IR.getregister()));
		MAR.setMemaddress(EA);
		mfindex = cache.readCache(MAR.getMemaddress() + 8);
		checkaddress();
		MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
		alu.add(MBR.getData());
		GPRs.setregister(IR.getregister(), alu.getResult());
	}
	
	// SMR instruction
	public void SMR() {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		}
		else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
			else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress()+8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress()+8));
				EA = MBR.getData();
			}
		}
		
		alu.setY(GPRs.getregister(IR.getregister()));
		MAR.setMemaddress(EA);
		mfindex = cache.readCache(MAR.getMemaddress() + 8);
		checkaddress();
		MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
		alu.subtract(MBR.getData());
		GPRs.setregister(IR.getregister(), alu.getResult());
	}
	
	// AIR instruction
	public void AIR() {
		alu.setY(IR.getregister());
		alu.add(IR.getaddress());
		GPRs.setregister(IR.getregister(), alu.getResult());
	}
	
	// SIR instruction
	public void SIR() {
		alu.setY(IR.getregister());
		alu.subtract(IR.getaddress());
		GPRs.setregister(IR.getregister(), alu.getResult());
	}
	
	// MLT instruction
	public void MLT() {
		if (IR.getregister() != 0 && IR.getregister() != 2) {
			MFR.setFault(2);
			halt = 1;
			return;
		}
		alu.setY(GPRs.getregister(IR.getregister()));
		alu.multiply(GPRs.getregister(IR.getindexregister()));
		GPRs.setregister(IR.getregister() + 1, alu.getResult() % (int) Math.pow(2, 16));
		GPRs.setregister(IR.getregister(), alu.getResult() / (int) Math.pow(2, 16));
	}
	
	// DVD instruction
	public void DVD() {
		if (IR.getregister() != 0 && IR.getregister() != 2) {
			MFR.setFault(2);
			halt = 1;
			return;
		}
		if (IR.getindexregister() != 0 && IR.getindexregister() != 2) {
			MFR.setFault(2);
			halt = 1;
			return;
		}
		alu.setY(GPRs.getregister(IR.getregister()));
		alu.divide(GPRs.getregister(IR.getindexregister()));
		GPRs.setregister(IR.getregister(), alu.getResult());
		alu.remainder(GPRs.getregister(IR.getindexregister()));
		GPRs.setregister(IR.getregister() + 1, alu.getResult());
	}
	
	// TRR instruction
	public void TRR() {
		alu.setY(GPRs.getregister(IR.getregister()));
		alu.equal(GPRs.getregister(IR.getindexregister()));
	}
	
	// AND instruction
	public void AND() {
		alu.setY(GPRs.getregister(IR.getregister()));
		alu.and(GPRs.getregister(IR.getindexregister()));
		GPRs.setregister(IR.getregister(), alu.getResult());
	}
	
	// ORR instruction
	public void ORR() {
		alu.setY(GPRs.getregister(IR.getregister()));
		alu.or(GPRs.getregister(IR.getindexregister()));
		GPRs.setregister(IR.getregister(), alu.getResult());
	}
	
	// NOT instruction
	public void NOT() {
		alu.not(GPRs.getregister(IR.getregister()));
		GPRs.setregister(IR.getregister(), alu.getResult());
	}
	
	// SRC instruction
	public void SRC() {
		int AL = IR.getindexregister() / 2;
		int LR = IR.getindexregister() % 2;
		alu.shift(GPRs.getregister(IR.getregister()), AL, LR, IR.getaddress());
		GPRs.setregister(IR.getregister(), alu.getResult());
 	}
	
	// RRC instruction
	public void RRC() {
		int AL = IR.getindexregister() / 2;
		if (AL != 1) {
			MFR.setFault(2);
			halt = 1;
			return;
		}
		int LR = IR.getindexregister() % 2;
		alu.rotate(GPRs.getregister(IR.getregister()), LR, IR.getaddress());
		GPRs.setregister(IR.getregister(), alu.getResult());
	}
	
// check the access of memory is write or not. If not, we go to set the MFR and get solution which is halt right now
	public void checkaddress() {
		if (mfindex == -1) {
			MFR.setFault(0);
			machinefault();
		}
		else if (mfindex == -2) {
			MFR.setFault(3);
			machinefault();
		}
	}

// deal with the machine fault
	public void machinefault() {
		if (MFR.getFault() >= 0 && MFR.getFault() < 4) {
			// find the solution's address which is 6
			MAR.setMemaddress(1);
			MBR.setData(cache.CPUaccess(MAR.getMemaddress()));
			//get the solution instruction which is halt right now
			MAR.setMemaddress(MBR.getData());
			MBR.setData(cache.CPUaccess(MAR.getMemaddress()));
			IR.setinstruction(MBR.getData());
			runinstruction();
		}
	}
	
	public void Halt() {
		halt = 1;
	}
}