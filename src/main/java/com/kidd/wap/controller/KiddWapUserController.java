package com.kidd.wap.controller;

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

import com.kidd.base.KiddBaseController;
import com.kidd.base.annotation.KiddSecureAnno;
import com.kidd.base.asnyc.IAsyncTaskExecutor;
import com.kidd.base.asnyc.SimpleAsyncTaskExecutor;
import com.kidd.base.cache.KiddCacheManager;
import com.kidd.base.exception.KiddControllerException;
import com.kidd.base.exception.KiddGlobalValidException;
import com.kidd.wap.controller.dto.GetValidateCodeReq;
import com.kidd.wap.controller.dto.GetValidateCodeResp;


/**
 *
 * @history
 */
@Controller
@RequestMapping("/wap/user")
public class KiddWapUserController extends KiddBaseController{
	/* Slf4j */
	private static Logger log = LoggerFactory.getLogger(KiddWapUserController.class);
	
	// 验证码字符个数
    private static final int CODE_COUNT = 4;
    private static final char[] CODE_SEQUENCE = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    
	@Autowired
	private KiddCacheManager cacheManager;
	
	/** 异步线程 **/
	private IAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
    

	@RequestMapping(value = "/index", method = {RequestMethod.GET, RequestMethod.POST})
	public String index() throws KiddControllerException{
		log.info("index enter");
		
		return toWapHtml("userInfo");
	}
	@RequestMapping(value = "/info", method = {RequestMethod.GET, RequestMethod.POST})
	public String info(Model model) throws KiddControllerException{
		log.info("info enter");
		
		asyncSendVerifiCode("info code");
		
		model.addAttribute("message", "hello word!");
		return toWapHtml("info");
	}

	@RequestMapping(value = "/getVerificationCode_{flag}", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Object getVerifiCode(@KiddSecureAnno GetValidateCodeReq req,@PathVariable String flag){
		try {
			log.info("getVerifiCode,req={},flag={}", req, flag);
			if (!"wap".equals(flag)) {
				return toErr("error", "message");
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
			
			String wap = cacheManager.getCacheConfig("0_wap");
			log.info("getCacheConfig,val={},", wap);
			
			cacheManager.setCacheConfig(req.getMobile(), randomCode.toString());
			
			String view = cacheManager.getCacheConfig("view");
			log.info("getCacheConfig,val={},", view);
			
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
	private void asyncSendVerifiCode(final String code) {
		try {
			asyncTaskExecutor.exeWithoutResult(new IAsyncTaskExecutor.AsyncTaskCallBack<Object>() {
				@Override
				public Object invork() throws KiddGlobalValidException {
					try {
						log.info("asynchronous start,code={}",code);
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
