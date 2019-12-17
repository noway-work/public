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
 * �؎�̐؂�������������߂�N���X
 */
public class StampChecker {
	/**
	 * �؎�̐؂�������������߂�又��
	 * @param args 1�Ԗڂ����̓t�@�C���i�ۑ�ƂȂ�t�@�C���j�@2�Ԗڂ����ʏo�̓t�@�C��
	 */
	public static void main(String[] args) {
		QuestionManager qm = null;
		OrgFileWriter ofw = null;
		try {
			// �ۑ�Ǘ��N���X
			qm = new QuestionManager(args[0]);
			// ���ʏo�͗p�N���X
			ofw = new OrgFileWriter(args[1]);
			for(int i = 0; i < qm.getQuestionSize(); i++) {
				//System.out.println(qm.getQuestionnaire(i).getHeight());
				//System.out.println(qm.getQuestionnaire(i).getWidth());
				//System.out.println(qm.getQuestionnaire(i).getNum());
				// �ۑ�̉񓚂����߂�N���X
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
 * �ۑ�̉񓚂����߂�N���X
 */
class Solve {
	private Questionnaire qst = null;

	/**
	 * �R���X�g���N�^
	 * @param qst�@�񓚂����߂�ΏۂƂȂ�ۑ�
	 */
	public Solve(Questionnaire qst) {
		this.qst = qst;
	}
	/**
	 * ����̕�����̍���"0"���߂���
	 * @param s�@"0"���ߑΏۂƂȂ镶����
	 * @param n�@��"0"���߂�����̌���
	 * @return�@��"0"���߂���������
	 */
	private String padLeft(String s, int n) {
	    return String.format("%1$0" + n +"d", new BigInteger(s));  
	}
	/**
	 * �e�[�u�������񂪑Ώۂ��ǂ����𔻒肷��
	 * �؎�̑S�̂�0��1�̕�����ŕ\�����ꂽ�e�[�u��������̓��A�؂���ΏۂƂȂ�؎薇�����K��̐��i1�ƂȂ��Ă��镶���̐����K��̐��j�ɂȂ��Ă��邩�𔻒�
	 * @param s�@2�i���̃e�[�u��������
	 * @return�@�؂���ΏۂƂȂ�؎�̖���������͂���ăe�[�u�������񂪑ΏۂƂȂ邩�ۂ��𔻒肵�ĕԂ�
	 * ���c2���A��3���̐؎�Ő؂��肽��������3�̎��A010101��1��3�Ȃ̂�true�A010100��1��2�Ȃ̂�false
	 */
	private boolean isTarget(String s) {
		return new StringBuilder().append("0").append(s).append("0").toString().split("1").length == qst.getNum() + 1;
	}
	/**
	 * �e�[�u���������ArrayList�ō쐬���ĕԂ�
	 * @return�@2�i���Ő؎�̑S�̂ƂȂ錅���܂ł̒l�ŁA�Ȃ����؂���ΏۂƂȂ�؎�̖������w��ƍ����Ă��镶�����ArrayList�����ĕԂ�
	 * ���c2���A��3���̏ꍇ��000000����111111�܂ł̒l���ΏۂƂȂ�A�؂���Ώۖ�����2���̏ꍇ001100����1��2�̕�����݂̂�ArrayList�����ĕԂ�
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
	 * 2�i���̃e�[�u��������̓��A1�ƂȂ��Ă��錅���̐��l��z��Ƃ��ĕԂ�
	 * @param s�@2�i���̃e�[�u��������
	 * @return�@2�i���̃e�[�u��������̓��A1�ƂȂ��Ă��錅���̐��l
	 * ��010101�̏ꍇ�A[0][2][4]�Ƃ����z�񂪕Ԃ�i1���ڂ�0�j
	 */
	private Integer[] getTableNumbers(String s) {
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		for(int i = 0; i < s.length(); i++) {
			// �e�[�u��������̒���"1"�ƂȂ��Ă��錅���̐��l��ArrayList�ɒǉ�
			if("1".equals(s.substring(i, i + 1))) numbers.add(new Integer(i));
		}
		return (Integer[]) numbers.toArray(new Integer[]{});
	}
	/**
	 * 1�ɂȂ��Ă��錅����2�n�����̂ŁA�����̌����؎�V�[�g��ŗד��m�ł��邩�𔻒肷��
	 * @param num1�@�؂���؎�1����
	 * @param num2�@�؂���؎�2����
	 * @return�@���͂��ꂽ2���̐؎肪�ד��m�i���E�ׁA�㉺�ׁj�ł��邩�𔻒肵�ĕԂ�
	 */
	private boolean isNeighbor(Integer num1, Integer num2) {
		// num1�����[
		if(num1 % qst.getWidth() == 0) {
			// num1�����[�ŁAnum2�����̉E�ׂ�
//System.out.println("ino1");
			if(num1 == num2 - 1) return true;
			// num2�����[
			else if(num2 % qst.getWidth() == 0) return true;
		// num1���E�[
		} else if(num1 % qst.getWidth() == qst.getWidth() - 1) {
//System.out.println("ino2");
			// num1���E�[�ŁAnum2�����̍��ׂ�
			if(num1 == num2 + 1) return true;
			// num2���E�[
			else if(num2 % qst.getWidth() == qst.getWidth() - 1) return true;
		// num1���Ԃ̗�
		} else {
//System.out.println("ino3");
			// num1��num2�̍��ׂ�
			if(num1 == num2 - 1) return true;
			// num1��num2�̉E�ׂ�
			if(num1 == num2 + 1) return true;
			// num1��num2�̏ォ��
			if(num1 % qst.getWidth() == num2 % qst.getWidth()) return true;
		}
//System.out.println("ino4");
		// �ד��m�ł͂Ȃ�
		return false;
	}
	/**
	 * �؎肪�S�Čq�������܂ܐ؂���邩��Ԃ�
	 * @param s�@2�i���̃e�[�u��������
	 * @return�@���͂��ꂽ�e�[�u�������񂪐؎�V�[�g�ƃV�[�g���Ő؂���؎�������Ă���ׁA�؎肪�S�Čq�������܂ܐ؂���邩��Ԃ�
	 */
	private boolean check(String s) {
		// 1�ƂȂ��Ă��錅���̐��l��z�񉻂��Ď擾
		Integer[] tableNumbers = getTableNumbers(s);
		boolean flag = false;
		System.out.println(s);
		
		// ��r���̃C���f�b�N�X
		for(int i = 0; i < tableNumbers.length; i++) {
			flag = false;
			// ��r��̃C���f�b�N�X
			for(int j = 0; j < tableNumbers.length; j++) {
				// ��r���Ɣ�r��ň�ł����ד��m������Ύ��̔�r��
				if(flag) break;
				// ����C���f�b�N�X�͔�r�ΏۊO
				if(i == j) continue;
				System.out.println(tableNumbers[i] + " : " + tableNumbers[j]);
				// ��r���Ɣ�r�悪���ד��m�����m�F
				if(isNeighbor(tableNumbers[i], tableNumbers[j])) flag = true;
			}
//System.out.println("false");
			// ��r���Ɣ�r��ł��ד��m��������Όq�������܂ܐ؂���Ȃ��Ɣ��f����false��Ԃ�
			if(!flag) return false;
		}
//System.out.println("true");
		// �S�Ă̔�r���Ɣ�r�悪���ד��m�̏ꍇ�͌q�������܂ܐ؂����Ɣ��f����true��Ԃ�
		return true;
	}
	/**
	 * �؎�V�[�g��Ōq�������܂ܐ؂����p�^�[�����J�E���g���ĕԂ�
	 * @return�@�؎�V�[�g��̐؎肪�q�������܂ܐ؂����p�^�[����
	 */
	public int count() {
		int cnt = 0;
		// �ΏۂƂȂ�e�[�u����������쐬
		ArrayList<String> list = createTable();
		for(int i = 0; i < list.size(); i++) {
			// �ΏۂƂȂ�e�[�u���������1���`�F�b�N
			if(check(list.get(i))) cnt++;
		}
		return cnt;
	}
}

/**
 *�@�ۑ�N���X
 *�@�ۑ�Ƃ��ė^������u�؎�V�[�g�̏c�����v�u�؎�V�[�g�̉������v�u�؂��肽���؎�̖����v���Ǘ�����N���X
 */
class Questionnaire {
	private int height;
	private int width;
	private int num;
	
	/**
	 * �؎�V�[�g�̏c������Ԃ�
	 * @return�@�؎�V�[�g�̏c����
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * �؎�V�[�g�̏c������ݒ肷��
	 * @param height�@�؎�V�[�g�̏c����
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	/**
	 * �؎�V�[�g�̉�������Ԃ�
	 * @return�@�؎�V�[�g�̉�����
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * �؎�V�[�g�̉�������ݒ肷��
	 * @param width�@�؎�V�[�g�̉�����
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * �؂��肽���؎�̖�����Ԃ�
	 * @return�@�؂��肽���؎�̖���
	 */
	public int getNum() {
		return num;
	}
	/**
	 * �؂��肽���؎�̖�����ݒ肷��
	 * @param num�@�؂��肽���؎�̖���
	 */
	public void setNum(int num) {
		this.num = num;
	}
}

/**
 * �ۑ�Ǘ��N���X
 * �ۑ�Ƃ��ė^����ꂽ�t�@�C����ǂݍ���ŁA�ۑ�̓��e���Ǘ�����
 */
class QuestionManager {
	private OrgFileReader ofr = null;
	private Questionnaire[] qst = null;
	private String line = null;
	
	/**
	 * �ۑ�Ƃ��ė^����ꂽ�t�@�C���̈�s���ۑ�N���X�Ƃ��Đݒ肷��
	 * @param s�@�ۑ�Ƃ��ė^����ꂽ�t�@�C���̈�s
	 * @param i�@�ۑ�N���X�̔z��
	 */
	private void setQuestionnaire(String s, int i) {
		String[] temp = s.split(" ");
		if(temp.length == 3) {
			qst[i] = new Questionnaire();
			// �؎�V�[�g�̏c����
			qst[i].setHeight(Integer.parseInt(temp[0]));
			// �؎�V�[�g�̉�����
			qst[i].setWidth(Integer.parseInt(temp[1]));
			// �؂��肽���؎�̖���
			qst[i].setNum(Integer.parseInt(temp[2]));
		}
	}
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
}

/**
 * �ȈՃt�@�C���������݃N���X
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
 * �ȈՃt�@�C���ǂݍ��݃N���X
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
