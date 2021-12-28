package com.kidd.test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
/**
 * @description 反射
 *
 * @auth chaijd
 * @date 2021/12/28
 */
public class HelloPeoxy implements InvocationHandler {

	// 目标对象，也就是我们主要的业务，主要目的要做什么事
	private Object targetObj;

	/**
	 * 和你额外需要做得事情，进行绑定，返回一个全新的对象（写法，基本上固定的）
	 * 
	 * @param targetObj
	 * @return
	 */
	public Object bind(Object targetObj) {
		this.targetObj = targetObj;
		return Proxy.newProxyInstance(this.targetObj.getClass()
				.getClassLoader(), this.targetObj.getClass().getInterfaces(),
				this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		// TODO Auto-generated method stub
		Object obj = null;
		// 执行前置的方法
		System.out.println("do some thing before");
		// 通过反射，执行目标方法，也就是你的主要目的
		obj = method.invoke(this.targetObj, args);
		// 执行后置的方法
		System.out.println("do some thing after");
		// 返回值给调用者
		return obj;
	}

	public static void main(String[] args) {
		// 不启用代理
		// HelloWordService service = new HelloWordService();
		
		// 使用代理
		HelloWordService service = (HelloWordService) new HelloPeoxy()
				.bind(new HelloWordServiceImpl());

		service.sayHello();
	}
}
