package components;

public class Memory_Buffer_Register{
	//initializes the MBR
	private int Data = 0;

	public Memory_Buffer_Register(){
	}

	//gets the returns the data in the MBR
	public int getData(){
		return Data;
	}

	public boolean setData(int newData){
		//checks if the data is appropriate before assigning it to the MBR
		if (newData < Math.pow(2,16) && newData >= 0){
			Data = newData;
			return true;
		}
		else
			return false;
	}
}