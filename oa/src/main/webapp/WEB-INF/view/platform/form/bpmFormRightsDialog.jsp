<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>表单授权</title>
<%@include file="/commons/include/form.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/util/easyTemplate.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/Permission.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/ScriptDialog.js" ></script>


<script type="text/javascript">
	/*KILLDIALOG*/
	var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)

	var nodeId="${nodeId}";
	var actDefId="${actDefId}";
	var formKey="${formKey}";
	var tableId="${tableId}";
	var isNodeRights=${isNodeRights};
	var parentActDefId = "${parentActDefId}";
	

	var __Permission__;
	
	$(function() {
	
		//权限处理
		__Permission__=new Permission();
		//设置节点权限。
		if(isNodeRights){
			__Permission__.loadByNode(actDefId,nodeId,formKey,parentActDefId);
			$("#nodeId").change(changeNode);
		}else if(actDefId){
			__Permission__.loadByActDefId(actDefId,formKey,parentActDefId);
		}else{
			//根据表单key获取表单权限。
			__Permission__.loadPermission(formKey);
		}
		$("#dataFormSave").click(savePermission);
		$("body").delegate("input:radio[name='rdoPermission']","click", setPermision);
		$("#initRights").click(function(){
			$.ligerDialog.confirm("是否确定重置权限设置?",function (r){
					if(r){
						var url = __ctx+"/platform/form/bpmFormRights/initRights.ht";
						$.post(url,{formKey:formKey,actDefId:actDefId,nodeId:nodeId,parentActDefId:parentActDefId},function(d){
							if(d.result)
								$.ligerDialog.success(d.message,function(){
									//location.reload();
									dialog.close();
								});
							else
								$.ligerDialog.warn(d.message);
						});
					}
				});
		});
	});
	
	
	
	
	//批量设置权限。
	function setPermision(){
		
		var val=$(this).val();
		var obj=$(this).closest("[name=tableContainer]");
		
		$("span[name='r_span'],span[name='w_span'],span[name='b_span']",obj).hide();
		switch(val){
			//hidden
			case "1":
				$(".r_select,.w_select,.b_select",obj).val("none");
				$("[name=rpost]",obj).removeAttr("checked");
				break;
			//readonly
			case "2":
				$(".r_select",obj).val("everyone");
				$(".w_select,.b_select",obj).val("none");
				$('input:checkbox[value="y"]', obj).each(function(){
					$(this).siblings('input').removeAttr('checked');
					$(this).siblings('input').attr('show', 'false');
				});
				break;
			//edit
			case "3":
				$(".r_select,.w_select",obj).val("everyone");
				$(".b_select",obj).val("none");
				$("[name=rpost]",obj).removeAttr("checked");
				break;
			
			case "4":
				$(".r_select,.w_select,.b_select",obj).val("everyone");
				$("[name=rpost]",obj).removeAttr("checked");
				break;
		}
	}
	
	//重新加载任务节点的权限
	function changeNode(){
		var obj=$("#nodeId");
		nodeId=obj.val();
		__Permission__.loadByNode(actDefId,nodeId,formKey,parentActDefId,platform);
	};
	
	//保存权限数据。
	function savePermission(){
		//设置所有的权限。
		__Permission__.setAllPermission();
		
		var json=__Permission__.getPermissionJson();
		var params={};
		params.formKey=formKey;
		params.permission=json;
		params.actDefId=actDefId;
		if(parentActDefId!=""){
			params.parentActDefId = parentActDefId;
		}
		//流程节点权限。
		if(isNodeRights){
			//params.actDefId=actDefId;
			params.nodeId=nodeId;
		}
		
		$.post("savePermission.ht",params,showResponse);
	}
		
	function showResponse(data){
		var obj=new com.hotent.form.ResultMessage(data);
		if(obj.isSuccess()){//成功
			$.ligerDialog.confirm('操作成功,继续操作吗?','提示信息',function(rtn){
				if(!rtn){
					dialog.close();
				}
			});
	    }else{//失败
	    	$.ligerDialog.err('出错信息',"表单授权失败",obj.getMessage());
	    }
	};
	//主表下的只读提交选中事件
	function clickCheckbox(target){
		var isChecked = $(target).is(":checked");
		if(!isChecked) return;
		var obj = $(target).closest('tr');
		$(".r_select",obj).val("everyone");
		$(".w_select,.b_select",obj).val("none");
	};
