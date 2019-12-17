package prime;

import java.math.BigDecimal;

public class DoublePrime {
	public static void main(String[] args) {
		PrimeCalculator pc = new PrimeCalculator();
		for (int i = 0; i < args.length; i++) {
			if(isNumber(args[i])) {
				pc.start(Integer.parseInt(args[i]));
			}
		}
	}
	
	private static boolean isNumber(String arg) {
		try {
			Integer.parseInt(arg);
			return true;
		} catch(NumberFormatException nfe) {
			return false;
		}
	}
}

class PrimeCalculator {
	public void start(int digit) {
		if(digit % 2 != 0) return;
		
		long result = 0;
		int end = getEndPoint(digit);
		//int end = 0;
		for(int i = getStartPointForOuterLoop(digit); i > end; i--) {
			//System.out.println("i:" + i);
			//System.out.println("test:" + getStartPointForInnerLoop(digit, i));
			for(int j = getStartPointForInnerLoop(digit, i); j > 0; j--) {
				//System.out.println("j:" + j);
				result = check(digit, i, j);
				if(result != 0) {
					System.out.println("result:" + result);
				}
			}
		}
	}
	
	private int getStartPointForOuterLoop(int digit) {
		return new BigDecimal(10).pow(digit / 2).intValue() - 1;
	}
	
	private int getEndPoint(int digit) {
		return new BigDecimal(10).pow(digit / 2 - 1).intValue();
	}
	
	private int getStartPointForInnerLoop(int digit, int start) {
		BigDecimal temp1 = new BigDecimal(start).pow(2);
		BigDecimal temp2 = new BigDecimal(10).pow(digit);
		return (int) Math.floor(Math.sqrt(temp2.subtract(temp1).doubleValue()));
	}
	
	private long check(int digit, int fHalf, int sHalf) {
		long temp1 = new BigDecimal(fHalf).multiply(
				new BigDecimal(10).pow(digit / 2))
				.add(new BigDecimal(sHalf)).longValue();
		long temp2 = compute(fHalf, sHalf);
		
		if (temp1 == temp2) {
			return temp2;
		} else {
			return 0;
		}
	}

	private long compute(int fHalf, int sHalf) {
		BigDecimal temp1 = new BigDecimal(fHalf).pow(2)
				.add(new BigDecimal(sHalf).pow(2));
		return temp1.longValue();
	}
}