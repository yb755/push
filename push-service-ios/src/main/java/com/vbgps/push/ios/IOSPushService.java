package com.vbgps.push.ios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vbgps.push.bean.PushRequest;
import com.vbgps.push.bean.PushResponse;
import com.vbgps.push.bean.ios.IOSMessage;
import com.vbgps.push.service.PushService;

public class IOSPushService implements PushService {

	private final Logger logger = LoggerFactory.getLogger(IOSPushService.class);

	boolean sendCount = true;

	private static Map<String, IOSCert> CERT_MAP = new HashMap<String, IOSCert>();

	static {
		CERT_MAP.put("ios_app_001", new IOSCert("D://bycdaDevelopPush.p12", "123456"));
	}

	@Override
	public PushResponse pushMessage(PushRequest message) {
		if (!(message instanceof IOSMessage)) {
			return null;
		}
		IOSMessage iosMsg = (IOSMessage) message;
		String appId = iosMsg.getAppId();
		Integer badge = iosMsg.getBadge();
		String sound = iosMsg.getSound();
		IOSCert cert = CERT_MAP.get(appId);
		String templateId = iosMsg.getTemplateId();
		String deviceToken = iosMsg.getDeviceToken();
		Map<String, String> params = iosMsg.getData();
		if (cert == null) {
			throw new RuntimeException("此appId" + appId + "未配置证书信息");
		}
		if (templateId == null) {
			throw new RuntimeException("模块ID不能为空");
		}
		if (badge == null) {
			badge = 1;
		}

		List<String> tokens = new ArrayList<String>();
		tokens.add(deviceToken);
		String content = generateContent(templateId, params);
		PushNotificationPayload payLoad = new PushNotificationPayload();
		try {
			// 消息内容
			payLoad.addAlert(content);
			payLoad.addBadge(badge);
			if (sound != null && !sound.equals("")) {
				payLoad.addSound(sound); // 铃音
			}
			PushNotificationManager pushManager = new PushNotificationManager();
			// true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
			pushManager.initializeConnection(new AppleNotificationServerBasicImpl(cert.getCertPath(), cert.getCertPassword(), iosMsg.isProduction()));
			List<PushedNotification> notifications = new ArrayList<PushedNotification>();
			// 发送push消息
			if (sendCount) {
				Device device = new BasicDevice();
				device.setToken(tokens.get(0));
				PushedNotification notification = pushManager.sendNotification(device, payLoad, true);
				notifications.add(notification);
			} else {
				List<Device> device = new ArrayList<Device>();
				for (String token : tokens) {
					device.add(new BasicDevice(token));
				}
				notifications = pushManager.sendNotifications(payLoad, device);
			}
			List<PushedNotification> failedNotifications = PushedNotification.findFailedNotifications(notifications);
			List<PushedNotification> successfulNotifications = PushedNotification.findSuccessfulNotifications(notifications);
			int failed = failedNotifications.size();
			int successful = successfulNotifications.size();
			pushManager.stopConnection();
			logger.info("发送IOS消息，成功＝{}条，失败={}条", successful, failed);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 读取模块内容，采用framemarker进行内容生成
	 * @param templateId
	 * @param params
	 * @return
	 */
	private String generateContent(String templateId, Map<String, String> params) {
		return "消息内容";
	}

	public static class IOSCert {
		private String certPath;
		private String certPassword;

		public String getCertPath() {
			return certPath;
		}

		public void setCertPath(String certPath) {
			this.certPath = certPath;
		}

		public String getCertPassword() {
			return certPassword;
		}

		public void setCertPassword(String certPassword) {
			this.certPassword = certPassword;
		}

		public IOSCert(String certPath, String certPassword) {
			super();
			this.certPath = certPath;
			this.certPassword = certPassword;
		}

	}
}
