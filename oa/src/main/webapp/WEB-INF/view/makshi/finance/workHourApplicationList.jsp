<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>工时填报申请管理</title>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
</head>
<body class="oa-mw-page">
	<div id="oa_list_title" class="oa-mgb20 oa-project-title" style="padding-top: 1px;">
		<div id="oa_search_table_button_wrap" class="oa-head-wrap" style="margin-bottom: 9px;">
			<a class="oa-button oa-button--primary oa-button--blue" href="edit.ht">添加</a>
			<a class="oa-button oa-button--primary oa-button--blue"  href="javascript:;" onclick="postSelected(this)">提交</a>
		</div>
        <!-- <h2>考勤填报列表</h2> -->
 	</div>
 	<div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">日期从</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input class="oa-input date"  name="Q_beginapplicant_time_DL" id="Q_beginapplicant_time_DL" value="${param['Q_beginapplicant_time_DL']}" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endapplicant_time_DG\');}'})" value="${param['Q_beginapplicant_time_DL']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">至</div>
                <div class="oa-input-wrap oa-mgl100">
                   <input class="oa-input date"  name="Q_endapplicant_time_DG" id="Q_endapplicant_time_DG" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'Q_beginapplicant_time_DL\');}'})"  value="${param['Q_endapplicant_time_DG']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10 oa-mgh20">
	                <button id="btnSearch" class="oa-button oa-button--primary oa-button--blue">查询</button>
	            </div>
	        </form>
    	</div>
	
            
		<div class="oa-pdb20 oa-mgh20">
	    	<div id="oa_list_content" class="oa-table-scroll-wrap">
		   	    <c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="workHourApplicationList" id="workHourApplicationItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap table-list">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" <c:if test="${empty workHourApplicationItem.refRunid  }">data-canrun="1"</c:if> class="pk" name="id" value="${workHourApplicationItem.id}">
				</display:column>
				<display:column title="序号" style="width:70px">
					${workHourApplicationItem_rowNum}
				</display:column>
				<display:column title="日期" style="width:70px">
					<fmt:formatDate value='${workHourApplicationItem.applicant_time}' pattern='yyyy-MM-dd' />
				</display:column>
				<%-- <display:column title="累计正常工时" style="width:70px">
					${workHourApplicationItem.work_hour_total}
				</display:column> --%>
				<display:column title="累计工作时间比例" style="width:70px">
					${workHourApplicationItem.work_hour_rate}
				</display:column>
				<display:column title="状态" style="width:70px">
					${workHourApplicationItem.runStatus}
				</display:column>
				<display:column title="管理" media="html" style="width:220px">
					<c:if test="${empty workHourApplicationItem.refRunid }">
						<a href="edit.ht?id=${workHourApplicationItem.id}" class="oa-button-label">编辑</a>
					</c:if>
					<a href="get.ht?id=${workHourApplicationItem.id}" class="oa-button-label">查看</a>
					<c:if test="${empty workHourApplicationItem.refRunid }">
						<a href="javascript:;" onclick="postData(this,${workHourApplicationItem.id})" class="oa-button-label">提交</a>
						<a href="del.ht?id=${workHourApplicationItem.id}" class="oa-button-label">删除</a>												
					</c:if>
				</display:column>
			</display:table>
		</div><!-- end of panel-body -->				
		<hotent:paging tableId="workHourApplicationItem"/>
	</div> <!-- end of panel -->
	<script type="text/javascript">
		
		function postData(obj,id){
			//obj.onclick = null;
			run(id);
		}
		function run(id){
			var url="/makshi/finance/workHourApplication/runStart.ht";
			$.post(url,{"id":id},function(responseText){
				var obj = new com.hotent.form.ResultMessage(responseText);
				if (obj.isSuccess()) {
					$.ligerDialog.success('<p><font color="green">'+obj.getMessage()+'</font></p>');
					setTimeout("window.location.reload(true);",2000);
				}else {
					$.ligerDialog.error(obj.getMessage(),"提示信息");
				}
			});
		}
		function postSelected(obj){
			
			var url="/makshi/finance/workHourApplication/runStart.ht";
			var ids="";
			var cnt=0;
			$("input[data-canrun='1']:checked").each(function(){
				ids+="&id="+$(this).val();
				cnt++;
			});
			if(ids.length<=0){
				$.ligerDialog.alert("请选择一行还未提交的记录!")
				return ;
			}
			if(cnt>4){
				$.ligerDialog.alert("一次最多只能提交4条数据!")
				return ;
			}
			//alert(ids);
			//obj.onclick = null;
			url=url+"?"+ids.substring(1);
			$.get(url,function(responseText){
				var obj = new com.hotent.form.ResultMessage(responseText);
				if (obj.isSuccess()) {
					setTimeout(function(){},1000);
					$.ligerDialog.success('<p><font color="green">'+obj.getMessage()+'</font></p>');
					//setTimeout("window.location.reload(true);",2000);
				}else {
					$.ligerDialog.error(obj.getMessage(),"提示信息");
					//setTimeout("window.location.reload(true);",2000);
				}
			});
		}
	</script>
	
</body>
</html>


