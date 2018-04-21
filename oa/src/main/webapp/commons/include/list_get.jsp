<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<f:link href="Aqua/css/ligerui-all.css"></f:link>
<f:link href="web.css"></f:link>
<f:link href="form.css" ></f:link>
<f:link href="jquery/plugins/rowOps.css"></f:link>
<f:js pre="js/lang/common"></f:js>
<f:js pre="js/lang/js"></f:js>
<link href="${ctx}/css/common/jquery-ui.min.css" rel="stylesheet" />
<link href="${ctx}/css/common/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/css/common/bootstrap-table.css" rel="stylesheet" />
<link href="${ctx}/css/common/bootstrap-table-fixed-columns.css" rel="stylesheet" />
<link href="${ctx}/css/common/bootstrap-datepicker.min.css"
	rel="stylesheet" />
<link href="${ctx}/css/common/dragtable.css" rel="stylesheet" />

<script src="${ctx}/js/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/dynamic.jsp"></script>
<script type="text/javascript" src="${ctx}/js/util/util.js"></script>
<script type="text/javascript" src="${ctx}/js/util/json2.js"></script>
<script type="text/javascript" src="${ctx}/js/util/form.js"></script>
<script type="text/javascript" src="${ctx}/js/tree/jquery.ztree.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/base.js"></script>
<script type="text/javascript" src="${ctx}/js/custom/cookie.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/displaytag.js" ></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDialog_list.js" ></script>

<script src="${ctx}/js/common/jquery-ui.js"></script>
<script src="${ctx}/js/common/bootstrap.js"></script>
<script src="${ctx}/js/common/bootstrap-table.js"></script>
<script src="${ctx}/js/common/bootstrap-table-zh-CN.js"></script>
<script src="${ctx}/js/common/bootstrap-datepicker.min.js"></script>
<script src="${ctx}/js/common/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="${ctx}/js/common/jquery.dragtable.js"></script>
<script src="${ctx}/js/common/bootstrap-table-reorder-columns.js"></script>
<script src="${ctx}/js/common/colResizable-1.6.js"></script>
<script src="${ctx}/js/common/bootstrap-table-resizable.js"></script>
<script src="${ctx}/js/common/bootstrap-table-export.js"></script>
<%-- <script src="${ctx}/js/common/bootstrap-table-fixed-columns.js"></script> --%>
<script src="${ctx}/js/common/tableExport.js"></script>
<script src="${ctx}/js/common/jquery.base64.js"></script>
<script src="${ctx}/js/common/maishicommon.js"></script>
<script type="text/javascript" src="${ctx}/js/calendar/My97DatePicker/newWdatePicker.js"></script>
<style>
.fixed-table-container {
	border: 0;
}

#tb_common_show td, #tb_common_show th {
	border: 1px solid #dddddd !important;
}
</style>

<%@include file="/js/msg.jsp"%>

