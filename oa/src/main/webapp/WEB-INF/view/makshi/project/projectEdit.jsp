<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>编辑项目</title>
    <%@include file="/codegen/include/customForm.jsp" %>
    <%@include file="/commons/include/ueditor.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
     <script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysPlanScript.js"></script>
    <style>
    	.oa-table{
    		font-size: 14px;
    	}
    	.oa-table td,
    	.oa-table th{
    		padding: 10px 5px;
    	}
    	.oa-input-wrap,
        .oa-select-wrap{
    		display: inline-block;
    	}

        .oa-table--form dt{
            float: left;
            line-height: 32px;
        }
        .oa-table--form dd{
            margin-left: 120px;
        }

    </style>
</head>
<body class="oa-mw-page">
    <div class="oa-project-title">
        <h2>编辑项目（项目ID:${project.id }）</h2>
    </div>
    <div class="oa-top-wrap">
        <a class="oa-button oa-button--primary oa-button--blue"  href="/makshi/project/project/list.ht?classifylibraryid=${project.classifylibraryid }">返回列表</a>
        <a class="oa-button oa-button--primary oa-button--blue"  href="getChangeHisView.ht?id=${project.id }">变更历史</a>
        <a class="oa-button oa-button--primary oa-button--blue" id="dataFormSave" href="javascript:;">保存</a>
    </div>
    <div class="oa-main-wrap">
        <form id="projectForm" method="post" action="save.ht">
            <table class="oa-table oa-table--form">
                <tr>
                    <td>
                        <dl>
                            <dt>项目分类：</dt>
                            <dd>
                                <div class="oa-input-wrap">
                                    <input class="oa-input" type="text" readonly="readonly" value="${classifyLibrary.name}">
                                </div>
                            </dd>
                        </dl>
                    </td>
                    <td>
                        <dl>
                            <dt>项目名称：</dt>
                            <dd>
                                <div class="oa-input-wrap">
                                    <input class="oa-input" type="text" name="name" value="${project.name}">
                                </div>  
                            </dd>
                        </dl>
                    </td>
                </tr>

                <tr>
                    <td>
                        <dl>
                            <dt>项目负责人：</dt>
                            <dd>
                                <div class="oa-input-wrap oa-input--readonly">
                                    <input name="applicant" type="text" ctltype="selector" class="users oa-input oa-hidden-trigger" value="${project.applicant}" validate="{empty:false}" readonly="readonly" />
                                </div>
                                <button class="oa-button-label" onclick="chooseUser(this,'chargeDiv','applicantID','applicant','no');" type="button">选择用户</button>
                            </dd>
                        </dl>
                    </td>
                    <td>
                        <dl>
                            <dt>项目组成员：</dt>
                            <dd>
                                <div class="oa-input-wrap oa-input--readonly">
                                    <input name="member"  type="text" ctltype="selector" class="users oa-input oa-hidden-trigger"  value="${project.member}" validate="{empty:false}" readonly="readonly" />
                                </div>
                                <button class="oa-button-label oa-trigger-hidden-button" type="button">选择用户</button>
                            </dd>
                        </dl>
                    </td>
                </tr>
                <tr>
                    <td>
                        <dl>
                            <dt>项目开工时间：</dt>
                            <dd>
                                <div class="oa-input-wrap">
                                    <input type="text" id="expectstarttime" name="expectstarttime" value="<fmt:formatDate value='${project.expectstarttime}' pattern='yyyy-MM-dd'/>" class="oa-input date" validate="{date:true}" />
                                </div>
                            </dd>
                        </dl>
                    </td>
                    <td>
                        <dl>
                            <dt>项目截止时间：</dt>
                            <dd>
                                <div class="oa-input-wrap">
                                    <input type="text" id="expectendtime" name="expectendtime" value="<fmt:formatDate value='${project.expectendtime}' pattern='yyyy-MM-dd'/>" class="oa-input date" validate="{date:true}" />
                                </div>
                            </dd>
                        </dl>
                    </td>
                </tr>

                <tr>
                    <td>
                        <dl>
                            <dt>项目状态：</dt>
                            <dd>
                                <div class="oa-select-wrap">
                                    <select class="oa-select" id="status" name="status">
                                        <option value="0" <c:if test="${project.status==0}">selected</c:if>>未开工</option>
                                        <option value="1" <c:if test="${project.status==1}">selected</c:if>>在建</option>
                                        <option value="2" <c:if test="${project.status==2}">selected</c:if>>停工</option>
                                        <option value="3" <c:if test="${project.status==3}">selected</c:if>>已完工</option>
                                        <option value="4" <c:if test="${project.status==4}">selected</c:if>>建设期</option>
                                        <option value="5" <c:if test="${project.status==5}">selected</c:if>>运营期</option>
                                    </select>
                                </div>
                            </dd>
                        </dl>
                    </td>
                    <td>
                        <dl>
                            <dt>经手人：</dt>
                            <dd>
                                <div class="oa-input-wrap oa-input--readonly">
                                    <input name="charger" type="text" ctltype="selector" class="users oa-input oa-hidden-trigger" value="${project.charger}" validate="{empty:false}" readonly="readonly" />
                                </div>
                                <button class="oa-button-label oa-trigger-hidden-button" type="button">选择用户</button>
                            </dd>
                        </dl>
                    </td>
                </tr>

                <tr>
                    <td>
                        <dl>
                            <dt>合同编码：</dt>
                            <dd>
                                <div class="oa-input-wrap">
                                    <input class="oa-input" type="text" name="contractnum" value="${project.contractnum}">
                                </div>
                            </dd>
                        </dl>
                    </td>
                    <td>
                        <dl>
                            <dt>合同名称：</dt>
                            <dd>
                                <div class="oa-input-wrap">
                                    <input class="oa-input" type="text" name="contractname" value="${project.contractname}">
                                </div>
                            </dd>
                        </dl>
                    </td>
                </tr>

                <tr>
                    <td>
                        <dl>
                            <dt>合同金额<span style="color: red;">(万元)</span>：</dt>
                            <dd>
                                <div class="oa-input-wrap">
                                    <input class="oa-input" type="text" name="contractmoney" value="${project.contractmoney}">
                                </div>
                            </dd>
                        </dl>
                    </td>
                    <td>
                        <dl>
                            <dt>法定代表人：</dt>
                            <dd>
                                <div class="oa-input-wrap oa-input--readonly">
                                    <input name="legalperson" type="text" ctltype="selector" class="users oa-input oa-hidden-trigger" value="${project.legalperson}" validate="{empty:false}" readonly="readonly" />
                                </div>
                                <button class="oa-button-label oa-trigger-hidden-button" type="button">选择用户</button>
                            </dd>
                        </dl>
                    </td>
                </tr>

                <tr>
                    <td>
                        <dl>
                            <dt>联系电话：</dt>
                            <dd>
                                <div class="oa-input-wrap">
                                    <input class="oa-input" type="text" name="tel" value="${project.tel}">
                                </div>
                            </dd>
                        </dl>
                    </td>
                    <td>
                        <dl>
                            <dt>审批文号：</dt>
                            <dd>
                                <div class="oa-input-wrap">
                                    <input class="oa-input" type="text" name="approvalnumber" value="${project.approvalnumber}">
                                </div>
                            </dd>
                        </dl>
                    </td>
                </tr>

                <tr>
                    <td>
                        <dl>
                            <dt>审批时间：</dt>
                            <dd>
                                <div class="oa-input-wrap">
                                    <input type="text" id="approvaltime" name="approvaltime" value="<fmt:formatDate value='${project.approvaltime}' pattern='yyyy-MM-dd'/>" class="oa-input date" validate="{date:true}" />
                                </div>
                            </dd>
                        </dl>
                    </td>
                    <td>
                        <dl>
                            <dt>建设单位：</dt>
                            <dd>
                                <div class="oa-input-wrap">
                                    <input class="oa-input" type="text"  name="procompany" value="${project.procompany}">
                                </div>
                            </dd>
                        </dl>
                    </td>
                </tr>
                
                <tr>
                    <td>
                        <dl>
                            <dt>建设单位联系人：</dt>
                            <dd>
                                <div class="oa-input-wrap">
                                    <input  class="oa-input" type="text" name="procompanyLinker" value="${project.procompanyLinker}" validate="{'maxlength':150}" />
                                </div>
                            </dd>
                        </dl>
                    </td>
                    <td>
                        <dl>
                            <dt>联系人电话：</dt>
                            <dd>
                                <div class="oa-input-wrap">
                                    <input class="oa-input" type="text" name="procompanyLinkerPhone" validate="{maxlength:20,digits:true}" value="${project.procompanyLinkerPhone}">
                                </div>
                            </dd>
                        </dl>
                    </td>
                </tr>

                <tr>
                    
                    <td>
                        <dl>
                            <dt>项目年份：</dt>
                            <dd>
                            	<div class="oa-input-wrap">
                                    <input  type="text" datefmt="yyyy" class="oa-input Wdate" name="proYear" value="${project.proYear}">
                                </div>
                            </dd>
                        </dl>
                    </td>
                    <td>
                        <dl>
                            <dt>备注：</dt>
                            <dd>
                                <div class="oa-input-wrap">
                                    <input class="oa-input" type="text" name="remark" value="${project.remark}">
                                </div>
                            </dd>
                        </dl>
                    </td>
                </tr>
            </table>

            <textarea style="display: none" id="field" name="field" >${project.field }</textarea>
            <input type="hidden" id="classifylibraryid" name="classifylibraryid" value="${project.classifylibraryid }" />
            <input type="hidden" name="id" value="${project.id}">
        </form>
		

		<button type="button" class="oa-button oa-button--primary oa-button--blue" onclick="addField()">新增字段</button>
		<!-- 自定义的字段 -->
        <div id="fielddiv">
			<table id="oa_table" class="oa-table">
				<thead>
					<tr>
						<th>字段名</th>
						<th>字段值</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody class="fieldDiv-wrap">
					<c:forEach items="${project.fieldArr}" var="js">
						<tr class="fieldItem">
							<td>
								<div class="oa-input-wrap">
									<input class="oa-input" name="fieldname" value="${js.fieldname}" validate="{'required':true,'maxlength':150}">
								</div>
							</td>
							<td>
								<div class="oa-input-wrap">
									<input class="oa-input" name="fieldvalue" value="${js.fieldvalue}" validate="{'required':true,'maxlength':150}">
								</div>
							</td>
							<td><button type="button" class="oa-button-label" onclick="removeField(this)">删除</button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
    </div>
	


