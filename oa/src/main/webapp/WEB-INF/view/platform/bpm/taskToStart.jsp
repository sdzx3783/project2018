<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@include file="/commons/include/html_doctype.html"%>
<%@page import="com.hotent.core.api.util.PropertyUtil"%>
<html>
    <head>
         <meta name="renderer" content="ie-comp">
         <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>流程任务-[${task.name}]执行</title>
        <%@include file="/commons/include/customForm.jsp" %>
        <%@include file="/commons/include/ueditor.jsp" %>
        <link rel="stylesheet" type="text/css" href="${ctx}/styles/default/css/hotent/task.css"></link>
        <script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/TaskAddSignWindow.js"></script>
        <script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/TaskBackWindow.js"></script>
        <script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/TaskImageUserDialog.js"></script>
        <script type="text/javascript" src="${ctx}/js/hotent/platform/tabOperator.js"></script>
        <script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/BpmTaskExeAssignDialog.js"></script>
        <script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/BpmRetransmissionDialog.js"></script>
        <script type="text/javascript" src="${ctx}/js/hotent/appropinion.js"></script>
        <script type="text/javascript">
        function getAndValidateAnnualVacationDay(){
        	var formKey=$("input[name='formKey']").val();
        	//校验请假申请确认请假天数和确认时间
        	if(formKey!=null && formKey=='qxjlcbd' && $("input[name='m:vaction:actualstartTime']").length>0){
        		var needActual=$("input[name='m:vaction:needActual'][type='radio']:checked").val();
        		if('1'==needActual){
        			//需要销假 校验时间
        			var actualstartTime=$("input[name='m:vaction:actualstartTime']").val();
        			if(actualstartTime.length<=0){
        				$.ligerDialog.warn("请选择确认请假时间","提示信息");
        				return ;
        			}
        			var actualendTime=$("input[name='m:vaction:actualendTime']").val();
        			if(actualendTime.length<=0){
        				$.ligerDialog.warn("请选择确认请假时间","提示信息");
        				return ;
        			}
        			var actualleave_days=$("input[name='m:vaction:actualleave_days']").val();
        			if(actualendTime.length<=0 || actualleave_days=='0'){
        				$.ligerDialog.warn("确认请假天数不能为0","提示信息");
        				return ;
        			}
        		}
        		
        	}
        	
        	//校验请假流程年假天数-start
            
            var currentNode=$("input[name='currentNode']").val();
            if(formKey!=null && formKey=='qxjlcbd' && currentNode && currentNode=='UserTask1'){
                var _leave_type=$('select[name="m:vaction:leave_type"] option:selected').val();
                if(_leave_type && _leave_type=='年假'){
                  	var date=new Date();
                      var t=date.getTime();
                          $.ajax({
                              type:"get",
                              async: false,
                              url:"/makshi/worksheet/annualVacation/annual_vacation_days.ht?t="+t,
                              success:function(data){
                                  var dataObj=eval("("+data+")");
                                  var vacationDay=dataObj.message;
                                  $("#remain_annual_leave").val(vacationDay);
                                  $("#remain_annual_leave").attr("class","none-border-text");
                                  $("#remain_annual_leave").attr("readonly",true);
                              }
                          });
                          
                      var leave_days=Number($("input[name='m:vaction:leave_days']").val());
                      var remain_annual_leave=Number($("input[name='m:vaction:remain_annual_leave']").val());
                      if(leave_days<=0){
                      	$.ligerDialog.warn("请的年假天数为0！","提示信息")
                          return false;
                      }
                      if(leave_days>=0.0){
                      	if(remain_annual_leave<0 || (leave_days-remain_annual_leave)>0.0){
                      		$.ligerDialog.warn("请的年假天数不能超过剩余年假天数！","提示信息")
                      		return false;
                      	}
                      }else{
                      	$.ligerDialog.warn("表单数据有误！","提示信息")
                      	return false;
                      }
                  }
            }
            ////校验请假流程年假天数-end
            
        	return true;
        }
        function judeVacationExists(){
        	var currentNode=$("input[name='currentNode']").val();
        	if(currentNode!='UserTask1'){return true};
            var formKey=$("input[name='formKey']").val();
            var flag=true;
            var msg="校验不通过";
            //请假流程，查询年假
            if(formKey!=null && formKey=='qxjlcbd'){
                var leave_start=$("#leave_start").val();
                var leave_end=$("#leave_end").val();
                var levea_start_select=$("#levea_start_select").val();
                var levea_end_select=$("#levea_end_select").val();
                var levea_days=$("#leave_days").val();
                var levea_type=$("select[name='m:vaction:leave_type']").val();
                var date=new Date();
                var t=date.getTime();
                $.ajax({
                    type:"get",
                    data:{leave_start:leave_start,leave_end:leave_end,levea_start_select:levea_start_select,levea_end_select:levea_end_select,levea_days:levea_days,levea_type:levea_type},
                    url:"/makshi/worksheet/annualVacation/validLeave.ht?t="+t,
                    async:false,
                    success:function(responseText){
                    var obj=new com.hotent.form.ResultMessage(responseText);
                    if(obj.isSuccess()){
                        flag=true;  
                     }else{
                        flag=false;
                        msg=obj.getMessage();
                     }
                    }
                });
                if(!flag){
                	$.ligerDialog.warn(msg);
                }
            }
            return flag;
        }
        
        
        function contractIsExists(){
        	//校验合同盖章流程-start
        	var runid='${processRun.runId}';
            var formKey=$("input[name='formKey']").val();
            var currentNode=$("input[name='currentNode']").val();
            if(formKey!=null && formKey=='contract_seal_application' && currentNode && currentNode=='UserTask7'){
                var contract_num=$("input[name='m:contract_seal_application:contract_num']").val();
                var contracttype=$("select[name='m:contract_seal_application:contracttype']").val();
                if(contract_num && contracttype){
                  	var date=new Date();
                    var t=date.getTime();
                    var isExist=false;
                          $.ajax({
                              type:"get",
                              async: false,
                              url:"/makshi/contract/contractinfo/contractIsExist.ht?contractnum="+contract_num+"&contracttype="+contracttype+"&runid="+runid+"&t="+t,
                              success:function(data){
                                  var dataObj=eval("("+data+")");
                                  var result=dataObj.result;
                                  var status=dataObj.message;
                                  if(result==1){
                                	  if(status=='1'){
                                		  isExist=true;
                                	  }
                                  }else{
                                	  isExist=true;
                                	  $.ligerDialog.warn(status,"提示信息");
                                  }
                              }
                          });
                     if(isExist){
                    	 $.ligerDialog.warn("该合同编号已被使用！","提示信息");
                     	return false;
                     }
                  }else{
                	  $.ligerDialog.warn("请选择合同类型","提示信息")
                    	return false;
                  }
            }
            //校验合同盖章流程合同编号-end
        	return true;
        }
        function validateWorkHour(){
        	/* var hour_total=$("input[name='m:work_hour_application:work_hour_total']").val();
        	var d=$("input[name='m:work_hour_application:applicant_time']").val();
        	if(d && hour_total){
        		var date=new Date(Date.parse(d.replace(/-/g, '/')));
            	var day=date.getDay();
            	if(day>=1 &&day<=5){
            		if(hour_total>7){
            			alert("周一至周五正常工时不能大于7个小时!");
            			return false;
            		}
            	}
            	if(day==6){
            		if(hour_total>3){
            			alert("周六正常工时不能大于3个小时!");
            			return false;
            		}
            	}
        	}  */
        	return true;
        }
        
        
        var taskId="${task.id}";
        var runId="${processRun.runId}";
        var isExtForm=${isExtForm};
        var isEmptyForm=${isEmptyForm};
        var isSignTask=${isSignTask};
        var isHidePath=${isHidePath};
        var isManage=${isManage};
        var isNeedSubmitConfirm=${bpmDefinition.submitConfirm==1};
        var isHandChoolse=${isHandChoolse};
        var bpmGangedSets=[];
        
        var form;
        //操作类型
        //1.完成任务
        //2.保存数据
        var operatorType=1;
        
        //补签
        function showAddSignWindow(){
            TaskAddSignWindow({taskId:taskId,callback:function(sign){
                loadTaskSign();
            }});
        }
        
        //加载会签数据。
        function loadTaskSign(){
            $(".taskOpinion").load('${ctx}/platform/bpm/task/toSign.ht?taskId=${task.id}');
        }
        //显示流程图
        function showTaskUserDlg(){
            TaskImageUserDialog({actInstId:${processRun.actInstId}});
        }
        //显示沟通意见。
        function showTaskCommunication(conf){
            var obj = {data:CustomForm.getData()};
            isTaskEnd(function(){
                if(!conf) conf={};
                //输入子页面
                var url=__ctx + "/platform/bpm/task/toStartCommunicate.ht?taskId=${task.id}";
                
                /*KILLDIALOG*/
                DialogUtil.open({
                    height:350,
                    width: 500,
                    title : '沟通意见',
                    url: url, 
                    isResize: true,
                    //自定义参数
                    obj: obj
                });
            })
        }
        //显示流转意见
        function showTaskTransTo(conf) {
            var obj = {data:CustomForm.getData()};
            isTaskEnd(function(){
                if(!conf) conf={};
                //输入子页面
                var url=__ctx + "/platform/bpm/task/toTransTo.ht?taskId=${task.id}&curUserId=${curUserId}";
                
                url=url.getNewUrl();
                
                
                DialogUtil.open({
                    height:500,
                    width: 550,
                    title : '显示流转意见',
                    url: url, 
                    isResize: true,
                    //自定义参数
                    obj: obj,
                    sucCall:function(rtn){
                        if(rtn=="ok"){
                            handJumpOrClose();
                        }
                    }
                });
            })
        }
        
        function isTaskEnd(callBack){
            var url="${ctx}/platform/bpm/task/isTaskExsit.ht";
            var params={taskId:"${task.id}"};
            
            $.post(url,params,function(responseText){
                var obj=new com.hotent.form.ResultMessage(responseText);            
                if(obj.isSuccess()){
                    callBack.apply(this);
                }
                else{
                    $.ligerDialog.warn("这个任务已经完成或被终止了!",'提示');
                }
            });
        }
        
        //判断手机是否已被使用
        function phoneSet(){
            var defId=$("input[name='defId']").val();
            var currentNode=$("input[name='currentNode']").val();
            //是入职流程并且是领取电话餐卡工作证环节，获取可用电话号码
             var phone_id=$("#phone_id").val();
            var flag=true;
            if(defId!=null && defId=='10000000050327' && currentNode!=null && currentNode=="UserTask4" && phone_id!=null && phone_id!=""){
                var url="${ctx}/makshi/telList/phoneList/rzlc_phone_save.ht";
                var userAccount=$("#tdAccount").html();
                var params={id:phone_id,account:userAccount};
                $.ajax({
                    type:"get",
                    data:params,
                    async:false,
                    url:url,
                    success:function(responseText){
                         var obj=new com.hotent.form.ResultMessage(responseText);            
                         if(obj.isSuccess()){
                             flag= true;
                         }else{
                             $.ligerDialog.warn("该手机号码已被领取过，请刷新页面重新领取新手机!",'提示');
                             flag= false;
                        }
                    }
                });
            }
            return flag;
        }
        
        function handFormJson(json){}
        
        function submitForm(action,button){
        
            var ignoreRequired=false;
            if(button=="#btnSave" || button=="#btnReject" || button=="#btnRejectToStart"){
                ignoreRequired=true;
            }
            
            if($(button).hasClass("disabled"))return;
            if(isEmptyForm){
                $.ligerDialog.error("还没有设置表单!",'提示信息');
                return;
            }
            
            //手机号码是否已被用
            if(!phoneSet()){
                return;
            }
            
            var frmWorkFlow=$('#frmWorkFlow');
            frmWorkFlow.attr("action",action);
            if(isExtForm){
                var rtn = true;
                if(button!="a.save"){
                    rtn=form.valid()
                }
                if(!rtn) return;
                
                $(button).addClass("disabled");
                frmWorkFlow.ajaxForm({success:showResponse }); //terry add
                if(frmWorkFlow.handleFieldName){//url表单清除命名
                    frmWorkFlow.handleFieldName();
                }
                if(frmWorkFlow.setData){
                    frmWorkFlow.setData();
                }
                frmWorkFlow.submit();
                
            }else{
            	var rtn=CustomForm.validate({ignoreRequired:ignoreRequired,returnErrorMsg:true});
                if(!rtn.success){
                    $.ligerDialog.warn("表单验证不成功,请检查表单是否正确填写!"+rtn.errorMsg,"提示信息");
                    return;
                }
        
                if(button!="#btnSave" || button=="#btnReject" || button=="#btnRejectToStart" ){
                    //子表必填检查(兼容新旧版本)                    
                    rtn = SubtablePermission.subRequired();
                    if(!rtn){   
                        $.ligerDialog.warn("请填写子表表单数据！","提示信息");
                        return;
                    }
                }
                
                var data=CustomForm.getData();
                //WebSign控件提交。 有控件时才提交   xcx
                if(WebSignPlugin.hasWebSignField){
                    WebSignPlugin.submit();
                }               
                
                $(button).addClass("disabled");
                
                var uaName=navigator.userAgent.toLowerCase();               
                if(uaName.indexOf("firefox")>=0||uaName.indexOf("chrome")>=0){  //异步处理
                    //Office控件提交。 有可以提交的文档
                    if(OfficePlugin.submitNum>0){
                        OfficePlugin.submit();             //火狐和谷歌 的文档提交包括了  业务提交代码部分（完成  OfficePlugin.submit()后面的回调 函数 有 业务提交代码），所以 后面就不用加上业务提交代码
                    }else{   //没有可提交的文档时 直接做 业务提交代码
                        data=CustomForm.getData();
                        //设置表单数据
                        $("#formData").val(data);
                        FormSubmitUtil.submitFormAjax(frmWorkFlow,showResponse,handFormJson);
                    }                   
                }else{   //同步处理
                    //Office控件提交。 有可以提交的文档
                    if(OfficePlugin.submitNum>0){
                        OfficePlugin.submit();
                        //当提交问题 等于 提交数量的变量 时 表示所有文档 都提交了  然后做 业务相关的提交
                        if(OfficePlugin.submitNum == OfficePlugin.submitNewNum){    
                            //获取自定义表单的数据
                            data=CustomForm.getData();
                            //设置表单数据
                            $("#formData").val(data);
                            
                            FormSubmitUtil.submitFormAjax(frmWorkFlow,showResponse,handFormJson);
                            OfficePlugin.submitNewNum = 0; //重置  提交数量的变量
                        }else{
                            $.ligerDialog.warn("提交失败,OFFICE控件没能正常使用，请重新安装 ！！！","提示");
                        }
                    }else{
                        //获取自定义表单的数据
                        data=CustomForm.getData();
                        //设置表单数据
                        $("#formData").val(data);
                        
                        FormSubmitUtil.submitFormAjax(frmWorkFlow,showResponse,handFormJson);
                    }       
                }
                
            }
        }
        
        function saveData(){
            var operatorObj=getByOperatorType();
            var button="#" +operatorObj.btnId;
            
            var rtn=beforeClick(operatorType);
            
            if( rtn==false){
                return;
            }
            if(isExtForm){//提交第三方表单时检查该表单的参数
                var frm=$('#frmWorkFlow');
                if(!frm.valid()) return ;
                if(frm.setData)frm.setData();
            }
            
            var action="${ctx}/platform/bpm/task/saveData.ht";
            submitForm(action,button);
        }
        
        // 在完成任务之前，加上判断是否需要弹出意见对话框
        function completeTaskBefore(){
            // 加上判断是否需要弹出意见对话框
            var isPopup = '${bpmNodeSet.isPopup}';
            var isRequired = '${bpmNodeSet.isRequired}';
            if(isPopup == 1){
                
                //var rtn=form.valid();
                //if(!rtn) return;
                
                opintionDialog();
            } else {
                completeTask();
            }
        }
        

        //完成当前任务。
        function completeTask(){
            
            if($("input[name='nextPathId']").length>0){
                var b=false;//是否需要判断同步条件
                var destTask=$("input[name='destTask']:checked");
                if(destTask.length==0){
                    b=true;
                }else{
                    var nextPathId=destTask.parents("tr").find("[name=nextPathId]");
                    if(nextPathId.length>0){
                        b=true;
                    }
                }
                if(b){
                    var v=$("input[name='nextPathId']:checked").val();
                    if(!v){
                        $.ligerDialog.error("在同步条件后的执行路径中，您至少需要选择一条路径!",'提示信息');
                        return; 
                    }
                    
                }
            }
            //判断每条执行路径是否已选择用户
            var flag = false;
            var spanSelcUser = $("span[name='spanSelectUser'],#jumpUserDiv");
            var spanSelcUserLen = spanSelcUser.length;
            var tag = 0;
            spanSelcUser.each(function(index){
                var lenChil = $(this).children("input[name$='_userId']:checked").length;
                if(lenChil>0){
                    tag++;
                }
                if(spanSelcUserLen==tag){
                    flag = true;
                }
            });
            var lastDestTaskId = $("input[name='lastDestTaskId']").val();
            if(lastDestTaskId){
                if(lastDestTaskId.indexOf("EndEvent")==-1 && !flag){
                    $.ligerDialog.warn("每条执行路径至少要选择一个用户!",'提示信息');
                    return;
                }
            }
                    
            var operatorObj=getByOperatorType();
            var button="#" +operatorObj.btnId;
            var action="${ctx}/platform/bpm/task/complete.ht";
            //执行前置脚本
            var rtn=beforeClick(operatorType);
            if( rtn==false){
                return;
            }
            //确认执行任务。
            if(isNeedSubmitConfirm){
                $.ligerDialog.confirm("您确定执行此操作吗？","提示",function(rtn){
                    if(rtn){
                        submitForm(action,button);
                    }
                });
            }
            else{
                submitForm(action,button);  
            }
        }
        
        
        function getByOperatorType(){
            var obj={};
            switch(operatorType){
                //同意
                case 1:
                    obj.btnId="btnAgree";
                    obj.msg="执行任务成功!";
                    break;
                //反对
                case 2:
                    obj.btnId="btnNotAgree";
                    obj.msg="执行任务成功!";
                    break;
                //弃权
                case 3:
                    obj.btnId="btnAbandon";
                    obj.msg="执行任务成功!";
                    break;
                    //驳回
                case 4:
                    obj.btnId="btnReject";
                    obj.msg="驳回任务成功!";
                    break;
                //驳回到发起人
                case 5:
                    obj.btnId="btnRejectToStart";
                    obj.msg="驳回到发起人成功!";
                    break;
                //保存表单
                case 8:
                    obj.btnId="btnSave";
                    obj.msg="保存表单数据成功!";
                    break;
                
            }
            return obj;
        }
        
        function getErrorByOperatorType(){
            var rtn="";
            switch(operatorType){
                //同意
                case 1:
                //反对
                case 2:
                //弃权
                case 3:
                    rtn="执行任务失败!";
                    break;
                //驳回
                    //驳回
                case 4:
                    rtn="驳回任务失败!";
                    break;
                //驳回到发起人
                case 5:
                    rtn="驳回到发起人失败!";
                    break;
                //保存表单
                case 8:
                    rtn="保存表单数据失败!";
                    break;
            }
            return rtn;
        }
        
        function showResponse(responseText){
            var operatorObj=getByOperatorType();
            $("#" +operatorObj.btnId).removeClass("disabled");
            var obj=new com.hotent.form.ResultMessage(responseText);
            if(obj.isSuccess()){
                $.ligerDialog.success(operatorObj.msg,'提示信息',function(){
                    var rtn=afterClick(operatorType);
                    location.reload(); 
                    if( rtn==false){
                        return;
                    }
                    handJumpOrClose();
                });
            }else{
                var title=getErrorByOperatorType();
                $.ligerDialog.err('出错信息',title,obj.getMessage());
            }
        }
        
        //弹出回退窗口 TODO 去除
        function showBackWindow(){
            new TaskBackWindow({taskId:taskId}).show();
        }
        
        $(function(){
            initForm();
            //显示路径
            if(isHandChoolse){
                chooseJumpType(2);
            }else{
                chooseJumpType(1);
            }
            resizeIframe();
            //初始化联动设置
            <c:if test="${!empty bpmGangedSets}">
                bpmGangedSets = ${bpmGangedSets};
                FormUtil.InitGangedSet(bpmGangedSets);
            </c:if>         
        }); 
        
        //获取是否允许直接结束。
        function getIsDirectComplete(){
            var isDirectComlete = false;
            if($("#chkDirectComplete").length>0){
                if($("#chkDirectComplete").attr("checked")=="checked"){
                    isDirectComlete = true;
                }
            }
            return isDirectComlete;
        }
        
        //提交第三方表单时检查该表单的参数
        function setExtFormData(){
            if(isExtForm){
                var frm=$('#frmWorkFlow');
                if(!frm.valid()) return ;
                if(frm.setData)frm.setData();
            }
        }
        
        function initBtnEvent(){
            //0，弃权，1，同意，2反对。
            var objVoteAgree=$("#voteAgree");
            var objBack=$("#back");
            
            //同意
            if($("#btnAgree").length>0){
                $("#btnAgree").click(function(){
                     //请假流程，查询年假
                	/*  var formKey=$("input[name='formKey']").val();
                     var currentNode=$("input[name='currentNode']").val(); */
                    if(!getAndValidateAnnualVacationDay()){
                    	return ;
                    }
                    if(!judeVacationExists()){
                    	return ;
                    }
                   	if(!contractIsExists()){
                   		return ;
                   	}
                   	if(!validateWorkHour()){
                   		return ;
                   	}
                    setDestTaskInclude(true);
                    var voteContent = $('#voteContent'),
                    content = voteContent.val();
                    
                    setExtFormData();
                    
                    var isDirectComlete = getIsDirectComplete();
                    
                    operatorType=1;
                    //同意:5，一票通过。
                    var tmp =isDirectComlete?"5":"1";
                    
                    objVoteAgree.val(tmp);
                    objBack.val("0");
                    
                    // 提前校验表单 
                    var beforeScript=beforeClick(getByOperatorType());
                    if(beforeScript==false) return; 
                    var rtn=CustomForm.validate({ignoreRequired:false,returnErrorMsg:true});
                    if(!rtn.success){
                        $.ligerDialog.warn("表单验证不成功,请检查表单是否正确填写!"+rtn.errorMsg,"提示信息");
                        return;
                    }

                    //如果是dirSubmitProcs配置的流程，则可以直接提交。不弹意见框。
                    if(apprOp.ifFormKeyInTheMap($("input[name='formKey']").val())) {
                        console.log("YEP direct submit");

                        //将textarea中的内容赋值给原【意见】
                        var crnode = $('#currentNode').val();
                        if($('#opn_' + crnode).length > 0) {
                            $("#voteContent").val($.trim($('#opn_' + crnode).val()));
                        } else {
                            $.ligerDialog.warn("表单验证不成功,请检查表单是否存在需填写的意见信息!","提示信息");
                            return;
                        }
                        // 完成当前任务
                        completeTask();
                        return;
                    }

                    completeTaskBefore();
                });
            }
            //反对
            if($("#btnNotAgree").length>0){
                $("#btnNotAgree").click(function(){
                    setExtFormData();
                    setDestTaskInclude(true);
                    var isDirectComlete = getIsDirectComplete();
                    operatorType=2;
                    ////直接一票通过
                    var tmp =isDirectComlete?'6':'2';
                    objVoteAgree.val(tmp);
                    objBack.val("0");
                    completeTaskBefore();
                });
            }
            //弃权
            if($("#btnAbandon").length>0){
                $("#btnAbandon").click(function(){
                    setExtFormData();
                    setDestTaskInclude(true);
                    operatorType=3;
                    objVoteAgree.val(0);
                    objBack.val("");
                    completeTaskBefore();
                })
            }
            //驳回
            if($("#btnReject").length>0){
                $("#btnReject").click(function(){
                        setExtFormData();
                        
                        setDestTaskInclude(false);
                        operatorType=4;
                        objVoteAgree.val(3);
                        objBack.val(1);

                    //如果是dirSubmitProcs配置的流程，则可以直接提交。不弹意见框。
                    if(apprOp.ifFormKeyInTheMap($("input[name='formKey']").val())) {
                        console.log("YEP direct submit");

                        //将textarea中的内容赋值给原【意见】
                        var crnode = $('#currentNode').val();
                        if($('#opn_' + crnode).length > 0 && $.trim($('#opn_' + crnode).val()) != '') {
                            $("#voteContent").val($.trim($('#opn_' + crnode).val()));
                        } else {
                            $.ligerDialog.warn("表单验证不成功,请检查表单是否存在需填写的意见信息!","提示信息");
                            return;
                        }
                        // 完成当前任务
                        completeTask();
                        return;
                    }

                        completeTaskBefore(); 
                })
            }
            //驳回到发起人
            if($("#btnRejectToStart").length>0){
                $("#btnRejectToStart").click(function(){
                    var voteContent = $('#voteContent'),
                    content = voteContent.val();
                        setExtFormData();
                        setDestTaskInclude(false);
                        
                        operatorType=5;
                        //驳回到发起人
                        objVoteAgree.val(3);
                        objBack.val(2);

                    //如果是dirSubmitProcs配置的流程，则可以直接提交。不弹意见框。
                    if(apprOp.ifFormKeyInTheMap($("input[name='formKey']").val())) {
                        console.log("YEP direct submit");

                        //将textarea中的内容赋值给原【意见】
                        var crnode = $('#currentNode').val();
                        if($('#opn_' + crnode).length > 0 && $.trim($('#opn_' + crnode).val()) != '') {
                            $("#voteContent").val($.trim($('#opn_' + crnode).val()));
                        } else {
                            $.ligerDialog.warn("表单验证不成功,请检查表单是否存在需填写的意见信息!","提示信息");
                            return;
                        }
                        // 完成当前任务
                        completeTask();
                        return;
                    }

                        completeTaskBefore();
                })
            }
            //保存表单
            if($("#btnSave").length>0){
                $("#btnSave").click(function(){
                    setExtFormData();
                    operatorType=8;
                    saveData();
                })
            }
            
            //终止流程
            $("#btnEnd").click(function(){
                isTaskEnd(endTask);
            });
        }
        
        
        //驳回时设置是否提交目标路径。
        function setDestTaskInclude(isNeed){
            $("[name='destTask']").each(function(){
                if(isNeed){
                    $(this).attr("include","1");
                }
                else{
                    $(this).removeAttr("include");
                }
            })
        }
        
        // 弹出意见对话框
        function opintionDialog(){
            var isRequired = '${bpmNodeSet.isRequired}';
            var actDefId = $("#actDefId").val();
            var url= __ctx + '/platform/bpm/task/opDialog.ht?isRequired=' +isRequired+'&actDefId='+actDefId+'&operatorType='+operatorType;
            var voteContent = $("#voteContent").val();
            DialogUtil.open({
                height:350,
                width: 500,
                title : '填写意见',
                url: url, 
                isResize: true,
                //自定义参数
                voteContent:voteContent,
                sucCall:function(rtn){
                    // 填写到系统的意见
                    $("#voteContent").val(rtn);
                    if(!getAndValidateAnnualVacationDay()){
                    	return ;
                    }
                    if(!judeVacationExists()){
                    	return ;
                    }
                    if(!contractIsExists()){
                   		return ;
                   	}
                 	// 完成当前任务
                    completeTask();
                }
            });
        }
        
        
        //终止流程。
        function endTask(){
            var url=__ctx + "/platform/bpm/task/toStartEnd.ht";
            url=url.getNewUrl();
            
            DialogUtil.open({
                height:250,
                width: 400,
                title : '终止流程',
                url: url, 
                isResize: true,
                //自定义参数
                sucCall:function(rtn){
                    var url="${ctx}/platform/bpm/task/endProcess.ht?taskId=${task.id}";
                    var params={taskId:taskId,memo:rtn};
                    $.post(url,params,function(responseText){
                        var obj=new com.hotent.form.ResultMessage(responseText);
                        if(!obj.isSuccess()){
                            $.ligerDialog.err("提示信息","终止任务失败!",obj.getMessage());
                            return;
                        }
                        $.ligerDialog.success(obj.getMessage(),"提示信息",function(){
                            handJumpOrClose();
                        });
                    });
                }
            });
        }
        
        function handJumpOrClose(){
            //如果按钮类型为保存则不关闭窗口。
            if(operatorType==8) return;
            if(window.opener){
                try{
                	var url="";
                	if(window.opener.location.href.getNewUrl().indexOf("query")<0){
                		var tem=window.opener.location.href.getNewUrl();
                		tem=tem.substring(0,tem.indexOf(".ht"))+".ht";
                		url=tem+"&"+decodeURIComponent("${param['query']}");
                		
                	}else{
                		url=window.opener.location.href.getNewUrl();
                	}
                	if(url.indexOf(".ht&")>0){
                		//需要替换掉第一个&，改成？
                		url=url.replace("&","?");
                		
                	}
                	
                	window.opener.location.href=url;
                	window.opener.window.top.loadMenu(10000005010038);
                	//window.opener.window.top.addToTab(url,"我的待办","10000002740909","/images/transparent.png");
                }
                catch(e){}
            }
            var defId=$("input[name='defId']").val();
            var arrDefid=['10000000050327','10000000280178','10000009830004','10000003400050','10000009490429','10000011431989','10000002200654','10000000480116'
                          ,'10000002180203','10000002180253','10000002180270','10000003950821','10000004630004','10000003950846','10000001130191','10000001130259'
                          ];
            var needClose=true;
            for(var item in arrDefid) {
            		if(arrDefid[item]==defId){
            			needClose=false;
            		}
            }
            if(needClose){
            	window.close();
            }
        }
        
        function initForm(){
            //初始化按钮事件。
            initBtnEvent();
            
            if(isEmptyForm) return;
        
            if(isExtForm){      
                form=$('#frmWorkFlow').form({excludes:"[type=append]"});
                var formUrl=$('#divExternalForm').attr("formUrl");
                $('#divExternalForm').load(formUrl, function() {
                    hasLoadComplete=true;
                    //动态执行第三方表单指定执行的js
                    try{
                        afterOnload();
                    }catch(e){}
                    initSubForm();
                    OfficePlugin.init();
                });
            }else{
                $(".taskopinion").each(function(){
                    $(this).removeClass("taskopinion");
                    var actInstId=$(this).attr("instanceId");
                    $(this).load("${ctx}/platform/bpm/taskOpinion/listform.ht?actInstId="+actInstId);
                });
                
            }
        }
        
        function initSubForm(){
            $('#frmWorkFlow').ajaxForm({success:showResponse }); 
        }
        
        function showRoleDlg(){
            RoleDialog({callback:function(roleId,roleName){$('#forkUserUids').val(roleId);}}); 
        }
        
        function chooseJumpType(val){
            //如果是沟通不需要显示跳转信息，而且它没有executionId 会报错
            if(${task.description==15}) return;
            if(isHidePath&&isManage==0) return;
            var obj=$('#jumpDiv');
            var url="";
            if(val==1){
                url="${ctx}/platform/bpm/task/tranTaskUserMap.ht?taskId=${task.id}&selectPath=0";
            }
            //选择路径跳转
            else if(val==2){
                url="${ctx}/platform/bpm/task/tranTaskUserMap.ht?taskId=${task.id}";
            }
            //自由跳转
            else if(val==3){
                url="${ctx}/platform/bpm/task/freeJump.ht?taskId=${task.id}";
            }
        
            url=url.getNewUrl();
            
            if(val==3){  //自由跳转
                $.ajaxSetup({async:false});  //同步 获得结果后  再去查询相关的人员
                obj.html(obj.attr("tipInfo")).show().load(url);
                $.ajaxSetup({async:true}); //异步
                //自由跳转 时 从下拉节点的默认的值 中查出相关的人员 
                var destTask=$('#destTask');   //默认的选中的对象
                changeDestTask(destTask);    //查出相关的人员 并改显示出来
            }else{
                obj.html(obj.attr("tipInfo")).show().load(url);
            }
            
        };
        
        //为目标节点选择执行的人员列表        
        function selExeUsers(obj,nodeId,scope){
            var span=$(obj).siblings("span");
            
            var aryChk=$(":checkbox",span);
            var selectExecutors =[];  
            aryChk.each(function(){   
                var val=$(this).val();
                var k=val.split("^");
                var userObj={};
                userObj.type=k[0];
                userObj.id=k[1];
                userObj.name=k[2];
                selectExecutors.push(userObj);    
            });
            
            if(!scope){
                scope={};
                scope.type='system',
                scope.value='all';
                scope=JSON2.stringify(scope);
            }else{
                scope=scope.replaceAll("#@","\"");
            }

        
            FlowUserDialog({selectUsers:selectExecutors,scope:scope,callback:function(types,objIds,objNames){
                if(objIds==null) return;
                var aryTmp=[];
                for(var i=0;i<objIds.length;i++){
                    var check="<input type='checkbox' include='1' name='" + nodeId + "_userId' checked='checked' value='"+types[i] +"^"+  objIds[i]+"^"+objNames[i] +"'/>&nbsp;"+objNames[i];
                    aryTmp.push(check);
                }
                span.html(aryTmp.join(''));
            }});
        }
        
        function selectExeUsers(obj,scope){
            var destTask=$("#destTask");
            if(destTask){
                $("#lastDestTaskId").val(destTask.val());
            }
            selExeUsers(obj,destTask.val(),scope);
        }
        //显示审批历史
        function showTaskOpinions(){
            var url="${ctx}/platform/bpm/taskOpinion/list.ht?runId=${processRun.runId}&isOpenDialog=1";
            url=url.getNewUrl();
            DialogUtil.open({
                url:url,
                title:'审批历史',
                height: 600,
                width: 800,
        		showMax: true
            });
        }
        //更改
        function changeDestTask(sel){
            var nodeId=sel.value;
            if(typeof nodeId == 'undefined'){    //对象不是用原始JS的，而是通过Jquery获取的对象
                nodeId = sel.val();
            }
            if(typeof nodeId == 'undefined' || nodeId==null || nodeId==""){
                $('#jumpUserDiv').html("");
                $('#lastDestTaskId').val("");
                return;
            }
            $('#lastDestTaskId').val(nodeId);
            var url="${ctx}/platform/bpm/task/getTaskUsers.ht?taskId=${task.id}&nodeId="+nodeId;
            $.getJSON(url, function(dataJson){
                var data=eval(dataJson);
                var aryHtml=[];
                for(var i=0;i<data.length;i++){
                  var span="<input type='checkbox' include='1' name='" + nodeId + "_userId' checked='checked' value='"+data[i].type+"^"+data[i].executeId+"^"+data[i].executor+"'/>&nbsp;"+data[i].executor;
                  aryHtml.push(span);
                }
                $('#jumpUserDiv').html(aryHtml.join(''));
            });
            
        }
        
        function isTaskEnd(callBack){
            var url="${ctx}/platform/bpm/task/isTaskExsit.ht";
            var params={taskId:"${task.id}"};
            
            $.post(url,params,function(responseText){
                var obj=new com.hotent.form.ResultMessage(responseText);            
                if(obj.isSuccess()){
                    callBack.apply(this);
                }
                else{
                    $.ligerDialog.warn("当前任务已经完成或被终止","提示信息");
                }
            });
        }
        
        //转交待办
        function changeAssignee(){
            if(${isCanAssignee}){
                isTaskEnd(function(){
                     BpmTaskExeAssignDialog({taskId:taskId,callback:function(rtn){
                        if(rtn){
                            handJumpOrClose();
                        }
                    }
                    });
                });
            }
            else{
                $.ligerDialog.warn("当前任务不能转办!","提示信息");
            }
        };
        
        //转发
        function retransmission(){
            isTaskEnd(function(){
                BpmRetransmissionDialog({runId:runId,callback:function(rtn){
                    if(rtn){
                        handJumpOrClose();
                    }
                }
                });
            });
        }
        
        
        function resizeIframe(){
            if($("#frameDetail").length==0) return;
            $("#frameDetail").load(function() { 
                $(this).height($(this).contents().height()+20); 
            }) ;
        }
        
        
        // 选择常用语
        function addComment(){
            var objContent=document.getElementById("voteContent");
            var selItem = $('#selTaskAppItem').val();
            jQuery.insertText(objContent,selItem);
        }
        
        function openForm(formUrl){
            var winArgs="dialogWidth=500px;dialogHeight=400px;help=0;status=0;scroll=0;center=1";
            var url=formUrl.getNewUrl();
            window.open(url,"",winArgs);
        }
        
        function reference(){
            var defId=${bpmDefinition.defId};
            
            var url="${ctx}/platform/bpm/processRun/getRefList.ht?defId=" +defId;
            
            var params="height=400,width=700,status=no,toolbar=no,menubar=no,location=no,resizable=1,scrollbars=1";
            window.open(url);
        }
        
        function openHelpDoc(fileId){
            var h=screen.availHeight-35;
            var w=screen.availWidth-5;
            var vars="top=0,left=0,height="+h+",width="+w+",status=no,toolbar=no,menubar=no,location=no,resizable=1,scrollbars=1";
            var showUrl = __ctx+"/platform/form/office/get.ht?fileId=" + fileId;
            window.open(showUrl,"myWindow",vars);   
        }
        
        //增加Web签章
        function addWebSigns(){
            AddSecSignFromServiceX();          //WebSignPlugin JS类     
        }
        
        //增加手写签章
        function addHangSigns(){
            AddSecHandSignNoPromptX();          //WebSignPlugin JS类     
        }


        
        // 自定义打印
        function customPrint(printAlias){
            var url="${ctx}/platform/bpm/processRun/printForm.ht?runId=${processRun.runId}&printAlias="+printAlias;
            jQuery.openFullWindow(url);
        }
       
        //获取年龄
        function getAge(brith){
            var aDate=new Date();   
            var thisYear=aDate.getFullYear();
            brith=brith.substr(0,4);
            age=(thisYear-brith);
           // setAutoPropeties("age",age);
            $(".contractWordCardAge").html(age);
        } ;
        //获取性别  
        function setSex(obj){
            if(obj==0){
                setAutoPropeties("sex","女");
                $(".contractWordSex").html("女");
            }
            if(obj==1){
                setAutoPropeties("sex","男");
                $(".contractWordSex").html("男");
            }
        }
        function setAutoPropeties(id,val){
            if($("#"+id).length>0){
                if(val!=null &&  val!=""){
                        $("#"+id).val(val);
                }
            }
            if($("."+id).length>0){
                if(val!=null &&  val!=""){
                        $("."+id).val(val);
                }
            }
        }

        function getProcessInfo(src, callback){
            var $iframe = $('<iframe>');
            $iframe.attr('src', src);
            $iframe.on('load', function(e){
                var iframeDocument = $(e.target.contentDocument).find('table');
                var $wrapper = $('<div class="process-info-wrapper"><h3>审批历史</h3></div>').append(iframeDocument);
                callback && callback($wrapper);
            });

            $('body').append($iframe.hide());
        }
        
        //打印
        function justPrint(){  
            var oldstr = document.body.innerHTML;  

            var src ="${ctx}/platform/bpm/taskOpinion/list.ht?runId=${processRun.runId}&isOpenDialog=1";
            var processInfo = getProcessInfo(src, function($table){
                $('body').find('table:last').after($table);

                var headstr = "<html><head><title></title></head><body>";  
                var footstr = "</body>";  
                var printData = document.getElementById("custformDiv").innerHTML;
                document.body.innerHTML = headstr+printData+footstr;  
                window.print();  
                document.body.innerHTML = oldstr;  
                return false;
            });
        }
        
      //直接打印,无审批记录
        function justPrintNoProcess(){  
            var oldstr = document.body.innerHTML;  
            //var src = $("#JS_process_info").attr("action");
          //  var processInfo = getProcessInfo(src, function($table){
                //$('body').find('.subtable:last, table:last').after($table);

                var headstr = "<html><head><title></title></head><body>";  
                var footstr = "</body>";  
                var printData = document.getElementById("custformDiv").innerHTML;
                document.body.innerHTML = headstr+printData+footstr;  
                window.print();  
                document.body.innerHTML = oldstr;  
               // return false;
           // });
        }
        
      $(function(){

          //如果是那种新的表单样式 ，则不显示下面的【审批历史】
          if(apprOp.ifFormKeyInTheMap($("input[name='formKey']").val()))
              return;

    	  var oldstr = document.body.innerHTML;  

          var src ="${ctx}/platform/bpm/taskOpinion/list.ht?runId=${processRun.runId}&isOpenDialog=1";
          var processInfo = getProcessInfo(src, function($table){
              $('body').find('table:last').after($table);
              $('#approvalHistory').html($table);
          });

      });
      
    </script>   

