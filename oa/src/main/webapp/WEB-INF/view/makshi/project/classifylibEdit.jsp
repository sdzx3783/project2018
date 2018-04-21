<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑分类库</title>
	<%@include file="/commons/include/get.jsp"%>
	<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<style>
    	.oa-table{
    		font-size: 14px;
    	}
    	.oa-table td{
    		padding: 10px 20px;
    	}
    	.oa-input-wrap{
    		display: inline-block;
    	}

	</style>
</head>
<body>
    <div id="oa_list_title" class="oa-mgb20 oa-project-title">
        <h2>编辑分类库</h2>
    </div>

    <div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl30">
        <button class="oa-button oa-button--primary oa-button--blue" onclick="showFlowChart()">流程图</button>
        <button class="oa-button oa-button--primary oa-button--blue" id="check">检查</button>
        <button class="oa-button oa-button--primary oa-button--blue" id="save">保存</button>
		<c:if test="${1!=classifyLib.isused}">
        	<button class="oa-button oa-button--primary oa-button--blue" onclick="publish(${classifyLib.id})">启用</button>
        </c:if>
        <button class="oa-button oa-button--primary oa-button--blue" onclick="window.location.href='addCate.ht?id=${classifyLib.id}'">返回</button>
    </div>
	
<form id="classifylibForm" method="post" action="save.ht">

    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
		<table class="oa-table">
			<tr>
				<th>分类名称：</th>
				<td>
					<div class="oa-input-wrap">
						<input class="oa-input" type="text" name="name" value="${classifyLib.name }" validate="{'required':true,'maxlength':150}">
					</div>
				</td>
			</tr>
			<tr>
				<th>负责人：</th>
				<td>
					<div class="oa-input-wrap">
						<input name="charger"  type="text" ctltype="selector" class="users oa-input oa-hidden-trigger"  value="${classifyLib.charger}" validate="{'required':true}" readonly="readonly" />
					</div>
					<button class="oa-button-label oa-trigger-hidden-button" type="button">选择用户</button>
				</td>
			</tr>
			<tr>
				<th>状态：</th>
				<td>
					<c:if test="${classifyLib.isused==1 }">已启用</c:if>
					<c:if test="${classifyLib.isused!=1 }">禁用</c:if>
				</td>
			</tr>
			<tr>
				<th>阶段：</th>
				<td>
					<c:if test="${1!=classifyLib.isused}">
						<button class="oa-button-label " type="button" onclick="addstage();">添加</button>
					</c:if>
				</td>
			</tr>
		</table>
	</div>


	<div class="oa-pdb20 oa-mgh20">
		<c:set var="cnt" value="${(pageBeanclassifyStage.currentPage-1)*pageBeanclassifyStage.pageSize+1 }"/>
		<div id="oa_list_content" class="oa-table-scroll-wrap">
			<display:table name="classifyStageList" id="classifyStage"
				requestURI="edit.ht" sort="external"
				cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column title="序号">
					<c:out value="${cnt}"/>
					<c:set var="cnt" value="${cnt+1}"/>
				</display:column>
				<display:column title="阶段名称">
					${classifyStage.stagename }
				</display:column>
				<display:column title="阶段编号">
					${classifyStage.stageno }
				</display:column>
				<display:column title="所属部门">
					${classifyStage.createorg }
				</display:column>
				<display:column title="排序">
					<div class="oa-input-wrap oa-w20">
						<input class="oa-input oa-text-center" <c:if test="${1==classifyLib.isused}">readonly="readonly"</c:if> name='orderMap["${classifyStage.id }"]' value="${classifyStage.order }" validate="{'required':true,'digits':true,'range':[0,100000]}"/>
					</div>
				</display:column>
				<display:column title="操作" media="html">
					<button type="button" class="oa-button-label" onclick="openedit(${classifyStage.id })">修改</button>
					<c:if test="${1!=classifyLib.isused}">
						<a class="oa-button-label oa-button-label-remove" href="delStage.ht?id=${classifyStage.id }&classifyId=${classifyLib.id }">删除</a>
					</c:if>
				</display:column>
			</display:table>
		</div>
		<hotent:paging tableId="classifyStage" />
	</div>
	<input type="hidden" name="id" value="${classifyLib.id }">

</form>


<script type="text/javascript">
	$(function() {

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

    	$(".oa-trigger-hidden-button").on("click", function(){
    		$(this).parent("td").find("a.oa-hidden-trigger").click();
    	});


		var options={};
		if(showResponse){
			options.success=showResponse;
		}
		var frm=$('#classifylibForm').form();
		$("#save").click(function() {
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
						$('#classifylibForm').submit();
					});
				}else{
					frm.handleFieldName();
					$('#classifylibForm').submit();
				}
			}
		});
		
		$("#check").click(function() {
			if(frm.valid()){
				$.post("${ctx}/makshi/project/classifylib/check.ht",{id:'${classifyLib.id}'},function(responseText){
					var obj = new com.hotent.form.ResultMessage(responseText);
					if (obj.isSuccess()) {
						$.ligerDialog.success("检查校验成功！");
							
					} else {
						$.ligerDialog.error(obj.getMessage(),"提示信息");
					}
				});
				
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
					window.location.href = "${ctx}/makshi/project/classifylib/addCate.ht?id=${classifyLib.id}";
				}
			});
		} else {
			$.ligerDialog.error(obj.getMessage(),"提示信息");
		}
		
	}
	
	function addstage(){
		$.ligerDialog.open({ url: '${ctx}/makshi/project/stagelib/selector.ht?classifyid=${classifyLib.id}',title:'添加',width:600,height: 600, isResize:true});
	}
	function openedit(id){
		window.open('editStage.ht?classifystageid='+id, 'newwindow','height=500, width=900, top=200, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=n o, status=no');
	}
	$(function(){
		var _charger='${classifyLib.charger}';
		var _chargerID='${classifyLib.chargerID}';
		if(_charger!=null && _charger.length>0  && _chargerID!=null && _chargerID.length>0){
			$("input[name='chargerID']").eq(0).val(_chargerID);
		}
	})
	
	function confirmdel(stageid){
		$.ligerDialog.confirm('确认删除吗？','提示信息',function(rtn) {
			if(rtn) {
				 
				var url="delStage.ht?id="+stageid;
				window.location.href=url;
			}else{
				return false;
			}
		});
	}
	
	function publish(id){
		$.ligerDialog.confirm('确认启用吗？','提示信息',function(rtn) {
			if(rtn) {
				 
				var url="publish.ht?id="+id;
				$.get(url,function(responseText){
					var obj = new com.hotent.form.ResultMessage(responseText);
					if (obj.isSuccess()) {
						$.ligerDialog.success(obj.getMessage()+",是否继续操作","提示信息", function(rtn) {
							if(rtn){
								window.location.href = window.location.href;
							}else{
								window.location.href = "${ctx}/makshi/project/classifylib/list.ht";
							}
						});
					} else {
						$.ligerDialog.error(obj.getMessage(),"提示信息");
					}
				});
			}else{
				return false;
			}
		});
	}
	
	function showFlowChart(){
		
		$.post("${ctx}/makshi/project/classifylib/check.ht",{id:'${classifyLib.id}'},function(responseText){
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				window.location.href="${ctx}/makshi/project/classifylib/stageFlowchart.ht?id=${classifyLib.id}";
					
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		});
	
	}
</script>
</body>
</html>
