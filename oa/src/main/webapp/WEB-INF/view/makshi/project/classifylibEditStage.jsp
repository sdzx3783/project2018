<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>管理阶段</title>
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
					
					var stagetype=$("#stagetype").val();
					if(stagetype==1){
						$(".prenode").remove();
						
					}else if(stagetype==2){
						
					}else if(stagetype==3){
						$(".afternode").remove();
					}
					
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
			
			$("#stagetype").change(function(){
				
				var stagetype=$(this).val();
				if(stagetype==1){
					$(".prenode_add,.prenode_head,.prenode").hide();
					$(".afternode_add,.afternode_head,.afternode").show();
				}else if(stagetype==2){
					$(".prenode_add,.prenode_head,.prenode").show();
					$(".afternode_add,.afternode_head,.afternode").show();
				}else if(stagetype==3){
					$(".prenode_add,.prenode_head,.prenode").show();
					$(".afternode_add,.afternode_head,.afternode").hide();
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
			$.ligerDialog.open({ url: 'stageSelector.ht?classifyid=${classifyLib.id}&classifystageid=${classifyStage.id}&selectType=0',title:'前置节点',width:600,height: 600, isResize:true});
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
		
		function apendPreNode(obj){
			var pres=$(".prenode");
			var afters=$(".afternode");
			var pre_head=$(".prenode_head").eq(0);
			var html='<tr class="prenode"><td>templateseq</td><td>'+obj.stagename+'</td>'+
			'<td class="stageno">'+obj.stageno+'<input name="prestageid" type="hidden" value="'+obj.stageid+'"></td>'
			+'<td><span onclick="removePreNode(this)">删除</span></td></tr>'
			;
			var flag=true;
			if(!afters.is(":hidden") && afters && afters.length>0){
				afters.each(function(){
					var stageno=$(this).find("td.stageno").text();
					if($.trim(stageno)==obj.stageno){
						flag=false;
					}
				});
			};
			if(!flag){
				return ;
			}
			if(pres && pres.length>0){
				pres.each(function(){
					var stageno=$(this).find("td.stageno").text();
					if($.trim(stageno)==obj.stageno){
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
			var html='<tr class="afternode"><td>templateseq</td><td>'+obj.stagename+'</td>'+
			'<td class="stageno">'+obj.stageno+'<input name="afterstageid" type="hidden" value="'+obj.stageid+'"></td>'
			+'<td><span onclick="removeAfterNode(this)">删除</span></td></tr>'
			;
			var flag=true;
			if(!pres.is(":hidden") && pres && pres.length>0){
				pres.each(function(){
					var stageno=$(this).find("td.stageno").text();
					if($.trim(stageno)==obj.stageno){
						flag=false;
					}
				});
			}
			if(!flag){
				return ;
			}
			if(afters && afters.length>0){
				afters.each(function(){
					var stageno=$(this).find("td.stageno").text();
					if($.trim(stageno)==obj.stageno){
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
		
		function addAfter(){
			$.ligerDialog.open({ url: 'stageSelector.ht?classifyid=${classifyLib.id}&classifystageid=${classifyStage.id}&selectType=1',title:'后置节点',width:600,height: 600, isResize:true});
		}
		
		function stagetask(id){
			
			window.open('stageTaskList.ht?classifystageid='+id+'&classifylibid=${classifyLib.id }', 'stageTaskList','height=500, width=900, top=200, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');
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
		
		
		
		/* function confirmdel(relationstageid){
			$.ligerDialog.confirm('确认删除吗？','提示信息',function(rtn) {
				if(rtn) {
					 
					var url="delClassifyStageRelation.ht?stageid=${classifyStage.id}&relationstageid="+relationstageid;
					window.location.href=url;
				}else{
					return false;
				}
			});
		} */
	</script>
	<style>
		.oa-table td{
			padding: 10px 20px;
		}

		.oa-select-wrap{
			display: inline-block;
		}
	</style>
</head>
<body>
	<div class="oa-pd20">
		<button class="oa-button oa-button--primary oa-button--blue" id="dataFormSave">保存</button>
		<button class="oa-button oa-button--primary oa-button--blue" onclick="back();">取消</button>
	</div>

	<div class="oa-mgh20 oa-mgb10">
		<form id="stagelibForm" method="post" action="classifyStageSave.ht">
			<table class="oa-table">
				<tr>
					<th>项目分类名: </th>
					<td>${classifyLib.name }</td>
				</tr>
				<tr>
					<th>阶段名称: </th>
					<td>
						<div class="oa-input-wrap">
							<input name="stagename" class="oa-input" type="text" validate="{'required':true,'maxlength':150}" <c:if test="${classifyLib.isused==1 }">disabled="disabled"</c:if> value="${classifyStage.stagename}"  />
						</div>
					</td>
				</tr>
				<tr>
					<th>阶段类型: </th>
					<td>
						<div class="oa-select-wrap">
							<select class="oa-select" name="stagetype" id="stagetype" <c:if test="${classifyLib.isused==1 }">disabled="disabled"</c:if>>
								<option value="1" <c:if test="${classifyStage.stagetype==1 }">selected=selected</c:if>>起始阶段</option>
								<option value="2" <c:if test="${classifyStage.stagetype==2 }">selected=selected</c:if>>中间阶段</option>
								<option value="3" <c:if test="${classifyStage.stagetype==3 }">selected=selected</c:if>>结束阶段</option>
							</select>
						</div>
						<button type="button" class="oa-button-label" onclick="stagetask(${classifyStage.id})">阶段任务</button>
					</td>
				</tr>
			</table>


			<table class="oa-table">
				<tr class="prenode_add" <c:if test="${classifyStage.stagetype==1 }"> style="display:none"</c:if>>
					<th>前驱节点：</th>
					<td><c:if test="${classifyLib.isused!=1 }"><span onclick="addPre()">+添加</span></c:if></td>
				</tr>
				<tr class="prenode_head" <c:if test="${classifyStage.stagetype==1 }"> style="display:none"</c:if>>
					<th>序号</th>
					<td>阶段名称</td>
					<td>阶段编号</td>
				</tr>
				 <c:if test="${classifyStage.stagetype!=1 }">
					<c:forEach items="${prestageList }" var="prestage" varStatus="vs">
						<tr class="prenode">
							<td>${vs.index+1}</td>
							<td>${prestage.stagename}</td>
							<td class="stageno">${prestage.stageno}<input name="prestageid" type="hidden" value="${prestage.id }"></td>
							<td><c:if test="${classifyLib.isused!=1 }"><span onclick="removePreNode(this)">删除</span></c:if></td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
			
			<table class="oa-table">
				<tr class="afternode_add" <c:if test="${classifyStage.stagetype==3 }"> style="display:none"</c:if>>
					<th>后驱节点：</th>
					<td><c:if test="${classifyLib.isused!=1 }"><span onclick="addAfter()">+添加</span></c:if></td>
				</tr>
				<tr class="afternode_head" <c:if test="${classifyStage.stagetype==3 }"> style="display:none"</c:if>>
					<th>序号：</th>
					<td>阶段名称</td>
					<td>阶段编号</td>
				</tr>
				<c:if test="${classifyStage.stagetype!=3 }">
					<c:forEach items="${afterstageList }" var="afterstage" varStatus="vs">
						<tr class="afternode">
							<th>${vs.index+1}</th>
							<td>${afterstage.stagename}</td>
							<td class="stageno">${afterstage.stageno}<input name="afterstageid" type="hidden" value="${afterstage.id }"></td>
							
							<td><c:if test="${classifyLib.isused!=1 }"><span onclick="removeAfterNode(this)">删除</span></c:if></td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
			
			<input type="hidden" id="saveData" name="saveData"/>
			<input type="hidden" id="id" name="id" value="${classifyStage.id }"/>
			<input type="hidden" id="classifyId" name="classifyId" value="${classifyLib.id }"/>
		</form>
	</div>

</body>
</html>
