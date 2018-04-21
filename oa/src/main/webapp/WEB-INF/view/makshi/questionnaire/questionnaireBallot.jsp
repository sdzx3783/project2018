
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>投票</title>
<%@include file="/codegen/include/customForm.jsp"%>
<%@include file="/commons/include/ueditor.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript">
	$(function() {
		var options = {};
		if (showResponse) {
			options.success = showResponse;
		}
		var frm = $('#questionnaireForm').form();
		$("#dataFormSave").click(function() {
			frm.ajaxForm(options);
			$("#saveData").val(1);
			if (frm.valid()) {
				//如果有编辑器的情况下
				$("textarea[name^='m:'].myeditor").each(function(num) {
					var name = $(this).attr("name");
					var data = getEditorData(editor[num]);
					$("textarea[name^='" + name + "']").val(data);
				});
				var f = false;
				$(".questionTr").each(function(){
					var me = $(this);
					var flag =false;
					var num = $("input[name='max_choice']",me).val();
					if($("input[name='required']",me).val()=='true'){
						var i =0;
						$("input[name^='result']",me).each(function(){
							if($(this).attr('checked')=='checked'){
								flag=true;
								i++;
							}else if($(this).attr('type')=='text'){
								flag=true;
								i++;
							}
						});
						if(!flag){
							$.ligerDialog.warn($("input[name='title']",me).val()+'必选题','提示信息');
							f=true;
							return false;
						}
						if(num!='' && i>num){
							$.ligerDialog.warn($("input[name='title']",me).val()+'最多选择数量：'+num,'提示信息');
							f=true;
							return false;
						}
					}
				});
				
				if(f){
					return false;
				}
				
				if (WebSignPlugin.hasWebSignField) {
					WebSignPlugin.submit();
				}
				if (OfficePlugin.officeObjs.length > 0) {
					OfficePlugin.submit(function() {
						frm.handleFieldName();
						$('#detailList').val(getDetailList());
						$('#questionnaireForm').submit();
					});
				} else {
					frm.handleFieldName();
					$('#detailList').val(getDetailList());
					$('#questionnaireForm').submit();
				}
				
			}
		});
	});

	function showResponse(responseText) {
		var obj = new com.hotent.form.ResultMessage(responseText);
		if (obj.isSuccess()) {
			$.ligerDialog
					.confirm(
							obj.getMessage() + ",是否继续操作",
							"提示信息",
							function(rtn) {
								if (rtn) {
									window.location.href = window.location.href;
								} else {
									window.location.href = "${ctx}/makshi/questionnaire/questionnaire/list.ht";
								}
							});
		} else {
			$.ligerDialog.error(obj.getMessage(), "提示信息");
		}
	}
	
	</script>
