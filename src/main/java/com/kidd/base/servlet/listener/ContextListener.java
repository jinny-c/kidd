package com.kidd.base.servlet.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 项目启动时加载
 *
 */
public class ContextListener implements ServletContextListener {

	private static String LOCAL_URL = "http://127.0.0.1:8082/kidd/";

	// project start-up
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		sce.getServletContext().log("start-up:" + LOCAL_URL);
		sce.getServletContext().log("添加应用上下文：" + sce.getServletContext().getContextPath());
		sce.getServletContext().setAttribute("ctx", sce.getServletContext().getContextPath());
		
	}

	// project stop
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		sce.getServletContext().log("stop");
	}

}
