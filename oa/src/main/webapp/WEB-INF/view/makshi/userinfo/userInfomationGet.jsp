
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>用户信息档案表明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">用户信息档案表详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="list.ht"><span></span>返回</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<table class="formTable" parser="addpermission" data-sort="sortDisabled" interlaced="disabled" width="1391" cellspacing="0" cellpadding="2" border="1" align="right"> 
 <tbody> 
  <tr class="firstRow"> 
   <td class="formHead" colspan="4" style="word-break: break-all; border-color: rgb(102, 102, 102);">入职申请表</td> 
  </tr> 
  <tr> 
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">序号</span></td> 
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"><br /></td> 
   <td class="formInput" colspan="2" rowspan="7" style="border-color: rgb(102, 102, 102); word-break: break-all;"><br /></td> 
  </tr> 
  <tr> 
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">员工编号</span></td> 
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"> ${userInfomation.userId} </td> 
  </tr> 
  <tr> 
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">姓名</span></td> 
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><br /></td> 
  </tr> 
  <tr> 
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">出生日期</span></td> 
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"><br /> <fmt:formatDate value='${userInfomation.birthday}' pattern='yyyy-MM-dd'/> </td> 
  </tr> 
  <tr> 
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">婚姻状况</span></td> 
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"></td> 
  </tr> 
  <tr> 
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">曾用名</span></td> 
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"></td> 
  </tr> 
  <tr> 
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">民族</span></td> 
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${userInfomation.nation} </span> </td> 
  </tr> 
  <tr> 
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">籍贯</span></td> 
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${userInfomation.address} </span> </td> 
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">职称专业</span></td> 
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"></td> 
  </tr> 
  <tr> 
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">文化程度<br /></span></td> 
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${userInfomation.education} </span> </td> 
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">参加工作时间</span></td> 
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"></td> 
  </tr> 
  <tr> 
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">毕业院校</span></td> 
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"></td> 
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">政治面貌</span></td> 
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"></td> 
  </tr> 
  <tr> 
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">专业</span></td> 
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${userInfomation.major} </span> </td> 
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all; border-color: rgb(102, 102, 102);" width="343"><span style="font-size: 16px;">身份证号码</span></td> 
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"></td> 
  </tr> 
  <tr> 
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102); word-break: break-all;" width="343"><span style="font-size: 16px;">职称</span></td> 
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102); word-break: break-all;" width="343"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${userInfomation.positional} </span> </td> 
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102); word-break: break-all;" width="343"><span style="font-size: 16px;">户籍</span></td> 
   <td class="formInput" colspan="1" rowspan="1" style="border-color: rgb(102, 102, 102);" width="343"><br /></td> 
  </tr> 
 </tbody> 
</table>              
<table> 
 <tbody> 
  <tr class="firstRow"> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="331" valign="top">是否有传染病史</td> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="331" valign="top"><br /><span class="radioinput" parser="radioedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;options&amp;#39;:[{&amp;#39;key&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;value&amp;#39;:&amp;#39;否&amp;#39;},{&amp;#39;key&amp;#39;:&amp;#39;1&amp;#39;,&amp;#39;value&amp;#39;:&amp;#39;是&amp;#39;}],&amp;#39;comment&amp;#39;:&amp;#39;是否有传染病史&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;infection_history&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><label><input value="0" type="radio" />否</label><label><input value="1" type="radio" />是</label></span></td> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="331" valign="top">是否有遗传病史</td> 
   <td style="border-width: 1px; border-style: solid; word-break: break-all;" width="331" valign="top"><br /><span class="radioinput" parser="radioedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;options&amp;#39;:[{&amp;#39;key&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;value&amp;#39;:&amp;#39;否&amp;#39;},{&amp;#39;key&amp;#39;:&amp;#39;1&amp;#39;,&amp;#39;value&amp;#39;:&amp;#39;是&amp;#39;}],&amp;#39;comment&amp;#39;:&amp;#39;是否有遗传病史&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;disorders_history&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><label><input value="0" type="radio" />否</label><label><input value="1" type="radio" />是</label></span></td> 
  </tr> 
 </tbody> 
</table>  
<table> 
 <tbody> 
  <tr class="firstRow"> 
   <td style="word-break: break-all;" width="327" valign="top">社会保险电脑号</td> 
   <td style="word-break: break-all;" width="327" valign="top"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;社会保险电脑号&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;social_security_computer_id&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input style="width: 150px; height: 18px;" type="text" /></span></td> 
   <td style="word-break: break-all;" width="327" valign="top">利手</td> 
   <td width="327" valign="top"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;利手&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;handedness&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input style="width: 150px; height: 18px;" type="text" /></span></td> 
  </tr> 
 </tbody> 
