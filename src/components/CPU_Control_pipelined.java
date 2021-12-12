package components;

//This class control all the components' object and can decode the instruction and execute them
//This is the new version which has been simply pipelined

import java.io.*;
import java.util.LinkedList;

import conversion.ConvertHexToDec;

public class CPU_Control_pipelined {
// all the components including Memory
	public ProgramCounter PC = new ProgramCounter();
	public General_Purpose_Registers GPRs = new General_Purpose_Registers();
	public Index_Registers IXR = new Index_Registers();
	public Memory_Address_Register MAR = new Memory_Address_Register();
	public Memory_Buffer_Register MBR = new Memory_Buffer_Register();
	public Machine_Fault_Register MFR = new Machine_Fault_Register();
	public Cache cache = new Cache();
	public ALU alu = new ALU();
	public Device device = new Device();
	public Float_ALU FALU = new Float_ALU();
	public Float_Registers FRs = new Float_Registers();
	public Vector_Registers VR1 = new Vector_Registers();
	public Vector_Registers VR2 = new Vector_Registers();

	public Instruction_Register[] Instruction_Buffer = new Instruction_Register[5];
	public LinkedList<Integer>[] Stages = new LinkedList[5]; // each instruction has a linked list of stages (0~4) (0
																// represents stall)

	private int mfindex = 0;
	// halt or not
	public int halt = 0;

