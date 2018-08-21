package com.vbgps.push.api.http;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vbgps.push.api.http.bean.ApiResponse;
import com.vbgps.push.api.http.bean.ApiResponseCode;
import com.vbgps.push.bean.PushMessage;
import com.vbgps.push.bean.PushMessage.Platform;
import com.vbgps.push.ios.IOSPushService;
import com.vbgps.push.service.PushService;
import com.vbgps.push.weixin.WeiXinPushService;

@Controller
@RequestMapping("/api")
public class PushController extends BaseController {

	@RequestMapping("/push")
	public ApiResponse<String> push(PushMessage msg) {
		Platform platform = msg.getPlatform();
		String token = msg.getToken();
		String templateId = msg.getTemplateId();
		if (platform == null) {
			throw new RuntimeException("消息平台类型不能为空");
		}
		if (token == null) {
			throw new RuntimeException("token不能为空");
		}
		if (templateId == null) {
			throw new RuntimeException("templateId不能为空");
		}
		PushService pushService = getPushService(platform);
		if (pushService == null) {
			throw new RuntimeException(platform + "暂时还不支持");
		}
		pushService.pushMessage(msg);
		return new ApiResponse<String>(ApiResponseCode.SUCCESS);
	}

	@ResponseBody
	@RequestMapping("/request_token")
	public ApiResponse<String> requestToken() {
		ApiResponse<String> response = new ApiResponse<String>(ApiResponseCode.SUCCESS);
		response.setData("asdfasdfwerfgasd");
		return response;
	}

	public PushService getPushService(Platform platform) {
		if (platform == Platform.WEIXIN) {
			return new WeiXinPushService();
		} else if (platform == Platform.IOS) {
			return new IOSPushService();
		} else {
			return null;
		}
	}
}
