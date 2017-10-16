package com.kidd.db.mybatis.umg.services.bean;

import java.util.Date;

import com.kidd.base.common.utils.ToStringUtils;

public class KiddMerchRecommendBean {
    private Integer loginUserId;

    private String merchantCode;

    private String recommender;

    private Short recommendType;

    private String recommendPubId;

    private Date createTime;
    private Date uptTime;
    
    private String createTimeFormate;
    private String uptTimeFormate;
	public Integer getLoginUserId() {
		return loginUserId;
	}
	public void setLoginUserId(Integer loginUserId) {
		this.loginUserId = loginUserId;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getRecommender() {
		return recommender;
	}
	public void setRecommender(String recommender) {
		this.recommender = recommender;
	}
	public Short getRecommendType() {
		return recommendType;
	}
	public void setRecommendType(Short recommendType) {
		this.recommendType = recommendType;
	}
	public String getRecommendPubId() {
		return recommendPubId;
	}
	public void setRecommendPubId(String recommendPubId) {
		this.recommendPubId = recommendPubId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUptTime() {
		return uptTime;
	}
	public void setUptTime(Date uptTime) {
		this.uptTime = uptTime;
	}
	public String getCreateTimeFormate() {
		return createTimeFormate;
	}
	public void setCreateTimeFormate(String createTimeFormate) {
		this.createTimeFormate = createTimeFormate;
	}
	public String getUptTimeFormate() {
		return uptTimeFormate;
	}
	public void setUptTimeFormate(String uptTimeFormate) {
		this.uptTimeFormate = uptTimeFormate;
	}
	
	@Override
	public String toString() {
		ToStringUtils builder = new ToStringUtils(this);
		builder.add("loginUserId", loginUserId)
				.add("merchantCode", merchantCode)
				.add("recommender", recommender)
				.add("recommendType", recommendType)
				.add("recommendPubId", recommendPubId)
				.add("createTime", createTime).add("uptTime", uptTime)
				.add("createTimeFormate", createTimeFormate)
				.add("uptTimeFormate", uptTimeFormate);
		return builder.toString();
	}

}