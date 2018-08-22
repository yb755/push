package com.vbgps.push.api.http;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vbgps.push.api.http.bean.ApiResponse;
import com.vbgps.push.api.http.bean.ApiResponseCode;
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

	// String WX_APP_ID = "wx524efbb9e8c82052";
	// String templateId = "eUlNy1xrFNZGt0VUZy31Z_jBjQkrZxhjtHFphFrUrxk";
	// String toUser = "o8KZ30gobWdccLatGN0Pobn4OS4w";

	@ResponseBody
	@RequestMapping("/push")
	public ApiResponse<String> push(HttpServletRequest request) {
		String platform = request.getParameter("platform");
		String token = request.getParameter("token");
		String templateId = request.getParameter("templateId");
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
		PushRequest obj = getMessage(request);
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

	private PushRequest getMessage(HttpServletRequest request) {
		String platform = request.getParameter("platform");
		if (Platform.WEIXIN.name().equals(platform)) {
			return parseWeiXinMessage(request);
		} else if (Platform.IOS.name().equals(platform)) {
			return parseIOSMessage(request);
		}
		return null;
	}

	/**
	 * 将请求参数转化成微信的消息对象
	 * @param request
	 * @return
	 */
	private WeiXinMessage parseWeiXinMessage(HttpServletRequest request) {
		Enumeration<String> paramKeys = request.getParameterNames();
		WeiXinMessage wxmsg = new WeiXinMessage();
		Map<String, String> params = new HashMap<String, String>();
		while (paramKeys.hasMoreElements()) {
			String key = paramKeys.nextElement();
			String value = request.getParameter(key);
			if ("appId".equals(key)) {
				wxmsg.setWxAppId(value);
			} else if ("url".equals(key)) {
				wxmsg.setUrl(value);
			} else if ("token".equals(key)) {
				wxmsg.setOpenId(value);
			} else if ("templateId".equals(key)) {
				wxmsg.setTemplateId(value);
			} else {
				params.put(key, value);
			}
		}
		if (!params.isEmpty()) {
			wxmsg.setData(params);
		}
		return wxmsg;
	}

	/**
	 * 将请求转化成IOS的消息对象
	 * @param request
	 * @return
	 */
	private IOSMessage parseIOSMessage(HttpServletRequest request) {
		IOSMessage iosmsg = new IOSMessage();
		Enumeration<String> paramKeys = request.getParameterNames();
		Map<String, String> params = new HashMap<String, String>();
		while (paramKeys.hasMoreElements()) {
			String key = paramKeys.nextElement();
			String value = request.getParameter(key);
			if ("appId".equals(key)) {
				iosmsg.setAppId(value);
			}else if ("token".equals(key)) {
				iosmsg.setDeviceToken(value);
			} else if ("templateId".equals(key)) {
				iosmsg.setTemplateId(value);
			} else {
				params.put(key, value);
			}
		}
		if (!params.isEmpty()) {
			iosmsg.setData(params);
		}
		return iosmsg;
	}

	private PushService getPushService(String platform) {
		if (Platform.WEIXIN.name().equals(platform)) {
			return new WeiXinPushService();
		} else if (Platform.IOS.name().equals(platform)) {
			return new IOSPushService();
		} else {
			return null;
		}
	}
}
