package com.vbgps.push.api.http.bean;

public enum ApiResponseCode {

	SUCCESS(0, "成功"),

	FAILED(-1, "失败");

	private ApiResponseCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private int code;

	private String msg;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
