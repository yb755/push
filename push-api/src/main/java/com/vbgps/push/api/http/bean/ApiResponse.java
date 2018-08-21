package com.vbgps.push.api.http.bean;


public class ApiResponse<T> {

	private Integer code;

	private String msg;

	private T data;

	public ApiResponse(Integer code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public ApiResponse(ApiResponseCode responseCode) {
		super();
		this.code = responseCode.getCode();
		this.msg = responseCode.getMsg();
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
