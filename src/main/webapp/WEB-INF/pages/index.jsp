<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    //response.setHeader("Access-Control-Allow-Origin", "*");
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<meta name="viewport"
	content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0 minimal-ui"></meta>
<meta name="apple-mobile-web-app-capable" content="yes"></meta>
<meta name="apple-mobile-web-app-status-bar-style" content="white"></meta>

<meta http-equiv="Access-Control-Allow-Origin" content="*">

<title>index</title>
<script type="text/JavaScript" src="static/js/jquery-1.9.1.min.js"></script>

<script type="text/javascript">

var _ctx = "http://127.0.0.1:8082/kidd";
//var _ctx = "http://10.148.21.80:8082/kidd";
/* 同一个ip、同一个网络协议、同一个端口，三者都满足就是同一个域，否则就是跨域问题了 */

function getVerficCode(wildcard) {
	var type = "json";
	//type = "text";
	//type = "jsonp";
	$.ajax({
		url : _ctx + '/wap/user/getVerificationCode_'+wildcard+'.htm',
		method : 'POST',
		data : {
			"mobile" : "13612341234",
			"channel" : "channel",
			"clientChannel" : "clientChannel"
		},
		dataType : type,
		//dataType : 'json',
		//dataType : 'text',
		success : function(data) {
			
				if ('json' == type || 'jsonp' == type) {
					buildHtml(data);
				}

				if ('text' == type) {
					var dataObj = eval("(" + data + ")");
					buildHtml(dataObj);
				}

			}
		});
	}

	//构建内容  
	function buildHtml(jsonData) {
		if (jsonData) {
			if ('0000' != jsonData.responseCode) {
				var tr = [ '<tr>', '<td>', jsonData.responseCode, '</td>',
						'<td>', jsonData.errMessage, '</td>', '</tr>' ]
						.join('');
				$("#gripTablebody").append(tr);
			} else {
				var tr = [ '<tr>', '<td>', jsonData.channel, '</td>', '<td>',
						jsonData.imageCode, '</td>', '</tr>' ].join('');
				$("#gripTablebody").append(tr);
			}

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
	function buildHtml_list(list) {
		$.each(list, function(i, val) {
			var tr = [ '<tr>', '<td>', val.noticeId, '</td>', '<td>',
					val.title, '</td>', '</tr>' ].join('');
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
			<a id="reg_btn" href="#" onclick="getVerficCode('imageCode')">getVerficCode/imageCode</a>
			<br/>
			<a id="reg_btn" href="#" onclick="getVerficCode('wap')">getVerficCode/wap</a>
			<br/>
			<a id="reg_btn" href="#" onclick="getVerficCode('other')">getVerficCode/other</a>
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
				<li><a href="wap/user/index.htm?code=code">index_code</a></li>
			</ul>
			<ul>
				<li><a href="wap/user/toInfo.htm">toInfo</a></li>
			</ul>
			<ul>
				<li><a href="wap/user/toLogin.htm">toLogin</a></li>
			</ul>
			<ul>
				<li><a href="wap/user/login.htm">login</a></li>
			</ul>
			<ul>
				<li><a href="wap/prize/showPrizeIndex.htm">Luck Draw</a></li>
			</ul>
			<ul>
				<li><a href="wap/user/toQRCode.htm">toQRCode</a></li>
			</ul>
			<ul>
				<li>
					<a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb17ce3d03ed8073e&redirect_uri=http://10.148.21.80:8082/kidd/wap/user/wechat.htm?pubId=gh_d8ca418ebb2b&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect">wechat</a>
				</li>
			</ul>
		</div>
		<div class="preview_item">
			<br />
			<ul>
				<li><a href="view/user/index.do">index</a></li>
			</ul>
			<ul>
				<li><a href="view/user/toInfo.do">toInfo</a></li>
			</ul>
		</div>
	</div>
</body>
</html>
