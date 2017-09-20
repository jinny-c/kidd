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
public class UserLoginReq  extends KiddBaseReqDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@KiddNotBlank(code = KiddErrorCodes.E_KIDD_NULL, message = "[渠道]为空！")
	private String channel;
	@KiddNotBlank(code = KiddErrorCodes.E_KIDD_NULL, message = "[客户端渠道]为空！")
	private String clientChannel;
	// 类型
	private String actionFlag;
	
	private String userName;// 用户名
	@KiddDecrAnno
	private String password;// 密码
	
	@KiddDecrAnno
	private String mobile;
	// 验证码
	private String verifyCode;
	
	// 登录图形验证码
	private String imageCode;
	// 00:用户名登录,01:手机号登录
	@KiddNotBlank(code = KiddErrorCodes.E_KIDD_NULL, message = "[登录类型]为空！")
	private String loginType;

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

	public String getActionFlag() {
		return actionFlag;
	}

	public void setActionFlag(String actionFlag) {
		this.actionFlag = actionFlag;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getImageCode() {
		return imageCode;
	}

	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}

	public String getClientChannel() {
		return clientChannel;
	}

	public void setClientChannel(String clientChannel) {
		this.clientChannel = clientChannel;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	@Override
	public String toString() {
		ToStringUtils builder = new ToStringUtils(this);
		builder.add("channel", channel).add("clientChannel", clientChannel)
				.add("actionFlag", actionFlag).add("userName", userName)
				.add("password", "******").add("mobile", mobile)
				.add("verifyCode", verifyCode).add("imageCode", imageCode)
				.add("loginType", loginType);
		return builder.toString();
	}

	@Override
	public KiddValidResp valid() {
		String errMsg = KiddSymbolEnum.Blank.symbol();
		if(isLoginBy00()){
			if (KiddStringUtils.isBlank(userName)) {
				errMsg = "[用户名]为空";
				return new KiddValidResp(KiddErrorCodes.E_KIDD_NULL, errMsg);
			}
			if (KiddStringUtils.isBlank(password)) {
				errMsg = "[密码]为空";
				return new KiddValidResp(KiddErrorCodes.E_KIDD_NULL, errMsg);
			}
			if (KiddStringUtils.isBlank(imageCode)) {
				errMsg = "[图形验证码]为空";
				return new KiddValidResp(KiddErrorCodes.E_KIDD_NULL, errMsg);
			}
		}
		if(isLoginBy01()){
			if (KiddStringUtils.isBlank(mobile)) {
				errMsg = "[手机号]为空";
				return new KiddValidResp(KiddErrorCodes.E_KIDD_NULL, errMsg);
			}
			if (KiddStringUtils.isBlank(verifyCode)) {
				errMsg = "[验证码]为空";
				return new KiddValidResp(KiddErrorCodes.E_KIDD_NULL, errMsg);
			}
		}
		return new KiddValidResp(true);
	}

	public boolean isLoginBy00(){
		return "00".equals(loginType);
	}
	public boolean isLoginBy01(){
		return "01".equals(loginType);
	}
}
