package com.kidd.test.pattern.creation.three;

//用"影子实例"的办法为单例对象的属性同步更新
//单例模式（Singleton）
public class Test2 extends Test1{
	public Test2() {
		System.out.println("X");
	}
	public static void stMethod(){
		System.out.println("Y");
	}
	public void method(){
		System.out.println("Z");
	}
}
