package com.kidd.test.base;

import com.kidd.test.bean.TestBean;

public class MethodPassTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		methodPass3();
		TestBean tb = null;
		methodPass1(tb);
		System.out.println(tb);
		
		tb = new TestBean();
		tb.setIntId(110);
		methodPass1(tb);
		System.out.println(tb);
		
		methodPass2(tb);
		System.out.println(tb);
	}

	private static void methodPass1(TestBean tb){
		System.out.println(tb);
		if(null==tb){
			tb = new TestBean();
			tb.setStId("null methodPass1");
		}else{
			tb = new TestBean();
			tb.setStId("not null methodPass1");
		}
		System.out.println(tb);
	}
	private static void methodPass2(TestBean tb){
		System.out.println(tb);
		tb = null;
		System.out.println(tb);
	}
	private static void methodPass3(){
		//Ncpu
		System.out.println(Runtime.getRuntime().availableProcessors());
	}
}
