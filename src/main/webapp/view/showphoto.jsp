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
<title>照片信息展示</title>
<link rel="stylesheet" href="css/jquery-ui.css">
<link rel="stylesheet" href="css/style.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
</head>
<body>
<table  width="100%"  class="showPhotoTable_class">
	<tr>
		<td colspan="2">
			<img alt="" src="images/photomessage.png">
		</td>
	</tr>
	<tr>
		<td class="td_class" align="center">
			<div style="width:322px;height:275px;"><img alt="" src="images/find.png"></div>
		</td>
		<td class="td_class" align="center">
			<div style="width:293px; height:250px;"><img alt="" src="images/photo.png"></div>
		</td>
	</tr>
</table>
</body>
</html>