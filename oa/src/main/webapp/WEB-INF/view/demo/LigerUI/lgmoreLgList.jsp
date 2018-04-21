<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>页内编辑多种编辑器模式Demo</title>
<%@include file="/js/lg/getLg.jsp"%>
<script type="text/javascript">
$(function(){
	//动态添加 columns
	var columns=[
		{
			display : '姓名',
			name : 'name',
	         type : 'text',
	         isSort : true,
			 editor: {
	            type: 'text'
	         } 
		},
		{
			display : '性别',
			name : 'sex',
			type : 'text',
			isSort : true,
			editor: {
	            type: 'select',
	            data:getSelectData('[{"key":"1","value":"男"},{"key":"0","value":"女"}]','sex'),
	            valueField : 'sex'
	        },
            render: function (item){
            	var datas=getSelectData('[{"key":"1","value":"男"},{"key":"0","value":"女"}]','sex');
            	for(var data in datas){
            		if(item.sex==datas[data]['sex']){
            			return datas[data]['text'];
            		}
            	}
            }
		},
		{
			display : '生日',
			name : 'birthday',
			type : 'date',
			format : getFormat('{"format":"yyyy-MM-dd","displayDate":0}'),
			isSort : true,
			editor: {
	            type: 'date'
	        } 
		},
		{
			display : '结婚日期',
			name : 'jiehunDay',
			type : 'date',
			format : getFormat('{"format":"yyyy-MM-dd","displayDate":0}'),
			isSort : true,
			editor: {
	            type: 'date'
	        } 
		},
		{
			display : '备注',
			name : 'comment',
			 type : 'text',
	         isSort : false,
			 editor: {
	            type: 'textarea',
	            height:100
	         } 
		},
		{
			display : '选择用户',
			name : 'selectUser',
			type : 'text',
			isSort : true,
			editor: {
	            type: 'selectUser',
	            multi: true
	        } 
		},
		{
			display : '选择组织',
			name : 'selectOrg',
			type : 'text',
			isSort : true,
			editor: {
	            type: 'selectOrg',
	            multi: true
	        }
		},
		{
			display : '选择角色',
			name : 'selectRole',
			type : 'text',
			isSort : true,
			editor: {
	            type: 'selectRole',
	            multi: false
	        }
		},
		{
			display : '选择岗位',
			name : 'selectJob',
			type : 'text',
			isSort : true,
			editor: {
	            type: 'selectPos',
	            multi: true
	        }
		},
		{
			display : '下拉选项',
			name : 'selectOpinion',
			type : 'text',
			isSort : true,
			editor: {
	            type: 'select',
	            data:getSelectData('[{"key":"选择1","value":"选择1"},{"key":"选择2","value":"选择2"},{"key":"选择3","value":"选择3"},{"key":"选择4","value":"选择4"},{"key":"选择5","value":"选择5"}]','selectOpinion'),
	            valueField : 'selectOpinion'
	        },
            render: function (item){
            	var datas=getSelectData('[{"key":"选择1","value":"选择1"},{"key":"选择2","value":"选择2"},{"key":"选择3","value":"选择3"},{"key":"选择4","value":"选择4"},{"key":"选择5","value":"选择5"}]','selectOpinion');
            	for(var data in datas){
            		if(item.selectOpinion==datas[data]['selectOpinion']){
            			return datas[data]['text'];
            		}
            	}
            }
		}]
	initData({"columns":columns,"innerEdit":false,"needToolbar":true});

})
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">lg多样化管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="groupUI"><a class="link search" id="btnSearch"><span></span>查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="groupUI"><a class="link add" id="btnAdd" action="edit.ht"><span></span>添加</a></div>
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


