package sandbox;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {
	
	public DateTest() {
		init();
	}
	
	public static void main(String args[]) {
		new DateTest();
	}
	
	private Boolean check(String time) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
			sdf.setLenient(false);
			Date result = sdf.parse(time);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private void init() {
		System.out.println("----------\nSucc");
		System.out.println(check("0000"));
		System.out.println("\n----------\nFail1");
		System.out.println(check("2400"));
		System.out.println("\n----------\nFail2");
		System.out.println(check("2360"));
	}
}

