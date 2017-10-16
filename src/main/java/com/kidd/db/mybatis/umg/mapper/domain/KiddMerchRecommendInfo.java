package com.kidd.db.mybatis.umg.mapper.domain;

import java.util.Date;

public class KiddMerchRecommendInfo {
    private Integer loginUserId;

    private String merchantCode;

    private String recommender;

    private Short recommendType;

    private String recommendPubId;

    private Date createTime;

    private Date uptTime;

    public KiddMerchRecommendInfo(Integer loginUserId, String merchantCode, String recommender, Short recommendType, String recommendPubId, Date createTime, Date uptTime) {
        this.loginUserId = loginUserId;
        this.merchantCode = merchantCode;
        this.recommender = recommender;
        this.recommendType = recommendType;
        this.recommendPubId = recommendPubId;
        this.createTime = createTime;
        this.uptTime = uptTime;
    }

    public KiddMerchRecommendInfo() {
        super();
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

    public String getRecommender() {
        return recommender;
    }

    public void setRecommender(String recommender) {
        this.recommender = recommender == null ? null : recommender.trim();
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
        this.recommendPubId = recommendPubId == null ? null : recommendPubId.trim();
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
}