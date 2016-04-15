package com.zxhy.xjl.rna.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.zxhy.xjl.notification.sms.SMS;
import com.zxhy.xjl.notification.verifyCode.VerifyCode;
import com.zxhy.xjl.rna.business.RealNameAuthBusiness;
import com.zxhy.xjl.rna.mapper.RealNameAuthMapper;
import com.zxhy.xjl.rna.model.RealNameAuth;
import com.zxhy.xjl.rna.web.model.Message;
import com.zxhy.xjl.rna.web.model.RealNameAuthTask;

@Controller
@RequestMapping("/realNameAuth")
public class RealNameAuthController {
	private static final Log log = LogFactory.getLog(RealNameAuthController.class);
	private final static String PHONE_EXISTS="手机号已经存在!";
	private final static String CODE_ERROR="验证码错误,请重新输入!";
	@Autowired
	private SMS sms ;//短信接口
	@Autowired
	private VerifyCode verifyCode;//验证码接口
	@Autowired
	private RealNameAuthMapper mapper;//实名认证数据库操作类
	@Autowired
	private RealNameAuthBusiness realNameAuthBusiness;//实名认证操作类
	
	/**
	 * 跳转到登录页面
	 */
	@RequestMapping(value="/toLogon",method=RequestMethod.GET)
	public String toLogon(){
		return "login";
	}
	/**
	 * 登录
	 * @param realNameAuthTask实名认证实体类
	 */
	@ResponseBody
	@RequestMapping(value="/logon",method=RequestMethod.POST,consumes = "application/json")
	public RealNameAuthTask logon(@RequestBody RealNameAuthTask realNameAuthTask){
		log.debug("logon phone:" + realNameAuthTask.getPhone() + " passwd:" + realNameAuthTask.getPasswd());
		boolean logon = true;
		if (logon){
			RealNameAuthTask task =new RealNameAuthTask();
			task.setPhone(realNameAuthTask.getPhone());
			task.setProcessName("实名认证");
			task.setTaskId("1");
			task.setTaskName("核名");
			return task;
		} else {
			return new RealNameAuthTask();
		}
	}
	/**
	 * 发送短信
	 * @param realNameAuthTask 实名认证实体类
	 * @return 字符串类型验证码
	 */
	@ResponseBody
	@RequestMapping(value="/sendCode",method=RequestMethod.POST,consumes = "application/json")
	public boolean sendCode(@RequestBody RealNameAuthTask realNameAuthTask){
		String code=this.verifyCode.generate(realNameAuthTask.getPhone(),2);//产生随机四位验证码
		return this.sms.sendMessage(realNameAuthTask.getPhone(),2,code)==0?true:false;//通过手机发送验证码;
	}
	/**
	 * 跳转至注册页面
	 */
	@RequestMapping(value="/toRegister" , method=RequestMethod.GET)
	public String toRegister(){
		return "register";
	}
	/**
	 * 执行注册操作
	 * @param realNameAuthTask 实名认证实体类
	 * @return true:注册验证成功,false：注册验证失败
	 */
	@SuppressWarnings("static-access")
	@ResponseBody
	@RequestMapping(value="/doRegister",method=RequestMethod.POST)
	public Message doRegister(@RequestBody RealNameAuthTask realNameAuthTask){
		 boolean flag = this.verifyCode.check(realNameAuthTask.getPhone(),realNameAuthTask.getCode());//验证验证码是否正确
		 Message message = new Message();
		 if(flag){
			 //判断注册手机号码是否存在
			 RealNameAuth realNameAuth = this.mapper.findByPhone(realNameAuthTask.getPhone());
			 if(null==realNameAuth){
				 //执行入库操作
				 this.realNameAuthBusiness.register(realNameAuthTask.getPhone(),realNameAuthTask.getCode());
				 message.setResult("success");
			 }else{
				 message.setMsg(this.PHONE_EXISTS);
				 message.setResult("false");
			 }
		 }else{
			 message.setMsg(this.CODE_ERROR);
			 message.setResult("false");
		 }
		 return message;
	}
	/**
	 * 跳转到核名页面
	 */
	@RequestMapping(value="/toCheckMessage" , method=RequestMethod.GET)
	public String toCheckMessage(){
		return"checkMessage";
	}
	
	/**
	 * 执行信息核名操作
	 */
	@ResponseBody
	@RequestMapping(value="/doCheckMessage",method=RequestMethod.POST)
	public Message doCheckMessage(@RequestBody RealNameAuthTask realNameAuthTask,HttpServletRequest request){
		 System.out.println(realNameAuthTask.getFile());
		 
		return new Message();
	}
	

	public void doUploadImage(HttpServletRequest request){
		//转型为MultipartHttpRequest(重点的所在)  
		MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;  
	}
	/**
	 * 执行密码修改操作
	 */
	@ResponseBody
	@RequestMapping(value="/doUpdatePassword",method=RequestMethod.POST,consumes = "application/json")
	public boolean doUpdatePassword(@RequestBody RealNameAuthTask realNameAuthTask){
		//判断验证码是否正确
		boolean flag = this.verifyCode.check(realNameAuthTask.getPhone(),realNameAuthTask.getCode());//验证验证码是否正确
		if(flag){
			log.debug("phone:"+realNameAuthTask.getPhone()+"password:"+realNameAuthTask.getPasswd()+"code:"+realNameAuthTask.getCode());
		}
		return true;
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
