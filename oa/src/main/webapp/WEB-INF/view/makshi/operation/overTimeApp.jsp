<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>流程启动--值班申请--版本:1</title>
    <%@include file="/codegen/include/customForm.jsp" %>
    <%@include file="/commons/include/ueditor.jsp" %>
</head>
<script src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/BpmImageDialog.js"></script>
<body class="oa-mw-page">
        <div class="oa-pd20">
            <a class="run oa-button oa-button--primary oa-button--blue">提交</a>
            <a class="oa-button oa-button--primary oa-button--blue" onclick="showBpmImageDlg()"><span></span>流程图</a>
        </div>
        <div style="padding:6px 8px 3px 12px;" class="noprint">
                        <b>流程简述：</b>
        </div>
        <div class="oa-pd20">
            <form id="urlForm" >
                <table class="oa-table--default" parser="addpermission" data-sort="sortDisabled" width="-420" cellspacing="0" cellpadding="2" border="1">
                    <caption>值班申请</caption>
                    <tr>
                        <th>申请人</th>
                        <td>
                            ${userName}
                            <input type="hidden" name="m:overTime:applicant"  lablename="申请人"  value="${userName}" />
                            <input type="hidden" name="m:overTime:applicantID"  lablename="申请人ID"  value="${userId}" />
                        </td>
                        <th>岗位</th>
                        <td>
                            ${posName}
                            <input class="oa-url"  name="m:overTime:position" value="${posName}" type="hidden" />
                        </td>
                    </tr>
                    <tr>
                        <th>值班申请日期</th>
                        <td>
                            <input id="appliDate" parser="datetable" name="m:overTime:overTime_appliDate" lablename="值班申请日期" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" type="hidden"/>
                            ${now}
                        </td>
                        <th>值班类型</th>
                        <td>
                            <div class="oa-select-wrap oa-input-wrap--ib">
                                <select class="oa-select" name="m:overTime:type" lablename="加班类型" controltype="select" validate="{empty:false}" val="${overtime.type}">
                                    <option value="平时">平时</option>
                                    <option value="周日">周日</option>
                                    <option value="节假日">节假日</option>
                                </select>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>值班开始时间</th>
                        <td>
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input type="text" id="startDate" name="m:overTime:overTime_start" class="Wdate oa-input"
                                displaydate="0"　
                                onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}',onpicked:function(dp){countOffdays();}})"  validate="{&quot;date&quot;:&quot;true&quot;,&quot;required&quot;:&quot;true&quot;}"/>
                            </div>
                            <div class="oa-select-wrap oa-input-wrap--ib">
                                <select class="oa-select" id="startPoint" name="m:overTime:overTime_start_point" onchange="countOffdays();"  controltype="select" validate="{empty:false}" val="${overtime.overTime_start_point}">
                                    <option value="0">上午</option>
                                    <option value="1">下午</option>
                                </select>
                            </div>
                        </td>
                        <th>
                            是否包含上半夜
                        </th>
                        <td>
                            <div class="oa-select-wrap oa-input-wrap--ib">
                                <select class="oa-select" name="m:overTime:include_front"  controltype="select" validate="{empty:false}" val="">
                                    <option value="0">否</option>
                                    <option value="1">是</option>
                                </select>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>值班结束时间</th>
                        <td>
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input type="text" id="endDate" name="m:overTime:overTime_end" class="Wdate oa-input" validate="{&quot;date&quot;:&quot;true&quot;,&quot;required&quot;:&quot;true&quot;}" 
                               onclick = "WdatePicker({minDate:'#F{$dp.$D(\'startDate\');}',onpicked:function(dp){countOffdays();}})"  />
                            </div>
                            <div class="oa-select-wrap oa-input-wrap--ib">
                                <select class="oa-select" id="endPoint" name="m:overTime:overTime_end_point"  onchange="countOffdays();" controltype="select" validate="{empty:false}" val="">
                                    <option value="0">上午</option>
                                    <option value="1">下午</option>
                                </select>                              
                            </div>

                        </td>
                        <th>是否包含下半夜</th>
                        <td>
                            <div class="oa-select-wrap oa-input-wrap--ib">
                                <select class="oa-select" name="m:overTime:include_later"  controltype="select" validate="{empty:false}" val="">
                                    <option value="0">否</option>
                                    <option value="1">是</option>
                                </select>
                            </div>
                        </td>
                    </tr>
                    <th>值班天数</th>
                    <td colspan="3">
                    		<span id="showdays">0.0</span>
	                   		<input type="hidden" id="offdays" class = "oa-input" name="m:overTime:offdays" value="0.0"/>
                    </td>
                    </tr>
                    <c:if test="${isGropLeader!=1}">
                        <tr>
                            <c:if test="${isProjectLeader!=1}">
                                <th>项目负责人</th>
                                <td colspan="3">
                                    <div class="oa-input-wrap oa-input-wrap--ib">
                                        <input type="text" name="m:overTime:project_leader" ctltype="selector" class="oa-input user oa-hidden-trigger" lablename="项目负责人"   isflag="tableflag"  validate="{required:true}"/>
                                    </div>
                                    <button class="oa-button-label oa-trigger-hidden-button" type="button">选择人员</button>
                                </td>
                            </c:if>
                            <c:if test="${isProjectLeader==1}">
                                <th>是否需要片区负责人审核</th>
                                <td colspan="3">
                                    <label><input name="m:overTime:zoneLeader_check" type="radio" value="0" />否</label>
                                    <label><input name="m:overTime:zoneLeader_check" type="radio" value="1" checked="checked" />是</label>
                                </td>
                            </c:if>
                        </tr>
                    </c:if>
                    <tr>
                        <th>值班事由</th>
                        <td colspan="3">
                            <div class="oa-textarea-wrap">
                                <textarea name="m:overTime:overTime_reason" lablename="值班事由" class="oa-textarea" rows="5" cols="40" validate="{maxlength:1000}"  isflag="tableflag"></textarea>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>备注</th>
                        <td colspan="3">
                            <div class="oa-textarea-wrap">
                                <textarea name="m:overTime:overTime_remark" lablename="备注" class="oa-textarea" rows="5" cols="40" validate="{maxlength:1000}"  isflag="tableflag"></textarea>
                            </div>
                        </td>
                    </tr>
               		<tr>
			  			 <th>附件</th>
   						 <td colspan="3">
   					     <input ctltype="attachment" name="m:overTime:overTime_attachment" isdirectupload="0" right="w"  validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td> 
					</tr>
                </table>
                <input type="hidden" include="1"  name="curUserId" value="${userId}">
                <input type="hidden" include="1"  name="curUserName" value="${userName}">
                <input type="hidden" include="1" id="defId" name="defId" value="10000011160170">
                <input type="hidden" include="1" id="formKey" name="formKey" value="overTime">
                <input type="hidden" include="1" name="actDefId" value="overTime:2:10000011160174">
                <input type="hidden" include="1" name="formData" id="formData" />
            </form>
        </div>
	
	<style>
		.mask{
			display: none;
			position: fixed;
			left: 0;
			top: 0;
			right: 0;
			bottom: 0;
			background: #ccc;
		}
		.mask.view{
			display: block;
		}
	</style>
	<div id="mask" class="mask"></div>

