//var __ctx="/bpmx";
var __ctx="";
var isProduct = false; //是否为生产环境

document.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
document.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no\">");
document.write("<meta name=\"renderer\" content=\"webkit\">");
document.write("<meta http-equiv=\"Cache-Control\" content=\"no-siteapp\" />");
document.write("<link rel=\"icon\" type=\"image/png\" href=\""+__ctx+"/weixin/assets/i/favicon.png\">");
document.write("<meta name=\"mobile-web-app-capable\" content=\"yes\">");
document.write("<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">");
document.write("<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\">");
document.write("<meta name=\"msapplication-TileColor\" content=\"#0e90d2\">");


if(isProduct) {
	var aryCss__ = "<link rel=\"stylesheet\" href=\""+__ctx + "/weixin/assets/css/??amazeui.min.css,font-awesome.min.css,app.css \">";

	var aryJs__ = "<script src=\""+__ctx +"/weixin/assets/js/??jquery.min.js,amazeui.js,handlebars.min.js,WxUtil.js\"></script>";
	
	/**
	 * js引入时导入必须的css样式。
	 */
	document.write(aryCss__);
	
	/**
	 * js引入时导入必须的js文件。
	 */
	document.write(aryJs__);

	/**
	 * 外部导入的js文件。
	 */
	function importJs(aryJs){
		var jsArr = [], jsNameArr = [], local, name;
		for(var i=0;i<aryJs.length;i++){
			local = aryJs[i].substr(1, aryJs[i].lastIndexOf("/"));
			name = aryJs[i].substr(aryJs[i].lastIndexOf("/") + 1);
			if(jsArr.indexOf(local) == -1) {
				jsArr.push(local);
				jsNameArr.push("??" + name);
			}else {
				jsNameArr[jsArr.indexOf(local)] += "," + name;
			}			
		}
		for(var i=0;i<jsNameArr.length;i++){
			var str="<script src=\""+__ctx + jsArr[i] + jsNameArr[i] +"\"></script>";
			document.write(str);
		}
	}
	/**
	 * 外部导入css。
	 */
	function importCss(aryCss){
		var cssArr = [], cssNameArr = [], local, name;
		for(var i=0;i<aryCss.length;i++){
			local = aryCss[i].substr(1, aryCss[i].lastIndexOf("/"));
			name = aryCss[i].substr(aryCss[i].lastIndexOf("/") + 1);
			if(cssArr.indexOf(local) == -1) {
				cssArr.push(local);
				cssNameArr.push("??" + name);
			}else {
				cssNameArr[cssArr.indexOf(local)] += "," + name;
			}
		}
		for(var i=0;i<cssNameArr.length;i++){
			var str="<link rel=\"stylesheet\" href=\""+__ctx + cssArr[i] + cssNameArr[i] +"\">";
			document.write(str);
		}
	}
}else {
	var aryCss__=["/weixin/assets/css/amazeui.min.css",
	              "/weixin/assets/css/font-awesome.min.css",
	            "/weixin/assets/css/app.css"];

	var aryJs__=["/weixin/assets/js/jquery.min.js",
	            "/weixin/assets/js/amazeui.js",
	            "/weixin/assets/js/handlebars.min.js",
	            "/weixin/assets/js/WxUtil.js"];
		
	/**
	 * js引入时导入必须的css样式。
	 */
	for(var i=0;i<aryCss__.length;i++){
		var str="<link rel=\"stylesheet\" href=\""+__ctx + aryCss__[i] +"\">";
		document.write(str);
	}

	/**
	 * js引入时导入必须的js文件。
	 */
	for(var i=0;i<aryJs__.length;i++){
		var str="<script src=\""+__ctx + aryJs__[i] +"\"></script>";
		document.write(str);
	}

	/**
	 * 外部导入的js文件。
	 */
	function importJs(aryJs){
		for(var i=0;i<aryJs.length;i++){
			var str="<script src=\""+__ctx + aryJs[i] +"\"></script>";
			document.write(str);
		}
	}
	/**
	 * 外部导入css。
	 */
	function importCss(aryCss){
		for(var i=0;i<aryCss.length;i++){
			var str="<link rel=\"stylesheet\" href=\""+__ctx + aryCss[i] +"\">";
			document.write(str);
		}
	}

}
