package com.kidd.base.http.httpclient;

import java.util.concurrent.TimeUnit;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

class KiddHttpPoolConfig {

	/**
	 * 创建线程池连接管理
	 * 
	 * @param params
	 *            [http 参数对象]
	 * @param ssf
	 *            [SSL证书工厂]
	 * @return
	 * @throws Exception
	 */
	static HttpClientConnectionManager build(KiddHttpParams params,
			SSLConnectionSocketFactory ssf) throws Exception {
		Registry<ConnectionSocketFactory> registry = RegistryBuilder
				.<ConnectionSocketFactory> create()
				.register(KiddHttpConstants.HTTP_PREFIX,
						PlainConnectionSocketFactory.INSTANCE)
				.register(
						KiddHttpConstants.HTTPS_PREFIX,
						ssf == null ? SSLConnectionSocketFactory
								.getSocketFactory() : ssf).build();

		PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(
				registry);
		// 设置连接池大小
		connMgr.setMaxTotal(params.getMaxTotal());
		// 每个域名最大分配连接数
		connMgr.setDefaultMaxPerRoute(params.getMaxPerRoute());
		// 验证切换连接时一定时间内不活动的连接
		// connMgr.setValidateAfterInactivity(1000);
		// 启动监控线程
		new IdleConnectionMonitorThread(connMgr).start();
		return connMgr;
	}
}

/**
 * http连接监控线程，用户关闭空闲的连接
 * 
 * @history
 */
class IdleConnectionMonitorThread extends Thread {
	/**
	 * http连接管理
	 */
	private final HttpClientConnectionManager connMgr;
	/**
	 * 
	 */
	private volatile boolean shutdown;

	public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
		super();
		this.connMgr = connMgr;
	}

	@Override
	public void run() {
		try {
			while (!shutdown) {
				synchronized (this) {
					wait(5000);
					// 关闭失效的连接
					connMgr.closeExpiredConnections();
					// 回收5秒内多线程线程池不活动的http连接
					connMgr.closeIdleConnections(5, TimeUnit.SECONDS);
				}
			}
		} catch (InterruptedException ex) {
		}
	}

	public void shutdown() {
		shutdown = true;
		synchronized (this) {
			notifyAll();
		}
	}
}