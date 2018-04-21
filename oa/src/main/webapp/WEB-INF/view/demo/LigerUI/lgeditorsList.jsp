<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>list页面带有树的Demo</title>
<%@include file="/js/lg/getLg.jsp"%>
<script type="text/javascript">
$(function(){
	//动态添加 columns
	var columns=[
	 {
		display : '姓名',
		name : 'name',
		type: 'text',
		editor: {
            type: 'text'
        } 
	}, {
		display : '性别',
		name : 'sex',
		editor: { type: 'select', data: [{ sex: '1', text: '男' }, { sex: '0', text: '女'}], valueField: 'sex' },
         render: function (item){
         	if (parseInt(item.sex) == 1) return '男';
         return '女';
         },
	}, {
		display : '生日',
		name : 'birthday',
		type: 'date',
		editor : {
			type: 'date'
		}
	}, {
		display : '年薪',
		name : 'money',
		editor : {
			type : 'text'
		}
	}, {
		display : '入职日期',
		name : 'joinDate',
		type: 'date',
		format : 'yyyy/MM/dd hh:mm:ss', 
		editor : {
			type: 'date'
		}
	}, {
		display : '工作性质',
		name : 'jobType',
		type: 'text',
	    editor: { type: 'text'}
	}, {
		display : '备注',
		name : 'comment',
		isSort : false,
		hide : false,
		editor: {
            type: 'text',height: 100
        } 
	}];
	initData({"columns":columns,"innerEdit":true,needToolbar:true});
})
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">lgEditor多样化管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="groupUI"><a class="link search" id="btnSearch"><span></span>查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="groupUI"><a class="link add" action="edit.ht"><span></span>添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="groupUI"><a class="link update" id="btnUpd" action="edit.ht"><span></span>修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="groupUI"><a class="link del"  action="del.ht"><span></span>删除</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="getList.ht">
					<div class="row">
						<span class="label">姓名:</span><input type="text" name="Q_name_S"  class="inputText" />
						<span class="label">性别:</span><input type="text" name="Q_sex_S"  class="inputText" />
						<span class="label">生日 从:</span> <input  name="Q_beginbirthday_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endbirthday_DG" class="inputText date" />
						<span class="label">年薪:</span><input type="text" name="Q_money_L"  class="inputText" />
						<span class="label">入职日期 从:</span> <input  name="Q_beginjoinDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endjoinDate_DG" class="inputText date" />
						<span class="label">结婚日期 从:</span> <input  name="Q_beginjiehunDay_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endjiehunDay_DG" class="inputText date" />
						<span class="label">工作性质:</span><input type="text" name="Q_jobType_S"  class="inputText" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<div id="grid" style="PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; PADDING-TOP: 0px"></div>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


