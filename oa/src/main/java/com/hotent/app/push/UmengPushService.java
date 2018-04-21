package com.hotent.app.push;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hotent.app.push.android.AndroidBroadcast;
import com.hotent.app.push.android.AndroidCustomizedcast;
import com.hotent.app.push.android.AndroidFilecast;
import com.hotent.app.push.android.AndroidGroupcast;
import com.hotent.app.push.android.AndroidUnicast;
import com.hotent.app.push.ios.IOSBroadcast;
import com.hotent.app.push.ios.IOSCustomizedcast;
import com.hotent.app.push.ios.IOSFilecast;
import com.hotent.app.push.ios.IOSGroupcast;
import com.hotent.app.push.ios.IOSUnicast;
import com.maishi.component.util.PropUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author lin
 * 2017年2月14日15:53:26
 */
@Service
public class UmengPushService {
	private final Logger log = Logger.getLogger(this.getClass());
	private final static String iosAppkey = PropUtils.getProperty(
			"umeng_appkey_ios", "push");
	private final static String iosAppMasterSecret = PropUtils.getProperty(
			"umeng_appsecret_ios", "push");

	private final static String androidAppkey = PropUtils.getProperty(
			"umeng_appkey_android", "push");
	private final static String androidAppMasterSecret = PropUtils.getProperty(
			"umeng_appsecret_android", "push");

	public static String getIosappkey() {
		return iosAppkey;
	}

	public static String getIosappmastersecret() {
		return iosAppMasterSecret;
	}

	public static String getAndroidappkey() {
		return androidAppkey;
	}

	public static String getAndroidappmastersecret() {
		return androidAppMasterSecret;
	}

	@Resource
	PushClient client;

	/**
	 * @param content
	 *            发送内容
	 * @param url
	 *            打开参数
	 * @return 任务ID
	 * @throws Exception
	 */
	public String sendAndroidBroadcast(String content, String url)
			throws Exception {
		AndroidBroadcast broadcast = new AndroidBroadcast(androidAppkey,
				androidAppMasterSecret);
		broadcast.setTicker("OA");
		broadcast.setTitle("OA");
		broadcast.setText(content);
		if (StringUtils.isBlank(url)) {
			broadcast.goAppAfterOpen();
		} else {
			broadcast.goCustomAfterOpen(url);
		}
		broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		//broadcast.setProductionMode();
		if (System.getenv("RUN_ENV").equals("prd")) {
			broadcast.setProductionMode();
		}else{
			broadcast.setTestMode();
		}
		// Set customized fields
		// broadcast.setExtraField("test", "helloworld");
		JSONObject respJson = client.send(broadcast);
		log.info(respJson.toString());
		String ret = respJson.getString("ret");
		if (!ret.equals("SUCCESS")) {
			throw new Exception("安卓推送失败");
		}
		JSONObject data = respJson.getJSONObject("data");
		String taskId = data.getString("task_id");
		return taskId;
	}

	public JSONObject sendAndroidUnicast(String deviceToken,String ticker,String title,String content, String url){
		JSONObject obj = new JSONObject();
		try {
			AndroidUnicast unicast = new AndroidUnicast(androidAppkey,androidAppMasterSecret);
			// TODO Set your device token
			unicast.setDeviceToken(deviceToken);
			unicast.setTicker(ticker);
			unicast.setTitle(title);
			unicast.setText(content);
			if (StringUtils.isBlank(url)) {
				unicast.goAppAfterOpen();
			} else {
				unicast.goCustomAfterOpen(url);
			}
			unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
			// TODO Set 'production_mode' to 'false' if it's a test device.
			// For how to register a test device, please see the developer doc.
			if (System.getenv("RUN_ENV").equals("prd")) {
				unicast.setProductionMode();
			}else{
				unicast.setTestMode();
			}
			// Set customized fields
			//unicast.setExtraField("test", "helloworld");
			obj = client.send(unicast);
			log.info(obj.toString());
			
		} catch (Exception e) {
			log.error(e);
		}
		return obj;
	}

