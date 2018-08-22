package com.vbgps.push.service;

import com.vbgps.push.bean.PushRequest;
import com.vbgps.push.bean.PushResponse;

public interface PushService {

	PushResponse pushMessage(PushRequest message);
}
