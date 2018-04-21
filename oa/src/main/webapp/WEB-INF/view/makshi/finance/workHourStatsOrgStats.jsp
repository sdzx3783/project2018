
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>工时填报申请明细</title>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<f:link href="web.css" ></f:link>
<f:link href="Aqua/css/ligerui-all.css" ></f:link>
<f:link href="tree/zTreeStyle.css" ></f:link>
<f:link href="form.css" ></f:link>
<f:js pre="js/lang/common" ></f:js>
<f:js pre="js/lang/js" ></f:js>
<script type="text/javascript" src="${ctx}/js/dynamic.jsp"></script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/js/util/util.js"></script>
<script type="text/javascript" src="${ctx}/js/util/json2.js"></script>
<script type="text/javascript" src="${ctx}/js/util/form.js"></script>
<script type="text/javascript" src="${ctx}/js/tree/jquery.ztree.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/base.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerComboBox.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/htDicCombo.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerMenuBar.js" ></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerMenu.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerTextBox.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerTip.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerTab.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerMessageBox.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerMsg.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDrag.js" ></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDialog.js" ></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerResizable.js" ></script>
<script type="text/javascript" src="${ctx}/js/calendar/My97DatePicker/WdatePicker.js" ></script>
<link href="${ctx}/styles/default/css/jquery.qtip.css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery.qtip.js" ></script>
<%@include file="/commons/include/ueditor.jsp" %>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/rule.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/util/DialogUtil.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/SelectorInit.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CustomForm.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/HtmlUploadDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/SubtablePermission.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/ReadOnlyQuery.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/OfficeControl.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/OfficePlugin.js"></script>
<script type="text/javascript" src="${ctx}/js/ntkoWebSign/NtkoAddSecSign.js"></script>
<script type="text/javascript" src="${ctx}/js/ntkoWebSign/NtkoWebSign.js"></script>
<script type="text/javascript" src="${ctx}/js/ntkoWebSign/WebSignPlugin.js"></script>

<link href="${ctx}/js/pictureShow/css/jquery.fancybox.css" rel="stylesheet" />
<link href="${ctx}/js/pictureShow/css/pictureShow.css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/js/pictureShow/jquery.fancybox.pack.js"></script>
<script type="text/javascript" src="${ctx}/js/pictureShow/PictureShowControl.js"></script>
<script type="text/javascript" src="${ctx}/js/pictureShow/PictureShowPlugin.js"></script>

<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/FormMath.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/FormDate.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/FormUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/FormExcel.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/ShowExeInfo.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysAuditLink.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/Cascadequery.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/ImageQtip.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/FormInit.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/RunAliasScript.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/bpmFormDialogCombinate/CombinateDialogUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/Share.js"></script>

<script type="text/javascript" src="${ctx}/js/makshi/asset.js"></script>
<script type="text/javascript" src="${ctx}/js/makshi/userInfo.js"></script>
<script type="text/javascript" src="${ctx}/js/makshi/qualification.js"></script>
<script type="text/javascript" src="${ctx}/js/makshi/political.js"></script>


<%-- <%@include file="/commons/include/customForm.jsp"%> --%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript">
	//放置脚本
