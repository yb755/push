package com.vbgps.push.bean;

import java.util.HashMap;
import java.util.Map;

public class PushMessage {

	private Platform platform;

	private String token;

	private Map<String, Object> params = new HashMap<String, Object>();

	private String templateId;

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {	
		this.platform = platform;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public static enum Platform {
		IOS, WEIXIN, ANDROID, SMS, EMAIL;
	}
}
