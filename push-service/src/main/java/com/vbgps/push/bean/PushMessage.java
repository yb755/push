package com.vbgps.push.bean;

import java.util.HashMap;
import java.util.Map;

public class PushMessage {

	private PlatformType platformType;

	private String token;

	private Map<String, Object> params = new HashMap<String, Object>();

	private String templateId;

	public PlatformType getPlatformType() {
		return platformType;
	}

	public void setPlatformType(PlatformType platformType) {
		this.platformType = platformType;
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

	static enum PlatformType {
		IOS, WEIXIN, ANDROID, SMS, EMAIL;
	}
}
