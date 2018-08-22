package com.vbgps.push.api.http;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vbgps.push.api.http.bean.ApiResponse;
import com.vbgps.push.api.http.bean.ApiResponseCode;
import com.vbgps.push.api.http.bean.PushMessage;
import com.vbgps.push.api.http.bean.PushMessage.Platform;
import com.vbgps.push.bean.PushRequest;
import com.vbgps.push.bean.ios.IOSMessage;
import com.vbgps.push.bean.weixin.WeiXinMessage;
import com.vbgps.push.ios.IOSPushService;
import com.vbgps.push.service.PushService;
import com.vbgps.push.weixin.WeiXinPushService;

@Controller
@RequestMapping("/api")
public class PushController extends BaseController {

	@ResponseBody
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
		PushRequest obj = getMessage(msg);
		if (obj == null) {
			throw new RuntimeException("此消息不支持发送");
		}
		pushService.pushMessage(obj);
		return new ApiResponse<String>(ApiResponseCode.SUCCESS);
	}

	@ResponseBody
	@RequestMapping("/request_token")
	public ApiResponse<String> requestToken() {
		ApiResponse<String> response = new ApiResponse<String>(ApiResponseCode.SUCCESS);
		response.setData("asdfasdfwerfgasd");
		return response;
	}

	private PushRequest getMessage(PushMessage msg) {
		Platform platform = msg.getPlatform();
		if (platform == Platform.WEIXIN) {
			WeiXinMessage wxmsg = new WeiXinMessage();
			return wxmsg;
		} else if (platform == Platform.IOS) {
			IOSMessage iosmsg = new IOSMessage();
			return iosmsg;
		}
		return null;
	}

	private PushService getPushService(Platform platform) {
		if (platform == Platform.WEIXIN) {
			return new WeiXinPushService();
		} else if (platform == Platform.IOS) {
			return new IOSPushService();
		} else {
			return null;
		}
	}
}
