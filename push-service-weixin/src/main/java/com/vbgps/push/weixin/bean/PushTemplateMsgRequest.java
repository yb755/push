package com.vbgps.push.weixin.bean;

import java.util.Map;

public class PushTemplateMsgRequest {

	private String touser;

	private String template_id;

	private String url;

	private MiniProgram miniprogram;

	private Map<String, Text> data;

	public Map<String, Text> getData() {
		return data;
	}

	public void setData(Map<String, Text> data) {
		this.data = data;
	}

	public MiniProgram getMiniprogram() {
		return miniprogram;
	}

	public void setMiniprogram(MiniProgram miniprogram) {
		this.miniprogram = miniprogram;
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static class MiniProgram {
		private String appid;
		private String pagepath;

		public String getAppid() {
			return appid;
		}

		public void setAppid(String appid) {
			this.appid = appid;
		}

		public String getPagepath() {
			return pagepath;
		}

		public void setPagepath(String pagepath) {
			this.pagepath = pagepath;
		}

	}

	public static class Text {
		private String value;
		private String color;

		public Text() {
			super();
		}

		public Text(String value, String color) {
			super();
			this.value = value;
			this.color = color;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

	}
}
