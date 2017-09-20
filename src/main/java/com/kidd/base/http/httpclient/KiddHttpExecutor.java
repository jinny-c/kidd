package com.kidd.base.http.httpclient;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.kidd.base.enums.KiddSymbolEnum;
import com.kidd.base.serialize.KiddObjectTypeUtils;
import com.kidd.base.serialize.KiddSerialTypeEnum;
import com.kidd.base.utils.KiddStringUtils;

public class KiddHttpExecutor {
	private static final Logger logger = Logger
			.getLogger(KiddHttpExecutor.class);

	/** 通讯编码，默认:UTF-8 **/
	private Charset charset = KiddHttpConstants.DEF_CHARSET;
	/** 通讯URL，全局共享 **/
	private final String requestUrl;
	/** http请求配置 **/
	private RequestConfig requestConfig;
	/** http client **/
	private CloseableHttpClient httpClient;

	/**
	 * @param charset
	 * @param url
	 * @param httpClient
	 * @param requestConfig
	 */
	KiddHttpExecutor(Charset charset, String url,
			CloseableHttpClient httpClient, RequestConfig requestConfig) {
		this.charset = charset;
		this.requestUrl = url;
		this.requestConfig = requestConfig;
		this.httpClient = httpClient;
	}

	/**
	 * http get请求[自动拼接bodyMap的参数到URL：url?k1=v1&k2=v2],[自动封装成响应对象]
	 * 
	 * @param bodyMap
	 * @param headMap
	 * @param serial
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> T doGet(Map<String, String> bodyMap,
			Map<String, String> headMap, KiddSerialTypeEnum serial,
			Class<? extends T> clazz) throws Exception {
		validate(serial, clazz);
		String resp = this.doGet(bodyMap, headMap);
		if (KiddStringUtils.isBlank(resp)) {
			return null;
		}
		return serial.toObj(resp, clazz);
	}

	/**
	 * http get请求[自动拼接bodyMap的参数到URL：url?body],[自动封装成响应对象]
	 * 
	 * @param body
	 * @param headMap
	 * @param serial
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> T doGet(String body, Map<String, String> headMap,
	                   KiddSerialTypeEnum serial, Class<? extends T> clazz)
			throws Exception {
		validate(serial, clazz);
		String resp = this.doGet(body, headMap);
		if (KiddStringUtils.isBlank(resp)) {
			return null;
		}
		return serial.toObj(resp, clazz);
	}

	/**
	 * http get请求[无拼接，整个URL必须为queryString],[自动封装成响应对象]
	 * 
	 * @param headMap
	 * @param serial
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> T doGet(Map<String, String> headMap,
	                   KiddSerialTypeEnum serial, Class<? extends T> clazz)
			throws Exception {
		validate(serial, clazz);
		String resp = this.doGet(headMap);
		if (KiddStringUtils.isBlank(resp)) {
			return null;
		}
		return serial.toObj(resp, clazz);
	}

	/**
	 * http get请求[自动拼接bodyMap的参数到URL：url?k1=v1&k2=v2]
	 * 
	 * @param bodyMap
	 * @param headMap
	 * @return
	 * @throws Exception
	 */
	public String doGet(Map<String, String> bodyMap, Map<String, String> headMap)
			throws Exception {
		return doGetWithUrl(requestUrl, bodyMap, headMap);
	}

	/**
	 * http get请求[自动拼接bodyMap的参数到URL：url?k1=v1&k2=v2]，适用URL动态
	 *
	 * @param url
	 * @param bodyMap
	 * @param headMap
	 * @return
	 * @throws Exception
	 */
	public String doGetWithUrl(String url, Map<String, String> bodyMap, Map<String, String> headMap)
			throws Exception {
		StringBuilder body = new StringBuilder();
		int count = 0;
		for (String key : bodyMap.keySet()) {
			if (KiddStringUtils.isBlank(key)) {
				continue;
			}
			body.append(count > 0 ? KiddSymbolEnum.Ampersand.symbol()
					: KiddSymbolEnum.Blank.symbol());
			body.append(key).append(KiddSymbolEnum.Equals.symbol())
					.append(bodyMap.get(key));
			count ++;
		}
		return doGetWithUrl(url, body.toString(), headMap);
	}

	/**
	 * http get请求[自动拼接bodyMap的参数到URL：url?body]，适用URL动态
	 *
	 * @param url
	 * @param body
	 * @param headMap
	 * @return
	 * @throws Exception
	 */
	public String doGetWithUrl(String url, String body, Map<String, String> headMap)
			throws Exception {
		validate(url, body);
		final String realUrl = KiddStringUtils.join(KiddSymbolEnum.QuestionCode, url, body);
		logger.debug("realUrl: " + realUrl);
		return doReq(new HttpGet(realUrl), headMap);
	}

	/**
	 * http get请求[自动拼接bodyMap的参数到URL：url?body]
	 *
	 * @param body
	 * @param headMap
	 * @return
	 * @throws Exception
	 */
	public String doGet(String body, Map<String, String> headMap)
			throws Exception {
		return doGetWithUrl(requestUrl, body, headMap);
	}

	/**
	 * http get请求[无拼接，整个URL必须为queryString]
	 * 
	 * @param headMap
	 * @return
	 * @throws Exception
	 */
	public String doGet(Map<String, String> headMap) throws Exception {
		validate(requestUrl);
		return doReq(new HttpGet(requestUrl), headMap);
	}

	/**
	 * http get请求[无拼接，整个URL必须为queryString]，适用动态Url
	 *
	 * @param url
	 * @param headMap
	 * @return
	 * @throws Exception
	 */
	public String doGetWithUrl(String url, Map<String, String> headMap) throws Exception {
		validate(url);
		return doReq(new HttpGet(url), headMap);
	}