</script>
</head>
<body >
	<div class="panel-top">
		<div class="tbar-title">
			<span class="tbar-label">表单权限</span>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group">
					<a class="link save" id="dataFormSave" href="javascript:;"><span></span>保存</a>
				</div>
				
				<div class="l-bar-separator"></div>
				<div class="group">
					<a class="link initRights" id="initRights" href="javascript:;"><span></span>重置权限设置</a>
				</div>
				<div class="l-bar-separator"></div>
				<div class="group">
					<a class="link close" href="javascript:dialog.close();"><span></span>关闭</a>
				</div>
			</div>
		</div>
	</div>

	<div  class="panel-body">
			<c:if test="${isNodeRights}">
				<form id="bpmFormDefForm">
					<table cellpadding="0" cellspacing="0" border="0" style=" margin: 4px 0px;"  class="table-detail">
						<tr style="height:25px;">
							<td>流程节点:
								<select id="nodeId" >
									<c:forEach items="${bpmNodeSetList }" var="bpmNodeSet">
										<option value="${bpmNodeSet.nodeId}" <c:if test="${bpmNodeSet.nodeId== nodeId}"> selected="selected" </c:if> >${bpmNodeSet.nodeName}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
					</table>
					
				</form>
				
			</c:if>
			
			<table cellpadding="1" cellspacing="1" class="table-grid" name="tableContainer" >
				<tr>
					<th width="20%">字段</th>
					<th width="20%">只读权限</th>
					<th width="20%">编辑权限</th>
					<th width="20%">必填权限</th>
					<th width="20%">只读提交</th>
				</tr>
				<tr>
					<td colspan="5">
						<input type="radio" value="1" name="rdoPermission" id="fieldHidden" ><label for="fieldHidden">隐藏</label>
						<input type="radio" value="2" name="rdoPermission" id="fieldReadonly"><label for="fieldReadonly">只读</label>
						<input type="radio" value="3" name="rdoPermission" id="fieldEdit" ><label for="fieldEdit">编辑</label>
						<input type="radio" value="4" name="rdoPermission" id="fieldRequired" ><label for="fieldRequired">必填</label>			
					</td>
				</tr>
				<tbody id="fieldPermission"></tbody>
			</table>
		
			<table  cellpadding="1" cellspacing="1" class="table-grid" style="margin-top: 5px;"  >
				<tbody id="tablePermission"></tbody>
			</table>
		
			<table  cellpadding="1" cellspacing="1" class="table-grid" style="margin-top: 5px;" name="tableContainer">
				<tr>
					<th width="20%">意见</th>
					<th width="25%">只读权限</th>
					<th width="25%">编辑权限</th>
					<th width="30%">必填权限</th>
				</tr>
				
				<tbody id="opinionPermission"></tbody>
			</table>
	</div>
	
	
	<textarea class="hidden" id="subtableTemplate">
	<tr name="tableContainer">
		<td  >
			<table   cellpadding="1" cellspacing="1" class="table-grid" style="margin-top: 5px;" name="subTablePermission" value="\${data.title}">
				<tr id="thead_\${data.title}">
					<th colspan="4" style="text-align:left;">
						\${data.memo} &nbsp; &nbsp; 
						&nbsp; <input type="checkbox" value="y" name="checkbox_\${data.title}"  onclick="changeCheckbox(this)" >
						<label for="SubtableShow">子表隐藏<a href="javascript:;" class="tipinfo"><span>子表将直接隐藏</span></a></label>
						&nbsp; <input type="checkbox" value="b" name="checkbox_\${data.title}"  onclick="changeCheckbox(this)" >
						<label for="SubtableShow">子表必填<a href="javascript:;" class="tipinfo"><span>子表要求至少有一行数据</span></a></label>
						&nbsp; <input type="checkbox" value="addRow" name="checkbox_\${data.title}"  onclick="changeCheckbox(this)" >
						<label for="SubtableShow">允许添加</label>
						&nbsp; <input type="checkbox" value="del" name="checkbox_\${data.title}"  onclick="changeCheckbox(this)" >
						<label for="SubtableShow">允许删除</label>
					</th>
				</tr>
				<tr>
					<td colspan="4">
						<input type="radio" value="1" name="rdoPermission" id="tableHidden" ><label for="tableHidden">隐藏</label>
						<input type="radio" value="2" name="rdoPermission" id="tableReadonly"><label for="tableReadonly">只读</label>
						<input type="radio" value="3" name="rdoPermission" id="tableEdit" ><label for="tableEdit">编辑</label>
						<input type="radio" value="4" name="rdoPermission" id="tableRequired" ><label for="tableRequired">必填</label>					
					</td>
				</tr>
				<tr>
						<th width="22%">子表字段</th>
						<th width="26%">只读权限</th>
						<th width="26%">编辑权限</th>
						<th width="26%">必填权限</th>
				</tr>
				<tbody id="tableId_\${data.title}">
					\${data.additionalHtml}
				</tbody>
			
			
			</table>
		</td>
	</tr>
	</textarea>	
	<textarea class="hidden" id="fieldPermissionTemplate">
	<tr type="\${data.type}" controlType="\${data.permission.controlType}"  tableName="\${data.permission.tableName}" >
		<td>
		<span class="mySpan">\${data.permission.memo}</span>
		<#if (data.permission.controlType == 12)>
		<a class="officeMenu" href="javascript:;" onclick="selectOfficeMenu(this)" menuRight="\${data.permission.menuRight}">文档菜单设置</a>
		</#if>
		</td>
		\${data.readHtml}
		\${data.writeHtml}
		\${data.requiredHtml}
		<#if (data.type == 'field')>
		<td><input name="rpost" type="checkbox" onclick="clickCheckbox(this);" <#if (data.permission.rpost != null && data.permission.rpost!='')>checked="checked"</#if> /></td>
		</#if>
	</tr>
	</textarea>
	<script class="hidden" id="tdTemplate">
	<td <#if (data.type == 'subtable')> width="25%"</#if>>
		<select class="\${data.right}_select"  permissonType="\${data.permission.#rightName.type}" name="\${data.permission.title}">
			<option value="user">用户</option>
			<option value="role">角色</option>
			<option value="org">组织</option>
			<option value="orgMgr">组织负责人</option>
			<option value="pos">岗位</option>
			<option value="script">脚本</option>
			<option value="everyone">所有人</option>
			<option value="none">无</option>
		</select>
		<span name="\${data.right}_span">
			<input type="hidden" value="\${data.permission.#rightName.id}"/>
			<textarea  >\${data.permission.#rightName.fullname}</textarea>
			<a href="javascript:;" class="link-get" mode="#rightName" ><span class="link-btn">选择</span></a>
		</span>
	</td>
	</script>
</body>
</html>

