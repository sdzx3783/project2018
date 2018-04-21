var app = angular.module('app',['baseServices','commonListService','arrayToolService']);
app.controller("ctrl",['$scope','BaseService','CommonListService','ArrayToolService',function($scope,BaseService,CommonListService,ArrayToolService){
	$scope.CommonList=CommonListService;
	$scope.ArrayTool=ArrayToolService;
	
	//external对象
	$scope.external={};
	
	//初始化字段类型
	$scope.external.dbType={};
	$scope.external.dbType.type="varchar";
	$scope.external.dbType.length="50";
	
	//初始化字段来源
	$scope.external.valueFrom={};
	$scope.external.valueFrom.value="0";
	$scope.external.valueFrom.content="";
	
	//选择器的配置
	$scope.external.buttoncontent="选择";
	$scope.external.singleselect="0";
	$scope.external.scope={};
	$scope.external.scope.value="all";
	$scope.external.scope.type="system";
	
	$scope.init = function(){
		initRule();
		
		if(targetEl){//编辑模式
			var external=$(targetEl).attr("external").replace(new RegExp(/(&#39;)/g),"'");
			$scope.external = eval("("+external+")");
		}
	};
	
	$scope.scopeTypeChange = function(){
		if($scope.external.scope.type=="system"){
			$scope.external.scope.value="all";
		}else{
			$scope.external.scope.value="";
		}
	};
	
	//选择脚本
	$scope.addScript = function(){
		ScriptDialog({
			callback : function(data) {
				$scope.$apply(function() {
					$scope.external.scope.value+=data;
				});
			}
		});
	};
	
	//初始化规则列表
	function initRule(){
		var url = __ctx + "/platform/form/bpmFormRule/getAllRules.ht";
		BaseService.post(url,{},function(data){
			$scope.ruleList=data;
		});
		
	}
}]);