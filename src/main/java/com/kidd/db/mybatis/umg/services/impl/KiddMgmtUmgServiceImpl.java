package com.kidd.db.mybatis.umg.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kidd.base.common.exception.KiddServiceException;
import com.kidd.base.common.utils.KiddDateUtils;
import com.kidd.base.common.utils.KiddObjectUtils;
import com.kidd.db.mybatis.umg.mapper.KiddMerchRecommendInfoMapper;
import com.kidd.db.mybatis.umg.mapper.KiddUserInfoMapper;
import com.kidd.db.mybatis.umg.mapper.domain.KiddMerchRecommendInfo;
import com.kidd.db.mybatis.umg.mapper.domain.KiddUserInfo;
import com.kidd.db.mybatis.umg.services.IKiddMgmtUmgService;
import com.kidd.db.mybatis.umg.services.bean.KiddMerchRecommendBean;
import com.kidd.db.mybatis.umg.services.bean.KiddUserInfoBean;

@Service(value = "kiddMgmtUmgService")
public class KiddMgmtUmgServiceImpl implements IKiddMgmtUmgService {
	private static Logger log = LoggerFactory.getLogger(KiddMgmtUmgServiceImpl.class);
    @Autowired
    private KiddMerchRecommendInfoMapper merchRecommendInfoMapper;
    @Autowired
    private KiddUserInfoMapper userInfoMapper;
    
	@Override
	public Integer queryCount() {
		// TODO Auto-generated method stub
		log.info("queryCount start");
		//merchRecommendInfoMapper.selectAll();
		try {
			return merchRecommendInfoMapper.selectAll();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("query count exception:",e);
		}
		return 0;
	}

	@Override
	public KiddMerchRecommendBean queryByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		log.info("queryByPrimaryKey start,id={}",id);
		KiddMerchRecommendInfo info = merchRecommendInfoMapper.selectByPrimaryKey(id);
		if (null == info) {
			return null;
		}
		return KiddObjectUtils.copyProperty(KiddMerchRecommendBean.class, info);
	}

	@Override
	public List<KiddUserInfoBean> queryUserInfoByCondition(KiddUserInfoBean reqBean) {
		// TODO Auto-generated method stub
		log.info("queryUserInfoByCondition start,reqBean={}",reqBean);
		//userInfoMapper.selectByPaging(KiddObjectUtils.copyProperty(KiddUserInfo.class, reqBean));
		return null;
	}

	@Override
	public KiddUserInfoBean queryUseInfoByPrimaryKey(KiddUserInfoBean reqBean) {
		// TODO Auto-generated method stub
		log.info("queryUseInfoByPrimaryKey start,reqBean={}",reqBean);
		if (null == reqBean) {
			return null;
		}
		KiddUserInfo uInfo = userInfoMapper.selectByCondition(KiddObjectUtils
				.copyProperty(KiddUserInfo.class, reqBean));
		
		//userInfoMapper.selectByPaging(KiddObjectUtils.copyProperty(KiddUserInfo.class, reqBean));
		
		//userInfoMapper.countRecordsByPaging(KiddObjectUtils.copyProperty(KiddUserInfo.class, reqBean));
		log.info("queryUseInfoByPrimaryKey start,reqBean={}",uInfo);
		return null;
	}

	@Override
	public void modifyUserInfo(KiddUserInfoBean reqBean)
			throws KiddServiceException {
		// TODO Auto-generated method stub
		log.info("modifyUserInfo start,reqBean={}",reqBean);
		if (null == reqBean) {
			throw new KiddServiceException("","");
		}
		KiddUserInfo reqInfo = new KiddUserInfo();
		KiddObjectUtils.copyProperty(reqInfo, reqBean);
		
		KiddUserInfo info = userInfoMapper.selectByUserName(reqBean
				.getUserName());
		if (null == info) {
			log.info("insert start");
			reqInfo.setUserId(KiddDateUtils.getCurrentDate());
			userInfoMapper.insertSelective(reqInfo);
		} else {
			log.info("update start");
			reqInfo.setUserId(info.getUserId());
			reqInfo.setUserName(info.getUserName());
			reqInfo.setUpdTime(KiddDateUtils.getCurrentDate());
			userInfoMapper.updateByPrimaryKeySelective(reqInfo);
		}
	}
	
}
