package com.vbgps.push.service;

import com.vbgps.push.bean.PushMessage;
import com.vbgps.push.bean.PushResponse;

public interface PushService {

	PushResponse pushMessage(PushMessage message);
}
