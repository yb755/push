package com.vbgps.push.weixin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vbgps.push.bean.PushRequest;
import com.vbgps.push.bean.PushResponse;
import com.vbgps.push.bean.weixin.WeiXinMessage;
import com.vbgps.push.service.PushService;
import com.vbgps.push.util.http.HttpUtil;
import com.vbgps.push.weixin.bean.PushTemplateMsgRequest;
import com.vbgps.push.weixin.bean.PushTemplateMsgRequest.Text;

public class WeiXinPushService implements PushService {

	private final Logger logger = LoggerFactory.getLogger(WeiXinPushService.class);

	public String PUSH_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";

	public String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

	private static Map<String, AccessToken> WX_ACCESS_TOKEN_MAP = new HashMap<String, AccessToken>();
	private static Map<String, String> APPID_SECRET_MAP = new HashMap<String, String>();

	static {
		APPID_SECRET_MAP.put("wx524efbb9e8c82052", "4004416554a669a4376c03cdc5bd1814");
	}

	public PushResponse pushMessage(PushRequest message) {
		if (!(message instanceof WeiXinMessage)) {
			return null;
		}
		WeiXinMessage wxmsg = (WeiXinMessage) message;
		String appId = wxmsg.getWxAppId();
		String openId = wxmsg.getOpenId();
		String url = wxmsg.getUrl();
		String templateId = wxmsg.getTemplateId();
		Map<String, String> dataMap = wxmsg.getData();
		if (appId == null) {
			throw new RuntimeException("微信appId不能为空");
		}
		String accessToken = null;
		try {
			accessToken = getAccessToken(appId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (accessToken == null) {
			throw new RuntimeException("获取微信accessToken失败");
		}
		String response = null;
		try {
			PushTemplateMsgRequest request = new PushTemplateMsgRequest();
			request.setTouser(openId);
			request.setUrl(url);
			request.setTemplate_id(templateId);
			Map<String, Text> data = new HashMap<String, PushTemplateMsgRequest.Text>();
			Set<String> keys = dataMap.keySet();
			for (String key : keys) {
				data.put(key, new Text(dataMap.get(key), "#000000"));
			}			
			request.setData(data);
			String strRequest = new ObjectMapper().writeValueAsString(request);
			response = HttpUtil.doPost(String.format(PUSH_URL, accessToken), strRequest);
			logger.debug("response={}", response);
		} catch (Exception e) {
			throw new RuntimeException("发送模板消息失败");
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private synchronized String getAccessToken(String appId) throws JsonParseException, JsonMappingException, IOException {
		AccessToken accessToken = WX_ACCESS_TOKEN_MAP.get(appId);
		if (accessToken == null || (System.currentTimeMillis() - accessToken.getCreateTime()) > 6000000) {
			accessToken = new AccessToken();
			Map<String, String> params = new HashMap<String, String>();
			String secret = APPID_SECRET_MAP.get(appId);
			String response = HttpUtil.post(String.format(GET_TOKEN_URL, appId, secret), params);
			Map<String, String> responseMap = new ObjectMapper().readValue(response, Map.class);
			logger.debug("response={}", response);
			accessToken.setToken(responseMap.get("access_token"));
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
