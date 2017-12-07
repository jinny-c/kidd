package com.kidd.test.pattern.structural.six;

public class Wrapper implements Targetable {

	private Source6 source;

	public Wrapper(Source6 source) {
		super();
		this.source = source;
	}

	@Override
	public void method1() {
		// TODO Auto-generated method stub
		source.method1();
	}

	@Override
	public void method2() {
		// TODO Auto-generated method stub
		System.out.println("this is the targetable method!");
	}

}
