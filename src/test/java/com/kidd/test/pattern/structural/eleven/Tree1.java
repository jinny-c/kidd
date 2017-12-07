package com.kidd.test.pattern.structural.eleven;

import com.kidd.base.common.utils.ToStringUtils;

public class Tree1 {
	public TreeNode1 root = null;

	public Tree1(String name) {
		root = new TreeNode1(name);
	}

	@Override
	public String toString() {
		ToStringUtils builder = new ToStringUtils(this);
		builder.add("root", root);
		return builder.toString();
	}
	
}
