package com.kidd.test;

import java.security.SecureRandom;

import com.kidd.wap.controller.enums.KiddWapWildcardEnum;

/**
 * @description 获取随机数
 *
 * @auth chaijd
 * @date 2021/12/28
 */
public class TestMethod {

	// 验证码字符个数
    private static final int CODE_COUNT = 4;
    private static final char[] CODE_SEQUENCE = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	private static final char[][] CODE_SEQUENCE_2 = {
			{ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
					'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
					'Z' },
			{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' },
			{ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
					'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
					'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' } };
	
	/**
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		SecureRandom random = new SecureRandom();
		
		for (int i = 0; i < 4; i++) {
			System.out.println("----------------------------------------" + i);
			randAll(random);
			randAll2(random);
			randByParam(random,0);
		}
		
		switch (KiddWapWildcardEnum.convert2Self("123")) {
		case wap_wildcard_imageCode:
			System.out.println("wap_wildcard_imageCode");
			break;
		case wap_wildcard_verifyCode:
			System.out.println("wap_wildcard_verifyCode");
			break;
		default:
			System.out.println("default");
			break;
		}
		
	}

	private void randNext(SecureRandom random){
		for(int i=0;i<2;i++){
			System.out.println("1===="+ (random.nextInt(9999 - 1000 + 1) + 1000));
			System.out.println("2===="+random.nextInt(10000));
		}
	}
	
	private static void randAll(SecureRandom random){
		StringBuilder randomCode = new StringBuilder();
		String strRand = null;
		for (int i = 0; i < CODE_COUNT; i++) {
			strRand = String.valueOf(CODE_SEQUENCE[random.nextInt(CODE_SEQUENCE.length)]);
			randomCode.append(strRand);
		}
		System.out.println("randAll,randomCode===="+randomCode.toString());
	}
	
	private static void randAll2(SecureRandom random){
		StringBuilder randomCode = new StringBuilder();
		String strRand = null;
		int dl = 0;
		for (int i = 0; i < CODE_COUNT; i++) {
			dl = random.nextInt(2);
			System.out.print("[dl====]"+dl);
			strRand = String.valueOf(CODE_SEQUENCE_2[dl][random.nextInt(CODE_SEQUENCE_2[dl].length)]);
			randomCode.append(strRand);
		}
		System.out.println();
		System.out.println("randAll2,randomCode===="+randomCode.toString());
	}
	
	private static void randByParam(SecureRandom random, int dl){
		StringBuilder randomCode = new StringBuilder();
		String strRand = null;
		
		for (int i = 0; i < CODE_COUNT; i++) {
			strRand = String.valueOf(CODE_SEQUENCE_2[dl][random.nextInt(CODE_SEQUENCE_2[dl].length)]);
			randomCode.append(strRand);
		}
		System.out.println("randByParam,randomCode===="+randomCode.toString());
	}
}
