package com.kidd.test.pattern.behavioral.twentyOne;

public class MyVisitor implements Visitor {
	@Override
	public void visit(Subject21 sub) {
		System.out.println("visit the subject：" + sub.getSubject());
	}
}
