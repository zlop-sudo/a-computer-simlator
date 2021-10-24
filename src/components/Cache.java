package components;

// a fully associative, FIFO, write-through cache
public class Cache extends Memory {
	private int[] flags = new int[16];	// valid
	private int[] cnts = new int[16];	// for FIFO
	private int[] tags = new int[16];	// matches the block number of memory
	private int[][] data = new int [16][8]; // 16 lines, each block has 8 words
	
	public Cache() {
		super(2048, 8);
	}
	
	public int readCache(int address) {
		// deal with machine fault
		if (address < start && address >= 0) {
			return -1;
		}
		else if (address > Memwords.length) {
			return -2;
		}
		
		int tag = address / 8;
		int readline = matchTag(tag);
		// target block is not in cache if get -1
		if (readline == -1) {
			 readline = FIFO(tag);
		}
		int wordAddress = address % 8;
		return data[readline][wordAddress];
	}
	
	public int writeCache(int address, int newData) {
		// deal with machine fault
		if (address < start && address >= 0) {
			return -1;
		}
		else if (address > Memwords.length) {
			return -2;
		}
		
		int tag = address / 8;
		int writeline = matchTag(tag);
		// target block is not in cache if get -1
		if (writeline == -1) {
			 writeline = FIFO(tag);
		}
		int wordAddress = address % 8;
		data[writeline][wordAddress] = newData;
		
		// write-through
		for (int i = 0; i < 8; i++) {
			writeMem(tag * 8 + i, data[writeline][i]);
		}
		return 0;
	}
	
	// find the target line, if the target block is not in cache now then return -1
	private int matchTag(int tag) {
		for (int i = 0; i < 16; i++) {
			if (flags[i] == 1) {
				if (tags[i] == tag) {
					return i;
				}
			}
		}
		return -1;
	}
	
	// replace strategy FIFO
	private int FIFO(int tag) {
		// find replace line
		int replaceLine = -1;
		for (int i = 0; i < 16; i++) {
			if (flags[i] == 0) {
				replaceLine = i;
				flags[i] = 1;
			}
		}
		if (replaceLine == -1) {
			int maxCnt = 0;
			for (int i = 0; i < 16; i++) {
				if (cnts[i] > maxCnt) {
					maxCnt = cnts[i];
					replaceLine = i;
				}
			}
		}
		
		//replace
		for (int i = 0; i < 8; i++) {
			data[replaceLine][i] = readMem(tag * 8 + i);
		}
		tags[replaceLine] = tag;
		for (int i = 0; i < 16; i++) {
			if (flags[i] == 1) {
				cnts[i]++;
			}
		}
		return replaceLine;
	}
}
