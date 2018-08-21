package com.vbgps.push.weixin;

import com.vbgps.push.bean.PushMessage;
import com.vbgps.push.bean.PushResponse;
import com.vbgps.push.bean.weixin.WeiXinMessage;
import com.vbgps.push.service.PushService;

public class WeiXinPushService implements PushService {

	public String PUSH_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";

	@Override
	public PushResponse pushMessage(PushMessage message) {
		if (message instanceof WeiXinMessage) {
			return null;
		}
		WeiXinMessage wxmsg = (WeiXinMessage) message;
		String token = wxmsg.getToken();
		
		return null;
	}

}
