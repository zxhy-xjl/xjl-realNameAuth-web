package com.zxhy.xjl.rna.web.controller;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxhy.xjl.web.controller.BaseControllerTest;
import com.zxhy.xjl.web.controller.RealNameAuthTask;
 
public  class RealNameAuthControllerTest extends BaseControllerTest {
	private static Log log = LogFactory.getLog(RealNameAuthControllerTest.class);
	@Autowired
	private RealNameAuthController controller;
	@Override
	public Object getController(){
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
