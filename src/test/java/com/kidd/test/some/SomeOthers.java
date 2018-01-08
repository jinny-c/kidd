package com.kidd.test.some;

import java.util.Arrays;

public class SomeOthers {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		someThings();
	}
	private static void someThings(){
		int[] ar1 = {1,2,3,5,8};
		int[] ar2 = {1,3,5,6,7,8,10};
		int[] ar3 = new int[12];
		
		int len1 = ar1.length-1;
		int len2 = ar2.length-1;
		int len3 = ar3.length-1;
		
		while (len1 >= 0 && len2 >= 0) {
			if (ar1[len1] > ar2[len2]) {
				ar3[len3] = ar1[len1];
				len1--;
			}else{
				ar3[len3] = ar2[len2];
				len2--;
			}
			len3--;
		}
		
		System.out.println(Arrays.toString(ar3));
	}
}
