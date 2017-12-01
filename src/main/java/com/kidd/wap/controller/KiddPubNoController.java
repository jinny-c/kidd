package com.kidd.wap.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kidd.base.common.KiddBaseController;
import com.kidd.base.common.enums.KiddSymbolEnum;
import com.kidd.base.common.exception.KiddControllerException;
import com.kidd.base.common.params.valid.VerifyControllerUtil;
import com.kidd.base.common.utils.KiddStringUtils;
import com.kidd.base.common.utils.KiddTraceLogUtil;
import com.kidd.base.factory.message.IMsgHandlerDispatcher;
import com.kidd.base.factory.message.dto.KiddPushMessage;
import com.kidd.base.factory.message.dto.WechatMessage;
import com.kidd.base.factory.wechat.KiddPubNoClient;
import com.kidd.base.factory.wechat.WechatMessageXMLUtil;
import com.kidd.base.http.RequestResponseContext;

@Controller
@RequestMapping("/wap/pubnum")
public class KiddPubNoController extends KiddBaseController {
	/* Slf4j */
	private static Logger log = LoggerFactory.getLogger(KiddPubNoController.class);


	@Autowired
	private KiddPubNoClient pubNoClient;
	
	@Autowired
	private IMsgHandlerDispatcher msgHandlerDispatcher;
	
	/**
	 * 清除本地公众号缓存（手动）
	 *
	 */
	/*@RequestMapping(value = "/clear", method = {RequestMethod.GET, RequestMethod.POST})
	public String clear(Model model){
		pubNoClient.clearCache();
		model.addAttribute("message", "本地公众号缓存清除成功");
		return "wap/tips";
	}*/