<script type="text/javascript">
    function addField(){
    	var tr = '<tr class="fieldItem">' +
					'<td><div class="oa-input-wrap"><input class="oa-input" name="fieldname" validate="{\'required\':true,\'maxlength\':150}"></div></td>' +
					'<td><div class="oa-input-wrap"><input class="oa-input" name="fieldvalue" validate="{\'required\':true,\'maxlength\':150}"></div></td>' +
					'<td><button type="button" class="oa-button-label" onclick="removeField(this)">删除</button></td>' +
				'</tr>';

        $(".fieldDiv-wrap").append(tr);

        $("#oa_table").trigger("table.change");
    }
    
    function removeField(obj){
        $(obj).parents(".fieldItem").remove();
        $("#oa_table").trigger("table.change");
    }

    function showResponse(responseText) {
        var obj = new com.hotent.form.ResultMessage(responseText);
        if (obj.isSuccess()) {
            $.ligerDialog.confirm(obj.getMessage()+",是否继续操作","提示信息", function(rtn) {
                if(rtn){
                    window.location.href = window.location.href;
                }else{
                   	// var classifyLibraryId = $("#classifylibraryid").val();
                   // window.location.href = "${ctx}/makshi/project/project/list.ht?classifylibraryid="+classifyLibraryId;
                    window.location.href = "${returnUrl}";
                }
            });
        } else {
            $.ligerDialog.error(obj.getMessage(),"提示信息");
        }
    }

    function getFieldJson(){
        var arr=[];
        var fields=$(".fieldDiv-wrap").eq(0).find(".fieldItem");
        if(fields==null || fields.length==0){
            return arr;
        }
        for(var i=0;i<fields.length;i++){
            var obj={};
            obj['fieldname']=$(fields[i]).find("input[name='fieldname']").eq(0).val();
            obj['fieldvalue']=$(fields[i]).find("input[name='fieldvalue']'").eq(0).val();
            arr.push(obj);
        }
        return arr;
    }

    $(function() {
    	// 触发自定义选择器
		$(".oa-trigger-hidden-button").on("click", function(){
    		$(this).parent("dd").find("a.oa-hidden-trigger").click();
    	});

		//	监听table的自定义change事件 决定是否显示table
    	$("#oa_table").on("table.change", function(){
    		var $trs = $(this).find("tbody").find("tr");
    		if($trs.length > 0){
    			$(this).show();
    		}else{
    			$(this).hide();
    		}
    	});
    	//	初始化触发
    	$("#oa_table").trigger('table.change');


        var options={};
        if(showResponse){
            options.success=showResponse;
        }
        var frm=$('#projectForm').form();
        $("#dataFormSave").click(function() {
            frm.ajaxForm(options);
            if(frm.valid()){
                var jsonArr=getFieldJson();
                //if(jsonArr==null || jsonArr.length==0){
                //  $.ligerDialog.warn("表单字段不能为空！");
                //  return ;
                //}
                $("#field").val(JSON.stringify(jsonArr));
                
                
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
                        $('#projectForm').submit();
                    });
                }else{
                    frm.handleFieldName();
                    $('#projectForm').submit();
                }
            }
        });

        var applicantID = "${project.applicantID}";
         if(applicantID!=null&&applicantID!=""){
             $("input[name='applicantID']").val(applicantID);
         };
         
         var chargerID = "${project.chargerID}";
         if(chargerID!=null&&chargerID!=""){
             $("input[name='chargerID']").val(chargerID);
         };
         
        var memberID = "${project.memberID}";
         if(memberID!=null&&memberID!=""){
             $("input[name='memberID']").val(memberID);
         };
         
         var reportPersonID = "${project.reportPersonID}";
         if(reportPersonID!=null&&reportPersonID!=""){
             $("input[name='reportPersonID']").val(reportPersonID);
         };
         
         var legalPersonID = "${project.legalPersonID}";
         if(legalPersonID!=null&&legalPersonID!=""){
             $("input[name='legalPersonID']").val(legalPersonID);
         };
         
         
    });
</script>
</body>
</html>
