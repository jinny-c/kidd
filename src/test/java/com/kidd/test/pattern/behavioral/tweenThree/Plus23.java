package com.kidd.test.pattern.behavioral.tweenThree;

public class Plus23 implements Expression {
	@Override
	public int interpret(Context23 context) {
		return context.getNum1() + context.getNum2();
	}
}
