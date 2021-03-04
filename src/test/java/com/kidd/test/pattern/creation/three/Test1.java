package com.kidd.test.pattern.creation.three;

//用"影子实例"的办法为单例对象的属性同步更新
//单例模式（Singleton）
public class Test1 {
	public Test1() {
		System.out.println("A");
	}
	public static void stMethod(){
		System.out.println("B");
	}
	public void method(){
		System.out.println("C");
	}
}
