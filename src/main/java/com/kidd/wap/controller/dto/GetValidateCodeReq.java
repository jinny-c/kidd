package com.kidd.wap.controller.dto;

import java.io.Serializable;

import com.kidd.base.KiddBaseReqDto;
import com.kidd.base.annotation.KiddDecrAnno;
import com.kidd.base.annotation.KiddNotBlank;
import com.kidd.base.annotation.KiddSecureAnno;
import com.kidd.base.common.KiddErrorCodes;
import com.kidd.base.enums.KiddSymbolEnum;
import com.kidd.base.params.valid.KiddValidResp;
import com.kidd.base.utils.KiddStringUtils;
import com.kidd.base.utils.ToStringUtils;

@KiddSecureAnno
public class GetValidateCodeReq  extends KiddBaseReqDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@KiddNotBlank(code = KiddErrorCodes.E_KIDD_NULL, message = "渠道不能为空！")
	private String channel;

	@KiddDecrAnno
	private String mobile;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Override
	public String toString() {
		ToStringUtils builder = new ToStringUtils(this);
		builder.add("channel", channel).add("mobile", mobile);
		return builder.toString();
	}

	@Override
	public KiddValidResp valid() {
		String errMsg = KiddSymbolEnum.Blank.symbol();
		if (KiddStringUtils.isBlank(mobile)) {
			errMsg = "[手机号]为空";
			return new KiddValidResp(KiddErrorCodes.E_KIDD_NULL, errMsg);
		}
		return new KiddValidResp(true);
	}

}
