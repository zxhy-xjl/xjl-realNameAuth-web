<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>"> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册</title>
<link rel="stylesheet" href="css/jquery-ui.css">
<link rel="stylesheet" href="css/style.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script type="text/javascript">
	/**
	 * 初始化
	 */
	 var count;
	$(function() {
		var msg="${msg}";
		if(msg!=""){
			if(msg=="success"){
				showError("验证失败,请重新发送验证码！");
			}
		}
		//按钮样式
	    $("button").button()
	      .click(function( event ) {
	        event.preventDefault();
	      });
	});	
	/**
     * 发送验证码
     */
	function doSendCode(){
		var phone = $("#phone");
		if(phone.val()==""){
			showError("对不起,手机号不能为空!");
			return;
		}
		//隐藏错误提示
		hideError();
		//发送短信
		$.ajax({
			url:'rest/realNameAuth/sendCode',
			type:'post',
			dataType:"json",
			data:{'phone':phone.val()},
			success:function(data){
				var sendCode = $("#sendCode");
				if(true==data){
					showError("短信已经发送至您的手机，请查收!");
					//停止按钮事件
					sendCode.attr("disabled","disabled");
					//改变按钮背景颜色
					sendCode.css('background','#B5B5B5');
					//调用倒计时
					count=61;
					countDown();
					$("#sign").val('');
				}
			}
		});
	}
	/**
     * 确认
     */
	function doAffirm(){
		var phone = $("#phone");
		var code = $("#code");
		var sign = $("#sign");
		var successMessage="验证码将作为您的登录密码,请妥善保存或及时更改密码!";
		if(phone.val()==""){
			showError("对不起,手机号不能为空!");
			return;
		}
		if(code.val()==""){
			showError("对不起,验证码不能为空!");
			return;
		}
		if(sign.val()==1){
			showError("短信验证码已经失效,请重新获取!");
			return;
		}
		$("#registerForm").submit();
	}
	/**
     * 计数
     */
	function countDown(){
		var msg="";
		if(count!=0){
			count--;
			msg="(请在"+count+"秒之内输入验证码)";
			$("#showCount").html(msg);
			//设置定时任务
			setTimeout(function(){countDown()},1000) 
		}else{
			var sendCode = $("#sendCode");
			//启动按钮事件
			sendCode.removeAttr("disabled");
			//恢复按钮可用颜色
			sendCode.css('background','#F6F6F6')
			//清空错误信息提示
			$("#showCount").html('');
			//设置失效标记
			$("#sign").val('1');
		}
	}
	/**
     * 显示错误提示信息
     */
	function showError(content){
		var showError=$("#showError");
		showError.show();
		showError.html(content);
	}
	/**
     * 隐藏错误提示信息
     */
	function hideError(){
		var showError=$("#showError");
		showError.hide();
	}
</script>
</head>
<body>
<form id="registerForm" name="registerFormName" action="rest/realNameAuth/doRegister"  method="post">
<input id="sign" type="hidden"/>
<table  width="100%"  class="table_class">
	<tr>
		<td>手机号码：<input id="phone" class="input" name="phone" type="text"/></td>
	</tr>
	<tr>
		<td><button  id="sendCode" class="button" onclick="doSendCode();">发送验证码</button></td>
	</tr>
	<tr>
		
		<td><span id="showCount"  class="showCount_class" ></span><br/>验&nbsp;&nbsp;证&nbsp;&nbsp;码：<input id="code" class="input" name="code" type="text"/></td>
	</tr>
	<tr>
		<td><button id="affirm" class="button" onclick="doAffirm();" >确认</button></td>
	</tr>
	<tr>
		<td class="showError" align="center" >
			<div id="showError" class="showError_div"></div>
		</td>
	</tr>
</table>
</form>
</body>
</html>