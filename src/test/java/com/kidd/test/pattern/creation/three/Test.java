package com.kidd.test.pattern.creation.three;

import java.util.Vector;

//用"影子实例"的办法为单例对象的属性同步更新
//单例模式（Singleton）
public class Test {
	public static void main(String[] args) {
		Test2 t2 = new Test2();
		Test2.stMethod();
		t2.method();
	}
}
