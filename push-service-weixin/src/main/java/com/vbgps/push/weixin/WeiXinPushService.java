package com.vbgps.push.weixin;

import java.util.HashMap;
import java.util.Map;

import com.vbgps.push.bean.PushRequest;
import com.vbgps.push.bean.PushResponse;
import com.vbgps.push.bean.weixin.WeiXinMessage;
import com.vbgps.push.service.PushService;

public class WeiXinPushService implements PushService {

	public String PUSH_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";

	public String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

	private static Map<String, AccessToken> WX_ACCESS_TOKEN_MAP = new HashMap<String, AccessToken>();

	@Override
	public PushResponse pushMessage(PushRequest message) {
		if (!(message instanceof WeiXinMessage)) {
			return null;
		}
		WeiXinMessage wxmsg = (WeiXinMessage) message;
		String appId = wxmsg.getWxAppId();
		if (appId == null) {
			throw new RuntimeException("微信appId不能为空");
		}
		String accessToken = getAccessToken(appId);
		if (accessToken == null) {
			throw new RuntimeException("获取微信accessToken失败");
		}

		return null;
	}

	private synchronized String getAccessToken(String appId) {
		AccessToken accessToken = WX_ACCESS_TOKEN_MAP.get(appId);
		if (accessToken == null || (System.currentTimeMillis() - accessToken.getCreateTime()) > 6000000) {
			accessToken = new AccessToken();
			accessToken.setToken("aaaa");
			accessToken.setCreateTime(System.currentTimeMillis());
			WX_ACCESS_TOKEN_MAP.put(appId, accessToken);
		}
		return accessToken.getToken();
	}

	static class AccessToken {
		private String token;
		private Long createTime;

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public Long getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Long createTime) {
			this.createTime = createTime;
		}

	}
}
