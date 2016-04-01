package com.zxhy.xjl.rna.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zxhy.xjl.rna.web.model.RealNameAuthTask;


@Controller
@RequestMapping("/realNameAuth")
public class RealNameAuthController {
	private static final Log log = LogFactory.getLog(RealNameAuthController.class);
	@ResponseBody
	@RequestMapping("/logon")
	public RealNameAuthTask logon(@RequestParam(name="phone") String phone, @RequestParam(name="passwd") String passwd){
		log.debug("logon phone:" + phone + " passwd:" + passwd);
		boolean logon = true;
		if (logon){
			RealNameAuthTask task =new RealNameAuthTask();
			task.setPhone(phone);
			task.setProcessName("实名认证");
			task.setTaskId("1");
			task.setTaskName("核名");
			return task;
		} else {
			return new RealNameAuthTask();
		}
	}
	@ResponseBody
	@RequestMapping("/register")
	public RealNameAuthTask register(@RequestParam(name="phone") String phone, @RequestParam(name="passwd") String passwd){
		log.debug("logon phone:" + phone + " passwd:" + passwd);
		RealNameAuthTask task =new RealNameAuthTask();
		task.setPhone(phone);
		task.setProcessName("实名认证");
		task.setTaskId("1");
		task.setTaskName("核名");
		return task;
	}
	@ResponseBody
	@RequestMapping("/finishTask")
	public RealNameAuthTask finishTask(
			@RequestParam(name="phone") String phone, 
			@RequestParam(name="passwd") String passwd,
			@RequestParam(name="taskName") String taskName,
			@RequestParam(name="taskId") String taskId){
		log.debug("logon phone:" + phone + " passwd:" + passwd + " taskName:" + taskName + " taskId:" + taskId);
		if ("注册".equals(taskName)){
			log.debug("完成注册步骤");
		} else if ("核名".equals(taskName)){
			log.debug("完成核名步骤");
		} else if ("刷脸".equals(taskName)){
			log.debug("完成刷脸步骤");
		}
		RealNameAuthTask task =new RealNameAuthTask();
		task.setPhone(phone);
		task.setProcessName("实名认证");
		task.setTaskId("1");
		task.setTaskName("核名");
		return task;
	}
	
}