</table>  
<table> 
 <tbody> 
  <tr class="firstRow"> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="327" valign="top">特长爱好</td> 
   <td style="border-width: 1px; border-style: solid;" width="327" valign="top"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;特长爱好&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;hobby&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input style="width: 150px; height: 18px;" type="text" /></span></td> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="327" valign="top">户籍所在地</td> 
   <td style="border-width: 1px; border-style: solid;" width="327" valign="top"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;户籍所在地&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;home_address&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input style="width: 150px; height: 18px;" type="text" /></span></td> 
  </tr> 
  <tr> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="327" valign="top">配偶姓名</td> 
   <td style="border-width: 1px; border-style: solid;" width="327" valign="top"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;配偶姓名&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;spouse_name&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input style="width: 150px; height: 18px;" type="text" /></span></td> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="327" valign="top">父母居住地</td> 
   <td style="border-width: 1px; border-style: solid;" width="327" valign="top"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;父母居住地&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;parents&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input style="width: 150px; height: 18px;" type="text" /></span></td> 
  </tr> 
  <tr> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="327" valign="top">配偶身份证号码</td> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="327" valign="top"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;配偶身份证号码&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;spouse_identification_id&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input style="width: 150px; height: 18px;" type="text" /></span></td> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="327" valign="top">配偶居住地</td> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="327" valign="top"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;配偶居住地&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;spouse_address&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input style="width: 150px; height: 18px;" type="text" /></span></td> 
  </tr> 
  <tr> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="327" valign="top">通讯地址</td> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="327" valign="top"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;通讯地址&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;link_address&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input style="width: 150px; height: 18px;" type="text" /></span></td> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="327" valign="top">手机号码</td> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="327" valign="top"><br /></td> 
  </tr> 
  <tr> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="327" valign="top">家庭电话</td> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="327" valign="top"><br /></td> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="327" valign="top">手机短号</td> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="327" valign="top"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;手机短号&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;sjdh&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input style="width: 150px; height: 18px;" type="text" /></span></td> 
  </tr> 
  <tr> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="327" valign="top">紧急联系人</td> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="327" valign="top"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;紧急联系人&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;emergency_link_person&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input style="width: 150px; height: 18px;" type="text" /></span></td> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="327" valign="top">交行卡号</td> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="327" valign="top"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;交行卡号&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;BOC_id&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input style="width: 150px; height: 18px;" type="text" /></span></td> 
  </tr> 
  <tr> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="327" valign="top">紧急联系人电话</td> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="327" valign="top"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;紧急联系人电话&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;emergency_link_person_phone&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input style="width: 150px; height: 18px;" type="text" /></span></td> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="327" valign="top">个人邮箱</td> 
   <td style="word-break: break-all; border-width: 1px; border-style: solid;" width="327" valign="top"><br /></td> 
  </tr> 
  <tr> 
   <td colspan="1" rowspan="1" style="border-left-width: 1px; border-top-width: 1px; word-break: break-all;" valign="top">QQ号码</td> 
   <td colspan="1" rowspan="1" style="border-left-width: 1px; border-top-width: 1px; word-break: break-all;" valign="top"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;QQ号码&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;QQ&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input style="width: 150px; height: 18px;" type="text" /></span></td> 
   <td colspan="1" rowspan="1" style="border-left-width: 1px; border-top-width: 1px; word-break: break-all;" valign="top">微信</td> 
   <td colspan="1" rowspan="1" style="border-left-width: 1px; border-top-width: 1px;" valign="top"><span class="textinput" parser="inputedit" external="{&amp;#39;dbType&amp;#39;:{&amp;#39;type&amp;#39;:&amp;#39;varchar&amp;#39;,&amp;#39;length&amp;#39;:&amp;#39;50&amp;#39;,&amp;#39;intLen&amp;#39;:&amp;#39;14&amp;#39;,&amp;#39;decimalLen&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;format&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;,&amp;#39;dateStrFormat&amp;#39;:&amp;#39;yyyy-MM-dd&amp;#39;},&amp;#39;valueFrom&amp;#39;:{&amp;#39;value&amp;#39;:&amp;#39;0&amp;#39;,&amp;#39;content&amp;#39;:&amp;#39;&amp;#39;},&amp;#39;width&amp;#39;:&amp;#39;150&amp;#39;,&amp;#39;widthUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;height&amp;#39;:&amp;#39;18&amp;#39;,&amp;#39;heightUnit&amp;#39;:&amp;#39;px&amp;#39;,&amp;#39;comment&amp;#39;:&amp;#39;微信&amp;#39;,&amp;#39;name&amp;#39;:&amp;#39;wechart&amp;#39;,&amp;#39;isRequired&amp;#39;:0,&amp;#39;isList&amp;#39;:0,&amp;#39;isFlowVar&amp;#39;:0,&amp;#39;isWebSign&amp;#39;:0}"><input style="width: 150px; height: 18px;" type="text" /></span></td> 
  </tr> 
 </tbody> 
