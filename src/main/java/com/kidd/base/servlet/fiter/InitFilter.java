package com.kidd.base.servlet.fiter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kidd.base.common.serialize.KiddSerialTypeEnum;
import com.kidd.base.common.utils.KiddTraceLogUtil;
import com.kidd.base.http.HttpHeader;
import com.kidd.base.http.RequestResponseContext;
import com.kidd.base.servlet.fiter.wrapper.KiddServletRequestWrapper;
import com.kidd.base.servlet.traffic.KiddTrafficCounter;

/**
 * 初始化过滤器
 * 
 * 
 */
public class InitFilter extends OncePerRequestFilter {
	private static final Logger logger = LoggerFactory.getLogger(InitFilter.class);

	private KiddTrafficCounter trafficCounter;
	
	@Override
	protected void initFilterBean() throws ServletException {
		super.initFilterBean();
		String maxToken = super.getFilterConfig().getInitParameter("traffic.maxToken");
		trafficCounter = new KiddTrafficCounter(maxToken);
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		KiddTraceLogUtil.beginTrace(); // 日志跟踪开始
		long begin = System.currentTimeMillis();//毫秒 /1000s
		logger.info("----do filter start--{},currentCount=[{}],maxTokenCount=[{}]",
				request.getRequestURI(), trafficCounter.get() + 1, trafficCounter.getMaxToken());
		try {
			if (!trafficCounter.acquire()) {
				logger.info("流量超限，当前会话请求[{}]被阻断", request.getRequestURI());
				
				String respMsg = "流量超限！";
				writeToResponse(response, respMsg);
				return;
			}
			initHeader(request);
			
			if (request.getMethod().equals(HttpMethod.POST.name())) {
				// req数据过滤
				request = new KiddServletRequestWrapper(request);
			}
			//response.setHeader("Access-Control-Allow-Origin", "*");
			RequestResponseContext.setRequest(request);
			RequestResponseContext.setResponse(response);
			chain.doFilter(request, response);
		} finally {
			trafficCounter.release();
		}
		
		logger.info("----do filter end--{},execute time:{}(ms)",
				request.getRequestURI(), System.currentTimeMillis() - begin);
		KiddTraceLogUtil.endTrace(); // 日志跟踪结束
	}

	/**
	 * 返回错误信息
	 *
	 */
	private void writeToResponse(HttpServletResponse response, String msg)
			throws IOException {
		byte[] data = msg.getBytes("UTF-8");
		//TODO
		response.setContentType("text/plain; charset=UTF-8");
		ServletOutputStream outputStream = response.getOutputStream();
		try {
			outputStream.write(data);
			outputStream.flush();
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}
	
	private void initHeader(HttpServletRequest request) {
		HttpHeader header = new HttpHeader();
		header.setSessionId("sessionId");
		header.setUniqueIdentifier("uniqueIdentifier");
		header.setSignature("signature");
		header.setDataType(KiddSerialTypeEnum.convert2Self("serilalType"));
		
		//TODO
		//header.setUserAgent(request.getHeader(KiddConstants.FIELD_USER_AGENT));
		//header.setXmlRequestedWith(request.getHeader(KiddConstants.X_REQUESTED_WITH));
		
		header.setVersion("version");
		header.setImei("imei");
		header.setMachineModel("machineModel");
		header.setPlantform("plantform");
		
		logger.info("initHeader end,{}", header);
		request.setAttribute(HttpHeader.class.getName(), header);
	}
}
