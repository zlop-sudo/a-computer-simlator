package components;

// The Float_Registers has two registers
public class Float_Registers {
	public int[] registers = {0, 0};
	
	// write the FRs
	public boolean writeFloat (int index, int data) {
		if (index >= 0 && index < 2){
			if (data < Math.pow(2, 16) && data >= 0){
				registers[index] = data;
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}
	
	// write the FRs
	public boolean writeFloat (int index, double data) {
		int sign = data > 0 ? 0 : 1;
		data = Math.abs(data);
		int integer = (int) data;
		String intergerBinary = Integer.toBinaryString(integer);
		double decimal = data - integer;
		StringBuffer decimalBinary = new StringBuffer();
		int bitOfint = intergerBinary.length();
		int indexOfdecimal = 0;
		for (int i = 0; i <= 8 + indexOfdecimal; i++) {
			decimal *= 2;
			if (indexOfdecimal == 0 && (int) decimal > 0) {
				indexOfdecimal = i + 1;
			}
			decimalBinary.append((int) decimal);
			decimal -= (int) decimal;
		}
		int Exponent = 0;
		int signOfExponent = 0;
		int Mantissa = 0;
		if (integer > 0) {
			Exponent = bitOfint - 1;
			signOfExponent = 0;
			if (bitOfint > 8) {
				for (int i = 0; i < 8; i++) {
					Mantissa *= 2;
					Mantissa += intergerBinary.charAt(i) - '0';
				}
			}
			else {
				for (int i = 1; i < bitOfint; i++) {
					Mantissa *= 2;
					Mantissa += intergerBinary.charAt(i) - '0';
				}
				for (int i = 0; i < 8 - bitOfint + 1; i++) {
					Mantissa *= 2;
					Mantissa += decimalBinary.toString().charAt(i) - '0';
				}
			}
		}
		else {
			Exponent = (int) Math.pow(2, 6) - indexOfdecimal;
			signOfExponent = 1;
			for (int i = indexOfdecimal; i < 8 + indexOfdecimal; i++) {
				Mantissa += decimalBinary.toString().charAt(i) - '0';
				Mantissa *= 2;
			}
		}
		int bits = sign * (int) Math.pow(2, 15) + signOfExponent * (int) Math.pow(2, 14) + Exponent * (int) Math.pow(2, 8) + Mantissa;
		if (index >= 0 && index < 2){
			if (data < Math.pow(2, 16) && data >= 0){
				registers[index] = bits;
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}
	
	// read the float number, Exponent is two's complement
	public double readFloat(int index) {
		int data = registers[index];
		int sign = data / (int) Math.pow(2, 15);
		int signOfExponent = data % (int) Math.pow(2, 15) / (int) Math.pow(2, 14);
		int Exponent = data % (int) Math.pow(2, 14) / (int) Math.pow(2, 8);
		int Mantissa = data % (int) Math.pow(2, 8);
		double temp = 0;
		for (int i = 1; i < 9; i++) {
			if ((Mantissa % (int) Math.pow(2, 9 - i) / (int) Math.pow(2, 8 - i)) != 0) {
				temp += 1.0 / ((Mantissa % (int) Math.pow(2, 9 - i) / (int) Math.pow(2, 8 - i)) * Math.pow(2, i));
			}
		}
		temp += 1;
		double res;
		if (signOfExponent == 0) {
			res = Math.pow(-1, sign) * temp * Math.pow(2, Exponent);
		}
		else {
			res = Math.pow(-1, sign) * temp * Math.pow(2, -1 * (Math.pow(2, 6) - Exponent));
		}
		return res;
	}
}
