package com.kidd.test.some;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @description 两个数组合并并排序
 *
 * @auth chaijd
 * @date 2021/8/6
 */
public class SomeOthers {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//someThings();
		addAll();
	}

	private static int[] ar1 = {1,2,3,5,8};
	private static int[] ar2 = {1,3,5,6,7,8,10};
	private static int[] ar3 = new int[12];

	private static void someThings(){
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

	private static void addAll(){
		int[] ar30 = ArrayUtils.addAll(ar1,ar2);
		System.out.println(Arrays.toString(ar30));
		//List<int[]> lt = Arrays.asList(ar33);
		//Collections.addAll(lt, ar33);

		//Arrays.asList() 只能用基本数据类型的包装类型（即引用类型）
		Integer[] ar22 = {1,3,5,6,7,8,10};
		List<Integer> lt = Arrays.asList(ar22);
		Integer[] ar11 = {1,2,3,5,8};
		Integer[] ar33 = ArrayUtils.addAll(ar11,ar22);

		List lt3 = Arrays.asList(ar33);
		//排序（默认字典顺序）
		Collections.sort(lt3);
		//lt3.sort(null);
		System.out.println(lt3);
//		List<Integer> lt33 = new ArrayList();
//		Collections.sort(lt33);
//		System.out.println(lt33);


		//System.out.println(Collections.sort(lt3));
	}
}
