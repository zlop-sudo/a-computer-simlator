package components;

// ALU used for float computation
public class Float_ALU {
	
	// the second Operand is load from memory so need to be changed to float
	public double floatAdd (double firstOperand, int secondOperand) {
		int sign = secondOperand / (int) Math.pow(2, 15);
		int signOfExponent = secondOperand % (int) Math.pow(2, 15) / (int) Math.pow(2, 14);
		int Exponent = secondOperand % (int) Math.pow(2, 14) / (int) Math.pow(2, 8);
		int Mantissa = secondOperand % (int) Math.pow(2, 8);
		double temp = 0;
		for (int i = 1; i < 9; i++) {
			if ((Mantissa % (int) Math.pow(2, 9 - i) / (int) Math.pow(2, 8 - i)) != 0) {
				temp += 1.0 / ((Mantissa % (int) Math.pow(2, 9 - i) / (int) Math.pow(2, 8 - i)) * Math.pow(2, i));
			}
		}
		temp += 1;
		System.out.println(temp);
		double second;
		if (signOfExponent == 0) {
			second = Math.pow(-1, sign) * temp * Math.pow(2, Exponent);
		}
		else {
			second = Math.pow(-1, sign) * temp * Math.pow(2, -1 * (Math.pow(2, 6) - Exponent));
		}
		return firstOperand + second;
	}
	
	// the second Operand is load from memory so need to be changed to float
	public double floatSub (double firstOperand, int secondOperand) {
		int sign = secondOperand / (int) Math.pow(2, 15);
		int signOfExponent = secondOperand % (int) Math.pow(2, 15) / (int) Math.pow(2, 14);
		int Exponent = secondOperand % (int) Math.pow(2, 14) / (int) Math.pow(2, 8);
		int Mantissa = secondOperand % (int) Math.pow(2, 8);
		double temp = 0;
		for (int i = 1; i < 9; i++) {
			if ((Mantissa % (int) Math.pow(2, 9 - i) / (int) Math.pow(2, 8 - i)) != 0) {
				temp += 1.0 / ((Mantissa % (int) Math.pow(2, 9 - i) / (int) Math.pow(2, 8 - i)) * Math.pow(2, i));
			}
		}
		temp += 1;
		System.out.println(temp);
		double second;
		if (signOfExponent == 0) {
			second = Math.pow(-1, sign) * temp * Math.pow(2, Exponent);
		}
		else {
			second = Math.pow(-1, sign) * temp * Math.pow(2, -1 * (Math.pow(2, 6) - Exponent));
		}
		return firstOperand - second;
	}
	
	public int convertToFix(int data) {
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
		return (int) res;
	}
	
	public int convertToFloat(double data) {
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
		int res = sign * (int) Math.pow(2, 15) + signOfExponent * (int) Math.pow(2, 14) + Exponent * (int) Math.pow(2, 8) + Mantissa;
		return res;
	}
}