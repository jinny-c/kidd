package com.kidd.base.factory.message.dto;

import java.util.ArrayList;
import java.util.List;

import com.kidd.base.common.constant.KiddConstants;

/**
 * 微信消息
 *
 * @history
 */
public class WechatMessage implements IPubNumMessage {
	/**
	 * 接收人
	 */
	private String toUserName;

	/**
	 * 发送人
	 */
	private String fromUserName;

	/**
	 * 上下行创建时间（System.currentTimeMillis() / 1000）
	 */
	private Integer createTime;

	/**
	 * 上行消息类型
	 */
	private String msgType;

	/**
	 * 文本上下行
	 */
	private String content;

	/**
	 * 上行消息id
	 */
	private Long msgId;

	/**
	 * 图文下行专用
	 */
	private Integer articleCount;

	/**
	 * 事件上行专用
	 */
	private String event;

	/**
	 * 事件上行专用
	 */
	private String eventKey;

	/**
	 * 图片上行专用
	 */
	private String picUrl;

	/**
	 * 地理信息上行专用
	 */
	private Double locationX;

	/**
	 * 地理信息上行专用
	 */
	private Double locationY;

	/**
	 * 地理信息上行专用
	 */
	private Integer scale;

	/**
	 * 地理信息上行专用
	 */
	private String label;

	/**
	 * 链接信息上行专用
	 */
	private String url;

	/**
	 * 二维码图片
	 */
	private String ticket;

	/**
	 * 地理位置
	 */
	private String latitude;

	private String longitude;

	private String precision;

	private String MenuId;

	/**
	 * 模板消息
	 */
	private Long msgID;

	private String status;

	/**
	 * 媒体Id
	 */
	private String mediaId;

	private String format;

	private String recognition;

	/**
	 * 图文下行专用
	 */
	private List<Article> articles;

	/**
	 * 音乐下行专用
	 */
	private Music music;


	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public Integer getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(Integer articleCount) {
		this.articleCount = articleCount;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Double getLocationX() {
		return locationX;
	}

	public void setLocationX(Double locationX) {
		this.locationX = locationX;
	}

	public Double getLocationY() {
		return locationY;
	}

	public void setLocationY(Double locationY) {
		this.locationY = locationY;
	}

	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getMenuId() {
		return MenuId;
	}

	public void setMenuId(String menuId) {
		MenuId = menuId;
	}

	public Long getMsgID() {
		return msgID;
	}

	public void setMsgID(Long msgID) {
		this.msgID = msgID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

	public void addArticle(Article article) {
		if (this.getArticles() == null) {
			this.setArticles(new ArrayList<Article>());
			this.setArticleCount(0);
		}
		this.getArticles().add(article);
		this.setArticleCount(this.getArticleCount() + 1);
	}

	public static class Article {
		private String title;
		private String description;
		private String picUrl;
		private String url;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getPicUrl() {
			return picUrl;
		}

		public void setPicUrl(String picUrl) {
			this.picUrl = picUrl;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

	}

	public static class Music {
		private String title;
		private String description;
		private String musicUrl;
		private String hqMusicUrl;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getMusicUrl() {
			return musicUrl;
		}

		public void setMusicUrl(String musicUrl) {
			this.musicUrl = musicUrl;
		}

		public String getHqMusicUrl() {
			return hqMusicUrl;
		}

		public void setHqMusicUrl(String hqMusicUrl) {
			this.hqMusicUrl = hqMusicUrl;
		}
	}


	@Override
	public String getWechatMsgType() {
		if (KiddConstants.MSG_TYPE_CLICK.equals(getEvent())) {
			return KiddConstants.MSG_TYPE_CLICK;
		}

		if (KiddConstants.MSG_TYPE_LOCATION.equals(getEvent())) {
			return KiddConstants.MSG_TYPE_LOCATION;
		}

		if (KiddConstants.MSG_TYPE_SUBSCRIBE.equals(getEvent())) {
			return KiddConstants.MSG_TYPE_SUBSCRIBE;
		}
		return KiddConstants.MSG_TYPE_TEXT;
	}

	@Override
	public String getWechatMsgKey() {
		if (KiddConstants.MSG_TYPE_TEXT.equals(getWechatMsgType())) {
			return getContent();
		}
		return getEventKey();
	}

	@Override
	public String getWechatOpenId() {
		return getFromUserName();
	}

	@Override
	public String getWechatMsgBody() {
		return "";
	}

	@Override
	public String getWechatPubId() {
		return getToUserName();
	}
}