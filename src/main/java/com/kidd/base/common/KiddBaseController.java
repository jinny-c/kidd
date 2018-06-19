package com.kidd.base.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.kidd.base.common.constant.KiddConstants;
import com.kidd.base.common.enums.KiddErrorCodeEnum;
import com.kidd.base.common.exception.KiddControllerException;
import com.kidd.base.common.serialize.KiddSerialTypeEnum;
import com.kidd.base.common.utils.KiddStringUtils;
import com.kidd.base.http.HttpHeader;
import com.kidd.base.http.RequestResponseContext;
import com.kidd.base.spring.modelview.KiddModelAndView;
import com.kidd.base.spring.modelview.RespErr;
import com.kidd.base.spring.modelview.RespSucc;

/**
 * 基础Controller
 * 
 * @history
 */
public class KiddBaseController {

	/** 日志 */
	private static Logger log = LoggerFactory
			.getLogger(KiddBaseController.class);

    /**
     * 获取HTTP关信息
     *
     * @history
     * @throws ControllerException 没有进行初始化过滤（InitFilter）调用此方法将抛异常
     */
	protected HttpHeader getHttpHeader() throws KiddControllerException{
		Object header = RequestResponseContext.getRequest().getAttribute(HttpHeader.class.getName());
		return (HttpHeader) header;
	}
	
	protected String toWapHtml(String viewName) {
		return KiddStringUtils.join("wap/", viewName);
	}
	
	protected String toViewHtml(String viewName) {
		return KiddStringUtils.join("view/", viewName);
	}
	
	public String convertJson(Object object) {
		return JSON.toJSONString(object);
	}

	protected String toRedirect(String url) {
		return KiddStringUtils.join("redirect:", url);
	}
	
	
	protected <E>KiddModelAndView<RespSucc<E>> toSucc() {
		return KiddModelAndView.succ(getSerialType());
	}
	protected KiddModelAndView<RespErr> toErr(String code, String msg) {
		return KiddModelAndView.err(getSerialType(), code, msg);
	}
	
	protected <E> KiddModelAndView<RespSucc<E>> toSuccData(E data) {
		return KiddModelAndView.succ(getSerialType(),data);
	}

	protected <T> KiddModelAndView<T> toData(T data) {
		return new KiddModelAndView<T>(getSerialType(), data);
	}
	
	protected <E>KiddModelAndView<RespSucc<E>> toRequestForward(String url)
			throws KiddControllerException {
		try {
			log.info("toRequestForward start");
			RequestResponseContext
					.getRequest()
					.getRequestDispatcher(url)
					.forward(RequestResponseContext.getRequest(),
							RequestResponseContext.getResponse());
		} catch (ServletException e) {
			e.printStackTrace();
			throw new KiddControllerException(
					KiddErrorCodeEnum.ERROR_CODE_KW9000.getErrorCode(),
					KiddErrorCodeEnum.ERROR_CODE_KW9000.getErrorMsg());
		} catch (IOException e) {
			e.printStackTrace();
			throw new KiddControllerException(
					KiddErrorCodeEnum.ERROR_CODE_KW9000.getErrorCode(),
					KiddErrorCodeEnum.ERROR_CODE_KW9000.getErrorMsg());
		}
		log.info("toRequestForward success");
		return toSucc();
	}
	
	
	protected HttpServletRequest getRequest() {
		return RequestResponseContext.getRequest();
	}

	protected HttpSession getSession() {
		return RequestResponseContext.getRequest().getSession();
	}

	private KiddSerialTypeEnum getSerialType() {
		HttpHeader httpHeader = null;
		try {
			httpHeader = getHttpHeader();
		} catch (KiddControllerException e) {
			log.error("KiddControllerException:", e);
		}

		if (httpHeader == null || httpHeader.getDataType() == null) {
			return KiddSerialTypeEnum.SERILAL_TYPE_FASTJSON;
		}
		return httpHeader.getDataType();
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
			//TODO
			response.setContentType(KiddConstants.CONTENT_TYPE_TEXT);
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
