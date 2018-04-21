<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<%@include file="/commons/include/form.jsp"%>
<title>编辑日程</title>
<f:link href="form.css" ></f:link>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysPlanScript.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=sysPlan"></script>

<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/HtmlUploadDialog.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js" ></script>

<script type="text/javascript">

    $(function(){
        //初始化选择器
        initData();
        
        //初始化返回提示
        $('#sysPlanEdit').ajaxForm({success:showResponse });
        
        //初始化保存按键
        initSubmit();
        
        //初始化点击人员事件
        openDetailEvent();

        AttachMent.init("w");
    });
    
    
    //提交后返回内容
    function showResponse(r){
        var type = $("input[name='type']").val();
        var id = $("input[name='id']").val();
        var data = eval("("+r+")");
        var currentViweDate = $("input[name='currentViweDate']").val();
        if(data.result){
            $.ligerDialog.success(data.message,"消息提示",function(){
                var url = __ctx + '/platform/system/sysPlan/submit.ht?currentViweDate='+currentViweDate;
                if(id!=''&&type=='charge'){
                    url = __ctx + '/platform/system/sysPlan/exchange.ht?id='+id+'&currentViweDate='+currentViweDate;
                }else if(type=='charge'){
                    url = __ctx + '/platform/system/sysPlan/charge.ht?currentViweDate='+currentViweDate;
                }

                 // 由于当前是显示在弹窗内，需要关闭弹窗
                 closeLayer();
                // window.location.href = url;
            });
        }
        else{
            $.ligerDialog.warn(data.message,"消息提示");
        }
    }
    
    // 关闭父页面的layer
    function closeLayer(){
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index);

        return false;
    }
    
</script>
</head>
<body>
    <div class="oa-pd20">
    	<div class="oa-pd21">
	        <a class="oa-button oa-button--primary oa-button--blue save">保存</a>
	        <c:choose>
	            <c:when test="${type eq 'charge'}">
	                <a class="oa-button oa-button--primary oa-button--blue back" href="${ctx}/platform/system/sysPlan/charge.ht?currentViweDate=${currentViweDate}">返回</a>
	            </c:when>
	            <c:when test="${type eq 'myPlan'}">
	                <a class="oa-button oa-button--primary oa-button--blue back" href="${ctx}/platform/system/sysPlan/myList.ht">返回</a>
	            </c:when>
	            <c:otherwise>
	                <a class="oa-button oa-button--primary oa-button--blue back" href="${ctx}/platform/system/sysPlan/submit.ht?currentViweDate=${currentViweDate}">返回</a>
	            </c:otherwise>
	        </c:choose>
    </div>
     </div>

    <div class="oa-pd20">
        
        <form id="sysPlanEdit"  method="post" action="/makshi/msgscheduler/sysPlan/save.ht">
            <input type="hidden" name="id" value="${sysPlan.id}"/>
            <input type="hidden" name="currentViweDate" value="${currentViweDate}"/>
            <input type="hidden" name="type" value="${type}"/>

            <table class="oa-table--default" cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <th>任务名称:</th>
                    <td colspan="3">
                        <div class="oa-input-wrap">
                        	<input type="text" name="taskName" class="oa-input" value="${sysPlan.taskName}" validate="{'required':true}" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>内容:</th>
                    <td colspan="3">
                        <div class="oa-textarea-wrap">
                            <textarea name="description" class="oa-textarea">${sysPlan.description}</textarea>
                        </div>
                    </td>
                </tr>
                <tr>
                   <%--  <th>提交人:</th>
                    <td colspan="3">
                        <input type="hidden" name="submitId" value="${sysPlan.submitId}"  />
                        <input type="hidden" name="submitor" value="${sysPlan.submitor}"  />
                        <span id='submitDiv'></span>
                    </td> --%>
                </tr>
                <tr>
                    <th>负责人:</th>
                    <td colspan="3">
                        <input type="hidden" name="chargeId" value="${sysPlan.chargeId}" />
                        <input type="hidden" name="charge" value="${sysPlan.charge}"  />
                        <span id='chargeDiv'></span>
                        <button type="button" class="oa-button-label" onclick="chooseUser(this,'chargeDiv','chargeId','charge','yes');">选择</button>
                        <button type="button" class="oa-button-label" onclick="resetSelect('chargeDiv','chargeId','charge');">重置</button>
                    </td>
                </tr>
                <tr>
                    <th>参与人:</th>
                    <td colspan="3">
                        <input type="hidden" name="participantIds" value="${participantIds}"  />
                        <input type="hidden" name="participants" value="${participants}"  />
                        <span id='participantDiv'></span>
                        <button type="button" class="oa-button-label" onclick="chooseUser(this,'participantDiv','participantIds','participants','no');" >选择</button>
                        <button type="button" class="oa-button-label" onclick="resetSelect('participantDiv','participantIds','participants');" >重置</button>
                    </td>
                </tr>
                <tr>
                    <th>开始时间:</th>
                    <td colspan="3">
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input id="d4311" type="text" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" name="startTime"  class="oa-input Wdate"  value="<fmt:formatDate value='${sysPlan.startTime}' pattern='yyyy-MM-dd HH:mm:ss'/>" validate="{'required':true}"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>结束时间:</th>
                    <td colspan="3">
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input id="d4312" type="text" onclick="WdatePicker({minDate:'#F{$dp.$D(\'d4311\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" name="endTime"  class="oa-input Wdate"  value="<fmt:formatDate value='${sysPlan.endTime}' pattern='yyyy-MM-dd HH:mm:ss'/>" validate="{'required':true}"/>
                        </div>
                    </td>
                </tr>
                <c:choose>
                    <c:when test="${type eq 'charge' or type eq 'myPlan'}">
                        <tr>
                            <th width="10%">相关项目:</th>
                            <td>
                                <input type="text" name="projectName"  class="inputText" value="${sysPlan.projectName}" validate="{'required':false}" />
                            </td>
                            <th width="10%">日程进度:</th>
                            <td>
                                <input type="text" name="rate"  class="inputText" value="${sysPlan.rate}" validate="{'range':[0,100],'digits':true}" /> %
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                    <tr>
                        <th>项目名称:</th>
                        <td>
                            <div class="oa-input-wrap">
                                <input type="text" name="projectName" class="oa-input" value="${sysPlan.projectName}" validate="{'required':false}" />
                            </div>
                        </td>
                       <%--  <th>日程进度:</th>
                        <td>
                            <c:choose>
                                <c:when test="${sysPlan.rate==null ||sysPlan.rate==''}">
                                    0%
                                </c:when>
                                <c:otherwise>
                                    ${sysPlan.rate}%
                                </c:otherwise>
                            </c:choose>
                        </td> --%>
                    </tr>
                    </c:otherwise>
                </c:choose>
                <tr>
                    <th>相关文档: </th>
                    <td colspan="3">
                        <div name="div_attachment_container">
                            <div class="attachement"></div>
                            <textarea style="display: none" controltype="attachment"
                            id="doc" name="doc" lablename="主表附件" validate="{}">${sysPlan.doc}</textarea>
                            <a href="javascript:;" field="doc" class="link selectFile"
                            type="select" onclick="AttachMent.addFile(this);">选择</a>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>自定义提醒时间:</th>
                    <td colspan="3">
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input type="text" name="noteTime"  class="oa-input datetime"  />
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    
</body>
</html>

