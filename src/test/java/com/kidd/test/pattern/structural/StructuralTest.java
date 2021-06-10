package com.kidd.test.pattern.structural;

import java.util.Enumeration;

import com.kidd.test.pattern.structural.eight.Proxy;
import com.kidd.test.pattern.structural.eleven.Composite;
import com.kidd.test.pattern.structural.eleven.Employee;
import com.kidd.test.pattern.structural.eleven.Leaf1;
import com.kidd.test.pattern.structural.eleven.LeafTest;
import com.kidd.test.pattern.structural.eleven.Tree1;
import com.kidd.test.pattern.structural.eleven.TreeLinkedList;
import com.kidd.test.pattern.structural.eleven.TreeNode1;
import com.kidd.test.pattern.structural.eleven.TreeNodeTest;
import com.kidd.test.pattern.structural.eleven.TreeTest;
import com.kidd.test.pattern.structural.nine.Computer;
import com.kidd.test.pattern.structural.seven.Decorator;
import com.kidd.test.pattern.structural.seven.Source;
import com.kidd.test.pattern.structural.seven.Sourceable;
import com.kidd.test.pattern.structural.six.*;
import com.kidd.test.pattern.structural.ten.Bridge;
import com.kidd.test.pattern.structural.ten.MyBridge;
import com.kidd.test.pattern.structural.ten.SourceSub1;
import com.kidd.test.pattern.structural.ten.SourceSub2;

//结构型模式，共七种：(适配器模式)、装饰器模式、(代理模式)、外观模式、桥接模式、组合模式、享元模式。
public class StructuralTest {
	public static void main(String[] args) {
		test6_3();
		// test8();
		// test9();
		// test10();
		// test11_2();
		// test11_0();
	}

	// 适配器模式(Adapder)。主要分为三类：类的适配器模式、对象的适配器模式、接口的适配器模式

	// 类的适配器模式：当希望将一个类转换成满足另一个新接口的类时，可以使用类的适配器模式，创建一个新类，继承原有的类，实现新的接口即可。
	private static void test6_1() {
		Targetable target = new Adapter();
		target.method1();
		target.method2();
	}

	// 对象的适配器模式：当希望将一个对象转换成满足另一个新接口的对象时，可以创建一个Wrapper类，持有原类的一个实例，在Wrapper类的方法中，调用实例的方法就行。
	private static void test6_2() {
		Source6 source = new Source6();
		Targetable target = new Wrapper(source);
		target.method1();
		target.method2();
	}

	// 接口的适配器模式：当不希望实现一个接口中所有的方法时，可以创建一个抽象类Wrapper，实现所有方法，我们写别的类的时候，继承抽象类即可。
	private static void test6_3() {
		Sourceable6 source1 = new SourceSub61();
		Sourceable6 source2 = new SourceSub62();

		source1.method1();
		source1.method2();
		source2.method1();
		source2.method2();
		//abstract类 不能new 但是可以用匿名内部类来创建
		Wrapper2 w2 = new Wrapper2() {
			//重写
			@Override
			public void method2() {
				System.out.println("StructuralTest.method2");
			}
		};
	}

	// 装饰模式（Decorator）
	// 装饰模式就是给一个对象增加一些新的功能，而且是动态的，要求装饰对象和被装饰对象实现同一个接口，装饰对象持有被装饰对象的实例
	/**
	 * 装饰器模式的应用场景： 1、需要扩展一个类的功能。
	 * 2、动态的为一个对象增加功能，而且还能动态撤销。（继承不能做到这一点，继承的功能是静态的，不能动态增删。） 缺点：产生过多相似的对象，不易排错！
	 */
	private static void test7() {
		Sourceable source = new Source();
		Sourceable obj = new Decorator(source);
		obj.method();
	}

	// 代理模式（Proxy）
	// 其实每个模式名称就表明了该模式的作用，代理模式就是多一个代理类出来，替原对象进行一些操作
	/**
	 * 代理模式的应用场景： 如果已有的方法在使用的时候需要对原有的方法进行改进，此时有两种办法：
	 * 1、修改原有的方法来适应。这样违反了“对扩展开放，对修改关闭”的原则。
	 * 2、就是采用一个代理类调用原有的方法，且对产生的结果进行控制。这种方法就是代理模式。 使用代理模式，可以将功能划分的更加清晰，有助于后期维护！
	 */
	private static void test8() {
		Sourceable source = new Proxy();
		source.method();
	}

	// 外观模式（Facade）
	// 外观模式是为了解决类与类之间的依赖关系的，像spring一样，可以将类和类之间的关系配置到配置文件中，而外观模式就是将他们的关系放在一个Facade类中，降低了类类之间的耦合度，该模式中没有涉及到接口
	/**
	 * 如果我们没有Computer类，那么，CPU、Memory、Disk他们之间将会相互持有实例，产生关系，这样会造成严重的依赖，修改一个类，
	 * 可能会带来其他类的修改
	 * ，这不是我们想要看到的，有了Computer类，他们之间的关系被放在了Computer类里，这样就起到了解耦的作用，这，就是外观模式！
	 */
	private static void test9() {
		Computer computer = new Computer();
		computer.startup();
		computer.shutdown();
	}

	// 桥接模式（Bridge）
	/**
	 * 桥接模式就是把事物和其具体实现分开，使他们可以各自独立的变化。桥接的用意是：将抽象化与实现化解耦，使得二者可以独立变化，
	 * 像我们常用的JDBC桥DriverManager一样
	 * ，JDBC进行连接数据库的时候，在各个数据库之间进行切换，基本不需要动太多的代码，甚至丝毫不用动
	 * ，原因就是JDBC提供统一接口，每个数据库提供各自的实现，用一个叫做数据库驱动的程序来桥接就行了
	 */
	private static void test10() {
		Bridge bridge = new MyBridge();

		/* 调用第一个对象 */
		Sourceable source1 = new SourceSub1();
		bridge.setSource(source1);
		bridge.method();

		/* 调用第二个对象 */
		Sourceable source2 = new SourceSub2();
		bridge.setSource(source2);
		bridge.method();
	}

