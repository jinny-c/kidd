package com.kidd.test.pattern.behavioral.twentyOne;

public class MySubject21 implements Subject21 {

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String getSubject() {
		return "love";
	}

}
