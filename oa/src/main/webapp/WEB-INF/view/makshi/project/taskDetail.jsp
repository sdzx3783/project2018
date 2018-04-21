<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>任务详情</title>
<%@include file="/codegen/include/customForm.jsp"%>
<%@include file="/commons/include/ueditor.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerTab.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
<script type="text/javascript">
        $(function() {
            var options={};
            if(showResponse){
                options.success=showResponse;
            }
            var frm=$('#projectForm').form();
            $("a.save").click(function() {
                frm.ajaxForm(options);
                if(frm.valid()){
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
        var url = "";
        
        function showResponse(responseText) {
            var obj = new com.hotent.form.ResultMessage(responseText);
            if (obj.isSuccess()) {
               /*  $.ligerDialog.confirm(obj.getMessage()+",是否继续操作","提示信息", function(rtn) {
                    if(rtn){
                        window.location.href = window.location.href;
                        $("#back").attr("href",url); 
                        console.log(111);
                    }else{
                    	
                    }
                }); */
            	window.location.href="${returnUrl}";
            } else {
                $.ligerDialog.error(obj.getMessage(),"提示信息");
            }
            
        }
         
         function end(taskId) {
                if(taskId=='' || taskId ==null){
                    return ;
                }
                $.post("/makshi/project/project/endTask.ht", {
                    'id' : taskId,
                }, function(data) {
                	
                	showResponse(data);
                });

            }
         
         
         function restart(taskId) {
             if(taskId=='' || taskId ==null){
                 return ;
             }
             $.post("/makshi/project/project/restartTask.ht", {
                 'id' : taskId,
             }, function(data) {
             	
             	showResponse(data);
             });

         }

         
         function addField(taskId){
            if(taskId=='' || taskId==null) return;
            $.post("${ctx}/makshi/project/project/checkPre.ht",{
                    id:taskId,
                    },function(responseText){
                    var obj = new com.hotent.form.ResultMessage(responseText);
                    if (obj.isSuccess()) {
                        window.open('toTask.ht?taskId='+taskId, 'addaddField','height=500, width=900, top=200, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=n o, status=no');                             
                    } else {
                        $.ligerDialog.error(obj.getMessage(),"提示信息");
                    }
            });
        }
         
         
         function editField(taskId,ctime){
             if(taskId=='' ||taskId==null ||ctime=='' || ctime==null) return;
             window.open('toEditField.ht?taskId='+taskId+'&ctime='+ctime, 'editField','height=500, width=900, top=200, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=n o, status=no');
         }
         
         
         function addFile(taskId){
            if(taskId=='' || taskId==null) return;
            var files = $("[name='files']").val();
            if(files.length < 2){
            	alert("请选择资料上传后，再试");
            	return;
           	}
            $.post("${ctx}/makshi/project/project/fileSave.ht",{
                    id:taskId,
                    files:files
                    },function(responseText){
                    var obj = new com.hotent.form.ResultMessage(responseText);
                    if (obj.isSuccess()) {
                        //$.ligerDialog.success("上传成功！");
                        location.reload();
                            
                    } else {
                        $.ligerDialog.error(obj.getMessage(),"提示信息");
                    }
            });
         }
         
         
         function startFlow(taskId,defId){
                if(taskId=='' || taskId==null) return;
                $.post("${ctx}/makshi/project/project/checkPre.ht",{
                        id:taskId,
                        },function(responseText){
                        var obj = new com.hotent.form.ResultMessage(responseText);
                        if (obj.isSuccess()) {
                            window.open("/platform/bpm/task/startFlowForm.ht?defId="+defId+"&projectTaskId="+taskId);                               
                        } else {
                            $.ligerDialog.error(obj.getMessage(),"提示信息");
                        }
                });
             }
         
         function delFile(taskId,fileId){
            if(taskId=='' || taskId==null ||fileId=='' ||fileId==null) return;
            $.post("${ctx}/makshi/project/project/fileDel.ht",{
                    'taskId':taskId,
                    'fileId':fileId
                    },function(responseText){
                    var obj = new com.hotent.form.ResultMessage(responseText);
                    if (obj.isSuccess()) {
                        location.reload();
                            
                    } else {
                        $.ligerDialog.error(obj.getMessage(),"提示信息");
                    }
            });
         }
         
         function delField(taskId,ctime){
            if(taskId=='' ||taskId==null ||ctime=='' || ctime==null) return;
            $.ligerDialog.confirm('确认删除吗？','提示信息',function(flag) {
                if(flag){
                     $.post("${ctx}/makshi/project/project/fieldDel.ht",{
                             'taskId':taskId,
                             'ctime':ctime
                             },function(responseText){
                             var obj = new com.hotent.form.ResultMessage(responseText);
                             if (obj.isSuccess()) {
                                 location.reload();
                             } else {
                                 $.ligerDialog.error(obj.getMessage(),"提示信息");
                             }
                     });
                }
            });


          }
    </script>
	<style>
	.link.selectFile{
		display: none;
	}
    dt{
        float: left;
    }
    dd{
        margin-left: 100px;
    }
</style>
</head>
<body class="oa-mw-page">
        <div class="oa-project-title">
            <h2>任务详情</h2>
        </div>

        <div class="oa-mg20">
            <a id="back" class="oa-button oa-button--primary oa-button--blue" href="${returnUrl}">返回</a>
            <c:if test="${task.status!=1 && isCharge}">
                <button class="oa-button oa-button--primary oa-button--blue" onClick="end(${task.id })">完成任务</button>
            </c:if>
            <c:if test="${task.status==1 && isCharge}">
                <button class="oa-button oa-button--primary oa-button--blue" onClick="restart(${task.id })">重启任务</button>
            </c:if>
        </div>
        
        <div class="oa-mg20">
            <input type="hidden" id="proId" value="${proId }"/>
            <table class="oa-table--form">
                <tr>
                    <td>
                        <dl>
                            <dt>任务名称：</dt>
                            <dd>${task.taskname}</dd>
                        </dl>
                    </td>
                    <td>
                        <dl>
                            <dt>负责人：</dt>
                            <dd>${task.charge}</dd>
                        </dl>
                    </td>
                </tr>
                <tr>
                    <td>
                        <dl>
                            <dt>截止时间：</dt>
                            <dd><fmt:formatDate value='${task.endtime }' pattern='yyyy-MM-dd' /></dd>
                        </dl>
                    </td>
                    <td>
                    </td>
                </tr>
            </table>

            <!--表单填写类  -->
            <c:if test="${task.tasktype==1 }">
                <c:if test="${task.status!=1 && isCharge}">
                	<div class="oa-mgv10">
                    	<button class="oa-button oa-button--primary oa-button--blue" onclick='addField(${task.id})'>填写表单</button>
                	</div>
                </c:if>
                <table class="oa-table--default">
                    <thead>
                        <tr>
                            <th>序号</th>
                            <!-- <th>字段名称</th>
                            <th>字段值</th> -->
                            <th>填写人</th>
                            <th>填写时间</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                    	<c:forEach items="${fieldList}" var="field" varStatus="no">
                            <tr>
                                <td>${ no.index + 1}</td>
                               <%--  <td>${field.fieldName }</td>
                                <td>
                                    <c:if test="${field.fieldType=='attach' }">
                                        <input name="fieldvalue"  ctltype="attachment"  isdirectupload="0" right="r" value='${field.fieldValue }' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
                                    </c:if>
                                    <c:if test="${field.fieldType!='attach' }">
                                        ${field.fieldValue }
                                    </c:if>
                                </td> --%>
                                <td>${field.cuser }</td>
                                <td><fmt:formatDate value='${field.ctime }' pattern='yyyy-MM-dd' /></td>
                                 <td>
                                 	<a class="oa-button-label" href="fieldDetail.ht?id=${task.id}&ctime=<fmt:formatDate value='${field.ctime }' pattern='yyyy-MM-dd HH:mm:ss' />" >详情</a>
                                 	<c:if test="${task.status!=1 && isCharge}">
	                                    <button type="button" class="oa-button-label" onclick='editField(${task.id},"<fmt:formatDate value='${field.ctime }' pattern='yyyy-MM-dd HH:mm:ss' />")'>编辑</button>
	                                    <button type="button" class="oa-button-label" onClick='delField(${task.id},"<fmt:formatDate value='${field.ctime }' pattern='yyyy-MM-dd HH:mm:ss' />")'>删除</button>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        <%-- <c:forEach items="${task.fieldArr}" var="js" varStatus="no">
                            <tr>
                                <td>${ no.index + 1}</td>
                                <td>${js.fieldname }</td>
                                <td>
                                    <c:if test="${js.fieldtype=='attach' }">
                                        <input name="fieldvalue"  ctltype="attachment"  isdirectupload="0" right="r" value='${js.fieldvalue }' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
                                    </c:if>
                                    <c:if test="${js.fieldtype!='attach' }">
                                        ${js.fieldvalue }
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach> --%>
                    </tbody>
                </table>
            </c:if>


            <!--资料上传类 -->
            <c:if test="${task.tasktype==2 }">
                <c:if test="${task.status!=1 && isCharge }">
                	<div class="oa-mgv10">
                    	<button class="oa-button oa-button--primary oa-button--blue" onclick='addFile(${task.id })'>保存</button>
                    	<button class="oa-button oa-button--primary oa-button--blue" id="file_upload">选择资料上传</button>
                    	<input ctltype="attachment" name="files" isdirectupload="0" right="w" value="" validatable="false"  validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
                	</div>
                </c:if>
                
                <table class="oa-table--default">
                    <thead>
                        <tr>
                            <th>序号</th>
                            <th>文件名称</th>
                            <th>提交人员</th>
                            <th>提交时间</th>
                            <th>大小</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${files}" var="file" varStatus="no">
                            <tr>
                                <td>${ no.index + 1}</td>
                                <td>${file.fileName }.${file.ext }</td>
                                <td>${file.creator }</td>
                                <td><fmt:formatDate value='${file.createtime }' pattern='yyyy-MM-dd HH:mm:ss' /></td>
                                <td>${file.note }</td>
                                <td>
                                    <a class="oa-button-label" href="/platform/system/sysFile/file_${file.fileId }.ht" target="_blank">查看</a>
                                    <c:if test="${task.status!=1 && isCharge }"><a class="oa-button-label" onClick="delFile(${task.id},${file.fileId })">删除</a></c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <!--流程审批类 -->
            <c:if test="${task.tasktype==3 }">
                <c:if test="${task.status!=1 && isCharge && !(fn:length(runs)>0 and (!task.ismore))}">
                	<div class="oa-mgv10">
                    	<button class="oa-button oa-button--primary oa-button--blue" onClick="startFlow(${task.id},${task.flowid })">提交申请</button>
                    </div>
                </c:if>
                <table class="oa-table--default">
                    <thead>
                        <tr>
                            <th>序号</th>
                            <th>流程名称</th>
                            <th>申请人</th>
                            <th>申请时间</th>
                            <th>审批状态</th>
                            <th>审批历史</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${runs }" var="run" varStatus="runno">
                        <c:if test="${!isHjsyb || run.status==1}">
                            <tr>
                                <td>${runno.index+1 }</td>
                                <td>${run.processName }</td>
                                <td>${run.creator }</td>
                                <td><fmt:formatDate value='${run.createtime }' pattern='yyyy-MM-dd' /></td>
                                <td>${run.taskName }</td>
                                <td><a  class="oa-button-label" href="/platform/bpm/processRun/getForm.ht?tab=1&runId=${run.runId }" target="_blank">查看</a></td>
                            </tr>
                        </c:if>
                        </c:forEach>
                    </tbody>                                        
                </table>
            </c:if>
                
                <h3 class="oa-h3">可查看节点信息</h3>

                <table class="oa-table--default">
                	<thead>
	                    <tr>
	                        <th>任务名称</th>
	                        <th>任务类型</th>
	                        <th>开始时间</th>
	                        <th>结束时间</th>
	                        <th>完成数量</th>
	                        <th>需要审批</th>
	                        <th>操作</th>
	                    </tr>
                    </thead>
	                <tbody>
	                    <c:forEach items="${queryTasks }" var="task">
		                    <tr>
		                        <td>${task.taskname}</td>
		                        <td>
		                            <c:if test="${task.tasktype==1 }">表单填写类</c:if>
		                            <c:if test="${task.tasktype==2 }">资料上传类</c:if>
		                            <c:if test="${task.tasktype==3 }">流程审批类</c:if>
		                        </td>
		                        <td><fmt:formatDate value='${task.starttime }' pattern='yyyy-MM-dd' /></td>
		                        <td><fmt:formatDate value='${task.endtime }' pattern='yyyy-MM-dd' /></td>
		                        <td>${task.endcount}</td>
		                        <td>
		                            <c:if test="${task.isexamine}">是</c:if>
		                            <c:if test="${!task.isexamine}">否</c:if>
		                        </td>
		                        <td><a class="oa-button-label" href="taskDetail.ht?id=${task.id}" target="_blank">详情</a></td>
		                    </tr>
		                </c:forEach>
                    </tbody>     
                </table>  
        </div>

<script>
	$(function(){
		/**
		 * 触发打开上传组件的按钮
		 */
		$("#file_upload").on("click", function(){
			$(".link.selectFile").click();
		});

	});
</script>
</body>
</html>
