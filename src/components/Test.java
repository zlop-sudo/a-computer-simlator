package components;

public class Test {
	public static void main(String args[]) {
		CPU_Control Test = new CPU_Control();
		Test.initial();
		Test.loadprogram1();
		
		// test our own simple IPL of 4 instructions
//		for (int i = 0; i < 4; i++) {
//			System.out.println("PC:" + Test.PC.getPCaddress());
//			Test.runsinglestep();
//		}
		
		// check the results: load the data from location 100 and 101 and then write them to location 1000 and 1001
//		System.out.println(Test.Mem.readMem(100));
//		System.out.println(Test.Mem.readMem(101));
//		
//		System.out.println(Test.GPRs.getregister(0));
//		System.out.println(Test.GPRs.getregister(1));
//		
//		System.out.println(Test.Mem.readMem(1000));
//		System.out.println(Test.Mem.readMem(1001));
//		
		//test load
		//opcode 000001 R 00 IX 01 I 0 Address 000000
		//Test.Mem.writeMem(7, 0b0000010001000000);
		//Test.runsinglestep();
		//System.out.println(Test.GPRs.getregister(0));
		
		//test store
		//opcode 000010 R 01 IX 10 I 0 Address 000000
		//Test.Mem.writeMem(8, 0b0000100110000000);
		//Test.GPRs.setregister(1, 10);
		//Test.runsinglestep();
		//System.out.println(Test.Mem.readMem(100));
	}
}
