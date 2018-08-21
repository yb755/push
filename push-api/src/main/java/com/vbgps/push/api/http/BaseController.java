package com.vbgps.push.api.http;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vbgps.push.api.http.bean.ApiResponse;
import com.vbgps.push.api.http.bean.ApiResponseCode;

public class BaseController {

	@ExceptionHandler
	@ResponseBody
	public ApiResponse<String> exp(HttpServletRequest request, Exception ex) {
		ex.printStackTrace();
		return new ApiResponse<String>(ApiResponseCode.FAILED);
	}
}
