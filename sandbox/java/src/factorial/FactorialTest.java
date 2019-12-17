package factorial;

import java.io.IOException;

public class FactorialTest {
	private void init() {
		System.out.println("Enter a Number:");
		int inChar;
		try {
			inChar = System.in.read();
			System.out.println("You entered ");
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
