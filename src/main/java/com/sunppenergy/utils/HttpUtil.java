package com.sunppenergy.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtil {

	public static String http(String url, Map<String, Object> params) {
		URL u = null;
		HttpURLConnection con = null;
		// 构建请求参数
		StringBuffer sb = new StringBuffer();
		if (params != null) {
			for (Entry<String, Object> e : params.entrySet()) {
				sb.append(e.getKey());
				sb.append("=");
				sb.append(e.getValue());
				sb.append("&");
			}
			//System.out.println(sb);
			sb.substring(0, sb.length() - 1);
		}
		//System.out.println("send_url:" + url);
		//System.out.println("send_data:" + sb.toString());
		// 尝试发送请求
		try {
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			// // POST 只能为大写，严格限制，post会不识别
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			OutputStreamWriter osw = new OutputStreamWriter(
					con.getOutputStream(), "UTF-8");
			osw.write(sb.toString());
			osw.flush();
			osw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}

		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		try {
			// 一定要有返回值，否则无法把请求发送给server端。
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return buffer.toString();
	}

	public static void main(String[] args) {
		Map parames = new HashMap<String, String>();
		parames.put("param1", "param1_value");
		parames.put("param2", "param2_value");
		parames.put("param3", "param3_value");
		// 如果地址栏中有aaa这个参数，则默认选择地址栏的，如果没有则选择添加的参数
		parames.put("aaa", "aaa_value");
		HttpUtil.http("http://localhost:8080/a/Abc?aaa=dddd", parames);
	}

	/**
	 * 发送json格式的psot请求
	 * 
	 * @param jsonStr
	 *            json报文体
	 * @return 服务器响应报文
	 * @throws IOException
	 */
	public static String sendPostJson(String jsonStr, String requrl)
			throws IOException {
		HttpURLConnection conn = null;
		try {
			//System.out.println("jsonStr"+jsonStr);
			URL url = new URL(requrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			// 设置头信息
			conn.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			conn.connect();

			// POST请求
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());

			// 这样可以处理中文乱码问题
			out.write(jsonStr.getBytes("UTF-8"));
			out.flush();
			out.close();

			// 读取返回内容
			StringBuffer buffer = new StringBuffer();
			try {
				// 一定要有返回值，否则无法把请求发送给server端。
				BufferedReader br = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "UTF-8"));
				String temp;
				while ((temp = br.readLine()) != null) {
					buffer.append(temp);
					buffer.append("\n");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return buffer.toString();
		} catch (MalformedURLException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}
	public static String httpJson(String url, String jsonStr) {
		URL u = null;
		HttpURLConnection con = null;
		// 构建请求参数
		StringBuffer sb = new StringBuffer();
		//System.out.println("jsonStr"+jsonStr);
		//System.out.println(jsonStr.indexOf("{")+"jsonStr.indexOf");
		if (jsonStr != null) {
			//String substring = jsonStr.substring(jsonStr.indexOf("{")+1, jsonStr.indexOf("}"));
			String[] list_1 = jsonStr.split(",");
			for (String string : list_1) {
				String[] list2 = string.split(":");
				for (int i = 0; i < list2.length; i++) {
					String a=list2[i];
					//String replaceA = a.replace("\"", "");
					if(i==0){
						sb.append(a);
					}
					if(i==1){
						sb.append("=");
						sb.append(a);
						sb.append("&");
					}
				}
			}
			/*for (Entry<String, Object> e : params.entrySet()) {
				sb.append(e.getKey());
				sb.append("=");
				sb.append(e.getValue());
				sb.append("&");
			}*/
			//System.out.println("sb"+sb);
			sb.substring(0, sb.length() - 1);
		}
		//System.out.println("send_url:" + url);
		//System.out.println("send_data:" + sb.toString());
		// 尝试发送请求
		try {
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			// // POST 只能为大写，严格限制，post会不识别
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			OutputStreamWriter osw = new OutputStreamWriter(
					con.getOutputStream(), "UTF-8");
			osw.write(sb.toString());
			osw.flush();
			osw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}

		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		try {
			// 一定要有返回值，否则无法把请求发送给server端。
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return buffer.toString();
	}
}