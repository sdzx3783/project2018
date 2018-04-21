<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>阶段库</title>
<%@include file="/commons/include/get.jsp"%>
<style>
    .icon-location-arrow{
        margin-top: 3px;
        font-size: 16px;
    }
    .link.search{
        padding: 4px 15px;
        font-size: 14px;
        display: inline-block;
    }
    a.link.send{
        color: #fff;
        padding: 5px 10px;
        padding-left: 30px;
        border-radius: 3px;
        background: #478de4 url(/images/email_icon.png) 5px center no-repeat;
    }
    a.link.send + a{
        margin-left: 5px;
        background: #36bb5f url(/images/message_icon.png) 5px center no-repeat;
    }
    .panel-table{
            overflow-y: auto !important;
    }
   
</style>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
</head>
<script type="text/javascript">
	function editStage(){
		$.ligerDialog.open({ url: 'edit.ht' ,width:400,height: 400, isResize:true,id:'classifyEditFrame' });
		return false;
	}
	
	function addClassifyStage(){
		//单击删除超链接的事件处理
		$("div.group > a.link.save").click(function()
		{	
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			
			if($(this).hasClass('disabled')) return false;
			
			var action=$(this).attr("action");
			var _infoMsg=$(this).attr("infoMsg");//自定义属性（用户修改原有批量操作时的提示信息）
			
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
			url+="&classifyid=${classifyid}";
			$.ligerDialog.confirm(_infoMsg?'确认'+_infoMsg+'吗？':'确认删除吗？','提示信息',function(rtn) {
				if(rtn) {
					/* var form=new com.hotent.form.Form();
					form.creatForm("form", action);
					form.addFormEl(keyName, delId);
					form.ajaxForm(options);
					form.submit(); */
					$.get(url,function(responseText){
						var obj = new com.hotent.form.ResultMessage(responseText);
						if (obj.isSuccess()) {
							window.parent.location.reload(true);
						} else {
							$.ligerDialog.error(obj.getMessage(),"提示信息");
						}
					});
				}
			});
			return false;
		});
	}
	$(function(){
		addClassifyStage();
	})
	function showResponse(responseText) {
		alert(responseText);
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				window.location.reload(true);
				window.close();
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
			
		}
</script>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">阶段库列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link save" infoMsg="保存" action="/makshi/project/classifylib/addClassifyStage.ht"><span></span>保存</a>
					</div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="selector.ht">
					<div class="row">
						<p>
						<%-- <span class="label" >创建部门 </span><select name="createorgid" id="orgs">
								<c:forEach items="${orgs}" var="org" >
									<option value="${org.orgId }" <c:if test="${param['createorgid']==org.orgId}">selected="selected"</c:if>>${org.orgName}</option>
								</c:forEach>
							</select> --%>
                          <input type="hidden" name="createorgid" value="${orgid }"> 
                          <input type="hidden" name="classifyid" value="${classifyid }"> 
                         <span class="label">阶段名称 </span><input type="text" name="Q_stagename_SL" value="${param['Q_stagename_SL'] }" class="inputText" />
						<a class="link search button" id="btnSearch"><span class="icon-location-arrow"></span><span>筛选</span></a>
						</p>
						
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="stagelibList" id="stagelib" requestURI="selector.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
					<input type="checkbox" class="pk" name="stageno" value="${stagelib.stageno}">
				</display:column>
				<display:column property="stagename" title="阶段名称" sortable="false" ></display:column>
				<display:column property="stageno" title="阶段编号"  sortable="false" ></display:column>
                       
                 <display:column property="createorg" title="创建部门" sortable="false" ></display:column>
                 <display:column property="tasknum" title="包含任务数" sortable="false" ></display:column> 
                 <display:column property="remark" title="备注" sortable="false" ></display:column> 
				
			</display:table>
			<hotent:paging tableId="stagelib"/>
		</div><!-- end of panel-body -->	
	</div> <!-- end of panel -->
	

</body>
</html>


