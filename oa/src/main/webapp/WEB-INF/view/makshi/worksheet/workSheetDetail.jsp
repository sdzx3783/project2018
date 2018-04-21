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
<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled" width="-418">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="6">主 &nbsp; &nbsp; &nbsp;表</td>
  </tr>
  <tr>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">姓名:</td>
   <td style="width: 15%" class="formInput"><br /> ${workSheet.fullname} </td>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">部门:</td>
   <td style="width: 15%" class="formInput"> ${workSheet.org} </td>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">岗位:</td>
   <td style="width: 15%" class="formInput"> ${workSheet.postion} </td>
  </tr>
  <tr>
   <td class="formTitle" align="right" colspan="1" rowspan="1" style="word-break: break-all;">签到时间:</td>
   <td class="formTitle" align="left" colspan="1" rowspan="1" valign="top"></td>
   <td class="formTitle" align="right" colspan="1" rowspan="1" style="word-break: break-all;">星期:</td>
   <td class="formTitle" align="right" colspan="1" rowspan="1"> ${workSheet.week} </td>
   <td class="formTitle" align="right" colspan="1" rowspan="1" style="word-break: break-all;"><span style="font-size: 13.3333px; text-align: right; background-color: rgb(240, 241, 241);">类型:</span></td>
   <td class="formTitle" align="right" colspan="1" rowspan="1"> ${workSheet.type} </td>
  </tr>
  <tr>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 13.3333px; text-align: right; background-color: rgb(240, 241, 241);">创建时间:</span></td>
   <td style="width: 15%" class="formInput"></td>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">备注</td>
   <td style="width: 15%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${workSheet.remark} </span> </td>
  </tr>
 </tbody>
</table>