	public void sendAndroidGroupcast() throws Exception {
		AndroidGroupcast groupcast = new AndroidGroupcast(androidAppkey,
				androidAppMasterSecret);
		/*
		 * TODO Construct the filter condition: "where": { "and": [
		 * {"tag":"test"}, {"tag":"Test"} ] }
		 */
		JSONObject filterJson = new JSONObject();
		JSONObject whereJson = new JSONObject();
		JSONArray tagArray = new JSONArray();
		JSONObject testTag = new JSONObject();
		JSONObject TestTag = new JSONObject();
		testTag.put("tag", "test");
		TestTag.put("tag", "Test");
		tagArray.put(testTag);
		tagArray.put(TestTag);
		whereJson.put("and", tagArray);
		filterJson.put("where", whereJson);
		log.info(filterJson.toString());

		groupcast.setFilter(filterJson);
		groupcast.setTicker("Android groupcast ticker");
		groupcast.setTitle("中文的title");
		groupcast.setText("Android groupcast text");
		groupcast.goAppAfterOpen();
		groupcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		groupcast.setProductionMode();
		client.send(groupcast);
	}

	public void sendAndroidCustomizedcast(Integer userId,String content,String url) throws Exception {
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(
				androidAppkey, androidAppMasterSecret);
		// TODO Set your alias here, and use comma to split them if there are
		// multiple alias.
		// And if you have many alias, you can also upload a file containing
		// these alias, then
		// use file_id to send customized notification.
		customizedcast.setAlias(userId+"", "UserId");
		customizedcast.setTicker("美味大联盟");
		customizedcast.setTitle("美味大联盟");
		customizedcast.setText(content);
		if (StringUtils.isBlank(url)) {
			customizedcast.goAppAfterOpen();
		} else {
			customizedcast.goCustomAfterOpen(url);
		}
		customizedcast
				.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		customizedcast.setProductionMode();
		client.send(customizedcast);
	}

	public void sendAndroidCustomizedcastFile() throws Exception {
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(
				androidAppkey, androidAppMasterSecret);
		// TODO Set your alias here, and use comma to split them if there are
		// multiple alias.
		// And if you have many alias, you can also upload a file containing
		// these alias, then
		// use file_id to send customized notification.
		String fileId = client.uploadContents(androidAppkey,
				androidAppMasterSecret, "aa" + "\n" + "bb" + "\n" + "alias");
		customizedcast.setFileId(fileId, "alias_type");
		customizedcast.setTicker("Android customizedcast ticker");
		customizedcast.setTitle("中文的title");
		customizedcast.setText("Android customizedcast text");
		customizedcast.goAppAfterOpen();
		customizedcast
				.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		customizedcast.setProductionMode();
		client.send(customizedcast);
	}

	public void sendAndroidFilecast() throws Exception {
		AndroidFilecast filecast = new AndroidFilecast(androidAppkey,
				androidAppMasterSecret);
		// TODO upload your device tokens, and use '\n' to split them if there
		// are multiple tokens
		String fileId = client.uploadContents(androidAppkey,
				androidAppMasterSecret, "aa" + "\n" + "bb");
		filecast.setFileId(fileId);
		filecast.setTicker("Android filecast ticker");
		filecast.setTitle("中文的title");
		filecast.setText("Android filecast text");
		filecast.goAppAfterOpen();
		filecast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		client.send(filecast);
	}

	/**
	 * @param content 发送内容
	 * @param url 打开参数
	 * @return 任务ID
	 * @throws Exception
	 */
	public String sendIOSBroadcast(String content, String url) throws Exception {
		IOSBroadcast broadcast = new IOSBroadcast(iosAppkey, iosAppMasterSecret);

		broadcast.setAlert(content);
		broadcast.setBadge(1);
		broadcast.setSound("default");

		// TODO set 'production_mode' to 'true' if your app is under production
		// mode
		if (System.getenv("RUN_ENV").equals("prd")) {
			broadcast.setProductionMode();
		}else{
			broadcast.setTestMode();
		}
		
		// Set customized fields
		broadcast.setCustomizedField("custom", url);
		JSONObject respJson = client.send(broadcast);
		String ret = respJson.getString("ret");
		if (!ret.equals("SUCCESS")) {
			throw new Exception("IOS推送失败");
		}
		log.info(respJson.toString());
		JSONObject data = respJson.getJSONObject("data");
		String taskId = data.getString("task_id");
		return taskId;
	}

