<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@page import="com.hotent.core.api.util.PropertyUtil"%>
<html>
<head>
	<title>编辑 公告</title>
	<%@include file="/commons/include/form.jsp"%>
	<f:link href="form.css" ></f:link>
	
	<link href="${ctx}/styles/default/css/hotent/dataRights.css" rel="stylesheet" />
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js" ></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/HtmlUploadDialog.js" ></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor1433/ueditor_default.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor1433/ueditor.all.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor1433/lang/zh-cn/zh-cn.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/js/ueditor1433/themes/default/css/ueditor.css"/>
    <script type="text/javascript" charset="utf-8" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctx}/weixin/assets/js/handlebars.min.js"></script>
    
	<script type="text/javascript">
	var bulletinUeditor;
	
		$(function() {
			var bulletinUeditor = new baidu.editor.ui.Editor({minFrameHeight:300,initialFrameWidth:'100%',lang:'zh_cn'});
			bulletinUeditor.render("txtHtml"); 
		    var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#sysBulletinForm').form();
			$("a.save").click(function() {
				frm.ajaxForm(options);
				if(frm.valid()){
					$('#content').val(bulletinUeditor.getContent());
					$('#sysBulletinForm').submit();
				}
			});
			AttachMent.init("w");
			$("#templateList").change(function(){
				var temId = $(this).val();
				if(temId=="1"){
					return;
				}
				var temContent = bulletinUeditor.getContent();
				var url= __ctx + "/platform/system/sysBulletinTemplate/selector.ht?id="+temId;
				$.get(url, function(data) {
					var htmldata =data.sysbulletintemplate.template;
					var content = temContent+htmldata;
					bulletinUeditor.setContent(content);
				});
			});
			
			$("#sendWxInfo").click(function(){
				$("#trUser").toggle();
			});
			
			$("input[name='selectUser']").click(function(){
				var val=$(this).val();
				if(val==0){
					$("#usersContainer").hide();
				}
				else{
					$("#usersContainer").show();
				}
			});
			
			$("#btnSelectImg").click(function(){
				HtmlUploadDialog({max:1,callback:function(data){
					if(data.length==1){
						var fileId=data[0].fileId;
						var url=__ctx +"/platform/system/sysFile/file_"+fileId+".ht"
						$("#imgUrl").val(fileId);
						$("#columnImg").attr("src",url)
					}
				}});
			});
			
		});
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerDialog.confirm(obj.getMessage()+",是否继续操作","提示信息", function(rtn) {
					if(rtn){
						window.location.href = window.location.href;
					}else{
						window.location.href = "${returnUrl}";
					}
				});
			} else {
				$.ligerDialog.err("提示信息","发布公告出错!",obj.getMessage());
			}
		}
		 function reset(obj) {
	        	$("input[name='subject']").val("");
	        	bulletinUeditor.setContent("");
			}
		 
		 function selectUser(){
			 UserDialog({callback:function(userIds,fullnames,emails,mobiles,rtn){
				 var aryAccount=rtn.accounts.split(",");
				 var aryFullname=rtn.fullnames.split(",");
				 var aryObj=[];
				 for(var i=0;i<aryAccount.length;i++){
					 var obj={};
					 obj.account=aryAccount[i];
					 obj.name=aryFullname[i];
					 aryObj.push(obj);
				 }
				 var compiler = Handlebars.compile($('#tpi-list-item').html());
				 var html=compiler(aryObj);
				 $("#spanUsers").html(html);
			 }});
		 }
		 
		 function removeUser(obj){
			 var o=$(obj).parent();
			 o.remove();
		 }
	</script>
	
	<script type="text/x-handlebars-template" id="tpi-list-item">
  		{{#each this}}
			<span class="owner-span" >{{name}}
					<a class="flootbutton" title="移除该项" onclick="removeUser(this)">x</a>
					<input type="hidden" name="account" value="{{account}}"/>
			</span>
  		{{/each}}
	</script>
</head>
<body>
<div class="panel">
	<c:if test="${!isAddByFlow}">
	<div class="panel-top">
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:;"><span></span>保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="list.ht"><span></span>返回</a>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link undo" onclick="reset(this)"><span></span>重置</a></div>
				<div class="l-bar-separator"></div>
				</div>
			</div>
		</div>
	</div>
	</c:if>
	<div class="panel-body">
	<c:if test="${!isAddByFlow}">
	<form id="sysBulletinForm" method="post" action="save.ht">
	</c:if>
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
				<tbody>
					<tr>
						<th width="20%">主题:<span class="required red">*</span></th>
						<td>
							<input type="text" name="subject" value="${sysBulletin.subject}" validate="{required:true,maxlength:128}" class="inputText" style="width: 50%;"  />
						</td>
					</tr>
					<tr>
						<th width="15%">所属栏目: </th>
						<td>
							<select name="columnid" id="columnid" class="inputText" style="width: 250px;">
								<c:forEach items="${columnList}" var="columnList">
									<option value="${columnList.id}" <c:if test="${sysBulletin.columnid==columnList.id}">selected="selected"</c:if> >${columnList.name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th width="20%">标题图:</th>
						<td>
							<input type="hidden" id="imgUrl" name="imgUrl" value="${sysBulletin.imgUrl}">
							<c:choose>
								<c:when test="${empty sysBulletin.imgUrl}">
									<img id="columnImg" alt="" src="">
								</c:when>
								<c:otherwise>
									<img id="columnImg" alt=""  src="${ctx }/platform/system/sysFile/file_${sysBulletin.imgUrl}.ht">
								</c:otherwise>
							</c:choose>
							
							<input id="btnSelectImg" type="button" value="选择">
						</td>
					</tr>
					<tr>
						<th width="15%">附件: </th>
						<td>
							<div name="div_attachment_container">
								<div class="attachement"></div>
								<textarea style="display: none" controltype="attachment"
									id="attachment" name="attachment" lablename="主表附件" validate="{}">${sysBulletin.attachment}</textarea>
								<a href="javascript:;" field="attachment" class="link selectFile"
									atype="select" onclick="AttachMent.addFile(this);">选择</a>
							</div> 
						</td>
					</tr>
					<tr>
						<th width="15%">公告模板: </th>
						<td>
							<select name="templateList" id="templateList" class="inputText" style="width: 250px;">
									<option value="1" selected="selected">---请选择---</option>
								<c:forEach items="${templateList}" var="templateList">
									<option value="${templateList.id}" >${templateList.name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right" nowrap="nowarp" style="width: 20%;"
							class="formTitle">内容</td>
						<td class="formInput">
						<div id="editor" position="center"
								style="overflow: hidden; height: 100%;">
								<textarea id="txtHtml" name="html">${sysBulletin.content}</textarea>
								<textarea id="content" name="content" style="display:none;"></textarea>
							</div>
						</td>
					</tr>
					<c:if test="${isSupportWeixin}">
						<tr>
							<td align="right" nowrap="nowarp" style="width: 20%;"
								class="formTitle">发送微信通知</td>
							<td class="formInput">
								<input type="checkbox" value="1" name="sendWxInfo" id="sendWxInfo">
							</td>
						</tr>
						<c:if test="${canSelect}">
							<tr id="trUser" style="display: none;">
								<td align="right" nowrap="nowarp" style="width: 20%;"
									class="formTitle">选择人员</td>
								<td class="formInput">
									<input type="radio" name="selectUser" value="0" id="selectUserAll" checked="checked" >
									<label for="selectUserAll" >所有人</label>
									<input type="radio" name="selectUser" value="1" id="selectUserSelect">
									<label for="selectUserSelect" >选择人员</label>
									
									<span id="usersContainer" style="display: none;">
										<span id="spanUsers"  class="ht-input" style="width: initial;max-width: 260px;"></span>
										<a href="javascript:;" onclick="selectUser()" >选择</a>
									</span>
								</td>
							</tr>
						</c:if>
					</c:if>
					
				</tbody>
			</table>
			
			<input type="hidden" name="id" value="${sysBulletin.id}"/>
		<c:if test="${!isAddByFlow}">
		</form>
		</c:if>
	</div>
</body>
</html>
