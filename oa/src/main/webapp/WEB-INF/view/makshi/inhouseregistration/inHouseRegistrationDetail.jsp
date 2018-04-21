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
<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled"> 
 <tbody> 
  <tr class="firstRow"> 
   <td class="formHead" colspan="4" style="word-break: break-all;">租房入住人员登记</td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">租房编号:</td> 
   <td style="width: 35%" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">房屋地址:</td> 
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${inHouseRegistration.address} </span> </td> 
  </tr> 
  <tr> 
   <td class="formInput" colspan="4" rowspan="1"><br /> 
    <div type="subtable" tablename="user_info" tabledesc="员工入住信息" show="true" parser="rowmodeedit" id="user_info" formtype="edit"> 
     <table class="listTable" border="0" cellpadding="2" cellspacing="0"> 
      <tbody> 
       <tr class="toolBar firstRow"> 
        <td colspan="5"></td> 
       </tr> 
       <tr class="headRow"> 
        <th style="word-break: break-all;">入住人员姓名</th> 
        <th style="word-break: break-all;">是为负责人</th> 
        <th style="word-break: break-all;">入住家属</th> 
        <th style="word-break: break-all;">入住时间</th> 
        <th style="word-break: break-all;">离开时间</th> 
       </tr> 
       <c:forEach items="${user_infoList}" var="user_info" varStatus="status">
        <tr class="listRow" type="subdata"> 
         <td></td> 
         <td></td> 
         <td></td> 
         <td></td> 
         <td></td> 
        </tr>
       </c:forEach> 
      </tbody> 
     </table> 
    </div></td> 
  </tr> 
 </tbody> 
</table>
