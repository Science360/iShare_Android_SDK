package com.kuketech.ishare.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.Character.UnicodeBlock;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Enumeration;

import org.apache.http.conn.util.InetAddressUtils;
import org.json.JSONObject;

import com.kuketech.ishare.bean.IpBean;
import com.kuketech.ishare.bean.LatLonBean;

import android.R.string;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

public class NetUtils {
	/**
	 * 获取外网地址
	 * 
	 * @return
	 */
	public static IpBean GetNetIp() {
		IpBean bean = new IpBean();
		try {
			// 访问外网ip
			String address = "http://ip.taobao.com/service/getIpInfo.php?ip=myip";
			URL url = new URL(address);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setUseCaches(false);

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream in = connection.getInputStream();

				// 将流转化为字符串
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in));

				String tmpString = "";
				StringBuilder retJSON = new StringBuilder();
				while ((tmpString = reader.readLine()) != null) {
					retJSON.append(tmpString + "\n");
				}

				// {"data":{"city_id":"510100","region":"四川省","area_id":"500000","area":"西南","county":"","isp":"电信","county_id":"-1","country_id":"CN","region_id":"510000","ip":"218.88.4.195","isp_id":"100017","city":"成都市","country":"中国"},"code":0}
				JSONObject jsonObject = new JSONObject(retJSON.toString());
				String code = jsonObject.getString("code");
				Log.e("提示", "IP接口，"+jsonObject.toString());
				if (code.equals("0")) {
					JSONObject data = jsonObject.getJSONObject("data");
					bean.setInfo(data.getString("ip") + "("
							+ data.getString("country")
							+ data.getString("area") + "区"
							+ data.getString("region") + data.getString("city")
							+ data.getString("isp") + ")");
					bean.setCity(data.getString("city"));
					bean.setIp(data.getString("ip"));
					return bean;
				} else {
					Log.e("提示", "IP接口异常，无法获取IP地址！");
				}
			} else {
				Log.e("提示", "网络连接异常，无法获取IP地址！");
			}
		} catch (Exception e) {
			Log.e("提示", "获取IP地址时出现异常，异常信息是：" + e.toString());
		}

		return null;
	}

	/**
	 * 获取城市经纬度
	 * 
	 * @param city
	 * @return
	 */
	public static LatLonBean GetNetGps(String city) {
		LatLonBean bean = new LatLonBean();
		bean.setCity(city);
		try {
			// 阿里云接口
			String address = "http://gc.ditu.aliyun.com/geocoding?a="
					+ Uri.encode(city, "utf-8");
			URL url = new URL(address);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setUseCaches(false);
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream in = connection.getInputStream();

				// 将流转化为字符串
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in));

				String tmpString = "";
				StringBuilder retJSON = new StringBuilder();
				while ((tmpString = reader.readLine()) != null) {
					retJSON.append(tmpString + "\n");
				}

				JSONObject jsonObject = new JSONObject(retJSON.toString());
				String lon = jsonObject.getString("lon");
				String lat = jsonObject.getString("lat");
				bean.setLon("30.675903");
				bean.setLat("104.013541");
				return bean;

			}
		} catch (Exception e) {
		}
		return null;
	}
	
}