	public CPU_Control_pipelined() {
	}

// This sets the initial components of the machine (initial or restart) and load IPL.txt
	public void initial() {
		PC = new ProgramCounter(0);
		GPRs = new General_Purpose_Registers();
		Instruction_Buffer = new Instruction_Register[5];
		Stages = new LinkedList[5];
		IXR = new Index_Registers(0, 100, 1000);
		MAR = new Memory_Address_Register();
		MBR = new Memory_Buffer_Register();
		FALU = new Float_ALU();
		FRs = new Float_Registers();
		MFR.resetMFR();
		device = new Device();
		cache = new Cache();
		cache.CPUwrite(1, 6);
		cache.CPUwrite(6, 0b0000000000000000);

		// reset halt
		halt = 0;

		// read IPX.txt and load it to the memory
		try {
			String pathname = "./IPL.txt";
			File IPL = new File(pathname);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(IPL));
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			while (line != null) {
				line = br.readLine();
				if (line == null)
					break;
				String[] loadtoMem = line.split(" ");
				mfindex = cache.writeCache(ConvertHexToDec.convertHexToDec(loadtoMem[0]) + 8,
						ConvertHexToDec.convertHexToDec(loadtoMem[1]));
				checkaddress();
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		initialPipeline(0);
	}

//  This sets the initial components of the machine (initial or restart) and load program1
	public void loadprogram1() {
		PC = new ProgramCounter(0);
		GPRs = new General_Purpose_Registers();
		Instruction_Buffer = new Instruction_Register[5];
		Stages = new LinkedList[5];
		IXR = new Index_Registers(0, 100, 1000);
		MAR = new Memory_Address_Register();
		MBR = new Memory_Buffer_Register();
		FALU = new Float_ALU();
		FRs = new Float_Registers();
		MFR.resetMFR();
		device = new Device();
		cache = new Cache();
		cache.CPUwrite(1, 6);
		cache.CPUwrite(6, 0b0000000000000000);

		// reset halt
		halt = 0;

		// read IPX.txt and load it to the memory
		try {
			String pathname = "./program1.txt";
			File IPL = new File(pathname);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(IPL));
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			while (line != null) {
				line = br.readLine();
				if (line == null)
					break;
				String[] loadtoMem = line.split(" ");
				mfindex = cache.writeCache(ConvertHexToDec.convertHexToDec(loadtoMem[0]) + 8,
						ConvertHexToDec.convertHexToDec(loadtoMem[1]));
				checkaddress();
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		initialPipeline(0);
	}

//  This sets the initial components of the machine (initial or restart) and load program2
	public void loadprogram2() {
		PC = new ProgramCounter(0);
		GPRs = new General_Purpose_Registers();
		Instruction_Buffer = new Instruction_Register[5];
		Stages = new LinkedList[5];
		IXR = new Index_Registers(0, 100, 1000);
		MAR = new Memory_Address_Register();
		MBR = new Memory_Buffer_Register();
		FALU = new Float_ALU();
		FRs = new Float_Registers();
		MFR.resetMFR();
		device = new Device();
		cache = new Cache();
		cache.CPUwrite(0, 200);
		cache.CPUwrite(1, 6);
		cache.CPUwrite(6, 0b0000000000000000);

		// reset halt
		halt = 0;

		// read IPX.txt and load it to the memory
		try {
			String pathname = "./program2.txt";
			File IPL = new File(pathname);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(IPL));
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			while (line != null) {
				line = br.readLine();
				if (line == null)
					break;
				String[] loadtoMem = line.split(" ");
				mfindex = cache.writeCache(ConvertHexToDec.convertHexToDec(loadtoMem[0]) + 8,
						ConvertHexToDec.convertHexToDec(loadtoMem[1]));
				checkaddress();
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		initialPipeline(0);
	}

// This function is for post-Project #1 to run a single cycle of our machine simulator.
	public void runsinglestep() {
		// if the machine halts, the CPU will not work any more
		if (halt == 1)
			return;
		for (int i = 0; i < 5; i++) {
			LinkedList<Integer> stages = Stages[i];
			int stage = stages.pollFirst();
			Instruction_Register IR = Instruction_Buffer[i];
			if (stage < 3) {
				continue;
			} else if (stage == 4) {
				for (int j = 0; j < 4; j++) {
					Instruction_Buffer[j] = Instruction_Buffer[j + 1];
					Stages[j] = Stages[j + 1];
				}
				// set the MAR according to the PC
				MAR.setMemaddress(PC.getPCaddress() + 5);
				// PC += 1
				PC.PCPlus();
				// get the instruction from the Memory to MBR
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				// set the instruction to IR from MBR
				Instruction_Buffer[4].setinstruction(MBR.getData());
				// decode and get the opcode and then execute instruction accordingly
				LinkedList<Integer> newStages = new LinkedList<Integer>();
				initialStages(newStages, 4);
				Stages[4] = newStages;
			} else {
				switch (IR.getopcode()) {
				case 1:
					Load(IR);
					break;
				case 2:
					Store(IR);
					break;
				case 0:
					Halt(IR);
					break;
				case 3:
					LDA(IR);
					break;
				case 33:
					LDX(IR);
					break;
				case 34:
					STX(IR);
					break;
				case 8:
					JZ(IR);
					break;
				case 9:
					JNE(IR);
					break;
				case 10:
					JCC(IR);
					break;
				case 11:
					JMA(IR);
					break;
				case 12:
					JSR(IR);
					break;
				case 13:
					RFS(IR);
					break;
				case 14:
					SOB(IR);
					break;
				case 15:
					JGE(IR);
					break;
				case 4:
					AMR(IR);
					break;
				case 5:
					SMR(IR);
					break;
				case 6:
					AIR(IR);
					break;
				case 7:
					SIR(IR);
					break;
				case 16:
					MLT(IR);
					break;
				case 17:
					DVD(IR);
					break;
				case 18:
					TRR(IR);
					break;
				case 19:
					AND(IR);
					break;
				case 20:
					ORR(IR);
					break;
				case 21:
					NOT(IR);
					break;
				case 25:
					SRC(IR);
					break;
				case 26:
					RRC(IR);
					break;
				case 49:
					IN(IR);
					break;
				case 50:
					OUT(IR);
					break;
				case 24:
					TRAP(IR);
					break;
				case 27:
					FADD(IR);
					break;
				case 28:
					FSUB(IR);
					break;
				case 31:
					CNVRT(IR);
					break;
				case 40:
					LDFR(IR);
					break;
				case 41:
					STFR(IR);
					break;
				case 29:
					VADD(IR);
					break;
				case 30:
					VSUB(IR);
					break;
				default:
					MFR.setFault(2);
					halt = 1;
				}
				if (IR.getopcode() >= 8 && IR.getopcode() <= 15) {
					initialPipeline(PC.getPCaddress());
				}
				if (IR.getopcode() == 24) {
					initialPipeline(PC.getPCaddress());
				}
			}
		}
	}

// this function is for load and store button in UI
	public void runinstruction(Instruction_Register IR) {
		// if the machine halts, the CPU will not work any more
		if (halt == 1)
			return;
		switch (IR.getopcode()) {
		case 1:
			Load(IR);
			break;
		case 2:
			Store(IR);
			break;
		default:
			MFR.setFault(2);
			halt = 1;
		}
	}

// This acts as the load instruction
	public void Load(Instruction_Register IR) {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		} else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			}
		}
		// set the correct EA to the MAR
		MAR.setMemaddress(EA);
		// read the Memory and fetch the data to the MBR
		mfindex = cache.readCache(MAR.getMemaddress() + 8);
		checkaddress();
		MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
		// load the data in MBR to the target register
		GPRs.setregister(IR.getregister(), MBR.getData());
	}

// Store instruction
	public void Store(Instruction_Register IR) {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		} else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			}
		}
		// set the correct EA to the MAR
		MAR.setMemaddress(EA);
		// get the data from GPRs to MBR
		MBR.setData(GPRs.getregister(IR.getregister()));
		// write the data in MBR to the Memory with the address of MAR
		mfindex = cache.writeCache(MAR.getMemaddress() + 8, MBR.getData());
		checkaddress();
	}

	// This acts as the LDA instruction
	public void LDA(Instruction_Register IR) {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		} else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			}
		}

		// load the EA to the target register
		GPRs.setregister(IR.getregister(), EA);
	}

	// This acts as the LDX instruction
	public void LDX(Instruction_Register IR) {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		} else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			}
		}
		// set the correct EA to the MAR
		MAR.setMemaddress(EA);
		// read the Memory and fetch the data to the MBR
		mfindex = cache.readCache(MAR.getMemaddress() + 8);
		checkaddress();
		MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
		// load the data in MBR to the target register
		IXR.setregister(IR.getregister(), MBR.getData());
	}

	// STX instruction
	public void STX(Instruction_Register IR) {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		} else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			}
		}
		// set the correct EA to the MAR
		MAR.setMemaddress(EA);
		// get the data from GPRs to MBR
		MBR.setData(IXR.getregister(IR.getindexregister()));
		// write the data in MBR to the Memory with the address of MAR
		mfindex = cache.writeCache(MAR.getMemaddress() + 8, MBR.getData());
		checkaddress();
	}

	// JZ instruction
	public void JZ(Instruction_Register IR) {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		} else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			}
		}

		// if c(r) != 0, then PC <- EA
		if (GPRs.getregister(IR.getregister()) == 0) {
			PC.setPCaddress(EA);
		}
	}

	// JNE instruction
	public void JNE(Instruction_Register IR) {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		} else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			}
		}

		// if c(r) != 0, then PC <- EA
		if (GPRs.getregister(IR.getregister()) != 0) {
			PC.setPCaddress(EA);
		}
	}

	// JCC instruction
	public void JCC(Instruction_Register IR) {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		} else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			}
		}

		if (alu.CC.getccbit(IR.getregister()) == 1) {
			PC.setPCaddress(EA);
		}
	}

	// JMA instruction
	public void JMA(Instruction_Register IR) {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		} else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			}
		}

		PC.setPCaddress(EA);
	}

	// JSR instruction
	public void JSR(Instruction_Register IR) {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		} else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			}
		}

		GPRs.setregister(3, PC.getPCaddress());
		PC.setPCaddress(EA);
	}

	// RFS instruction
	public void RFS(Instruction_Register IR) {
		PC.setPCaddress(GPRs.getregister(3));
		GPRs.setregister(0, IR.getaddress());
	}

	// SOB instruction
	public void SOB(Instruction_Register IR) {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		} else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			}
		}

		GPRs.setregister(IR.getregister(), GPRs.getregister(IR.getregister()) - 1);
		if (GPRs.getregister(IR.getregister()) != 0) {
			PC.setPCaddress(EA);
		}
	}

	// JGE instruction
	public void JGE(Instruction_Register IR) {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		} else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			}
		}

		if (GPRs.getregister(IR.getregister()) >= 0) {
			PC.setPCaddress(EA);
		}
	}

	// AMR instruction
	public void AMR(Instruction_Register IR) {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		} else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
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
	public void SMR(Instruction_Register IR) {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		} else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
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
	public void AIR(Instruction_Register IR) {
		alu.setY(GPRs.getregister(IR.getregister()));
		alu.add(IR.getaddress());
		GPRs.setregister(IR.getregister(), alu.getResult());
	}

	// SIR instruction
	public void SIR(Instruction_Register IR) {
		alu.setY(GPRs.getregister(IR.getregister()));
		alu.subtract(IR.getaddress());
		GPRs.setregister(IR.getregister(), alu.getResult());
	}

	// MLT instruction
	public void MLT(Instruction_Register IR) {
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
	public void DVD(Instruction_Register IR) {
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
	public void TRR(Instruction_Register IR) {
		alu.setY(GPRs.getregister(IR.getregister()));
		alu.equal(GPRs.getregister(IR.getindexregister()));
	}

	// AND instruction
	public void AND(Instruction_Register IR) {
		alu.setY(GPRs.getregister(IR.getregister()));
		alu.and(GPRs.getregister(IR.getindexregister()));
		GPRs.setregister(IR.getregister(), alu.getResult());
	}

	// ORR instruction
	public void ORR(Instruction_Register IR) {
		alu.setY(GPRs.getregister(IR.getregister()));
		alu.or(GPRs.getregister(IR.getindexregister()));
		GPRs.setregister(IR.getregister(), alu.getResult());
	}

	// NOT instruction
	public void NOT(Instruction_Register IR) {
		alu.not(GPRs.getregister(IR.getregister()));
		GPRs.setregister(IR.getregister(), alu.getResult());
	}

	// SRC instruction
	public void SRC(Instruction_Register IR) {
		int AL = IR.getindexregister() / 2;
		int LR = IR.getindexregister() % 2;
		alu.shift(GPRs.getregister(IR.getregister()), AL, LR, IR.getaddress());
		GPRs.setregister(IR.getregister(), alu.getResult());
	}

	// RRC instruction
	public void RRC(Instruction_Register IR) {
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

	// IN instruction
	public void IN(Instruction_Register IR) {
		if (IR.getaddress() == 0) {
			GPRs.setregister(IR.getregister(), device.readKeyboard());
		}
	}

	// OUT instruction
	public void OUT(Instruction_Register IR) {
		if (IR.getaddress() == 1) {
			device.printer = GPRs.getregister(IR.getregister());
		}
	}

	// TRAP instruction
	public void TRAP(Instruction_Register IR) {
		int traptable_address = cache.CPUaccess(0);
		System.out.print("Trap table address: ");
		System.out.println(traptable_address);
		int trap_number = IR.getaddress();
		cache.CPUwrite(2, PC.getPCaddress() + 1);
		System.out.print("Trap entry: ");
		System.out.println(cache.readCache(traptable_address + trap_number + 8));
		PC.setPCaddress(cache.readCache(traptable_address + trap_number + 8));
	}

	// FADD instruction
	public void FADD(Instruction_Register IR) {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		} else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			}
		}
		// set the correct EA to the MAR
		MAR.setMemaddress(EA);
		// read the Memory and fetch the data to the MBR
		mfindex = cache.readCache(MAR.getMemaddress() + 8);
		checkaddress();
		MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
		double res = FALU.floatAdd(FRs.readFloat(IR.getregister()), MBR.getData());
		FRs.writeFloat(IR.getregister(), res);
	}

	// FSUB instruction
	public void FSUB(Instruction_Register IR) {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		} else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			}
		}
		// set the correct EA to the MAR
		MAR.setMemaddress(EA);
		// read the Memory and fetch the data to the MBR
		mfindex = cache.readCache(MAR.getMemaddress() + 8);
		checkaddress();
		MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
		double res = FALU.floatSub(FRs.readFloat(IR.getregister()), MBR.getData());
		FRs.writeFloat(IR.getregister(), res);
	}

	// CNVRT instruction
	public void CNVRT(Instruction_Register IR) {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		} else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			}
		}
		// set the correct EA to the MAR
		MAR.setMemaddress(EA);
		// read the Memory and fetch the data to the MBR
		mfindex = cache.readCache(MAR.getMemaddress() + 8);
		checkaddress();
		MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
		int F = GPRs.getregister(IR.getregister());
		if (F == 0) {
			int fixed = FALU.convertToFix(MBR.getData());
			GPRs.setregister(IR.getregister(), fixed);
		} else {
			int floatnumber = FALU.convertToFloat(MBR.getData());
			FRs.writeFloat(0, floatnumber);
		}
	}

	// LDFR instruction
	public void LDFR(Instruction_Register IR) {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		} else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			}
		}
		// set the correct EA to the MAR
		MAR.setMemaddress(EA);
		// read the Memory and fetch the data to the MBR
		mfindex = cache.readCache(MAR.getMemaddress() + 8);
		checkaddress();
		MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
		FRs.writeFloat(IR.getregister(), MBR.getData());
	}

	// STFR instruction
	public void STFR(Instruction_Register IR) {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		} else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			}
		}
		// set the correct EA to the MAR
		MAR.setMemaddress(EA);
		// read the Memory and fetch the data to the MBR
		mfindex = cache.readCache(MAR.getMemaddress() + 8);
		checkaddress();
		MBR.setData(FRs.registers[IR.getregister()]);
		cache.writeCache(MAR.getMemaddress() + 8, MBR.getData());
		cache.writeCache(MAR.getMemaddress() + 8 + 1, MBR.getData());
	}

	// VADD instruction
	public void VADD(Instruction_Register IR) {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		} else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			}
		}
		int vecterLength = (int) FRs.readFloat(IR.getregister());
		VR1.initial(vecterLength);
		VR2.initial(vecterLength);
		MAR.setMemaddress(EA);
		mfindex = cache.readCache(MAR.getMemaddress() + 8);
		checkaddress();
		MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
		int vector1Address = MBR.getData();
		MAR.setMemaddress(EA + 1);
		mfindex = cache.readCache(MAR.getMemaddress() + 8);
		checkaddress();
		MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
		int vector2Address = MBR.getData();
		for (int i = vector1Address, j = 0; i < vector1Address + vecterLength; i++, j++) {
			MAR.setMemaddress(i);
			mfindex = cache.readCache(MAR.getMemaddress() + 8);
			checkaddress();
			MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
			VR1.writeElement(j, MBR.getData());
		}
		for (int i = vector2Address, j = 0; i < vector2Address + vecterLength; i++, j++) {
			MAR.setMemaddress(i);
			mfindex = cache.readCache(MAR.getMemaddress() + 8);
			checkaddress();
			MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
			VR1.writeElement(j, MBR.getData());
		}
		for (int i = 0; i < vecterLength; i++) {
			VR1.writeElement(i, VR1.readElement(i) + VR2.readElement(i));
		}
		for (int i = vector1Address, j = 0; i < vector1Address + vecterLength; i++, j++) {
			MAR.setMemaddress(i);
			mfindex = cache.readCache(MAR.getMemaddress() + 8);
			checkaddress();
			MBR.setData(VR1.readElement(j));
			cache.writeCache(MAR.getMemaddress() + 8, MBR.getData());
		}
	}

	// VSUB instruction
	public void VSUB(Instruction_Register IR) {
		int EA = 0;
		// checks for an IR indirect in each register and computing the correct EA
		if (IR.getindirect() == 0) {
			if (IR.getindexregister() == 0) {
				EA = IR.getaddress();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				EA = IXR.getregister(IR.getindexregister()) + IR.getaddress();
			}
		} else if (IR.getindirect() == 1) {
			if (IR.getindexregister() == 0) {
				MAR.setMemaddress(IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			} else if (IR.getindexregister() > 0 && IR.getindexregister() < 4) {
				MAR.setMemaddress(IXR.getregister(IR.getindexregister()) + IR.getaddress());
				mfindex = cache.readCache(MAR.getMemaddress() + 8);
				checkaddress();
				MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
				EA = MBR.getData();
			}
		}
		int vecterLength = (int) FRs.readFloat(IR.getregister());
		VR1.initial(vecterLength);
		VR2.initial(vecterLength);
		MAR.setMemaddress(EA);
		mfindex = cache.readCache(MAR.getMemaddress() + 8);
		checkaddress();
		MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
		int vector1Address = MBR.getData();
		MAR.setMemaddress(EA + 1);
		mfindex = cache.readCache(MAR.getMemaddress() + 8);
		checkaddress();
		MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
		int vector2Address = MBR.getData();
		for (int i = vector1Address, j = 0; i < vector1Address + vecterLength; i++, j++) {
			MAR.setMemaddress(i);
			mfindex = cache.readCache(MAR.getMemaddress() + 8);
			checkaddress();
			MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
			VR1.writeElement(j, MBR.getData());
		}
		for (int i = vector2Address, j = 0; i < vector2Address + vecterLength; i++, j++) {
			MAR.setMemaddress(i);
			mfindex = cache.readCache(MAR.getMemaddress() + 8);
			checkaddress();
			MBR.setData(cache.readCache(MAR.getMemaddress() + 8));
			VR1.writeElement(j, MBR.getData());
		}
		for (int i = 0; i < vecterLength; i++) {
			VR1.writeElement(i, VR1.readElement(i) - VR2.readElement(i));
		}
		for (int i = vector1Address, j = 0; i < vector1Address + vecterLength; i++, j++) {
			MAR.setMemaddress(i);
			mfindex = cache.readCache(MAR.getMemaddress() + 8);
			checkaddress();
			MBR.setData(VR1.readElement(j));
			cache.writeCache(MAR.getMemaddress() + 8, MBR.getData());
		}
	}

// check the access of memory is write or not. If not, we go to set the MFR and get solution which is halt right now
	public void checkaddress() {
		if (mfindex == -1) {
			MFR.setFault(0);
			machinefault();
		} else if (mfindex == -2) {
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
			// get the solution instruction which is halt right now
			MAR.setMemaddress(MBR.getData());
			MBR.setData(cache.CPUaccess(MAR.getMemaddress()));
			Instruction_Register IR = new Instruction_Register();
			IR.setinstruction(MBR.getData());
			runinstruction(IR);
		}
	}

	public void Halt(Instruction_Register IR) {
		halt = 1;
	}

	// used to load instruction into pipeline
	public void initialStages(LinkedList<Integer> stages, int initialstall) {
		for (int i = 0; i < initialstall; i++) {
			stages.offerLast(0);
		}
		for (int i = 1; i < 5; i++) {
			stages.offerLast(i);
		}
	}
	
	// used to initial pipeline
	public void initialPipeline(int pc) {
		for (int i = 0; i < 5; i++) {
			LinkedList<Integer> ins = new LinkedList<Integer>();
			initialStages(ins, i);
			Stages[i] = ins;
			Instruction_Buffer[i] = new Instruction_Register();
			Instruction_Buffer[i].setinstruction(cache.readCache(pc + 8));
		}
	}
}