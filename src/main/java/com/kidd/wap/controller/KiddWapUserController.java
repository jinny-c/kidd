package com.kidd.wap.controller;

import java.security.SecureRandom;

import javax.servlet.http.HttpServletRequest;

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
import com.kidd.base.common.constant.KiddErrorCodes;
import com.kidd.base.common.exception.KiddControllerException;
import com.kidd.base.common.exception.KiddGlobalValidException;
import com.kidd.base.common.utils.KiddStringUtils;
import com.kidd.base.common.utils.KiddTraceLogUtil;
import com.kidd.base.factory.annotation.KiddSecureAnno;
import com.kidd.base.factory.asnyc.IAsyncTaskExecutor;
import com.kidd.base.factory.asnyc.SimpleAsyncTaskExecutor;
import com.kidd.base.factory.cache.KiddCacheManager;
import com.kidd.base.http.RequestResponseContext;
import com.kidd.wap.controller.dto.GetValidateCodeReq;
import com.kidd.wap.controller.dto.GetValidateCodeResp;
import com.kidd.wap.controller.dto.UserLoginReq;
import com.kidd.wap.controller.enums.KiddWapWildcardEnum;


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
	@RequestMapping(value = "/toInfo", method = {RequestMethod.GET, RequestMethod.POST})
	public String toInfo(Model model) throws KiddControllerException{
		log.info("toInfo enter");
		
		asyncSendVerifiCode("info code");
		
		model.addAttribute("message", "hello word!");
		return toWapHtml("info");
	}
	@RequestMapping(value = "/toLogin", method = {RequestMethod.GET, RequestMethod.POST})
	public String toLogin() throws KiddControllerException{
		log.info("toLogin enter");
		
		return toWapHtml("login");
	}
	@RequestMapping(value = "/toSuccess", method = {RequestMethod.GET, RequestMethod.POST})
	public String toSuccess(Model model) throws KiddControllerException{
		HttpServletRequest request = RequestResponseContext.getRequest();
		String flag = request.getParameter("flag");
		log.info("toSuccess enter,flag={}", flag);
		model.addAttribute("flag", flag);
		return toWapHtml("res_success");
	}
	@RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Object login(@KiddSecureAnno UserLoginReq req) throws KiddControllerException{
		log.info("login enter,req={}", req);
		
		String code = cacheManager.getCacheConfig(req.getMobile());
		
		if(KiddStringUtils.isBlank(code)){
			throw new KiddControllerException(KiddErrorCodes.E_KIDD_NULL, "未查到验证码，请重新获取！");
		}
		
		if(req.isLoginBy00()){
			//用户名
			if(!code.equals(req.getImageCode())){
				return toErr(KiddErrorCodes.E_KIDD_ERROR, "图形验证码不匹配，请重新输入");
			}
		}
		if(req.isLoginBy01()){
			//手机号
			if(!code.equals(req.getVerifyCode())){
				return toErr(KiddErrorCodes.E_KIDD_ERROR, "手机验证码不匹配，请重新输入");
			}
		}
		return toSucc();
	}

	@RequestMapping(value = "/getVerificationCode_{wildcard}", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Object getVerifiCode(@KiddSecureAnno GetValidateCodeReq req,@PathVariable String wildcard){
		try {
			log.info("getVerifiCode,req={},wildcard={}", req, wildcard);
			if (!KiddWapWildcardEnum.isExsit(wildcard)) {
				return toErr(KiddErrorCodes.E_KIDD_ERROR, "没有定义的请求！");
			}
			SecureRandom random = new SecureRandom();
			StringBuilder randomCode = new StringBuilder();
			GetValidateCodeResp resp = new GetValidateCodeResp();
			resp.setChannel(req.getChannel());

			if (KiddWapWildcardEnum.isImageCode(wildcard)) {
				String strRand = null;
				for (int i = 0; i < CODE_COUNT; i++) {
					strRand = String.valueOf(CODE_SEQUENCE[random
							.nextInt(CODE_SEQUENCE.length)]);
					randomCode.append(strRand);
				}
				resp.setImageCode(randomCode.toString());
			}

			if (KiddWapWildcardEnum.isVerifyCode(wildcard)) {
				int rd = random.nextInt(9999 - 1000 + 1) + 1000;
				randomCode.append(String.valueOf(rd));
			}

			// String wap = cacheManager.getCacheConfig("0_wap");
			// log.info("getCacheConfig,val={},", wap);

			cacheManager.setCacheConfig(req.getMobile(), randomCode.toString());

			// String view = cacheManager.getCacheConfig("view");
			// log.info("getCacheConfig,val={},", view);

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
				public Object invork() throws KiddGlobalValidException {
					KiddTraceLogUtil.beginTrace(traceId);
					try {
						log.info("asynchronous start,code={}",verifyCode);
					} catch (Exception e) {
						log.error("asynchronous exception", e);
					}
					KiddTraceLogUtil.endTrace();
					return null;
				}
			});
		} catch (Exception e) {
			log.error("do asynchronous exception",e);
		}
	}
}
