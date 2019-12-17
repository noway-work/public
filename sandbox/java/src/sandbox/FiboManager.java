package sandbox;

import java.util.ArrayList;

public class FiboManager {
	
	private static FiboManager instance;
	private static ArrayList<Long> list;
	private static int keta = 1000;
	
	public static FiboManager getInstance() {
		if(instance == null) {
			instance = new FiboManager();
			
			list = new ArrayList<Long>();
			list.add((long) 0);
			list.add((long) 1);
		}
		return instance;
	}
	
	public void computeFiboNumber(int to) {
		for(int i = list.size() - 1; i < to; i++) {
			list.add(getFiboNumber(list.get(i - 1), list.get(i)));
		}
	}
	
	private Long getFiboNumber(long num1, long num2) {
		long res = num1 + num2;
		if(res >= keta) {
			res = res - keta;
		}
		return res;
	}
	
	public void output() {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(i + ":" + list.get(i));
		}
	}
	
	private FiboManager() {
		instance = null;
		list = null;
	}
	
	public static void main(String[] args) {
		System.out.println("0 made");
		FiboManager.getInstance().computeFiboNumber(0);
		FiboManager.getInstance().output();
		
		System.out.println("10 made");
		FiboManager.getInstance().computeFiboNumber(10);
		FiboManager.getInstance().output();
		
		System.out.println("10 made");
		FiboManager.getInstance().computeFiboNumber(100);
		FiboManager.getInstance().output();
	}
}
