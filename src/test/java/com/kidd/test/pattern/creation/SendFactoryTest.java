package com.kidd.test.pattern.creation;

import com.kidd.test.pattern.creation.four.Builder;
import com.kidd.test.pattern.creation.one.SendFactory1;
import com.kidd.test.pattern.creation.one.SendFactory2;
import com.kidd.test.pattern.creation.one.SendFactory3;
import com.kidd.test.pattern.creation.one.Sender;
import com.kidd.test.pattern.creation.two.Provider;
import com.kidd.test.pattern.creation.two.SendMailFactory;

//创建型模式，共五种：工厂方法模式、抽象工厂模式、单例模式、建造者模式、原型模式。
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
	private static void test2() {
		SendFactory2 factory = new SendFactory2();
		Sender sender = factory.produceMail();
		sender.Send();
	}

	// 静态工厂方法模式
	private static void test3() {
		Sender sender = SendFactory3.produceMail();
		sender.Send();
	}

	// 抽象工厂模式（Abstract Factory）
	private static void test4() {
		Provider provider = new SendMailFactory();
		Sender sender = provider.produce();
		sender.Send();
	}

	// 单例模式（Singleton）
	private static void test5() {
	}

	// 建造者模式（Builder）
	private static void test6() {
		Builder builder = new Builder();
		builder.produceMailSender(10);
	}

	// 原型模式（Prototype）
	private static void test7() {
	}
}