<script type="text/javascript">
    $(function(){
        var val = '${now}';
        val = val.replace(/-/g,"/");
        var date=new Date(val);
        date=date.format("yyyy-MM-dd");
        $("#appliDate").val(date);


        // 触发隐藏的选择器组件
        $(".oa-trigger-hidden-button").on("click", function(){
            $(this).parent("td").find("a.oa-hidden-trigger").click();
        });
    });

    function countOffdays(){
    	var start = $("#startDate").val();
    	var end = $("#endDate").val();
    	var startPoint = $("#startPoint").val();
    	var endPoint = $("#endPoint").val();
    	if((!start)||(!end)){
    		return false;
    	}
    	if((new Date(start)).getTime()-(new Date(end)).getTime()>0){
    		$.ligerDialog.warn('开始时间不能大于结束时间','提示信息');
    		return false;
    	}
    	$.post("/makshi/operation/overTimeAndAdjust/getDays.ht?type=1",{start:start,end:end,startPoint:startPoint,endPoint:endPoint},function(result){
    		if(result<0){
    			$.ligerDialog.warn('开始时间不能大于结束时间','提示信息');
    			$("#startDate").val("");
    			return false;
    		}
    		$("#offdays").val(result);
    		$("#showdays").html(result);
    	});
    }
    
    function checkSelectDay(start,end,startPoint,endPoint){
        $.ajax({
            type:"get",
            url:"/makshi/operation/overTimeAndAdjust/overTimeInfoList.ht?",
            data:{start:start,end:end,startPoint:startPoint,endPoint:endPoint},
            success:function(data){
            	if(1==data){
            		 $.ligerDialog.warn('请填写开始时间或结束时间','提示信息');
            		 return false;
            	}
            	
                if(2==data){
                    $.ligerDialog.warn('该时间段您已存在值班申请','提示信息');
                    return false;
                }
                if(3==data){
                    $.ligerDialog.warn('该时间段您已存在调休申请','提示信息');
                    return false;
                }
                if(0==data){
                startWorkFlow();
                }else{
                	 $.ligerDialog.warn('服务器繁忙,请刷新重试','提示信息');
                }
            }
        });
    }
    $("a.run").click(function(){
        var start = $("input[name='m:overTime:overTime_start']").val();
        var end  = $("input[name='m:overTime:overTime_end']").val();
        var startPoint  = $("select[name='m:overTime:overTime_start_point']").val();
        var endPoint  = $("select[name='m:overTime:overTime_end_point']").val();
        checkSelectDay(start,end,startPoint,endPoint);
    });
    function startWorkFlow(){
        isStartFlow=true;
        
        var  action="${ctx}/platform/bpm/task/startFlow.ht";
        $.ligerDialog.confirm("确认启动流程吗?","提示",function(rtn){
            if(rtn){
            	
                submitForm(action,"a.run");
                
            }else{
                return false;
            }
        });
    }
    function submitForm(action,button){
    	//$("#mask").addClass("view");
		//百度编辑器数据处理
		var ignoreRequired=false;
		if(button=="a.save"){
		    ignoreRequired=true;
		}
		var urlForm=$('#urlForm');
		urlForm.attr("action",action);
		data=CustomForm.getData();
		//设置表单数据
		$("#formData").val(data);
		FormSubmitUtil.submitFormAjax(urlForm,showResponse);
}
function showResponse(responseText){
    var button= "a.run";
    var operatorType=1;
   // $("#mask").removeClass("view");
    var obj=new com.hotent.form.ResultMessage(responseText);
    if(obj.isSuccess()){
        var msg="启动流程成功!";
        $.ligerDialog.success(msg,'提示信息',function(){
            if(window.opener){
                window.opener.location.href = window.opener.location.href;
                window.close();
            }else{
                window.close();
            }
        });

    }
    else{
        var msg="启动流程失败!";
        $.ligerDialog.err('提示信息',msg,obj.getMessage());
        $(button).removeClass("disabled");
    }
}
function showBpmImageDlg(){
    BpmImageDialog({actDefId:$("input[name='actDefId']").val()});
}
</script>
</body>
</html>