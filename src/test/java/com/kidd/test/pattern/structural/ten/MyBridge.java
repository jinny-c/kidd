package com.kidd.test.pattern.structural.ten;

public class MyBridge extends Bridge {
	public void method() {
		getSource().method();
	}
}
