<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 入职信息</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#entryForm').form();
			$("a.save").click(function() {
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
							frm.sortList();
							$('#entryForm').submit();
						});
					}else{
						frm.handleFieldName();
						frm.sortList();
						$('#entryForm').submit();
					}
				}
			});
			$("a.run").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(0);
				$('#entryForm').attr("action","run.ht");
				if(frm.valid()){
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					data=CustomForm.getData();
					//设置表单数据
					$("#formData").val(data);
					frm.handleFieldName();
					OfficePlugin.submit();
					frm.sortList();
					$('#entryForm').submit();
				}
			});
		});
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerDialog.confirm(obj.getMessage()+",是否继续操作","提示信息", function(rtn) {
					if(rtn){
						window.location.href = window.location.href;
					}else{
						window.location.href = "${ctx}/makshi/hr/entry/list.ht";
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
	</script>
</head>
<body>
<div class="panel" style="height:100%;overflow:auto;">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${not empty entryItem.id}">
			        <span class="tbar-label"><span></span>编辑入职信息</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加入职信息</span>
			    </c:otherwise>	
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:;"><span></span>保存</a></div>
				<div class="l-bar-separator"></div>
				<c:if test="${runId!=0}">
					<div class="group"><a class="link detail"  onclick="FlowDetailWindow({runId:${runId}})" href="javascript:;" ><span></span>流程明细</a></div>
					<div class="l-bar-separator"></div>
				</c:if>
				<div class="group"><a class="link back" href="list.ht"><span></span>返回</a></div>
			</div>
		</div>
	</div>
	<form id="entryForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled" interlaced="disabled" align="right" width="1391">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="4" style="word-break: break-all; border-color: rgb(102, 102, 102);">入职申请表</td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">序号</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:Entry:xh" lablename="序号" class="inputText" value="${entry.xh}" validate="{maxlength:50,required:true}" isflag="tableflag" /> </span> </td>
   <td class="formInput" colspan="2" rowspan="8" style="border-color: rgb(102, 102, 102); word-break: break-all;"> 
    <div id="div_zp" style="width:400px;height:340px" class="pictureShow-div"> 
     <div id="div_zp_container"></div> 
     <table id="pictureShow_zp_Toolbar"> 
      <tbody>
       <tr> 
        <td width="80"> <a href="javascript:;" field="zp" class="link selectFile" atype="uploadPicture" onclick="{PictureShowPlugin.upLoadPictureFile(this);}">上传图片</a> </td> 
        <td width="80"> <a href="javascript:;" field="zp" class="link del" atype="delPicture" onclick="{PictureShowPlugin.deletePictureFile(this);}">删除图片</a> </td> 
       </tr> 
      </tbody>
     </table> 
    </div> <input type="hidden" class="hidden" name="zp" lablename="照片" controltype="pictureShow" value="${entry.zp}" right="w" /> </td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">员工编号</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"> <input name="m:Entry:ygbh" type="text" value="${entry.ygbh}" showtype="{"decimalValue":"0"}" validate="{number:true,maxIntLen:14,maxDecimalLen:0,required:true}" /> </td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">姓名</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:Entry:xm" lablename="姓名" class="inputText" value="${entry.xm}" validate="{maxlength:50,required:true}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">性别</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"> <span> <label><input val="${entry.xb}" type="radio" name="m:Entry:xb" value="1" lablename="性别" validate="{}" />男</label> <label><input val="${entry.xb}" type="radio" name="m:Entry:xb" value="2" lablename="性别" validate="{}" />女</label> </span> </td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">出生日期</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"><br /> <input name="m:Entry:csrq" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${entry.csrq}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">婚姻状况</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:Entry:hyzk" lablename="婚姻状况" class="inputText" value="${entry.hyzk}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">曾用名</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:Entry:cym" lablename="曾用名" class="inputText" value="${entry.cym}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">民族</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:Entry:mz" lablename="民族" class="inputText" value="${entry.mz}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">籍贯</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:Entry:jg" lablename="籍贯" class="inputText" value="${entry.jg}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">职称专业</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:Entry:zczy" lablename="职称专业" class="inputText" value="${entry.zczy}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">文化程度<br /></span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:Entry:whcd" lablename="文化程度" class="inputText" value="${entry.whcd}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">参加工作时间</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"> <input name="m:Entry:cjgzsj" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${entry.cjgzsj}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">毕业院校</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:Entry:byyx" lablename="毕业院校" class="inputText" value="${entry.byyx}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">政治面貌</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:Entry:zzmm" lablename="政治面貌" class="inputText" value="${entry.zzmm}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">专业</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:Entry:zy" lablename="专业" class="inputText" value="${entry.zy}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">身份证号码</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:Entry:sfzhm" lablename="身份证号码" class="inputText" value="${entry.sfzhm}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102); word-break: break-all;" width="343"><span style="font-size: 16px;">职称</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102); word-break: break-all;" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:Entry:zc" lablename="职称" class="inputText" value="${entry.zc}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102); word-break: break-all;" width="343"><span style="font-size: 16px;">户籍</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"><br /> <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> <select name="m:Entry:hj" lablename="户籍" controltype="select" validate="{empty:false}" val="${entry.hj}"> <option value="0">请选择</option> <option value="1">非深户-农村</option> <option value="2">非深户-城市</option> <option value="3">深户-集体</option> <option value="4">深户-非集体</option> </select> </span> </td>
  </tr>
 </tbody>
</table>
<table>
 <tbody>
  <tr class="firstRow">
   <td width="331" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span style="font-size: 16px;">是否有传染病史</span></td>
   <td width="331" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><br /><span class="radioinput" parser="radioedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;options&amp;#39;:[{&amp;#39;key&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;value&amp;#39;:&amp;#39;否&amp;#39;},{&amp;#39;key&amp;#39;:&amp;#39;1&amp;#39;,&amp;#39;value&amp;#39;:&amp;#39;是&amp;#39;}],&amp;#39;comment&amp;#39;:&amp;#39;是否有传染病史&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;sfycrbs&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><label><input type="radio" value="0" />否</label><label><input type="radio" value="1" />是</label></span></td>
   <td width="331" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span style="font-size: 16px;">是否有遗传病史</span></td>
   <td width="331" valign="top" style="border-width: 1px; border-style: solid; word-break: break-all;"><br /><span class="radioinput" parser="radioedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;options&amp;#39;:[{&amp;#39;key&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;value&amp;#39;:&amp;#39;否&amp;#39;},{&amp;#39;key&amp;#39;:&amp;#39;1&amp;#39;,&amp;#39;value&amp;#39;:&amp;#39;是&amp;#39;}],&amp;#39;comment&amp;#39;:&amp;#39;是否有遗传病史&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;sfyycbs&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><label><input type="radio" value="0" />否</label><label><input type="radio" value="1" />是</label></span></td>
  </tr>
 </tbody>
</table>
<table>
 <tbody>
  <tr class="firstRow">
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span style="font-size: 16px;">社会保险电脑号</span></td>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;社会保险电脑号&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;shbxdnh&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input type="text" style="width: 150px; height: 18px;" /></span></td>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span style="font-size: 16px;">利手</span></td>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;利手&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;ls&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input type="text" style="width: 150px; height: 18px;" /></span></td>
  </tr>
 </tbody>
</table>
<table>
 <tbody>
  <tr class="firstRow">
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span style="font-size: 16px;">特长爱好</span></td>
   <td width="327" valign="top" style="border-width: 1px; border-style: solid;"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;特长爱好&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;tzah&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input type="text" style="width: 150px; height: 18px;" /></span></td>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span style="font-size: 16px;">户籍所在地</span></td>
   <td width="327" valign="top" style="border-width: 1px; border-style: solid;"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;户籍所在地&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;hjszd&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input type="text" style="width: 150px; height: 18px;" /></span></td>
  </tr>
  <tr>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span style="font-size: 16px;">配偶姓名</span></td>
   <td width="327" valign="top" style="border-width: 1px; border-style: solid;"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;配偶姓名&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;poxm&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input type="text" style="width: 150px; height: 18px;" /></span></td>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span style="font-size: 16px;">父母居住地</span></td>
   <td width="327" valign="top" style="border-width: 1px; border-style: solid;"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;父母居住地&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;fmjzd&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input type="text" style="width: 150px; height: 18px;" /></span></td>
  </tr>
  <tr>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span style="font-size: 16px;">配偶身份证号码</span></td>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;配偶身份证号码&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;posfzhm&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input type="text" style="width: 150px; height: 18px;" /></span></td>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span style="font-size: 16px;">配偶居住地</span></td>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;配偶居住地&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;pojzd&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input type="text" style="width: 150px; height: 18px;" /></span></td>
  </tr>
  <tr>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span style="font-size: 16px;">通讯地址</span></td>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;通讯地址&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;txdz&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input type="text" style="width: 150px; height: 18px;" /></span></td>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span style="font-size: 16px;">手机号码</span></td>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;手机号码&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;sjhm&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input type="text" style="width: 150px; height: 18px;" /></span></td>
  </tr>
  <tr>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span style="font-size: 16px;">家庭电话</span></td>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;家庭电话&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;jtdh&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input type="text" style="width: 150px; height: 18px;" /></span></td>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span style="font-size: 16px;">手机短号</span></td>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;手机短号&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;sjdh&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input type="text" style="width: 150px; height: 18px;" /></span></td>
  </tr>
  <tr>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span style="font-size: 16px;">紧急联系人</span></td>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;紧急联系人&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;jjlxr&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input type="text" style="width: 150px; height: 18px;" /></span></td>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span style="font-size: 16px;">交行卡号</span></td>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;交行卡号&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;jxkh&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input type="text" style="width: 150px; height: 18px;" /></span></td>
  </tr>
  <tr>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span style="font-size: 16px;">紧急联系人电话</span></td>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;紧急联系人电话&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;jjlxrdh&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input type="text" style="width: 150px; height: 18px;" /></span></td>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span style="font-size: 16px;">个人邮箱</span></td>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;个人邮箱&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;gryx&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input type="text" style="width: 150px; height: 18px;" /></span></td>
  </tr>
  <tr>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span style="font-size: 16px;">QQ号码</span></td>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;QQ号码&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;QQhm&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input type="text" style="width: 150px; height: 18px;" /></span></td>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span style="font-size: 16px;">微信</span></td>
   <td width="327" valign="top" style="word-break: break-all; border-width: 1px; border-style: solid;"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;微信&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;wx&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input type="text" style="width: 150px; height: 18px;" /></span></td>
  </tr>
 </tbody>
</table>
<div type="subtable" tablename="entry_jtcy" tabledesc="家庭成员" show="true" parser="rowmodeedit" id="entry_jtcy" formtype="edit">
 <table class="listTable" border="0" cellpadding="2" cellspacing="0" width="-173">
  <tbody>
   <tr class="toolBar firstRow">
    <td colspan="7"><a class="link add" href="javascript:;">添加</a></td>
   </tr>
   <tr class="headRow">
    <th style="word-break: break-all;">关系</th>
    <th style="word-break: break-all;">姓名</th>
    <th style="word-break: break-all;">性别</th>
    <th style="word-break: break-all;">出生年</th>
    <th style="word-break: break-all;">工作单位</th>
    <th style="word-break: break-all;">联系电话</th>
    <th style="word-break: break-all;">附件</th>
   </tr>
   <c:forEach items="${entry_jtcyList}" var="entry_jtcy" varStatus="status">
    <tr class="listRow" type="subdata">
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_jtcy:gx" lablename="关系" class="inputText" value="${entryJtcy.gx}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_jtcy:xm" lablename="姓名" class="inputText" value="${entryJtcy.xm}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_jtcy:xb" lablename="性别" class="inputText" value="${entryJtcy.xb}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_jtcy:csn" lablename="出生年" class="inputText" value="${entryJtcy.csn}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
     <td style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_jtcy:csny" lablename="出生年月" class="inputText" value="${entryJtcy.csny}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_jtcy:lxdh" lablename="联系电话" class="inputText" value="${entryJtcy.lxdh}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
     <td></td>
    </tr>
   </c:forEach>
   <tr class="listRow" type="append" style="display:none;"> 
    <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_jtcy:gx" lablename="关系" class="inputText" value="${entryJtcy.gx}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_jtcy:xm" lablename="姓名" class="inputText" value="${entryJtcy.xm}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_jtcy:xb" lablename="性别" class="inputText" value="${entryJtcy.xb}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_jtcy:csn" lablename="出生年" class="inputText" value="${entryJtcy.csn}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_jtcy:csny" lablename="出生年月" class="inputText" value="${entryJtcy.csny}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_jtcy:lxdh" lablename="联系电话" class="inputText" value="${entryJtcy.lxdh}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td></td> 
   </tr>
  </tbody>
 </table>
 <input name="s:entry_jtcy:id" type="hidden" pk="true" value="" />
</div>
<div type="subtable" tablename="entry_xxjl" tabledesc="学习经历" show="true" parser="rowmodeedit" id="entry_xxjl" formtype="edit">
 <table class="listTable" border="0" cellpadding="2" cellspacing="0" width="-231">
  <tbody>
   <tr class="toolBar firstRow">
    <td colspan="5"><a class="link add" href="javascript:;">添加</a></td>
   </tr>
   <tr class="headRow">
    <th style="word-break: break-all;">起止时间</th>
    <th style="word-break: break-all;">就读学校或机构</th>
    <th style="word-break: break-all;">专业</th>
    <th style="word-break: break-all;">所获证书、学位、奖励</th>
    <th style="word-break: break-all;">附件</th>
   </tr>
   <c:forEach items="${entry_xxjlList}" var="entry_xxjl" varStatus="status">
    <tr class="listRow" type="subdata">
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_xxjl:qzsj" lablename="起止时间" class="inputText" value="${entryXxjl.qzsj}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_xxjl:jdxxhjg" lablename="就读学校或机构" class="inputText" value="${entryXxjl.jdxxhjg}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_xxjl:zy" lablename="专业" class="inputText" value="${entryXxjl.zy}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_xxjl:shzs" lablename="所获证书" class="inputText" value="${entryXxjl.shzs}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
     <td style="word-break: break-all;"></td>
    </tr>
   </c:forEach>
   <tr class="listRow" type="append" style="display:none;"> 
    <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_xxjl:qzsj" lablename="起止时间" class="inputText" value="${entryXxjl.qzsj}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_xxjl:jdxxhjg" lablename="就读学校或机构" class="inputText" value="${entryXxjl.jdxxhjg}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_xxjl:zy" lablename="专业" class="inputText" value="${entryXxjl.zy}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_xxjl:shzs" lablename="所获证书" class="inputText" value="${entryXxjl.shzs}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td style="word-break: break-all;"></td> 
   </tr>
  </tbody>
 </table>
 <input name="s:entry_xxjl:id" type="hidden" pk="true" value="" />
</div>
<div type="subtable" tablename="entry_gzjl" tabledesc="工作经历" show="true" parser="rowmodeedit" id="entry_gzjl" formtype="edit">
 <table class="listTable" border="0" cellpadding="2" cellspacing="0" width="-231">
  <tbody>
   <tr class="toolBar firstRow">
    <td colspan="5"><a class="link add" href="javascript:;">添加</a></td>
   </tr>
   <tr class="headRow">
    <th style="word-break: break-all;">起止时间</th>
    <th style="word-break: break-all;">工作单位</th>
    <th style="word-break: break-all;">部门岗位</th>
    <th style="word-break: break-all;">技术职务</th>
    <th style="word-break: break-all;">附件</th>
   </tr>
   <c:forEach items="${entry_gzjlList}" var="entry_gzjl" varStatus="status">
    <tr class="listRow" type="subdata">
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_gzjl:qzsj" lablename="起止时间" class="inputText" value="${entryGzjl.qzsj}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
     <td style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_gzjl:gzdw" lablename="工作单位" class="inputText" value="${entryGzjl.gzdw}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_gzjl:bmgw" lablename="部门岗位" class="inputText" value="${entryGzjl.bmgw}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
     <td style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_gzjl:jszw" lablename="技术职务" class="inputText" value="${entryGzjl.jszw}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
     <td></td>
    </tr>
   </c:forEach>
   <tr class="listRow" type="append" style="display:none;"> 
    <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_gzjl:qzsj" lablename="起止时间" class="inputText" value="${entryGzjl.qzsj}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_gzjl:gzdw" lablename="工作单位" class="inputText" value="${entryGzjl.gzdw}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_gzjl:bmgw" lablename="部门岗位" class="inputText" value="${entryGzjl.bmgw}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_gzjl:jszw" lablename="技术职务" class="inputText" value="${entryGzjl.jszw}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td></td> 
   </tr>
  </tbody>
 </table>
 <input name="s:entry_gzjl:id" type="hidden" pk="true" value="" />
</div>
<div type="subtable" tablename="entry_zyzc" tabledesc="专业职称" show="true" parser="rowmodeedit" id="entry_zyzc" formtype="edit">
 <table class="listTable" border="0" cellpadding="2" cellspacing="0" width="-231">
  <tbody>
   <tr class="toolBar firstRow">
    <td colspan="5"><a class="link add" href="javascript:;">添加</a></td>
   </tr>
   <tr class="headRow">
    <th style="word-break: break-all;">职称名称</th>
    <th style="word-break: break-all;">发证机构</th>
    <th style="word-break: break-all;">职称专业</th>
    <th style="word-break: break-all;">取得职称时间</th>
    <th style="word-break: break-all;">附件</th>
   </tr>
   <c:forEach items="${entry_zyzcList}" var="entry_zyzc" varStatus="status">
    <tr class="listRow" type="subdata">
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_zyzc:zcmc" lablename="职称名称" class="inputText" value="${entryZyzc.zcmc}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_zyzc:fzjg" lablename="发证机构" class="inputText" value="${entryZyzc.fzjg}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_zyzc:zczy" lablename="职称专业" class="inputText" value="${entryZyzc.zczy}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
     <td><br /> <input name="s:entry_zyzc:qdzcsj" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${entryZyzc.qdzcsj}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td>
     <td></td>
    </tr>
   </c:forEach>
   <tr class="listRow" type="append" style="display:none;"> 
    <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_zyzc:zcmc" lablename="职称名称" class="inputText" value="${entryZyzc.zcmc}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_zyzc:fzjg" lablename="发证机构" class="inputText" value="${entryZyzc.fzjg}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_zyzc:zczy" lablename="职称专业" class="inputText" value="${entryZyzc.zczy}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td><br /> <input name="s:entry_zyzc:qdzcsj" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${entryZyzc.qdzcsj}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td> 
    <td></td> 
   </tr>
  </tbody>
 </table>
 <input name="s:entry_zyzc:id" type="hidden" pk="true" value="" />
</div>
<div type="subtable" tablename="entry_zyzg" tabledesc="执业资格" show="true" parser="rowmodeedit" id="entry_zyzg" formtype="edit">
 <table class="listTable" border="0" cellpadding="2" cellspacing="0" width="-198">
  <tbody>
   <tr class="toolBar firstRow">
    <td colspan="6"><a class="link add" href="javascript:;">添加</a></td>
   </tr>
   <tr class="headRow">
    <th style="word-break: break-all;">执业资格名称</th>
    <th style="word-break: break-all;">发证机构</th>
    <th style="word-break: break-all;">执业资格证专业</th>
    <th style="word-break: break-all;">取得证书时间</th>
    <th style="word-break: break-all;">是否转入本公司</th>
    <th style="word-break: break-all;">附件</th>
   </tr>
   <c:forEach items="${entry_zyzgList}" var="entry_zyzg" varStatus="status">
    <tr class="listRow" type="subdata">
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_zyzg:zyzgmc" lablename="执业资格名称" class="inputText" value="${entryZyzg.zyzgmc}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_zyzg:fzjg" lablename="发证机构" class="inputText" value="${entryZyzg.fzjg}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_zyzg:zyzgzzy" lablename="执业资格证专业" class="inputText" value="${entryZyzg.zyzgzzy}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
     <td> <input name="s:entry_zyzg:qdzssj" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${entryZyzg.qdzssj}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td>
     <td><br /> <span> <label><input val="${entryZyzg.sfzrbgs}" type="radio" name="s:entry_zyzg:sfzrbgs" value="0" lablename="是否转入本公司" validate="{}" />否</label> <label><input val="${entryZyzg.sfzrbgs}" type="radio" name="s:entry_zyzg:sfzrbgs" value="1" lablename="是否转入本公司" validate="{}" />是</label> </span> </td>
     <td></td>
    </tr>
   </c:forEach>
   <tr class="listRow" type="append" style="display:none;"> 
    <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_zyzg:zyzgmc" lablename="执业资格名称" class="inputText" value="${entryZyzg.zyzgmc}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_zyzg:fzjg" lablename="发证机构" class="inputText" value="${entryZyzg.fzjg}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:entry_zyzg:zyzgzzy" lablename="执业资格证专业" class="inputText" value="${entryZyzg.zyzgzzy}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td> <input name="s:entry_zyzg:qdzssj" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${entryZyzg.qdzssj}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td> 
    <td><br /> <span> <label><input val="${entryZyzg.sfzrbgs}" type="radio" name="s:entry_zyzg:sfzrbgs" value="0" lablename="是否转入本公司" validate="{}" />否</label> <label><input val="${entryZyzg.sfzrbgs}" type="radio" name="s:entry_zyzg:sfzrbgs" value="1" lablename="是否转入本公司" validate="{}" />是</label> </span> </td> 
    <td></td> 
   </tr>
  </tbody>
 </table>
 <input name="s:entry_zyzg:id" type="hidden" pk="true" value="" />
</div>
			</div>
		</div>
		<input type="hidden" name="id" value="${entry.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
