var app = angular.module('app',['baseServices','commonListService','arrayToolService']);
app.controller("ctrl",['$scope','BaseService','CommonListService','ArrayToolService',function($scope,BaseService,CommonListService,ArrayToolService){
	$scope.CommonList=CommonListService;
	$scope.ArrayTool=ArrayToolService;
	
	//external对象
	$scope.external={};
	
	//初始化字段类型
	$scope.external.dbType={};
	$scope.external.dbType.type="varchar";
	$scope.external.dbType.length="1000";
	
	//初始化字段来源
	$scope.external.valueFrom={};
	$scope.external.valueFrom.value="0";
	$scope.external.valueFrom.content="";
	
	$scope.external.cols="20";
	$scope.external.rows="3";
	
	$scope.init = function(){
		initSerialnum();
		initRule();
		
		if(targetEl){//编辑模式
			var external=$(targetEl).attr("external").replace(new RegExp(/(&#39;)/g),"'");
			$scope.external = eval("("+external+")");
		}
	};
	
	//是否货币
	$scope.eIsCoinChange = function(){
		if($scope.external.dbType.isCoin=='1'){
			$scope.external.dbType.isShowComdify="1";
		}
	};
	
	//选择脚本
	$scope.addScript = function(){
		ScriptDialog({
			callback : function(data) {
				$scope.$apply(function() {
					$scope.external.valueFrom.content+=data;
				});
			}
		});
	};
	
	//初始化流水号
	function initSerialnum(){
		var url = __ctx + "/platform/system/identity/getAllIdentity.ht";
		BaseService.post(url,{},function(data){
			$scope.serialnumList=data;
		});
	}
	
	//初始化规则列表
	function initRule(){
		var url = __ctx + "/platform/form/bpmFormRule/getAllRules.ht";
		BaseService.post(url,{},function(data){
			$scope.ruleList=data;
		});
		
	}
}]);