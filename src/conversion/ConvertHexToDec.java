package conversion;

// convert hex to decimal
public class ConvertHexToDec {
	public static int convertHexToDec(String hex_number) {
		int decimal=Integer.parseInt(hex_number,16); 
		//System.out.println(Integer.parseInt("ffff",16));
		return decimal;
	} 
	
	public static void main(String args[]) {
		System.out.println(Integer.parseInt("ffff",16));
	}
}
