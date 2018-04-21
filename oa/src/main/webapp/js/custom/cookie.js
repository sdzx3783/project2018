function setCookieHours(name, value, expireHours) {
	var cookieString = name + "=" + escape(value);
	var date = new Date();
	date.setTime(date.getTime + expireHours * 3600 * 1000);
	cookieString = cookieString + "; expire=" + date.toGMTString();
	document.cookie = cookieString;
}

function setCookie(c_name, value, expiredays, path) {
	var exdate = new Date()
	exdate.setDate(exdate.getDate() + expiredays)
	document.cookie = c_name + "=" + escape(value)
			+ ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
			+ ((path == null) ? "" : ";path=" + path)
}

// 清除 cookie
function clearCookie() {
	var strCookie = document.cookie;
 
	var arrCookie = strCookie.split("; ");
	for ( var i = 0; i < arrCookie.length; i++) {
		var arr = arrCookie[i].split("=");
		
		setCookie(arr[0], "", -1,"/");
	}
}

//删除COOIKIE
function delCookie(cookieName)
{
	var strCookie = document.cookie;
	 
	var arrCookie = strCookie.split("; ");
	for ( var i = 0; i < arrCookie.length; i++) {
		var arr = arrCookie[i].split("=");
		if(cookieName == arr[0] ){
			setCookie(arr[0], "", -1,"/");
		}
		
	}
}


function getCookie(c_name) {
	return getParam(document.cookie, c_name, ";");
}

function cookieEnable() {
	var result = false;
	if (navigator.cookiesEnabled) {
		return true;
	}
	document.cookie = "testcookie=yes;";
	var cookieSet = document.cookie;
	if (cookieSet.indexOf("testcookie=yes") > -1) {
		result = true;
	} else {
		document.cookie = "";
	}
	return result;
}

function getParam(params, name, delimiter) {
	var mark = "&";
	if (delimiter) {
		mark = delimiter;
	}
	if (params.length > 0) {
		start = params.indexOf(name + "=")
		if (start != -1) {
			start = start + name.length + 1
			end = params.indexOf(mark, start)
			if (end == -1) {
				end = params.length;
			}
			var value=params.substring(start, end);
			value=decodeURI(value);
			return value;
		}
	}
	return ""
}