	/**
	 * http post请求[封装body到post请求],[自动封装成响应对象]
	 * 
	 * @param body
	 * @param headMap
	 * @param serial
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> T doPost(String body, Map<String, String> headMap,
	                    KiddSerialTypeEnum serial, Class<? extends T> clazz)
			throws Exception {
		validate(serial, clazz);
		String resp = this.doPost(body, headMap);
		if (KiddStringUtils.isBlank(resp)) {
			return null;
		}
		return serial.toObj(resp, clazz);
	}

	/**
	 * http post请求[封装k-v到post请求],[自动封装成响应对象]
	 * 
	 * @param bodyMap
	 * @param headMap
	 * @param serial
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> T doPost(Map<String, String> bodyMap,
			Map<String, String> headMap, KiddSerialTypeEnum serial,
			Class<? extends T> clazz) throws Exception {
		validate(serial, clazz);
		String resp = this.doPost(bodyMap, headMap);
		if (KiddStringUtils.isBlank(resp)) {
			return null;
		}
		return serial.toObj(resp, clazz);
	}

	/**
	 * http post请求[封装k-v到post请求]
	 * 
	 * @param bodyMap
	 * @param headMap
	 * @return
	 * @throws Exception
	 */
	public String doPost(Map<String, String> bodyMap,
			Map<String, String> headMap) throws Exception {
		validate(requestUrl, bodyMap);
		return doPost(requestUrl, new UrlEncodedFormEntity(convert2PostMap(bodyMap),
				charset), headMap);
	}

	/**
	 * http post请求[封装k-v到post请求]，适用动态Url
	 *
	 * @param bodyMap
	 * @param headMap
	 * @return
	 * @throws Exception
	 */
	public String doPostWithUrl(String url, Map<String, String> bodyMap,
	                     Map<String, String> headMap) throws Exception {
		validate(url, bodyMap);
		return doPost(url, new UrlEncodedFormEntity(convert2PostMap(bodyMap),
				charset), headMap);
	}

	/**
	 * http post请求[封装body到post请求]
	 * 
	 * @param body
	 * @param headMap
	 * @return
	 * @throws Exception
	 */
	public String doPost(String body, Map<String, String> headMap)
			throws Exception {
		validate(requestUrl, body);
		return doPost(requestUrl, new StringEntity(body, charset), headMap);
	}

	/**
	 * http post请求，适用动态Url
	 *
	 * @param url
	 * @param headMap
	 * @return
	 * @throws Exception
	 */
	public String doPostWithUrl(String url, String body, Map<String, String> headMap)
			throws Exception {
		validate(url, body);
		return doPost(url, new StringEntity(body, charset), headMap);
	}

	private String doPost(String url, HttpEntity entity, Map<String, String> headMap)
			throws Exception {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		httpPost.setEntity(entity);
		return doReq(httpPost, headMap);
	}

	/**
	 * 将传入的键/值对参数转换为NameValuePair参数集
	 * 
	 * @param bodyMap
	 * @return
	 */
	private List<NameValuePair> convert2PostMap(Map<String, String> bodyMap) {
		if (bodyMap == null || bodyMap.size() == 0) {
			return null;
		}
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (String key : bodyMap.keySet()) {
			params.add(new BasicNameValuePair(key, bodyMap.get(key)));
		}
		return params;
	}

	/**
	 * 发送http请求
	 * 
	 * @param request
	 * @param headMap
	 * @return
	 * @throws Exception
	 */
	private String doReq(HttpUriRequest request, Map<String, String> headMap)
			throws Exception {
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		try {
			if (headMap != null && !headMap.isEmpty()) {
				for (String key : headMap.keySet()) {
					request.addHeader(key, headMap.get(key));
				}
			}
			// 默认会创建一个BasicHttpContext
			response = httpClient.execute(request);
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {
				entity = response.getEntity();
				return EntityUtils.toString(entity, charset);
			}
			logger.error("http response code is not 200,status code:" + status);
		} catch (Exception e) {
			logger.error("do http request fail", e);
			throw (e);
		} finally {
			if (null != response) {
				try {
					response.close();
					if (null != entity) {
						EntityUtils.consume(response.getEntity());
					}
				} catch (IOException e) {
					logger.error("consumer response fail", e);
				}
			}
			this.release();
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	private void release() {
		if (null != httpClient) {
			httpClient.getConnectionManager().closeExpiredConnections();
			httpClient.getConnectionManager().closeIdleConnections(5,
					TimeUnit.SECONDS);
		}
	}

	/**
	 * 验证参数的正确性
	 * 
	 * @param body
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private void validate(String url, Object body) throws Exception {
		Args.notNull(httpClient, "http client");
		Args.notNull(url, "http request url");
		Args.notBlank(url, "http request url");
		Args.notNull(body, "http request body");
		if (KiddObjectTypeUtils.isMap(body) && ((Map) body).isEmpty()) {
			logger.error("do request is fail , because bodyMap is empty");
			throw new Exception("do request bodyMap is empty");
		}
		if (KiddObjectTypeUtils.isString(body)) {
			Args.notBlank(body.toString(), "http request body");
		}
	}

	private void validate(String url) throws Exception {
		Args.notNull(httpClient, "http client");
		Args.notNull(url, "http request url");
		Args.notBlank(url, "http request url");
	}

	private <T> void validate(KiddSerialTypeEnum e, Class<? extends T> clazz)
			throws Exception {
		Args.notNull(e, "http serializable enum");
		Args.notNull(clazz, "http serializable object clazz");
	}
}
