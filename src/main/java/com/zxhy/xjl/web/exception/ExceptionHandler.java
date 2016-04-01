package com.zxhy.xjl.web.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.json.JsonObjectConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import com.alibaba.fastjson.JSONObject;

public class ExceptionHandler extends AbstractHandlerExceptionResolver {
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		//…Ë÷√ContentType
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		//±‹√‚¬“¬Î
		response.setCharacterEncoding("UTF-8");
		 response.setHeader("Cache-Control","no-cache, must-revalidate");
		 try {
			 JSONObject json = new JSONObject();
			 json.put("success", false);
			 json.put("errmsg", ex.getMessage());
			 response.getWriter().write(json.toJSONString());
			  } catch (IOException e) {
			  throw new RuntimeException(e);
			 }
		return null;
	}
}
