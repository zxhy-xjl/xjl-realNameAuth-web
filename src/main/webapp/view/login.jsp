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
<title>登录</title>
<link rel="stylesheet" href="css/jquery-ui.css" media="screen" >
<link rel="stylesheet" href="css/style.css" media="screen" >
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script type="text/javascript">
    /**
     * 初始化
     */
	$(function() {
		//按钮样式
	    $("button").button()
	      .click(function( event ) {
	        event.preventDefault();
	      });
	});	
	/**
     * 登录
     */
    function doLogin(){
    	var phone=$("#phone");
    	var pwd=$("#pwd");
    	if(phone.val()==""){
    		showError('对不起,手机号不能为空！');
    		return;
    	}
    	if(phone.val().length!=11){
    		showError('对不起,手机号长度不能超过或者低于11位,请重新输入！');
    		return;
    	}
    	if(pwd.val()==""){
    		showError('对不起,密码不能为空！');
    		return;
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
     * 跳转至注册页面
     */
    function toRegister(){
    	window.location.href='../view/register.jsp';
    }
</script>
</head>
<body>
<form  id="loginForm" method="post">
<table  width="100%"  class="table_class">
	<tr >
		<td class="showError" align="center" >
			<div id="showError" class="showError_div"></div>
		</td>
	</tr>
	<tr>
		<td>用户名：<input id="phone" class="input" name="phone" type="text"/></td>
	</tr>
	<tr>
		<td>密&nbsp;&nbsp;&nbsp;&nbsp;码：<input id="pwd" class="input" name="pwd" type="password"/></td>
	</tr>
	<tr >
		<td><button id="login" class="button"  onclick="doLogin()">登录</button>
	</tr>
	<tr>
		<td><button id="register" class="button"  onclick="toRegister()" >注册</button>
	</tr>
	<tr>
		<td><a href="view/forgetpassword.jsp" class="retrieve" >忘记密码？</a></td>
	</tr>
</table>
</form>
</body>
</html>