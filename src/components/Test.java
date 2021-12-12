package components;

public class Test {
	public static void main(String args[]) {
		CPU_Control Test = new CPU_Control();
		Test.initial();
		Test.loadprogram1();
		Test.loadprogram2();
		
//		for (int i = 0; i < 10; i++) {
//			System.out.println(Test.cache.readCache(i+8));
//		}
		Test.FRs.writeFloat(0, 64.0);
		System.out.println(Test.FRs.readFloat(0));
	}
}
