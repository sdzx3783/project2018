<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>填写表单</title>
	<%@include file="/commons/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerTab.js" ></script>
</head>
<body class="oa-mw-page">
	<div class="oa-top-wrap">
        <a class="oa-button oa-button--primary oa-button--blue" href="${returnUrl}" >返回</a>
	</div>

    <div class="oa-main-wrap">
        <div class="oa-table-scroll-wrap">
        	<table class="oa-table--default oa-table--nowrap">
                <thead> 
            		<tr>
            			<th>序号</th>
                        <th>字段名称</th>
                        <th>字段值</th>
                        <th>填写人</th>
                        <th>填写时间</th>
            		</tr>
                </thead>

                <tbody>
            		<c:forEach items="${list}" var="field" varStatus="no">
                        <tr>
                            <td>${ no.index + 1}</td>
                            <td>${field.fieldName }</td>
                            <td>
                                <c:if test="${field.fieldType=='attach' }">
                                    <input name="fieldvalue"  ctltype="attachment"  isdirectupload="0" right="r" value='${field.fieldValue }' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
                                </c:if>
                                <c:if test="${field.fieldType=='user' }">
                                	 ${fn:split(field.fieldValue,'|')[0]}
                                </c:if>
                                <c:if test="${field.fieldType!='attach' and field.fieldType!='user'}">
                                    ${field.fieldValue }
                                </c:if>
                            </td>
                            <td>${field.cuser}</td>
                            <td><fmt:formatDate value='${field.ctime }' pattern='yyyy-MM-dd' /></td>
                        </tr>
                    </c:forEach>
                </tbody>
        	</table>
        </div>
    </div>
</body>
</html>
