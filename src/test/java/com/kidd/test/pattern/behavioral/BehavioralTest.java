package com.kidd.test.pattern.behavioral;

import com.kidd.test.pattern.behavioral.eighteen.Command;
import com.kidd.test.pattern.behavioral.eighteen.Invoker;
import com.kidd.test.pattern.behavioral.eighteen.MyCommand;
import com.kidd.test.pattern.behavioral.eighteen.Receiver;
import com.kidd.test.pattern.behavioral.fifteen.MySubject;
import com.kidd.test.pattern.behavioral.fifteen.Observer1;
import com.kidd.test.pattern.behavioral.fifteen.Observer2;
import com.kidd.test.pattern.behavioral.fifteen.Subject;
import com.kidd.test.pattern.behavioral.fourteen.AbstractCalculator14;
import com.kidd.test.pattern.behavioral.fourteen.Plus14;
import com.kidd.test.pattern.behavioral.nineteen.Original;
import com.kidd.test.pattern.behavioral.nineteen.Storage;
import com.kidd.test.pattern.behavioral.seventeen.MyHandler;
import com.kidd.test.pattern.behavioral.sixteen.Collection;
import com.kidd.test.pattern.behavioral.sixteen.Iterator;
import com.kidd.test.pattern.behavioral.sixteen.MyCollection;
import com.kidd.test.pattern.behavioral.thirteen.ICalculator;
import com.kidd.test.pattern.behavioral.thirteen.Plus;
import com.kidd.test.pattern.behavioral.tweenThree.Context23;
import com.kidd.test.pattern.behavioral.tweenThree.Minus23;
import com.kidd.test.pattern.behavioral.tweenThree.Plus23;
import com.kidd.test.pattern.behavioral.twenty.Context;
import com.kidd.test.pattern.behavioral.twenty.State;
import com.kidd.test.pattern.behavioral.twentyOne.MySubject21;
import com.kidd.test.pattern.behavioral.twentyOne.MyVisitor;
import com.kidd.test.pattern.behavioral.twentyOne.Subject21;
import com.kidd.test.pattern.behavioral.twentyOne.Visitor;
import com.kidd.test.pattern.behavioral.twentyTwo.Mediator;
import com.kidd.test.pattern.behavioral.twentyTwo.MyMediator;

//行为型模式，共十一种：策略模式、模板方法模式、观察者模式、迭代子模式、责任链模式、命令模式、备忘录模式、状态模式、访问者模式、中介者模式、解释器模式。
public class BehavioralTest {
	// 看看这11中模式的关系：
	// 第一类：通过父类与子类的关系进行实现。【(策略模式)、模板方法模式】
	// 第二类：两个类之间。【(观察者模式)、迭代子模式、责任链模式、命令模式】
	// 第三类：类的状态。【备忘录模式、状态模式】
	// 第四类：通过中间类【访问者模式、中介者模式、解释器模式】
	public static void main(String[] args) {
		// test13();
		// test14();
		// test15();
		// test16();
		// test17();
		// test18();
		// test19();
		// test20();
		// test21();
		test22();
	}

	// 策略模式（strategy）
	// 策略模式定义了一系列算法，并将每个算法封装起来，使他们可以相互替换，且算法的变化不会影响到使用算法的客户。
	// 需要设计一个接口，为一系列实现类提供统一的方法，多个实现类实现该接口，设计一个抽象类（可有可无，属于辅助类），提供辅助函数
	// 策略模式的决定权在用户，系统本身提供不同算法的实现，新增或者删除算法，对各种算法做封装。因此，策略模式多用在算法决策系统中，外部用户只需要决定用哪个算法即可。
	private static void test13() {
		String exp = "2+8";
		ICalculator cal = new Plus();
		int result = cal.calculate(exp);
		System.out.println(result);
	}

	// 模板方法模式（Template Method）
	// 指：一个抽象类中，有一个主方法，再定义1...n个方法，可以是抽象的，也可以是实际的方法，定义一个类，继承该抽象类，重写抽象方法，通过调用抽象类，实现对子类的调用
	private static void test14() {
		String exp = "8+8";
		AbstractCalculator14 cal = new Plus14();
		int result = cal.calculate(exp, "\\+");
		System.out.println(result);
	}

	// 包括这个模式在内的接下来的四个模式，都是类和类之间的关系，不涉及到继承
	// 观察者模式（Observer）
	// 类似于邮件订阅和RSS订阅，当我们浏览一些博客或wiki时，经常会看到RSS图标，就这的意思是，当你订阅了该文章，如果后续有更新，会及时通知你。
	// 其实，简单来讲就一句话：当一个对象变化时，其它依赖该对象的对象都会收到通知，并且随着变化！对象之间是一种一对多的关系
	private static void test15() {
		Subject sub = new MySubject();
		sub.add(new Observer1());
		sub.add(new Observer2());
		sub.operation();
	}

	// 迭代子模式（Iterator）
	// 迭代器模式就是顺序访问聚集中的对象，一般来说，集合中非常常见，如果对集合类比较熟悉的话，理解本模式会十分轻松。
	// 这句话包含两层意思：一是需要遍历的对象，即聚集对象，二是迭代器对象，用于对聚集对象进行遍历访问。
	private static void test16() {
		Collection collection = new MyCollection();
		Iterator it = collection.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}

