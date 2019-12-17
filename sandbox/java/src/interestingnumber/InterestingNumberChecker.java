package interestingnumber;

import java.math.BigDecimal;
import java.util.ArrayList;

public class InterestingNumberChecker {

	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		// 1桁の数値で2乗して下桁が元の数値と同じ値を取得
		for(int i = 1; i < 10; i++) {
			if(isTarget(i)) list.add(String.valueOf(i));
		}
		
		// 桁数ループ（1桁から9桁まで）
		for(int i = 1; i < 9; i++) {
			list = compute(list, i);
		}
		
		// 結果出力のループ
		for(int i = 0; i < list.size(); i++) {
			if(isOutput(list.get(i))) {
				System.out.println(list.get(i));
			}
		}
	}
	
	/**
	 * 出力対象か否かの判定
	 * @param val
	 * @return
	 */
	private static boolean isOutput(String val) {
		int temp1 = Integer.parseInt(val);
		return String.valueOf(temp1).length() == 9;
	}
	
	/**
	 * 単純にループで結果を求めるロジック（正解値を確認する用）
	 * 結果は以下の2つの数値
	 * 212890625
	 * 787109376
	 */
	/*
	private static void compute2() {
		BigDecimal src = null;
		BigDecimal num1 = null;
		BigDecimal num2 = null;
		
		BigDecimal TEMP = new BigDecimal(1000000000);
		BigDecimal ZERO = new BigDecimal(0);
		
		DecimalFormat df = new DecimalFormat("0.##");
		
		for(int i = 100000000; i < 1000000000; i++) {
			src = new BigDecimal(i);
			num1 = src.pow(2);
			num2 = num1.subtract(src);
			if(num2.remainder(TEMP).compareTo(ZERO) == 0) {
				System.out.println(i);
			}
		}
	}
	*/
	
	private static ArrayList<String> compute(ArrayList<String> list, int keta) {
		ArrayList<String> result = new ArrayList<String>();
		int temp = 0;
		boolean flag;
		
		// 候補リストのループ
		for(int i = 0; i < list.size(); i++) {
			flag = false;
			// 候補リストの値の頭に1から9までの文字を付けて対称となるかを確認するループ
			for(int j = 1; j < 10; j++) {
				temp = (int) ((j * Math.pow(10, keta)) + new Integer(list.get(i).toString()).intValue());
				if(isTarget(temp)) {
					flag = true;
					result.add(String.valueOf(temp));
				}
			}
			// 対象となる候補が無い場合には頭に0をつけて次のループにチャレンジ
			if(!flag) {
				result.add("0" + String.valueOf(list.get(i)));
			}
		}
		
		return result;
	}
	
	/**
	 * 渡された数値を2乗し、渡された値と2乗した結果の下桁が同じ数値かどうかを返す
	 * @param i
	 * @return
	 */
	private static boolean isTarget(int i) {
		BigDecimal ZERO = new BigDecimal("0");
		BigDecimal ONE = new BigDecimal("1");
		
		BigDecimal src = null;
		BigDecimal num1 = null;
		
		src = new BigDecimal(i);
		num1 = src.multiply(src);	// 2乗
		
		int len = src.toString().length();
		return num1.subtract(src).remainder(ONE.movePointRight(len)).compareTo(ZERO) == 0;
	}
}
