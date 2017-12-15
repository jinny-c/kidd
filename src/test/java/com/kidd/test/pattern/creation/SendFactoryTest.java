package com.kidd.test.pattern.creation;

import com.kidd.test.pattern.creation.four.Builder;
import com.kidd.test.pattern.creation.one.SendFactory1;
import com.kidd.test.pattern.creation.one.SendFactory2;
import com.kidd.test.pattern.creation.one.SendFactory3;
import com.kidd.test.pattern.creation.one.Sender;
import com.kidd.test.pattern.creation.two.Provider;
import com.kidd.test.pattern.creation.two.SendMailFactory;

//创建型模式，共五种：(工厂方法模式)、(抽象工厂模式)、(单例模式)、(建造者模式)、原型模式。
public class SendFactoryTest {

	public static void main(String[] args) {
		test1();
		test2();
		test3();
	}

	// 工厂方法模式（Factory Method）
	// 普通工厂模式
	private static void test1() {
		SendFactory1 factory = new SendFactory1();
		Sender sender = factory.produce("sms");
		sender.Send();
	}

	// 多个工厂方法模式
	private static void test1_2() {
		SendFactory2 factory = new SendFactory2();
		Sender sender = factory.produceMail();
		sender.Send();
	}

	// 静态工厂方法模式
	private static void test1_3() {
		Sender sender = SendFactory3.produceMail();
		sender.Send();
	}

	// 抽象工厂模式（Abstract Factory）
	// 工厂方法模式有一个问题就是，类的创建依赖工厂类，也就是说，如果想要拓展程序，必须对工厂类进行修改，这违背了闭包原则，所以，从设计角度考虑，有一定的问题，如何解决？
	// 就用到抽象工厂模式，创建多个工厂类，这样一旦需要增加新的功能，直接增加新的工厂类就可以了，不需要修改之前的代码。
	private static void test2() {
		Provider provider = new SendMailFactory();
		Sender sender = provider.produce();
		sender.Send();
	}

	// 单例模式（Singleton）
	private static void test3() {
	}

	// 建造者模式（Builder）
	// 工厂类模式提供的是创建单个类的模式，而建造者模式则是将各种产品集中起来进行管理，用来创建复合对象，
	// 所谓复合对象就是指某个类具有不同的属性，其实建造者模式就是前面抽象工厂模式和最后的Test结合起来得到的。
	private static void test4() {
		Builder builder = new Builder();
		builder.produceMailSender(10);
	}

	// 原型模式（Prototype）
	// 原型模式虽然是创建型的模式，但是与工程模式没有关系，从名字即可看出，该模式的思想就是将一个对象作为原型，对其进行复制、克隆，产生一个和原对象类似的新对象
	private static void test7() {
	}
}
