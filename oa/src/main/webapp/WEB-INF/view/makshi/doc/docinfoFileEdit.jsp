<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 文档</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/HtmlUploadDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor1433/ueditor_default.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor1433/ueditor.all.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor1433/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor1433/extend/picture.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor1433/themes/default/css/ueditor.css"></script>
	<script type="text/javascript">
		$(function() {
			var messageEditor = new baidu.editor.ui.Editor({minFrameHeight:1300,initialFrameWidth: '100%',lang:'zh_cn'});
			messageEditor.render("txtHtml");
			function showRequest(formData, jqForm, options) { 
				return true;
			} 
			AttachMent.init("w");
			
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#docinfoForm').form();
			$("a.save").click(function() {
				frm.ajaxForm(options);
				$("#type").val("");
				$("#saveData").val(1);
				$('#content').val(messageEditor.getContent());
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
							$('#docinfoForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#docinfoForm').submit();
					}
				}
			});
			
			$("a.preview").click(function(){
				$("#type").val("preview");
				//frm.ajaxForm(options);
				$("#saveData").val(1);
				$('#content').val(messageEditor.getContent());
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
							$('#docinfoForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#docinfoForm').submit();
					}
				}
			})
		});
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerDialog.confirm(obj.getMessage()+",是否继续操作","提示信息", function(rtn) {
					if(rtn){
						//alert(window.location.href.length);
						if(window.location.href.indexOf("&returnUrl")<0){
							window.location.href = window.location.href+"&returnUrl="+encodeURIComponent("${returnUrl}");
						}else{
							window.location.href=window.location.href;
						}
					}else{
						var rurl='${returnUrl}';
						var idx=rurl.indexOf("&lastReturnUrl=");
						if(idx>=0){
							lurl=rurl.substring(idx+15);
							purl=rurl.substring(0,idx+1);
							if(lurl.indexOf("%")>=0){//已经被编码了
								
								window.location.href=purl+"lastReturnUrl="+lurl;
							}else{
								window.location.href=purl+"lastReturnUrl="+encodeURIComponent(lurl);
							}
						}else{
							window.location.href=rurl;
						}
						/* if(returnUrl.indexOf("/doc/docinfo/filedetail.ht")>=0){
							window.location.href=returnUrl;
						}else{
							var docId= $("#docid").val();
							window.location.href = "${ctx}/makshi/doc/docinfo/filelist.ht?docId="+docId;
						} 
						window.location.href = '${returnUrl}';
						*/
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
		
		function toback(){
			var rurl="${returnUrl}";
			var idx=rurl.indexOf("&lastReturnUrl=");
			if(idx>=0){
				lurl=rurl.substring(idx+15);
				purl=rurl.substring(0,idx+1);
				if(lurl.indexOf("%")>=0){//已经被编码了
					
					window.location.href=purl+"lastReturnUrl="+lurl;
				}else{
					window.location.href=purl+"lastReturnUrl="+encodeURIComponent(lurl);
				}
			}else{
				window.location.href=rurl;
			}
		}
	</script>
	<style>
		.panel-toolbar{
			margin-top: 0;
		}
		#docinfoForm{
			margin: 0 16px 16px 16px;
		}
		.input-text{
			width: 230px;
			height: 34px;
			line-height: 34px;
			padding: 0 10px;
			border: 1px solid #dadfed;
		}
		.table-detail{
			table-layout: fixed;
		}
		.table-detail th{
			width: 200px;
		}
		.table-detail td{
			padding: 5px;
		}
		.table-detail th{
			padding: 5px 10px
		}
		select{
			box-sizing: content-box;
		}
		.table-detail .editor-wrap{
			padding: 0;
		}
		.edui-default .edui-editor{
			border: 0 !important;
		}
		#edui1_elementpath,
		#edui1_wordcount{
			border-bottom: 0;
			border-right: 0;
		}
		.edui-default .edui-editor-bottomContainer td{
			 border-top: 1px solid #dadfed !important;
		}
		.selectlink{
		border-radius: 3px 3px 3px 3px !important;
	    text-align: center;
	    border: 0  !important;
	    color: #fff  !important;
	    padding: 6px 15px 3px 15px  !important;
	    background: #478de4 !important ;
	    -moz-box-shadow: 0 1px 1px rgba(0,0,0,0.15)  !important;
	    -webkit-box-shadow: 0 1px 1px rgba(0,0,0,0.15)  !important;
	    box-shadow: 0 1px 1px rgba(0,0,0,0.15)  !important; 
	    }
	</style>
</head>
<body>
<div class="panel" style="height:100%;overflow:auto;">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${not empty docinfoItem.id}">
			        <span class="tbar-label"><span></span>编辑文档</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加文档</span>
			    </c:otherwise>	
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:;"><span></span>保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group">
					<%-- <a class="link back" href="filelist.ht?docId=${docId}"><span></span>返回</a> --%>
					<a class="link back" href="javascript:;" onclick="toback()"><span></span>返回</a>
				</div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link preview" href="javascript:;"><span></span>预览</a></div>
			</div>
		</div>
	</div>
	<form id="docinfoForm" method="post" action="filesave.ht">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th>标题: </th>
				<td><input name="title" type="text" class="input-text"  validate="{'required':true,'maxlength':150}"  value="${docFile.title}"  /></td>
			</tr>
			<tr>
				<th>编号: </th>
				<td>
				<input name="filenum" type="text" class="input-text" id="filenum" value="${docFile.filenum}" validate="{'maxlength':20}" />
				<a href="javascript:;" class="link defs selectlink" data-value='filenum' data-show='filenumField'>选择</a>
				<span id="filenumField"></span>
				<span style="color: red;">注：如果是流程帮助文档，请使用选择按钮获取流程编号</span>
				</td>
			</tr>
			<tr>
				<th>版本: </th>
				<td>
					<c:if test="${docFile.version==null }">
						1
					</c:if>
					<c:if test="${docFile.version!=null }">
						${docFile.version}
					</c:if>
					<input name="version" type="hidden"  value="${docFile.version}"  />
				</td>
			</tr>
			<tr>
				<th>文档状态: </th>
				<td>
					<select  class="input-text" name="status">
						<option value="0" <c:if test='${docFile.status==0 }'>selected='selected'</c:if>>草稿</option>
						<option value="1" <c:if test='${docFile.status==1 }'>selected='selected'</c:if>>正常</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>文档目录: </th>	
				<td>${docName}</td>
			</tr>
			<tr>
				<th>文档所有者: </th>
				<td>${docFile.creator}</td>
			</tr>
			
			<tr>
				<th>部门: </th>
				<td>${orgName}</td>
			</tr>
			<tr>
				<th>关键字: </th>
				<td><input name="keyword" type="text" class="input-text"  value="${docFile.keyword}" validate="{'maxlength':200}" /></td>
			</tr>
			<tr>
				<th>附件:</th>
				<td>
					<div name="div_attachment_container">
						<div class="attachement"></div>
						<textarea style="display: none" controltype="attachment" name="file" lablename="附件" validate="{}">${docFile.file}</textarea>
						<a href="javascript:;" field="attachment" class="link selectFile" atype="select" onclick="AttachMent.addFile(this);">选择</a>
					</div>
				</td>
			</tr>
			<tr>
				<th align="right" style="text-align: right;" nowrap="nowarp" class="">内容: </th>
				<td class="editor-wrap">
					<div id="editor" position="center" style="overflow: hidden; height: 100%;">
						<textarea style="height:300px;" id="txtHtml" name="html">${docFile.content}</textarea>
						<textarea id="content" name="content" style="display: none;"></textarea>
					</div>
				</td>
			</tr>
		</table>
		<input type="hidden" name="dfid" value="${docFile.dfid}"/>
		<input type="hidden" id="docid" name="docid" value="${docId}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
		<input type="hidden" name="type"  id="type" />
		<input type="hidden" name="returnUrl" value="${returnUrl }"/>
		<input type="hidden" name="lastReturnUrl" value="${lastReturnUrl }"/>
	</form>
</body>
</html>
