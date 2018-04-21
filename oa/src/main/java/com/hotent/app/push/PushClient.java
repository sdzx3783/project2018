package com.hotent.app.push;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.json.JSONObject;


@Service
public class PushClient {
	private static Logger logger = LoggerFactory.getLogger("pushlog");
	private static Logger errorlog = LoggerFactory.getLogger("pusherrorlog");
	// The user agent
	protected final String USER_AGENT = "Mozilla/5.0";

	// This object is used for sending the post request to Umeng
	protected HttpClient client = new DefaultHttpClient();
	
	// The host
	protected static final String host = "http://msg.umeng.com";
	
	// The upload path
	protected static final String uploadPath = "/upload";
	
	// The post path
	protected static final String postPath = "/api/send";

	protected static final String statusPath = "/api/status";
	
	public JSONObject send(UmengNotification msg) throws Exception {
		String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		msg.setPredefinedKeyValue("timestamp", timestamp);
        String url = host + postPath;
        String postBody = msg.getPostBody();
        logger.info("post body :"+postBody);
        String sign = DigestUtils.md5Hex(("POST" + url + postBody + msg.getAppMasterSecret()).getBytes("utf8"));
        url = url + "?sign=" + sign;
        logger.info("post url :"+url);
        HttpPost post = new HttpPost(url);
        post.setHeader("User-Agent", USER_AGENT);
        StringEntity se = new StringEntity(postBody, "UTF-8");
        post.setEntity(se);
        // Send the post request and get the response
        HttpResponse response = client.execute(post);
        int status = response.getStatusLine().getStatusCode();
        logger.info("Response Code : " + status);
        StringBuffer result = new StringBuffer();
        String line = "";
        try(BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));){        	
        	while ((line = rd.readLine()) != null) {
        		result.append(line);
        	}
        }
        logger.info(result.toString());
        return new JSONObject(result.toString());
    }

	// Upload file with device_tokens to Umeng
	public String uploadContents(String appkey,String appMasterSecret,String contents) throws Exception {
		// Construct the json string
		JSONObject uploadJson = new JSONObject();
		uploadJson.put("appkey", appkey);
		String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		uploadJson.put("timestamp", timestamp);
		uploadJson.put("content", contents);
		// Construct the request
		String url = host + uploadPath;
		String postBody = uploadJson.toString();
		String sign = DigestUtils.md5Hex(("POST" + url + postBody + appMasterSecret).getBytes("utf8"));
		url = url + "?sign=" + sign;
		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent", USER_AGENT);
		StringEntity se = new StringEntity(postBody, "UTF-8");
		post.setEntity(se);
		// Send the post request and get the response
		HttpResponse response = client.execute(post);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		StringBuffer result = new StringBuffer();
		String line = "";
		try(BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));){
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		}
		System.out.println(result.toString());
		// Decode response string and get file_id from it
		JSONObject respJson = new JSONObject(result.toString());
		String ret = respJson.getString("ret");
		if (!ret.equals("SUCCESS")) {
			throw new Exception("Failed to upload file");
		}
		JSONObject data = respJson.getJSONObject("data");
		String fileId = data.getString("file_id");
		// Set file_id into rootJson using setPredefinedKeyValue
		
		return fileId;
	}
	
	public JSONObject taskStatus(String appkey,String appMasterSecret,String taskId) throws Exception {
		// Construct the json string
		JSONObject statusJson = new JSONObject();
		statusJson.put("appkey", appkey);
		String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		statusJson.put("timestamp", timestamp);
		statusJson.put("task_id", taskId);
		// Construct the request
		String url = host + statusPath;
		String postBody = statusJson.toString();
		logger.info("post body:"+postBody);
		String sign = DigestUtils.md5Hex(("POST" + url + postBody + appMasterSecret).getBytes("utf8"));
		url = url + "?sign=" + sign;
		logger.info("post url:"+url);
		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent", USER_AGENT);
		StringEntity se = new StringEntity(postBody, "UTF-8");
		post.setEntity(se);
		// Send the post request and get the response
		HttpResponse response = client.execute(post);
		logger.info("Response Code : " + response.getStatusLine().getStatusCode());
		StringBuffer result = new StringBuffer();
		String line = "";
		try(BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));){
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		}
		logger.info("result:"+result.toString());
		// Decode response string and get file_id from it
		JSONObject respJson = new JSONObject(result.toString());
		
		return respJson;
	}

}
