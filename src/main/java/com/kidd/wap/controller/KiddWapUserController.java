package com.kidd.wap.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kidd.base.KiddBaseController;
import com.kidd.base.exception.KiddControllerException;


/**
 *
 * @history
 */
@Controller
@RequestMapping("/wap/user")
public class KiddWapUserController extends KiddBaseController{
	/* Slf4j */
	private static Logger log = LoggerFactory.getLogger(KiddWapUserController.class);

	@RequestMapping(value = "/index", method = {RequestMethod.GET, RequestMethod.POST})
	public String index() throws KiddControllerException{
		log.info("index enter");
		
		return "/wap/userInfo";
	}

}
