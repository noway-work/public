package sandbox;

public class ReplaceTest {

	public static void main(String[] args) {
		String temp = "https://192.168.1.107:7183/top/CSfTop.jsp";
		System.out.println(temp.replaceFirst("https?://([^:/]+).*", "$1"));
	}
}
