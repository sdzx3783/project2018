<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>

<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/scriptMgr.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript">
	function afterOnload(){
		var afterLoadJs=[
		     			'${ctx}/js/hotent/formdata.js',
		     			'${ctx}/js/hotent/subform.js'
		 ];
		ScriptMgr.load({
			scripts : afterLoadJs
		});
	}
</script>
<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled" interlaced="disabled" align="right" width="1391">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="4" style="word-break: break-all; border-color: rgb(102, 102, 102);">入职申请表</td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">序号</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entry.xh} </span> </td>
   <td class="formInput" colspan="2" rowspan="8" style="border-color: rgb(102, 102, 102); word-break: break-all;"> 
    <div id="div_zp" style="width:400px;height:340px" class="pictureShow-div"> 
     <div id="div_zp_container"></div> 
     <table id="pictureShow_zp_Toolbar"> 
      <tbody>
       <tr right="r"> 
        <td width="80"> <a href="javascript:;" field="zp" class="link selectFile" atype="uploadPicture" onclick="{PictureShowPlugin.upLoadPictureFile(this);}">上传图片</a> </td> 
        <td width="80"> <a href="javascript:;" field="zp" class="link del" atype="delPicture" onclick="{PictureShowPlugin.deletePictureFile(this);}">删除图片</a> </td> 
       </tr> 
      </tbody>
     </table> 
    </div> <input type="hidden" class="hidden" name="zp" lablename="照片" controltype="pictureShow" value="${entry.zp}" right="r" /> </td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">员工编号</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"> ${entry.ygbh} </td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">姓名</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entry.xm} </span> </td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">性别</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"> <span> <label><input data="${entry.xb}" type="radio" name="xb" value="1" lablename="性别" validate="{}" disabled="disabled" />男</label> <label><input data="${entry.xb}" type="radio" name="xb" value="2" lablename="性别" validate="{}" disabled="disabled" />女</label> </span> </td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">出生日期</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"><br /> <fmt:formatDate value='${entry.csrq}' pattern='yyyy-MM-dd'/> </td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">婚姻状况</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entry.hyzk} </span> </td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">曾用名</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entry.cym} </span> </td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">民族</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entry.mz} </span> </td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">籍贯</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entry.jg} </span> </td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">职称专业</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entry.zczy} </span> </td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">文化程度<br /></span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entry.whcd} </span> </td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">参加工作时间</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"> <fmt:formatDate value='${entry.cjgzsj}' pattern='yyyy-MM-dd'/> </td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">毕业院校</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entry.byyx} </span> </td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">政治面貌</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entry.zzmm} </span> </td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">专业</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entry.zy} </span> </td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">身份证号码</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entry.sfzhm} </span> </td>
  </tr>
  <tr>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102); word-break: break-all;" width="343"><span style="font-size: 16px;">职称</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102); word-break: break-all;" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entry.zc} </span> </td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102); word-break: break-all;" width="343"><span style="font-size: 16px;">户籍</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"><br /> ${entry.hj} </td>
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
    <td colspan="7"></td>
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
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryJtcy.gx} </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryJtcy.xm} </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryJtcy.xb} </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryJtcy.csn} </span> </td>
     <td style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryJtcy.csny} </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryJtcy.lxdh} </span> </td>
     <td> 
      <div name="div_attachment_container" right="r"> 
       <div class="attachement"></div> 
       <textarea style="display:none" controltype="attachment" name="fj" lablename="附件" readonly="readonly">${entryJtcy.fj}</textarea> 
      </div> </td>
    </tr>
   </c:forEach>
  </tbody>
 </table>
</div>
<div type="subtable" tablename="entry_xxjl" tabledesc="学习经历" show="true" parser="rowmodeedit" id="entry_xxjl" formtype="edit">
 <table class="listTable" border="0" cellpadding="2" cellspacing="0" width="-231">
  <tbody>
   <tr class="toolBar firstRow">
    <td colspan="5"></td>
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
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryXxjl.qzsj} </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryXxjl.jdxxhjg} </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryXxjl.zy} </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryXxjl.shzs} </span> </td>
     <td style="word-break: break-all;"> 
      <div name="div_attachment_container" right="r"> 
       <div class="attachement"></div> 
       <textarea style="display:none" controltype="attachment" name="fj" lablename="附件" readonly="readonly">${entryXxjl.fj}</textarea> 
      </div> </td>
    </tr>
   </c:forEach>
  </tbody>
 </table>
</div>
<div type="subtable" tablename="entry_gzjl" tabledesc="工作经历" show="true" parser="rowmodeedit" id="entry_gzjl" formtype="edit">
 <table class="listTable" border="0" cellpadding="2" cellspacing="0" width="-231">
  <tbody>
   <tr class="toolBar firstRow">
    <td colspan="5"></td>
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
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryGzjl.qzsj} </span> </td>
     <td style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryGzjl.gzdw} </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryGzjl.bmgw} </span> </td>
     <td style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryGzjl.jszw} </span> </td>
     <td> 
      <div name="div_attachment_container" right="r"> 
       <div class="attachement"></div> 
       <textarea style="display:none" controltype="attachment" name="fj" lablename="附件" readonly="readonly">${entryGzjl.fj}</textarea> 
      </div> </td>
    </tr>
   </c:forEach>
  </tbody>
 </table>
</div>
<div type="subtable" tablename="entry_zyzc" tabledesc="专业职称" show="true" parser="rowmodeedit" id="entry_zyzc" formtype="edit">
 <table class="listTable" border="0" cellpadding="2" cellspacing="0" width="-231">
  <tbody>
   <tr class="toolBar firstRow">
    <td colspan="5"></td>
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
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryZyzc.zcmc} </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryZyzc.fzjg} </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryZyzc.zczy} </span> </td>
     <td><br /> <fmt:formatDate value='${entryZyzc.qdzcsj}' pattern='yyyy-MM-dd'/> </td>
     <td> 
      <div name="div_attachment_container" right="r"> 
       <div class="attachement"></div> 
       <textarea style="display:none" controltype="attachment" name="fj" lablename="附件" readonly="readonly">${entryZyzc.fj}</textarea> 
      </div> </td>
    </tr>
   </c:forEach>
  </tbody>
 </table>
</div>
<div type="subtable" tablename="entry_zyzg" tabledesc="执业资格" show="true" parser="rowmodeedit" id="entry_zyzg" formtype="edit">
 <table class="listTable" border="0" cellpadding="2" cellspacing="0" width="-198">
  <tbody>
   <tr class="toolBar firstRow">
    <td colspan="6"></td>
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
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryZyzg.zyzgmc} </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryZyzg.fzjg} </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryZyzg.zyzgzzy} </span> </td>
     <td> <fmt:formatDate value='${entryZyzg.qdzssj}' pattern='yyyy-MM-dd'/> </td>
     <td><br /> <span> <label><input data="${entryZyzg.sfzrbgs}" type="radio" name="sfzrbgs" value="0" lablename="是否转入本公司" validate="{}" disabled="disabled" />否</label> <label><input data="${entryZyzg.sfzrbgs}" type="radio" name="sfzrbgs" value="1" lablename="是否转入本公司" validate="{}" disabled="disabled" />是</label> </span> </td>
     <td> 
      <div name="div_attachment_container" right="r"> 
       <div class="attachement"></div> 
       <textarea style="display:none" controltype="attachment" name="fj" lablename="附件" readonly="readonly">${entryZyzg.fj}</textarea> 
      </div> </td>
    </tr>
   </c:forEach>
  </tbody>
 </table>
</div>