<style>
div.attachement{
    padding: 0 0 0 5px;
}
div.attachement li{
    margin-top: 0 !important;
    margin-bottom: 5px !important;
}
span.attachement-span{
    float: none;
}
    #topNavWrapper #topNav{
        background: #fff;
    }
    input[type="checkbox"]{
        vertical-align: middle;
        min-width: auto;
    }
    .subtable{
        width: auto;
    }
     .link.reset{
        display: none;
    }
    #addList{
          text-align: left;
        border: 0;
        color: #fff;
        padding: 3px 15px 3px 15px;
        line-height: 20px;
        background: #478de4 !important;
        -moz-box-shadow: 0 1px 1px rgba(0,0,0,0.15);
        -webkit-box-shadow: 0 1px 1px rgba(0,0,0,0.15);
        box-shadow: 0 1px 1px rgba(0,0,0,0.15);
        border-radius: 3px;
    }
    .link.add{
        text-align: left;
        border: 0;
        color: #fff;
        padding: 3px 15px 3px 15px;
        line-height: 20px;
        background: #478de4 !important;
        -moz-box-shadow: 0 1px 1px rgba(0,0,0,0.15);
        -webkit-box-shadow: 0 1px 1px rgba(0,0,0,0.15);
        box-shadow: 0 1px 1px rgba(0,0,0,0.15);
        border-radius: 3px;
    }

    .browser-info{
        padding: 20px;
        font-size: 18px;
        color: #ff9000;
    }
    .browser-info div{
        line-height: 1.7;
    }
    .browser-img{
        float: left;
        margin-top: 30px;
    }
    .browser-text{
        margin-left: 100px;
        border-left: 1px solid #dadfed;
        padding-left: 20px;
    }
    .process-info-wrapper {
    	padding: 20px 0;
    }
    .process-info-wrapper h3 {
        font-size: 16px;
        text-align: center;
    }
    .process-info-wrapper .table-grid {
         border: 1px solid #dadfed;
     }
     .process-info-wrapper .table-grid td {
         border: 1px solid #dadfed;
     }
     .process-info-wrapper .table-grid th {
         border: 1px solid #dadfed;
     }
     table.ProjectNoticebook{width: 100%;}
   	 table.ProjectNoticebook td{
   		padding: 10px 20px;
   		border: 1px solid #121213;
  		height:20px !important;
  	}
