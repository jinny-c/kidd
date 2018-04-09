package com.kidd.test.bean;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import com.kidd.base.common.utils.ToStringUtils;

public class UserTestBean  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
	private String channel;
	@Getter
	@Setter
	private String clientChannel;
	// 类型
	@Getter
	@Setter
	private String actionFlag;
	@Getter
	@Setter
	private String userName;// 用户名
	@Getter
	@Setter
	private String password;// 密码
	@Getter
	@Setter
	private String mobile;
	// 验证码
	@Getter
	@Setter
	private String verifyCode;
	// 登录图形验证码
	@Getter
	@Setter
	private String imageCode;
	// 00:用户名登录,01:手机号登录
	@Getter
	@Setter
	private String loginType;

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

}
