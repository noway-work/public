package sandbox;

public class SetterTest {
	public static void main(String[] args) {
		String val1 = "ABC";
		String val2 = val1;
		System.out.println("[before]val1:" + val1);
		System.out.println("[before]val2:" + val2);
		val2 = null;
		System.out.println("[after]val1:" + val1);
		System.out.println("[after]val2:" + val2);
	}
}
