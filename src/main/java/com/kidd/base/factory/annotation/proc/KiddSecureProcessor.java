package com.kidd.base.factory.annotation.proc;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import com.kidd.base.common.exception.KiddControllerException;
import com.kidd.base.common.secure.SecureManager;
import com.kidd.base.common.secure.utils.KiddDESedeUtils;
import com.kidd.base.common.utils.KiddStringUtils;
import com.kidd.base.factory.annotation.KiddDecrAnno;
import com.kidd.base.factory.annotation.KiddEncrAnno;
import com.kidd.base.factory.annotation.KiddSecureAnno;
/**
 * 处理器
 * 
 * @history
 */
@Component
public class KiddSecureProcessor implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(KiddSecureProcessor.class);


/*	@Autowired
	private IUserLogService userLogService;

	public UserKeyBean getUserKey(String version, String terminalUserID)
			throws KiddGlobalValidException {}

	*//**
	 * 获取头信息
	 * 
	 * @return
	 *//*
	public KiddHttpHeader getHeader() {}*/

	public boolean isSecure(MethodParameter parameter) {
		return parameter.getParameterAnnotation(KiddSecureAnno.class) != null;
	}

	public boolean isSecure(Class<? extends Object> clazz) {
		return clazz.isAnnotationPresent(KiddSecureAnno.class);
	}

	/**
	 * 是否需要加密
	 * 
	 * @param filed
	 * @return
	 */
	public boolean isEncr(Field filed) {
		return filed != null && filed.isAnnotationPresent(KiddEncrAnno.class);
	}

	/**
	 * 是否需要解密
	 * 
	 * @param filed
	 * @return
	 */
	public boolean isDecr(Field filed) {
		return filed != null && filed.isAnnotationPresent(KiddDecrAnno.class);
	}

	/**
	 * 客户端数据加密
	 * 
	 * @param userKey
	 * @param plain
	 * @param version
	 * @param sessionId
	 * @return
	 * @throws ActionException
	 */
	public String encrypt(String key, String fieldName, String plain,
			String version, String sessionId) throws KiddControllerException {
		logger.info("encrypt start------------------");
		if (KiddStringUtils.isBlank(plain)) {
			return "";
		}
		return encryptDes(key, plain);
	}

	/***
	 * 客户端数据解密
	 * 
	 * @param userKey
	 * @param cipherText
	 * @param version
	 * @param sessionId
	 * @return
	 * @throws ActionException
	 */
	public String decrypt(String key, String cipherText,
			String version, String sessionId) throws KiddControllerException {
		logger.info("decrypt start------------------");
		if (KiddStringUtils.isBlank(cipherText)) {
			return "";
		}
		return decryptDes(key, cipherText);
	}

	private String encryptDes(String key, String data)
			throws KiddControllerException {
		if("key".equals(key)){
			return KiddStringUtils.join("enc_",data);
		}
		//des
		//data = new SecureManager().Des(data, 1,key);
		//3des
		data = KiddDESedeUtils.doEncr(key, data);
		
		data = data != null ? data.trim() : data;
		return data;
	}
	private String decryptDes(String key, String data)
			throws KiddControllerException {
		return KiddStringUtils.join("dec_",data);
		/*data = KiddStringUtils.trimStr(new SecureManager().Des(data, 0,
				key));
		return data;*/
	}
	
	public static void main(String[] args) {
		//des加解密 key[16]位
		//加密
		String encStr = new SecureManager().Des("123123", 1, "50d43ea2a4e66786");
		System.out.println("---------"+encStr);
		//解密
		String desStr = new SecureManager().Des(encStr, 0, "50d43ea2a4e66786");
		System.out.println("---------"+desStr);
		
		
	}
	
}