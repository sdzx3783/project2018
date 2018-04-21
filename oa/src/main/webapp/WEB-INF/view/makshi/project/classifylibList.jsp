<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>分类库</title>
<%@include file="/commons/include/get.jsp"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
</head>
<body class="oa-mw-page">

    <div id="oa_list_title" class="oa-mgb20 oa-project-title">
        <h2>分类库列表</h2>
    </div>

    <div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl40">
        <button class="oa-button oa-button--primary oa-button--blue" type="button" id="updateOrder">更新排序</button>
        <button class="oa-button oa-button--primary oa-button--blue" type="button" onclick="return addClassifyLib()">新建分类</button>
    </div>
    
    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">所属部门</div>
                <div class="oa-select-wrap oa-mgl100">
                    <select class="oa-select" name="orgID">
                        <option value="">请选择部门</option>
                        <c:forEach items="${orgs}" var="org" >
                            <option value="${org.orgId }" <c:if test="${param['orgID']==org.orgId}">selected="selected"</c:if>>${org.orgName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">分类名称</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input class="oa-input" type="text" name="Q_name_SL" value="${param['Q_name_SL'] }">
                </div>
            </div>

            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
            </div>
        </form>

    </div>

	<div class="oa-pdb20 oa-mgh20">
        <div id="oa_list_content" class="oa-table-scroll-wrap">
            <form action="updateClassifyLibOrder.ht" id="classifylibForm" method="post">
            	<display:table name="classifyliblist" id="classifylib"
        			requestURI="list.ht" sort="external"
        			cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
        			<display:column title="分类名称">
        				${classifylib.name }
        			</display:column>
        			<display:column title="项目数">
        				${classifylib.pronum }
        			</display:column>
        			<display:column title="管理人">
        				${classifylib.charger }
        			</display:column>
        			<display:column title="所属部门">
        				${classifylib.org }
        			</display:column>
        			<display:column title="阶段数">
        				${classifylib.stagenum }
        			</display:column>
        			<display:column title="任务数">
        				${classifylib.tasknum }
        			</display:column>
        			<display:column title="状态">
        				<c:choose>
        					<c:when test="${classifylib.isused==1 }">启用</c:when>
        					<c:otherwise>禁用</c:otherwise>
        				</c:choose>
        			</display:column>
        			<display:column title="排序">
    						<div class="oa-input-wrap oa-w20">
    							<input class="oa-input oa-text-center"  name='orderMap["${classifylib.id }"]' value="${classifylib.order }" validate="{'required':true,'digits':true,'range':[0,100000]}"/>
    						</div>
    					</display:column>
        			<display:column title="操作" media="html">
        				<c:if test="${classifylib.stagenum==0 }">
                            <a class="oa-button-label" href="javascipt:;" onclick="addClassifyLib(${classifylib.id })">编辑</a>
                        </c:if>
        				<c:if test="${classifylib.isused==1 }">
                            <a class="oa-button-label" onClick="return check(${classifylib.id });">创建项目</a>
                        </c:if>
        				<c:if test="${classifylib.pronum<=0 }">
                            <a href="del.ht?id=${classifylib.id }" class="oa-button-label oa-button-label-remove">删除</a>
                        </c:if>
                        <a href="edit.ht?id=${classifylib.id }" class="oa-button-label">配置</a>
        			</display:column>
        		</display:table>
        		<input type="hidden" name="${tableIdCode}p" value="${pageBeanclassifylib.currentPage }" />
        		<input type="hidden" name="${tableIdCode}z" value="${pageBeanclassifylib.pageSize }" />
        		<input type="hidden" name="orgID" value="${param['orgID']}" />
        		<input type="hidden" name="Q_name_SL" value="${param['Q_name_SL']}" />
    		</form>
        </div>
		
		<hotent:paging tableId="classifylib" />

    </div>

<script type="text/javascript">
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
    function addClassifyLib(id){
        if(id){
            var obj=$.ligerDialog.open({ url: 'add.ht?id='+id ,title:'编辑',width:400,height: 400, isResize:true });
        }else{
            var obj=$.ligerDialog.open({ url: 'add.ht' ,title:'新建',width:400,height: 400, isResize:true });
        }
        return true;
    }
    
    function check(id){
        $.post("${ctx}/makshi/project/classifylib/check.ht",{
            'id':id
            },function(responseText){
            var obj = new com.hotent.form.ResultMessage(responseText);
            if (obj.isSuccess()) {
                 window.location.href="/makshi/project/project/toAdd.ht?id="+id;
                    
            } else {
                $.ligerDialog.error(obj.getMessage(),"提示信息");
                return false;
            }
    });
    }
    $(function(){
    	var options={};
    	var frm=$('#classifylibForm').form();
		$("#updateOrder").click(function() {
			//frm.ajaxForm(options);
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
		
    })
</script>
</body>
</html>