	public JSONObject sendIOSUnicast(String devideToken,String content, String url) throws Exception {
		JSONObject obj = new JSONObject();
		try {
			IOSUnicast unicast = new IOSUnicast(iosAppkey, iosAppMasterSecret);
			// TODO Set your device token
			unicast.setDeviceToken(devideToken);
			unicast.setAlert(content);
			unicast.setBadge(0);
			unicast.setSound("default");
			// TODO set 'production_mode' to 'true' if your app is under production
			// mode
			if (System.getenv("RUN_ENV").equals("prd")) {
				unicast.setProductionMode();
			}else{
				unicast.setTestMode();
			}
			// Set customized fields
			//unicast.setCustomizedField("test", "helloworld");
			unicast.setCustomizedField("custom", url);
			obj = client.send(unicast);
			log.info(obj.toString());
		} catch (Exception e) {
			log.error(e);
		}
		return obj;
	}

	public void sendIOSGroupcast() throws Exception {
		IOSGroupcast groupcast = new IOSGroupcast(iosAppkey, iosAppMasterSecret);
		/*
		 * TODO Construct the filter condition: "where": { "and": [
		 * {"tag":"iostest"} ] }
		 */
		JSONObject filterJson = new JSONObject();
		JSONObject whereJson = new JSONObject();
		JSONArray tagArray = new JSONArray();
		JSONObject testTag = new JSONObject();
		testTag.put("tag", "iostest");
		tagArray.put(testTag);
		whereJson.put("and", tagArray);
		filterJson.put("where", whereJson);
		log.info(filterJson.toString());

		// Set filter condition into rootJson
		groupcast.setFilter(filterJson);
		groupcast.setAlert("IOS 组播测试");
		groupcast.setBadge(0);
		groupcast.setSound("default");
		// TODO set 'production_mode' to 'true' if your app is under production
		// mode
		groupcast.setTestMode();
		client.send(groupcast);
	}

	public void sendIOSCustomizedcast(Integer userId,String content,String url) throws Exception {
		IOSCustomizedcast customizedcast = new IOSCustomizedcast(iosAppkey,
				iosAppMasterSecret);
		// TODO Set your alias and alias_type here, and use comma to split them
		// if there are multiple alias.
		// And if you have many alias, you can also upload a file containing
		// these alias, then
		// use file_id to send customized notification.
		customizedcast.setAlias(userId+"", "UserId");
		customizedcast.setAlert(content);
		customizedcast.setBadge(1);
		customizedcast.setSound("default");
		customizedcast.setCustomizedField("custom", url);
		// TODO set 'production_mode' to 'true' if your app is under production
		// mode
		if (System.getenv("RUN_ENV").equals("prd")) {
			customizedcast.setProductionMode();
		}else{
			customizedcast.setTestMode();
		}
		client.send(customizedcast);
	}

	public void sendIOSFilecast() throws Exception {
		IOSFilecast filecast = new IOSFilecast(iosAppkey, iosAppMasterSecret);
		// TODO upload your device tokens, and use '\n' to split them if there
		// are multiple tokens
		String fileId = client.uploadContents(iosAppkey, iosAppMasterSecret,
				"aa" + "\n" + "bb");
		filecast.setFileId(fileId);
		filecast.setAlert("IOS 文件播测试");
		filecast.setBadge(0);
		filecast.setSound("default");
		// TODO set 'production_mode' to 'true' if your app is under production
		// mode
		filecast.setTestMode();
		client.send(filecast);
	}
	
	public JSONObject taskStatus(boolean ios,String taskId) throws Exception{
		JSONObject respJson=null;
		if(ios){
			respJson = client.taskStatus(iosAppkey, iosAppMasterSecret, taskId);
		} else {
			respJson = client.taskStatus(androidAppkey, androidAppMasterSecret, taskId);
		}
		String ret = respJson.getString("ret");
		if (!ret.equals("SUCCESS")) {
			throw new Exception("查询状态失败");
		}
		JSONObject data = respJson.getJSONObject("data");
		return data;
	}

}
