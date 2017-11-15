package com.kidd.db.mybatis.umg.mapper.domain;

public class KiddUserInfo {
    private String userId;

    private String userName;

    private String realName;

    private String nickName;

    private String reservedField;

    private String creatTime;

    private String updTime;

    private String userSex;

	public KiddUserInfo(String userId, String userName, String realName,
			String nickName, String reservedField, String creatTime,
			String updTime, String userSex) {
        this.userId = userId;
        this.userName = userName;
        this.realName = realName;
        this.nickName = nickName;
        this.reservedField = reservedField;
        this.creatTime = creatTime;
        this.updTime = updTime;
        this.userSex = userSex;
    }

    public KiddUserInfo() {
        super();
    }

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
}