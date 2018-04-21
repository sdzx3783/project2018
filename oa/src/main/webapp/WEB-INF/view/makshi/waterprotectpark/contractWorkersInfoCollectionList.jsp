<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>劳务人员信息采集(水保示范园流程)管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">

    <div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <h2>劳务人员信息管理列表</h2>
	</div>


    <div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl40">
		<c:if test="${hasAccess }">
			<a class="oa-button oa-button--primary oa-button--blue add" href="edit.ht">添加</a>
			<a class="oa-button oa-button--primary oa-button--blue update" id="btnUpd" action="edit.ht">修改</a>
			<a class="oa-button oa-button--primary oa-button--blue del"  action="del.ht">删除</a>
		</c:if>
	</div>


    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">姓名</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_name_S"  class="oa-input" value="${param['Q_name_S'] }" />
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">入职时间</div>
                <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                    <input id="Q_beginentry_date_DL" name="Q_beginentry_date_DL" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endentry_date_DL\');}'})" value="${param['Q_beginentry_date_DL'] }"  class="oa-input Wdate" />
                </div>
                <span>至</span>
                <div class="oa-input-wrap oa-input-wrap--ib">
                    <input id="Q_endentry_date_DL"  name="Q_endentry_date_DL" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'Q_beginentry_date_DL\');}'})" value="${param['Q_endentry_date_DL'] }" class="oa-input Wdate" />
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">劳务工种</div>
                <div class="oa-select-wrap oa-mgl100">
                    <select id="work_type" name="work_type" class="oa-select">
                        <option value="">-请选择-</option>
                        <option value="1" <c:if test="${param['work_type']==1}">selected</c:if>>保安</option>
                        <option value="2" <c:if test="${param['work_type']==2}">selected</c:if>>保洁</option>
                    </select>
                </div>
            </div>

            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
            </div>
        </form>
    </div>

	<div class="oa-pdb20 oa-mgh20">
	    <div id="oa_list_content" class="oa-table-scroll-wrap">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
			<c:set var="cnt" value="${(pageBeancontractWorkersInfoCollectionItem.currentPage-1)*pageBeancontractWorkersInfoCollectionItem.pageSize+1 }"/>
		    <display:table name="contractWorkersInfoCollectionList" id="contractWorkersInfoCollectionItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column title="${checkAll}" media="html">
			  		<input type="checkbox" class="pk" name="id" value="${contractWorkersInfoCollectionItem.id}">
				</display:column>
				<display:column title="序号">
					<c:out value="${cnt}"/>
					<c:set var="cnt" value="${cnt+1}"/>
				</display:column>
				<display:column title="姓名">
					${contractWorkersInfoCollectionItem.name}
				</display:column>
				<display:column title="劳务工种">
					<c:if test="${contractWorkersInfoCollectionItem.work_type==1}">保安</c:if>
					<c:if test="${contractWorkersInfoCollectionItem.work_type==0}">保洁</c:if>
				</display:column>
				<display:column title="年龄">
					${contractWorkersInfoCollectionItem.age}
				</display:column>
				<display:column title="性别">
					<c:if test="${contractWorkersInfoCollectionItem.gender==1}">男</c:if>
					<c:if test="${contractWorkersInfoCollectionItem.gender==0}">女</c:if>
				</display:column>
				<display:column title="是否住宿">
					<c:if test="${contractWorkersInfoCollectionItem.is_stay==1}">是</c:if>
					<c:if test="${contractWorkersInfoCollectionItem.is_stay==0}">否</c:if>
				</display:column>
				<display:column title="入职时间">
					<fmt:formatDate value="${contractWorkersInfoCollectionItem.entry_date}" pattern="yyyy-MM-dd" type="date" dateStyle="long" />
				</display:column>
				<display:column title="在职状态">
					<c:if test="${contractWorkersInfoCollectionItem.status==1}">在职</c:if>
					<c:if test="${contractWorkersInfoCollectionItem.status==0}">离职</c:if>
				</display:column>
				
				<display:column title="管理" media="html">
					<c:if test="${hasAccess }">
					<a href="edit.ht?id=${contractWorkersInfoCollectionItem.id}" class="oa-button-label edit">编辑</a>
					<a href="del.ht?id=${contractWorkersInfoCollectionItem.id}" class="oa-button-label oa-button-label-remove">删除</a></c:if>
					<a href="get.ht?id=${contractWorkersInfoCollectionItem.id}" class="oa-button-label detail">明细</a>
				</display:column>
			</display:table>
		</div>

		<hotent:paging tableId="contractWorkersInfoCollectionItem"/>
	</div>

<script>
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


