<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
    <head>
        <title>编辑 工时填报申请</title>
        <%@include file="/codegen/include/customForm.jsp" %>
        <%@include file="/commons/include/ueditor.jsp" %>
        <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
        <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
        <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
        <style>     
            .formTable{
                margin-bottom: 20px;
            }
            table.listTable{
                /* table-layout: fixed; */
                margin-bottom: 10px;
            }
            .my-sort {
            	padding: 0 20px
            }
            .my-sort p {
            	margin-top: 5px;	
            }
            .my-sort span {
            	font-size: 14px;
            }
            a.link.add {
            	border: 0;
			    color: #fff;
			    padding: 3px 15px 3px 15px;
			    background: #478de4 !important;
			    -moz-box-shadow: 0 1px 1px rgba(0,0,0,0.15);
			    -webkit-box-shadow: 0 1px 1px rgba(0,0,0,0.15);
			    line-height: 20px;
			    box-shadow: 0 1px 1px rgba(0,0,0,0.15);
			    border-radius: 3px;
			    margin-bottom: 8px;
            }
            a.link.add:hover {
            	background: #5ca0f5 !important;
            }
            table.listTable tr.listRow td {
            	padding-top: 6px;
            	padding-bottom: 6px;
            }
            .inputText, select {
            	width: 90%;
    			height: 26px;
    			line-height: 26px;
       		 	border: 1px solid #8c9fd6;
            	border-radius: 3px;
            }
            .inputTextMin{
            	width: 50px;
            }
            select {
            	width: 200px;
            	height: 32px;
            	line-height: 32px;
            }
            .formTable .formTitle {
            	white-space: nowrap;
            }
            input[readonly] {
            	background: #eee;
            }
            input[readonly].Wdate {
            	background: #fff;
            }
            .projectTaskHourProjectNameField{
                text-align: left !important;
    			display: inline-block;
    			float: left;
            }
            .oa-button-label.del{
            	    padding: 0px 0px;
            }
        </style>
    </head>
    <body>
    	<input type="hidden" value="${isHjsyb }" id="isHjsyb">
    	<input type="hidden" value="${fn:length(projectTaskHourList)}" id="projectTaskHourListSize">
        <div class="panel" style="height:100%;overflow:auto;">
            <div class="panel-top">
                <div class="tbar-title">
                    <c:choose>
                    <c:when test="${not empty workHourApplicationItem.id}">
                    <span class="tbar-label"><span></span>编辑工时填报申请</span>
                    </c:when>
                    <c:otherwise>
                    <span class="tbar-label"><span></span>添加工时填报申请</span>
                    </c:otherwise>
                    </c:choose>
                </div>
                <div class="panel-toolbar">
                    <div class="toolBar">
                        <div class="group"><a class="link save" id="dataFormSave" href="javascript:;">保存</a></div>
                         <c:if test="${canStartFlow }"><div class="l-bar-separator"></div>
                        <div class="group"><a class="link submit" href="javascript:;">提交</a></div></c:if>
                        <div class="l-bar-separator"></div>
                        <div class="group"><a class="link back" href="list.ht">返回</a></div>
                        <div class="group" id="filehelp"  style="display: none;"><a class="link back" target="_blank"  href="/makshi/doc/docinfo/getDocFilesByNum.ht?filenum=50000000000533">帮助</a></div>
                        
                    </div>
                </div>
            </div>
            <form id="workHourApplicationForm" method="post" action="save.ht">
                <div type="custform">
                    <div class="panel-detail">
                        <table class="formTable" border="1" cellspacing="0" cellpadding="2" data-sort="sortDisabled" parser="addpermission">
                            <tbody>
                                <tr class="firstRow">
                                    <td class="formHead" colspan="6">考勤填报(工作日志)</td>
                                </tr>
                                <tr>
                                    <td align="right" class="formTitle">姓名:</td>
                                    <td class="formInput"> <input name="m:work_hour_application:applicant" type="text" value="${workHourApplication.applicant}" validate="{empty:false}" readonly="readonly"  /><input name="m:work_hour_application:applicantID" type="hidden" value="${workHourApplication.applicantID}" validate="{empty:false}" readonly="readonly"  /> </td>
                                    <td align="right" class="formTitle">所属部门:</td>
                                    <td class="formInput"> <input name="m:work_hour_application:org" type="text" value="${workHourApplication.org}"  validate="{empty:false}" readonly="readonly" /> <input name="m:work_hour_application:orgID" type="hidden" value="${workHourApplication.orgID}"  validate="{empty:false}" readonly="readonly" /></td>
                                    <td align="right" class="formTitle">考勤日期:</td>
                                    <td class="formInput">
                                    	<input name="m:work_hour_application:applicant_time" id="applicantTime" onclick="WdatePicker({onpicked:changeTime,maxDate:new Date()})" readonly="readonly"  parser="datetable"  class="Wdate oa-input" displaydate="0" type="text" value="<fmt:formatDate value='${workHourApplication.applicant_time}' pattern='yyyy-MM-dd'/>">
                                    </td>
                                    <%-- <td class="formInput"><input onclick="WdatePicker({minDate:'%y-%M-{%d-3}', maxDate:'%y-%M-%d'})" name="m:work_hour_application:applicant_time" id="applicantTime" onchange="changeTime()" readonly="readonly"  parser="datetable"  class="Wdate oa-input" displaydate="0" type="text" value="<fmt:formatDate value='${workHourApplication.applicant_time}' pattern='yyyy-MM-dd'/>"></td> --%>
                                </tr>
                                <tr>
                                	<td align="right" class="formTitle" >累计工作时间比例:</td>
                                    <td class="formInput" ${!isHjsyb?'colspan="5"':''}><input name="m:work_hour_application:work_hour_rate" id="work_hour_rate" readonly="readonly" type="text" validate='{"number":"true","maxIntLen":"3","maxDecimalLen":"0","range":[0,100]}' value="${workHourApplication.work_hour_rate }"/>%</td>
                                    <td align="right" style="display:none" class="formTitle">累计正常工时:</td>
                                    <td class="formInput" style="display:none"><input name="m:work_hour_application:work_hour_total" readonly="readonly" type="text" validate='{"number":"true","maxIntLen":"10","maxDecimalLen":"2","maxlength":"50"}' value="${workHourApplication.work_hour_total }"/></td>
									<td style="word-break: break-all; <c:if test='${!isHjsyb}'>display:none;</c:if>"  align="right" class="formTitle">考勤制度:</td>
                                    <td class="formInput"  style="word-break: break-all;<c:if test='${!isHjsyb}'>display:none;</c:if>"   colspan="3"><select style="width: 150px; height: 18px;" name="m:work_hour_application:basehour" validate="{&quot;required&quot;:&quot;true&quot;}">
                                    	<option value="7" <c:if test="${workHourApplication.basehour==7 }">selected=selected</c:if>>7小时制</option>
                                    	<option value="3" <c:if test="${workHourApplication.basehour==3 }">selected=selected</c:if>>3小时制</option>
                                    	<option value="8" <c:if test="${workHourApplication.basehour==8 }">selected=selected</c:if>>8小时倒班制（环境事业部）</option>
                                    	<option value="12" <c:if test="${workHourApplication.basehour==12 }">selected=selected</c:if>>12小时倒班制（环境事业部）</option>
                                    	</select>
                                    </td>
                                    <td align="right" style="display:none" class="formTitle">累计加班工时:</td>
                                    <td class="formInput" style="display:none"  rowspan="1"><input name="m:work_hour_application:overtime_hour_total"  readonly="readonly" type="text" validate='{"number":"true","maxIntLen":"10","maxDecimalLen":"2","maxlength":"50"}' value="${workHourApplication.overtime_hour_total }"/></td>
                                </tr>
                            </tbody>
                        </table>
                        <div type="subtable"  parser="rowmodeedit" show="true" tabledesc="项目任务工时" tablename="projectTaskHour" id="projectTaskHour" formtype="edit">
                            <table class="listTable" border="0" cellspacing="0" cellpadding="2">
                                <tbody>
                                    <tr class="toolBar firstRow" style="display: none;">
                                        <td colspan="5"><a class="link add" id="addtaskbtn" href="javascript:;">添加</a></td>
                                    </tr>
                                    <tr class="headRow">
                                        <th width="26%">项目名称<br /></th>
                                        <th width="29%">任务名称</th>
                                        <th width="8%">工作时间比例</th>
                                        <th style="display:none">正常工时</th>
                                        <th style="display:none">加班工时</th>
                                        <th width="8%">进度</th>
                                        <th>备注</th>
                                        <th>操作</th>
                                    </tr>
                                    <c:forEach items="${projectTaskHourList}" var="projectTaskHour" varStatus="status">
                                    <tr class="listRow forbidevent" type="subdata" >
                                        <td> <span name="editable-input" isflag="tableflag"> 
                                        <span class="projectTaskHourProjectNameField">${projectTaskHour.projectName}</span>
                                        <input type="hidden" name="s:projectTaskHour:projectName" readonly="readonly" lablename="项目名称" class="inputText" value="${projectTaskHour.projectName}" validate="{maxlength:250}" isflag="tableflag" />  <input type="hidden" name="s:projectTaskHour:proid" lablename="项目id" value="${projectTaskHour.proid}" validate="{maxlength:50}" isflag="tableflag" /></span> </td>
                                        <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:projectTaskHour:taskName" lablename="任务名称" class="inputText" value="${projectTaskHour.taskName}" validate="{maxlength:250}" isflag="tableflag" /> <input type="hidden" name="s:projectTaskHour:taskid" lablename="任务id"  value="${projectTaskHour.taskid}" validate="{maxlength:50}" isflag="tableflag" /></span> </td>
                                        <td><span name="editable-input" isflag="tableflag"> <input type="text" name="s:projectTaskHour:project_work_rate"  lablename="工作时间比例" class="inputText inputTextMin" oninput="calWorkhourRate()" value="${projectTaskHour.project_work_rate}" validate='{"number":"true","maxIntLen":"3","maxDecimalLen":"0","range":[0,100]}' isflag="tableflag" /> </span>%</td>
                                        <td style="display:none"><span name="editable-input" isflag="tableflag"> <input type="text" name="s:projectTaskHour:work_hour"  lablename="正常工时" class="inputText inputTextMin" oninput="calWorkhour()" value="${projectTaskHour.work_hour}" validate='{"number":"true","maxIntLen":"10","maxDecimalLen":"2","maxlength":"50"}' isflag="tableflag" /> </span></td>
                                        
                                        <td style="display:none"><span name="editable-input" isflag="tableflag"> <input type="text" name="s:projectTaskHour:overtime_hour" lablename="加班工时" class="inputText inputTextMin" oninput="calOvertimehour()" value="${projectTaskHour.overtime_hour}" validate='{"number":"true","maxIntLen":"10","maxDecimalLen":"2","maxlength":"50"}' isflag="tableflag" /> </span> </td>
                                        <td><span name="editable-input" isflag="tableflag"> <input type="text" name="s:projectTaskHour:progress_rate" lablename="进度" class="inputText inputTextMin" value="${projectTaskHour.progress_rate}" validate="{maxlength:50}" isflag="tableflag" /> </span></td>
                                        <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:projectTaskHour:remark" lablename="备注" class="inputText " value="${projectTaskHour.remark}" validate="{maxlength:500}" isflag="tableflag" /> </span> </td>
                                        <td></td>
                                        <input name="s:projectTaskHour:id" type="hidden" value="${projectTaskHour.id}" />
                                    </tr>
                                    </c:forEach>
                                    <tr class="listRow forbidevent" type="append" style="display:none;">
                                        <td> <span name="editable-input" isflag="tableflag"> 
                                        <span class="projectTaskHourProjectNameField"></span>
                                        <input type="hidden" name="s:projectTaskHour:projectName"   lablename="任务名称" class="inputText" validate="{maxlength:250}" isflag="tableflag" /><input type="hidden" name="s:projectTaskHour:proid" lablename="项目id"  validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                                        <td> <span name="editable-input" isflag="tableflag"> <input type="text"  name="s:projectTaskHour:taskName" lablename="任务名称" class="inputText"  validate="{maxlength:250}" isflag="tableflag" /><input type="hidden" name="s:projectTaskHour:taskid" lablename="任务id"   validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                                        <td><span name="editable-input" isflag="tableflag"> <input type="text"  name="s:projectTaskHour:project_work_rate" lablename="工作时间比例" class="inputText inputTextMin" oninput="calWorkhourRate()"  validate='{"number":"true","maxIntLen":"3","maxDecimalLen":"0","range":[0,100]}' isflag="tableflag" value="0"/> %</span></td>
                                        <td style="display:none"><span name="editable-input" isflag="tableflag"> <input type="text" name="s:projectTaskHour:work_hour" lablename="正常工时" class="inputText inputTextMin" oninput="calWorkhour()"  validate='{"number":"true","maxIntLen":"10","maxDecimalLen":"2","maxlength":"50"}' isflag="tableflag" /> </span></td>
                                        <td style="display:none"><span name="editable-input" isflag="tableflag"> <input type="text" name="s:projectTaskHour:overtime_hour" lablename="加班工时" class="inputText inputTextMin" oninput="calOvertimehour()"  validate='{"number":"true","maxIntLen":"10","maxDecimalLen":"2","maxlength":"50"}' isflag="tableflag" /> </span> </td>
                                        <td><span name="editable-input" isflag="tableflag"> <input type="text" name="s:projectTaskHour:progress_rate" lablename="进度" class="inputText inputTextMin"  validate="{maxlength:50}" isflag="tableflag" /> </span></td>
                                        <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:projectTaskHour:remark" lablename="备注" class="inputText"  validate="{maxlength:500}" isflag="tableflag" /> </span> </td>
                                        <td></td>
                                        <input name="s:projectTaskHour:id" type="hidden"  value="" />
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div type="subtable" parser="rowmodeedit" show="true" tabledesc="自定义任务工时" tablename="customTaskHour" id="customTaskHour" formtype="edit">
                            <table class="listTable" border="0" cellspacing="0" cellpadding="2">
                                <tbody>
                                    <tr class="toolBar firstRow">
                                        <td colspan="5"><a class="link add" href="javascript:;">添加</a></td>
                                    </tr>
                                    <tr class="headRow">
                                        <th width="26%">任务名称</th>
                                        <th width="29%">工作内容</th>
                                        <th width="8%">工作时间比例</th>
                                        <th style="display:none">正常工时</th>
                                        <th style="display:none">加班工时</th>
                                        <th width="8%">进度</th>
                                        <th>备注</th>
                                        <th>操作</th>
                                    </tr>
                                    <c:forEach items="${customTaskHourList}" var="customTaskHour" varStatus="status">
                                    <tr class="listRow" type="subdata">
                                        <td> <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> <select name="s:customTaskHour:taskName" lablename="任务名称" controltype="select" validate="{empty:false}" val="${customTaskHour.taskName}"> <option value="1">培训</option> <option value="2">会议</option> <option value="3">请假</option> <option value="4">集体活动</option> <option value="5">空闲</option><option value="7">日常工作</option> <option value="6">其他</option> </select> </span> </td>
                                        <td><span name="editable-input" isflag="tableflag"> <input type="text" name="s:customTaskHour:work_content" lablename="备注" class="inputText" value="${customTaskHour.work_content}" validate="{maxlength:250}" isflag="tableflag" /> </span> </td>
                                        <td><span name="editable-input" isflag="tableflag"> <input type="text" name="s:customTaskHour:task_work_rate" lablename="备注" class="inputText inputTextMin " value="${customTaskHour.task_work_rate}" oninput="calWorkhourRate()" validate='{"number":"true","maxIntLen":"3","maxDecimalLen":"0","range":[0,100]}' isflag="tableflag" /> </span>%</td>
                                        <td style="display:none"><span name="editable-input" isflag="tableflag"> <input type="text" name="s:customTaskHour:work_hour" lablename="备注" class="inputText inputTextMin" value="${customTaskHour.work_hour}" oninput="calWorkhour()" validate='{"number":"true","maxIntLen":"10","maxDecimalLen":"2","maxlength":"50"}' isflag="tableflag" /> </span></td>
                                        <td style="display:none"><span name="editable-input" isflag="tableflag"> <input type="text" name="s:customTaskHour:overtime_hour" lablename="备注" class="inputText inputTextMin" value="${customTaskHour.overtime_hour}" oninput="calOvertimehour()" validate='{"number":"true","maxIntLen":"10","maxDecimalLen":"2","maxlength":"50"}' isflag="tableflag" /> </span></td>
                                        <td><span name="editable-input" isflag="tableflag"> <input type="text" name="s:customTaskHour:progress_rate" lablename="备注" class="inputText inputTextMin" value="${customTaskHour.progress_rate}" validate="{maxlength:50}" isflag="tableflag" /> </span></td>
                                        <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:customTaskHour:remark" lablename="备注" class="inputText" value="${customTaskHour.remark}" validate="{maxlength:500}" isflag="tableflag" /> </span></td>
                                        <td><span class="oa-button-label del">删除</span></td>
                                        <input name="s:customTaskHour:id" type="hidden"  value="${customTaskHour.id}" />
                                    </tr>
                                    </c:forEach>
                                    <tr class="listRow" type="append" style="display:none;">
                                        <td> <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> <select name="s:customTaskHour:taskName" lablename="任务名称" controltype="select" validate="{empty:false}" ><option value="7" selected="selected">日常工作</option> <option value="1">培训</option> <option value="2">会议</option> <option value="3">请假</option> <option value="4">集体活动</option> <option value="5">空闲</option> <option value="6">其他</option> </select> </span> </td>
                                        <td><span name="editable-input" isflag="tableflag"> <input type="text" name="s:customTaskHour:work_content" lablename="备注" class="inputText"  validate="{maxlength:250}" isflag="tableflag" /> </span> </td>
                                        <td><span name="editable-input" isflag="tableflag"> <input type="text" name="s:customTaskHour:task_work_rate" lablename="备注" class="inputText inputTextMin" oninput="calWorkhourRate()" validate='{"number":"true","maxIntLen":"3","maxDecimalLen":"0","range":[0,100]}' isflag="tableflag" value="0" /> </span>%</td>
                                        <td style="display:none"><span name="editable-input" isflag="tableflag"> <input type="text" name="s:customTaskHour:work_hour" lablename="备注" class="inputText inputTextMin" oninput="calWorkhour()" validate='{"number":"true","maxIntLen":"10","maxDecimalLen":"2","maxlength":"50"}' isflag="tableflag" /> </span></td>
                                        <td style="display:none"><span name="editable-input" isflag="tableflag"> <input type="text" name="s:customTaskHour:overtime_hour" lablename="备注" class="inputText inputTextMin"  oninput="calOvertimehour()" validate='{"number":"true","maxIntLen":"10","maxDecimalLen":"2","maxlength":"50"}' isflag="tableflag" /> </span></td>
                                        <td><span name="editable-input" isflag="tableflag"> <input type="text" name="s:customTaskHour:progress_rate" lablename="备注" class="inputText inputTextMin" validate="{maxlength:500}" isflag="tableflag" /></span> </td>
                                        <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:customTaskHour:remark" lablename="备注" class="inputText"  validate="{maxlength:500}" isflag="tableflag" /></span></td>
                                        <td><span class="oa-button-label del">删除</span> </td>
                                        <input name="s:customTaskHour:id" type="hidden"  value="${customTaskHour.id}" />
                                    </tr>
                                </tbody>
                            </table>
                            
                        </div>
                    </div>
                </div>
                <input type="hidden" name="id" value="${workHourApplication.id}"/>
                <input type="hidden" id="saveData" name="saveData"/>
                <input type="hidden" name="executeType"  value="start" />
                <input type="hidden" id="optype" name="optype"  value="0" />
            </form>
            <div class="my-sort">
            	<span>填写说明：</span>
				<p>1. 日期默认为当前编辑日期，可选择；可补录当前日期前3天的数据，每天一条数据；保存后的工时记录（按天）三天内必须上报审批，超过3天无法提交审批（和只允许补录前3天的数据同理）。</p>
				<p>2.默认带出的项目是当前用户所选择的填写日期在项目开工时间和项目截止时间范围内的所有项目（如项目状态为未开工、停工和已完工，则不带出，只带出在建状态项目）； 如果该项目已完工，需在部门——项目管理里面更新项目状态xxxxx。</p>
				<p>3.工作时间占比只允许填写整数，不能超过100；“累计工作时间比例”不用填写，自动汇总各项工作时间比例。</p>
				<p>4.任务名称、进度为非必填项，只作为审批时的参考。</p>
				<p>5.其他任务名称为下拉选择项。类型：培训、会议、请假、集体活动、空闲、其他，工作内容为手填。</p>
            </div>
            <script type="text/javascript" src="${ctx }/js/makshi/finance/workHourApplicationEdit.js"></script>
        </body>
    </html>
