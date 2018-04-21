<!DOCTYPE html>
<%@page language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title>添加任务</title>
    <%@include file="/commons/include/customForm.jsp" %>
    <%@include file="/commons/include/ueditor.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysPlanScript.js"></script>
    <style>
        .oa-table th{
            white-space: nowrap;
        }
    </style>
</head>
<body class="oa-mw-page">

    <div class="oa-mg20">
        <button class="oa-button oa-button--primary oa-button--blue" onclick="window.location.href='/makshi/task/task/list.ht?type=${type}'">返回</button>
        <button class="oa-button oa-button--primary oa-button--blue" id="dataFormSave">保存</button>
        <button class="oa-button oa-button--primary oa-button--blue" id="dataFormPub">发布</button>
    </div>

    <div class="oa-mg20">
        <form id="projectForm" method="post" action="save.ht?type=${type}">
            <table class="oa-table">
            	<input type="hidden"  name="id" value="${task.id}"/>
            	<input type="hidden" id="ispub"  name="ispub" value="${task.ispub}"/>
            	<input type="hidden" id="sysplanid"  name="sysplanid" value="${task.sysplanid}"/>
            	<c:if test="${type==1 }">
                <tr>
                    <th>任务标题：</th>
                    <td>
                    	<div class="oa-input-wrap oa-input-wrap--ib"><input class="oa-input" type="text" name="name"  value="${task.name}" validate="{required:true}" /></div>
                    </td>
                </tr>
                <tr>
                    <th>重要：</th>
                    <td>
                    	<div class="oa-select-wrap oa-select-wrap--ib">
                        	<select name="imports" class="oa-select">
                        		<option value="1" <c:if test="${task.stage==1}">selected='selected'</c:if>>普通</option>
                        		<option value="2" <c:if test="${task.stage==2}">selected='selected'</c:if>>重要</option>
                        	</select>
                    	</div>
                    </td>
                </tr>
                <tr>
                    <th>负责人：</th>
                    <td>
                    	<div class="oa-input-wrap oa-input-wrap--ib">
                    	 <input type="text" class="oa-input" name="charger" value="${task.charger }" readonly="readonly" validate="{required:true}"/>
                         <a href="javascript:;" class="link users oa-hidden-trigger"  name="charger" >选择</a>
                         <input type="hidden" name="chargerID" value="${task.chargerID }"/>
                   	    </div>
                     	<button type="button" class="oa-button-label" onclick="chooseUser(this,'chargeDiv','chargerID','charger','yes');">选择用户</button>	
                    </td>
                </tr>
                <tr>
                    <th>参与人：</th>
                    <td>
                    	<div class="oa-input-wrap oa-input-wrap--ib">
                    	 <input type="text" class="oa-input" name="member" value="${task.member }" readonly="readonly" />
                         <a href="javascript:;" class="link users oa-hidden-trigger"  name="member" >选择</a>
                         <input type="hidden" name="memberID" value="${task.memberID }"/>
                   	    </div>
                     	<button type="button" class="oa-trigger-hidden-button oa-button-label">选择用户</button>	
                    </td>
                </tr>
                <tr>
                    <th>开始日期：</th>
					<td>
                      <div class="oa-input-wrap oa-input-wrap--ib">
                          <input type="text" id="startDate"  name="startDate" 
                          onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\');}'})" value="<fmt:formatDate value='${task.startDate }' pattern='yyyy-MM-dd'/>" class="oa-input Wdate" validate="{date:true,required:true}" />
                      </div>
                    </td>
                </tr>
                 <tr>
                    <th>结束日期：</th>
					<td>
                      <div class="oa-input-wrap oa-input-wrap--ib">
                      	  <input type="text" id="endDate"  name="endDate" 
                          onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\');}'})" value="<fmt:formatDate value='${task.endDate }' pattern='yyyy-MM-dd'/>" class="oa-input Wdate" validate="{date:true,required:true}" />
                      </div>
                    </td>
                </tr>
                <tr>
                    <th>详情：</th>
                    <td class="editor-wrap">
					<div id="editor" position="center" style="overflow: hidden; height: 100%;">
						<textarea style="height:300px;" id="content" name="content">${task.content}</textarea>
					</div>
					</td>
                </tr>
                 <tr>
                    <th>附件：</th>
                    <td>
                    	<input  ctltype="attachment" name="file" isdirectupload="0" right="w" value='${task.file}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
                    </td>
                </tr>
                </c:if>
                
                <c:if test="${type==2 }">
                <tr>
                    <th>任务标题：</th>
                    <td>${task.name}</td>
                </tr>
                <tr>
                    <th>重要：</th>
                    <td>
                    	<c:if test="${task.imports==1}">普通</c:if>
                    	<c:if test="${task.imports==2}">重要</c:if>
                    </td>
                </tr>
                <tr>
                    <th>负责人：</th>
                    <td>
                    	${task.charger }
                    </td>
                </tr>
                <tr>
                    <th>参与人：</th>
                    <td>
                    	${task.member }
                    </td>
                </tr>
                <tr>
                    <th>任务分配人：</th>
                    <td>
                    	${task.cuser }
                    </td>
                </tr>
                <tr>
                    <th>时间：</th>
					<td>
                      <fmt:formatDate value='${task.startDate }' pattern='yyyy-MM-dd'/>__<fmt:formatDate value='${task.endDate }' pattern='yyyy-MM-dd'/>
                    </td>
                </tr>
                 <%-- <tr>
                    <th>阶段：</th>
                    <td>
                    	<div class="oa-input-wrap oa-input-wrap--ib"><input class="oa-input" type="radio" name="stage" value="1"  <c:if test="${task.stage==1}">checked='checked'</c:if> />未开始</div>
                    	<div class="oa-input-wrap oa-input-wrap--ib"><input class="oa-input" type="radio" name="stage" value="2"  <c:if test="${task.stage==2}">checked='checked'</c:if> />取消</div>
                    	<div class="oa-input-wrap oa-input-wrap--ib"><input class="oa-input" type="radio" name="stage" value="3"  <c:if test="${task.stage==3}">checked='checked'</c:if>/>进行中</div>
                    	<div class="oa-input-wrap oa-input-wrap--ib"><input class="oa-input" type="radio" name="stage" value="4"  <c:if test="${task.stage==4}">checked='checked'</c:if>/>完成</div>
                    </td>
                </tr> --%>
                <tr>
                    <th>详情：</th>
                    <td class="editor-wrap">
					${task.content}
					</td>
                </tr>
                 <tr>
                    <th>附件：</th>
                    <td>
                    	<input  ctltype="attachment" name="file" isdirectupload="0" right="r" value='${task.file}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
                    </td>
                </tr>
                 
                </c:if>
            </table>
        </form>
    </div>


