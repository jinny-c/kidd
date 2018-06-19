
package com.kidd.base.servlet.fiter.wrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kidd.base.common.constant.KiddConstants;
import com.kidd.base.common.enums.KiddSymbolEnum;
import com.kidd.base.http.RequestResponseContext;

/**
 * servlet包装
 * 
 */
public class KiddServletRequestWrapper extends HttpServletRequestWrapper {
	private static final Logger logger = Logger
			.getLogger(KiddServletRequestWrapper.class);

	/**
	 * 请求参数的Map
	 */
	private Map<String, String[]> requestParaMap = null;
	/**
	 * 请求的内容字符串
	 */
	private String requestData = null;
	/**
	 * 请求参数列表
	 */
	private Vector<String> requestParaList = null;

	/**
	 * @param request
	 */
	public KiddServletRequestWrapper(HttpServletRequest request)
			throws UnsupportedEncodingException {
		super(request);
		byte[] reqStr = getServletInputStream(request);
		if (null != reqStr) {
			String contentType = request.getHeader("content-type");
			this.requestData = new String(reqStr, KiddConstants.CHARSET_DEF);
			if ("application/json".equals(contentType)) {
				this.requestParaMap = this.parseRequestJSONString(requestData);
				this.requestParaList = this.parseRequestJSONStringPara(requestData);
			}else{
				this.requestParaMap = this.parseRequestData(requestData);
				this.requestParaList = this.parseRequestPara(requestData);
			}
			this.setAttribute(KiddConstants.ATTR_REQUEST_DATA, this.requestData);
		}
        //重设ThreadLocalMap
        RequestResponseContext.setRequest(this);
	}

	private byte[] getServletInputStream(HttpServletRequest request) {
		byte[] inputBytes = new byte[request.getContentLength()];
		int len = 0;
		int readLen = 0;
		int offset = 0;
		int inputLen = request.getContentLength();
		try {
			while (readLen < inputLen && len >= 0) {
				len = request.getInputStream().read(inputBytes, offset,
						inputLen);
				// 2012.5.21 Wen Tao 这里长度可能是负数，如果到达末尾，和预计接收长度不一致时，可能会造成数据数组问题
				if (len < 0)
					break;
				offset += len;
				readLen += len;
			}
		} catch (IOException e) {
			logger.error(e, e);
			throw new RuntimeException("读取客户端留信息错");
		}

		// 2012.5.12 Wen Tao 根据实际的数据长度检查是否要修改接收的缓冲大小
		if (inputLen != readLen) {
			logger.warn("实际读取的内容长度不一致：" + inputLen + "!=" + readLen);
			byte[] tmp = new byte[readLen];
			System.arraycopy(inputBytes, 0, tmp, 0, readLen);
			inputBytes = tmp;
		}

		return inputBytes;
	}

	/**
	 * 转换请求的内容，将请求信息放入Map中
	 * 
	 * @author lfjiang 2016年4月22日
	 * @param requestData
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private Map<String, String[]> parseRequestData(String requestData)
			throws UnsupportedEncodingException {
		Map<String, String[]> map = new HashMap<String, String[]>();
		StringTokenizer st = new StringTokenizer(requestData,
				KiddSymbolEnum.Ampersand.symbol());
		while (st.hasMoreTokens()) {
			String keyValue = st.nextToken();
			int loc = keyValue.indexOf(KiddSymbolEnum.Equals.symbol());
			String key = keyValue.substring(0, loc);
			String value = keyValue.substring(loc + 1);
			String decodeValue = null;
			try {
				decodeValue = URLDecoder.decode(value,
						KiddConstants.CHARSET_DEF);
			} catch (Exception e) {
				decodeValue = value;
			}
			String[] values = null;
			if (map.containsKey(key)) {
				String[] str = map.get(key);
				String[] newStr = new String[str.length + 1];
				for (int i = 0; i <= str.length; i++) {
					if (i == str.length) {
						newStr[i] = decodeValue;
					} else {
						newStr[i] = str[i];
					}
				}
				values = newStr;
			} else {
				values = new String[] { decodeValue };
			}
			map.put(key, values);
		}
		return map;
	}

	/**
	 * 微信小程序转换请求的内容，将请求信息放入Map中
	 *
	 * @author JerryWang
	 * @param requestData
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private Map<String, String[]> parseRequestJSONString(String requestData)
			throws UnsupportedEncodingException {
		Map<String, String[]> map = new HashMap<String, String[]>();

		JSONObject jsonObject = JSON.parseObject(requestData);
		if (jsonObject.isEmpty()) {
			return map;
		}
		Set<String> keySet = jsonObject.keySet();
		Iterator it = keySet.iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			String value = jsonObject.getString(key);
			String decodeValue = null;
			try {
				decodeValue = URLDecoder.decode(value, KiddConstants.CHARSET_DEF);
			} catch (Exception e) {
				decodeValue = value;
			}
			String[] values = null;
			if (map.containsKey(key)) {
				String[] str = map.get(key);
				String[] newStr = new String[str.length + 1];
				for (int i = 0; i <= str.length; i++) {
					if (i == str.length) {
						newStr[i] = decodeValue;
					} else {
						newStr[i] = str[i];
					}
				}
				values = newStr;
			} else {
				values = new String[] { decodeValue };
			}
			map.put(key, values);
		}
		return map;
	}

	/**
	 * 转换请求内容,将请求信息放入Vector中
	 * 
	 * @author lfjiang 2016年4月22日
	 * @param requestData
	 * @return
	 */
	private Vector<String> parseRequestPara(String requestData) {
		Vector<String> list = new Vector<String>();

		StringTokenizer st = new StringTokenizer(requestData, "&");
		while (st.hasMoreTokens()) {
			String keyValue = st.nextToken();
			int loc = keyValue.indexOf("=");
			String key = keyValue.substring(0, loc);
			list.add(key);
		}
		return list;
	}

	/**
	 * 转换请求内容,将请求信息放入Vector中
	 * 微信小程序适用
	 *
	 * @author JerryWang
	 * @param requestData
	 * @return
	 */
	private Vector<String> parseRequestJSONStringPara(String requestData) {
		Vector<String> list = new Vector<String>();

		JSONObject jsonObject = JSON.parseObject(requestData);
		if (jsonObject.isEmpty()) {
			return list;
		}
		Set<String> keySet = jsonObject.keySet();
		Iterator it = keySet.iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			list.add(key);
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
	 */
	@Override
	public String getParameter(String name) {
		if (!requestParaMap.containsKey(name)) {
			return null;
		}
		String s = this.requestParaMap.get(name)[0];
		if (s != null)
			s = s.replaceAll(KiddSymbolEnum.WRAP.symbol(),
					KiddSymbolEnum.Blank.symbol()).replaceAll(
					KiddSymbolEnum.ENTER.symbol(),
					KiddSymbolEnum.Blank.symbol());
		return s;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletRequestWrapper#getParameterMap()
	 */
	@Override
	public Map<String, String[]> getParameterMap() {
		return requestParaMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletRequestWrapper#getParameterNames()
	 */
	@Override
	public Enumeration<String> getParameterNames() {
		return requestParaList.elements();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletRequestWrapper#getParameterValues(java.lang.String)
	 */
	@Override
	public String[] getParameterValues(String name) {
		return requestParaMap.get(name);
	}

	public String getRequestData() {
		return requestData;
	}
}
