
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>lg多样化明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">lg多样化详细信息</span>
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
	<table cellpadding="2" cellspacing="0" border="1" class="formTable">
 <tbody>
  <tr>
   <td colspan="8" class="formHead">lg多样化</td>
  </tr>
  <tr>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">姓名:</td>
   <td style="width:15%;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag">${lgmoreLg.name}</span></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">性别:</td>
   <td style="width:15%;" class="formInput"><span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"><c:if test='${lgmoreLg.sex==1}'>男</c:if><c:if test='${lgmoreLg.sex==0}'>女</c:if></span></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">生日:</td>
   <td style="width:15%;" class="formInput"><fmt:formatDate value='${lgmoreLg.birthday}' pattern='yyyy-MM-dd'/></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">年薪:</td>
   <td style="width:15%;" class="formInput">${lgmoreLg.money}</td>
  </tr>
  <tr>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">toWife:</td>
   <td style="width:15%;" class="formInput">${lgmoreLg.toWife}</td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">入职日期:</td>
   <td style="width:15%;" class="formInput"><fmt:formatDate value='${lgmoreLg.joinDate}' pattern='yyyy-MM-dd HH:mm:00'/></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">结婚日期:</td>
   <td style="width:15%;" class="formInput"><fmt:formatDate value='${lgmoreLg.jiehunDay}' pattern='yyyy-MM-dd'/></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">工作性质:</td>
   <td style="width:15%;" class="formInput"><span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"><c:if test='${lgmoreLg.jobType==零售业}'>零售业</c:if><c:if test='${lgmoreLg.jobType==销售业}'>销售业</c:if><c:if test='${lgmoreLg.jobType==物流业}'>物流业</c:if><c:if test='${lgmoreLg.jobType==打杂}'>打杂</c:if></span></td>
  </tr>
  <tr>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">备注:</td>
   <td style="width:15%;" class="formInput"><textarea name="comment" validate="{empty:false}" readonly="readonly">${lgmoreLg.comment}</textarea></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">选择用户:</td>
   <td style="width:15%;" class="formInput">
    <div>
     <input name="selectUserID" type="hidden" lablename="选择用户ID" class="hidden" value="${lgmoreLg.selectUserID}" />${lgmoreLg.selectUser}
    </div></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">选择组织:</td>
   <td style="width:15%;" class="formInput">
    <div>
     <input name="selectOrgID" type="hidden" class="hidden" lablename="选择组织ID" value="${lgmoreLg.selectOrgID}" />${lgmoreLg.selectOrg}
    </div></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">选择角色:</td>
   <td style="width:15%;" class="formInput">
    <div>
     <input name="selectRoleID" type="hidden" class="hidden" lablename="选择角色ID" value="${lgmoreLg.selectRoleID}" />${lgmoreLg.selectRole}
    </div></td>
  </tr>
  <tr>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">选择岗位:</td>
   <td style="width:15%;" class="formInput">
    <div>
     <input name="selectJobID" type="hidden" class="hidden" lablename="选择岗位ID" value="${lgmoreLg.selectJobID}" />${lgmoreLg.selectJob}
    </div></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">下拉选项:</td>
   <td style="width:15%;" class="formInput"><span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"><c:if test='${lgmoreLg.selectOpinion==选择1}'>选择1</c:if><c:if test='${lgmoreLg.selectOpinion==选择2}'>选择2</c:if><c:if test='${lgmoreLg.selectOpinion==选择3}'>选择3</c:if><c:if test='${lgmoreLg.selectOpinion==选择4}'>选择4</c:if><c:if test='${lgmoreLg.selectOpinion==选择5}'>选择5</c:if></span></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">图片控件:</td>
   <td style="width:15%;" class="formInput">
    <div id="div_m_lgMore_picSelect" style="width:400px;height:340px" class="pictureShow-div">
     <div id="div_m_lgMore_picSelect_container"></div>
     <table id="pictureShow_m_lgMore_picSelect_Toolbar">
      <tbody>
       <tr right="r">
        <td width="80"></td>
        <td width="80"></td>
       </tr>
      </tbody>
     </table>
    </div><input type="hidden" class="hidden" name="picSelect" lablename="图片控件" controltype="pictureShow" right="w" value="${lgmoreLg.picSelect}" /></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">文件上传:</td>
   <td style="width:15%;" class="formInput" right="r">
    <div name="div_attachment_container" right="r">
     <div class="attachement"></div>
     <textarea style="display:none" controltype="attachment" name="fileUpload" lablename="文件上传" readonly="readonly">${lgmoreLg.fileUpload}</textarea>
    </div></td>
  </tr>
 </tbody>
</table>
</body>
</html>

