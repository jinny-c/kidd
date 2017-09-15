function validateMobile(mobile) {
	//获取手机号码 去除空格
	var m = mobile.value.replace(/\s+/g,""); 
	if(m.length != 11){
		showEorrTips('请输入完整注册手机号');
		return;
	}else if(!(/^1[3|4|5|7|8]\d{9}$/.test(m))){
		showEorrTips('请输入正确的手机号');
		return;
	}
	return true;
}

// 电话号码格式化 (136 1234 1234)
function formatMobile(mobile) {
	var account = new String(mobile.value);
	account = account.substring(0, 13); /* 帐号的总数, 包括空格在内 */
	if (account.match(".[0-9]{3}-[0-9]{4}-[0-9]{4}") == null) {
		/* 对照格式 */
		if (account.match(".[0-9]{3}-[0-9]{4}-[0-9]{4}|"
				+ ".[0-9]{3}-[0-9]{4}-[0-9]{4}|"
				+ ".[0-9]{3}-[0-9]{4}-[0-9]{4}") == null) {
			var accountNumeric = accountChar = "", i;
			for (i = 0; i < account.length; i++) {
				accountChar = account.substr(i, 1);
				if (!isNaN(accountChar) && (accountChar != " "))
					accountNumeric = accountNumeric + accountChar;
			}
			account = "";
			for (i = 0; i < accountNumeric.length; i++) { /* 可将以下空格改为-,效果也不错 */
				if (i == 3)
					account = account + " "; /* 帐号第四位数后加空格 */
				if (i == 7)
					account = account + " "; /* 帐号第八位数后加空格 */
				account = account + accountNumeric.substr(i, 1)
			}
		}
	} else {
		account = account.substring(1, 3) + " " + account.substring(4, 8) + " "
				+ account.substring(12, 16);
	}
	if (account != mobile.value)
		mobile.value = account;
}

//用户名称验证
function validateName(name){
	if(!/^[a-zA-Z0-9]+$/.test(name.value)){
		showEorrTips('只能由字母、数字组成！');
		return false;
	}
	return true;
}
//倒计时
var _seconds=0;
var _interval = {
		_clear(_target){
			_seconds=0;
			_target.text('获取验证码');
			_target.removeClass('deactive');
			_target.removeAttr("disabled");
			_window_int = window.clearInterval(_window_int);
		},
		_remaining(_target){
			_target.attr('disabled', 'disabled');
			_target.addClass('deactive');
	    	
			_window_int = setInterval(function(){
				if (_seconds < 5) {
					_target.text('等待 ' + (5 - _seconds) + ' 秒');
					_seconds++;
				} else {
					_target.text('获取验证码');
					_target.removeClass('deactive');
					_target.removeAttr("disabled");
					_window_int = window.clearInterval(_window_int);
					_seconds = 0;
				}
			}, 1000);
		}
};

//生产图形验证码
var _canvas;
function initCanvas(_date,_target,_func){
	if(_canvas){
		_canvas._change(_date);
		return ;
	}
	_canvas = {
			_onclick(_func){
				mycanvas.onclick=function(e){
					e.preventDefault(); // 阻止鼠标点击发生默认的行为
					_func();
				};
			},
			_change(_date){
				var _cxt = _target[0].getContext('2d');
				_cxt.fillStyle = '#000';// 填充色
				_cxt.fillRect(0, 0, 90, 40);
				/* 生成干扰线20条 */
				for (var j = 0; j < 20; j++) {
					_cxt.strokeStyle = '#FF6100';// 干扰线条颜色
					_cxt.beginPath(); // 若省略beginPath，则每点击一次验证码会累积干扰线的条数
					_cxt.moveTo(this._lineX(), this._lineY());
					_cxt.lineTo(this._lineX(), this._lineY());
					_cxt.lineWidth = 0.5;
					_cxt.closePath();
					_cxt.stroke();
				}
				_cxt.fillStyle = 'white';// 验证码颜色
				_cxt.font = 'bold 20px Arial';// 字体
				_cxt.fillText(_date, 25, 25); // 把rand()生成的随机数文本填充到canvas中
			},
			/* 干扰线的随机x坐标值 */
			_lineX(){
				var ranLineX=Math.floor(Math.random()*90);
				return ranLineX;
			},
			/* 干扰线的随机y坐标值 */
			_lineY(){
				var ranLineY=Math.floor(Math.random()*40);
				return ranLineY;
			}
	};
	initCanvas(_date,_target,_func);
	
	_canvas._onclick(_func);
}


// 更换title的值
var userAgent = navigator.userAgent;
var cookiestr = document.cookie;
if (userAgent.indexOf('cdvsupport') >= 0
		|| cookiestr.indexOf('cdvsupport') >= 0) {
	// alert('nativejs')
	document
			.write("<script type='text/javascript' src='nacodovafile/js/cdplugins.js#nacodovafile'><\/script>");
	document
			.write("<script type='text/javascript' src='nacodovafile/js/cordova.js#nacodovafile'><\/script>");
}
function deviceReadyOne(cb_back) {
	var deviceReady = function() {
		document.removeEventListener("deviceready", deviceReady, false);
		if ('function' == typeof cb_back) {
			cb_back();
		}
	}
	document.addEventListener("deviceready", deviceReady, false);
}

function setPageTitle(title) {
	document.title = title;
	var callback = function() {
		try {
			window.CD58UtilsPlugin.setTitle(title);
		} catch (e) {
		}
	}
	if (window.CD58UtilsPlugin) {
		callback();
	} else {
		deviceReadyOne(callback);
	}
}