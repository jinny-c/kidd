package com.kidd.base.factory.wechat.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.kidd.base.common.utils.ToStringUtils;

/**
 * 微信公众号菜单
 *
 * @history
 */
public class KiddPubNoMenuDTO implements Serializable {

	private static final long serialVersionUID = -756496923850016753L;
	private static final String MAIN_MENU = "1";
	private static final String MENU_TYPE_CLICK = "click";
	private static final String MENU_TYPE_VIEW = "view";
	private static final String MENU_TYPE_SCANCODE_PUSH = "scancode_push";
	private static final String MENU_TYPE_SUB_BUTTON = "sub_button";
	private static final String SHORT_URL = "1";

	private Integer menuId;

	private String mainMenuFlag;

	@JSONField(name = "type")
	private String menuType;

	private Integer menuParentId;

	@JSONField(name = "name")
	private String menuName;

	@JSONField(name = "key")
	private String menuKey;

	@JSONField(name = "url")
	private String menuValue;

	private String shortUrlFlag;

	private String menuSortNo;

	private Date createTime;

	private Date uptTime;

	private List<KiddPubNoMenuDTO> sub_button;
	private List<KiddPubNoMenuDTO> button;

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMainMenuFlag() {
		return mainMenuFlag;
	}

	public void setMainMenuFlag(String mainMenuFlag) {
		this.mainMenuFlag = mainMenuFlag;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public Integer getMenuParentId() {
		return menuParentId;
	}

	public void setMenuParentId(Integer menuParentId) {
		this.menuParentId = menuParentId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuKey() {
		return menuKey;
	}

	public void setMenuKey(String menuKey) {
		this.menuKey = menuKey;
	}

	public String getMenuValue() {
		return menuValue;
	}

	public void setMenuValue(String menuValue) {
		this.menuValue = menuValue;
	}

	public String getShortUrlFlag() {
		return shortUrlFlag;
	}

	public void setShortUrlFlag(String shortUrlFlag) {
		this.shortUrlFlag = shortUrlFlag;
	}

	public String getMenuSortNo() {
		return menuSortNo;
	}

	public void setMenuSortNo(String menuSortNo) {
		this.menuSortNo = menuSortNo;
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

	public List<KiddPubNoMenuDTO> getSub_button() {
		return sub_button;
	}

	public void setSub_button(List<KiddPubNoMenuDTO> sub_button) {
		this.sub_button = sub_button;
	}

	public List<KiddPubNoMenuDTO> getButton() {
		return button;
	}

	public void setButton(List<KiddPubNoMenuDTO> button) {
		this.button = button;
	}

	@JSONField(serialize=false)
	public boolean isMainMenu(){
		return MAIN_MENU.equals(mainMenuFlag);
	}

	@JSONField(serialize=false)
	public boolean isViewType(){
		return MENU_TYPE_VIEW.equals(menuType);
	}

	@JSONField(serialize=false)
	public boolean isEventType(){
		return MENU_TYPE_CLICK.equals(menuType) ||
				MENU_TYPE_SCANCODE_PUSH.equals(menuType);
	}

	@JSONField(serialize=false)
	public boolean isSubButtonType(){
		return MENU_TYPE_SUB_BUTTON.equals(menuType);
	}

	@JSONField(serialize=false)
	public boolean isShortUrl(){
		return SHORT_URL.equals(shortUrlFlag);
	}

	@Override
	public String toString() {
		ToStringUtils builder = new ToStringUtils(this);
		builder.add("menuId", menuId).add("mainMenuFlag", mainMenuFlag)
				.add("menuType", menuType).add("menuParentId", menuParentId)
				.add("menuName", menuName).add("menuKey", menuKey)
				.add("menuValue", menuValue).add("shortUrlFlag", shortUrlFlag)
				.add("menuSortNo", menuSortNo).add("createTime", createTime)
				.add("uptTime", uptTime).add("sub_button", sub_button)
				.add("button", button);
		return builder.toString();
	}

}