	// 责任链模式（Chain of Responsibility）
	// 有多个对象，每个对象持有对下一个对象的引用，这样就会形成一条链，请求在这条链上传递，直到某一对象决定处理该请求。
	// 但是发出者并不清楚到底最终那个对象会处理该请求，所以，责任链模式可以实现，在隐瞒客户端的情况下，对系统进行动态的调整。
	private static void test17() {
		MyHandler h1 = new MyHandler("h1");
		MyHandler h2 = new MyHandler("h2");
		MyHandler h3 = new MyHandler("h3");

		h1.setHandler(h2);
		h2.setHandler(h3);

		h1.operator();
	}

	// 命令模式（Command）
	// 命令模式很好理解，举个例子，司令员下令让士兵去干件事情，从整个事情的角度来考虑，司令员的作用是，发出口令，口令经过传递，传到了士兵耳朵里，士兵去执行。
	// 这个过程好在，三者相互解耦，任何一方都不用去依赖其他人，只需要做好自己的事儿就行，司令员要的是结果，不会去关注到底士兵是怎么实现的
	// 命令模式的目的就是达到命令的发出者和执行者之间解耦，实现请求和执行分开
	// 熟悉Struts的同学应该知道，Struts其实就是一种将请求和呈现分离的技术，其中必然涉及命令模式的思想！
	private static void test18() {
		// Invoker是调用者（司令员），Receiver是被调用者（士兵），MyCommand是命令，实现了Command接口，持有接收对象
		Receiver receiver = new Receiver();
		Command cmd = new MyCommand(receiver);
		Invoker invoker = new Invoker(cmd);
		invoker.action();
	}

	// 备忘录模式（Memento）
	// 主要目的是保存一个对象的某个状态，以便在适当的时候恢复对象，个人觉得叫备份模式更形象些，
	// 通俗的讲下：假设有原始类A，A中有各种属性，A可以决定需要备份的属性，备忘录类B是用来存储A的一些内部状态，类C呢，就是一个用来存储备忘录的，且只能存储，不能修改等操作。
	private static void test19() {
		// 创建原始类
		Original origi = new Original("egg");
		// 创建备忘录
		Storage storage = new Storage(origi.createMemento());
		// 修改原始类的状态
		System.out.println("初始化状态为：" + origi.getValue());
		origi.setValue("niu");
		System.out.println("修改后的状态为：" + origi.getValue());
		// 回复原始类的状态
		origi.restoreMemento(storage.getMemento());
		System.out.println("恢复后的状态为：" + origi.getValue());
	}

	// 状态模式（State）
	// 核心思想就是：当对象的状态改变时，同时改变其行为，很好理解！
	// 就拿QQ来说，有几种状态，在线、隐身、忙碌等，每个状态对应不同的操作，而且你的好友也能看到你的状态，所以，状态模式就两点：1、可以通过改变状态来获得不同的行为。2、你的好友能同时看到你的变化。
	// 根据这个特性，状态模式在日常开发中用的挺多的，尤其是做网站的时候，我们有时希望根据对象的某一属性，区别开他们的一些功能，比如说简单的权限控制等。
	private static void test20() {
		// State类是个状态类，Context类可以实现切换
		State state = new State();
		Context context = new Context(state);

		// 设置第一种状态
		state.setValue("state1");
		context.method();

		// 设置第二种状态
		state.setValue("state2");
		context.method();
	}

	// 访问者模式（Visitor）
	// 访问者模式把数据结构和作用于结构上的操作解耦合，使得操作集合可相对自由地演化。访问者模式适用于数据结构相对稳定算法又易变化的系统。
	// 因为访问者模式使得算法操作增加变得容易。若系统数据结构对象易于变化，经常有新的数据对象增加进来，则不适合使用访问者模式。访问者模式的优点是增加操作很容易，因为增加操作意味着增加新的访问者。
	// 访问者模式将有关行为集中到一个访问者对象中，其改变不影响系统数据结构。其缺点就是增加新的数据结构很困难。—— From 百科
	// 简单来说，访问者模式就是一种分离对象数据结构与行为的方法，通过这种分离，可达到为一个被访问者动态添加新的操作而无需做其它的修改的效果。
	private static void test21() {
		// 该模式适用场景：如果我们想为一个现有的类增加新功能，不得不考虑几个事情：1、新功能会不会与现有功能出现兼容性问题？
		// 2、以后会不会再需要添加？3、如果类不允许修改代码怎么办？面对这些问题，最好的解决方法就是使用访问者模式，访问者模式适用于数据结构相对稳定的系统，把数据结构和算法解耦，
		Visitor visitor = new MyVisitor();
		Subject21 sub = new MySubject21();
		sub.accept(visitor);
	}

	// 中介者模式（Mediator）
	// 中介者模式也是用来降低类类之间的耦合的，因为如果类类之间有依赖关系的话，不利于功能的拓展和维护，因为只要修改一个对象，其它关联的对象都得进行修改。
	// 如果使用中介者模式，只需关心和Mediator类的关系，具体类类之间的关系及调度交给Mediator就行，这有点像spring容器的作用。
	private static void test22() {
		Mediator mediator = new MyMediator();
		mediator.createMediator();
		mediator.workAll();
	}

	// 解释器模式（Interpreter）
	// 一般主要应用在OOP开发中的编译器的开发中
	private static void test23() {
		// Context类是一个上下文环境类，Plus和Minus分别是用来计算的实现
		// 计算9+2-8的值
		int result = new Minus23().interpret((new Context23(new Plus23()
				.interpret(new Context23(9, 2)), 8)));
		System.out.println(result);
	}
}
