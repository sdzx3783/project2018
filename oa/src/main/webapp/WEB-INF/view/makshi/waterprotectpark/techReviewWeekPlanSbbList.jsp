<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>技术评审周计划管理</title>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
</head>
<body class="oa-mw-page">

	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <h2>技术评审周计划管理列表</h2>
	</div>

	<div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl20">
		<a class="oa-button oa-button--primary oa-button--blue" href="edit.ht">添加</a>
		<a class="oa-button oa-button--primary oa-button--blue update" id="btnUpd" action="edit.ht">修改</a>
		<a class="oa-button oa-button--primary oa-button--blue del"  action="del.ht">删除</a>
	</div>

	<div class="oa-pdb20 oa-mgh20">
	    <div id="oa_list_content" class="oa-table-scroll-wrap">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="techReviewWeekPlanSbbList" id="techReviewWeekPlanSbbItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column title="${checkAll}" media="html">
			  		<input type="checkbox" class="pk" name="id" value="${techReviewWeekPlanSbbItem.id}">
				</display:column>
				<display:column title="时间">
					<fmt:formatDate value="${techReviewWeekPlanSbbItem.start_time}" pattern="yyyy-MM-dd" type="date" dateStyle="long" />
					至
					<fmt:formatDate value="${techReviewWeekPlanSbbItem.end_time}" pattern="yyyy-MM-dd" type="date" dateStyle="long" />
				</display:column>
				<display:column  title="计划安排人">
					${techReviewWeekPlanSbbItem.plan_arranger}
				</display:column>
				<display:column  title="执行人">
					${techReviewWeekPlanSbbItem.plan_executor}
				</display:column>
				<display:column title="管理" media="html">
					<a href="edit.ht?id=${techReviewWeekPlanSbbItem.id}" class="oa-button-label edit">编辑</a>
					<a href="del.ht?id=${techReviewWeekPlanSbbItem.id}" class="oa-button-label oa-button-label-remove">删除</a>
					<c:if test="${(not empty  techReviewWeekPlanSbbItem.refRunIds) and (!techReviewWeekPlanSbbItem.isInExamine)}">
						<a href="javascript:;" id="${techReviewWeekPlanSbbItem.id}" class="oa-button-label oa-button-label-submit">再次提交</a>
					</c:if>
					<c:if test="${empty  techReviewWeekPlanSbbItem.refRunIds}">
						<a href="javascript:;" id="${techReviewWeekPlanSbbItem.id}" class="oa-button-label oa-button-label-submit">提交</a>
					</c:if>
					
					<c:if test="${not empty  techReviewWeekPlanSbbItem.refRunIds}"><a href="processRunHistory.ht?id=${techReviewWeekPlanSbbItem.id}" class="oa-button-label ">审批历史</a></c:if>
				</display:column>
			</display:table>
	    </div>

	    <hotent:paging tableId="techReviewWeekPlanSbbItem"/>

	</div>

<script type="text/javascript">
	function startFlow(id){
		var url="/makshi/waterprotectpark/techReviewWeekPlanSbb/getJson.ht"
		var startFormUrl="/platform/bpm/task/startFlow.ht";
		var jsonParams={'curUserId':${curUserId},'curUserName':'${curUserName}','actDefId':'zjhlc:1:10000015860146','defId':10000015860147,'businessKey':'','startNode':'','formKey':'jspszjh-fqsq'};
		$.post(url,{'id':id},function(data){
			var formData={"main": {
		        "fields": {
		            "start_time": data.start_time,
		            "end_time": data.end_time,
		            "plan_arrangerID": data.plan_arrangerID,
		            "plan_arranger": data.plan_arranger,
		            "plan_executorID": data.plan_executorID,
		            "plan_executor": data.plan_executor,
		            "week": data.week,
		            "applicantTime": data.applicantTime,
		            "week_plan_RefId": data.id
		        }
		    },
		    "sub": [
		        {
		            "tableName": "week_plan_sbb",
		            "fields":data.weekPlanSbbList
		        }
		    ],
		    "opinion": []}
			jsonParams.formData=JSON.stringify(formData);
			$.post(startFormUrl,jsonParams,function(responseText){
				var obj = new com.hotent.form.ResultMessage(responseText);
				if (obj.isSuccess()) {
					$.ligerDialog.success('<p><font color="green">'+obj.getMessage()+'</font></p>');
					setTimeout("window.location.reload(true);",2000);
				}else {
					$.ligerDialog.error(obj.getMessage(),"提示信息");
				}
			});
		});
	}
	
	$(function(){

        //处理删除当前行
        $(".oa-button-label-remove").click(function(){
            if($(this).hasClass('disabled')) return false;
            var ele = this;
            $.ligerDialog.confirm('确认删除吗？','提示信息',function(rtn) {
                if(rtn) {
                    if(ele.click) {
                        $(ele).unbind('click');
                        ele.click();
                    } else {
                        location.href=ele.href;
                    }
                }
            });
            
            return false;
        });

        //处理提交当前行
        $(".oa-button-label-submit").click(function(){
            if($(this).hasClass('disabled')) return false;
            
            var ele = this;
            $.ligerDialog.confirm('确认'+$(this).text() +'吗？','提示信息',function(rtn) {
                if(rtn) {
                        $(ele).unbind('click');
                        startFlow(ele.id);
                    }
                }
            );
            return false;
        });

//更新
function handlerUpd(){
    $("#oa_list_operation a.update").click(function() {
        if($(this).hasClass('disabled')) return false;
        
        var action=$(this).attr("action");
        var aryId = $("input[type='checkbox'][disabled!='disabled'][class='pk']:checked");
        var len=aryId.length;
        if(len==0){
            $.ligerDialog.warn("还没有选择,请选择一项进行编辑!",'提示信息');
            return;
        }
        else if(len>1){
            $.ligerDialog.warn("已经选择了多项,请选择一项进行编辑!",'提示信息');
            return;
        }
        var name=aryId.attr("name");
        var value=aryId.val();
        var form=new com.hotent.form.Form();
        form.creatForm("form", action);
        form.addFormEl(name, value);
        form.submit();
        
    });
}


function handlerDelSelect()
{
    //单击删除超链接的事件处理
    $("#oa_list_operation a.del").click(function()
    {   
        if($(this).hasClass('disabled')) return false;
        
        var action=$(this).attr("action");
        var $aryId = $("input[type='checkbox'][disabled!='disabled'][class='pk']:checked");
        
        if($aryId.length == 0){
            $.ligerDialog.warn("请选择记录！");
            return false;
        }
        
        //提交到后台服务器进行日志删除批处理的日志编号字符串
        var delId="";
        var keyName="";
        var len=$aryId.length;
        $aryId.each(function(i){
            var obj=$(this);
            if(i<len-1){
                delId+=obj.val() +",";
            }
            else{
                keyName=obj.attr("name");
                delId+=obj.val();
            }
        });
        var url=action +"?" +keyName +"=" +delId ;
        
        $.ligerDialog.confirm('确认删除吗？','提示信息',function(rtn) {
            if(rtn) {
                var form=new com.hotent.form.Form();
                form.creatForm("form", action);
                form.addFormEl(keyName, delId);
                form.submit();
            }
        });
        return false;
    
        
    });
}

handlerUpd();
handlerDelSelect();
    });
</script>
</body>
</html>


