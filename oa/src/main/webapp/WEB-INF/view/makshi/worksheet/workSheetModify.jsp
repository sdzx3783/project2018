<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>

<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/js/hotent/scriptMgr.js"></script>
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
<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled" width="-418">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="6">主 &nbsp; &nbsp; &nbsp;表</td>
  </tr>
  <tr>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">姓名:</td>
   <td style="width: 15%" class="formInput"><br /> <input name="m:work_sheet:fullname" type="text" ctltype="selector" class="users" lablename="姓名" value="${workSheet.fullname}" validate="{empty:false,required:true}" readonly="readonly" /> </td>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">部门:</td>
   <td style="width: 15%" class="formInput"> <input name="m:work_sheet:org" type="text" ctltype="selector" class="orgs" lablename="部门" value="${workSheet.org}" validate="{empty:false,required:true}" readonly="readonly" /> </td>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">岗位:</td>
   <td style="width: 15%" class="formInput"> <input name="m:work_sheet:postion" type="text" ctltype="selector" class="positions" lablename="岗位" value="${workSheet.postion}" validate="{empty:false,required:true}" readonly="readonly" /> </td>
  </tr>
  <tr>
   <td class="formTitle" align="right" colspan="1" rowspan="1" style="word-break: break-all;">签到时间:</td>
   <td class="formTitle" align="left" colspan="1" rowspan="1" valign="top"></td>
   <td class="formTitle" align="right" colspan="1" rowspan="1" style="word-break: break-all;">星期:</td>
   <td class="formTitle" align="right" colspan="1" rowspan="1"> <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> <select name="m:work_sheet:week" lablename="星期" controltype="select" validate="{empty:false,required:true}" val="${workSheet.week}"> <option value="日">日</option> <option value="一">一</option> <option value="二">二</option> <option value="三">三</option> <option value="四">四</option> <option value="五">五</option> <option value="六">六</option> </select> </span> </td>
   <td class="formTitle" align="right" colspan="1" rowspan="1" style="word-break: break-all;"><span style="font-size: 13.3333px; text-align: right; background-color: rgb(240, 241, 241);">类型:</span></td>
   <td class="formTitle" align="right" colspan="1" rowspan="1"> <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> <select name="m:work_sheet:type" lablename="类型" controltype="select" validate="{empty:false,required:true}" val="${workSheet.type}"> <option value="签到">签到</option> <option value="补录">补录</option> </select> </span> </td>
  </tr>
  <tr>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 13.3333px; text-align: right; background-color: rgb(240, 241, 241);">创建时间:</span></td>
   <td style="width: 15%" class="formInput"></td>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">备注</td>
   <td style="width: 15%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:work_sheet:remark" lablename="备注" class="inputText" value="${workSheet.remark}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
 </tbody>
</table>
