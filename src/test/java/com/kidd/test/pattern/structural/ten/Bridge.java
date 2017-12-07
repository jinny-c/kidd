package com.kidd.test.pattern.structural.ten;

import com.kidd.test.pattern.structural.seven.Sourceable;

public abstract class Bridge {
	private Sourceable source;

	public void method() {
		source.method();
	}

	public Sourceable getSource() {
		return source;
	}

	public void setSource(Sourceable source) {
		this.source = source;
	}
}