</table>   
<div type="subtable" tablename="entry_family" tabledesc="家庭成员" show="true" parser="rowmodeedit" id="entry_family" formtype="edit"> 
 <table class="listTable" border="0" cellpadding="2" cellspacing="0"> 
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
   <c:forEach items="${entry_familyList}" var="entry_family" varStatus="status">
    <tr class="listRow" type="subdata"> 
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryFamily.relationship} </span> </td> 
     <td></td> 
     <td></td> 
     <td></td> 
     <td></td> 
     <td></td> 
     <td></td> 
    </tr>
   </c:forEach> 
  </tbody> 
 </table> 
</div>   
<div type="subtable" tablename="entry_education_history" tabledesc="学习经历" show="true" parser="rowmodeedit" id="entry_education_history" formtype="edit"> 
 <table class="listTable" border="0" cellpadding="2" cellspacing="0"> 
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
   <c:forEach items="${entry_education_historyList}" var="entry_education_history" varStatus="status">
    <tr class="listRow" type="subdata"> 
     <td></td> 
     <td></td> 
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryEducationHistory.major} </span> </td> 
     <td></td> 
     <td> 
      <div name="div_attachment_container" right="r"> 
       <div class="attachement"></div> 
       <textarea style="display:none" controltype="attachment" name="attachment" lablename="附件" readonly="readonly">${entryEducationHistory.attachment}</textarea> 
      </div> </td> 
    </tr>
   </c:forEach> 
  </tbody> 
 </table> 
</div>   
<div type="subtable" tablename="entry_work_history" tabledesc="工作经历" show="true" parser="rowmodeedit" id="entry_work_history" formtype="edit"> 
 <table class="listTable" border="0" cellpadding="2" cellspacing="0"> 
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
   <c:forEach items="${entry_work_historyList}" var="entry_work_history" varStatus="status">
    <tr class="listRow" type="subdata"> 
     <td></td> 
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryWorkHistory.workplace} </span> </td> 
     <td></td> 
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryWorkHistory.positions} </span> </td> 
     <td> 
      <div name="div_attachment_container" right="r"> 
       <div class="attachement"></div> 
       <textarea style="display:none" controltype="attachment" name="attachment" lablename="附件" readonly="readonly">${entryWorkHistory.attachment}</textarea> 
      </div> </td> 
    </tr>
   </c:forEach> 
  </tbody> 
 </table> 
</div>    
<div type="subtable" tablename="entry_professional" tabledesc="专业职称" show="true" parser="rowmodeedit" id="entry_professional" formtype="edit"> 
 <table class="listTable" border="0" cellpadding="2" cellspacing="0" width="-258"> 
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
   <c:forEach items="${entry_professionalList}" var="entry_professional" varStatus="status">
    <tr class="listRow" type="subdata"> 
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryProfessional.name} </span> </td> 
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryProfessional.organization} </span> </td> 
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryProfessional.major} </span> </td> 
     <td></td> 
     <td> 
      <div name="div_attachment_container" right="r"> 
       <div class="attachement"></div> 
       <textarea style="display:none" controltype="attachment" name="attachment" lablename="附件" readonly="readonly">${entryProfessional.attachment}</textarea> 
      </div> </td> 
    </tr>
   </c:forEach> 
  </tbody> 
 </table> 
</div>    
<div type="subtable" tablename="entry_vocation_qualification" tabledesc="执业资格" show="true" parser="rowmodeedit" id="entry_vocation_qualification" formtype="edit"> 
 <table class="listTable" border="0" cellpadding="2" cellspacing="0"> 
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
   <c:forEach items="${entry_vocation_qualificationList}" var="entry_vocation_qualification" varStatus="status">
    <tr class="listRow" type="subdata"> 
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryVocationQualification.name} </span> </td> 
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryVocationQualification.organization} </span> </td> 
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${entryVocationQualification.major} </span> </td> 
     <td></td> 
     <td> <span> <label><input data="${entryVocationQualification.switchs}" type="radio" name="switchs" value="1" lablename="是否转入本公司" validate="{}" disabled="disabled" />是</label> <label><input data="${entryVocationQualification.switchs}" type="radio" name="switchs" value="0" lablename="是否转入本公司" validate="{}" disabled="disabled" />否</label> </span> </td> 
     <td> 
      <div name="div_attachment_container" right="r"> 
       <div class="attachement"></div> 
       <textarea style="display:none" controltype="attachment" name="attachment" lablename="附件" readonly="readonly">${entryVocationQualification.attachment}</textarea> 
      </div> </td> 
    </tr>
   </c:forEach> 
  </tbody> 
 </table> 
</div>
</body>
</html>