<style>
	.panel-toolbar{
		margin-top: 0;
	}
	#questionnaireForm{
		margin: 0 11px;
	}
	

	.group .btn, .btn{
		display: inline-block;
		color: #fff;
		line-height: 26px;
		padding: 0 15px;
		background: #478de4;
		border-radius: 3px;
		-moz-box-shadow: 0 1px 1px rgba(0,0,0,0.15);
		-webkit-box-shadow: 0 1px 1px rgba(0,0,0,0.15);
		box-shadow: 0 1px 1px rgba(0,0,0,0.15);
	}
	.icon-tick,
	.icon-arrow-back{
		font-size: 16px;
		vertical-align: middle;
	}
	.text{
		vertical-align: middle;
		margin: 0 5px;
	}

	table.formTable{
		font-size: 14px;
		margin-bottom: 80px;
	}
	table.formTable td{
		font-size: 14px;
	}
	.questionTable{
		border-collapse: collapse;
    	width: 100%;
	}

	.questionTable td,
	.questionTable th{
		border: 1px solid #dadfed;
	
	}
	.questionTable td{
		color: #657386;
	}

	.questionTable th{
		font-size: 16px;
		font-weight: bold;
	}
				ol li{
				list-style: upper-latin;
			}
	.questionTable input,
	.questionTable label span{
		vertical-align: middle;
		font-size: 14px;
	}

	.questionTable label input{
		margin: 0;
	}

	.quest-list .quest-item{
	}
	.quest-state{
		margin-left: 3px;
		font-size: 14px;
	}

	.formTable{
		table-layout: fixed;
	}
	.formTable td{
			padding: 15px !important;
		}
	.percentum{
		position: relative;
		display: inline-block;
		text-align: center;
		width: 250px;
		border: 1px solid #96d7f8;
		line-height: 25px;
		color: #333;
	}
	.percentum-inner{
		position: absolute;
		left: 0;
		 top: 0;
		height: 25px;
		background: #96d7f8;
		z-index: -1;
	}
	.num{
		display: inline-block;
		width: 30px;
	}
	.questionTable td, .questionTable th {
    border: 1px solid #dadfed;
    padding: 15px !important;
}
.quest-list {
    padding-left: 20px;
}
.quest-list .quest-item {
    padding: 5px;
}
</style>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">投票</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="btn" href="list.ht"><span class="icon-arrow-back"></span><span class="text">返回</span></a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<form id="questionnaireForm" method="post" action="vote.ht">
		<div type="custform">
			<div class="panel-detail">
				<table cellpadding="2" cellspacing="0" border="1" class="formTable"
					parser="addpermission">

						<tr>
							<td class="formInput" style="width: 100px; text-align: right; "
								style="border-color: rgb(102, 102, 102); text-align: right; word-break: break-all;"
								><span>问卷调查</span></td>
							<td class="formInput"
								style="border-color: rgb(102, 102, 102);     color: #657386; word-break: break-all;"
								><span name="editable-input"
								style="display: inline-block;" isflag="tableflag">
									${questionnaire.title} </span></td>
						</tr>
						<tr>
							<td class="formInput"
								style="border-color: rgb(102, 102, 102);  text-align: right;word-break: break-all;"
								><span>描述</span></td>
							<td class="formInput"
								style="border-color: rgb(102, 102, 102);    color: #657386; word-break: break-all;"
								><span name="editable-input"
								style="display: inline-block;" isflag="tableflag">
									${questionnaire.desc} </span></td>
						</tr>

						<tr>
							<td class="formInput"
								style="border-color: rgb(102, 102, 102); text-align: right; word-break: break-all;"
								><span>截止时间</span></td>
							<td class="formInput"
								style="border-color: rgb(102, 102, 102);    color: #657386; word-break: break-all;"
								><span name="editable-input"
								style="display: inline-block;" isflag="tableflag">
									<fmt:formatDate value="${questionnaire.end_date}" pattern="yyyy-MM-dd HH:mm:ss"/> </span></td>
						</tr>

				</table>

						<table class="questionTable">
						<c:forEach items="${questionnaire.questionnaireQuestionList }"
							var="item" varStatus="i">
							
							<tr>
								<th>问题${i.index+1 }、${item.title } <c:if test="${item.max_choice !=null}">（最多可选${item.max_choice }项）</c:if></th>
								</td>
							</tr>
							<tr  class="questionTr">
								<td>
									<ol class="quest-list">
									<input type="hidden" name="questionnaire_result.questionnaire_ques_id" value="${item.id}">
								<input type="hidden" name="questionnaire_result.questionnaire_id" value="${item.questionnaire_id}">
								<input type="hidden" name="required" value="${item.required}">
								<input type="hidden" name="title" value="${item.title }">
								<input type="hidden" name="max_choice" value="${item.max_choice }">
									<c:if test="${item.type ==0}">
										<c:forEach items="${item.questionnaireOptionList }" var="item2" varStatus="j">
											<li class="quest-item" onclick="checkChoice('result${item.id}','${item.max_choice }')">
												<label>
													<c:choose>
														<c:when test="${item.checkbox}">
															<input class="questionnaire_result" name="result${item.id}" <c:if test="${questionnaire.status!=1}">disabled="disabled"</c:if> type="checkbox" value="${item2.id}">
														</c:when>
														<c:otherwise>
															<input class="questionnaire_result"  name="result${item.id}" <c:if test="${questionnaire.status!=1}">disabled="disabled"</c:if> type="radio" value="${item2.id}">
														</c:otherwise>
													</c:choose>
													<span class="title" title="${item2.desc}">${item2.desc}</span>
													<c:if test="${hasVote }">
														<span class="num">${item2.count}人</span>
														<div class="percentum"><div class="percentum-inner" style="width: ${item2.percentum}%;"></div>${item2.percentum}%</div>
													</c:if>
												</label>
											</li>
										</c:forEach>
									</c:if>
									
									<c:if test="${item.type ==1}">
										<c:if test="${!hasVote }">
											<input name="result${item.id}"   type="text"  />
										</c:if>
										<c:if test="${hasVote }">
											<c:forEach items="${item.resultList }" var="result" >
												${result.result}<br/>
											</c:forEach>
										</c:if>
									</c:if>
									</ol>
								</td>
							</tr>
							
						</c:forEach>
						</table>

			</div>
		</div>
		
		<input type="hidden" name="id" value="${questionnaire.id}" /> <input
			type="hidden" id="saveData" name="saveData" /> <input type="hidden"
			name="executeType" value="start" /> 
			<textarea style="display: none" id="detailList" name="detailList"></textarea>
			<div class="quest-state">
				<c:choose>
					<c:when test="${questionnaire.status==0}">未开始</c:when>
					<c:when test="${questionnaire.status==1}">
					<a class="btn" style="margin-left: 5px; margin-bottom: 20px;" id="dataFormSave" href="javascript:;"><span class="icon-tick"></span><span class="text">提交</span></a></c:when>
					<c:when test="${questionnaire.status==2}">已投票</c:when>
					<c:when test="${questionnaire.status==3}">已结束</c:when>
				</c:choose>
			</div>
	</form>
<script type="text/javascript">
function getDetailList(){
	var json =  [];
	$(".questionTr").each(function(){
		var me = $(this),obj={},result=[];
		$("input[name^='result']",me).each(function(){
			if($(this).attr('checked')=='checked'){
				result.push($(this).val());
			}else if($(this).attr('type')=="text"){
				result.push($(this).val());
			}
		});
		obj.result =result;
		obj.questionnaire_id =$("input[name='questionnaire_result.questionnaire_id']",me).val();
		obj.questionnaire_ques_id = $("input[name='questionnaire_result.questionnaire_ques_id']",me).val();
		
		json.push(obj);
	});
	return JSON2.stringify(json)
}
function checkChoice(name,count){
	if(count!=''){
		$("input[name='"+name+"']").attr("disabled",false);
		var $aryId = $("input[name='"+name+"']:checked");
		var len=$aryId.length;
		if(len>=count){
			 $("input[name='"+name+"']").each(function(i){
				var obj=$(this);
				if(obj.attr("checked")!='checked'){
					obj.attr("disabled",true);
				}
			});
			return false;
		}
	}
}

</script>
</body>
</html>

