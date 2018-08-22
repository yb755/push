package com.vbgps.push.ios;

import com.vbgps.push.bean.PushRequest;
import com.vbgps.push.bean.PushResponse;
import com.vbgps.push.bean.ios.IOSMessage;
import com.vbgps.push.service.PushService;

public class IOSPushService implements PushService {

	@Override
	public PushResponse pushMessage(PushRequest message) {
		if (!(message instanceof IOSMessage)) {
			return null;
		}
		IOSMessage iosMsg = (IOSMessage) message;
		return null;
	}

}
