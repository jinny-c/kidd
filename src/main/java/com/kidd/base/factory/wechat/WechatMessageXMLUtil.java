package com.kidd.base.factory.wechat;

import java.io.Writer;

import com.kidd.base.factory.message.dto.WechatMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

/**
 * OXM工具类 【微信消息对象与XML转换】
 *
 * @history
 */
public class WechatMessageXMLUtil {

	private static class CDataXppDriver extends DomDriver {

		private CDataXppDriver(String encoding) {
			super(encoding);
		}

		@Override
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out){

				boolean cdata = false;

				@Override
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
					cdata = clazz == String.class;
				}

				@Override
				public void setValue(String text) {
					super.setValue(text);
				}

				@Override
				protected void writeText(QuickWriter writer, String text) {
					if(cdata){
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					}else {
						writer.write(text);
					}
				}
			};
		}
	}

	private static XStream genXStream(WechatMessage message) {
		XStream xStream = new XStream(new CDataXppDriver("utf-8"));
		xStream.alias("xml", WechatMessage.class);
		xStream.aliasField("ToUserName", WechatMessage.class, "toUserName");
		xStream.aliasField("FromUserName", WechatMessage.class, "fromUserName");
		xStream.aliasField("CreateTime", WechatMessage.class, "createTime");
		xStream.aliasField("MsgType", WechatMessage.class, "msgType");
		xStream.aliasField("Content", WechatMessage.class, "content");
		xStream.aliasField("PicUrl", WechatMessage.class, "picUrl");
		xStream.aliasField("MsgId", WechatMessage.class, "msgId");
		xStream.aliasField("MediaId", WechatMessage.class, "mediaId");
		xStream.aliasField("Event", WechatMessage.class, "event");
		xStream.aliasField("EventKey", WechatMessage.class, "eventKey");
		xStream.aliasField("Format", WechatMessage.class, "format");
		xStream.aliasField("Recognition", WechatMessage.class, "recognition");
		xStream.aliasField("Ticket", WechatMessage.class, "ticket");
		xStream.aliasField("Latitude", WechatMessage.class, "latitude");
		xStream.aliasField("Longitude", WechatMessage.class, "longitude");
		xStream.aliasField("Precision", WechatMessage.class, "precision");
		xStream.aliasField("MsgID", WechatMessage.class, "msgID");
		xStream.aliasField("Status", WechatMessage.class, "status");
		if (message != null && message.getArticleCount() != null && message.getArticleCount() > 0) {
			xStream.aliasField("ArticleCount", WechatMessage.class, "articleCount");
			xStream.aliasField("Articles", WechatMessage.class, "articles");
			xStream.alias("item", WechatMessage.Article.class);
			xStream.aliasField("Title", WechatMessage.Article.class, "title");
			xStream.aliasField("Description", WechatMessage.Article.class, "description");
			xStream.aliasField("PicUrl", WechatMessage.Article.class, "picUrl");
			xStream.aliasField("Url", WechatMessage.Article.class, "url");
		}
		if (message != null && message.getMusic() != null) {
			xStream.alias("Music", WechatMessage.Music.class);
			xStream.aliasField("Title", WechatMessage.Music.class, "title");
			xStream.aliasField("Description", WechatMessage.Music.class, "description");
			xStream.aliasField("MusicUrl", WechatMessage.Music.class, "musicUrl");
			xStream.aliasField("HQMusicUrl", WechatMessage.Music.class, "hqMusicUrl");
		}
		return xStream;
	}

	public static WechatMessage xmlToMessage(String body) {
		return (WechatMessage) genXStream(null).fromXML(body, new WechatMessage());
	}

	public static String messageToXml(WechatMessage message) {
		return genXStream(message).toXML(message);
	}

}
