package com.kidd.base.common.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;

public class ServiceProvider implements ApplicationContextAware{
	private static ApplicationContext ctx;

    @Override
	public void setApplicationContext(ApplicationContext ctx) {
    	System.out.println("ServiceProvider load start");
	    ServiceProvider.ctx = ctx;
		((AbstractApplicationContext) ctx).registerShutdownHook();
	}

	/**
	 * 查找CTX服务
	 *
	 * @param name beanID
	 * @param <T>
	 * @return 未找到返回null
	 */
    @SuppressWarnings("unchecked")
	public static <T> T getService(String name) {
	    //应用未初始化完成，返回null
	    if (ctx == null) {
		    return null;
	    }
 		return (T)ctx.getBean(name);
	}

}
