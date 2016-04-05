package com.zxhy.xjl.rna.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zxhy.xjl.notification.sms.SMS;
import com.zxhy.xjl.notification.verifyCode.VerifyCode;
import com.zxhy.xjl.rna.web.model.RealNameAuthTask;


@Controller
@RequestMapping("/realNameAuth")
public class RealNameAuthController {
	private static final Log log = LogFactory.getLog(RealNameAuthController.class);
	@Autowired
	private SMS sms ;//短信接口
	@Autowired
	private VerifyCode verifyCode;//验证码接口
	
	//参考webController中的例子，使用requestbody接受客户端传递过来的参数，注意方法是post
	@ResponseBody
	@RequestMapping(value="/logon",method=RequestMethod.POST)
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
	/**
	 * 发送短信
	 * @param phone 发送短信目标手机号
	 * @return 字符串类型验证码
	 */
	@ResponseBody
	@RequestMapping("/sendCode")
	public boolean sendCode(@RequestParam(name="phone") String phone,HttpServletResponse response){
		String code=this.verifyCode.generate(phone,1);//产生随机四位验证码
		return this.sms.send(phone,code);//通过手机发送验证码;
	}
	/**
	 * 执行注册操作
	 * @param phone 注册手机号
	 * @param code  验证码
	 */
	@RequestMapping("/doRegister")
	public void doRegister(@RequestParam(name="phone") String phone,@RequestParam(name="code") String code,HttpServletRequest request,HttpServletResponse resphonse){
		boolean flag = this.verifyCode.check(phone,code);//验证验证码是否正确
		if(flag){
			//进入信息核名页面
				try {
					request.getRequestDispatcher("/view/checkMessage.html").forward(request, resphonse);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}else{
			//返回注册页面给与提示
			try {
				resphonse.sendRedirect(request.getHeader("Referer"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 执行信息核名操作
	 */
	@RequestMapping("/doCheckMessage")
	public void doCheckMessage(HttpServletRequest request,HttpServletResponse resphonse){
		try {
			request.getRequestDispatcher("/view/showPhoto.html").forward(request, resphonse);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 执行密码修改操作
	 */
	@RequestMapping("/doUpdatePassword")
	public void doUpdatePassword(HttpServletRequest request,HttpServletResponse resphonse){
		try {
			resphonse.sendRedirect(request.getHeader("Referer"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
