package interestingnumber;

import java.math.BigDecimal;
import java.util.ArrayList;

public class InterestingNumberChecker {

	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		// 1���̐��l��2�悵�ĉ��������̐��l�Ɠ����l���擾
		for(int i = 1; i < 10; i++) {
			if(isTarget(i)) list.add(String.valueOf(i));
		}
		
		// �������[�v�i1������9���܂Łj
		for(int i = 1; i < 9; i++) {
			list = compute(list, i);
		}
		
		// ���ʏo�͂̃��[�v
		for(int i = 0; i < list.size(); i++) {
			if(isOutput(list.get(i))) {
				System.out.println(list.get(i));
			}
		}
	}
	
	/**
	 * �o�͑Ώۂ��ۂ��̔���
	 * @param val
	 * @return
	 */
	private static boolean isOutput(String val) {
		int temp1 = Integer.parseInt(val);
		return String.valueOf(temp1).length() == 9;
	}
	
	/**
	 * �P���Ƀ��[�v�Ō��ʂ����߂郍�W�b�N�i����l���m�F����p�j
	 * ���ʂ͈ȉ���2�̐��l
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
		
		// ��⃊�X�g�̃��[�v
		for(int i = 0; i < list.size(); i++) {
			flag = false;
			// ��⃊�X�g�̒l�̓���1����9�܂ł̕�����t���đΏ̂ƂȂ邩���m�F���郋�[�v
			for(int j = 1; j < 10; j++) {
				temp = (int) ((j * Math.pow(10, keta)) + new Integer(list.get(i).toString()).intValue());
				if(isTarget(temp)) {
					flag = true;
					result.add(String.valueOf(temp));
				}
			}
			// �ΏۂƂȂ��₪�����ꍇ�ɂ͓���0�����Ď��̃��[�v�Ƀ`�������W
			if(!flag) {
				result.add("0" + String.valueOf(list.get(i)));
			}
		}
		
		return result;
	}
	
	/**
	 * �n���ꂽ���l��2�悵�A�n���ꂽ�l��2�悵�����ʂ̉������������l���ǂ�����Ԃ�
	 * @param i
	 * @return
	 */
	private static boolean isTarget(int i) {
		BigDecimal ZERO = new BigDecimal("0");
		BigDecimal ONE = new BigDecimal("1");
		
		BigDecimal src = null;
		BigDecimal num1 = null;
		
		src = new BigDecimal(i);
		num1 = src.multiply(src);	// 2��
		
		int len = src.toString().length();
		return num1.subtract(src).remainder(ONE.movePointRight(len)).compareTo(ZERO) == 0;
	}
}
