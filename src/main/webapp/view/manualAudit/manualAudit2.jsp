<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <base target="_self" />
<meta charset="gb2312">
<title>人工审核页面</title>
<script type="text/javascript" src="../../js/jquery-2.2.2.js"></script>
<script type="text/javascript" src="../../js/jquery-ui.js"></script>
<script type="text/javascript" src="../../js/jquery-rest.js"></script>
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
     var b;
    
	/**
     * 显示页面数据
     */
    function pageData(){
    	var datas='aa';
    	var processName=getQueryString("a");
    	if(null==processName){
    		processName='1';
    	}
    	$.restPost({
			 type:'post',
			 dataType: 'json',
			 contentType : "application/json",
			 url:'../../rest/realNameAuth/manualAudit',
			 data:{'processName':processName},
			 success:function(data){
				 var rows="";
				 var a=0;
				 for(var i in data){
				 a=parseFloat(i)+1;
				 rows =rows + "<tr>"+
				 "<td width='100'> "+a+"</td>"+
				 "<td width='100'> 人工审核</td>"+
				 "<td width='100'> "+data[i].id_name+"</td>"+
				 "<td width='200'> "+data[i].id_code+"</td>"+
				 "<td width='100'> "+data[i].phone+"</td>"+
				 "<td width='100'><input name='hi' type='button' value='审核' onClick='doExamine(\""+data[i].id_photo_url+"\",\""+data[i].face_url+"\",\""+data[i].phone+"\",\""+data[i].processname+"\")'/></td>"+ 
				 "</tr>" ;
			 }
		    	 $("#show_table").append(rows);//添加到table 
		 }
		});
    	
    	
	} 
 
	/**
	 * 获取url请求参数
	 */
	function getQueryString(name) {
	    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	    var r = window.location.search.substr(1).match(reg);
	    if (r != null) return unescape(r[2]); return null;
	}
	/**
	 * 审核 
	 */
	  function doExamine(a,b,c,d){
		var e=d;
		  if(d=="审核中"){
			  d='1';
		   }
		 if(d=="审核通过"){
			 d='2';
		  }
		 if(d=="审核不通过"){
			  d='3';
		  }
		  window.open('../manualAudit/auditPicture.html?a='+a+"&b="+b+"&c="+c+"&d="+d,'height=800, width=800, top=100,left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
		  //window.showModalDialog("../manualAudit/auditPicture.html","dialogWidth=230px;dialogHeight=100px");
	
	}
</script>
<style type="text/css">
.main td{ height:45px; }
</style>
</head >
<body onload='pageData()'>
 <p>&nbsp;</p>
 <p>&nbsp;</p>
 <p>&nbsp;</p>
<table width="70%" border="0" id="show_table"  cellpadding="0" cellspacing="0"  class="main"  style="vertical-align:top;table-layout:fixed;text-align:center;">
  <tr>
        <th  width="100">序号 </th>
        <th  width="100">批准方式  </th>
        <th  width="100">申请人 </th>
        <th  width="200">身份证号码 </th>
        <th  width="100">手机号码 </th>
        <th  width="100" >审核 </th>
   </tr>
   

</table>
</body>
</html>