package com.kidd.base.factory.wechat.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.kidd.base.factory.wechat.dto.KiddPubNoMenuDTO;
import com.kidd.base.factory.wechat.service.IKiddPubNoMenuService;
import com.kidd.base.http.httpclient.KiddHttpBuilder;
import com.kidd.base.http.httpclient.KiddHttpExecutor;


/**
 * 微信公众号菜单服务
 *
 */
@Service(value = "kiddPubNoMenuService")
@Transactional
public class KiddPubNoMenuServiceImpl implements IKiddPubNoMenuService {
    /** SLF4J */
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(KiddPubNoMenuServiceImpl.class);

	//@Autowired
	//private MicroPubNoMenuConfigMapper microPubNoMenuConfigMapper;
	//@Autowired
    //private MicroPubNoMenuMapper microPubNoMenuMapper;

	/** 微信长链接转短链接 */
	@Value("${wap.long2short.url}")
	private String long2shortUrl;

	@Override
	public List<KiddPubNoMenuDTO> queryAllMenu() {
		List<KiddPubNoMenuDTO> pubNoMenuDtoList = new ArrayList<KiddPubNoMenuDTO>();
		/*List<MicroPubNoMenu> pubNoMenuList = microPubNoMenuMapper.selectAllMenu();
		for(MicroPubNoMenu pubNoMenu: pubNoMenuList){
			KiddPubNoMenuDTO pubNoMenuDTO = new KiddPubNoMenuDTO();
			MicroObjectUtils.copyProperties(pubNoMenuDTO, pubNoMenu);
			pubNoMenuDtoList.add(pubNoMenuDTO);
		}*/
		return pubNoMenuDtoList;
	}

	@Override
	public List<KiddPubNoMenuDTO> queryAllMenuByIds(List<Integer> menuIds) {
		List<KiddPubNoMenuDTO> pubNoMenuDtoList = new ArrayList<KiddPubNoMenuDTO>();
		KiddPubNoMenuDTO pubNoMenuDTO = new KiddPubNoMenuDTO();
		/*List<MicroPubNoMenu> pubNoMenuList = microPubNoMenuMapper.selectAllMenuByIds(menuIds);
		for(MicroPubNoMenu pubNoMenu: pubNoMenuList){
			KiddPubNoMenuDTO pubNoMenuDTO = new KiddPubNoMenuDTO();
			MicroObjectUtils.copyProperties(pubNoMenuDTO, pubNoMenu);
			pubNoMenuDtoList.add(pubNoMenuDTO);
		}*/
		return pubNoMenuDtoList;
	}

	@Override
	public String generateJsonMenuTree(String pubId) {
		
		List<KiddPubNoMenuDTO> buttonList = new ArrayList<KiddPubNoMenuDTO>();
		Map<Integer, KiddPubNoMenuDTO> parentButtonMap = new HashMap<Integer, KiddPubNoMenuDTO>();
		List<KiddPubNoMenuDTO> menuList = this.queryAllMenuByIds(null);
		KiddHttpExecutor executor = getHttpExecutor();
		for(KiddPubNoMenuDTO menu: menuList){
			KiddPubNoMenuDTO button = new KiddPubNoMenuDTO();
			if(menu.isViewType()){
				button.setMenuType(menu.getMenuType());
				button.setMenuName(menu.getMenuName());
				button.setMenuValue(menu.getMenuValue());
				if (menu.isShortUrl()) {
					// 长连接转短链接
					button.setMenuValue(long2short(executor, pubId, menu.getMenuValue()));
				}
			} else if (menu.isEventType()){
				button.setMenuType(menu.getMenuType());
				button.setMenuName(menu.getMenuName());
				button.setMenuKey(menu.getMenuKey());
			} else if (menu.isSubButtonType()){
				button.setMenuName(menu.getMenuName());
				button.setSub_button(new ArrayList<KiddPubNoMenuDTO>());
				parentButtonMap.put(menu.getMenuId(), button);
			}
			if(menu.isMainMenu()){
				buttonList.add(button);
			} else {
				KiddPubNoMenuDTO parentMenu = parentButtonMap.get(menu.getMenuParentId());
				// 处理父级菜单关闭子级菜单未关闭的情况
				if (parentMenu == null) {
					continue;
				}
				parentMenu.getSub_button().add(button);
			}
		}
		KiddPubNoMenuDTO rootMenu = new KiddPubNoMenuDTO();
		rootMenu.setButton(buttonList);
		return JSON.toJSONString(rootMenu);
	}

	private KiddHttpExecutor getHttpExecutor() {
		try {
			return KiddHttpBuilder.create()
					.loadTimeOut(3000, 6000)
					.loadUrl(long2shortUrl)
					.loadIgnoreSSL()
					.build();
		} catch (Exception e) {
			log.error("create http executor fail, url={}", long2shortUrl, e);
		}
		return null;
	}

	private String long2short(KiddHttpExecutor executor, String pubId, String url) {
		if (executor == null) {
			return url;
		}
		Map<String, String> bodyValueMap = new HashMap<String, String>();
		bodyValueMap.put("pubId", pubId);
		bodyValueMap.put("url", url + "?pubId=" + pubId);
		try {
			return executor.doPost(bodyValueMap, null);
		} catch (Exception e) {
			log.error("HTTP请求异常(long2short)", e);
		}
		return url;
	}
}
