package com.kidd.wap.controller;

import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kidd.base.KiddBaseController;
import com.kidd.base.annotation.KiddSecureAnno;
import com.kidd.base.exception.KiddControllerException;
import com.kidd.wap.controller.dto.GetValidateCodeReq;


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

    

	@RequestMapping(value = "/index", method = {RequestMethod.GET, RequestMethod.POST})
	public String index() throws KiddControllerException{
		log.info("index enter");
		
		return "/wap/userInfo";
	}

	@RequestMapping(value = "/getVerificationCode_{flag}", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String getVerifiCode(@KiddSecureAnno GetValidateCodeReq req,@PathVariable String flag){
		try {
			log.info("getVerifiCode,req={},flag={}", req, flag);
			if("mobile".equals(flag)){
				
			}
			SecureRandom random = new SecureRandom();
			StringBuilder randomCode = new StringBuilder();
			for (int i = 0; i < CODE_COUNT; i++) {
				String strRand = String.valueOf(CODE_SEQUENCE[random.nextInt(CODE_SEQUENCE.length)]);
				randomCode.append(strRand);
			}
			return randomCode.toString();
		} catch (Exception e) {
			log.info("getVerifiCode exception:{}", e);
			return "error";
		}
	}
	
}