</script>
<style>
    .oa-input-wrap.select{
        position: relative;
        margin-right: 50px;
    }
    .link.org{
        position: absolute;
        right: -50px;
        top: 7px;
    }
    .file-prefer label {
    	color: #657386;
    }
	.showFileName {
		margin-left: 20px;
	}
	.my-file input {
	    position: absolute;
	    display: block;
	    width: 100%;
	    height: 100%;
	    right: 0;
	    top: 0;
	    opacity: 0;
	    font-size: 0;
	}
	.vertical{ margin:2 auto;width:10px;line-height:24px;border:1px solid #333} 
	.oa-table-scroll-wrap {
		overflow-x: visible;
	}
	.oa-pdb20{
		margin:20;
		padding:10;
	}
	td,th{
		text-align:center;
		width: 10px;
	}
	td.title{
		 font-size:20px
	}
	.oa-table-scroll-wrap{
		border:0
	}
	.oa-table--default{
		border-width: 1px;
    	border-style: solid;
    	border-color: rgb(236, 239, 248);
    	border-image: initial;
	}
	.oa-table--default th, .oa-table--default td{
		padding: 0px 1px;
	}
	
</style>
</head>
<body>
	<div id="oa_list_title" class="oa-project-title">
	</div>
	<div id="oa_list_search" class="oa-pd20 my-condition">
        <form id="searchForm" method="post" action="orgStats.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">月份</div>
                <div class="oa-input-wrap oa-mgl100">
                   <input id="queryMonth" name="queryMonth" type="text" datefmt="yyyy-MM" value="${queryMonth }" class="oa-input Wdate" />
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">部门</div>
                <div class="oa-input-wrap select oa-mgl100">
                    <input name="org"  type="text" required="required" ctltype="selector" class="org oa-input"  value="${org}" initValue="${orgID}" validate="{empty:false}" readonly="readonly" />
                </div>
            </div>
            <div class="oa-fl oa-mgb10 oa-mgh20 my-options">
                <button type="button" onclick="queryFunc()" class="oa-button oa-button--primary oa-button--blue">查询</button>
            	<button type="button" id="exportBtn" class="oa-button oa-button--primary oa-button--blue">导出</button>
            </div>
        </form>
    </div>
    <c:if test="${not empty userWorkStats}">
	    <div class="oa-pdb20 oa-mgh20">
			<div class="oa-table-scroll-wrap" >
				<table class="oa-table--default oa-table--nowrap" >
					<thead><tr><td class="notscroll title" colspan="${mondDays+3 }">深 水 咨 询 公 司 考 勤 表</td> </tr></thead>
					<thead><tr><td colspan="${mondDays+3 }" class="notscroll">部门：${org }                月份：${queryMonth }</td> </tr></thead>
					<tr>
						<th rowspan="2" class="vertical">姓名</th>
						<th>日期</th>
							<c:forEach var="d" begin="1" end="${mondDays }" step="1"> 
	         					<th>${d}</th>
							</c:forEach>
						<th rowspan="2">考勤天数</th>
						<!-- <th rowspan="2">考勤小时</th> -->
						<!-- <th rowspan="2">签名</th> -->
					</tr>
					<tr>
						<th>星期</th>
						<c:forEach var="daystr"  items="${daysStr }"> 
	         					<th>${daystr}</th>
						</c:forEach>
					</tr>
					<c:forEach items="${userWorkStats }" varStatus="vs" var="workStat">
						<tr>
							<td rowspan="3">${workStat.value.get(0).username }</td>
							<td>上</td>
							<c:forEach var="ws" varStatus="wsvs" items="${workStat.value}">
									<td>
									<c:choose>
										<c:when test="${ws.amMap.isovertime==1}">加</c:when>
										<c:when test="${ws.amMap.isleave==1 && (not empty vacationMap.get(ws.day))}">${vacationMap.get(ws.day) }</c:when>
										<c:when test="${ws.amMap.isleave==1 && (empty vacationMap.get(ws.day))}">${ws.amMap.leavetype }<c:if test="${empty ws.amMap.leavetype}">假</c:if></c:when>
										<c:when test="${ws.amMap.isleave==0 && ws.hadwork!=0 && (ws.amOrBm==1 || ws.amOrBm==0) }">√</c:when>
										<c:when test="${not empty vacationMap.get(ws.day)}">${vacationMap.get(ws.day) }</c:when>
									</c:choose>
									</td>
								</td>
							</c:forEach>
							<td rowspan="3"></td>
							<!-- <td rowspan="3"></td> -->
							<!-- <td rowspan="3"></td> -->
						</tr>
						<tr>
							<td>下</td>
							<c:forEach var="ws" items="${workStat.value}">
								<td>
									<c:choose>
										<c:when test="${ws.bmMap.isovertime==1}">加</c:when>
										<c:when test="${ws.bmMap.isleave==1 && (not empty vacationMap.get(ws.day))}">${vacationMap.get(ws.day) }</c:when>
										<c:when test="${ws.bmMap.isleave==1 && (empty vacationMap.get(ws.day))}">${ws.bmMap.leavetype }<c:if test="${empty ws.bmMap.leavetype}">假</c:if></c:when>
										<c:when test="${ws.bmMap.isleave==0 && ws.hadwork!=0 && (ws.amOrBm==2 || ws.amOrBm==0)}">√</c:when>
										<c:when test="${not empty vacationMap.get(ws.day)}">${vacationMap.get(ws.day) }</c:when>
									</c:choose>
								</td>
							</c:forEach>
						</tr>
						<tr>
							<td>加</td>
							<c:forEach var="ws"  items="${workStat.value}">
								<td></td>
							</c:forEach>
						</tr>
					</c:forEach>
					<tr>
						<th colspan="${mondDays+3}">注：√=上班      加=加班   假=请假（事假、调休、年假、病假、 产假、婚假、 丧假、陪产假）</th>
					</tr>
				</table>
			</div>
		</div>
	</c:if>
	
    
     <script type="text/javascript">  
	     	function queryFunc(){
				var orgID=$("input[name='orgID']").val();
				if(orgID==undefined || orgID.length<=0){
					alert("请选择部门");
					return ;
				}
				$("#searchForm").submit();
			}
             
             $(function(){
            	 $("#exportBtn").click(function exportExcel(){
            		var orgID=$("input[name='orgID']").val();
     				if(orgID==undefined || orgID.length<=0){
     					alert("请选择部门");
     					return ;
     				}
            		 /* $("#exportBtn").off("click"); */
            		 var url="exportOrgStatsExcel.ht"
            		 window.location.href=url+"?queryMonth="+$("#queryMonth").val()+"&orgID="+orgID;
            	 });
             })
    </script>
</body>
</html>

