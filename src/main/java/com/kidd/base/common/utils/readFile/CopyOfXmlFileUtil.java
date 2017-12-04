package com.kidd.base.common.utils.readFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CopyOfXmlFileUtil {

	private static final Logger log = LoggerFactory
			.getLogger(CopyOfXmlFileUtil.class);

	private final static String encoding = "UTF-8";

	public static String readXmlFile(String path, String name) {
		try {
			File file = new File(path + name);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				return readXmlFile(file);
			} else {
				log.error("找不到指定的文件");
			}
		} catch (Exception e) {
			log.error("获取文件出错");
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T readXmlFile(Class<? extends T> clazz, String path,
			String name) {
		try {
			File file = new File(path + name);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				return readXmlFile(clazz, file);
			} else {
				log.error("找不到指定的文件");
			}
		} catch (Exception e) {
			log.error("获取文件出错:", e);
		}
		return null;
	}

	private static <T> T readXmlFile(Class<? extends T> clazz, File configFile) {
		try {
			return parseXml(clazz, readXmlFile(configFile));
		} catch (IOException e) {
			log.error("readXmlFile IOException:", e);
		}
		return null;
	}

	private static String readXmlFile(File configFile) throws IOException {
		StringBuffer resp = new StringBuffer();
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					configFile), encoding);// 考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				resp.append(lineTxt.trim());
			}
			read.close();
		} catch (Exception e) {
			log.error("读取文件内容出错");
			e.printStackTrace();
		}
		return resp.toString();
	}

	@SuppressWarnings("unchecked")
	private static <T> T parseXml(Class<? extends T> clazz, String xmlText) {
		T t = null;
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			t = (T) unmarshaller.unmarshal(new StringReader(xmlText));
			//t = (T) unmarshaller.unmarshal(new ByteArrayInputStream(xmlText.getBytes()));
		} catch (Exception e) {
			log.error("parseXml Exception:", e);
		}
		return t;
	}
	
	public static String convertToXml(Object obj) {  
		String result = null;
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

			StringWriter writer = new StringWriter();
			marshaller.marshal(obj, writer);
			result = writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}