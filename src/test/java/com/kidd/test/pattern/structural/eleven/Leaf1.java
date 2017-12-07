package com.kidd.test.pattern.structural.eleven;

public class Leaf1 extends Component {
	public Leaf1(String name) {
		super(name);
	}

	@Override
	public void Add(Component c) {
		System.out.println("Can not add to a leaf");
	}

	@Override
	public void Remove(Component c) {
		System.out.println("Can not remove from a leaf");
	}

	@Override
	public void Display(int depth) {
		String temp = "";
		for (int i = 0; i < depth; i++)
			temp += '-';
		System.out.println(temp + name);
	}
}
