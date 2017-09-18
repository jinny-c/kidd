<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<meta name="viewport"
	content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0 minimal-ui"></meta>
<meta name="apple-mobile-web-app-capable" content="yes"></meta>
<meta name="apple-mobile-web-app-status-bar-style" content="white"></meta>
<title>index</title>
<script type="text/JavaScript" src="static/js/jquery-1.9.1.min.js"></script>

<script type="text/javascript">

var _ctx = "http://127.0.0.1:8082/kidd";

function getVerficCode() {
	var type = "json";
	//type = "text";
	$.ajax({
		url : _ctx + '/wap/user/getVerificationCode_wap.htm',
		method : 'POST',
		data : {
			"mobile" : "13612341234",
			"channel" : "c"
		},
		dataType : type,
		//dataType : 'json',
		//dataType : 'text',
		success : function(data) {
			
			if('json'==type){
				buildHtml(data);
			}
			
			if('text'==type){
				var dataObj=eval("("+data+")");
				buildHtml(dataObj);
			}
			
		}
	});
}

//构建内容  
function buildHtml(jsonData){
	if(jsonData){
        var tr = [
            '<tr>',
              '<td>',jsonData.channel,'</td>',
              '<td>',jsonData.imageCode,'</td>',
              '</tr>'  
        ].join('');
        $("#gripTablebody").append(tr);
        
        /* var tr = [
            '<tr>',
              '<td>',jsonData.verifyCode,'</td>',
              '<td>',jsonData.imageCode,'</td>',
              '</tr>'  
        ].join('');
        $("#gripTablebody").append(tr); */
        
	}
}
//构建内容  
function buildHtml_list(list){
    $.each(list,function(i,val){
        var tr = [
            '<tr>',
              '<td>',val.noticeId,'</td>',
              '<td>',val.title,'</td>',
              '</tr>'
        ].join('');
        $("#gripTablebody").append(tr);
    });
}
</script>
</head>
<body>
	<div class="weui_msg">
		<div class="weui_icon_area">
			<img src="static/images/succ_green.png" /> <span class="pay_msg"><h2>projects
					is deployed successfully</h2></span>
		</div>
	</div>

	<div>
		<div>
			<a id="reg_btn" href="#" onclick="getVerficCode()">getVerficCode/wap</a>
			<table style="width: 100%">
				<tbody id="gripTablebody">
				</tbody>
			</table>
		</div>

		<div class="preview_item">
			<br />
			<ul>
				<li><a href="wap/user/index.htm">index</a></li>
			</ul>
			<ul>
				<li><a href="wap/user/info.htm">info</a></li>
			</ul>
		</div>
	</div>
</body>
</html>
