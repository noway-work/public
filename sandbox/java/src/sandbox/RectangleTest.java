package sandbox;

import java.util.ArrayList;
import java.util.Iterator;

public class RectangleTest {
	
	private static ArrayList<Long> buffer = new ArrayList<Long>();

	public static void main(String[] args) {
		int question = 10000;
		int cnt = 0;
		for (int i = question; i > 0; i--) {
			if(chkBuffer(i)) {
				cnt++;
			} else if(chkTarget(i)) cnt++;
		}
		System.out.println(question + " : " + cnt);
	}
	
	private static boolean chkBuffer(int target) {
		Iterator<Long> it = buffer.iterator();
		while(it.hasNext()) {
			if(target % it.next() == 0) return true;
		}
		return false;
	}
	
	private static boolean chkTarget(int target) {
		boolean result = false;
		boolean loop = true;
		long temp = 0;
		
		for(int i = 2; loop && i < target / 3; i++) {
			for(int j = 2; loop && j < target / 3; j++) {
				for(int k = 2; loop && k < target / 3; k++) {
					temp = i * j * k;
					if(temp == target) {
						buffer.add(temp);
						result = true;
						loop = false;
					} else if(temp > target) {
						break;
					}
				}
			}
		}
		return result;
	}
}
