package com.kidd.wap.controller;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kidd.base.common.KiddBaseController;
import com.kidd.base.common.exception.KiddControllerException;
import com.kidd.base.common.utils.KiddStringUtils;
import com.kidd.wap.controller.dto.KiddLuckDrawResp;

@Controller
@RequestMapping("/wap/prize")
public class KidWapLuckDrawController extends KiddBaseController {
	/* Slf4j */
	private static Logger log = LoggerFactory.getLogger(KidWapLuckDrawController.class);

	public static final String PRIZE_TOKEN = "prizeToken";


	private Map<String, Object[]> queryPrizeInfos()
			throws KiddControllerException {
		Map<String, Object[]> dataMap = new HashMap<String, Object[]>();
		List<String> colorList = new ArrayList<String>();
		List<String> prizeList = new ArrayList<String>();
		List<Integer> prizeIdList = new ArrayList<Integer>();
		int len = 8;
		for (int i = 0; i < len; i++) {
			prizeList.add("PrizeName-" + i);
			if (i % 2 == 0) {
				colorList.add("#FFFFFF");
			} else {
				colorList.add("#FFF4D6");
			}
			prizeIdList.add(i);
		}

		String[] prizes = (String[]) prizeList.toArray(new String[len]);
		String[] colors = (String[]) colorList.toArray(new String[len]);
		Integer[] prizeIds = (Integer[]) prizeIdList.toArray(new Integer[len]);
		dataMap.put("prizes", prizes);
		dataMap.put("colors", colors);
		dataMap.put("prizeIds", prizeIds);

		return dataMap;
	}

	/**
	 * 抽奖界面【微信公众号调用】
	 * @param request
	 * @param model
	 * @return
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/showPrizeIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String showPrizeIndex(HttpServletRequest request, Model model)
			throws KiddControllerException {
		try {
			Map<String, Object[]> dataMap = queryPrizeInfos();// 奖品列表
			KiddLuckDrawResp resp = new KiddLuckDrawResp();
			resp.setLoginUserId(19886615);
			
			resp.setActivityId(1);
			
			resp.setMobile("mobile");
			resp.setPrizeName("prizeName");
			
			List<KiddLuckDrawResp> prizeLogs = new ArrayList<KiddLuckDrawResp>();
			prizeLogs.add(resp);
			
			model.addAttribute("startDate","");
			model.addAttribute("endDate","");
			model.addAttribute("prizeLogs", prizeLogs); // 获奖名单
			model.addAttribute("dataMap", dataMap);
			model.addAttribute("userPrizeStat", resp);// 根据用户获取抽奖次数
			//String tokenValue =UUIDGenerator.getUUID();
			String tokenValue ="tokenValue";
			getSession().setAttribute(PRIZE_TOKEN, tokenValue);
			model.addAttribute("tokenValue", tokenValue);
			model.addAttribute("loginUserId", "loginUserId");
			model.addAttribute("merchantCode", "merchantCode");
			model.addAttribute("channel", "channel");
			model.addAttribute("prizeThreshold", "20000");

		} catch (KiddControllerException e) {
			log.error("KiddControllerException error", e);
			model.addAttribute("message", e.getErrorMsg());
			return "wap/prize/prize_tips";
		}
		return "wap/prize/show_prize_index";
	}
	
	
	/**
	 * 用户抽奖
	 * 
	 * @return
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/prizeLottery", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String prizeLottery() throws KiddControllerException {
		String tokenValue = getRequest().getParameter("tokenValue");
		
		String token = (String) getSession().getAttribute(PRIZE_TOKEN);
		if (KiddStringUtils.isNotBlank(token) && token.equals(tokenValue)) {
			tokenValue ="tokenValue2";
			getSession().setAttribute(PRIZE_TOKEN, tokenValue);
		}
		Map<String, String> resp = new HashMap<String, String>();
		resp.put("returnCode", "00");
		resp.put("tokenValue", "tokenValue");
		resp.put("isAmtvalue", "1");
		resp.put("prizeCode", "8");
		resp.put("prizeId", String.valueOf(new SecureRandom().nextInt(8)));
				
		return convertJson(resp);
	}
}
