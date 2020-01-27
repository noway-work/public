package factorial;

import java.io.IOException;

public class FactorialTest {
	private void init() {
		System.out.println("Enter a Number:");
		int inChar;
		try {
			inChar = System.in.read();
			System.out.println("You entered ");
			System.out.println("updated0 ");
			System.out.println("updated2 ");
			System.out.println("updated3 ");
			System.out.println("updated4 ");
			System.out.println(inChar);
		} catch (IOException ioe) {
			
		}
	}
	public FactorialTest() {
		init();
	}
	public static void main(String args[]) {
		new FactorialTest();
	}
}