</style>
</head>
<body class="oa-mw-page">
    <input type="hidden" id="curCtx" value="${ctx}" />
    <form id="frmWorkFlow"  method="post" >
         <div class="panel">

                <div class="panel-top"> 
                    <!-- 悬浮工具栏实现的对象topNavWrapper 和 topNav 名称ID可以更改,但要和css的对象一致-->
                    <div id="topNavWrapper">
                        <ul id="topNav">
                          <iframe  style="position:absolute; visibility:inherit; top:0px; left:0px; width:100%; height:70px; z-index:-1; border-width:0px;"></iframe>                                   
                          <div class="l-layout-header noprint" >
                            任务审批处理--<b>${task.name}</b>--<i>[${bpmDefinition.subject}-V${bpmDefinition.versionNo}]</i>
                          </div>
                          <c:choose>
                                <c:when test="${(empty task.executionId) && task.description=='15' }">
                                    <jsp:include page="incTaskNotify.jsp"></jsp:include>
                                </c:when>
                                <c:when test="${(empty task.executionId) && (task.description=='38' || task.description=='39') }">
                                    <jsp:include page="incTaskTransTo.jsp"></jsp:include>
                                </c:when>
                                <c:otherwise>
                                    <jsp:include page="incToolBarNode.jsp"></jsp:include>
                                </c:otherwise>
                          </c:choose>
                        </ul>                      
                    </div>
                 </div>
                

                <div class="panel-body">
                    <c:choose>
                        <c:when test="${bpmNodeSet.isHidePath==0||isManage==1}">
                        <div id="jumpDiv" class="noprint" style="display:none;" tipInfo="正在加载表单请稍候...">
                        </div>
                        </c:when>
                    </c:choose>
                    <!--审批意见-->
                    <c:choose>
                        <c:when test="${bpmNodeSet.isHideOption ==0 && bpmNodeSet.isPopup==0  && task.description!='15' &&  task.description!='38' && task.description!='39'}">
                            <div class="noprint">
                                <jsp:include page="incTaskOpinion.jsp"></jsp:include>
                            </div>
                        </c:when>
                        <c:when test="${  bpmNodeSet.isPopup == 1 && task.description!='15' &&  task.description!='38' && task.description!='39'}">
                            <div class="hidden"><textarea class="hidden" include="1" id="voteContent" name="voteContent" >${taskOpinion.opinion}</textarea></div>
                        </c:when>
                    </c:choose>
                    <div class="printForm panel-detail" >
                            <c:choose>
                                <c:when test="${isEmptyForm==true}">
                                    <div class="noForm">没有设置流程表单。</div>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${hasGlobalFlowNo }">
                                        <div align="right">工单号：${processRun.globalFlowNo}</div>
                                    </c:if>
                                    <c:choose>
                                        <c:when test="${isExtForm}">
                                            <c:choose>
                                                <c:when test="${!empty detailUrl}">
                                                    <iframe id="frameDetail" src="${detailUrl}" scrolling="no"  frameborder="no"  width="100%" height="100%"></iframe>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="printForm" id="divExternalForm" formUrl="${form}"></div>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <div id="custformDiv" class="printForm" type="custform">
                                            	${form}
                                            	<div id="approvalHistory"></div>
                                            </div>
                                            <input type="hidden" include="1" name="formData" id="formData" />
                                        </c:otherwise>
                                    </c:choose> 
                                </c:otherwise>
                            </c:choose>
                    </div>
                    <input type="hidden" id="taskId" include="1" name="taskId" value="${task.id}"/> 
                    <%--驳回和撤销投票为再次提交 --%>
                    <c:choose>
                        <c:when test="${processRun.status eq 5 or processRun.status eq 6}">
                            <input type="hidden" include="1" id="voteAgree" name="voteAgree" value="34"/>   
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" include="1" id="voteAgree" name="voteAgree" value="1"/>
                        </c:otherwise>
                    </c:choose>
                    <input type="hidden" include="1" id="back" name="back" value=""/>
                    <input type="hidden" include="1" id="actDefId" name="actDefId" value="${bpmDefinition.actDefId}"/>
                    <input type="hidden" include="1" name="defId" value="${bpmDefinition.defId}"/>
                    <input type="hidden" include="1" id="isManage" name="isManage" value="${isManage}"/>
                    <input type="hidden" include="1" id="businessKey" name="businessKey" value="${processRun.businessKey}"/>
                    <input type="hidden" include="1" id="startNode" name="startNode" value="${toBackNodeId}"/>
                    <input type="hidden" include="1" name="curUserId" value="${curUserId}"/>
                    <input type="hidden" include="1" name="curUserName" value="${curUserName}"/>
                    <input type="hidden" include="1" id="currentNode" name="currentNode" value="${task.taskDefinitionKey}"/>
                    <input type="hidden" include="1" name="formKey" value="${formKey}"/>
                    <input type="hidden" include="1" id="curNodeName" value="${task.name}"/>
                    <input type="hidden" include="1" id="runId" value="${processRun.runId}"/>
                    <input type="hidden" include="1" id="phone_id"/>
                    <input id="pageType" type="hidden" value="taskToStart" />
                    <input type="hidden" include="1" id="theFormKey" value="${formKey}"/>

                    <input type="hidden" id="finSealInfo" value="" />
                    <input type="hidden" id="tempSealInfo" value=""/>
                    <input type="hidden" id="curCtx" value="${ctx}" />
                </div>
         </div>
    </form>
    
    
    <div id="JS_special_contract_print" class="special" style="display: none;">
    	<p>&nbsp;</p>
    	<div style="font-size: 28px;line-height:55px;">
		    <div>
				<h2 style="text-align:center;line-height: 2em;font-size: 29px;">深圳市深水水务咨询有限公司</h2>
				<h2 style="text-align:center;line-height: 2em;font-size: 29px;">解除（终止）劳动合同证明（存根）</h2>
		    </div>
	    	<p style="text-align:right;">
			    <span>第<span class="contractWordNo">_____</span>号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			</p>
			<p  style="text-align:left;padding-left: 80px;">
		    	&nbsp; &nbsp;<span class='contractWordName'></span>&nbsp;&nbsp;同志（身份证号&nbsp;&nbsp;<span class="contractWordCardNo">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;
		    	，性别&nbsp;&nbsp;<span class='contractWordSex'></span>&nbsp;&nbsp;，年
		    </p>
		    <p  style="text-align:left;padding-left: 80px;">	
		    	龄&nbsp; &nbsp;<span class="contractWordCardAge"></span>&nbsp;&nbsp;），
		    	自&nbsp;&nbsp;<span class='contractWordYear'></span>至
		        &nbsp;&nbsp;<span  class='contractWordYear1'></span>在我
		   </p>
		   <p  style="text-align:left;padding-left: 80px;">	  
		                      公司从事&nbsp;&nbsp;<span class='contractWordZhiye' >_________________________</span>&nbsp;&nbsp;工作，劳动合同期限至&nbsp;&nbsp;
		        <span class="contractWordLimitYear"></span>。
		       	 现因&nbsp;&nbsp;
		        <span class="reason"> </span><span >，已终止、解除劳动合同。</span>
		    </p>
			<p  style="text-align:left;padding-left: 80px;">
			    <span>&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;</span><span>特此证明。</span>
			</p>
			<p  style="text-align:left;padding-left: 80px;">
			    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span>经办人：</span>
			</p>
			<p style="text-align:right;">
			    <span >签收：________&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			</p>
			<p style="text-align:center">
			    <span>﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍</span>
			</p>
    	</div>
    	
    	<div style="font-size: 28px;line-height:55px;">
		    <div>
				<h2 style="text-align:center;line-height: 2em;font-size: 29px;">深圳市深水水务咨询有限公司</h2>
				<h2 style="text-align:center;line-height: 2em;font-size: 29px;">解除（终止）劳动合同证明</h2>
		    </div>
	    	<p style="text-align:right;">
			    <span>第<span class="contractWordNo">_____</span>号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			</p>
			<p  style="text-align:left;padding-left: 80px;">
		    	&nbsp; &nbsp;<span class='contractWordName'></span>&nbsp;&nbsp;同志（身份证号&nbsp;&nbsp;<span class="contractWordCardNo">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;
		    	，性别&nbsp;&nbsp;<span class='contractWordSex'></span>&nbsp;&nbsp;，年
		    </p>
		    <p  style="text-align:left;padding-left: 80px;">	
		    	龄&nbsp; &nbsp;<span class="contractWordCardAge"></span>&nbsp;&nbsp;），
		    	自&nbsp;&nbsp;<span class='contractWordYear'></span>至
		        &nbsp;&nbsp;<span  class='contractWordYear1'></span>在我
		   </p>
		   <p  style="text-align:left;padding-left: 80px;">	  
		                      公司从事&nbsp;&nbsp;<span class='contractWordZhiye' >_________________________</span>&nbsp;&nbsp;工作，劳动合同期限至&nbsp;&nbsp;
		        <span class="contractWordLimitYear"></span>。
		       	 现因&nbsp;&nbsp;
		        <span class="reason"></span><span >，已终止、解除劳动合同。</span>
		    </p>
			<p  style="text-align:left;padding-left: 80px;">
			    <span>&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;</span><span>特此证明。</span>
			</p>
			<p  style="text-align:left;padding-left: 80px;">
			    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span>经办人：</span>
			</p>
			<p style="text-align:right;">
			    <span >（单位盖章）&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			</p>
			<p style="text-align:right;">
			    <span>&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			</p>
    	</div>
    </div>
    
    
    
    <div id="JS_income_print" class="" style="display:none ;">
    	<p>&nbsp;</p>
    	<div style="font-size: 25px;line-height:85px;">
		    <div>
				<h2 style="text-align:center;line-height: 4em;font-size: 35px;">个人收入证明</h2>
		    </div>
	    	 
		   <p  style="text-align:left;padding-left: 80px;">
		    	&nbsp; &nbsp;兹证明&nbsp;<span class='incomeWordName'></span>&nbsp;先生/女士（身份证号:&nbsp;<span class="incomeWordCardNo">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;
		    	为本单位正式职工， 自&nbsp;&nbsp;<span class='incomeWordYear'></span>至今一直在我单位，目前担任&nbsp;<span class='incomeWordZhiye' >办公室主任</span>&nbsp;&nbsp;,近一年度该员工税后月平均收入人民币&nbsp;&nbsp;<span class="incomeYearMaxIncome"></span>
		                      （小写：<span class="incomeYearMinIncome"></span>元）。
		    </p>
			<p  style="text-align:left;padding-left: 80px;">
			    &nbsp; &nbsp;&nbsp;&nbsp;&nbsp; 特此证明
			</p>
			<p  style="text-align:left;padding-left: 80px;">
			     
			</p>
			<p  style="text-align:left;padding-left: 80px;">
			    &nbsp; &nbsp;&nbsp;&nbsp;&nbsp; 本证明仅用于证明我单位员工的工作情况和工资收入，不作为我公司对该员工任何形式的承诺或担保。
			</p>
			<p style="text-align:right;padding-right: 300px;line-height: 5em;">
			    <span >单位公章：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			</p>
			<p style="text-align:right;">
			    <span>&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			</p>
			<p  style="text-align:left;padding-left: 80px;">
			   单位全称： 深圳市深水水务咨询有限公司
			</p>
			<p  style="text-align:left;padding-left: 80px;">
			    单位地址：深圳市罗湖区黄贝街道延芳路63号
			</p>
			<p  style="text-align:left;padding-left: 80px;">
			     单位电话：0755-22385939
			</p>
			<p  style="text-align:left;padding-left: 80px;">
			     人力资源部门联系人：梁晖 
			</p>
    	</div>
    </div>
    
    
    <div id="JS_director_print" class="" style="display: none;">
    	<p>&nbsp;</p>
    	<div style="font-size: 25px;line-height:85px;">
		    <div>
				<h2 style="text-align:center;line-height: 4em;font-size: 35px;">总监理工程师任命书</h2>
		    </div>
		    <div style="text-align:left;">
		    	<div style="width:55%;display: inline-block;">&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;工程名称：&nbsp;<span class='directorWordName'></span></div>
		    	<div style="width:40%;display: inline-block;">&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;任命书编号：&nbsp;<span class="directorWordCardNo"></span></div>
		    </div>
			<div style="border: 1px solid black;margin-left:25px;">
				<p  style="text-align:left;">
				   &nbsp; 致：<u >&nbsp;&nbsp;&nbsp;&nbsp;<span class="directorBuildName"></span>&nbsp;&nbsp;&nbsp;&nbsp;</u>
				</p>
				<p  style="text-align:left;padding-left: 20px;">    
				   &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;兹授权 <u>&nbsp;&nbsp;&nbsp;&nbsp;<span class="directorGongchengName"></span>&nbsp;&nbsp;&nbsp;</u>（监理工程师注册号：
				   <u>&nbsp;&nbsp;&nbsp;&nbsp;<span class="directorGongchengNo"></span>&nbsp;&nbsp;&nbsp;&nbsp;</u>）为我单位
	               <u>&nbsp;&nbsp;&nbsp;&nbsp;<span class='directorWordName'></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>项目总监理工程师，负责履行建设工程监理合同，主持项目监理机构工作。
				</p>
				</br>
				</br>
				<p  style="text-align:left;padding-left:260px;">
				  监理单位：深圳市深水水务咨询有限公司
				</p>
				<p  style="text-align:left;padding-left:260px;">
				法人代表（签字）：
				</p>
				<p  style="text-align:left;padding-left:260px;">
				 被任命人：
				</p>
				<p  style="text-align:left;padding-left:360px;">
				（签字、加盖注册印章）
				</p>
				<p  style="text-align:left;padding-left:260px;">
				 二O&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日
				</p>
				<p  style="text-align:left;padding-left:100px;">
				   附件：监理工程师资格及注册证书复印件
				</p>
				</br>
			</div>
			<p  style="text-align:center;">
			  填报说明：本表一式<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>份，建设单位、监理机构各<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>份，各承包人<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>份。
			</p>
    	</div>
    </div>
    
    
    <div id="JS_ProjectNoticebook_print" class="" style="display: none;">
    	<p>&nbsp;</p>
    	<div style="font-size: 22px;line-height:55px;">
		    <div>
				<h2 style="text-align:center;line-height: 4em;font-size: 28px;">项目监理机构设置通知书</h2>
		    </div>
		    <div style="text-align:left;">
		    	<div style="width:55%;display: inline-block;">&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;工程名称：&nbsp;<span class='projectNoticebookWordName'></span></div>
		    	<div style="width:40%;display: inline-block;">&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;编号：&nbsp;<span class="projectNoticebookWordCardNo"></span></div>
		    </div>
			<div style="border:1px solid black;margin-left:25px;border-bottom: 0px;">
				<p  style="text-align:left;">
				   &nbsp;&nbsp;&nbsp; 致：<u >&nbsp;&nbsp;&nbsp;&nbsp;<span class="projectNoticebookBuildName">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;</u>（建设单位）:
				</p>
				<p  style="text-align:left;padding-left: 20px;">    
				   &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;根据&nbsp;<u>&nbsp;&nbsp;&nbsp;&nbsp;<span class="projectNoticebookWordName">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;</u>&nbsp;工程实际监理业务需要，我司有针对性地设置和调配项目监理架构及其组成人员，现将本项目监理机构监理人员名单及其专业分工通知贵方，请贵方予以确认。
				</p>
				<p  style="text-align:left;padding-left:20px;">
				   &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;附件：监理机构成员资格证明资料复印件
				</p>
				<p  style="text-align:left;padding-left:131px;">
				   项目监理机构组织架构
				</p>
				<p  style="text-align:left;padding-left:460px;">
				  监理机构：（项目章）
				</p>
				<p  style="text-align:left;padding-left:460px;">
				 总监理工程师：  
				</p>
				<p  style="text-align:left;padding-left:460px;">
				 日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日
				</p>
			</div>
			<div style="border: 1px solid black;margin-left:25px;">
				<p  style="text-align:left;">
				   &nbsp;&nbsp;&nbsp; 建设单位确认意见：
				</p>
				<p  style="text-align:left;">
				   &nbsp;&nbsp;&nbsp; 同意此项目监理组织架构及人员岗位分工。
				</p>
				<p  style="text-align:left;padding-left:460px;">
				  建设单位（盖项目章）
				</p>
				<p  style="text-align:left;padding-left:460px;">
				 负责人（签字）：       
				</p>
				<p  style="text-align:left;padding-left:460px;">
				 日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日
				</p>
				<p  style="text-align:left;">
				   &nbsp;&nbsp;&nbsp; 报：工程质量、安全监督部门各1份
				</p>
				<p  style="text-align:left;">
				   &nbsp;&nbsp;&nbsp; 抄送（仅此表）：（承包单位项目经理部）1份
				</p>
				<table class="ProjectNoticebook" border="1" style="border-left: 0px;" cellspacing="0" cellpadding="2">
					<tr><td style="text-align: center;">姓  名</td><td style="text-align: center;">专业分工</td><td style="text-align: center;">职  务</td><td style="text-align: center;">执业/岗位证书号</td><td style="text-align: center;">签  名</td></tr>
					<tr><td></td><td></td><td></td><td></td><td></td></tr>
					<tr><td></td><td></td><td></td><td></td><td></td></tr>
					<tr><td></td><td></td><td></td><td></td><td></td></tr>
					<tr><td></td><td></td><td></td><td></td><td></td></tr>
					<tr><td></td><td></td><td></td><td></td><td></td></tr>
					<tr><td></td><td></td><td></td><td></td><td></td></tr>
				</table>
			</div>
			<p  style="text-align:left;padding-left:40px;">
				 附注：1、本表根据《深圳市施工监理规程》4.1制定；
			</p>
			<p  style="text-align:left;padding-left:105px;">
      			2、本表一式四份，发包人、监理机构、承包人、档案馆各一份。
			</p>
    	</div>
    </div>
    
    
    <div id="JS_ProjectOrganization_print" class="" style="display: none;">
    	<p>&nbsp;</p>
    	<div style="font-size: 22px;line-height:51px;">
		    <div>
				<h2 style="text-align:center;line-height: 4em;font-size: 28px;">项目监理机构设置通知书</h2>
		    </div>
		    <div style="text-align:left;">
		    	<div style="width:55%;display: inline-block;">&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;工程名称：&nbsp;<span class='projectOrganizationName'></span></div>
		    	<div style="width:40%;display: inline-block;">&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;编号：&nbsp;<span class="projectOrganizationCardNo"></span></div>
		    </div>
			<div style="border:1px solid black;margin-left:25px;">
				<p  style="text-align:left;">
				   &nbsp;&nbsp;&nbsp; 致：&nbsp;<u >&nbsp;&nbsp;&nbsp;&nbsp;<span class="projectOrganizationBuildName">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;</u>（建设单位）:
				</p>
				<p  style="text-align:left;">
				   &nbsp;&nbsp;&nbsp; 致：&nbsp;<u >&nbsp;&nbsp;&nbsp;&nbsp;<span class="projectOrganizationContractingName">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;</u>（承包单位项目经理部）:
				</p>
				<p  style="text-align:left;padding-left: 20px;">    
				   &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;一、现授权总监&nbsp;<u >&nbsp;&nbsp;&nbsp;&nbsp;<span class="projectOrganizationDirectorName">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;</u>&nbsp;同志在工程中使用“&nbsp;<u >&nbsp;&nbsp;&nbsp;&nbsp;<span class="projectOrganizationSealNum">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;</u>&nbsp;”印章（如下图）。
				</p>
				<p  style="text-align:left;padding-left:20px;">
				   &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;二、授权期限：从贵单位收到本授权书之日起至监理合同及监理业务完成终止之日止。
				</p>
				<p  style="text-align:left;padding-left:20px;">
				   &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;三、印章使用范围：所有应由监理审核签认的工程资料和来往文件。
				</p>
				<p  style="text-align:left;padding-left:20px;">
				   &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;1.监理合同履行期间，授权人更换项目总监理工程师的，被授权人在本授权书上的授权行为在贵单位收到授权人更换项目总监通知之日起执行终止，由新任项目总监自动履行本授权书的权利和义务，本公司不再另行通知。
				</p>
				<p  style="text-align:left;padding-left:20px;">
				   &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;2.在递交贵单位的需加盖本授权书印章的文件，还应由（总）监理工程师签字方可生效；仅加盖印章无（总）监理工程师签字的无效。
				</p>
				<p  style="text-align:left;padding-left:20px;">
				   &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;3.总监代表、专业监理工程师在监理合同履行过程中使用该印章，必须有总监理工程师的授权，且不得超越授权书规定的使用范围，超越授权书的规定范围使用无效。
				</p>
				<p  style="text-align:left;padding-left:20px;">
				   &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;4.除《开工报告》、《设计图纸会审记录》、《专项工程验收记录》系列表、《分部（子单位）质量验收记录》系列表、《工程验收及备案资料》系列表由企业法人出具的文件资料及现行法律法规规定要盖法人章的均盖“企业法人章”外，其他均加盖“项目章”也为有效文函。
				</p>
				<p  style="text-align:left;padding-left:20px;">
				   &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;项目监理机构印章样板
				</p>
				<p  style="text-align:left;padding-left:360px;">
				监理单位（法人章）
				</p>
				<p  style="text-align:left;padding-left:360px;">
				 法人代表（签字）：
				</p>
				<p  style="text-align:left;padding-left:360px;">
				 被授权人（签字）：         
				</p>
				<p  style="text-align:left;padding-left:360px;">
				 日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日
				</p>
				<p  style="text-align:left;">
				   &nbsp;&nbsp;&nbsp; 报：建设单位、工程质量、安全监督部门各1份
				</p>
				<p  style="text-align:left;">
				   &nbsp;&nbsp;&nbsp; 发：承包单位1份
				</p>
			</div>
			<p  style="text-align:left;padding-left:40px;">
				 附注：1、本表根据《深圳市施工监理规程》4.1制定；
			</p>
			<p  style="text-align:left;padding-left:105px;">
      			2、本表一式四份，发包人、监理机构、承包人、档案馆各一份。
			</p>
    	</div>
    </div>
    
