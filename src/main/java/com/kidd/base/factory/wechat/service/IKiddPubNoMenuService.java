package com.kidd.base.factory.wechat.service;

import java.util.List;

import com.kidd.base.factory.wechat.dto.KiddPubNoMenuDTO;


/**
 * 微信公众号菜单服务
 *
 * @history
 */
public interface IKiddPubNoMenuService {

	/**
	 * 查询所有的菜单
	 *
	 * @return
	 */
	List<KiddPubNoMenuDTO> queryAllMenu();

	/**
	 * 根据菜单ID集合查询菜单
	 *
	 * @return
	 */
	List<KiddPubNoMenuDTO> queryAllMenuByIds(List<Integer> menuIds);

	/**
	 * 生成JSON格式菜单树
	 *
	 * @param pubId
	 * @return
	 */
	String generateJsonMenuTree(String pubId);

}