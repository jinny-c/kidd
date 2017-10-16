package com.kidd.db.mybatis.umg.services;

import com.kidd.db.mybatis.umg.services.bean.KiddMerchRecommendBean;


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
}
