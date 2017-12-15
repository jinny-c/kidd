package com.kidd.test.pattern.structural.twelve;

import com.kidd.base.common.utils.KiddStringUtils;

public class ConcreteFlyWeight implements FlyWeight {

	private String name;

	public ConcreteFlyWeight(String name) {
		this.name = name;
	}

	@Override
	public void action(String externalState) {
		System.out.println(KiddStringUtils.join("name=", this.name,
				",outerState=", externalState));
	}
}
