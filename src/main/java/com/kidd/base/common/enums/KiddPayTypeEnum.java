package com.kidd.base.common.enums;

import com.kidd.base.common.utils.KiddStringUtils;


public enum KiddPayTypeEnum {
	/** posPay-刷卡支付 **/
	PAY_TYPE_POS("posPay", "刷卡支付"),
	/** wechatPay-微信支付（反扫） **/
	PAY_TYPE_WECHATPAY("wechatPay", "微信支付"),
	/** aliPay-支付宝支付 **/
	PAY_TYPE_ALIPAY("aliPay", "支付宝支付"),
    /** wechatScanPay-微信正扫支付(扫码支付) **/
    PAY_TYPE_WECHAT_SCAN_PAY("wechatScanPay", "微信正扫支付"),
	/** aliScanPay-支付宝正扫支付(扫码支付) **/
	PAY_TYPE_ALI_SCAN_PAY("aliScanPay", "支付宝正扫支付"),
    /** citicCodePay-中信银行扫码支付 **/
    PAY_TYPE_CITIC_CODE_PAY("citicCodePay", "中信银行扫码支付"),
	/** hpWithdraw-秒提 **/
	PAY_TYPE_TS_WITHDRAW("tsWithdraw", "秒提"),
	/** 空类型，处理NULL**/
	PAY_TYPE_NONE("","空类型"),
	/** unionQRCode-银联二维码支付**/
	PAY_TYPE_UNION_CODE_PAY("unionQRCode","银联二维码支付"),
	/** shortCutPay-银联无卡支付**/
	PAY_TYPE_UNION_NO_CARD_PAY("shortCutPay","银联无卡支付"),
	/** 分润提现T+1 **/
	PAY_TYPE_FR_WITHDRAW("frWithdraw", "分润提现"),

	;
	/** 支付类型 **/
	private String type;
	/** 支付类型描述 **/
	private String desc;

	private KiddPayTypeEnum(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}

	public boolean isPospay() {
		return this == PAY_TYPE_POS;
	}

	public boolean isWechatpay() {
		return this == PAY_TYPE_WECHATPAY;
	}

	public boolean isAlipay() {
		return this == PAY_TYPE_ALIPAY;
	}

    public boolean isWechatScanPay() {
        return this == PAY_TYPE_WECHAT_SCAN_PAY;
    }

	public boolean isAliScanPay() {
		return this == PAY_TYPE_ALI_SCAN_PAY;
	}

	public boolean isNull() {
		return this == PAY_TYPE_NONE;
	}
	
	public boolean isUnionNoCardPay() {
		return this == PAY_TYPE_UNION_NO_CARD_PAY;
	}

	public boolean isTsWithDraw() {
		return this == PAY_TYPE_TS_WITHDRAW;
	}

	/**
	 * 是否是二维码支付
	 * 
	 * @return
	 */
	public boolean isQrCodePay() {
		return isAlipay() || isWechatpay();
	}

	/**
	 * 是否是二维码支付
	 * @param payType
	 * @return
	 */
	public static boolean isQrCodePay(String payType) {
		KiddPayTypeEnum e = covertByType(payType);
		return !e.isNull() && e.isQrCodePay();
	}

	/**
	 * 是否是扫码支付
	 *
	 * @return
	 */
	public boolean isScanCodePay() {
		return isAliScanPay() || isWechatScanPay();
	}

	/**
	 * 是否是扫码支付
	 * @param payType
	 * @return
	 */
	public static boolean isScanCodePay(String payType) {
		KiddPayTypeEnum e = covertByType(payType);
		return !e.isNull() && e.isScanCodePay();
	}

	/**
	 * 是否是银行卡支付
	 * 
	 * @return
	 */
	public boolean isCardPay() {
		return isPospay();
	}

	/**
	 * 转换对应的支付类型为对应的枚举
	 * 
	 * @param payType
	 * @return
	 */
	public static KiddPayTypeEnum covertByType(String payType) {
		if (KiddStringUtils.isBlank(payType)) {
			return PAY_TYPE_NONE;
		}
		for (KiddPayTypeEnum e : values()) {
			if (e.getType().equals(payType)) {
				return e;
			}
		}
		return PAY_TYPE_NONE;
	}

}