<script type="text/javascript">
    $(function(){
        var defId=$("input[name='defId']").val();
        var currentNode=$("input[name='currentNode']").val();
        //是入职流程并且是领取电话餐卡工作证环节，获取可用电话号码
        if(defId!=null && defId=='10000000050327' 
                && currentNode!=null && currentNode=="UserTask4"){
            getPhoneNum();
        }
        function getPhoneNum(){
            var phone=$("#phone").val();
            if(null==phone || phone==""){
                $.ajax({
                    type:"get",
                    url:"/makshi/telList/phoneList/rzlc_phone.ht",
                    success:function(data){
                        var dataObj=eval("("+data+")");
                        var message=dataObj.message;
                        if(null!=message && message!=""){
                            message=eval("("+message+")");
                            var phone_number=message.phone_number;
                            var short_number=message.short_munber;
                            if(null!=phone_number && phone_number!=""){
                                $("#phone_id").val(message.id);
                                $("#phone").val(phone_number);
                                $("#phone").prop("readonly","readonly");
                                $("#short_number").prop("readonly","readonly");
                            }
                            if(short_number!=null && short_number!=""){
                                $("#short_number").val(short_number);
                            }
                        }
                    }
                });
            }
        }
        if(defId!=null && defId=='10000000480116' 
            && currentNode!=null && currentNode=="UserTask11"){
            autoSetUserInfo();
        }
    });
    $(function(){
        var formKey=$("input[name='formKey']").val();
        //请假流程，查询年假
        var currentNode=$("input[name='currentNode']").val();
        if(formKey!=null && formKey=='qxjlcbd' && currentNode && currentNode=='UserTask1'){
            getAnnualVacationDay();
        }
        function getAnnualVacationDay(){
            var date=new Date();
            var t=date.getTime();
                $.ajax({
                    type:"get",
                    url:"/makshi/worksheet/annualVacation/annual_vacation_days.ht?t="+t,
                    success:function(data){
                        var dataObj=eval("("+data+")");
                        var vacationDay=dataObj.message;
                        $("#remain_annual_leave").val(vacationDay);
                        $("#remain_annual_leave").attr("class","none-border-text");
                        $("#remain_annual_leave").attr("readonly",true);
                    }
                });
                $("#remain_annual_leave").attr("class","none-border-text");
                $("#remain_annual_leave").attr("readonly",true);
                //$("#leave_days").attr("class","none-border-text");
                //$("#leave_days").attr("readonly",true);
        }
    });
    
    function getVacationDay(){
        var leave_start=$("#leave_start").val();
        var leave_end=$("#leave_end").val();
        var levea_start_select=$("#levea_start_select").val();
        var levea_end_select=$("#levea_end_select").val();
        var leava_type=$("select[name='m:vaction:leave_type'] option:selected").val();
        var date=new Date();
        var t=date.getTime();
        $.ajax({
            type:"get",
            data:{leave_start:leave_start,leave_end:leave_end,levea_start_select:levea_start_select,levea_end_select:levea_end_select,leava_type:leava_type},
            url:"/makshi/worksheet/annualVacation/leave_vacation_days.ht?t="+t,
            success:function(data){
                var dataObj=eval("("+data+")");
                var vacationDay=dataObj.message;
                $("#leave_days").val(vacationDay);
                //$("#leave_days").attr("class","none-border-text");
                //$("#leave_days").attr("readonly",true);
            }
        });
    }
