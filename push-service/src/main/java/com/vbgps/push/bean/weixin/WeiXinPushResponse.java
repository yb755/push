package com.vbgps.push.bean.weixin;

import com.vbgps.push.bean.PushResponse;

public class WeiXinPushResponse extends PushResponse {

	private Integer errcode;

	private String errmsg;

	private Integer msgid;

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public Integer getMsgid() {
		return msgid;
	}

	public void setMsgid(Integer msgid) {
		this.msgid = msgid;
	}

}