	// 组合模式（Composite）
	// 组合模式有时又叫部分-整体模式在处理类似树形结构的问题时比较方便
	// 将多个对象组合在一起进行操作，常用于表示树形结构中，例如二叉树，数等
	private static void test11_0() {
		TreeTest tree = new TreeNodeTest("tree");
		// 第一层
		TreeTest node1a = new TreeNodeTest("1node", tree);
		tree.add(node1a);
		TreeTest leaf1 = new LeafTest("1leaf", tree);
		tree.add(leaf1);
		// 第二层
		TreeTest node2a = new TreeNodeTest("2node", node1a);
		node1a.add(node2a);
		TreeTest leaf2 = new LeafTest("2leaf", node1a);
		node1a.add(leaf2);

		node2a.add(new LeafTest("3leaf", node2a));

		System.out.println("build the tree finished!" + tree);
		Enumeration<TreeTest> children = tree.getChildren();
		getAll(children, 1);
	}

	private static void getAll(Enumeration<TreeTest> children, int i) {
		while (children.hasMoreElements()) {
			TreeTest frist1 = (TreeTest) children.nextElement();// 调用nextElement方法获得元素
			System.out.println(i + "--" + frist1.getName());
			if (null != frist1.getChildren()) {
				getAll(frist1.getChildren(), i + 1);
			}

		}
	}

	private static void test11_1() {
		Tree1 tree = new Tree1("A");
		TreeNode1 nodeB = new TreeNode1("B");
		nodeB.setParent(new TreeNode1("A"));

		TreeNode1 nodeC = new TreeNode1("C");

		nodeB.add(nodeC);

		tree.root.add(nodeB);
		System.out.println("build the tree finished!" + tree);
	}

	// 组合模式 (Component) 将对象组合成树形结构以表示“部分-整体”的层次结构。
	// 组合模式使得用户对单个对象和组合对象的使用具有唯一性。
	// 组合模式是一种结构型模式。
	private static void test11_2() {
		Composite root = new Composite("root");
		root.Add(new Leaf1("Leaf A"));
		root.Add(new Leaf1("Leaf B"));

		Composite compX = new Composite("Composite X");
		compX.Add(new Leaf1("Leaf XA"));
		compX.Add(new Leaf1("Leaf XB"));
		root.Add(compX);

		Composite compXY = new Composite("Composite XY");
		compXY.Add(new Leaf1("Leaf XYA"));
		compXY.Add(new Leaf1("Leaf XYB"));
		compX.Add(compXY);

		root.Display(1);
		root.Display(0);
	}

	private static void test11_3() {
		Employee CEO = new Employee("John", "CEO");

		Employee headSales = new Employee("Rob", "Sales");

		Employee headMarketing = new Employee("Mike", "Marketing");

		Employee programmer1 = new Employee("Lili", "Programmer");
		Employee programmer2 = new Employee("Bob", "Programmer");

		Employee tester1 = new Employee("Jack", "Tester");
		Employee tester2 = new Employee("Tom", "Tester");

		CEO.add(headSales);
		CEO.add(headMarketing);

		headSales.add(tester1);
		headSales.add(tester2);
		headMarketing.add(programmer1);
		headMarketing.add(programmer2);
		// print all employees of the organization
		System.out.println(CEO);
		for (Employee headEmployee : CEO.getSubordinates()) {
			System.out.println(headEmployee);
			for (Employee employee : headEmployee.getSubordinates()) {
				System.out.println(employee);
			}
		}
	}

	private static void test11_4() {
		TreeLinkedList a = new TreeLinkedList();
		TreeLinkedList b = new TreeLinkedList();
		TreeLinkedList c = new TreeLinkedList();
		TreeLinkedList d = new TreeLinkedList();
		TreeLinkedList e = new TreeLinkedList();
		TreeLinkedList f = new TreeLinkedList();
		TreeLinkedList g = new TreeLinkedList();

		a = new TreeLinkedList(null, 0, d, null);
		b = new TreeLinkedList(a, 1, d, c.getFirstChild());
		c = new TreeLinkedList(a, 2, null, null);
		d = new TreeLinkedList(b, 3, f, e.getFirstChild());
		e = new TreeLinkedList(b, 4, null, null);
		f = new TreeLinkedList(d, 5, null, g.getFirstChild());
		g = new TreeLinkedList(d, 6, null, null);

		System.out.println(a.getDepth());
		System.out.println(b.getDepth());
		System.out.println(c.getDepth());
		System.out.println(d.getDepth());
		System.out.println(e.getDepth());
		System.out.println(f.getDepth());

		System.out.println(a.getHeight());
		System.out.println(b.getHeight());
		System.out.println(c.getHeight());
		System.out.println(d.getHeight());
		System.out.println(e.getHeight());
		System.out.println(f.getHeight());
		System.out.println(g.getHeight());
	}

	// 享元模式（Flyweight）
	// 享元模式的主要目的是实现对象的共享，即共享池，当系统中对象多的时候可以减少内存的开销，通常与工厂模式一起使用。
	// 通过连接池的管理，实现了数据库连接的共享，不需要每一次都重新创建连接，节省了数据库重新创建的开销，提升了系统的性能！
	private static void test12() {
	}
}
