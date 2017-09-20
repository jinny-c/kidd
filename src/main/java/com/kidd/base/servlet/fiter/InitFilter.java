package com.kidd.base.servlet.fiter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kidd.base.http.RequestResponseContext;
import com.kidd.base.traffic.KiddTrafficCounter;
import com.kidd.base.utils.KiddTraceLogUtil;

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
	
}
