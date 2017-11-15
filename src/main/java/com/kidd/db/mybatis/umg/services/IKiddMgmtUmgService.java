package com.kidd.db.mybatis.umg.services;

import java.util.List;

import com.kidd.base.common.exception.KiddServiceException;
import com.kidd.db.mybatis.umg.services.bean.KiddMerchRecommendBean;
import com.kidd.db.mybatis.umg.services.bean.KiddUserInfoBean;


public interface IKiddMgmtUmgService {
	
	/**
	 * 查询总数
	 * @return
	 */
    Integer queryCount();
    
    /**
     * 根据主键查询
     * @param id
     * @return
     */
    KiddMerchRecommendBean queryByPrimaryKey(Integer id);
    
    /**
     * 查询用户信息
     * @param reqBean
     * @return
     */
    List<KiddUserInfoBean> queryUserInfoByCondition(KiddUserInfoBean reqBean);
    
    /**
     * 查询用户信息
     * @param reqBean
     * @return
     */
    KiddUserInfoBean queryUseInfoByPrimaryKey(KiddUserInfoBean reqBean);
    
    /**
     * 用户表修改操作
     * @param reqBean
     * @throws KiddServiceException
     */
    void modifyUserInfo(KiddUserInfoBean reqBean) throws KiddServiceException;
}
