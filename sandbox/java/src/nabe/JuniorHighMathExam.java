package nabe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JuniorHighMathExam {
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
			qm = new QuestionManagerForJuniorHigh(args[0]);
			
			// �ۑ�����N���X
			if(args.length == 1) {
				solve = new SolveForJuniorHigh(qm);
			} else {
				solve = new SolveForJuniorHigh(qm, args[1]);
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
	private String questionID = null;
	private String fromNumber = null;
	private String toNumber = null;
	private String targetNumber = null;
	private String answer = null;
	
	public String getQuestionID() {
		return questionID;
	}
	public void setQuestionID(String questionID) {
		this.questionID = questionID;
	}
	public String getFromNumber() {
		return fromNumber;
	}
	public void setFromNumber(String fromNumber) {
		this.fromNumber = fromNumber;
	}
	public String getToNumber() {
		return toNumber;
	}
	public void setToNumber(String toNumber) {
		this.toNumber = toNumber;
	}
	public String getTargetNumber() {
		return targetNumber;
	}
	public void setTargetNumber(String targetNumber) {
		this.targetNumber = targetNumber;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
}

/**
 * �ۑ�Ǘ��N���X
 */
class QuestionManagerForJuniorHigh extends QuestionManager {
	
	/* (non-Javadoc)
	 * @see hitandblow.QuestionManager#setQuestionnaire(java.lang.String, int)
	 */
	void setQuestionnaire(String s) {
		String[] temp1 = s.split("\t");
		String[] temp2 = temp1[1].split(",");
		
		if(temp1.length == 3 && temp2.length == 3) {
			Questionnaire question = new Questionnaire();
			question.setQuestionID(temp1[0]);
			question.setFromNumber(temp2[0]);
			question.setToNumber(temp2[1]);
			question.setTargetNumber(temp2[2]);
			question.setAnswer(temp1[2]);
			super.qst.add(question);
		}
	}
	
	void createQuestionnaire(OrgFileReader ofr) throws IOException {
		String line = null;
		
		while(ofr.ready()) {
			line = ofr.read();
			setQuestionnaire(line);
		}
	}
	
	/**
	 * �ۑ�Ǘ��N���X�̃R���X�g���N�^
	 * �ۑ�t�@�C���̓��e��ǂݍ��݁A���̓��e���ۑ�N���X�ɂ��ĊǗ�
	 * @param s�@�ۑ�t�@�C���̃p�X
	 * @throws IOException
	 */
	public QuestionManagerForJuniorHigh(String s) throws IOException {
		super(s);
	}
}

/**
 * �ۑ�̉񓚂����߂�N���X
 */
class SolveForJuniorHigh extends Solve {
	
	/* (non-Javadoc)
	 * @see hitandblow.Solve#compute()
	 */
	void compute() throws IOException {
		int cnt;
		
		// ���␔���̃��[�v
		for(int i = 0; i < qm.getQuestionSize(); i++) {
			// ������
			qst = qm.getQuestionnaire(i);
			cnt = 0;
			
			for(long j = Long.parseLong(qst.getFromNumber()); j <= Long.parseLong(qst.getToNumber()); j++) {
				cnt =+ count(String.valueOf(i), qst.getTargetNumber());
			}
			
			if(qst.getAnswer().equals(String.valueOf(cnt))) {
				if(ofw == null) {
					// ���ʏo�̓t�@�C�����w�肳��Ă��Ȃ��ꍇ�͕W���o�͂ɏo��
					System.out.println(qst.getQuestionID());
				} else {
					ofw.write(qst.getQuestionID());
				}
				
			}
		}
	}
	
	private int count(String num, String tgt) {
		if(!num.contains(tgt)) return 0;
		
		byte[] chars = num.getBytes();
		int cnt = 0;
		for(int i = 0; i < chars.length; i++) {
			if(tgt.equals(chars[i])) cnt++;
		}
		return cnt;
	}
	
	/**
	 * �ۑ�����N���X�̃R���X�g���N�^
	 * @param qst�@�񓚂����߂�ΏۂƂȂ�ۑ�
	 * @param fineName�@�񓚂��o�͂����̃t�@�C���p�X
	 * @throws IOException 
	 */
	public SolveForJuniorHigh(QuestionManager qm, String fileName) throws IOException {
		super(qm, fileName);
	}
	
	/**
	 * �ۑ�����N���X�̃R���X�g���N�^
	 * @param qst�@�񓚂����߂�ΏۂƂȂ�ۑ�
	 * @throws IOException 
	 */
	public SolveForJuniorHigh(QuestionManager qm) throws IOException {
		super(qm, "");
	}
}




/**
 * �ۑ�Ǘ��N���X�i�X�[�p�[�N���X�j
 * �ۑ�Ƃ��ė^����ꂽ�t�@�C����ǂݍ���ŁA�ۑ�̓��e���Ǘ�����
 */
abstract class QuestionManager {
	private OrgFileReader ofr = null;
	ArrayList<Questionnaire> qst = null;
	//Questionnaire[] qst = null;
	
	/**
	 * �ۑ�Ƃ��ė^����ꂽ�t�@�C���̈�s���ۑ�N���X�Ƃ��Đݒ肷��
	 * @param s�@�ۑ�Ƃ��ė^����ꂽ�t�@�C���̈�s
	 * @param i�@�ۑ�N���X�̔z��
	 */
	abstract void setQuestionnaire(String s);
	
	/**
	 * �ۑ茏����Ԃ�
	 * @return�@�ۑ茏��
	 */
	public int getQuestionSize() {
		return qst.size();
	}
	
	/**
	 * ����C���f�b�N�X�̉ۑ�N���X��Ԃ�
	 * @param i�@�C���f�b�N�X
	 * @return�@���͂��ꂽ�C���f�b�N�X�̉ۑ�N���X
	 */
	public Questionnaire getQuestionnaire(int i) {
		return qst.get(i);
	}
	
	/**
	 * �ۑ�Ǘ��N���X�̏I�������i�t�@�C���ǂݍ��݃N���X�̏I���j
	 * @throws IOException
	 */
	public void quit() throws IOException {
		if(ofr != null) ofr.quit();
	}
	
	abstract void createQuestionnaire(OrgFileReader ofr) throws IOException;
	
	/**
	 * �ۑ�Ǘ��N���X�̃R���X�g���N�^
	 * �ۑ�t�@�C���̓��e��ǂݍ��݁A���̓��e���ۑ�N���X�ɂ��ĊǗ�
	 * @param s�@�ۑ�t�@�C���̃p�X
	 * @throws IOException
	 */
	public QuestionManager(String s) throws IOException {
		ofr = new OrgFileReader(s);
		qst = new ArrayList<Questionnaire>();
		
		createQuestionnaire(ofr);
	}

	/**
	 * �ȈՃt�@�C���ǂݍ��݃N���X
	 */
	class OrgFileReader {
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
		
		public boolean ready() throws IOException {
			return br.ready();
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
