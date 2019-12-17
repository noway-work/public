package fibo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

//10 0 1 2 3 4 5 100 12345 1234567 33333
//20 12434 657751 151251 805211 1755178 1767886 27720560 40259059 41225425 40850067 86284265 41102465 157079636 234491556 200551464 35743807 20020263 86069783 133288536 49609347
public class FiboTest2 {

	public static void main(String[] args) throws IOException {
		new FiboTest2().start();
	}
	
	private void start() throws IOException {
		FiboCalculator fibo = new FiboCalculator();
		int line = System.in.read();
		int c = 0;
		for(int i = 0; i < line; i++) {
			c = System.in.read();
			if(c == -1) continue;
			System.out.println(fibo.getAnswer(c));
		}
	}

	class FiboCalculator {
		private int cnt = 0;
		private ArrayList<Long> list = new ArrayList<Long>();
		
		public FiboCalculator() {
			
		}
		
		private void init() {
			list.add((long)0);
			list.add((long)1);
			cnt = 2;
		}
		
		private void calculate(int target) {
			for(; cnt <= target; cnt++) {
				genFibo();
			}
		}
		
		private void genFibo() {
			int i = cnt;
			list.add(list.get(i - 2) + list.get(i - 1));
		}
		
		public long getAnswer(int target) {
			if(list.size() < 2) init();
			if(cnt < target) {
				calculate(target);
			}
			
			String temp = list.get(target - 1).toString();
			if(temp.length() > 3)
				temp = temp.substring(temp.length() - 3);
			
			return Long.parseLong(temp);
		}
		
		public String getAnswerForDebug(int target) {
			Iterator<Long> it = list.iterator();
			StringBuffer sb = new StringBuffer();
			long i = 0;
			while(it.hasNext()) {
				i = it.next();
				if(sb.length() != 0) sb.append(",");
				sb.append(i);
			}
			return sb.toString();
		}
	}
}
