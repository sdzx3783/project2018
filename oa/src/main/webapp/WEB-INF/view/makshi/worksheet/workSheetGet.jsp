
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>考勤记录明细</title>
    <%@include file="/commons/include/customForm.jsp"%>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <script type="text/javascript">
	//放置脚本
</script>
<style>
  .panel-toolbar{
    margin: 0;
}
.container{
    margin: 0 16px;
}
</style>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">考勤记录详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="list.ht"><span></span>返回</a>
					</div>
				</div>
			</div>
		</div>
	</div>

    <div class="container">
        <table class="formTable" border="0" cellspacing="0" cellpadding="0" parser="addpermission" data-sort="sortDisabled" width="-418">
            <tbody>
                <tr class="firstRow">
                    <td class="formHead" colspan="6">主 &nbsp; &nbsp; &nbsp;表</td>
                </tr>
                <tr>
                    <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">姓名:</td>
                    <td style="width: 15%" class="formInput"> ${workSheet.fullname} </td>
                    <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">部门:</td>
                    <td style="width: 15%" class="formInput"> ${workSheet.org} </td>
                    <td class="formTitle" align="right" colspan="1" rowspan="1" style="word-break: break-all;">签到时间:</td>
                    <td class="formInput">
                        <fmt:formatDate value="${workSheet.clock_time}" pattern="yyyy-MM-dd"/>
                    </td>
                </tr>
                <tr>
                    <td class="formTitle" align="right" colspan="1" rowspan="1" style="word-break: break-all;">星期:</td>
                    <td class="formInput" align="left" colspan="1" rowspan="1"> ${workSheet.week} </td>
                    <td class="formTitle" align="right" colspan="1" rowspan="1" style="word-break: break-all;"><span style="font-size: 13.3333px; text-align: right; background-color: rgb(240, 241, 241);">类型:</span></td>
                    <td class="formInput" align="right" colspan="1" rowspan="1"> ${workSheet.type} </td>
                    <td style="width: 10%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 13.3333px; text-align: right; background-color: rgb(240, 241, 241);">创建时间:</span></td>
                    <td style="width: 15%" class="formInput">
                        <fmt:formatDate value="${workSheet.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                </tr>
                <tr>
                    <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">备注</td>
                    <td style="width: 15%; word-break: break-all;" class="formInput" rowspan="1" colspan="5"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${workSheet.remark} </span> </td>
                </tr>
            </tbody>
        </table>
    </div>

</body>
</html>

