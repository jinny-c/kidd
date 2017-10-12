package com.kidd.base.factory.wechat.dto;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;

public class WechatTemplateMsg {

	private static final String DEF_COLOR = "#000000";

	/**
	 * 模板消息id
	 */
	private String template_id;
	/**
	 * 用户openId
	 */
	private String touser;
	/**
	 * URL置空，则在发送后，点击模板消息会进入一个空白页面（ios），或无法点击（android）
	 */
	private String url;
	/**
	 * 标题颜色
	 */
	private String topcolor;
	/**
	 * 详细内容
	 */
	@JSONField(name = "data")
	private Map<String, Data> dataMap = new HashMap<String, Data>();
	;

	public WechatTemplateMsg() {
	}

	public WechatTemplateMsg(String toUser, String templateID, String url) {
		this.touser = toUser;
		this.template_id = templateID;
		this.url = url;
		this.topcolor = DEF_COLOR;
	}

	private static class Data {
		private String value;
		private String color;

		public Data() {
			this.color = DEF_COLOR;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

	public Map<String, Data> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Data> dataMap) {
		this.dataMap = dataMap;
	}

	public void putData(String key, String value) {
		Data data = new Data();
		data.setValue(value);
		dataMap.put(key, data);
	}

}