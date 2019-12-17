package sandbox;

public class CharacterTest {
	public static void main(String args[]) {
		char c = 'a';
		int i = (int)c;
		System.out.println(c);
		System.out.println(i);
		i = i + 300;
		System.out.println(i);
		char c2 = (char)i;
		System.out.println(c2);
	}
}
