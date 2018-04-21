var  app = angular.module("app", ['flowServiceModule','formDirective']);
app.controller('ctrl', ['$scope','flowService',function($scope,flowService){
	
	
	var runId=HtUtil.getParameter("runId");
	var defId=HtUtil.getParameter("defId");
	
	/**
	 * 显示审批历史。
	 */
	$scope.showOpinion=function(){
		flowService.showOpinion(runId);
	};
	
	/**
	 * 手机流程图
	 */
	$scope.showFlowPic=function(){
		flowService.showFlowPic(runId);
	}
	
	/**
	 * 初始化表单。
	 */
	$scope.initForm=function(){
		//app隐藏底部按钮
		var source=getUrlParam("source");
		if(source!="" && source!=undefined && source=="app"){
		}else{
			$("body").removeClass("appnotviewapp");
		}
		var json=HtUtil.getJSON("form_" +defId);
		var defer=null;
		if(json==null){
			defer=flowService.getInstForm(runId);
		}
		else{
			defer=flowService.getInstForm(runId,json.formKey,json.version);
		}
		defer.then(function(data){
			flowService.handForm($scope,data,defId);
		},
		function(status){
			console.info(status);
		});
	};
	//初始化表单。
	$scope.initForm();
}]);
//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}
