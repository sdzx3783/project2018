<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的草稿</title>
<%@include file="/commons/include/get.jsp" %>
<style>
	a.link.del {
		text-decoration: none;
	    display: inline-block;
	    padding: 0 16px;
	    font-size: 14px;
	    height: 32px;
	    line-height: 32px;
	    text-align: center;
	    cursor: pointer;
	    color: #fff;
    	background: #0f88eb;
	    border: 1px solid #0f88eb;
	    -webkit-box-sizing: border-box;
	    -moz-box-sizing: border-box;
	    box-sizing: border-box;
	    -webkit-border-radius: 3px;
	    border-radius: 3px;
	    -webkit-box-shadow: 0 1px 1px rgba(0,0,0,0.15);
	    box-shadow: 0 1px 1px rgba(0,0,0,0.15);
	}
	div.panel-top {
		margin-left: 0 !important;
		overflow: hidden;
	}
	.panel-top .group {
		float: none;
	}
</style>
<script type="text/javascript">
	function checkFormChange(runId){
		var url="checkForm.ht?runId="+runId;
		$.post(url,function(result){
			if(result){
				$.ligerDialog.confirm('草稿表单版本发生变化时候加载旧版本数据?','提示信息',function(rtn){
					if(rtn){
						jQuery.openFullWindow('${ctx}/platform/bpm/task/startFlowForm.ht?runId='+runId)
					}else{
						jQuery.openFullWindow('${ctx}/platform/bpm/task/startFlowForm.ht?runId='+runId+'&isNewVersion=1')
					}
					this.close();
				})
			}else{
				jQuery.openFullWindow('${ctx}/platform/bpm/task/startFlowForm.ht?runId='+runId)
			}
		})
	}
	function copyDraft(runId){
		$.post("copyDraft.ht?runId="+runId,function(data){
			var obj=new com.hotent.form.ResultMessage(data);
			if(obj.isSuccess()){
				$.ligerDialog.success(obj.getMessage(),"提示信息",function(rtn){
					if(rtn){
						window.location.reload();
					}
				});
			}else{
				$.ligerDialog.err("提示信息","草稿列表拷贝失败!",obj.getMessage());
			}
		});
	}
</script>
</head>
<body>
	<div id="oa_list_title">
		<div class="panel-top">
			<div class="oa-mgt10 oa-mgh20 group">		    	
		        <f:a alias="delDraft" css="oa-button oa-button--primary oa-button--blue link del" action="delDraft.ht" showNoRight="false">删除</f:a>
		    </div>
	    </div>
	</div>
	<div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <div class="oa-accordion">
            <div class="oa-accordion__title">查询条件</div>
            <div class="oa-accordion__content">               
                <form id="searchForm" method="post" action="myForm.ht" class="oa-clear">
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">流程名称</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input type="text" name="Q_processName_SL"  class="oa-input" value="${param['Q_processName_SL']}" />
                        </div>
                    </div>   
                    <%-- <li>
						<span class="label">流程实例标题:</span><input type="text" name="Q_subject_SL"  class="inputText" value="${param['Q_subject_SL']}"/>
					</li> --%>              
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">创建时间从</div>
                        <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                            <input  name="Q_begincreatetime_DL"  class="oa-input datePicker" datetype="1"  value="${param['Q_begincreatetime_DL']}" />
                        </div>
                        <span>至</span>
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input name="Q_endcreatetime_DG" class="oa-input datePicker" datetype="2" value="${param['Q_endcreatetime_DG']}"/>
                        </div>
                    </div>       
                    <div class="oa-fl oa-mgb10 oa-mgh20">
                    	<button class="oa-button oa-button--primary oa-button--blue" type="button" onclick="tosubmit()" >查询</button>
		        		<button type="button" class="oa-button oa-button--primary oa-button--blue" onclick="$.clearQueryForm()">重置</button>
                    </div>             
                </form>
            </div>
        </div>
    </div>
    <div class="oa-pdb20 oa-mgh20">
        <div id="oa_list_content" class="oa-table-scroll-wrap">
            <c:set var="checkAll">
				<input type="checkbox" id="chkall" />
			</c:set>
            <display:table name="processRunList" id="processRunItem" requestURI="myForm.ht" sort="external" class="oa-table--default oa-table--nowrap">
                <display:column title="${checkAll}" media="html" style="width:30px;">
					  <input type="checkbox" class="pk" name="runId" value="${processRunItem.runId}">
				</display:column>
				<%-- <display:column property="subject" title="流程实例标题"  sortable="true" sortName="subject" style="text-align:left"></display:column> --%>
				<display:column property="processName" title="流程名称" sortable="true" sortName="processName" style="text-align:left"></display:column>
				<display:column property="creator" title="创建人" sortable="true" sortName="creator" style="text-align:left"></display:column>
				<display:column  title="创建时间" sortable="true" sortName="createtime">
					<fmt:formatDate value="${processRunItem.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</display:column>
				<display:column title="管理" media="html">
					<f:a alias="delDraft" href="delDraft.ht?runId=${processRunItem.runId}" css="oa-button-label">删除</f:a>					
					<a href="javascript:;" onclick="checkFormChange('${processRunItem.runId}')" class="oa-button-label">编辑</a>
					<c:if test="${processRunItem.formDefId!=0}">
						<%-- <a href="javascript:;" onclick="copyDraft('${processRunItem.runId}')" class="link copy">复制</a> --%>
					</c:if>
				</display:column>                                                                             
            </display:table>
        </div>
        <hotent:paging tableId="processRunItem" />
    </div>
</body>
<script>
	$(function(){
		$('.oa-accordion').oaAccordion({});
	});
	function tosubmit(){
		$("input[name='isexpand']").val(1);
		$("#searchForm").submit();
	}	 
</script>
</html>


