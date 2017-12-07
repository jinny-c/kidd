package com.kidd.test.pattern.structural.eleven;

import java.util.Enumeration;
import java.util.Vector;

import com.kidd.base.common.utils.ToStringUtils;

public abstract class TreeTest {
	private String name;
	private TreeTest parent;
	private Vector<TreeTest> children = new Vector<TreeTest>();

	public TreeTest(String name) {
		this.name = name;
	}

	public TreeTest(String name, TreeTest parent) {
		this.name = name;
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public TreeTest getParent() {
		return parent;
	}

	// 添加孩子节点
	public void add(TreeTest node) {
		children.add(node);
	}

	// 删除孩子节点
	public void remove(TreeTest node) {
		children.remove(node);
	}

	// 取得孩子节点
	public Enumeration<TreeTest> getChildren() {
		return children.elements();
	}

	@Override
	public String toString() {
		ToStringUtils builder = new ToStringUtils(this);
		builder.add("name", name).add("children", children);
		return builder.toString();
	}
	
}
