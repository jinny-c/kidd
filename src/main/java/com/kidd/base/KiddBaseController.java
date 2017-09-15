package com.kidd.base;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.kidd.base.httpclient.RequestResponseContext;
import com.kidd.base.utils.KiddStringUtils;

/**
 * 基础Controller
 * 
 * @history
 */
public class KiddBaseController {

	public static String CONTENT_TYPE_TEXT = "text/plain; charset=UTF-8";

	/** 日志 */
	private static Logger log = LoggerFactory
			.getLogger(KiddBaseController.class);


	
	protected String toHtml(String viewName) {
		return KiddStringUtils.join("wap/", viewName);
	}
	
	protected String toUserHtml(String viewName) {
		return KiddStringUtils.join("user/", viewName);
	}
	
	public String convertJson(Object object) {
		return JSON.toJSONString(object);
	}

	protected String toRedirect(String url) {
		return KiddStringUtils.join("redirect:", url);
	}
	
	protected HttpServletRequest getRequest() {
		return RequestResponseContext.getRequest();
	}

	protected HttpSession getSession() {
		return RequestResponseContext.getRequest().getSession();
	}

	/**
	 * 输出响应信息
	 * 
	 * @param responseMsg
	 * @param args
	 */
	protected void writeToResponse(String responseMsg, String... args) {
		HttpServletResponse response = RequestResponseContext.getResponse();
		if (args != null && args.length > 0) {
			response.setContentType(args[0]);
		} else {
			response.setContentType(CONTENT_TYPE_TEXT);
		}
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(responseMsg);
			out.flush();
		} catch (IOException e) {
			log.error("HTTP响应异常", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
