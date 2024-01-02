package com.atguigu.wms.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 *
 */
@Slf4j
public final class HttpUtil {

	static final String POST = "POST";
	static final String GET = "GET";
	static final int CONN_TIMEOUT = 30000;// ms
	static final int READ_TIMEOUT = 30000;// ms

	/**
	 * post 方式发送http请求.
	 * 
	 * @param strUrl
	 * @param paramMap
	 * @return
	 */
	public static String doPost(String strUrl, Map<String, Object> paramMap) {
		StringBuilder postdata = new StringBuilder();
		for (Map.Entry<String, Object> param : paramMap.entrySet()) {
			postdata.append(param.getKey()).append("=")
					.append(param.getValue()).append("&");
		}
		return send(strUrl, POST, postdata.toString().getBytes());
	}

	/**
	 * get方式发送http请求.
	 * 
	 * @param strUrl
	 * @return
	 */
	public static String doGet(String strUrl) {
		return send(strUrl, GET, null);
	}

	/**
	 * @param strUrl
	 * @param reqmethod
	 * @param reqData
	 * @return
	 */
	public static String send(String strUrl, String reqmethod, byte[] reqData) {
		try {
			URL url = new URL(strUrl);
			HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
			httpcon.setDoOutput(true);
			httpcon.setDoInput(true);
			httpcon.setUseCaches(false);
			httpcon.setInstanceFollowRedirects(true);
			httpcon.setConnectTimeout(CONN_TIMEOUT);
			httpcon.setReadTimeout(READ_TIMEOUT);
			httpcon.setRequestMethod(reqmethod);
			httpcon.connect();
			if (reqmethod.equalsIgnoreCase(POST)) {
				OutputStream os = httpcon.getOutputStream();
				os.write(reqData);
				os.flush();
				os.close();
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(httpcon.getInputStream(),"utf-8"));
			String inputLine;
			StringBuilder bankXmlBuffer = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {  
			    bankXmlBuffer.append(inputLine);  
			}  
			in.close();  
			httpcon.disconnect();
			return bankXmlBuffer.toString();
		} catch (Exception ex) {
			log.error(ex.toString(), ex);
			return null;
		}
	}
}
