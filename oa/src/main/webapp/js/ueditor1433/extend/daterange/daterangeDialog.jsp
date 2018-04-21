<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="/commons/include/form.jsp" %>
	<link rel="stylesheet" type="text/css" href="../input.css">
	<script type="text/javascript" src="${ctx}/js/ueditor1433/dialogs/internal.js"></script>
	<script type="text/javascript">
		var curElement = null,
			validate = '',
			name = '',
			tableName = '',
			isMoble =false,
			validateAttr="validate",
			isMain =true;
		$(function(){
			//JSON 没有兼容IE时 就用到json2.js
			if(typeof JSON == 'undefined'){
                $('head').append($("<script type='text/javascript' src='${ctx}/js/util/json2.js'>"));
            }
			var tableId = editor.tableId;
			
			if(editor.type=='mb'){
				isMoble =true;	
				validateAttr = "ht-validate";
			}
			
			curElement = dialog.targetEl;
			validate = curElement.getAttribute(validateAttr);	
	
			if(!validate){
				validate = '{}';
			}

			validate = eval("("+validate+")");
			
			if(tableId){ //通过表生成
				name = curElement.getAttribute("name");	
				var names = name.split(':');
				
				isMain = names[0]=='s'?false:true;
				tableName =names[1];
				
				
				var url = __ctx
						+ '/platform/form/bpmFormTable/getTableById.ht?tableId='
						+ tableId;
				$.post(url, function(data) {
					initFieldsDiv(data,validate);
				});
			}
			else{	//编辑器设计表单时获取字段并验证字段
				var element = curElement.parentNode;
				var div = $(element).parents('[type="subtable"]');
				if(div.length> 0){
					isMain = false;
					tableName = div.attr("tablename");
				}
					
				var objForm = window.parent.formContainer.getResult();			
				var params={};
				params.html=objForm.form;
				params.formDefId=editor.formDefId;
				$.post(__ctx + '/platform/form/bpmFormDef/validDesign.ht', params, function(data){
					if(data.valid){
						initFieldsDiv(data,validate);
					}
					else{
						alert(data.errorMsg);
					}
				});
			}
		});
		function initFieldsDiv(data,validate){
			var  mainTable = data.table,pre='';
			var dateList = [];
			if(isMain){
				tableName = mainTable.tableName;
				var fieldList =  mainTable.fieldList;
				getDateList(fieldList,dateList);
				pre =isMoble? "data.main." : "m:"+tableName+":";
			}else{
				var subTableList = mainTable.subTableList,
					subTable =  {};
				for (var i = 0, c; c = subTableList[i++];) {
					if(c.tableName == tableName){
						subTable = c;
						break;
					}
				}
				tableName = subTable.tableName;
				getDateList(subTable.fieldList,dateList)
				pre =isMoble? "data.sub."+tableName+"." : "s:"+tableName+":";
			}
			initFields(dateList,pre,validate);
			
		}
		
		function getDateList(fieldList,dateList){
			for (var i = 0, c; c = fieldList[i++];) {
				if(c.controlType == 15 || c.fieldType == 'date')
					dateList.push(c);
			}
		}
		
		function initFields(data,pre,validate){
			var startdate = $('#startdate'),
				enddate = $('#enddate');
			for (var i = 0, c; c = data[i++];) {
				var opt = $('<option value="'+pre+c.fieldName+'"  >'+c.fieldDesc+'</option>');
				var opt1 = $('<option value="'+pre+c.fieldName+'"  >'+c.fieldDesc+'</option>');
				startdate.append(opt);
				enddate.append(opt1);
			}
			var dateRangeStart =  validate.dateRangeStart,
				dateRangeEnd =  validate.dateRangeEnd;
			//对验证处理
			if(dateRangeStart){
				var target = 	dateRangeStart.target;
				startdate.val(target);
			}
			if(dateRangeEnd){
				var target = 	dateRangeEnd.target;
				enddate.val(target);
			}

		}
		
	
		//确定
		dialog.onok = function() {
			if(!curElement||!validate)return;
			
			var startdate = $("#startdate").val(),
				enddate = $("#enddate").val();

			delete validate['dateRangeStart'];
			delete validate['dateRangeEnd'];

			if(startdate){
				validate['dateRangeStart'] = {target:startdate,range:isMain?'':'sub'};
			}
			
			if(enddate){
				validate['dateRangeEnd'] = {target:enddate,range:isMain?'':'sub'};
			}
			var str = '';
			if(typeof validate != 'undefined' && validate!=""){
				var str = JSON2.stringify(validate).replace(/\"/g,"'");    //JS 把所有的双引号 替换成单引号
			}
			$(curElement).attr(validateAttr,str);
		}; 

	</script>
</head>
<body >
	<div id="inputPanel">
		<fieldset class="base">
			<legend>日期范围验证</legend>
			<table>
				<tr>
					<th>大于等于时间:</th>
					<td>
						<select id="startdate">
							<option value="">--请选择--</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>小于等于时间:</th>
					<td>
						<select id="enddate">
							<option value="">--请选择--</option>
						</select>
					</td>
				</tr>
			</table>
		</fieldset>
	</div>
</body>
</html>