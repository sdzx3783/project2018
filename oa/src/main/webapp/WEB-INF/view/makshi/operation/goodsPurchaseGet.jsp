<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>物品采购申请明细</title>
<%@include file="/codegen/include/customForm.jsp"%>
<%@include file="/commons/include/ueditor.jsp"%>
<script src="${ctx}/js/hotent/formdata.js"></script>
<script src="${ctx}/js/hotent/CustomValid.js"></script>
<script src="${ctx}/js/hotent/subform.js"></script>
<script src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
<script src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">物品采购申请详细信息</span>
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
		<div class="oa-pd20">
            <table class="oa-table--default" border="1" cellspacing="0" cellpadding="2"parser="addpermission" data-sort="sortDisabled">
                    <caption> 物品采购申请 </caption>
                    <tr>
                        <th>申请人</th>
                        <td>${goodsPurchase.applicant}</td>
                        <th>申请部门</th>
                        <td> ${goodsPurchase.appli_department}</td>
                    </tr>
                    <tr>
                        <th>申请日期</th>
                        <td><fmt:formatDate value='${goodsPurchase.appli_date}' pattern='yyyy-MM-dd'/></td>
                        <th>物品名称</th>
                        <td>${goodsPurchase.name}</td>   
                    </tr>
                    <tr>
                        <th>规格型号</th>
                        <td>${goodsPurchase.specification}</td>
                        <th>数量</th>
                        <td>${goodsPurchase.number}</td>
                    </tr>
                    <tr>
                        <th>单价</th>
                        <td>${goodsPurchase.price}</td>
                        <th>采购日期</th>
                        <td><fmt:formatDate value='${goodsPurchase.purchase_date}' pattern='yyyy-MM-dd'/></td>
                    </tr>
                    <tr>
                        <th>领用人</th>
                        <td>${goodsPurchase.recipients_user}</td>
                        <th>领用部门</th>
                        <td>${goodsPurchase.recipients_department}</td>
                    </tr>
                    <tr>
                        <th>附件</th>
                        <td colspan="3">
                        	<input  ctltype="attachment" right="r"  name="m:goods_purchase:attachment" isdirectupload="0"  value='${goodsPurchase.attachment}' validatable="false" />
                        </td>
                    </tr>
            </table>
          </div>
</body>
</html>

