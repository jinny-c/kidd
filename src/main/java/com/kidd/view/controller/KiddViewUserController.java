package com.kidd.view.controller;

import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kidd.base.common.KiddBaseController;
import com.kidd.base.common.exception.KiddControllerException;
import com.kidd.base.common.exception.KiddException;
import com.kidd.base.common.utils.KiddTraceLogUtil;
import com.kidd.base.factory.annotation.KiddSecureAnno;
import com.kidd.base.factory.asnyc.IAsyncTaskExecutor;
import com.kidd.base.factory.asnyc.SimpleAsyncTaskExecutor;
import com.kidd.base.factory.cache.KiddCacheManager;
import com.kidd.wap.controller.dto.GetValidateCodeReq;
import com.kidd.wap.controller.dto.GetValidateCodeResp;
import com.kidd.wap.controller.dto.UserLoginReq;


/**
 *
 * @history
 */
@Controller
@RequestMapping("/view/user")
public class KiddViewUserController extends KiddBaseController{
	/* Slf4j */
	private static Logger log = LoggerFactory.getLogger(KiddViewUserController.class);
	
	// 验证码字符个数
    private static final int CODE_COUNT = 4;
    private static final char[] CODE_SEQUENCE = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    
	@Autowired
	private KiddCacheManager cacheManager;
	
	/** 异步线程 **/
	//private IAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
	@Autowired
	private IAsyncTaskExecutor asyncTaskExecutor;


	@RequestMapping(value = "/index", method = {RequestMethod.GET, RequestMethod.POST})
	public String index() throws KiddControllerException{
		log.info("index enter");
		
		return toViewHtml("userInfo");
	}
	@RequestMapping(value = "/toInfo.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String toInfo(Model model) throws KiddControllerException{
		log.info("toInfo enter");
		
		asyncSendVerifiCode("info code");
		
		model.addAttribute("message", "hello word!");
		return toViewHtml("info");
	}
	@RequestMapping(value = "/toLogin", method = {RequestMethod.GET, RequestMethod.POST})
	public String toLogin() throws KiddControllerException{
		log.info("toLogin enter");
		
		return toWapHtml("login");
	}
	@RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
	public String login(@KiddSecureAnno UserLoginReq req) throws KiddControllerException{
		log.info("login enter,req={}", req);
		if(req.isLoginBy00()){
			req.paramsValid();
		}
		
		return toWapHtml("userInfo");
	}

	@RequestMapping(value = "/getVerificationCode_{flag}", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Object getVerifiCode(@KiddSecureAnno GetValidateCodeReq req,@PathVariable String flag){
		try {
			log.info("getVerifiCode,req={},flag={}", req, flag);
			if (!"wap".equals(flag)) {
				//return toErr("error", "message");
			}
			SecureRandom random = new SecureRandom();
			StringBuilder randomCode = new StringBuilder();
			for (int i = 0; i < CODE_COUNT; i++) {
				String strRand = String.valueOf(CODE_SEQUENCE[random
						.nextInt(CODE_SEQUENCE.length)]);
				randomCode.append(strRand);
			}
			
			GetValidateCodeResp resp = new GetValidateCodeResp();
			resp.setChannel(req.getChannel());
			resp.setImageCode(randomCode.toString());
			
			//String wap = cacheManager.getCacheConfig("0_wap");
			//log.info("getCacheConfig,val={},", wap);
			
			cacheManager.setCacheConfig(req.getMobile(), randomCode.toString());
			
			//String view = cacheManager.getCacheConfig("view");
			//log.info("getCacheConfig,val={},", view);
			
			return toData(resp);
		} catch (Exception e) {
			log.error("getVerifiCode exception:{}", e);
			return toErr("error", "message");
		}
	}
	
	/**
	 * 异步线程
	 *
	 */
	private void asyncSendVerifiCode(final String verifyCode) {
		try {
			final String traceId= KiddTraceLogUtil.getTraceId();
			log.info("asyncSendVerifiCode start");
			asyncTaskExecutor.exeWithoutResult(new IAsyncTaskExecutor.AsyncTaskCallBack<Object>() {
				@Override
				public Object invork() throws KiddException {
					KiddTraceLogUtil.beginTrace(traceId);
					try {
						log.info("asynchronous start,code={}",verifyCode);
					} catch (Exception e) {
						log.error("asynchronous exception", e);
					}
					return null;
				}
			});
		} catch (Exception e) {
			log.error("do asynchronous exception",e);
		}
	}
}
