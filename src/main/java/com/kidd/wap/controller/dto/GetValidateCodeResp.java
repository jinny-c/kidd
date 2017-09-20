package com.kidd.wap.controller.dto;

import java.io.Serializable;

import com.kidd.base.annotation.KiddEncrAnno;
import com.kidd.base.annotation.KiddSecureAnno;
import com.kidd.base.common.KiddBaseRespDto;
import com.kidd.base.common.utils.ToStringUtils;

@KiddSecureAnno
public class GetValidateCodeResp extends KiddBaseRespDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@KiddEncrAnno
	private String channel;
	
	// 登录图形验证码
	private String imageCode;

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getImageCode() {
		return imageCode;
	}

	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}

	@Override
	public String toString() {
		ToStringUtils builder = new ToStringUtils(this);
		builder.add("channel", channel).add("imageCode", imageCode);
		return builder.toString();
	}
}
