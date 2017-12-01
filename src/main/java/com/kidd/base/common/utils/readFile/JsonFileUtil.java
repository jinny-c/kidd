package com.kidd.base.common.utils.readFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kidd.base.common.serialize.KiddFastJsonUtils;

public class JsonFileUtil {

	private static final Logger log = LoggerFactory.getLogger(JsonFileUtil.class);
	private final static String encoding = "UTF-8";

	public static String readJsonFile(String path, String name) {
		try {
			File file = new File(path + name);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				return readJsonFile(file);
			} else {
				log.error("找不到指定的文件");
			}
		} catch (Exception e) {
			log.error("获取文件出错");
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T readJsonFile(Class<? extends T> clazz, String path,
			String name) {
		try {
			File file = new File(path + name);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				return readJsonFile(clazz, file);
			} else {
				log.error("找不到指定的文件");
			}
		} catch (Exception e) {
			log.error("获取文件出错:",e);
		}
		return null;
	}

	private static <T> T readJsonFile(Class<? extends T> clazz, File configFile) {
		try {
			return KiddFastJsonUtils.toObj(readJsonFile(configFile), clazz);
		} catch (IOException e) {
			log.error("readJsonFile IOException:", e);
		}
		return null;
	}
	
	public static <T> List<T> readJsonListFile(Class<T> clazz, String path,
			String name) {
		try {
			File file = new File(path + name);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				return readJsonListFile(clazz, file);
			} else {
				log.error("找不到指定的文件");
			}
		} catch (Exception e) {
			log.error("获取文件出错:",e);
		}
		return null;
	}
	private static <T> List<T> readJsonListFile(Class<T> clazz, File configFile) {
		try {
			return KiddFastJsonUtils.toListObj(readJsonFile(configFile), clazz);
		} catch (IOException e) {
			log.error("readJsonFile IOException:", e);
		}
		return null;
	}

	private static String readJsonFile(File configFile) throws IOException {
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
}