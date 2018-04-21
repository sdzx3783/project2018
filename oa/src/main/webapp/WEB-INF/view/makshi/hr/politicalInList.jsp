<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>党员转入管理</title>
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<%@include file="/commons/include/list_get.jsp" %>
</head>
<body class="oa-mw-page">
	<div id="oa_list_title" class="oa-mgb10 oa-project-title"></div>
	<c:choose>
		<%-- 判断是否有全部查询权限 --%>
		<c:when test="${queryAll}">
			<jsp:include page="/commons/include/list_common.jsp"></jsp:include>
			<script type="text/javascript">
				$(function() {
					$("#valuesx").hide();
					var tableParam = {
						columns : [ {
							/* checkbox : true,
							exportflag : false,//导出标志，默认导出。false为不导出该字段
						}, { */
							title : '序号',//显示的title
							formatter :'addNum'
						}, {
							field : 's.F_user_name',//查询的字段
							title : '姓名',//显示的title
							sortable : true
						//是否支持排序
						}, {
							field : 's.F_sex',
							title : '性别',
							sortable : true,
							formatter :'setSelect'
						//是否支持排序
						}, {
							field : 's.F_birthday',
							title : '出生年月',
							sortable : true
						//是否支持排序
						}, {
							field : 's.F_nation',
							title : '民族',
							sortable : true
						//是否支持排序
						}, {
							field : 's.F_home',
							title : '籍贯',
							sortable : true
						//是否支持排序
						}, {
							field : 's.F_marrige_status',
							title : '婚姻状况',
							sortable : true
						//是否支持排序
						}, {
							field : 's.F_join_date',
							title : '入党时间',
							sortable : true,
/* 							dateFlag : true,//是否是时间，这个会弹出时间控件选择器
							dateFormat : "yyyy-MM-dd",//时间的显示格式 */
						//是否支持排序
						}, {
							field : 's.F_beformal_date',
							title : '转正时间',
							sortable : true,
							/* dateFlag : true,//是否是时间，这个会弹出时间控件选择器
							dateFormat : "yyyy-MM-dd",//时间的显示格式 */
						//是否支持排序
						}, {
							field : 's.F_start_work_date',
							title : '参加工作时间',
							sortable : true,
							/* dateFlag : true,//是否是时间，这个会弹出时间控件选择器
							dateFormat : "yyyy-MM-dd",//时间的显示格式 */
						//是否支持排序
						}, {
							field : 's.F_join_branch',
							title : '入党时所在支部',
							sortable : true
						//是否支持排序
						}, {
							field : 's.F_beformal_branch',
							title : '转正时所在支部',
							sortable : true
						//是否支持排序
						}, {
							field : 's.F_edu',
							title : '学历',
							sortable : true
						//是否支持排序
						}, {
							field : 's.F_connect_tel',
							title : '联系电话',
							sortable : true
						//是否支持排序
						}, {
							field : 's.F_job_in_branch',
							title : '现任党内职务',
							sortable : true
						//是否支持排序
						}, {
							field : 's.F_now_branch',
							title : '所在支部',
							sortable : true
						//是否支持排序
						}, {
							field : 's.F_department',
							title : '所在部门',
							sortable : true
						//是否支持排序
						}, {
							field : 's.F_pay_date',
							title : '党费缴纳至',
							sortable : true,
							/* dateFlag : true,//是否是时间，这个会弹出时间控件选择器
							dateFormat : "yyyy-MM",//时间的显示格式 */
						//是否支持排序
						}, {
							field : '管理',//即使不去查询这个字段也必须。用来记录改变列的顺序用。如果不去查询必须带nosearch字段
							title : '管理',
							nosearch : true,//不去数据库查询
							formatter : 'edit'//显示的时候执行edit函数
						}, ],
						uniqueId : "s.ID",//唯一主键字段
						type : "w_political_inList",//类型，用户存储在数据库中，每个项目的不同页面保证唯一
						tableName : 'w_political_in s', //表明
						userId : '${sessionScope.LOGIN_USER_ID }', //用户ID
					//sortName : 's.seller_name',//初始化排序
					//sortOrder : 'desc'//初始化排序
					}
					initCommonTable(tableParam);
				});
				/* *
				修改显示列
				value: the field value. 
				row: the row record data.
				index: the row index
				 **/
			    function addNum(value, row, index) {  
						var page = $('#tb_common_show').bootstrapTable("getPage"); 
				        return page.pageSize * (page.pageNumber - 1) + index + 1;   
			    } 
				
				function edit(value, row, index) {
					var html = 
							 '<a class="oa-del" href="del.ht?id=' + row["s.ID"]+ '">&nbsp删除 </a>'+
							 '<a href="edit.ht?id=' + row["s.ID"]+'" target="_blank">&nbsp编辑</a>';
					return html;
				}
				
			 	function setSelect(value, row, index) {
					if (value == 0) {
						return "女";
					} 
					if (value == 1) {
						return "男";
					}
					
				} 
				/**
				 * 获取选择的数据
				 */
				function getSelect() {
					return $('#tb_common_show').bootstrapTable('getSelections');
				}
				$(function(){
					$(".oa-mw-page").on("click","a.oa-del",function(){
						  if($(this).hasClass('disabled')) return false;
						  var ele = this;
						  $.ligerDialog.confirm('确认删除吗？','提示信息',function(rtn) {
						  	 if(rtn) {
						    	if(ele.click) {
						    		window.location.href=ele.href;
						   		 } else {
						    		 location.href=ele.href;
						 	     }
						   	 }
						  });
						  return false;
					 });
				});
			</script>
		</c:when>
		<c:otherwise>
		    <div class="oa-pdb20 oa-mgh20">
	    		<div id="oa_list_content" class="oa-table-scroll-wrap">
			    	<table id="valuesx" class="oa-table--default oa-table--nowrap">
						<thead>
							<tr>
								<th>序号</th>
								<th>姓名</th>
								<th>性别</th>
								<th>出生年月</th>
								<th>民族</th>
								<th>籍贯</th>
								<th>婚姻状况</th>
								<th>入党时间</th>
								<th>转正时间</th>
								<th>参加工作时间</th>
								<th>入党时所在支部</th>
								<th>转正时所在支部</th>
								<th>学历</th>
								<th>联系电话</th>
								<th>现任党内职务</th>
								<th>所在支部</th>
								<th>所在部门</th>
								<th>党费缴纳至</th>
								<th>管理</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${not empty politicalInList}">
									<c:forEach items="${politicalInList}"  var="politicalIn" varStatus="id" >
										<tr>
											<td>${id.index+1}</td>
											<td>${politicalIn.user_name}</td>
											<td>${empty politicalIn.sex?"-":politicalIn.sex}</td>
											<td>${empty politicalIn.birthday?"-":politicalIn.birthday}</td>
											<td>${empty politicalIn.nation?"-":politicalIn.nation}</td>
											<td>${empty politicalIn.home?"-":politicalIn.home}</td>
											<td>${empty politicalIn.marrige_status?"-":politicalIn.marrige_status}</td>
											<td>${politicalIn.join_date}</td>
											<td>${politicalIn.beformal_date}</td>
											<td>${politicalIn.start_work_date}</td>
											<td>${empty politicalIn.join_branch?"-":politicalIn.join_branch}</td>
											<td>${empty politicalIn.beformal_branch?"-":politicalIn.beformal_branch}</td>
											<td>${empty politicalIn.edu?"-":politicalIn.edu}</td>
											<td>${empty politicalIn.connect_tel?"-":politicalIn.connect_tel}</td>
											<td>${empty politicalIn.job_in_branch?"-":politicalIn.job_in_branch}</td>
											<td>${empty politicalIn.now_branch?"-":politicalIn.now_branch}</td>
											<td>${empty politicalIn.department?"-":politicalIn.department}</td>
											<td>${empty politicalIn.pay_date?"-":politicalIn.pay_date}</td>
											<td><a href="edit.ht?id=${politicalIn.id}" target="_blank">&nbsp;编辑</a></td>
										</tr>
									</c:forEach>
								</c:when>
	   							<c:otherwise> 
	   								<tr>
	   									<td colspan="19"><p style="margin:30px 0;" align="center">当前页面暂无记录</p></td>
	   								</tr>
	   							</c:otherwise>
	   						</c:choose>
							
						</tbody>
					</table>
				</div>
			</div>
		</c:otherwise> 
	</c:choose>
</body>
</html>


