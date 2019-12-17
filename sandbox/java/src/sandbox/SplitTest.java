package sandbox;

public class SplitTest {
	public static void main(String args[]) {
		String[] test = null;
		test = "\t1\t2\3\t\t".split("\t");
		System.out.println("length : " + test.length);	// 2‚É‚È‚é
		for(int i = 0; i < test.length; i++) {
			System.out.println("cnt[" + i + "] : " + test[i]);
		}
		
		test = "\t1\t2\3\t\t".split("\t", -1);
		System.out.println("length : " + test.length);	// 3‚É‚È‚é
		for(int i = 0; i < test.length; i++) {
			System.out.println("cnt[" + i + "] : " + test[i]);
		}

	}
}
