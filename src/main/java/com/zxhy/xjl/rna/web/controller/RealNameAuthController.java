package com.zxhy.xjl.rna.web.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.zxhy.xjl.notification.sms.SMS;
import com.zxhy.xjl.notification.verifyCode.VerifyCode;
import com.zxhy.xjl.rna.business.RealNameAuthBusiness;
import com.zxhy.xjl.rna.fileService.RealNameAuthFileService;
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
	private final static String POWD_ERROR="密码错误!";
	private final static String PHONE_NOTEXISTS="账号不存在!";
	@Autowired
	private SMS sms ;//短信接口
	@Autowired
	private VerifyCode verifyCode;//验证码接口
	@Autowired
	private RealNameAuthMapper mapper;//实名认证数据库操作类
	@Autowired
	private RealNameAuthBusiness realNameAuthBusiness;//实名认证操作类
	@Autowired
	private RealNameAuthFileService realNameAuthFileService;//文件管理类
	
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
	@RequestMapping(value="/doLogon",method=RequestMethod.POST,consumes = "application/json")
	public RealNameAuthTask logon(@RequestBody RealNameAuthTask realNameAuthTask){
		log.debug("logon phone:" + realNameAuthTask.getPhone() + " passwd:" + realNameAuthTask.getPasswd());
		String  logon = null;
		logon=this.realNameAuthBusiness.logon(realNameAuthTask.getPhone(), realNameAuthTask.getPasswd());
		if ("success".equals(logon)){
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
	 * 跳转到管理员登录页面
	 */
	@RequestMapping(value="/toLogonAdmin",method=RequestMethod.GET)
	public String toLogonAdmin(){
		return "adminLogin";
	}
	/**
	 * 登录
	 * @param Admin 管理员登录
	 */
	@ResponseBody
	@SuppressWarnings("static-access")
	@RequestMapping(value="/doLogonAdmin",method=RequestMethod.POST,consumes = "application/json")
	public Message doLogonAdmin(@RequestBody com.zxhy.xjl.rna.web.model.Admin Admin){
		log.debug("logon name:" + Admin.getAccountNumber()+ " passwd:" + Admin.getPasswd());
		String  logon=realNameAuthBusiness.logon(Admin.getAccountNumber(), Admin.getPasswd());
		Message message = new Message();
		if ("success".equals(logon)){
			 message.setResult("success");
	    	 return  message;
		}if ("passwordError".equals(logon)){
			 message.setResult("false");
			 message.setMsg(this.POWD_ERROR);
			 return  message;
		}else{
			 message.setResult("false");
			 message.setMsg(this.PHONE_NOTEXISTS);
			 return  message;
		}
	}
	/**
	 * 跳转到管理员登录页面
	 */
	@RequestMapping(value="/toIndex",method=RequestMethod.GET)
	public String toIndex(){
		return "index";
	}
	/**
	 * 注册：发送短信
	 * @param realNameAuthTask 实名认证实体类
	 * @return 字符串类型验证码
	 */
	@ResponseBody
	@SuppressWarnings("static-access")
	@RequestMapping(value="/sendCode",method=RequestMethod.POST,consumes = "application/json")
	public Message sendCode(@RequestBody RealNameAuthTask realNameAuthTask){
		 RealNameAuth realNameAuth = this.mapper.findByPhone(realNameAuthTask.getPhone());
		 Message message = new Message();
		//判断注册手机号码是否存在
		 if(null==realNameAuth){
			 String code=this.verifyCode.generate(realNameAuthTask.getPhone(),2);//产生随机四位验证码
			 String content="门户网站，" + code + "是您本次身份校验码，" + 2 + "分钟内有效．审批局工作人员绝不会向您索取此校验码，切勿告知他人．";
			 this.sms.send(realNameAuthTask.getPhone(),content);//通过手机发送验证码;
			 message.setResult("success");
		 }else{
			 //号码已存在
			 message.setMsg(this.PHONE_EXISTS);
			 message.setResult("false");
		 }
		 return message;
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
	@ResponseBody
	@SuppressWarnings("static-access")
	@RequestMapping(value="/doRegister",method=RequestMethod.POST)
	public Message doRegister(@RequestBody RealNameAuthTask realNameAuthTask){
		 boolean flag = this.verifyCode.check(realNameAuthTask.getPhone(),realNameAuthTask.getCode());//验证验证码是否正确
		 Message message = new Message();
		 //判断验证码是否正确
		 if(flag){
			 //执行入库操作
			 this.realNameAuthBusiness.register(realNameAuthTask.getPhone(),realNameAuthTask.getCode());
			 message.setResult_phone(realNameAuthTask.getPhone());
			 message.setResult("success");
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
	@RequestMapping(value="/doCheckMessage",method=RequestMethod.POST)
	public String doCheckMessage(@RequestParam(name="cardId") String cardId,@RequestParam(name="name") String name,
			@RequestParam(name="phone") String phone,@RequestParam(value = "file_1", required = false) MultipartFile file_1,
			@RequestParam(value = "file_2", required = false) MultipartFile file_2,HttpServletRequest request){
		 String outPath = request.getSession().getServletContext().getRealPath("/");// 文件保存文件夹，也可自定为绝对路径
		 if(null!=file_1){
			 this.realNameAuthFileService.doUploadImage(file_1, outPath, phone+"sfz");//身份证照片
		 }
		 if(null!=file_2){
			 this.realNameAuthFileService.doUploadImage(file_2, outPath, phone);//人员照片
		 }
		 com.zxhy.xjl.rna.business.RealNameAuthTask task  = this.realNameAuthBusiness.getRealNameAuthTask(phone);//获取taskID
		 this.realNameAuthBusiness.checkRealName(phone,cardId,name,task.getTaskId());//执行核名操作
		 return "login";
	}
	
	/**
	 * 忘记密码：发送验证码
	 * @param realNameAuthTask 实名认证实体类
	 * @return
	 */
	@ResponseBody
	@SuppressWarnings("static-access")
	@RequestMapping(value="/SendCodeForUpdatePassword",method=RequestMethod.POST,consumes = "application/json")
	public Message SendCodeForUpdatePassword(@RequestBody RealNameAuthTask realNameAuthTask){
		RealNameAuth realNameAuth = this.mapper.findByPhone(realNameAuthTask.getPhone());//根据phone获取账号信息
		Message message = new Message();
		//判断是否存在
		if(null!=realNameAuth){
			 String code=this.verifyCode.generate(realNameAuthTask.getPhone(),2);//产生随机四位验证码
			 String content="门户网站，" + code + "是您本次身份校验码，" + 2 + "分钟内有效．审批局工作人员绝不会向您索取此校验码，切勿告知他人．";
			 this.sms.send(realNameAuthTask.getPhone(),content);//通过手机发送验证码;
			 message.setResult("success");
		}else{
			//不存在
			message.setResult("false");
			message.setResult(this.PHONE_NOTEXISTS);
		}
		 return message;
	}
	/**
	 * 跳转至忘记密码页面
	 */
	@RequestMapping(value="/toUpdatePassword" , method=RequestMethod.GET)
	public String toUpdatePassword(){
		return "forgetPassword";
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
