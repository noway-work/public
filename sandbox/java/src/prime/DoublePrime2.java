package prime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

public class DoublePrime2 {
	public static void main(String[] args) {
		PrimeChecker pc = new PrimeChecker();
		int digit = 0;
		
		if(isTargetNumber(args[0])) {			
			digit = Integer.parseInt(args[0]) / 2;
			for (int i = 1; i <= digit; i++) {
				pc.check(i);
			}
			
			Iterator<NumberCombination> it = pc.getAnswer().iterator();
			NumberCombination temp = null;
			while(it.hasNext()) {
				temp = it.next();
				//if(temp.getDigit() == digit) {
					System.out.println("Digit:" + temp.getDigit());
					System.out.println("FHalf:" + temp.getFHalf());
					System.out.println("SHalf:" + temp.getSHalf());
					System.out.println("Answer:" + temp.getAnswer());
				//}
			}
		}
	}
	
	private static boolean isTargetNumber(String arg) {
		try {
			int temp = Integer.parseInt(arg);
			if(temp % 2 != 0) return false;
			return true;
		} catch(NumberFormatException nfe) {
			return false;
		}
	}
}

class PrimeChecker {
	private ArrayList<NumberCombination> result = new ArrayList<NumberCombination>();
	
	public void check(int digit) {
		int level = new BigDecimal(10).pow(digit -1).intValue();
		int tempFHalf = 0;
		int tempSHalf = 0;
		
		for(int fHalf = 0; fHalf < 10; fHalf++) {
			for(int sHalf = 0; sHalf < 10; sHalf++) {
				// 1‚ÌˆÊˆ—
				if(digit == 1) {
					if(check(level, fHalf, sHalf)) {
						result.add(new NumberCombination(digit, fHalf, sHalf));
					}
				// ‚»‚Ì‘¼i10‚ÌˆÊˆÈãjˆ—
				} else {
					tempFHalf = fHalf * level;
					tempSHalf = sHalf * level;
					
					NumberCombination temp = null;
					
					for(int i = 0; i < result.size(); i++) {
						temp = result.get(i);
						tempFHalf = tempFHalf + temp.getFHalf();
						tempSHalf = tempSHalf + temp.getSHalf();
						if(check(level, tempFHalf, tempSHalf)) {
							result.add(new NumberCombination(digit, tempFHalf, tempSHalf));
						}
					}
				}
			}
		}
	}
	
	private boolean check(int level, int fHalf, int sHalf) {
		BigDecimal bdFHalf = new BigDecimal(fHalf);
		BigDecimal bdSHalf = new BigDecimal(sHalf);
		
		int val1 = bdFHalf.pow(2).add(bdSHalf.pow(2)).intValue();
		return (val1 % level == sHalf);
	}
	
	public ArrayList<NumberCombination> getAnswer() {
		return result;
	}
}

class NumberCombination {
	private int digit = 0;
	private int fHalf = 0;
	private int sHalf = 0;
	
	public NumberCombination(int digit, int fHalf, int sHalf) {
		this.digit = digit;
		this.fHalf = fHalf;
		this.sHalf = sHalf;
	}
	
	public int getDigit() {
		return digit;
	}
	public void setDigit(int digit) {
		this.digit = digit;
	}
	
	public int getFHalf() {
		return fHalf;
	}
	public void setFHalf(int fHalf) {
		this.fHalf = fHalf;
	}
	
	public int getSHalf() {
		return sHalf;
	}
	public void setSHalf(int sHalf) {
		this.sHalf = sHalf;
	}
	
	public long getAnswer() {
		int level = new BigDecimal(10).pow(digit).intValue();
		return fHalf * level + sHalf;
	}
}