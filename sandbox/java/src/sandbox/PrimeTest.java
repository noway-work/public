package sandbox;

public class PrimeTest {
	
	/**
	 * �f���`�F�b�N
	 * �^����ꂽ���lchk���f�����ǂ�����Ԃ�
	 * @param chk�f���`�F�b�N�̑ΏۂƂȂ鐔�l
	 * @return chk���f���̏ꍇtrue�A����ȊO�̏ꍇfalse
	 */
	private static boolean isPrime(int chk) {
		for(int i = 2; i <= (chk / 2); i++) {
			if(chk % i == 0) return false;
		}
		return true;
	}
	
	/**
	 * �^����ꂽ���lchk��菬�������l�̒��őf������������̂����`�F�b�N���ĕԂ�
	 * @param chk �f���`�F�b�N�̑ΏۂƂȂ鐔�l
	 * @return chk��菬�������l�̑f���̐�
	 */
	private static int count(int chk) {
		int cnt = 0;
		for(int i = chk - 1; i > 1; i--) {
			if(isPrime(i)) cnt++;
		}
		return cnt;
	}
	
	/**
	 * ���C������
	 * @param args �f���`�F�b�N�̑ΏۂƂȂ鐔��
	 */
	public static void main(String args[]) {
		for(int i = 0; i < args.length; i++) {
			System.out.println(args[i] + " : " + count(Integer.parseInt(args[i])));
		}
	}
}
