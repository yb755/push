package com.vbgps.push.bean.weixin;

import java.util.Map;

import com.vbgps.push.bean.PushRequest;

public class WeiXinMessage extends PushRequest {
	// 微信的AppId
	private String wxAppId;
	// 微信用户的OpenId
	private String openId;
	// 微信消息模板ID
	private String templateId;
	// URL和miniProgramXXXX不是必需的
	private String url;
	private String miniProgramAppid;
	private String miniProgramPagepath;
	private Map<String, String> data;

	public String getWxAppId() {
		return wxAppId;
	}

	public void setWxAppId(String wxAppId) {
		this.wxAppId = wxAppId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMiniProgramAppid() {
		return miniProgramAppid;
	}

	public void setMiniProgramAppid(String miniProgramAppid) {
		this.miniProgramAppid = miniProgramAppid;
	}

	public String getMiniProgramPagepath() {
		return miniProgramPagepath;
	}

	public void setMiniProgramPagepath(String miniProgramPagepath) {
		this.miniProgramPagepath = miniProgramPagepath;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

}
