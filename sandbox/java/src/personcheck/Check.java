package personcheck;

public class Check {
	/**
	 * メイン処理
	 * @param args 入力パラメータ（使用せず）
	 */
	public static void main(String args[]) {
		ChildTable table = null;
		int cnt = 0;
		System.out.println("start");
		for(int i = 6; true; i++) {
			// 特別条件（偶数はtrueにならない為に処理対象外とする）
			if(i % 2 == 0) continue;
			
			// メイン処理
			table = new ChildTable(i);
			if(table.check()) {
				// 結果の出力
				System.out.println("result[" + (cnt + 1) + "]:" + i);
				if(++cnt == 5) break;
			}
		}
		System.out.println("end");
	}
}

/**
 * 子供を管理するクラス
 * @author ifactory
 */
class ChildTable {
	private int idx = 0;
	private int[] table = null;

	/**
	 * コンストラクタ
	 * idxに最後尾の男の子の数値をセット
	 * tableに男の子と女の子を01で表現した文字列をセット
	 * ※"0"はまだチョコをもらえずにテーブルにいる子、"1"はチョコをもらってテーブルから離れた子を表現
	 * @param i
	 */
	public ChildTable(int i) {
		idx = i - 1;
		table = new int[i * 2];
		//System.out.println("test1:" + toString(table));
	}
	
	/**
	 * 今回の処理で最後の子が列から離れるかどうかを判定
	 * @return 最後はtrue、それ以外はfalse
	 */
	private boolean isLastChild() {
		int cnt = 0;
		// "0"が複数あればfalse
		for(int i = 0;i < table.length; i++) {
			if(table[i] == 0) {
				if(cnt == 1) return false;
				cnt++;
			}
		}
		return true;
	}
	
	/**
	 * 最後尾の男の子が処理対象（席を離れる）かどうかを判定
	 * @param i 処理対象の子
	 * @return 処理対象が最後尾の男の子の場合はtrue、それ以外はfalse
	 */
	private boolean isLastBoy(int i) {
		return i == idx;
	}
	
	/**
	 * 次の子供のインデックスを返す
	 * @param i 現在の処理対象の子供
	 * @return 次の処理対象の子供
	 */
	private int getNextChildNumber(int i) {
		int ret = i + 1;
		int cnt = 0;
		while(true) {
			//System.out.println("test3:" + ret + "," + table.length);
			if(ret < table.length && 0 == table[ret] && ++cnt > 1) break;
			if(++ret >= table.length) ret = 0;
		}
		return ret;
	}
	
	/**
	 * 対象の子供をテーブルから離れさせる
	 * ※フラグセットのみ
	 * @param i 処理対象の子供
	 */
	private void leaveTable(int i) {
		table[i] = 1;
		//System.out.println("test2:" + toString(table));
	}
	
	/**
	 * デバッグ用ログ出力メソッド
	 * 子供を管理している変数tableの内容を出力する事を想定
	 * @param args 子供管理変数が渡される事を想定
	 * @return 子供管理変数の内容をStringの形で返す
	 */
	private String toString(int args[]) {
		StringBuilder temp = new StringBuilder();
		for(int i = 0; i < args.length; i++) {
			temp.append(Integer.toString(args[i]));
		}
		return temp.toString();
	}
	
	/**
	 * 最後尾の男の子が最後に席を離れるかどうかのチェック処理を実行
	 * @return 最後尾の男の子が最後に席を離れる場合はtrue、それ以外はfalse
	 */
	public boolean check() {
		int i = -1;
		while(true) {
			i = getNextChildNumber(i);
			// 最後の一人
			if(isLastChild()) break;
			// 最後の一人を待たずに最後尾の男の子がテーブルを離れた場合は処理終了
			if(isLastBoy(i)) return false;
			// 対象となる子供をテーブルから離れさせる
			leaveTable(i);
		}
		return true;
	}
	
}