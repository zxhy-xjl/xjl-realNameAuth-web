<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>"> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>信息核对</title>
<link rel="stylesheet" href="css/jquery-ui.css">
<link rel="stylesheet" href="css/style.css">
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
     * 提交
     */
	function doSubmit(){
		var cardId=$("#cardId");
		var name=$("#name");
		if(cardId.val()==""){
			showError("对不起,身份证号不能为空!");
			return;
		}
		//验证身份证
		var re = new RegExp(/^\d{15}(\d{2}[A-Za-z0-9])?$/); 
		var result=cardId.val().match(re);
		if(result==null){
			showError("对不起,请输入正确的身份证号!");
			return;
		}
		if(name.val()==""){
			showError("对不起,姓名不能为空!");
			return;
		}
		//隐藏错误提示
		hideError();
		$("#checkMessage").submit();
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
<form id="checkMessage" action="rest/realNameAuth/doCheckMessage" method="post" ></form>
<table  width="100%"  class="table_class">
	<tr align="center">
		<td>身份证号：<input id="cardId" class="input" name="cardId" type="text"/></td>
	</tr>
	<tr align="center">
		<td>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：<input id="name" class="input" name="name" type="text"/></td>
	</tr>
	<tr align="center">
		<td><button id="submit" class="button" onclick="doSubmit();" >提交</button></td>
	</tr>
	<tr>
		<td class="showError" align="center" >
			<div id="showError" class="showError_div"></div>
		</td>
	</tr>
</table>
</body>
</html>