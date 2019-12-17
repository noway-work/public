package prime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

//2 5 10 19 54 224 312 616 888 977
//2128 10000 12345 14142 31415 50000 67890 77777 98765 100000
public class PrimeTest {
	public static void main(String[] args) throws IOException {
		PrimeCounter pc = new PrimeCounter();
		int c = 0;
		while((c = System.in.read()) != -1) {
			System.out.println("test:" + c + ":" + pc.getAnswer(c));
		}
	}
}

class PrimeCounter {
	private int cnt = 0;
	private ArrayList<Integer> list = new ArrayList<Integer>();
	
	private void compute(int target) {
		for(; cnt <= target; cnt++) {
			if(isPrime(cnt)) {
				list.add(cnt);
			}
		}
	}
	
	private boolean isPrime(int chk) {
		if(chk <= 1) return false;
		for (int i = 2; i <= chk / 2; i++) {
			if(chk % i == 0) return false;
		}
		return true;
	}
	
	public int getAnswer(int target) {
		if(cnt < target) {
			compute(target);
		}
		
		int i = 0;
		Iterator<Integer> it = list.iterator();
		while(it.hasNext()) {
			if(it.next() <= target) i++;
		}
		return i;
	}
	
	public String getAnswerForDebug(int target) {
		Iterator<Integer> it = list.iterator();
		StringBuffer sb = new StringBuffer();
		int i = 0;
		while(it.hasNext()) {
			i = it.next();
			if(i <= target) {
				if(sb.length() != 0) sb.append(",");
				sb.append(i);
			}
		}
		return sb.toString();
	}
}