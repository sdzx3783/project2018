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
   <td class="formHead" colspan="4" style="word-break: break-all;">合同详情</td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">合同类型 </td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${contractinfo.contracttype} </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">合同金额（万元）</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput">${contractinfo.contract_money}</td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">合同编号</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput">${contractinfo.contract_num}</td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">结算金额（万元）</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput">${contractinfo.settlement_money}</td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">合同名称</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">工程标底价</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">合同状态</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">工程中标价</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">甲方</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 12px; background-color: rgb(255, 255, 255);">费率</span></td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${contractinfo.rate} </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">乙方</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目内容</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">付费类型</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目规模</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">签约时间</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">合同是否备案</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><br /> <span> <label><input data="${contractinfo.isrecord}" type="radio" name="isrecord" value="0" lablename="合同是否备案" validate="{}" disabled="disabled" />否</label> <label><input data="${contractinfo.isrecord}" type="radio" name="isrecord" value="1" lablename="合同是否备案" validate="{}" disabled="disabled" />是</label> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目总监</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">竣工子资料是否存档</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span> <label><input data="${contractinfo.issave}" type="radio" name="issave" value="0" lablename="竣工子资料是否存档" validate="{}" disabled="disabled" />否</label> <label><input data="${contractinfo.issave}" type="radio" name="issave" value="1" lablename="竣工子资料是否存档" validate="{}" disabled="disabled" />是</label> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目状态<br /></td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">合同是否回收</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><br /> <span> <label><input data="${contractinfo.isrecovery}" type="radio" name="isrecovery" value="0" lablename="合同是否回收" validate="{}" disabled="disabled" />否</label> <label><input data="${contractinfo.isrecovery}" type="radio" name="isrecovery" value="1" lablename="合同是否回收" validate="{}" disabled="disabled" />是</label> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">合同归属部门</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><br /></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">系统内外</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><br /> <span> <label><input data="${contractinfo.inout}" type="radio" name="inout" value="0" lablename="系统内外" validate="{}" disabled="disabled" />内</label> <label><input data="${contractinfo.inout}" type="radio" name="inout" value="1" lablename="系统内外" validate="{}" disabled="disabled" />外</label> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">工程地址</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">开工时间</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">业主名称</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">完工时间</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目由来</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目负责人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">招标平台</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">合同经手人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">招标方式</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">合同审核人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">合同批签人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">备注</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${contractinfo.remark} </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">修改人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${contractinfo.updater} </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">创建人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${contractinfo.creater} </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">修改时间</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">创建时间</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">保管人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${contractinfo.custodian} </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">投资总额（万元）</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">进度</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${contractinfo.progress} </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">开票金额</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">开票时间</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><br /></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">到账金额</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">到账时间</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"><br /> 
    <div name="div_attachment_container" right="r"> 
     <div class="attachement"></div> 
     <textarea style="display:none" controltype="attachment" name="file" lablename="附件" readonly="readonly">${contractinfo.file}</textarea> 
    </div> </td> 
  </tr> 
 </tbody> 
</table>   
<div type="subtable" tablename="contract_billing_record" tabledesc="合同开票记录" show="true" parser="rowmodeedit" id="contract_billing_record" formtype="edit"> 
 <table class="listTable" border="0" cellpadding="2" cellspacing="0"> 
  <tbody> 
   <tr class="toolBar firstRow"> 
    <td colspan="8" style="word-break: break-all;"></td> 
   </tr> 
   <tr class="headRow"> 
    <th style="word-break: break-all;">序号</th> 
    <th style="word-break: break-all;">开票时间</th> 
    <th style="word-break: break-all;">发票金额</th> 
    <th style="word-break: break-all;">经办人</th> 
    <th style="word-break: break-all;">审批状态</th> 
    <th style="word-break: break-all;">领票人</th> 
    <th style="word-break: break-all;">到账时间</th> 
    <th style="word-break: break-all;">到账金额</th> 
   </tr> 
   <c:forEach items="${contract_billing_recordList}" var="contract_billing_record" varStatus="status">
    <tr class="listRow" type="subdata"> 
     <td class="tdNo" style="word-break: break-all;">${status.index+1}</td> 
     <td style="word-break: break-all;"></td> 
     <td style="word-break: break-all;"></td> 
     <td> ${contractBillingRecord.operator} </td> 
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${contractBillingRecord.status} </span> </td> 
     <td> ${contractBillingRecord.bearer} </td> 
     <td></td> 
     <td style="word-break: break-all;"></td> 
    </tr>
   </c:forEach> 
  </tbody> 
 </table> 
</div>    
<div style="text-align: center;"> 
 <span style="font-size: 12px;"> 
  <div type="subtable" tablename="contract_payment_record" tabledesc="合同付款记录" show="true" parser="rowmodeedit" id="contract_payment_record" formtype="edit"> 
   <table class="listTable" border="0" cellpadding="2" cellspacing="0"> 
    <tbody> 
     <tr class="toolBar firstRow"> 
      <td colspan="7"></td> 
     </tr> 
     <tr class="headRow"> 
      <th>序号</th> 
      <th style="word-break: break-all;">申请付款时间</th> 
      <th style="word-break: break-all;">申请付款金额</th> 
      <th style="word-break: break-all;">经办人</th> 
      <th style="word-break: break-all;">审批状态</th> 
      <th style="word-break: break-all;">付款时间</th> 
      <th style="word-break: break-all;">实际支付金额</th> 
     </tr> 
     <c:forEach items="${contract_payment_recordList}" var="contract_payment_record" varStatus="status">
      <tr class="listRow" type="subdata"> 
       <td class="tdNo">${status.index+1}</td> 
       <td><br /></td> 
       <td></td> 
       <td> ${contractPaymentRecord.operator} </td> 
       <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${contractPaymentRecord.status} </span> </td> 
       <td></td> 
       <td></td> 
      </tr>
     </c:forEach> 
    </tbody> 
   </table> 
  </div></span> 
</div>