<script type="text/javascript">
    $(function() {
    	var messageEditor = new baidu.editor.ui.Editor({minFrameHeight:1300,initialFrameWidth: '99%',lang:'zh_cn'});
		messageEditor.render("content");
    	$(".oa-trigger-hidden-button").on("click", function(){
    		$(this).parent("td").find("a.oa-hidden-trigger").click();
    	});

        var options={};
        if(showResponse){
            options.success=showResponse;
        }
        var frm=$('#projectForm').form();
        $("#dataFormSave").click(function() {
            frm.ajaxForm(options);
            if(frm.valid()){
            	var ispub =$("#ispub").val();
            	if(ispub==null || ispub ==''){
            		$("#ispub").val(0);
            	}
            	//$("#ispub").val(0);
                //如果有编辑器的情况下
                $("textarea[name^='m:'].myeditor").each(function(num) {
                    var name=$(this).attr("name");
                    var data=getEditorData(editor[num]);
                    $("textarea[name^='"+name+"']").val(data);
                });
                
                if(WebSignPlugin.hasWebSignField){
                    WebSignPlugin.submit();
                }
                if(OfficePlugin.officeObjs.length>0){
                    OfficePlugin.submit(function(){
                        frm.handleFieldName();
                        $('#projectForm').submit();
                    });
                }else{
                    frm.handleFieldName();
                    $('#projectForm').submit();
                }
            }
        });
        
        
        $("#dataFormPub").click(function() {
        	
            frm.ajaxForm(options);
            if(frm.valid()){
            	$("#ispub").val(1);
            	
                //如果有编辑器的情况下
                $("textarea[name^='m:'].myeditor").each(function(num) {
                    var name=$(this).attr("name");
                    var data=getEditorData(editor[num]);
                    $("textarea[name^='"+name+"']").val(data);
                });
                
                if(WebSignPlugin.hasWebSignField){
                    WebSignPlugin.submit();
                }
                if(OfficePlugin.officeObjs.length>0){
                    OfficePlugin.submit(function(){
                        frm.handleFieldName();
                        $('#projectForm').submit();
                    });
                }else{
                    frm.handleFieldName();
                    $('#projectForm').submit();
                }
            }
        });
    });
    
    function showResponse(responseText) {
        var obj = new com.hotent.form.ResultMessage(responseText);
        if (obj.isSuccess()) {
            $.ligerDialog.confirm(obj.getMessage()+",是否继续操作","提示信息", function(rtn) {
                if(rtn){
                    window.location.href = window.location.href;
                }else{
                    window.location.href = "/makshi/task/task/list.ht?type=${type }";
                }
            });
        } else {
            $.ligerDialog.error(obj.getMessage(),"提示信息");
        }
        
    }
    
    
</script>
</body>
</html>
