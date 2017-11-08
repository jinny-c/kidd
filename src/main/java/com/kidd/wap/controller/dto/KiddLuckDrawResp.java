package com.kidd.wap.controller.dto;

import java.io.Serializable;
import java.util.Date;

import com.kidd.base.common.KiddBaseRespDto;
import com.kidd.base.common.utils.ToStringUtils;

public class KiddLuckDrawResp extends KiddBaseRespDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer activityId;

	private Integer loginUserId;

	private String merchantCode;

	private String channel;

	private Integer prizeId;

	private Date createTime;

	private Date uptTime;

	private String mobile;

	private String prizeName;

	private String activityStartDate;

	private String activityEndDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

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
		this.merchantCode = merchantCode == null ? null : merchantCode.trim();
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel == null ? null : channel.trim();
	}

	public Integer getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(Integer prizeId) {
		this.prizeId = prizeId;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public String getActivityStartDate() {
		return activityStartDate;
	}

	public void setActivityStartDate(String activityStartDate) {
		this.activityStartDate = activityStartDate;
	}

	public String getActivityEndDate() {
		return activityEndDate;
	}

	public void setActivityEndDate(String activityEndDate) {
		this.activityEndDate = activityEndDate;
	}

	@Override
	public String toString() {
		ToStringUtils builder = new ToStringUtils(this);
		builder.add("id", id).add("activityId", activityId)
				.add("loginUserId", loginUserId)
				.add("merchantCode", merchantCode).add("channel", channel)
				.add("prizeId", prizeId).add("createTime", createTime)
				.add("uptTime", uptTime).add("mobile", mobile)
				.add("prizeName", prizeName)
				.add("activityStartDate", activityStartDate)
				.add("activityEndDate", activityEndDate);
		return builder.toString();
	}

}