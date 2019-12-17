package sandbox;

public class SplitTest2 {
	public static void main(String args[]) {
		String in = null;
		String res[] = null;

		in = "0100000000000000";
		res = in.split("0{1}+");
		System.out.println(res.length);
		for(int i = 0; i < res.length; i++) {
			System.out.println("cnt[" + i + "] : " + res[i]);
		}
		
		in = "00100";
		res = in.split("1");
		System.out.println(res.length);
		
		System.out.println(
			new StringBuilder()
				.append(in.substring(0, 2 - 1))
				.append("1")
				.append(in.substring(2))
				.toString()
				);
	}
}
