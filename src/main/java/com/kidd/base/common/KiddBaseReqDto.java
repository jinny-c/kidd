package com.kidd.base.common;

import com.kidd.base.exception.KiddControllerException;
import com.kidd.base.params.valid.KiddValidResp;
import com.kidd.base.params.valid.VerifyControllerUtil;

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
     * @throws com.micro.app.commcon.ControllerException
     */
    public void paramsValid() throws KiddControllerException {
        KiddValidResp valid = VerifyControllerUtil.validateReqDto(this);
        if (valid != null && !valid.isSucc()) {
            throw new KiddControllerException(valid.getErrCode(), valid.getErrMsg());
        }
    }
}
