package com.kidd.base.common;

import com.kidd.base.common.exception.KiddControllerException;
import com.kidd.base.common.params.valid.KiddValidResp;
import com.kidd.base.common.params.valid.VerifyControllerUtil;

/**
 * 请求参数验证类
 * @history
 */
public abstract class KiddBaseReqDto {

	/**
	 * 参数验证
	 * 
	 * @return
	 */
	public abstract KiddValidResp valid();

    /**
     * 验证参数的正确性 【该方法由未使用@JiddSecureAnno注解的Controller手动调用】
     *
     */
    public void paramsValid() throws KiddControllerException {
        KiddValidResp valid = VerifyControllerUtil.validateReqDto(this);
        if (valid != null && !valid.isSucc()) {
            throw new KiddControllerException(valid.getErrCode(), valid.getErrMsg());
        }
    }
}
