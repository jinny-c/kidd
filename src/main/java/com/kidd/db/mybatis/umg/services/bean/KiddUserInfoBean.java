package com.kidd.db.mybatis.umg.services.bean;

import com.kidd.base.common.utils.ToStringUtils;

public class KiddUserInfoBean {
    private String userId;

    private String userName;

    private String realName;

    private String nickName;

    private String reservedField;

    private String creatTime;

    private String updTime;

    private String userSex;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getReservedField() {
        return reservedField;
    }

    public void setReservedField(String reservedField) {
        this.reservedField = reservedField == null ? null : reservedField.trim();
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime == null ? null : creatTime.trim();
    }

    public String getUpdTime() {
        return updTime;
    }

    public void setUpdTime(String updTime) {
        this.updTime = updTime == null ? null : updTime.trim();
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex == null ? null : userSex.trim();
    }

	@Override
	public String toString() {
		ToStringUtils builder = new ToStringUtils(this);
		builder.add("userId", userId).add("userName", userName)
				.add("realName", realName).add("nickName", nickName)
				.add("reservedField", reservedField)
				.add("creatTime", creatTime).add("updTime", updTime)
				.add("userSex", userSex);
		return builder.toString();
	}
    
}