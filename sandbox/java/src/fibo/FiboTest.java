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
	 * �又��
	 * @param args�@1�ڂ̃p�����[�^�F�ۑ�t�@�C���̃p�X�i�K�{�j
	 * 2�ڂ̃p�����[�^�F�ۑ�̌��ʂ��o�͂����i�C�ӁA�n����Ȃ��ꍇ�ɂ͕W���o�͂Ɍ��ʂ��o�́j
	 */
	public static void main(String[] args) {
		QuestionManager qm = null;
		Solve solve = null;
		
		// �p�����[�^���s��
		if(args.length < 1 || args.length > 2) {
			System.out.println("parameter error");
			return;
		}
		try {
			// �ۑ�Ǘ��N���X
			qm = new QuestionManagerForFibo(args[0]);
			
			// �ۑ�����N���X
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
 *�@�ۑ�N���X
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
 * �ۑ�Ǘ��N���X
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
	 * �ۑ�Ǘ��N���X�̃R���X�g���N�^
	 * �ۑ�t�@�C���̓��e��ǂݍ��݁A���̓��e���ۑ�N���X�ɂ��ĊǗ�
	 * @param s�@�ۑ�t�@�C���̃p�X
	 * @throws IOException
	 */
	public QuestionManagerForFibo(String s) throws IOException {
		super(s);
	}
}

/**
 * �ۑ�̉񓚂����߂�N���X
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
		
		// ���␔���̃��[�v
		for(int i = 0; i < qm.getQuestionSize(); i++) {
			// ������
			qst = qm.getQuestionnaire(i);
			
			// ���ʂ��t�@�C���ɏo��
			res = fibonacchi(Long.parseLong(qst.getNum()) - 2) + fibonacchi(Long.parseLong(qst.getNum()) - 1);
			if(res > 999) res = res - 1000;
			if(ofw == null) {
				// ���ʏo�̓t�@�C�����w�肳��Ă��Ȃ��ꍇ�͕W���o�͂ɏo��
				System.out.println(res);
			} else {
				ofw.write(String.valueOf(res));
			}
		}
	}
	
	/**
	 * �ۑ�����N���X�̃R���X�g���N�^
	 * @param qst�@�񓚂����߂�ΏۂƂȂ�ۑ�
	 * @param fineName�@�񓚂��o�͂����̃t�@�C���p�X
	 * @throws IOException 
	 */
	public SolveForFibo(QuestionManager qm, String fileName) throws IOException {
		super(qm, fileName);
	}
	
	/**
	 * �ۑ�����N���X�̃R���X�g���N�^
	 * @param qst�@�񓚂����߂�ΏۂƂȂ�ۑ�
	 * @throws IOException 
	 */
	public SolveForFibo(QuestionManager qm) throws IOException {
		super(qm, "");
	}
}




/**
 * �ۑ�Ǘ��N���X�i�X�[�p�[�N���X�j
 * �ۑ�Ƃ��ė^����ꂽ�t�@�C����ǂݍ���ŁA�ۑ�̓��e���Ǘ�����
 */
abstract class QuestionManager {
	private OrgFileReader ofr = null;
	Questionnaire[] qst = null;
	private String line = null;
	
	/**
	 * �ۑ�Ƃ��ė^����ꂽ�t�@�C���̈�s���ۑ�N���X�Ƃ��Đݒ肷��
	 * @param s�@�ۑ�Ƃ��ė^����ꂽ�t�@�C���̈�s
	 * @param i�@�ۑ�N���X�̔z��
	 */
	
	abstract void setQuestionnaire(String s, int i);
	/**
	 * �ۑ茏����Ԃ�
	 * @return�@�ۑ茏��
	 */
	public int getQuestionSize() {
		return qst.length;
	}
	
	/**
	 * ����C���f�b�N�X�̉ۑ�N���X��Ԃ�
	 * @param i�@�C���f�b�N�X
	 * @return�@���͂��ꂽ�C���f�b�N�X�̉ۑ�N���X
	 */
	public Questionnaire getQuestionnaire(int i) {
		return qst[i];
	}
	
	/**
	 * �ۑ�Ǘ��N���X�̏I�������i�t�@�C���ǂݍ��݃N���X�̏I���j
	 * @throws IOException
	 */
	public void quit() throws IOException {
		if(ofr != null) ofr.quit();
	}
	
	/**
	 * �ۑ�Ǘ��N���X�̃R���X�g���N�^
	 * �ۑ�t�@�C���̓��e��ǂݍ��݁A���̓��e���ۑ�N���X�ɂ��ĊǗ�
	 * @param s�@�ۑ�t�@�C���̃p�X
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
	 * �ȈՃt�@�C���ǂݍ��݃N���X
	 */
	private class OrgFileReader {
		BufferedReader br = null;
		FileReader fr = null;
		
		/**
		 * �t�@�C���̈�s��ǂݍ���ŁA���ʂ�Ԃ�
		 * @return�@�t�@�C���̈�s������
		 * @throws IOException
		 */
		public String read() throws IOException {
			return br.readLine();
		}
		
		/**
		 * �I�u�W�F�N�g�̊J�����s��
		 * @throws IOException
		 */
		public void quit() throws IOException {
			if(br != null) br.close();
			if(fr != null) fr.close();
		}
		
		/**
		 * �R���X�g���N�^
		 * �t�@�C���ǂݍ��ݗp�̃I�u�W�F�N�g����
		 * @param fileName�@�ǂݍ��ݑΏۂƂȂ�t�@�C���p�X
		 * @throws FileNotFoundException
		 */
		public OrgFileReader(String fileName) throws FileNotFoundException {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
		}
	}
}

/**
 * �ۑ�̉񓚂����߂�N���X�i�X�[�p�[�N���X�j
 */
abstract class Solve {
	OrgFileWriter ofw = null;
	QuestionManager qm = null;
	Questionnaire qst = null;
	
	/**
	 * �ۑ�̌v�Z���s��
	 * @throws IOException
	 */
	abstract void compute() throws IOException;

	/**
	 * �I�u�W�F�N�g�̊J�����s��
	 * @throws IOException
	 */
	public void quit() throws IOException {
		if(ofw != null) ofw.quit();
	}
	
	/**
	 * �ۑ�����N���X�̃R���X�g���N�^
	 * @param qst�@�񓚂����߂�ΏۂƂȂ�ۑ�
	 * @param fineName�@�񓚂��o�͂����̃t�@�C���p�X
	 * @throws IOException 
	 */
	public Solve(QuestionManager qm, String fileName) throws IOException {
		this.qm = qm;
		if(fileName != null && !"".equals(fileName)) ofw = new OrgFileWriter(fileName);
	}

	/**
	 * �ȈՃt�@�C���������݃N���X
	 */
	class OrgFileWriter {
		BufferedWriter bw = null;
		FileWriter fw = null;
		
		/**
		 * �n���ꂽ��������t�@�C���ɏ�������
		 * @param line�@�t�@�C���ɏ������ޕ�����
		 * @throws IOException
		 */
		public void write(String line) throws IOException {
			bw.write(line);
			bw.write("\n");
			bw.flush();
		}
		
		/**
		 * �I�u�W�F�N�g�̊J�����s��
		 * @throws IOException
		 */
		public void quit() throws IOException {
			if(bw != null) bw.close();
			if(fw != null) fw.close();
		}
		
		/**
		 * �R���X�g���N�^
		 * �t�@�C���������ݗp�̃I�u�W�F�N�g�쐬
		 * @param fileName�@�������ݐ�̃t�@�C���p�X
		 * @throws IOException
		 */
		public OrgFileWriter(String fileName) throws IOException {
			fw = new FileWriter(fileName);
			bw = new BufferedWriter(fw);
		}
	}
}
