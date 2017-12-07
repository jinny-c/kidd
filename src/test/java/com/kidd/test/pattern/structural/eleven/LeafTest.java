package com.kidd.test.pattern.structural.eleven;

import java.util.Enumeration;

public class LeafTest extends TreeTest {
	public LeafTest(String name) {
		super(name);
	}

	public LeafTest(String name, TreeTest parent) {
		super(name, parent);
	}
	@Override
	public void add(TreeTest node) {
		// TODO Auto-generated method stub
		System.out.println("Do not execute this method");
	}
	@Override
	public void remove(TreeTest node) {
		// TODO Auto-generated method stub
		System.out.println("Do not execute this method");
	}
	@Override
	public Enumeration<TreeTest> getChildren() {
		// TODO Auto-generated method stub
		System.out.println("Do not execute this method");
		return null;
	}
}
