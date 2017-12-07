package com.kidd.test.pattern.structural.eleven;

import java.util.Enumeration;
import java.util.Vector;

import com.kidd.base.common.utils.ToStringUtils;

public class TreeNode1 {
	private String name;
	private TreeNode1 parent;
	private Vector<TreeNode1> children = new Vector<TreeNode1>();

	public TreeNode1(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TreeNode1 getParent() {
		return parent;
	}

	public void setParent(TreeNode1 parent) {
		this.parent = parent;
	}

	// 添加孩子节点
	public void add(TreeNode1 node) {
		children.add(node);
	}

	// 删除孩子节点
	public void remove(TreeNode1 node) {
		children.remove(node);
	}

	// 取得孩子节点
	public Enumeration<TreeNode1> getChildren() {
		return children.elements();
	}

	@Override
	public String toString() {
		ToStringUtils builder = new ToStringUtils(this);
		builder.add("name", name).add("parent", parent)
				.add("children", children);
		return builder.toString();
	}
	
}
