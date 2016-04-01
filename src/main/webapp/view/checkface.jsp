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
<title>人脸识别</title>
<link rel="stylesheet" href="css/jquery-ui.css">
<link rel="stylesheet" href="css/style.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
</head>
<body>
<table  width="100%"  class="checkFaceTable_class">
	<tr>
		<td class="td_class" align="center">
			<div style="width:303px;height:268px;"><img alt="" src="images/checkface.png"></div>
		</td>
		<td class="td_class" align="center">
			<div style="width:202px;height:254px;"><img alt="" src="images/two-dimension code.png"></div>
		</td>
	</tr>
</table>
</body>
</html>