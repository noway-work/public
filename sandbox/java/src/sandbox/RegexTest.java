package sandbox;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegexTest {
	private void init() {
		/*
		System.out.println(regex("000000000", "^[0-9]{10}$"));
		System.out.println(regex("0000000000", "^[0-9]{10}$"));
		System.out.println(regex("00000000000", "^[0-9]{10}$"));
		System.out.println(regex("00000000A", "^[0-9]{10}$"));
		System.out.println(regex("000000000A", "^[0-9]{10}$"));
		System.out.println(regex("0000000000A", "^[0-9]{10}$"));

		System.out.println(regex("000000000", "^\\d{10}$"));
		System.out.println(regex("0000000000", "^\\d{10}$"));
		System.out.println(regex("00000000000", "^\\d{10}$"));
		System.out.println(regex("00000000A", "^\\d{10}$"));
		System.out.println(regex("000000000A", "^\\d{10}$"));
		System.out.println(regex("0000000000A", "^\\d{10}$"));
		*/
		/*
		System.out.println(regex("0", "([0-1])"));
		System.out.println(regex("1", "([0-1])"));
		System.out.println(regex("01", "([0-1])"));
		System.out.println(regex("012", "([0-1])"));
		System.out.println(regex("0123", "([0-1])"));
		System.out.println(regex("2", "([0-1])"));
		System.out.println(regex("23", "([0-1])"));
		System.out.println(regex("234", "([0-1])"));
		*/
		String regex = "\\d{6,11}";
		System.out.println(regex("A", regex));
		System.out.println(regex("1", regex));
		System.out.println(regex("12", regex));
		System.out.println(regex("123", regex));
		System.out.println(regex("1234", regex));
		System.out.println(regex("12345", regex));
		System.out.println(regex("123456", regex));
		System.out.println(regex("1234567", regex));
		System.out.println(regex("A12345678", regex));
		System.out.println(regex("1234A56789", regex));
		System.out.println(regex("1234567890A", regex));
		System.out.println(regex("12345678901", regex));
		System.out.println(regex("123456789012", regex));
		System.out.println(regex("1234567890123", regex));
		System.out.println(regex("12345678901A", regex));
	}
	private String regex(String str, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);

		if (m.matches()){
		  return "[" + str + "]" + "[" + regex + "]" + "マッチしました";
		}else{
			  return "[" + str + "]" + "[" + regex + "]" + "マッチしません";
		}
	}
	public RegexTest() {
		init();
	}
	public static void main(String[] args) {
		new RegexTest();
	}
}