</script>

    <script type="text/javascript">

        //初始化印章图片
        $(function () {
            if ($("#dirSubDisOp").length > 0 && $("#dirSubDisOp").val() == 'seal_cancel') {

                var sealId = $("#td_sealid input").val();
                var imgUri = $("#td_sealimguri input").val();

                //设置印章图片
                if (imgUri == 'undefined' || typeof (imgUri) == 'undefined' || imgUri == '') {
                    return;
                }

                var fUrl = $('#curCtx').val() + imgUri;
                var newSealImg = '<img id="newSealImg" style="height: 300px;width: auto;" alt="印章图片" src="' + fUrl + '"/>';

                if ($('#td-sealImg').length > 0) {
                    if ($('#td-sealImg img').length > 0) {
                        $('#td-sealImg img').remove();
                    }
                    $('#td-sealImg').append(newSealImg);
                }


            }
        });

        //设置印章信息
        var cfgSealInfo = function () {
            //只有印章注销才执行
            if($("#dirSubDisOp").length > 0 && $("#dirSubDisOp").val() == 'seal_cancel') {
                if ($('#tempSealInfo').val() != '') {
                    $('#finSealInfo').val($('#tempSealInfo').val());

                    var json = eval('(' + $('#tempSealInfo').val() + ')');

                    if(typeof(json.ID) != 'undefined' && typeof(json.seal_img) != 'undefined') {
                        $('input[name="m:seal_cancel:sealId"]').val(json.ID);
                        $('input[name="m:seal_cancel:sealimguri"]').val(json.seal_img);
                        setSealImg(json.seal_img);
                    }

                }
            }
        }

        //设置中间变量，印章信息
        var setTempSealInfo = function (data) {
            //只有印章注销才执行
            if($("#dirSubDisOp").length > 0 && $("#dirSubDisOp").val() == 'seal_cancel') {
                $('#tempSealInfo').val(data);

            }
        }

        //设置印章图片
        var setSealImg = function (sufUrl) {

            if(sufUrl == 'undefined' || typeof (sufUrl) == 'undefined') {
                return;
            }

            var fUrl = $('#curCtx').val() + sufUrl;
            var newSealImg = '<img id="newSealImg" style="height: 300px;width: auto;" alt="" src="' + fUrl + '"/>';

            if($('#td-sealImg').length > 0) {
                if ($('#td-sealImg img').length > 0 ) {
                    $('#td-sealImg img').remove();
                }
                $('#td-sealImg').append(newSealImg);
            }
        }
        
        function specialContractPrint(){
            var oldstr = document.body.innerHTML;  
            var data = getDataFromTable();
            writeSpecialContractPrint(data);

            var headstr = "<html><head><title></title></head><body>";  
            var footstr = "</body>";  
            var printData = document.getElementById("JS_special_contract_print").innerHTML;
            document.body.innerHTML = headstr+printData+footstr;  
            window.print();  
            document.body.innerHTML = oldstr; 
            return false;
        }
        
        function writeSpecialContractPrint(data){
        	 $('.contractWordYear').html(getKeyVal('劳动合同开始时间', data).replace('-','&nbsp;&nbsp;年&nbsp;&nbsp;').replace('-','&nbsp;&nbsp;月&nbsp;&nbsp;')+'&nbsp;&nbsp;日');
        	 $('.contractWordYear1').html(getKeyVal('解除劳动合同时间', data).replace('-','&nbsp;&nbsp;年&nbsp;&nbsp;').replace('-','&nbsp;&nbsp;月&nbsp;&nbsp;')+'&nbsp;&nbsp;日');
        	 $('.contractWordLimitYear').html(getKeyVal('劳动合同终止时间', data).replace('-','&nbsp;&nbsp;年&nbsp;&nbsp;').replace('-','&nbsp;&nbsp;月&nbsp;&nbsp;')+'&nbsp;&nbsp;日');
        }
        
        function getKeyVal(key, data){
            var i = 0, len = data.length;

            for(; i < len; i++){
                if(data[i].key === key){
                    return data[i].val;
                }
            }
            return '';
        }

        
        function getDataFromTable(){
            var $table = $('table');
            var data = [];
            $table.find('tr').each(function(index, item){
                var ths = $(item).find('th');
                var tds = $(item).find('td');
                data.push({
                    key: $(ths[0]).text(),
                    val: $(tds[0]).text()
                });
                if($.trim($(ths[1]).text()).length){
                    key = $(ths[1]).text();
                    val = $(tds[1]).text();    
                    data.push({
                        key: key,
                        val: val
                    });
                }
            });
            return data;
        }
        
        function autoSetUserInfo(){
            var userId=$("#userId_procedure").html();
            var alias="common_user_info_search";
            var date=new Date();
             var t=date.getTime();
           $.ajax({
               type : "POST", 
               url:"/platform/bpm/bpmFormQuery/doQuery.ht?t="+t,
               data:{alias:alias,querydata:"{userId:"+userId+"}",page:1,pagesize:10},
               dataType: "json",
               asysc:false,
               success:function(data){ 
                   if(data!=null && data.list!=null && data.list.length>0){
                       var rowData=data.list[0];
                       console.log(rowData.identification_id);
                       $(".contractWordCardNo").html(rowData.identification_id);
                       if(rowData.birthday){
                        getAge(rowData.birthday);
                       }
                       setSex(rowData.sex);
                       if($("td[name='workName']").html().length>0){
                      	 $(".contractWordZhiye").html($("td[name='workName']").html());
                       }
                       $(".contractWordYear").html($("td[name='startWorkBegin']").html());
                       $(".contractWordYear1").html($("td[name='startWorkEnd']").html());
                       $(".contractWordLimitYear").html($("td[name='contractEnd']").html());
                       $(".reason").html($("td[name='reason']").html());
                       $(".contractWordName").html($("td[name='fullname']").html());
                       $(".contractWordNo").html($("td[name='globalNo']").html());
                   }
               }
           });
       }
        
        function getDataFromIncomeTable(){
            var $table = $('table');
            var data = [];
            $table.find('tr').each(function(index, item){
                var tds = $(item).find('td');
                var key = $.trim($(tds[0]).text()).replace(':', "");
                var val = $.trim($(tds[1]).text());

                data.push({
                    key: key,
                    val: val
                });

                if($.trim($(tds[2]).text()).length){
                    key = $(tds[2]).text().replace(':', "");
                    val = $(tds[3]).text();    
                    data.push({
                        key: key,
                        val: val
                    });
                }
       
            });

            return data;
        }
        
		function specialIncomePrint(){
            var oldstr = document.body.innerHTML;  
            var data = getDataFromIncomeTable();
            writeSpecialIncomePrint(data);
            var headstr = "<html><head><title></title></head><body>";  
            var footstr = "</body>";  
            var printData = document.getElementById("JS_income_print").innerHTML;
            document.body.innerHTML = headstr+printData+footstr;  
            window.print();  
            document.body.innerHTML = oldstr; 
            return false;
        }
		function writeSpecialIncomePrint(data){
       	 $('.incomeWordName').html(getKeyVal('申请人', data));
       	 $('.incomeWordCardNo').html(getKeyVal('身份证号', data));
       	 $('.incomeYearMaxIncome').html(getKeyVal('大写', data));
       	 $('.incomeYearMinIncome').html(getKeyVal('月平均收入', data));
       	 $('.incomeWordZhiye').html(getKeyVal('岗位', data));
       	 $('.incomeWordYear').html(getKeyVal('入职日期', data).replace('-','&nbsp;&nbsp;年&nbsp;&nbsp;').replace('-','&nbsp;&nbsp;月&nbsp;&nbsp;')+'&nbsp;&nbsp;日');
       }
	 
		
		function specialDirectorPrint(){
            var oldstr = document.body.innerHTML;  
            var data = getDataFromIncomeTable();
            writeDirectorPrint(data);
            var headstr = "<html><head><title></title></head><body>";  
            var footstr = "</body>";  
            var printData = document.getElementById("JS_director_print").innerHTML;
            document.body.innerHTML = headstr+printData+footstr;  
            window.print();  
            document.body.innerHTML = oldstr; 
            return false;
		}
		
		function writeDirectorPrint(data){
	       	 $('.directorWordName').html(getKeyVal('工程名称', data));
	       	 $('.directorWordCardNo').html(getKeyVal('任命书编号', data));
	       	 $('.directorBuildName').html(getKeyVal('致：', data));
	       	 $('.directorGongchengName').html(getKeyVal('总监姓名', data));
	       	 $('.directorGongchengNo').html(getKeyVal('监理工程师注册号', data));
	    }
		
		
		function specialProjectNoticebookPrint(){
            var oldstr = document.body.innerHTML;  
            var data = getDataFromIncomeTable();
            writeProjectNoticebookPrint(data);
            var headstr = "<html><head><title></title></head><body>";  
            var footstr = "</body>";  
            var printData = document.getElementById("JS_ProjectNoticebook_print").innerHTML;
            document.body.innerHTML = headstr+printData+footstr;  
            window.print();  
            document.body.innerHTML = oldstr; 
            return false;
		}
		
		function writeProjectNoticebookPrint(data){
	      	 $('.projectNoticebookWordName').html(getKeyVal('工程名称', data));
	      	 $('.projectNoticebookWordCardNo').html(getKeyVal('编号', data));
	      	 $('.projectNoticebookBuildName').html(getKeyVal('建设单位', data));
	    }
		
		function specialProjectOrganizationPrint(){
	        var oldstr = document.body.innerHTML;  
	        var data = getDataFromIncomeTable();
	        writeProjectOrganizationPrint(data);
	        var headstr = "<html><head><title></title></head><body>";  
	        var footstr = "</body>";  
	        var printData = document.getElementById("JS_ProjectOrganization_print").innerHTML;
	        document.body.innerHTML = headstr+printData+footstr;  
	        window.print();  
	        document.body.innerHTML = oldstr; 
	        return false;
		}
		
		function writeProjectOrganizationPrint(data){
	      	 $('.projectOrganizationName').html(getKeyVal('工程名称', data));
	      	 $('.projectOrganizationCardNo').html(getKeyVal('编号', data));
	      	 $('.projectOrganizationDirectorName').html(getKeyVal('授权总监', data));
	      	 $('.projectOrganizationBuildName').html(getKeyVal('建设单位', data));
	      	 $('.projectOrganizationSealNum').html(getKeyVal('印章', data));
	      	 $('.projectOrganizationContractingName').html(getKeyVal('承包单位项目经理部', data));
	       }
		
/*         $("#sendReceptDoc").click(function(){
        	var runId = $("#runId").val();
        	var businessKey = $("#businessKey").val();
        	$.ligerDialog.open({ url: '/makshi/dispatch/dispatch/setSendPerson.ht?type=receipt&runId='+runId+'&businessKey='+businessKey,title:'选择人员',width:600,height: 600, isResize:true});
        }); */
        
    </script>
</body>
</html>