<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 党员转入</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#politicalInForm').form();
			$("a.save").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(1);
				if(frm.valid()){
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
							$('#politicalInForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#politicalInForm').submit();
					}
				}
			});
		});
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerDialog.confirm(obj.getMessage()+",是否继续操作","提示信息", function(rtn) {
					if(rtn){
						window.location.href = window.location.href;
					}else{
						window.location.close();
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
		
		 //添加个人照片
	    function picCallBack(array){
	        if(!array && array!="") return;
	        var fileId=array[0].fileId,
	            fileName=array[0].fileName
	        	extName = array[0].extName;
	        var path= __ctx + "/platform/system/sysFile/getFileById.ht?fileId=" + fileId;
	        if(/(png|gif|jpg)$/gi.test(extName)){
	            $("#picture").val("/platform/system/sysFile/getFileById.ht?fileId=" + fileId);
	            $("#personPic").attr("src",path);
	            $("#picValue").attr("value",path);
	        }else {
	            $.ligerDialog.warn("请选择*png,*gif,*jpg类型图片!");
	        }
	    };
	    //上传照片
	    function addPic(){
	            HtmlUploadDialog({max:1,size:10,callback:picCallBack});
	    };
	    //删除照片
	    function delPic(){
	        $("#picture").val("");
	        $("#personPic").attr("src","${ctx}/commons/image/default_image_male.jpg");
	        $("#picValue").attr("value","");
	    };
	</script>
	<style>
		.pic-wrapper {
			text-align: center;
			margin-bottom: 30px;
		}
		.pic-wrapper img {
			max-width: 200px;
			max-height: 200px;
			margin-top: 10px;
		}
		.oa-table--second {
			margin-bottom: 50px;
		}
		.oa-table--second td,
	    .oa-table--second th{
	        padding: 5px;
	        border: 1px solid #eceff8;
	    }
	    .oa-table--second th {
	    	padding: 10px 20px;
	    }
	    .oa-table--second th{
	        font-weight: bold;
	        width: 100px;
	        background: #f6f7fb;
	    }
		input, select {
			height: 30px;
			min-width: 250px;
			border-radius: 3px;
			border: 1px solid #8c9fd6 !important;
		}
		select {
			height: 36px;
			min-width: 256px;
		}
	</style>
</head>
<body>
<div class="panel" style="height:100%;overflow:auto;">
	<div class="panel-top">
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:;"><span></span>保存</a></div>
			</div>
		</div>
	</div>
	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
		<c:choose>
		    <c:when test="${not empty politicalInItem.id}">
		        <h2>编辑党员转入</h2>
		    </c:when>
		    <c:otherwise>
		        <h2>添加党员转入</h2>
		    </c:otherwise>	
	    </c:choose>
  	</div>
  	<div class="pic-wrapper">
        <a class="link uploadPhoto" href="javascript:;" onclick="addPic();">上传照片</a>
        <a class="link del" href="javascript:;" onclick="delPic();">删除照片</a>
        <p><img id="personPic" onerror="this.src='/commons/image/default_image_male.jpg'" src="${politicalIn.pic}" alt="个人相片" /></p>
    	<input id="picValue" type="hidden" class="hidden" name="m:political_in:pic" lablename="照片" value="${politicalIn.pic}" />
    </div>
    <%-- <div id="div_pic" style="width:400px;height:340px" class="pictureShow-div"> 
     <div id="div_pic_container"></div> 
     <table id="pictureShow_pic_Toolbar"> 
      <tbody>
       <tr> 
        <td width="80"> <a href="javascript:;" field="pic" class="link selectFile" atype="uploadPicture" onclick="{PictureShowPlugin.upLoadPictureFile(this);}">上传图片</a> </td> 
        <td width="80"> <a href="javascript:;" field="pic" class="link del" atype="delPicture" onclick="{PictureShowPlugin.deletePictureFile(this);}">删除图片</a> </td> 
       </tr> 
       <div class="person_pic_div">
              <p><img onerror="this.src='/commons/image/default_image_male.jpg'" src="${pic}" style="height: 378px; width:270px" alt="个人相片" /></p>
       </div>
      </tbody>
     </table> 
    </div> <input type="hidden" class="hidden" name="pic" lablename="照片" controltype="pictureShow" value="${politicalIn.pic}" right="w" /> </td> --%>
	<form id="politicalInForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="oa-table--default oa-table--second" parser="addpermission" data-sort="sortDisabled" cellspacing="0" cellpadding="2" border="1">
  					<tr>
  						<th>姓名：</th>
  						<td><span name="editable-input" isflag="tableflag"><input type="text" name="m:political_in:user_name" lablename="姓名" class="inputText" value="${politicalIn.user_name}" validate="{maxlength:50}" isflag="tableflag" /></span></td>
  						<th>性别：</th>
  						<td>
  							<select name="m:political_in:sex" class="select">
								<option value="1" <c:if test="${politicalIn.sex==1}">selected</c:if> >男</option>
							    <option value="0" <c:if test="${politicalIn.sex==0}">selected</c:if> >女</option>
						    </select>
  						</td>
  					</tr>
  					<tr>
  						<th>出生年月：</th>
  						<td><input name="m:political_in:birthday" type="text" class="Wdate"  datefmt="yyyy-MM"  value='${politicalIn.birthday}'   /></td>
  						<th>民族：</th>
  						<td><span name="editable-input" isflag="tableflag"><input type="text" name="m:political_in:nation" lablename="民族" class="inputText" value="${politicalIn.nation}" validate="{maxlength:50}" isflag="tableflag" /></span></td>
  					</tr>
  					<tr>
  						<th>籍贯：</th>
  						<td><span name="editable-input" isflag="tableflag"><input type="text" name="m:political_in:home" lablename="籍贯" class="inputText" value="${politicalIn.home}" validate="{maxlength:50}" isflag="tableflag" /></span></td>
  						<th>婚姻状况：</th>
  						<td>
  							<select name="m:political_in:marrige_status" class="select">
							    <option value="未婚" <c:if test="${politicalIn.marrige_status=='未婚'}">selected</c:if> >未婚</option>
							    <option value="已婚" <c:if test="${politicalIn.marrige_status=='已婚'}">selected</c:if> >已婚</option>
					   		    <option value="离异" <c:if test="${politicalIn.marrige_status=='离异'}">selected</c:if> >离异</option>
							    <option value="丧偶" <c:if test="${politicalIn.marrige_status=='丧偶'}">selected</c:if> >丧偶</option>
						    </select>
						</td>
  					</tr>
					<tr>
  						<th>入党时间：</th>
  						<td><input name="m:political_in:join_date" type="text" class="Wdate"  datefmt="yyyy-MM" value='${politicalIn.join_date}' /></td>
  						<th>转正时间：</th>
  						<td><input name="m:political_in:beformal_date" type="text" class="Wdate"  datefmt="yyyy-MM" value='${politicalIn.beformal_date}' /></td>
  					</tr>
  					<tr>
  						<th>参加工作时间：</th>
  						<td><input name="m:political_in:start_work_date" type="text" class="Wdate"  datefmt="yyyy-MM" value='${politicalIn.start_work_date}' /></td>
  						<th>入党时所在支部：</th>
  						<td><span name="editable-input" isflag="tableflag"><input type="text" name="m:political_in:join_branch" lablename="入党时所在支部" class="inputText" value="${politicalIn.join_branch}" validate="{maxlength:50}" isflag="tableflag" /></span></td>
  					</tr>
  					<tr>  						
  						<th>转正时所在支部：</th>
  						<td><span name="editable-input" isflag="tableflag"><input type="text" name="m:political_in:beformal_branch" lablename="转正时所在支部" class="inputText" value="${politicalIn.beformal_branch}" validate="{maxlength:50}" isflag="tableflag" /></span></td>
  						<th>入党介绍人：</th>
  						<td><span name="editable-input" isflag="tableflag"><input type="text" name="m:political_in:introducer" lablename="入党介绍人" class="inputText" value="${politicalIn.introducer}" validate="{maxlength:50}" isflag="tableflag" /></span></td>
  					</tr>
  					<tr>  						
  						<th>所在支部：</th>
  						<td><span name="editable-input" isflag="tableflag"><input type="text" name="m:political_in:now_branch" lablename="所在支部" class="inputText" value="${politicalIn.now_branch}" validate="{maxlength:50}" isflag="tableflag" /></span></td>
  						<th>进入党支部日期：</th>
  						<td><input name="m:political_in:in_branch_date" type="text" class="Wdate"  datefmt="yyyy-MM" value='${politicalIn.in_branch_date}' /></td>
  					</tr>
  					<tr>  						
  						<th>现任党内职务：</th>
  						<td><span name="editable-input" isflag="tableflag"><input type="text" name="m:political_in:job_in_branch" lablename="现任党内职务" class="inputText" value="${politicalIn.job_in_branch}" validate="{maxlength:50}" isflag="tableflag" /></span></td>
  						<th>学历：</th>
  						<td><span name="editable-input" isflag="tableflag"><input type="text" name="m:political_in:edu" lablename="学历" class="inputText" value="${politicalIn.edu}" validate="{maxlength:50}" isflag="tableflag" /></span></td>
  					</tr>
  					<tr>  						
  						<th>学位：</th>
  						<td><span name="editable-input" isflag="tableflag"><input type="text" name="m:political_in:degree" lablename="学位" class="inputText" value="${politicalIn.degree}" validate="{maxlength:50}" isflag="tableflag" /></span></td>
  						<th>毕业院校：</th>
  						<td><span name="editable-input" isflag="tableflag"><input type="text" name="m:political_in:college" lablename="毕业院校" class="inputText" value="${politicalIn.college}" validate="{maxlength:50}" isflag="tableflag" /></span></td>
  					</tr>
  					<tr>  						
  						<th>专业：</th>
  						<td><span name="editable-input" isflag="tableflag"><input type="text" name="m:political_in:major" lablename="专业" class="inputText" value="${politicalIn.major}" validate="{maxlength:50}" isflag="tableflag" /></span></td>
  						<th>所在部门：</th>
  						<td><span name="editable-input" isflag="tableflag"><input type="text" name="m:political_in:department" lablename="所在部门" class="inputText" value="${politicalIn.department}" validate="{maxlength:50}" isflag="tableflag" /></span></td>
  					</tr>
  					<tr>  						
  						<th>组织关系所在地：</th>
  						<td><span name="editable-input" isflag="tableflag"><input type="text" name="m:political_in:relative_address" lablename="组织关系所在地" class="inputText" value="${politicalIn.relative_address}" validate="{maxlength:50}" isflag="tableflag" /></span></td>
  						<th>户籍所在地：</th>
  						<td><span name="editable-input" isflag="tableflag"><input type="text" name="m:political_in:home_address" lablename="户籍所在地" class="inputText" value="${politicalIn.home_address}" validate="{maxlength:50}" isflag="tableflag" /></span></td>
  					</tr>
  					<tr>  						
  						<th>现居住地：</th>
  						<td><span name="editable-input" isflag="tableflag"><input type="text" name="m:political_in:living_address" lablename="现居住地" class="inputText" value="${politicalIn.living_address}" validate="{maxlength:50}" isflag="tableflag" /></span></td>
  						<th>身份证号码：</th>
  						<td><span name="editable-input" isflag="tableflag"><input type="text" name="m:political_in:id_number" lablename="身份证号码" class="inputText" value="${politicalIn.id_number}" validate="{maxlength:50}" isflag="tableflag" /></span></td>
  					</tr>
  					<tr>  						
  						<th>联系电话：</th>
  						<td><span name="editable-input" isflag="tableflag"><input type="text" name="m:political_in:connect_tel" lablename="联系电话" class="inputText" value="${politicalIn.connect_tel}" validate="{maxlength:50}" isflag="tableflag" /></span></td>
  						<th>QQ号码：</th>
  						<td><span name="editable-input" isflag="tableflag"><input type="text" name="m:political_in:qq" lablename="QQ号码" class="inputText" value="${politicalIn.qq}" validate="{maxlength:50}" isflag="tableflag" /></span></td>
  					</tr>
  					<tr>  						
  						<th>党费缴纳金额：</th>
  						<td><span name="editable-input" isflag="tableflag"><input type="text" name="m:political_in:pay_amount" lablename="党费缴纳金额" class="inputText" value="${politicalIn.pay_amount}" validate="{maxlength:50}" isflag="tableflag" /></span></td>
  						<th>微信号：</th>
  						<td><span name="editable-input" isflag="tableflag"><input type="text" name="m:political_in:weixin" lablename="微信号" class="inputText" value="${politicalIn.weixin}" validate="{maxlength:50}" isflag="tableflag" /></span></td>
  					</tr>
  					<tr>  						
  						<th>党费缴纳至：</th>
  						<td><input name="m:political_in:pay_date" type="text" class="Wdate"  datefmt="yyyy-MM" value='${politicalIn.pay_date}' /></td>
  						<th>转出日期：</th>
  						<td><input name="m:political_in:out_date" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${politicalIn.out_date}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /></td>
  					</tr>
				</table>
			</div>
		</div>
		<input type="hidden" name="id" value="${politicalIn.id}"/>
		<input type="hidden" name="is_in" value="${politicalIn.is_in}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
