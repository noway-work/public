package stamp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * 切手の切り取り方総数を求めるクラス
 */
public class StampChecker {
	/**
	 * 切手の切り取り方総数を求める主処理
	 * @param args 1番目が入力ファイル（課題となるファイル）　2番目が結果出力ファイル
	 */
	public static void main(String[] args) {
		QuestionManager qm = null;
		OrgFileWriter ofw = null;
		try {
			// 課題管理クラス
			qm = new QuestionManager(args[0]);
			// 結果出力用クラス
			ofw = new OrgFileWriter(args[1]);
			for(int i = 0; i < qm.getQuestionSize(); i++) {
				//System.out.println(qm.getQuestionnaire(i).getHeight());
				//System.out.println(qm.getQuestionnaire(i).getWidth());
				//System.out.println(qm.getQuestionnaire(i).getNum());
				// 課題の回答を求めるクラス
				Solve solve = new Solve(qm.getQuestionnaire(i));
				ofw.write(String.valueOf(solve.count()));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				qm.quit();
				ofw.quit();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

/**
 * 課題の回答を求めるクラス
 */
class Solve {
	private Questionnaire qst = null;

	/**
	 * コンストラクタ
	 * @param qst　回答を求める対象となる課題
	 */
	public Solve(Questionnaire qst) {
		this.qst = qst;
	}
	/**
	 * 特定の文字列の左に"0"埋めする
	 * @param s　"0"埋め対象となる文字列
	 * @param n　左"0"埋めした後の桁数
	 * @return　左"0"埋めした文字列
	 */
	private String padLeft(String s, int n) {
	    return String.format("%1$0" + n +"d", new BigInteger(s));  
	}
	/**
	 * テーブル文字列が対象かどうかを判定する
	 * 切手の全体が0と1の文字列で表現されたテーブル文字列の内、切り取る対象となる切手枚数が規定の数（1となっている文字の数が規定の数）になっているかを判定
	 * @param s　2進数のテーブル文字列
	 * @return　切り取る対象となる切手の枚数から入力されてテーブル文字列が対象となるか否かを判定して返す
	 * ※縦2枚、横3枚の切手で切り取りたい枚数が3の時、010101は1が3つなのでtrue、010100は1が2つなのでfalse
	 */
	private boolean isTarget(String s) {
		return new StringBuilder().append("0").append(s).append("0").toString().split("1").length == qst.getNum() + 1;
	}
	/**
	 * テーブル文字列をArrayListで作成して返す
	 * @return　2進数で切手の全体となる桁数までの値で、なおかつ切り取る対象となる切手の枚数が指定と合っている文字列をArrayList化して返す
	 * ※縦2枚、横3枚の場合は000000から111111までの値が対象となり、切り取る対象枚数が2枚の場合001100等の1が2つの文字列のみをArrayList化して返す
	 */
	private ArrayList<String> createTable() {
		// binary
		String temp = null;
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; true; i++) {
			temp = Integer.toBinaryString(i);
			if(temp.length() > qst.getHeight() * qst.getWidth()) break;
			if(isTarget(temp)) list.add(padLeft(temp, qst.getHeight() * qst.getWidth()));
		}
			
		return list;
	}
	/**
	 * 2進数のテーブル文字列の内、1となっている桁数の数値を配列として返す
	 * @param s　2進数のテーブル文字列
	 * @return　2進数のテーブル文字列の内、1となっている桁数の数値
	 * ※010101の場合、[0][2][4]という配列が返る（1桁目は0）
	 */
	private Integer[] getTableNumbers(String s) {
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		for(int i = 0; i < s.length(); i++) {
			// テーブル文字列の中で"1"となっている桁数の数値をArrayListに追加
			if("1".equals(s.substring(i, i + 1))) numbers.add(new Integer(i));
		}
		return (Integer[]) numbers.toArray(new Integer[]{});
	}
	/**
	 * 1になっている桁数が2つ渡されるので、それらの桁が切手シート上で隣同士であるかを判定する
	 * @param num1　切り取る切手1枚目
	 * @param num2　切り取る切手2枚目
	 * @return　入力された2枚の切手が隣同士（左右隣、上下隣）であるかを判定して返す
	 */
	private boolean isNeighbor(Integer num1, Integer num2) {
		// num1が左端
		if(num1 % qst.getWidth() == 0) {
			// num1が左端で、num2がその右隣り
//System.out.println("ino1");
			if(num1 == num2 - 1) return true;
			// num2も左端
			else if(num2 % qst.getWidth() == 0) return true;
		// num1が右端
		} else if(num1 % qst.getWidth() == qst.getWidth() - 1) {
//System.out.println("ino2");
			// num1が右端で、num2がその左隣り
			if(num1 == num2 + 1) return true;
			// num2も右端
			else if(num2 % qst.getWidth() == qst.getWidth() - 1) return true;
		// num1が間の列
		} else {
//System.out.println("ino3");
			// num1がnum2の左隣り
			if(num1 == num2 - 1) return true;
			// num1がnum2の右隣り
			if(num1 == num2 + 1) return true;
			// num1がnum2の上か下
			if(num1 % qst.getWidth() == num2 % qst.getWidth()) return true;
		}
//System.out.println("ino4");
		// 隣同士ではない
		return false;
	}
	/**
	 * 切手が全て繋がったまま切り取れるかを返す
	 * @param s　2進数のテーブル文字列
	 * @return　入力されたテーブル文字列が切手シートとシート内で切り取る切手を示している為、切手が全て繋がったまま切り取れるかを返す
	 */
	private boolean check(String s) {
		// 1となっている桁数の数値を配列化して取得
		Integer[] tableNumbers = getTableNumbers(s);
		boolean flag = false;
		System.out.println(s);
		
		// 比較元のインデックス
		for(int i = 0; i < tableNumbers.length; i++) {
			flag = false;
			// 比較先のインデックス
			for(int j = 0; j < tableNumbers.length; j++) {
				// 比較元と比較先で一つでもお隣同士があれば次の比較へ
				if(flag) break;
				// 同一インデックスは比較対象外
				if(i == j) continue;
				System.out.println(tableNumbers[i] + " : " + tableNumbers[j]);
				// 比較元と比較先がお隣同士かを確認
				if(isNeighbor(tableNumbers[i], tableNumbers[j])) flag = true;
			}
//System.out.println("false");
			// 比較元と比較先でお隣同士が無ければ繋がったまま切り取れないと判断してfalseを返す
			if(!flag) return false;
		}
//System.out.println("true");
		// 全ての比較元と比較先がお隣同士の場合は繋がったまま切り取れると判断してtrueを返す
		return true;
	}
	/**
	 * 切手シート上で繋がったまま切り取れるパターンをカウントして返す
	 * @return　切手シート上の切手が繋がったまま切り取れるパターン数
	 */
	public int count() {
		int cnt = 0;
		// 対象となるテーブル文字列を作成
		ArrayList<String> list = createTable();
		for(int i = 0; i < list.size(); i++) {
			// 対象となるテーブル文字列を1つずつチェック
			if(check(list.get(i))) cnt++;
		}
		return cnt;
	}
}

/**
 *　課題クラス
 *　課題として与えられる「切手シートの縦枚数」「切手シートの横枚数」「切り取りたい切手の枚数」を管理するクラス
 */
class Questionnaire {
	private int height;
	private int width;
	private int num;
	
	/**
	 * 切手シートの縦枚数を返す
	 * @return　切手シートの縦枚数
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * 切手シートの縦枚数を設定する
	 * @param height　切手シートの縦枚数
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	/**
	 * 切手シートの横枚数を返す
	 * @return　切手シートの横枚数
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * 切手シートの横枚数を設定する
	 * @param width　切手シートの横枚数
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * 切り取りたい切手の枚数を返す
	 * @return　切り取りたい切手の枚数
	 */
	public int getNum() {
		return num;
	}
	/**
	 * 切り取りたい切手の枚数を設定する
	 * @param num　切り取りたい切手の枚数
	 */
	public void setNum(int num) {
		this.num = num;
	}
}

/**
 * 課題管理クラス
 * 課題として与えられたファイルを読み込んで、課題の内容を管理する
 */
class QuestionManager {
	private OrgFileReader ofr = null;
	private Questionnaire[] qst = null;
	private String line = null;
	
	/**
	 * 課題として与えられたファイルの一行を課題クラスとして設定する
	 * @param s　課題として与えられたファイルの一行
	 * @param i　課題クラスの配列数
	 */
	private void setQuestionnaire(String s, int i) {
		String[] temp = s.split(" ");
		if(temp.length == 3) {
			qst[i] = new Questionnaire();
			// 切手シートの縦枚数
			qst[i].setHeight(Integer.parseInt(temp[0]));
			// 切手シートの横枚数
			qst[i].setWidth(Integer.parseInt(temp[1]));
			// 切り取りたい切手の枚数
			qst[i].setNum(Integer.parseInt(temp[2]));
		}
	}
	/**
	 * 課題件数を返す
	 * @return　課題件数
	 */
	public int getQuestionSize() {
		return qst.length;
	}
	/**
	 * 特定インデックスの課題クラスを返す
	 * @param i　インデックス
	 * @return　入力されたインデックスの課題クラス
	 */
	public Questionnaire getQuestionnaire(int i) {
		return qst[i];
	}
	/**
	 * 課題管理クラスの終了処理（ファイル読み込みクラスの終了）
	 * @throws IOException
	 */
	public void quit() throws IOException {
		if(ofr != null) ofr.quit();
	}
	/**
	 * 課題管理クラスのコンストラクタ
	 * 課題ファイルの内容を読み込み、その内容を課題クラスにして管理
	 * @param s　課題ファイルのパス
	 * @throws IOException
	 */
	public QuestionManager(String s) throws IOException {
		ofr = new OrgFileReader(s);
		
		line = ofr.read();
		qst = new Questionnaire[Integer.parseInt(line)];
		
		for(int i = 0; true; i++) {
			line = ofr.read();
			if(line == null) break;
			setQuestionnaire(line, i);
		}
	}
}

/**
 * 簡易ファイル書き込みクラス
 */
class OrgFileWriter {
	BufferedWriter bw = null;
	FileWriter fw = null;
	
	/**
	 * @param line
	 * @throws IOException
	 */
	public void write(String line) throws IOException {
		bw.write(line);
		bw.write("\n");
		bw.flush();
	}
	/**
	 * @throws IOException
	 */
	public void quit() throws IOException {
		bw.close();
		fw.close();
	}
	/**
	 * @param fileName
	 * @throws IOException
	 */
	public OrgFileWriter(String fileName) throws IOException {
		fw = new FileWriter(fileName);
		bw = new BufferedWriter(fw);
	}
}

/**
 * 簡易ファイル読み込みクラス
 */
class OrgFileReader {
	BufferedReader br = null;
	FileReader fr = null;
	
	/**
	 * @return
	 * @throws IOException
	 */
	public String read() throws IOException {
		return br.readLine();
	}
	/**
	 * @throws IOException
	 */
	public void quit() throws IOException {
		br.close();
		fr.close();
	}
	/**
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	public OrgFileReader(String fileName) throws FileNotFoundException {
		fr = new FileReader(fileName);
		br = new BufferedReader(fr);
	}
}
