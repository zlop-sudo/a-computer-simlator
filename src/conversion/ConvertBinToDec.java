package conversion;

// convert Binary to Decimal
public class ConvertBinToDec {
	public static int convertbintodec(String bin_number) {
		int decimal=Integer.parseInt(bin_number,2);
		return decimal;
	}  
}
