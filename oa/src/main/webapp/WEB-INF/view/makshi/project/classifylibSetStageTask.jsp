<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>管理阶段任务</title>
	<%@include file="/commons/include/get.jsp"%>
	<%@include file="/codegen/include/customForm.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#stagelibForm').form();
			$("#dataFormSave").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(1);
				if(frm.valid()){
					//如果有编辑器的情况下
					$("textarea[name^='m:'].myeditor").each(function(num) {
						var name=$(this).attr("name");
						var data=getEditorData(editor[num]);
						$("textarea[name^='"+name+"']").val(data);
					});
					
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					if(OfficePlugin.officeObjs.length>0){
						OfficePlugin.submit(function(){
							frm.handleFieldName();
							$('#stagelibForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#stagelibForm').submit();
					}
				}
			});
		});
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				window.opener.location.reload(true);
				window.close();
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
			
		}
		
		function back(){
			window.close();
		}
		
		function addPre(){
			$.ligerDialog.open({ url: 'stageTaskSelector.ht?classifystagetaskid=${classifyStageTask.id}&classifystageid=${classifyStage.id}&selectType=0',title:'前置节点',width:600,height: 600, isResize:true});
		}
		function addAfter(){
			$.ligerDialog.open({ url: 'stageTaskSelector.ht?classifystagetaskid=${classifyStageTask.id}&classifystageid=${classifyStage.id}&selectType=1',title:'后置节点',width:600,height: 600, isResize:true});
		}
		
		function addQuery(){
			$.ligerDialog.open({ url: 'stageTaskSelector.ht?classifystagetaskid=${classifyStageTask.id}&classifystageid=${classifyStage.id}&selectType=2',title:'可查看节点',width:600,height: 600, isResize:true});
		}
		
		function addPreNode(jsonArr){
			
			if(jsonArr && jsonArr.length>0){
				for(var i=0;i<jsonArr.length;i++){
					apendPreNode(jsonArr[i]);
				}
			}
			$(".l-dialog-frame").css("display","none");
		}
		
		function addAfterNode(jsonArr){
			
			if(jsonArr && jsonArr.length>0){
				for(var i=0;i<jsonArr.length;i++){
					apendAfterNode(jsonArr[i]);
				}
			}
			$(".l-dialog-frame").css("display","none");
		}
		function addQueryNode(jsonArr){
			
			if(jsonArr && jsonArr.length>0){
				for(var i=0;i<jsonArr.length;i++){
					apendQueryNode(jsonArr[i]);
				}
			}
			$(".l-dialog-frame").css("display","none");
		}
		
		function apendQueryNode(obj){
			var querys=$(".querynode");
			
			var query_head=$(".querynode_head").eq(0);
			var html='<tr class="querynode"><td>templateseq</td><td>'+obj.taskname+'</td>'+
			'<td class="taskno">'+obj.taskno+'<input name="querystagetaskid" type="hidden" value="'+obj.taskid+'"></td>'
			+'<td><span onclick="removePreNode(this)">删除</span></td></tr>'
			;
			var flag=true;
			
			if(querys && querys.length>0){
				querys.each(function(){
					var taskno=$(this).find("td.taskno").text();
					if($.trim(taskno)==obj.taskno){
						flag=false;
					}
				});
				if(flag){
					var seq= $(querys[querys.length-1]).find("td:first-child").text();
					html=html.replace("templateseq",(parseInt(seq)+1)+"");
					$(querys[querys.length-1]).after(html);
				}
			}else{
				html=html.replace("templateseq",'1');
				query_head.after(html);
			}
		}
		
		function apendPreNode(obj){
			var pres=$(".prenode");
			var afters=$(".afternode");
			var pre_head=$(".prenode_head").eq(0);
			var html='<tr class="prenode"><td>templateseq</td><td>'+obj.taskname+'</td>'+
			'<td class="taskno">'+obj.taskno+'<input name="prestagetaskid" type="hidden" value="'+obj.taskid+'"></td>'
			+'<td><span onclick="removePreNode(this)">删除</span></td></tr>'
			;
			var flag=true;
			if(afters && afters.length>0){
				afters.each(function(){
					var taskno=$(this).find("td.taskno").text();
					if($.trim(taskno)==obj.taskno){
						flag=false;
					}
				});
			};
			if(!flag){
				return ;
			}
			if(pres && pres.length>0){
				pres.each(function(){
					var taskno=$(this).find("td.taskno").text();
					if($.trim(taskno)==obj.taskno){
						flag=false;
					}
				});
				if(flag){
					var seq= $(pres[pres.length-1]).find("td:first-child").text();
					html=html.replace("templateseq",(parseInt(seq)+1)+"");
					$(pres[pres.length-1]).after(html);
				}
			}else{
				html=html.replace("templateseq",'1');
				pre_head.after(html);
			}
		}
		
		function apendAfterNode(obj){
			var afters=$(".afternode");
			var pres=$(".prenode");
			var after_head=$(".afternode_head").eq(0);
			var html='<tr class="afternode"><td>templateseq</td><td>'+obj.taskname+'</td>'+
			'<td class="taskno">'+obj.taskno+'<input name="afterstagetaskid" type="hidden" value="'+obj.taskid+'"></td>'
			+'<td><span onclick="removeAfterNode(this)">删除</span></td></tr>'
			;
			var flag=true;
			if(pres && pres.length>0){
				pres.each(function(){
					var taskno=$(this).find("td.taskno").text();
					if($.trim(taskno)==obj.taskno){
						flag=false;
					}
				});
			}
			if(!flag){
				return ;
			}
			if(afters && afters.length>0){
				afters.each(function(){
					var taskno=$(this).find("td.taskno").text();
					if($.trim(taskno)==obj.taskno){
						flag=false;
					}
				});
				if(flag){
					var seq= $(afters[afters.length-1]).find("td:first-child").text();
					html=html.replace("templateseq",(parseInt(seq)+1)+"");
					$(afters[afters.length-1]).after(html);
				}
			}else{
				html=html.replace("templateseq",'1');
				after_head.after(html);
			}
		}
		
		
		
		function removePreNode(obj){
			var remtr=$(obj).parent().parent();
			var next=remtr.nextAll(".prenode");
			if(next && next.length>0){
				next.each(function(i){
					var i=$(this).find("td:first-child").text();
					$(this).find("td:first-child").text(i-1);
				});
			}
			remtr.remove();
		}
		
		function removeAfterNode(obj){
			var remtr=$(obj).parent().parent();
			var next=remtr.nextAll(".afternode");
			if(next && next.length>0){
				next.each(function(i){
					var i=$(this).find("td:first-child").text();
					$(this).find("td:first-child").text(i-1);
				});
			}
			remtr.remove();
		}
		
		function removeQueryNode(obj){
			var remtr=$(obj).parent().parent();
			var next=remtr.nextAll(".querynode");
			if(next && next.length>0){
				next.each(function(i){
					var i=$(this).find("td:first-child").text();
					$(this).find("td:first-child").text(i-1);
				});
			}
			remtr.remove();
		}
	</script>
	<style>
	.oa-table th{
		padding: 10px 0;
	}
	.oa-table td{
		padding: 10px 20px;
	}
	</style>
