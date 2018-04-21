<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>配置阶段</title>
	<%@include file="/commons/include/get.jsp"%>
	<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
	<%@include file="/codegen/include/customForm.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#stagelibForm').form();
			$("#dataFormSave").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(1);
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
							$('#stagelibForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#stagelibForm').submit();
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
						window.location.href = "${ctx}/makshi/project/stagelib/list.ht";
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
			
		}
	</script>
	<style>
	.my-jdk .l-dialog-content {
		overflow: hidden;
	}
</style>
</head>
<body class="oa-mw-page my-jdk">

    <div id="oa_list_title" class="oa-mgb20 oa-project-title">
        <h2>配置阶段</h2>
    </div>

	<form id="stagelibForm" method="post" action="update.ht">
		<div id="oa_list_search" class="oa-pdb10 oa-mgh20 oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">阶段名称</div>
                <div class="oa-input-wrap oa-mgl100">
					<input class="oa-input" type="text" name="stagename" validate="{'required':true,'maxlength':150}"  value="${stageLib.stagename}">
                </div>
            </div>
            <div class="oa-fl oa-mgb10 oa-mgh20">
				<button type="button" class="oa-button oa-button--primary oa-button--blue" id="dataFormSave">保存</button>
				<button type="button" class="oa-button oa-button--primary oa-button--blue" onclick="opennew()">新增任务</button>
				<button type="button" class="oa-button oa-button--primary oa-button--blue" onclick="window.location.href='/makshi/project/stagelib/list.ht'">返回</button>
			</div>
				<input type="hidden" id="stageno" name="stageno" value="${stageLib.stageno}"/>
				<input type="hidden" id="saveData" name="saveData"/>
	    </div>

		<div class="oa-pdb20 oa-mgh20">
			<c:set var="cnt" value="${(pageBeantaskItem.currentPage-1)*pageBeantaskItem.pageSize+1 }"/>

			<div id="oa_list_content" class="oa-table-scroll-wrap">
				<display:table name="taskLibs" id="taskItem"
					requestURI="toset.ht" sort="external"
					cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
					<display:column title="序号">
						<c:out value="${cnt}"/>
						<c:set var="cnt" value="${cnt+1}"/>
					</display:column>
					<display:column title="任务名称">
						${taskItem.taskname }
					</display:column>
					<display:column title="任务编号">
						${taskItem.id }
					</display:column>
					<display:column title="任务类型">
						<c:if test="${taskItem.tasktype==1 }">表单填写类</c:if>
						<c:if test="${taskItem.tasktype==2 }">资料上传类</c:if>
						<c:if test="${taskItem.tasktype==3 }">流程审批类</c:if>
					</display:column>
					<display:column title="排序">
						<div class="oa-input-wrap oa-w20">
				            <input class="oa-input oa-text-center" type="text" name='orderMap["${taskItem.id }"]' value="${taskItem.order }" validate="{'required':true,'digits':true,'range':[0,100000]  }" />
				        </div>
					</display:column>
					
					<display:column title="操作" media="html">
						<button type="button" class="oa-button-label" onclick="openview(${taskItem.id })">查看</button>
						<%-- <button type="button" class="oa-button-label" onclick="openedit(${taskItem.id })">编辑</button> --%>
						<a class="oa-button-label oa-button-label-remove" href="delTask.ht?id=${taskItem.id }">删除</a>
					</display:column>
				</display:table>
			</div>
			<hotent:paging tableId="taskItem" />
		</div>
	</form>

<script type="text/javascript">
	//	新增任务
	function opennew(){
		$.ligerDialog.open({ url: 'addtask.ht?stageno=${stageLib.stageno}',title:'新建阶段任务',width:540,height: 400, isResize:true});
        return false;
	}
	// 编辑
	function openedit(id){
		$.ligerDialog.open({ url: 'addtask.ht?stageno=${stageLib.stageno}&id='+id, title:'修改阶段任务',width:540,height: 400, isResize:true});
        return false;
	}
	
	// 查看
	function openview(id){
		$.ligerDialog.open({ url: 'viewtask.ht?stageno=${stageLib.stageno}&id='+id, title:'查看阶段任务',width:540,height: 400, isResize:true});
        return false;
	}
	
	function confirmdel(taskid){
		$.ligerDialog.confirm('确认删除吗？','提示信息',function(rtn) {
			if(rtn) {
				var url="delTask.ht?id="+taskid;
				window.location.href=url;
			}else{
				return false;
			}
		});
	}

	$(function(){
        //处理删除一行
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
    });
</script>
</body>
</html>
