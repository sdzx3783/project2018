<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>设施设备维修申请管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">

	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <h2>设施设备维修管理列表</h2>
	</div>

	<div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl40">
		<a class="oa-button oa-button--primary oa-button--blue" href="edit.ht">添加</a>
		<c:if test="${hasAccess }">
			<a class="update oa-button oa-button--primary oa-button--blue" id="btnUpd" action="edit.ht">修改</a>
			<a class="del oa-button oa-button--primary oa-button--blue"  action="del.ht">删除</a>
		</c:if>
	</div>

    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">申请人</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_applicant_S"  value="${param['Q_applicant_S'] }" class="oa-input" />
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">申请时间</div>
                <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                    <input id="Q_beginapplicationTime_DL" name="Q_beginapplicationTime_DL" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endapplicationTime_DL\');}'})" value="${param['Q_beginapplicationTime_DL'] }"  class="oa-input Wdate" />
                </div>
                <span>至</span>
                <div class="oa-input-wrap oa-input-wrap--ib">
                    <input id="Q_endapplicationTime_DL" name="Q_endapplicationTime_DL" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'Q_beginapplicationTime_DL\');}'})"  value="${param['Q_endapplicationTime_DL'] }" class="oa-input Wdate" />
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">故障发生时间</div>
                <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                    <input id="Q_beginfaultTime_DL"  name="Q_beginfaultTime_DL" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endfaultTime_DL\');}'})" value="${param['Q_beginfaultTime_DL'] }" class="oa-input Wdate" />
                </div>
                <span>至</span>
                <div class="oa-input-wrap oa-input-wrap--ib">
                    <input id="Q_endfaultTime_DL" name="Q_endfaultTime_DL" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'Q_beginfaultTime_DL\');}'})" value="${param['Q_endfaultTime_DL'] }" class="oa-input Wdate" />
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">设备名称</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_equipmentName_S" value="${param['Q_equipmentName_S'] }"  class="oa-input" />
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
		    <display:table name="equipmentMaintenanceApplicationList" id="equipmentMaintenanceApplicationItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column title="${checkAll}" media="html">
			  		<input type="checkbox" class="pk" name="id" value="${equipmentMaintenanceApplicationItem.id}">
				</display:column>
				<display:column  title="申请人">
					${equipmentMaintenanceApplicationItem.applicant}
				</display:column>
				<display:column  title="申请时间">
					<fmt:formatDate value="${equipmentMaintenanceApplicationItem.applicationTime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column  title="职能组">
					${equipmentMaintenanceApplicationItem.org}
				</display:column>
				<display:column  title="故障发生时间">
					<fmt:formatDate value="${equipmentMaintenanceApplicationItem.faultTime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="设备名称">
					${equipmentMaintenanceApplicationItem.equipmentName}
				</display:column>
				<display:column  title="存在问题及故障部位">
					${equipmentMaintenanceApplicationItem.problemDesc}
				</display:column>
				<display:column  title="维修方案">
					${equipmentMaintenanceApplicationItem.maintenancePlan}
				</display:column>
				<display:column  title="维修费用">
					${equipmentMaintenanceApplicationItem.maintenanceCost}
				</display:column>
				<display:column title="管理" media="html">
					<c:if test="${hasAccess }">
					<a href="edit.ht?id=${equipmentMaintenanceApplicationItem.id}" class="oa-button-label">编辑</a>
					<a href="del.ht?id=${equipmentMaintenanceApplicationItem.id}" class="oa-button-label oa-button-label-remove">删除</a>
					</c:if>
					<a href="get.ht?id=${equipmentMaintenanceApplicationItem.id}" class="oa-button-label">明细</a>
				</display:column>
			</display:table>
	    </div>
	    <hotent:paging tableId="equipmentMaintenanceApplicationItem"/>
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


