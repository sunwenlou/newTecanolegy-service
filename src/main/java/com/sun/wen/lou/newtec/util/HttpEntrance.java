package com.sun.wen.lou.newtec.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;

public class HttpEntrance {
	public static HttpEntrance getInstance() {
		return new HttpEntrance();
	}

	public static String conn(String path, Map<String, Object> map) {
		String result = "";
		try {
			URL url = new URL(path);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");

			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			conn.setConnectTimeout(10000);

			conn.setReadTimeout(2000);

			conn.setDoOutput(true);
			byte[] bypes = getParams(map);

			conn.getOutputStream().write(bypes);
			InputStream inStream = conn.getInputStream();
			result = IOUtils.toString(inStream, "UTF-8");
			inStream.close();
			System.out.println(result);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		String url = "http://interface.uuc.com/iresource/authorized";
		
		Map map = new HashMap();

		map.put("sysFlag", 1);
		map.put("accountId", "");
		String str = conn(url, map);
	}

	public static byte[] getParams(Map<String, Object> map) {
		if (map.size() <= 0) {
			return "".getBytes();
		}
		StringBuffer params = new StringBuffer();
		Set en = map.entrySet();
		Iterator it = en.iterator();
		int flag = 0;
		while (it.hasNext()) {
			Map.Entry next = (Map.Entry) it.next();
			String key = (String) next.getKey();
			Object value = next.getValue();
			if (flag == 0)
				params.append(key).append("=").append(value.toString());
			else {
				params.append("&" + key).append("=").append(value.toString());
			}
			flag++;
		}
		System.out.println(params.toString());
		byte[] bypes = params.toString().getBytes();
		return bypes;
	}
}