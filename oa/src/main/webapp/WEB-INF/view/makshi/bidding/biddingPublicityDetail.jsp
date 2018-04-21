
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>补充通知/标底公示审批明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
<script type="text/javascript">
	//放置脚本
</script>
<style type="text/css">
	@page { margin: 0; }
	@media print { 
	.noprint { display: none;color:green } 
	.oa-table{ margin-top: 60px; }
	}
	.oa-table{
		table-layout: fixed;
		width: 100%;
	}
	.oa-table th{
		padding: 20px 20px;
		border: 1px solid #333;
	}
	.oa-table td{
		padding: 20px 20px;
		border: 1px solid #333;
	}
	.oa-no-border{
		border: 0 !important;
	}
	caption{
		text-align: center;
		font-size: 20px;
		padding-bottom: 20px;
	}
	.oa-print-title{
	text-align: center;
	}
</style>
</head>
<body class="oa-mw-page">
	<div class="oa-top-wrap noprint noprint" >
		<a class="oa-button oa-button--primary oa-button--blue" onclick="window.print();">打印</a>	
	</div>	
			
	<div class="oa-main-wrap">
		<table class="oa-table">
			<caption>补充通知/标底公示审批表</caption>
			<tr>
				<th class="oa-print-title">文件名称:</th>
				<td colspan="2">${biddingPublicity.publicity_name}</td>
			</tr>
			<tr>
				<th class="oa-print-title">编制依据:</th>
				<td colspan="2">${biddingPublicity.publicity_evidence}</td>
			</tr>
			<c:forEach var="item" items="${taskOpinionList}" varStatus="status" begin="0"  >
				<tr>
					<th class="oa-print-title">${item.taskName}:</th>
					<td style="border-right: 0;">
						<table>
							<tr>
								<td class="oa-no-border">意见：${item.opinion}</td>
							</tr>
							<tr>
								<td class="oa-no-border">审核人：${item.exeFullname}</td>
							</tr>
						</table>
					</td>
					<td style="border-left: 0; text-align: right;">审核时间：<fmt:formatDate value="${item.endTime}" pattern='yyyy-MM-dd HH:mm:ss' /></td>
				</tr>
			</c:forEach>
		</table>
	</div>

</body>
</html>

