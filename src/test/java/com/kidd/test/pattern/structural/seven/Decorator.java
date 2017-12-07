package com.kidd.test.pattern.structural.seven;

public class Decorator implements Sourceable {
	private Sourceable source;

	public Decorator(Sourceable source) {
		super();
		this.source = source;
	}

	@Override
	public void method() {
		// TODO Auto-generated method stub
		System.out.println("before decorator!");
		source.method();
		System.out.println("after decorator!");

	}

}
