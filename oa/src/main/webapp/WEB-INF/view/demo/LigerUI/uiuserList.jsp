<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>多表联合查询Demo</title>
<%@include file="/js/lg/getLg.jsp"%>
<script type="text/javascript">
$(function(){
	//动态添加 columns
	var columns=[
	 {
		display : 'A用户名',
		name : 'F_UINAME',
		type: 'text'
	}, {
		display : 'A密码',
		name : 'PASSWORD',
		align : 'left'
	}, {
		display : 'B选择用户',
		name : 'F_SELECTUSER',
		type: 'text',
		isSort :false
	}, {
		display : 'B结婚日期',
		name : 'JIEHUNDAY',
		type: 'date',
		format : 'yyyy-MM-dd',
		isSort :false
	}, {
		display : 'C给妻子的钱',
		name : 'TOWIFE',
		type : "text",
		isSort :false
	}, {
		display : 'C生日',
		name : 'F_BIRTHDAY',
		type: 'date',
		format : 'yyyy-MM-dd',
		isSort :false
	}
	];
	initData({"columns":columns,"innerEdit":false,needToolbar:false});
})
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">jqueryUI用户表管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="groupUI"><a class="link search" id="btnSearch"><span></span>查询</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="getList.ht">
					<div class="row">
						<span class="label">A用户名:</span><input type="text" name="Q_UIname_S"  class="inputText" />
						<span class="label">A密码:</span><input type="text" name="Q_password_S"  class="inputText" />
						<span class="label">B选择用户:</span><input type="text" name="Q_selectUser_S"  class="inputText" />
						<span class="label">B结婚日期从:</span> <input  name="Q_beginjiehunday_DL"  class="inputText date" />
						<span class="label">B至: </span><input  name="Q_endjiehunday_DG" class="inputText date"  />
						<span class="label">C给妻子:</span><input type="text" name="Q_toWife_S"  class="inputText" />
						<span class="label">C生日 从:</span> <input  name="Q_beginbirthday_DL"  class="inputText date" />
						<span class="label">C至: </span><input  name="Q_endbirthday_DG" class="inputText date"  />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
			<div id="grid" style="PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; PADDING-TOP: 0px"></div>
		</div>
	</div> <!-- end of panel -->
</body>
</html>


