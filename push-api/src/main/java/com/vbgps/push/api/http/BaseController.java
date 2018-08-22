package com.vbgps.push.api.http;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vbgps.push.api.http.bean.ApiResponse;
import com.vbgps.push.api.http.bean.ApiResponseCode;

public class BaseController {

	private Logger logger = LoggerFactory.getLogger(BaseController.class);

	@ExceptionHandler
	@ResponseBody
	public ApiResponse<String> exp(HttpServletRequest request, Exception ex) {
		logger.error(ex.getMessage(), ex);
		return new ApiResponse<String>(ApiResponseCode.FAILED, ex);
	}
}
