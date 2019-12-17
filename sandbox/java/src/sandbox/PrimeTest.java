package sandbox;

public class PrimeTest {
	
	/**
	 * 素数チェック
	 * 与えられた数値chkが素数下どうかを返す
	 * @param chk素数チェックの対象となる数値
	 * @return chkが素数の場合true、それ以外の場合false
	 */
	private static boolean isPrime(int chk) {
		for(int i = 2; i <= (chk / 2); i++) {
			if(chk % i == 0) return false;
		}
		return true;
	}
	
	/**
	 * 与えられた数値chkより小さい数値の中で素数がいくつあるのかをチェックして返す
	 * @param chk 素数チェックの対象となる数値
	 * @return chkより小さい数値の素数の数
	 */
	private static int count(int chk) {
		int cnt = 0;
		for(int i = chk - 1; i > 1; i--) {
			if(isPrime(i)) cnt++;
		}
		return cnt;
	}
	
	/**
	 * メイン処理
	 * @param args 素数チェックの対象となる数字
	 */
	public static void main(String args[]) {
		for(int i = 0; i < args.length; i++) {
			System.out.println(args[i] + " : " + count(Integer.parseInt(args[i])));
		}
	}
}