</head>
<body>
	<div class="oa-top-wrap">
		<button type="button" class="oa-button oa-button--primary oa-button--blue" id="dataFormSave">保存</button>
		<button type="button" class="oa-button oa-button--primary oa-button--blue" onclick="back();">返回</button>
	</div>
	
	<div class="oa-main-wrap">
		<form id="stagelibForm" method="post" action="classifyStageTaskSave.ht">
			<table class="oa-table">
				<tr>
					<th>项目分类名: </th>
					<td>${classifyLib.name }</td>
				</tr>
				<tr>
					<th>阶段名称: </th>
					<td>${classifyStage.stagename}</td>
				</tr>
				<tr>
					<th>任务名称: </th>
					<td>
						<c:choose>
							<c:when test="${1!=classifyLib.isused}">
								<div class="oa-input-wrap">
									<input name="taskname" class="oa-input" type="text" validate="{'required':true,'maxlength':150}"  value="${classifyStageTask.taskname}"  />
								</div>
							</c:when>
							<c:otherwise>
								<div class="oa-input-wrap">
									<input name="taskname" readonly="readonly" class="oa-input" type="text" validate="{'required':true,'maxlength':150}"  value="${classifyStageTask.taskname}"  />
								</div>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				
				<tr>
					<th>可查看节点</th>
					<td><c:if test="${1!=classifyLib.isused}"><span onclick="addQuery()">+添加</span></c:if></td>
				</tr>
				<tr class="querynode_head">
					<th>序号</th>
					<td>任务名称</td>
					<td>任务编号</td>
				</tr>
				<c:forEach items="${querystagetaskList }" var="querystagetask" varStatus="vs">
					<tr class="querynode">
						<td>${vs.index+1 }</td>
						<td>${querystagetask.taskname }</td>
						<td class="taskno">${querystagetask.taskno } <input name="querystagetaskid" type="hidden" value="${querystagetask.id }"></td>
						<td><c:if test="${1!=classifyLib.isused}"><span onclick="removeQueryNode(this)">删除</span></c:if></td>
					</tr>
				</c:forEach>
				<tr>
					<th>前驱节点</th>
					<td><c:if test="${1!=classifyLib.isused}"><span onclick="addPre()">+添加</span></c:if></td>
				</tr>
				<tr class="prenode_head">
					<th>序号</th>
					<td>任务名称</td>
					<td>任务编号</td>
				</tr>
				<c:forEach items="${prestagetaskList }" var="prestagetask" varStatus="vs">
					<tr class="prenode">
						<td>${vs.index+1 }</td>
						<td>${prestagetask.taskname }</td>
						<td class="taskno">${prestagetask.taskno } <input name="prestagetaskid" type="hidden" value="${prestagetask.id }"></td>
						<td><c:if test="${1!=classifyLib.isused}"><span onclick="removePreNode(this)">删除</span></c:if></td>
					</tr>
				</c:forEach>
				<tr>
					<th>后驱节点</th>
					<td><c:if test="${1!=classifyLib.isused}"><span onclick="addAfter()">+添加</span></c:if></td>
				</tr>
				<tr class="afternode_head">
					<th>序号</th>
					<td>任务名称</td>
					<td>任务编号</td>
				</tr>
				<c:forEach items="${afterstagetaskList }" var="afterstagetask" varStatus="vs">
					<tr class="afternode">
						<td>${vs.index+1 }</td>
						<td>${afterstagetask.taskname }</td>
						<td class="taskno">${afterstagetask.taskno } <input name="afterstagetaskid" type="hidden" value="${afterstagetask.id }"></td>
						
						<td><c:if test="${1!=classifyLib.isused}"><span onclick="removeAfterNode(this)">删除</span></c:if></td>
					</tr>
				</c:forEach>
			</table>
			
			<input type="hidden" id="saveData" name="saveData"/>
			<input type="hidden" id="id" name="id" value="${classifyStageTask.id }"/>
			<input type="hidden" id="classifystageid" name="classifystageid" value="${classifyStage.id }"/>
			<input type="hidden" id="classifylibid" name="classifylibid" value="${classifyLib.id }"/>
		</form>

	</div>

</body>
</html>
