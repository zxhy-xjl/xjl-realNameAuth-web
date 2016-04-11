package com.zxhy.xjl.rna.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.WebApplicationContext;

import com.zxhy.xjl.rna.web.model.RealNameAuthTask;
import com.zxhy.xjl.web.controller.BaseControllerTest;

public class RealNameAuthControllerTest extends BaseControllerTest{
	private static Log log = LogFactory.getLog(RealNameAuthControllerTest.class);
	@Autowired
	private RealNameAuthController controller;
	@Override
	public Object getController() {
		return this.controller;
	}
	
	@Test
	public void logon(){
		String uri = "/logon";
		String resp = this.mockPost(uri, null);
		System.out.println("logon:" + resp);
	}
	
	@Test
	public void register() throws JsonGenerationException, JsonMappingException, IOException {
		String uri = "/register";
		RealNameAuthTask task =new RealNameAuthTask();
		task.setPhone("234");
		task.setProcessName("测试用户");
		task.setTaskId("1");
		task.setTaskName("测试任务");
		ObjectMapper mapper = new ObjectMapper();
		
		String resp = this.mockPost(uri,mapper.writeValueAsString(task) );
		System.out.println("register:" + resp);
	}
}
