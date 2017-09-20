package com.kidd.wap.controller.dto;

import java.io.Serializable;

import com.kidd.base.annotation.KiddDecrAnno;
import com.kidd.base.annotation.KiddNotBlank;
import com.kidd.base.annotation.KiddSecureAnno;
import com.kidd.base.common.KiddBaseReqDto;
import com.kidd.base.common.KiddErrorCodes;
import com.kidd.base.common.utils.KiddStringUtils;
import com.kidd.base.common.utils.ToStringUtils;
import com.kidd.base.enums.KiddSymbolEnum;
import com.kidd.base.params.valid.KiddValidResp;

@KiddSecureAnno
public class GetValidateCodeReq extends KiddBaseReqDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@KiddNotBlank(code = KiddErrorCodes.E_KIDD_NULL, message = "[渠道]为空！")
	private String channel;
	@KiddNotBlank(code = KiddErrorCodes.E_KIDD_NULL, message = "[客户端渠道]为空！")
	private String clientChannel;

	// 类型
	private String actionFlag;
	
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

	public String getClientChannel() {
		return clientChannel;
	}

	public void setClientChannel(String clientChannel) {
		this.clientChannel = clientChannel;
	}

	public String getActionFlag() {
		return actionFlag;
	}

	public void setActionFlag(String actionFlag) {
		this.actionFlag = actionFlag;
	}

	@Override
	public String toString() {
		ToStringUtils builder = new ToStringUtils(this);
		builder.add("channel", channel).add("clientChannel", clientChannel)
				.add("actionFlag", actionFlag).add("mobile", mobile);
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
