<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <title id="executiveTitle">员工考核</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<style type="text/css">
	 .formTitle{text-align:center !important;}
    .line{
        padding: 0 10px;
    }
    .line .oa-input-wrap--ib{
        
        margin-right: 20px;
    }
    .line .oa-input-wrap--ib div{
        display: inline;
    }
    .oa-table--default th, .oa-table--default td {
        padding: 10px 10px;
        border: 1px solid #eceff8;
    }
    .line .time{
        float: right;
    }
    input.oa-new-input, input[data-class=oa-new-input] {
      border: 1px solid #8c9fd6 !important;
    }
    table.oa-table--default .oa-new-input {
      width: 94px;
    }
    .border-wrapper {
      border-left: 1px solid #eceff8;
      border-right: 1px solid #eceff8;
      padding: 10px 0px 10px;
    }</style>
</head>
<body>
	<div class="panel-top">
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:;"><span></span>保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back " href="/makshi/assess/employee/main.ht"><span></span>返回</a></div>
			</div>
		</div>
	</div>
	 
	<div class="oa-pdb20 oa-mgh20">
		<div id="oa_list_content" class="oa-table-scroll-wrap">	
			<div id="result_field">
			  <form id="employeeForm" method="post" action="save.ht">
				<div style="width:980px;border: 1px solid #dadfed;margin:auto;">
				  <p style="text-align: center;"><strong><span style="font-size:24px;line-height:125%;font-family:宋体;font-weight:bold;">办公室员工考核表</span></strong></p>
				  <p><br></p>
				  <div class="oa-mw-page">
				   	<div class="border-wrapper">
				    	<div class="line table-head">
						     <div class="oa-input-wrap--ib" style="margin-left:30px;">
						    	  姓名：${dto.employee }
						      	<input type="hidden" value="${dto.employee }" name="m:employee_assess:employee">
						      	<input type="hidden" value="${dto.employeeID }" name="m:employee_assess:employeeID">
						     </div>
						     <div class="oa-input-wrap--ib" style="margin-left:60px;">
						     	 部门：${dto.department }
						      	<input name="m:employee_assess:departmentID" type="hidden" lablename="departmentID" class="hidden" value="${dto.departmentID }">
						      	<input type="hidden"  value="${dto.department }"   name="m:employee_assess:department">
						     </div>
						     <div class="oa-input-wrap--ib" style="margin-left:60px;">
						     	 岗位：${dto.post_name }
						      	<input type="hidden"  id="posName" name="m:employee_assess:post_name" value="${dto.post_name }">
						     </div>
						     <div class="oa-input-wrap--ib" style="margin-left:60px;">
						      	考评日期：<input type="text" style="width: 100px; height: 30px; border: 1px solid rgb(218, 223, 237);"
						       value="<fmt:formatDate value="${dto.evaluation_at }" pattern="yyyy年MM月"/>" 
						       name="m:employee_assess:evalAt" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy年MM月',maxDate:'%y-%M'})" validate="{required:true}" >
						     </div>
				    	</div>
				   	</div>
				  </div>
				  <table class="formTable" border="1" cellspacing="0" cellpadding="2" data-sort="sortDisabled">
				   	<tbody>
					    <tr class="firstRow" style="height:55px;">
					     <td style="width: 10%; word-break: break-all;text-align:center;" class="formTitle" align="right">考核因素</td>
					     <td style="width: 35%; word-break: break-all;text-align:center;" class="formTitle">对考核期间工作的考核要点</td>
					     <td style="width: 10%; word-break: break-all;text-align:center;" class="formTitle" width="66">自评（40%）</td>
					     <td style="width: 10%; word-break: break-all;text-align:center;" class="formTitle" width="71">部门评价（40%）</td>
					     <td style="width: 15%; word-break: break-all;text-align:center;" class="formTitle" width="100">分管领导评价（20%）</td>
					     <td style="width: 10%; word-break: break-all;text-align:center;" class="formTitle" width="71">考核成绩</td>
					    </tr>
					    <tr>
					     <td class="formTitle" align="right" rowspan="4" colspan="1">1.职业道德</td>
					     <td class="formInput" rowspan="4" colspan="1" style="line-height:30px;"><p>A．热爱集体，有团队合作精神，互相配合支持工作。</p><p>B．忠于职守，严守岗位，保守公司机密。</p><p>C．钻研业务，勤奋好学，及时了解掌握相关法律法规。</p><p>D．对内对外服务热情周到。</p></td>
					     <td class="formTitle zy1"><input type="text" validate="{required:true,range:[0,6]}" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_zy_self1" value="${dto.self_zy_self1 }"  ></td>
					     <td class="formTitle zy1"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_zy_depart1" value="" ></td>
					     <td class="formTitle zy1"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_zy_leader1" value="" ></td>
					     <td class="formTitle" id="zytotal1"></td>
					    </tr>
					    <tr>
					     <td class="formTitle zy2"><input type="text" validate="{required:true,range:[0,6]}" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_zy_self2" value="${dto.self_zy_self2 }" ></td>
					     <td class="formTitle zy2" style="word-break: break-all;"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_zy_depart2" value="" ></td>
					     <td class="formTitle zy2"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_zy_leader2" value="" ></td>
					     <td class="formTitle" id="zytotal2"></td>
					    </tr>
					    <tr>
					     <td class="formTitle zy3"><input type="text" validate="{required:true,range:[0,6]}" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_zy_self3" value="${dto.self_zy_self3 }" ></td>
					     <td class="formTitle zy3"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_zy_depart3" value="" ></td>
					     <td class="formTitle zy3"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_zy_leader3" value="" ></td>
					     <td class="formTitle" id="zytotal3"></td>
					    </tr>
					    <tr>
					     <td class="formTitle zy4"><input type="text" validate="{required:true,range:[0,6]}" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_zy_self4" value="${dto.self_zy_self4 }" ></td>
					     <td class="formTitle zy4"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_zy_depart4" value="" ></td>
					     <td class="formTitle zy4"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_zy_leader4" value="" ></td>
					     <td class="formTitle" id="zytotal4"></td>
					    </tr>
					    <tr>
					     <td class="formTitle" align="right" rowspan="4" colspan="1" style="word-break: break-all;">2.工作态度</td>
					     <td class="formInput" rowspan="4" colspan="1" style="line-height: 30px; word-break: break-all;"><p>A．把工作放在第一位，努力做好本职工作。</p><p>B．服务意识强，在工作中始终保持协作态度。</p><p>C．有高度责任感，不推托工作，不推卸责任。</p><p>D．对临时工作或新工作表现出积极态度，不讨价还价。</p></td>
					     <td class="formTitle td1"><input type="text" validate="{required:true,range:[0,6]}" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_td_self1" value="${dto.self_td_self1 }"  ></td>
					     <td class="formTitle td1"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_td_depart1" value="" ></td>
					     <td class="formTitle td1"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_td_leader1" value="" ></td>
					     <td class="formTitle" id="tdtotal1"></td>
					    </tr>
					    <tr>
					     <td class="formTitle td2"><input type="text" validate="{required:true,range:[0,6]}" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_td_self2" value="${dto.self_td_self2 }" ></td>
					     <td class="formTitle td2" style="word-break: break-all;"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_td_depart2" value="" ></td>
					     <td class="formTitle td2"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_td_leader2" value="" ></td>
					     <td class="formTitle" id="tdtotal2"></td>
					    </tr>
					    <tr>
					     <td class="formTitle td3"><input type="text" validate="{required:true,range:[0,6]}" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_td_self3" value="${dto.self_td_self3 }" ></td>
					     <td class="formTitle td3"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_td_depart3" value="" ></td>
					     <td class="formTitle td3"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_td_leader3" value="" ></td>
					     <td class="formTitle" id="tdtotal3"></td>
					    </tr>
					    <tr>
					     <td class="formTitle td4"><input type="text" validate="{required:true,range:[0,6]}" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_td_self4" value="${dto.self_td_self4 }" ></td>
					     <td class="formTitle td4"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_td_depart4" value="" ></td>
					     <td class="formTitle td4"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_td_leader4" value="" ></td>
					     <td class="formTitle" id="tdtotal4"></td>
					    </tr>
					    <tr>
					     <td class="formTitle" align="right" rowspan="4" colspan="1" style="word-break: break-all;">3.工作能力</td>
					     <td class="formInput" rowspan="4" colspan="1" style="line-height: 30px; word-break: break-all;"><p>A．正确理解工作指示和方针，完成工作及时。</p><p>B．主动努力改善工作和提高效率。</p><p>C．及时与有关部门进行必要的工作联系。</p><p>D．及时反馈各种信息，工作无差错。</p></td>
					     <td class="formTitle nl1"><input type="text" validate="{required:true,range:[0,6]}" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_nl_self1" value="${dto.self_nl_self1 }"  ></td>
					     <td class="formTitle nl1"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_nl_depart1" value="" ></td>
					     <td class="formTitle nl1"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_nl_leader1" value="" ></td>
					     <td class="formTitle" id="nltotal1"></td>
					    </tr>
					    <tr>
					     <td class="formTitle nl2"><input type="text" validate="{required:true,range:[0,6]}" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_nl_self2" value="${dto.self_nl_self2 }" ></td>
					     <td class="formTitle nl2" style="word-break: break-all;"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_nl_depart2" value="" ></td>
					     <td class="formTitle nl2"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_nl_leader2" value="" ></td>
					     <td class="formTitle" id="nltotal2"></td>
					    </tr>
					    <tr>
					     <td class="formTitle nl3"><input type="text" validate="{required:true,range:[0,6]}" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_nl_self3" value="${dto.self_nl_self3 }" ></td>
					     <td class="formTitle nl3"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_nl_depart3" value="" ></td>
					     <td class="formTitle nl3"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_nl_leader3" value="" ></td>
					     <td class="formTitle" id="nltotal3"></td>
					    </tr>
					    <tr>
					     <td class="formTitle nl4"><input type="text" validate="{required:true,range:[0,6]}" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_nl_self4" value="${dto.self_nl_self4 }" ></td>
					     <td class="formTitle nl4"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_nl_depart4" value="" ></td>
					     <td class="formTitle nl4"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_nl_leader4" value="" ></td>
					     <td class="formTitle" id="nltotal4"></td>
					    </tr>
					    <tr>
					     <td class="formTitle" align="right" rowspan="4" colspan="1" style="word-break: break-all;">4.工作效果</td>
					     <td class="formInput" rowspan="4" colspan="1" style="line-height: 30px; word-break: break-all;"><p>A．工作成绩达到预期目标或计划要求。</p><p>B．工作汇报准确真实</p><p>C．相关部门或人事关系方面没有不满和怨言。</p><p>D．注意进行目标管理，使工作协调进行。</p></td>
					     <td class="formTitle xg1"><input type="text" validate="{required:true,range:[0,7]}" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_xg_self1" value="${dto.self_xg_self1 }"  ></td>
					     <td class="formTitle xg1"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_xg_depart1" value="" ></td>
					     <td class="formTitle xg1"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_xg_leader1" value="" ></td>
					     <td class="formTitle" id="xgtotal1"></td>
					    </tr>
					    <tr>
					     <td class="formTitle xg2"><input type="text" validate="{required:true,range:[0,7]}" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_xg_self2" value="${dto.self_xg_self2 }" ></td>
					     <td class="formTitle xg2" style="word-break: break-all;"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_xg_depart2" value="" ></td>
					     <td class="formTitle xg2"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_xg_leader2" value="" ></td>
					     <td class="formTitle" id="xgtotal2"></td>
					    </tr>
					    <tr>
					     <td class="formTitle xg3"><input type="text" validate="{required:true,range:[0,7]}" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_xg_self3" value="${dto.self_xg_self3}" ></td>
					     <td class="formTitle xg3"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_xg_depart3" value="" ></td>
					     <td class="formTitle xg3"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_xg_leader3" value="" ></td>
					     <td class="formTitle" id="xgtotal3"></td>
					    </tr>
					    <tr>
					     <td class="formTitle xg4"><input type="text" validate="{required:true,range:[0,7]}" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_xg_self4" value="${dto.self_xg_self4}" ></td>
					     <td class="formTitle xg4"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_xg_depart4" value="" ></td>
					     <td class="formTitle xg4"><input type="hidden" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_xg_leader4" value="" ></td>
					     <td class="formTitle" id="xgtotal4"></td>
					    </tr>
					    <tr>
					     <td class="formInput" align="right" colspan="2" rowspan="1" style="text-align: center; word-break: break-all;">合计</td>
					     <td class="formTitle selftotal"><input type="text" validate="{required:true}" id="selftotal" readonly="readonly" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:self_total" value="${dto.self_total }" validate="{&quot;number&quot;:&quot;true&quot;,&quot;maxIntLen&quot;:&quot;14&quot;,&quot;maxDecimalLen&quot;:&quot;0&quot;,&quot;maxlength&quot;:&quot;50&quot;}" ></td>
					     <td class="formTitle departtotal"><input type="hidden" id="departtotal" readonly="readonly" style="width: 80px; height: 18px;min-width:80px;" name="m:employee_assess:depart_total" value="0" ></td>
					     <td class="formTitle leadertotal" style="word-break: break-all;"></td>
					     <td class="formTitle" id="alltotal"></td>
					    </tr>
					    <tr>
					     <td class="formInput" align="right" colspan="6" rowspan="1" style="line-height: 30px; word-break: break-all;"><p>备注：</p><p>1、员工自评占比40%；部门评价占比40%；分管领导评价占比20%。</p><p>2、评分等级（职业道德、工作态度、工作能力）：很好为6分；较好为5分；一般为4分；较差为3分。</p><p>3、评分等级（工作效果）：很好为7分；较好为6分；一般为5分；较差为4分。</p><p>4、考核成绩为各项评分按比例计算后之和。</p></td>
					    </tr>
				   	 </tbody>
				   </table>
				   	<input type="hidden" name="id" value="${dto.id}"/>
					<input type="hidden" id="saveData" name="saveData"/>
				</div>
				</form>
			</div>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
	<input type="hidden" name="employeeid" id="employeeid" value="${dto.id}"/>
	<script type="text/javascript">
		$("#executiveTitle").text($(".executivetitle").text());
	</script>
	<script type="text/javascript" src="${ctx}/js/makshi/assess/employee-edit.js"></script>
</body>
</html>
