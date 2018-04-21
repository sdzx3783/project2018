<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>排班列表管理</title>
<%@include file="/commons/include/ats.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/platform/ats/AtsDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/ats/AtsScheduleShiftList.js"></script>
<script type="text/javascript">
	function importData(){
		AtsImport({
			url:__ctx + '/platform/ats/atsScheduleShift/import.ht',
			title:'排班列表导入'
		});
	}
	function selectOrg(){
		OrgDialog({isSingle:true,callback:function(orgId,orgName){
			$('#orgId').val(orgId);
			$('#orgName').val(orgName);
		}});
	}
	function selectAttendanceFile(){
		
		AtsAttendanceFileDialog({
			isSingle:true,
			callback:function(data){
				$("#fileId").val(data.id);
				$("#fileName").val(data.userName);
			}
		});
	}
	
</script>
</head>
<body>
	<div class="panel">
	<div  class="panel-search">
			<div class="toolbar-box">
				<div class="toolbar-head clearfix">
					<!-- 顶部按钮 -->
					<div class="buttons"> 		
						<a class="btn btn-sm btn-primary fa fa-search" href="javascript:void(0);" ><span>查询</span></a>
						<a class="btn btn-sm btn-primary wizard"  href="${ctx}/platform/ats/atsTurnShift/wizard.ht" id="btnWizard" ><span>排班向导</span></a>
				        <a class="btn btn-sm btn-primary  import"   href="javascript:importData();"   ><span>导入</span></a>	
				        <a class="btn btn-sm btn-primary del "   href="javascript:void(0);"   ><span>删除</span></a>	
				        <label><input type="radio" name="showType" class="input_radio" value="0" checked="checked"/>列表
				         </label>
				        <label>
				       	 <input type="radio"  name="showType" class="input_radio" value="1" />日历
				       </label>
					</div>
				</div>
				<!-- #查询条件-->
				<div class="toolbar-body" >
					<form  id="searchForm" class="search-form" >
						<ul>
							<li>
								<span>姓名:</span>
								<input  id="userName"  class="inputText" />
							</li>
							<li>
								<span>组织:</span>
								<input id="orgId"  name="Q_orgId_L" type="hidden"/>
								<input  id="orgName"  class="inputText"  readonly="readonly" />
								<a class="btn btn-sm btn-primary fa fa-search-plus" href="javascript:selectOrg();" ><span></span></a>
							</li>
							<li>
								<span>考勤卡号:</span>
								<input  id="cardNumber"  class="inputText" />
							</li>
						</ul>
						<ul>
							<li>
								<span>考勤日期从:</span>
									 <input id="startTime" name="startTime"  class="inputText date"  value="${startTime}"/>
								
							</li>
							<li>	
								<span>至: </span>
								<input id="endTime"   name="endTime" class="inputText date"  value="${endTime}"/>
								</select>
							</li>
						</ul>
					</form>
				</div><!--/ 查询条件-->
			</div>
		</div><!--/ 操作、查询-->
		<div class="panel-body">
    			<div id="dataGrid" >
    			<table id="scheduleShiftGrid" style="height:1px;"></table>
    			<div id="gridPager1"></div>
    			</div>
    	</div>	
	
		</div><!-- end of panel-body -->
</body>
</html>


