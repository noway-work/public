package personcheck;

public class Check {
	/**
	 * ���C������
	 * @param args ���̓p�����[�^�i�g�p�����j
	 */
	public static void main(String args[]) {
		ChildTable table = null;
		int cnt = 0;
		System.out.println("start");
		for(int i = 6; true; i++) {
			// ���ʏ����i������true�ɂȂ�Ȃ��ׂɏ����ΏۊO�Ƃ���j
			if(i % 2 == 0) continue;
			
			// ���C������
			table = new ChildTable(i);
			if(table.check()) {
				// ���ʂ̏o��
				System.out.println("result[" + (cnt + 1) + "]:" + i);
				if(++cnt == 5) break;
			}
		}
		System.out.println("end");
	}
}

/**
 * �q�����Ǘ�����N���X
 * @author ifactory
 */
class ChildTable {
	private int idx = 0;
	private int[] table = null;

	/**
	 * �R���X�g���N�^
	 * idx�ɍŌ���̒j�̎q�̐��l���Z�b�g
	 * table�ɒj�̎q�Ə��̎q��01�ŕ\��������������Z�b�g
	 * ��"0"�͂܂��`���R�����炦���Ƀe�[�u���ɂ���q�A"1"�̓`���R��������ăe�[�u�����痣�ꂽ�q��\��
	 * @param i
	 */
	public ChildTable(int i) {
		idx = i - 1;
		table = new int[i * 2];
		//System.out.println("test1:" + toString(table));
	}
	
	/**
	 * ����̏����ōŌ�̎q���񂩂痣��邩�ǂ����𔻒�
	 * @return �Ō��true�A����ȊO��false
	 */
	private boolean isLastChild() {
		int cnt = 0;
		// "0"�����������false
		for(int i = 0;i < table.length; i++) {
			if(table[i] == 0) {
				if(cnt == 1) return false;
				cnt++;
			}
		}
		return true;
	}
	
	/**
	 * �Ō���̒j�̎q�������Ώہi�Ȃ𗣂��j���ǂ����𔻒�
	 * @param i �����Ώۂ̎q
	 * @return �����Ώۂ��Ō���̒j�̎q�̏ꍇ��true�A����ȊO��false
	 */
	private boolean isLastBoy(int i) {
		return i == idx;
	}
	
	/**
	 * ���̎q���̃C���f�b�N�X��Ԃ�
	 * @param i ���݂̏����Ώۂ̎q��
	 * @return ���̏����Ώۂ̎q��
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
	 * �Ώۂ̎q�����e�[�u�����痣�ꂳ����
	 * ���t���O�Z�b�g�̂�
	 * @param i �����Ώۂ̎q��
	 */
	private void leaveTable(int i) {
		table[i] = 1;
		//System.out.println("test2:" + toString(table));
	}
	
	/**
	 * �f�o�b�O�p���O�o�̓��\�b�h
	 * �q�����Ǘ����Ă���ϐ�table�̓��e���o�͂��鎖��z��
	 * @param args �q���Ǘ��ϐ����n����鎖��z��
	 * @return �q���Ǘ��ϐ��̓��e��String�̌`�ŕԂ�
	 */
	private String toString(int args[]) {
		StringBuilder temp = new StringBuilder();
		for(int i = 0; i < args.length; i++) {
			temp.append(Integer.toString(args[i]));
		}
		return temp.toString();
	}
	
	/**
	 * �Ō���̒j�̎q���Ō�ɐȂ𗣂�邩�ǂ����̃`�F�b�N���������s
	 * @return �Ō���̒j�̎q���Ō�ɐȂ𗣂��ꍇ��true�A����ȊO��false
	 */
	public boolean check() {
		int i = -1;
		while(true) {
			i = getNextChildNumber(i);
			// �Ō�̈�l
			if(isLastChild()) break;
			// �Ō�̈�l��҂����ɍŌ���̒j�̎q���e�[�u���𗣂ꂽ�ꍇ�͏����I��
			if(isLastBoy(i)) return false;
			// �ΏۂƂȂ�q�����e�[�u�����痣�ꂳ����
			leaveTable(i);
		}
		return true;
	}
	
}