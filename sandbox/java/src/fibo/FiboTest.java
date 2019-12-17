package fibo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FiboTest {
	/**
	 * 主処理
	 * @param args　1つ目のパラメータ：課題ファイルのパス（必須）
	 * 2つ目のパラメータ：課題の結果を出力する先（任意、渡されない場合には標準出力に結果を出力）
	 */
	public static void main(String[] args) {
		QuestionManager qm = null;
		Solve solve = null;
		
		// パラメータ数不正
		if(args.length < 1 || args.length > 2) {
			System.out.println("parameter error");
			return;
		}
		try {
			// 課題管理クラス
			qm = new QuestionManagerForFibo(args[0]);
			
			// 課題解決クラス
			if(args.length == 1) {
				solve = new SolveForFibo(qm);
			} else {
				solve = new SolveForFibo(qm, args[1]);
			}
			
			solve.compute();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(qm != null) qm.quit();
				if(solve != null) solve.quit();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
}

/**
 *　課題クラス
 */
class Questionnaire {
	private String num;

	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
}

/**
 * 課題管理クラス
 */
class QuestionManagerForFibo extends QuestionManager {
	
	/* (non-Javadoc)
	 * @see hitandblow.QuestionManager#setQuestionnaire(java.lang.String, int)
	 */
	void setQuestionnaire(String s, int i) {
		qst[i] = new Questionnaire();
		qst[i].setNum(s);
	}
	
	/**
	 * 課題管理クラスのコンストラクタ
	 * 課題ファイルの内容を読み込み、その内容を課題クラスにして管理
	 * @param s　課題ファイルのパス
	 * @throws IOException
	 */
	public QuestionManagerForFibo(String s) throws IOException {
		super(s);
	}
}

/**
 * 課題の回答を求めるクラス
 */
class SolveForFibo extends Solve {
	
	private long fibonacchi(long num) {
		long num1 = 0;
		long num2 = 0;
		long num3 = 0;
		
		if(num >= 1) num2 = 0;
		if(num >= 2) {
			num3 = num2;
			num2 = 1;
			num1 = num3;
		}
		for(int i = 3; num > i; i++) {
			num3 = num2;
			num2 = num1 + num2;
			if(num2 > 999) num2 = num2 - 1000;
			num1 = num3;
		}
		num2 = num1 + num2;
		if(num2 > 999) num2 = num2 - 1000;
		return num2;
	}
	
	/* (non-Javadoc)
	 * @see hitandblow.Solve#compute()
	 */
	void compute() throws IOException {
		Long res = null;
		
		// 質問数分のループ
		for(int i = 0; i < qm.getQuestionSize(); i++) {
			// 初期化
			qst = qm.getQuestionnaire(i);
			
			// 結果をファイルに出力
			res = fibonacchi(Long.parseLong(qst.getNum()) - 2) + fibonacchi(Long.parseLong(qst.getNum()) - 1);
			if(res > 999) res = res - 1000;
			if(ofw == null) {
				// 結果出力ファイルが指定されていない場合は標準出力に出力
				System.out.println(res);
			} else {
				ofw.write(String.valueOf(res));
			}
		}
	}
	
	/**
	 * 課題解決クラスのコンストラクタ
	 * @param qst　回答を求める対象となる課題
	 * @param fineName　回答を出力する先のファイルパス
	 * @throws IOException 
	 */
	public SolveForFibo(QuestionManager qm, String fileName) throws IOException {
		super(qm, fileName);
	}
	
	/**
	 * 課題解決クラスのコンストラクタ
	 * @param qst　回答を求める対象となる課題
	 * @throws IOException 
	 */
	public SolveForFibo(QuestionManager qm) throws IOException {
		super(qm, "");
	}
}




/**
 * 課題管理クラス（スーパークラス）
 * 課題として与えられたファイルを読み込んで、課題の内容を管理する
 */
abstract class QuestionManager {
	private OrgFileReader ofr = null;
	Questionnaire[] qst = null;
	private String line = null;
	
	/**
	 * 課題として与えられたファイルの一行を課題クラスとして設定する
	 * @param s　課題として与えられたファイルの一行
	 * @param i　課題クラスの配列数
	 */
	
	abstract void setQuestionnaire(String s, int i);
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

	/**
	 * 簡易ファイル読み込みクラス
	 */
	private class OrgFileReader {
		BufferedReader br = null;
		FileReader fr = null;
		
		/**
		 * ファイルの一行を読み込んで、結果を返す
		 * @return　ファイルの一行文字列
		 * @throws IOException
		 */
		public String read() throws IOException {
			return br.readLine();
		}
		
		/**
		 * オブジェクトの開放を行う
		 * @throws IOException
		 */
		public void quit() throws IOException {
			if(br != null) br.close();
			if(fr != null) fr.close();
		}
		
		/**
		 * コンストラクタ
		 * ファイル読み込み用のオブジェクト生成
		 * @param fileName　読み込み対象となるファイルパス
		 * @throws FileNotFoundException
		 */
		public OrgFileReader(String fileName) throws FileNotFoundException {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
		}
	}
}

/**
 * 課題の回答を求めるクラス（スーパークラス）
 */
abstract class Solve {
	OrgFileWriter ofw = null;
	QuestionManager qm = null;
	Questionnaire qst = null;
	
	/**
	 * 課題の計算を行う
	 * @throws IOException
	 */
	abstract void compute() throws IOException;

	/**
	 * オブジェクトの開放を行う
	 * @throws IOException
	 */
	public void quit() throws IOException {
		if(ofw != null) ofw.quit();
	}
	
	/**
	 * 課題解決クラスのコンストラクタ
	 * @param qst　回答を求める対象となる課題
	 * @param fineName　回答を出力する先のファイルパス
	 * @throws IOException 
	 */
	public Solve(QuestionManager qm, String fileName) throws IOException {
		this.qm = qm;
		if(fileName != null && !"".equals(fileName)) ofw = new OrgFileWriter(fileName);
	}

	/**
	 * 簡易ファイル書き込みクラス
	 */
	class OrgFileWriter {
		BufferedWriter bw = null;
		FileWriter fw = null;
		
		/**
		 * 渡された文字列をファイルに書き込む
		 * @param line　ファイルに書き込む文字列
		 * @throws IOException
		 */
		public void write(String line) throws IOException {
			bw.write(line);
			bw.write("\n");
			bw.flush();
		}
		
		/**
		 * オブジェクトの開放を行う
		 * @throws IOException
		 */
		public void quit() throws IOException {
			if(bw != null) bw.close();
			if(fw != null) fw.close();
		}
		
		/**
		 * コンストラクタ
		 * ファイル書き込み用のオブジェクト作成
		 * @param fileName　書き込み先のファイルパス
		 * @throws IOException
		 */
		public OrgFileWriter(String fileName) throws IOException {
			fw = new FileWriter(fileName);
			bw = new BufferedWriter(fw);
		}
	}
}
