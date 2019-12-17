package sandbox;

public class ArrayTest {
	public static void main(String args[]) {
		int[] test = new int[5];
		for(int i = 0; i < test.length; i++) {
			System.out.println("cnt[" + i + "] : " + test[i]);
		}
		
		test = new int[8];
		for(int i = 0; i < test.length; i++) {
			System.out.println("cnt[" + i + "] : " + test[i]);
		}
	}
}
