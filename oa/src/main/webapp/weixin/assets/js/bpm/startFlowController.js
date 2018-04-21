var  app = angular.module("startFlow", ['flowServiceModule','formDirective']);
app.controller('ctrl', ['$rootScope','$scope','flowService',function($rootScope,$scope,flowService){
	
	//获取流程定义ID
	var defId=HtUtil.getParameter("defId");
	var actDefId=HtUtil.getParameter("actDefId");
	var pk=HtUtil.getParameter("pk");
	var flowKey=HtUtil.getParameter("flowKey");
	var subject=decodeURIComponent(HtUtil.getParameter("subject"));
	var runId=HtUtil.getParameter("runId");
	$scope.pk=pk;

	/**
	 * 显示流程图
	 */
	$scope.showFlowPic=function(){
		var url=__ctx +"/bpmImage?definitionId="+actDefId;
		var conf={};
		conf.title="流程图";
		conf.url=url;
		createPopupDialog(conf,'flowStartDialog');
	}
	
	
	/**
	 * 添加子表行
	 */
	$scope.addRow=function(tableName){
		flowService.addRow($scope,tableName);
		return false;
	};
	/**弹出框编辑子表行*/
	$scope.editRow=function(tableName,index){
		flowService.editRow($scope,tableName,index);
		return false;
	};
	/**将弹出框子表清空**/
	$scope.cleansubTempData=function(tableName){
		$scope.subTempData[tableName] ={};
	}
	/**将弹出框子表数据填充至子表**/ 
	$scope.pushTempDataToForm=function(tableName){
		if($(".ng-invalid","#"+tableName+"_editDialog").length>0){
			Alert("提示信息","当前子表表单未校验通过！");
			return;
		} 
		var tempData =$.extend($scope.data.sub[tableName].rows[index],$scope.subTempData[tableName]);
		var index = tempData.$index;
		if(!index)index=0;
		$scope.data.sub[tableName].rows[index]=tempData;
		$('#'+tableName+'_editDialog').modal('close');
		//$rootScope.$digest();
	}
	
	/**
	 * 删除行
	 */
	$scope.removeRow=function(tableName,idx){
		flowService.removeRow($scope,tableName,idx);
	};
	
	/**
	 * 表单初始化。
	 */
	$scope.$on("formInited",function(event){
		var obj=$("#linkSubmit");
		$scope.$watch('myform.$invalid', function(newValue, oldValue){
			 if(newValue){
		        obj.attr("disabled","disabled")
		     }
			 else{
		        obj.removeAttr("disabled");
		     }
		});
    });
	
	/**
	 * 启动流程
	 */
	$scope.startFlow=function(){
		if(!flowService.checkForm($scope)) return;
		if($scope.flowSubmiting) return;
		
		
		$scope.flowSubmiting = true;
		var def=flowService.startFlow(flowKey,$scope.data,runId);
		def.then(function(data){
			if(data.result==1){
				Alert("提示信息","启动流程成功!",function(){
					location.href=pk?"myDraftList.html":"startFlowList.html";
				});
			}
			else{
				$scope.flowSubmiting = false;
				Alert("启动流程失败",data.message);
			}
		},function(status){
			$scope.flowSubmiting = false;
		})
	};
	
	/**
	 * 保存草稿
	 */
	$scope.saveDraft=function(){
		if($scope.myform.$invalid){
			Alert("提示信息","表单未校验通过！");
			return;
		}
		var def=flowService.saveDraft(actDefId,$scope.data,runId,pk);
		def.then(function(data){
			if(data.result==1){
				Alert("提示信息","保存草稿成功!",function(){
					location.href=pk?"myDraftList.html":"startFlowList.html";
				});
			}
			else{
				Alert("保存草稿失败",data.message);
			}
		},function(status){
			
		})
	};
	$scope.delDraft = function(){
		var def=flowService.delDraft(runId);
		def.then(function(data){
			if(data.result==1){
				Alert("提示信息","删除草稿成功!",function(){
					location.href="myDraftList.html";
				});
			}
			else{
				Alert("删除草稿失败",data.message);
			}
		},function(status){
			
		})
	}
	
	/**
	 * 表单加载。
	 */
	$scope.init=function($scope,defId){
		
		var json=HtUtil.getJSON("form_" +defId);
		var defer=null;
		if(json==null){
			defer=flowService.getStartForm(defId,null,null,pk);
		}
		else{
			defer=flowService.getStartForm(defId,json.formKey,json.version,pk);
		}
		defer.then(function(data){
			flowService.handForm($scope,data,defId);
		},function(status){
			console.info(status);
		});
	},
	//添加规则。
	flowService.addRule();
	//初始化表单。
	$scope.init($scope,defId);
	
}]);