	/**
	 * 刷新公众号TOKEN（手动）
	 *
	 */
	/*@RequestMapping(value = "/refresh", method = {RequestMethod.GET, RequestMethod.POST})
	public String refresh(Model model, String pubId){
		if (KiddStringUtils.isBlank(pubId)
				|| KiddStringUtils.isBlank(pubNoClient.getPubNoAppId(pubId))) {
			model.addAttribute("message", "公众号不存在，请检查配置！");
		}
		pubNoClient.refreshToken(pubId);
		model.addAttribute("message", "公众号TOKEN刷新成功！");
		return "wap/tips";
	}*/

	
	/**
	 * 长链接转短链接
	 *
	 */
	@RequestMapping(value = "/long2short", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String long2short(String pubId, String url){
		if (KiddStringUtils.isBlank(pubId) || KiddStringUtils.isBlank(url)) {
			return KiddSymbolEnum.Blank.symbol();
		}
		String pubNoAppId = pubNoClient.getPubNoAppId(pubId);
		if (KiddStringUtils.isBlank(pubNoAppId)) {
			return KiddSymbolEnum.Blank.symbol();
		}
		//String oAuth2Url = KiddNoCardPubNoUtil.getOAuth2Url(pubNoClient.getPubNoAppId(pubId), url);
		String oAuth2Url ="url";
		try {
			String shortUrl = pubNoClient.long2short(pubId, oAuth2Url);
			log.info("短链接：{}", shortUrl);
			return shortUrl;
		} catch (Exception e) {
			log.error("长链接转短链接失败, oAuth2Url={}", oAuth2Url, e);
		}
		return KiddSymbolEnum.Blank.symbol();
	}

	/**
	 * 重置微信公众号菜单 【手动】
	 *
	 */
	@RequestMapping(value = "/resetMenu", method = {RequestMethod.GET, RequestMethod.POST})
	public String resetMenu(String pubId, Model model) throws KiddControllerException{
		VerifyControllerUtil.assertNotBlank(pubId, "微信公众号ID不能为空！");
		//VerifyControllerUtil.assertNotBlank(pubNoClient.getPubNoAppId(pubId),KiddErrorCode.ERROR_CODE_MW016.getErrorMsg());
		log.info("TraceID:{}, 重置微信公众号[{}]菜单", KiddTraceLogUtil.getTraceId(), pubId);
		//String jsonMenu = KiddPubNoMenuService.generateJsonMenuTree(pubId);
		String jsonMenu = "jsonMenu";
		log.info("微信公众号[{}]菜单配置", pubId, jsonMenu);
		boolean flag = pubNoClient.createMenu(pubId, jsonMenu);
		model.addAttribute("message", flag ? "菜单重置成功":"菜单重置失败");
		return "wap/tips";
	}

	/**
	 * 公众号推送消息和事件，实现公众号与用户交互
	 *
	 */
	@RequestMapping(value = "/interaction_{id}", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void interaction(@PathVariable("id") String pubId){
		HttpServletRequest request = RequestResponseContext.getRequest();
		if(HttpMethod.GET.name().equals(request.getMethod())){
			validWechatServer(request);
			return;
		}
		//微信服务器发送POST请求
		try {
			String message = readMessage(request);
			WechatMessage wechatMessage = WechatMessageXMLUtil.xmlToMessage(message);
			if (wechatMessage == null) {
				writeToResponse("failed");
				return;
			}
			log.info("TraceID:{}, 接收到微信推送消息，wechatMessage={}", KiddTraceLogUtil.getTraceId(),
					JSON.toJSONString(wechatMessage));
			msgHandlerDispatcher.dispatch(wechatMessage);
			log.info("TraceID:{}, 微信消息推送成功", KiddTraceLogUtil.getTraceId());
			writeToResponse("success");
		} catch (Exception e) {
			log.error("微信推送消息失败", e);
			writeToResponse("failed");
		}
	}

	/**
	 * 后台推送通知
	 *
	 */
	@RequestMapping(value = "/wechatNotify", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void wechatNotify(){
		try {
			String message = readMessage(getRequest());
			KiddPushMessage KiddMessage = JSON.parseObject(message, KiddPushMessage.class);
			if (KiddMessage == null) {
				writeToResponse("Kidd message is empty");
				return;
			}
			log.info("TraceID:{}, 接收到推送消息，KiddMessage={}", KiddTraceLogUtil.getTraceId(),
					JSON.toJSONString(KiddMessage));
			msgHandlerDispatcher.dispatch(KiddMessage);
			writeToResponse("Kidd message push success");
		} catch (Exception e) {
			log.error("推送消息异常", e);
			writeToResponse("Kidd message push failed");
		}
	}


	/**
	 * 验证微信服务器
	 *
	 * @param request
	 */
	private void validWechatServer(HttpServletRequest request) {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		log.info("timestamp={},nonce={},echostr={},signature={}", timestamp, nonce, echostr, signature);
		//校验签名
		if (KiddStringUtils.isBlank(signature)) {
			log.error("TraceID:{}, 微信服务器未签名", KiddTraceLogUtil.getTraceId());
			return;
		}
		String[] strArr = new String[]{"kidd", timestamp, nonce};
		Arrays.sort(strArr);
		StringBuilder sbd = new StringBuilder();
		for(String str: strArr){
			sbd.append(str);
		}
		log.info("TraceID:{}, SHA1签名数据：{}", KiddTraceLogUtil.getTraceId(), sbd.toString());
		if(signature.equalsIgnoreCase(DigestUtils.sha1Hex(sbd.toString()))){
			log.info("TraceID:{}, 微信服务器验签成功", KiddTraceLogUtil.getTraceId());
			writeToResponse(echostr);
			return;
		}
		log.info("TraceID:{}, 微信服务器验签失败", KiddTraceLogUtil.getTraceId());
		writeToResponse("wechatserver verify sign failed");
	}

	private String readMessage(HttpServletRequest request) {
		String line;
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			while ((line = br.readLine()) != null){
				sb.append(line);
			}
		} catch (IOException e) {
			log.error("TraceID:{}, 获取消息失败", KiddTraceLogUtil.getTraceId(), e);
		}
		return sb.toString();
	}

	

}
